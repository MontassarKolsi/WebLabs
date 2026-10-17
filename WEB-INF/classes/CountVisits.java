
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CountVisits extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        int visits = 0;

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("visitCount".equals(cookie.getName())) {
                    try {
                        visits = Integer.parseInt(cookie.getValue());
                    } catch (NumberFormatException e) {
                        visits = 0;
                    }
                    break;
                }
            }
        }

        visits++;

        Cookie visitCookie =
                new Cookie("visitCount", String.valueOf(visits));

        // Keep the cookie for 30 days
        visitCookie.setMaxAge(30 * 24 * 60 * 60);

        // Make it available throughout this web application
        visitCookie.setPath(request.getContextPath());

        response.addCookie(visitCookie);
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'>");
        out.println("<title>Visit Counter</title></head><body>");
        out.println("<h2>Visit Counter</h2>");
        out.println("<p>You have visited this page "
                + visits + " time(s).</p>");
        out.println("<p><a href='CountVisits'>Count another visit</a></p>");
        out.println("<p><a href='index.html'>Back to form</a></p>");
        out.println("</body></html>");
    }
}