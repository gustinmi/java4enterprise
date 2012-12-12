package com.portal;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class PageBean {
	public Logger log = Logger.getLogger(this.getClass());
	protected HttpServletRequest request = null;
	protected ServletContext context = null;
	protected SessionUser sessionUser;
	protected boolean isLoggedIn;
	
	public PageBean() {
		
	}

	public boolean process() {
		log.debug("Page opened");
		if (request == null || context == null) { throw new RuntimeException("Init view failed!"); }

		// do we have existing session
		this.sessionUser = (SessionUser) (request.getSession().getAttribute("susr"));
		if (null == sessionUser) {

			//session data : see if user has a cookie
			this.sessionUser = getUserFromCookie();
			if (null!=sessionUser){
				isLoggedIn = true;
				request.getSession().setAttribute("susr", sessionUser);
				log.debug(String.format("User added to the session from cookie: %s", sessionUser.toString()));	
			}else{
				return false;
			}
			
		}else{
			log.debug("Existing session user");
		}
		return true;
	}
	
	private SessionUser getUserFromCookie() {
		SessionUser u = null;
		log.debug("check session if exists");
		if (null == sessionUser) {
			log.debug("check cookie and logg user in");
			String cookieName = "_AUTH";
			Cookie cookies[] = request.getCookies();
			if (cookies != null) {
				for (Cookie c : cookies) {
					if (c.getName().equals(cookieName)) {
						log.debug("We have gosAuth cookie");
						String[] cookieValues = c.getValue().split(":");
						String user = cookieValues[0];
						String cryptedPassword = cookieValues[1];
						log.debug(String.format("Cookie data: %s, %s", user, cryptedPassword));
						boolean ok = Database.instance.checkUser(user, cryptedPassword);
						if (ok){
							u = new SessionUser(user);
						}
					}
				}
			}
		}
		return u;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setContext(ServletContext context) {
		this.context = context;
	}

	public SessionUser getSessionUser() {
    	return sessionUser;
    }

	public String getUsername() {
    	return this.sessionUser.getUserName();
    }
		
}
