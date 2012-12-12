package com.maven.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet(name = "EchoServlet", urlPatterns = { "/Echo" }, description = "retrieves some test table from database")
public class EchoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("application/json;charset=UTF-8");
            final PrintWriter out = response.getWriter();
            out.print("{ \"name\":\"error\", \"aaData\": []}");
            out.flush();
            return;
        } catch (Exception ex) {
            noData(response);
            return;
        }
    }
    
    private void noData(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        final PrintWriter out = response.getWriter();
        out.print("{ \"name\":\"subcategories\", \"aaData\": []}");
        out.flush();
        return;
    }

}