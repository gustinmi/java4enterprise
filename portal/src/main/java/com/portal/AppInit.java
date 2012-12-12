package com.portal;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class AppInit implements ServletContextListener {

	public Logger log = Logger.getLogger(this.getClass()); 
	
    @Override public void contextInitialized(ServletContextEvent arg0) {
    	System.out.println("==========  App init =================");
        try {
        	DOMConfigurator.configure(this.getClass().getResource("/log4j.xml"));
            Database database = Database.instance;	
        }
        catch (RuntimeException e) {
        	log.error("ERROR!!! On App startup.", e);
            throw new RuntimeException(e);
        }
        
        System.out.println("==========  App started OK =================");
    }

    @Override public void contextDestroyed(ServletContextEvent arg0) {
        log.info("==========  App shutdown =================");
    }
	
}
