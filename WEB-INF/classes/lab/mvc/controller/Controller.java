package lab.mvc.controller;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lab.mvc.model.Person;

@WebServlet("/Controller")
public class Controller extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String birthDay = request.getParameter("birthDay");
        String sex = request.getParameter("sex");

        try {
            LocalDate birthDate = LocalDate.parse(birthDay);

            Person person = new Person(
                firstName, lastName, birthDay, sex
            );

            request.setAttribute("person", person);

            String page;

            if (birthDate.isBefore(LocalDate.of(1990, 1, 1))) {
                page = "/old.jsp";
            } else {
                page = "/young.jsp";
            }

            request.getRequestDispatcher(page)
                   .forward(request, response);

        } catch (java.time.format.DateTimeParseException
                 | NullPointerException e) {
            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST,
                "Invalid or missing birth date."
            );
        }
    }
}
