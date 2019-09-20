package example.com;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/metrics")
public class MetricsServlet extends HttpServlet {

    private int hits;

        @Override
        protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

            hits++;

            PrintWriter out = resp.getWriter();
            resp.setContentType("text/plain");
            out.println("# HELP wildfly_metrics_hits_total The total number of requests received.");
            out.println("# TYPE wildfly_metrics_hits_total counter");
            out.printf("wildfly_metrics_hits_total %f\n", (float)hits);
        }
}
