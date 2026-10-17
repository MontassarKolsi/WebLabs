
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.temporal.ChronoUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ShowSession extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        Integer count;
        try {
            count = (Integer) session.getAttribute("visitCount");
        } catch (ClassCastException e) {
            count = null;
        }
        if (count == null) {
            count = 1;
        } else {
            count++;
        }
        session.setAttribute("visitCount", count);

        String fullName = (String) session.getAttribute("fullName");
        String birthDate = (String) session.getAttribute("birthDate");

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html><head>");
        out.println("<meta charset='UTF-8'><title>Show Session</title>");
        out.println("</head><body>");

        if (fullName == null || birthDate == null) {
            out.println("<h2>Welcome to my site!</h2>");
            out.println("<p>This is your first visit.</p>");
            out.println("<p>Please enter your information:</p>");

            out.println("<form action='ShowSession' method='post'>");
            out.println("Full name: <input type='text' name='fullName' required><br><br>");
            out.println("Date of birth: <input type='date' name='birthDate' required><br><br>");
            out.println("<input type='submit' value='Save information'>");
            out.println("</form>");
        } else {
            long days = daysUntilBirthday(birthDate);

            out.println("<h2>Welcome back!</h2>");
            out.println("<p>Hi, " + escapeHtml(fullName) + ".</p>");
            out.println("<p>There are " + days
                    + " days to your birthday.</p>");
        }

        out.println("<p>Visits to this page during this session: "
                + count + "</p>");
        out.println("<p><a href='ShowSession'>Visit again</a></p>");
        out.println("<p><a href='index.html'>Back to main form</a></p>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String fullName = request.getParameter("fullName");
        String birthDate = request.getParameter("birthDate");

        if (fullName == null || fullName.trim().isEmpty()
                || birthDate == null || birthDate.trim().isEmpty()) {
            response.sendRedirect("ShowSession");
            return;
        }

        try {
            LocalDate.parse(birthDate);
        } catch (java.time.format.DateTimeParseException e) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println(
                    "<p>Invalid date. Please go back and try again.</p>"
                    + "<p><a href='ShowSession'>Return</a></p>");
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("fullName", fullName.trim());
        session.setAttribute("birthDate", birthDate);

        // Redirect to GET; this does not increment the visit counter again.
        response.sendRedirect("ShowSession");
    }

    private long daysUntilBirthday(String birthDate) {
        LocalDate today = LocalDate.now();
        LocalDate dob = LocalDate.parse(birthDate);

        MonthDay birthday = MonthDay.from(dob);
        LocalDate nextBirthday = birthday.atYear(today.getYear());

        if (!nextBirthday.isAfter(today)) {
            nextBirthday = birthday.atYear(today.getYear() + 1);
        }

        return ChronoUnit.DAYS.between(today, nextBirthday);
    }

    private String escapeHtml(String value) {
        return value.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
}