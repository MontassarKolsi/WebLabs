
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReadCookie extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String firstName = "Not found";
        String lastName = "Not found";
        String birthDay = "Not found";
        String sex = "Not found";

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                switch (cookie.getName()) {
                    case "firstName":
                        firstName = cookie.getValue();
                        break;
                    case "lastName":
                        lastName = cookie.getValue();
                        break;
                    case "birthDay":
                        birthDay = cookie.getValue();
                        break;
                    case "sex":
                        sex = cookie.getValue();
                        break;
                }
            }
        }

        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'>");
        out.println("<title>Read Cookies</title></head><body>");
        out.println("<h2>Information Read from Cookies</h2>");
        out.println("<table border='1' cellpadding='8'>");
        out.println("<tr><th>Cookie</th><th>Value</th></tr>");
        out.println("<tr><td>firstName</td><td>" + firstName + "</td></tr>");
        out.println("<tr><td>lastName</td><td>" + lastName + "</td></tr>");
        out.println("<tr><td>birthDay</td><td>" + birthDay + "</td></tr>");
        out.println("<tr><td>sex</td><td>" + sex + "</td></tr>");
        out.println("</table>");
        out.println("<p><a href='index.html'>Back to form</a></p>");
        out.println("</body></html>");
    }
}