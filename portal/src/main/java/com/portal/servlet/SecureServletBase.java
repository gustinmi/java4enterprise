package com.portal.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.portal.SessionUser;

public abstract class SecureServletBase extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AtomicBoolean useAuth = new AtomicBoolean();
    
    protected abstract void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    protected abstract void post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    
    public void init(ServletConfig config) throws ServletException {
        super.init();
        final Boolean yes = Boolean.parseBoolean(config.getServletContext().getInitParameter("authenticate"));
        useAuth.getAndSet(yes);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (useAuth.get()) {
            final SessionUser sessionusr = (SessionUser) (request.getSession().getAttribute("susr"));
            if (null != sessionusr) {
                this.get(request,response);
                return;
            } else {
                String message = "Expired user session";
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("text/plain");
                response.setContentLength(message.length());
                PrintWriter out = response.getWriter();
                out.print(message);
                out.flush();
                out.close();
                return;
            }
        }else{
            this.get(request,response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (useAuth.get()) {
            final SessionUser sessionusr = (SessionUser) (request.getSession().getAttribute("susr"));
            if (null != sessionusr) {
                this.post(request,response);
                return;
            } else {
                String message = "Expired user session";
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("text/plain");
                response.setContentLength(message.length());
                PrintWriter out = response.getWriter();
                out.print(message);
                out.flush();
                out.close();
                return;
            }

        }else{
            this.post(request,response);
        }
    }

}