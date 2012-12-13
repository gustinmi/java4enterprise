package com.portal.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import com.portal.Database;

@WebServlet(name = "UserDetailsServlet", urlPatterns = { "/UserDetails" })
public class UserDetailsServlet extends SecureServletBase {
    private static final long serialVersionUID = 1L;
    private static final Logger log = Logger.getLogger(UserDetailsServlet.class);

    protected void get(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        post(request, response);
    }

    protected void post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("Edit servlet");
        try {
            final String item = request.getParameter("user");
            if (item == null || item.isEmpty()) {
                noData(response);
                return;
            }
            String jsonStr = Database.instance.getUserDetails(item);
            if (null != jsonStr) {
                jsonStr = "{\"status\":\"ok\",\"data\": " + jsonStr  + " }";
            }
            log.info("CodesServlet finished processing");
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
        out.print("{\"status\":\"err\",\"msg\":\"Napaka pri komunikaciji!\"}");
        out.flush();
        return;
    }

}
