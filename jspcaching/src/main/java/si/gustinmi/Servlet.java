package si.gustinmi;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;


@WebServlet(name = "Servlet", urlPatterns = "/servlet1")
public class Servlet extends HttpServlet {

    CachedObject cache = null;

    class CachedObject {
        final String hugeCache;
        final Date created;

        CachedObject(String hugeCache) {
            this.hugeCache = hugeCache;
            created = new Date();
            //expensive operation
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public String getHugeCache() {
            return hugeCache;
        }

        public Date getCreated() {
            return created;
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        cache = new CachedObject("some large clob");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=utf8");
        out.println("<!DOCTYPE html><html><head><title>Test</title></head>");
        out.println("<body><p>Some cache " + cache.getHugeCache() + "</p>");
        out.println("<p>Timestamp " + cache.getCreated() + "</p></body></html>");
        out.flush();
        out.close();
        return;
    }
}
