
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FormServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String birthDate = request.getParameter("birthDate");
        String sex = request.getParameter("sex");
        int visits = 0;

	// Count form submissions using a cookie
	Cookie[] existingCookies = request.getCookies();
        if (existingCookies != null) {
            for (Cookie cookie : existingCookies) {
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

        // Create the four cookies
        Cookie firstNameCookie = new Cookie("firstName", firstName);
        Cookie lastNameCookie = new Cookie("lastName", lastName);
        Cookie birthDayCookie = new Cookie("birthDay", birthDate);
        Cookie sexCookie = new Cookie("sex", sex);
        Cookie visitCookie = new Cookie("visitCount", String.valueOf(visits));

        // Make the cookies available to the browser
        response.addCookie(firstNameCookie);
        response.addCookie(lastNameCookie);
        response.addCookie(birthDayCookie);
        response.addCookie(sexCookie);
        visitCookie.setMaxAge(30 * 24 * 60 * 60);
        visitCookie.setPath(request.getContextPath());
        response.addCookie(visitCookie);
	

        // Display the submitted information
        PrintWriter out = response.getWriter();


        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'>");
        out.println("<title>Person Information</title></head>");
        out.println("<body>");

        out.println("<h2>Submitted Person Information</h2>");
        out.println("<table border='1' cellpadding='8'>");
        out.println("<tr><th>Field</th><th>Value</th></tr>");
        out.println("<tr><td>First name</td><td>" + firstName + "</td></tr>");
        out.println("<tr><td>Last name</td><td>" + lastName + "</td></tr>");
        out.println("<tr><td>Birth date</td><td>" + birthDate + "</td></tr>");
        out.println("<tr><td>Sex</td><td>" + sex + "</td></tr>");
	out.println("<tr><td>Form submissions: " + visits + "</td></tr>");
        out.println("</table>");

        out.println("<p>Four cookies have been added to the response.</p>");
        out.println("<p><a href='index.html'>Back to form</a></p>");
        out.println("</body></html>");
    }
}