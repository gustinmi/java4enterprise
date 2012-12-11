package com.housing;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

@WebServlet(name = "EchoServlet", urlPatterns = { "/Echo" }, description = "retrieves some test table from database")
public class EchoServlet  extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(EchoServlet.class);
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            final String name = request.getParameter("name");
            if (null==name || name.isEmpty()){
                noData(response);
                return;
            }
            //final String jsonStr = Database.instance.getCodeTable(currSql, name, param, null);
            final String jsonStr = "{ \"name\":\"echo\", \"aaData\": [\"prodam\",\"kupim\",\"zamenjam\"]}";
            log.info("EchoServlet finished processing");
            response.setContentType("application/json;charset=UTF-8");
            final PrintWriter out = response.getWriter();
            out.print(jsonStr);
            out.flush();
            return;
        } catch (Exception ex) {
            log.error("Error while processing request:", ex);
            noData(response);
            return;
        }
    }
    
    private void noData(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        final PrintWriter out = response.getWriter();
        out.print("{ \"name\":\"echo\", \"aaData\": []}");
        out.flush();
        return;
    }

}
