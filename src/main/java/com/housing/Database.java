package com.housing;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;

public class Database {
	private static final Logger log = Logger.getLogger(Database.class);
	public static final Database instance = new Database();
	private final DataSource dataSource;
	private boolean mockup;

	private Database() {
		InitialContext ctx = null;
		try {
			ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/studentDatasource");
			mockup = false;
		} catch (NamingException e) {
			final String reason = "Failed to retrieve the datasource from the context: " + e.toString();
			log.error(reason, e);
			throw new IllegalStateException(reason, e);
		}
	}
	
	// IOC - Inversion of control 
	public void setProperties(Properties mailerProps) {
         this.mockup = Boolean.parseBoolean(mailerProps.getProperty("mockup", "false"));
    }
    
	public Connection getConnection() throws SQLException{
		try {
	        return dataSource.getConnection();
        } catch (SQLException e) {
        	log.error("Error Retrieving data for controlpanel: " + e.toString(), e);
        	throw e;
        }
	}
	
	public String vrniVseSekcije() {
		if (mockup) return "[{\"id\":\"1\",\"naziv\":\"kupim\"},{\"id\":\"2\",\"naziv\":\"zamenjam\"}]";
		
        final String sql =  "SELECT id,naziv FROM d_sekcija"; 
        final StringBuilder output = new StringBuilder();
        try {
            Connection conn = dataSource.getConnection();
            try {
                final PreparedStatement pst = conn.prepareStatement(sql);
                log.debug("Pst sql:" + sql);
                try {
                    ResultSet rs = pst.executeQuery();
                    try {
                        if (rs.next()) {
                            output.append("{");
                            output.append("\"id\":\"");
                            output.append(rs.getString("id"));
                            output.append("\",");
                            output.append("\"naziv\":\"");
                            output.append(rs.getString("naziv"));
                            output.append("\"");
                            output.append("}");
                        }
                        while (rs.next()) { // the rest if any
                            output.append(",");
                            output.append("{");
                            output.append("\"id\":\"");
                            output.append(rs.getString("id"));
                            output.append("\",");
                            output.append("\"naziv\":\"");
                            output.append(rs.getString("naziv"));
                            output.append("\"");
                            output.append("}");
                        }
                    } finally {
                        rs.close();
                    }
                } finally {
                    pst.close();
                }

            } finally {
                conn.close();
            }
        } catch (SQLException e) {
            log.error("Error" + e.toString(), e);
        }
        final String response;
        if (output.length() > 0) response = "[" + output.toString() + "]";
        else response = null;
        return response;
    }

}