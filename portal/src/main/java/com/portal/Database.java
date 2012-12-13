package com.portal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

public class Database {
	private static final Logger log = Logger.getLogger(Database.class);
	public static final Database instance = new Database();
	//private final Worker worker;
	  
	private final DataSource dataSource;

	private Database() {
		InitialContext ctx = null;
		try {
			ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/gostinecDB");
		} catch (NamingException e) {
			final String reason = "Failed to retrieve the datasource from the context: " + e.toString();
			log.error(reason, e);
			throw new IllegalStateException(reason, e);
		}
		//worker = new Worker(dataSource);
	    //worker.start();
	}

	public Connection getConnection() throws SQLException {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			log.error("Error Retrieving data for controlpanel: " + e.toString(), e);
			throw e;
		}
	}

	public boolean checkUser(final String user, final String password) {
		boolean isValid = true;
		ResultSet rs = null;
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			final String sql = "SELECT * FROM users WHERE name = ? and password = ? LIMIT 0, 1";
			connection = dataSource.getConnection();
			statement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.setString(1, user);
			statement.setString(2, password);
			rs = statement.executeQuery();
			if (!rs.next()) {
				isValid = false;
			}
		} catch (SQLException e) {
			log.error("Error db", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					;
				}
				rs = null;
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					;
				}
				statement = null;
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					;
				}
				connection = null;
			}
		}

		return isValid;
	}

	//  Worker thread (drop some job on worker and forget it)
	
    public void logValue(final String message, final Timestamp timestamp) {
        final LogValue logValue = new LogValue(message, timestamp);
        //worker.queue(logValue);
        if (log.isDebugEnabled())
            log.debug("Queueing log value for logId " + logValue.message + ": " + logValue.timestamp.toString());
    }
	
    private static class Worker implements Runnable {
        private static final int MAX_QUEUE_SIZE = 1000;
        private static final Logger log = Logger.getLogger(Database.class.getCanonicalName() + ".Worker");
        private final Deque<Job> queue = new LinkedList<Job>();
        private final Lock lock = new ReentrantLock();
        private final Condition jobAdded = lock.newCondition();
        private final Condition jobRemoved = lock.newCondition();
        private final DataSource dataSource;
        private final AtomicBoolean running = new AtomicBoolean(false);
        private final Calendar calendar = Calendar.getInstance();
        private Thread thread = null;

        Worker(final DataSource dataSource) {
            this.dataSource = dataSource;
            calendar.setFirstDayOfWeek(Calendar.MONDAY);
        }

        @Override
        public void run() {
            log.info("Worker started");
            processing:
            for (;;) {
                Job job;
                lock.lock();
                try {
                    while (null == (job = queue.pollFirst())) {
                        if (!running.get()) break processing;
                        if (log.isDebugEnabled()) log.debug("Waiting for a job");
                        try {
                            jobAdded.await();
                        }
                        catch (InterruptedException ie) {
                            log.error("Interrupted while waiting for a job: " + ie.toString(), ie);
                        }
                    }
                    jobRemoved.signal();
                }
                finally {
                    lock.unlock();
                }
                job.run(dataSource);
            }
            log.info("Worker stopped");
        }

        void start() {
            log.debug("Starting database worker");
            lock.lock();
            try {
                if (running.getAndSet(true)) return; // already running
                thread = new Thread(this, "Database worker");
                thread.start();
            }
            finally {
                lock.unlock();
            }
        }

        void stop() {
            log.debug("Stopping database worker");
            Thread runningThread;
            lock.lock();
            try {
                if (!running.getAndSet(false)) return; // already stopped
                runningThread = thread;
                thread = null;
                jobAdded.signal();
            }
            finally {
                lock.unlock();
            }

            // wait for the thread to finish while not holding a lock
            try {
                runningThread.join(2000); // we give it 2 seconds to empty its queue
            } catch (InterruptedException e) {
                log.error("Interrupted while waiting for the worker thread to finish: " + e.toString(), e);
            }
            runningThread.interrupt(); // we interrupt it just in case it hasn't finished yet
        }

        void queue(final Job job) {
            if (!running.get()) throw new IllegalStateException("Worker thread is not running");
            lock.lock();
            try {
                while (queue.size() >= MAX_QUEUE_SIZE) {
                    try {
                        jobRemoved.await();
                    } catch (InterruptedException e) {
                        log.error("Interrupted while waiting for the queue to empty: " + e.toString(), e);
                    }
                }
                queue.addLast(job);
                jobAdded.signal();
            }
            finally {
                lock.unlock();
            }
        }
    }

    private static interface Job {
        void run(DataSource dataSource);
    }

    private static class LogValue implements Job {
        private final static Logger log = Logger.getLogger(Database.class.getCanonicalName() + ".LogValue");
        final String message;
        final Timestamp timestamp;

        LogValue(final String logId, final Timestamp timestamp) {
            this.message = logId;
            this.timestamp = null == timestamp ? new Timestamp(System.currentTimeMillis()) : timestamp;
        }

        @Override
        public void run(final DataSource dataSource) {
            if (log.isDebugEnabled()) log.debug("Storing state value for log ID " + message + ": " + timestamp.toString() + ".");
            try {
                Connection conn = dataSource.getConnection();
                try {
                    PreparedStatement statement = conn.prepareStatement("insert into log (message, timestamp) values (?, ?)");
                    try {
                        statement.setString(1, message);
                        statement.setTimestamp(2, timestamp);
                        statement.execute();
                    }
                    finally {
                        statement.close();
                    }
                }
                finally {
                    conn.close();
                }
            } catch (SQLException e) {
                log.error("Error while trying to log a value for logId " + message + ": ", e);
            }
        }
    }
	
}
