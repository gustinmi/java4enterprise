package com.portal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

public class Database {
	private static final Logger log = Logger.getLogger(Database.class);
	public static final Database instance = new Database();

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

}
