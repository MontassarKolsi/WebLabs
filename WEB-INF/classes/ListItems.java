
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ListItems extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        displayItems(request, response, false);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();

        @SuppressWarnings("unchecked")
        ArrayList<String> items =
                (ArrayList<String>) session.getAttribute("items");

        if (items == null) {
            items = new ArrayList<String>();
            session.setAttribute("items", items);
        }

        String newItem = request.getParameter("newItem");

        if (newItem != null && !newItem.trim().isEmpty()) {
            items.add(newItem.trim());
        }

        // Redirect to GET so refreshing doesn't submit the same item again.
        response.sendRedirect("ListItems");
    }

    private void displayItems(HttpServletRequest request,
                              HttpServletResponse response,
                              boolean unused)
            throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();

        @SuppressWarnings("unchecked")
        ArrayList<String> items =
                (ArrayList<String>) session.getAttribute("items");

        if (items == null) {
            items = new ArrayList<String>();
            session.setAttribute("items", items);
        }

        Integer calls = (Integer) session.getAttribute("listItemsCalls");
        if (calls == null) {
            calls = 1;
        } else {
            calls++;
        }
        session.setAttribute("listItemsCalls", calls);

        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'>");
        out.println("<title>My Items</title></head><body>");
        out.println("<h2>Items Stored in Your Session</h2>");

        if (items.isEmpty()) {
            out.println("<p>Your list is empty. Add your first item!</p>");
        } else {
            out.println("<ol>");
            for (String item : items) {
                out.println("<li>" + escapeHtml(item) + "</li>");
            }
            out.println("</ol>");
        }

        out.println("<p>Total items: " + items.size() + "</p>");
        out.println("<p>Times ListItems was displayed in this session: "
                + calls + "</p>");
        out.println("<p><a href='items.html'>Add another item</a></p>");
        out.println("<p><a href='index.html'>Back to main form</a></p>");
        out.println("</body></html>");
    }

    private String escapeHtml(String value) {
        return value.replace("&", "&amp;")
                    .replace("<", "&lt;")
                    .replace(">", "&gt;")
                    .replace("\"", "&quot;")
                    .replace("'", "&#39;");
    }
}