package com.portal.servlet;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.portal.Database;

/**
 * This is servlet is called from main page (login html form) Process login
 * request, if there is a login problem, redirect to main page, with param errId
 * to be displayed.
 */
@WebServlet(name = "LoginServlet", urlPatterns = { "/Login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String uid = request.getParameter("ime");
		String pwd = request.getParameter("geslo");
		if (uid == null || uid.isEmpty() || pwd == null || pwd.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/error.jsp");
			return;
		}
		Boolean ok = Database.instance.checkUser(uid,pwd);
		if (!ok) {
			response.sendRedirect(request.getContextPath() + "/error.jsp");
			return;
		}
		Cookie cookie = new Cookie("_AUTH", uid + ":" + pwd);
		cookie.setMaxAge(365 * 24 * 60 * 60);
		response.addCookie(cookie);
		response.sendRedirect(request.getContextPath() + "/home.jsp");
		return;
	}

}
