package servlet_exercises;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data_access.StudentDAO;
import model.Student;

/**
 * Servlet implementation class StudentAddServlet
 */
@WebServlet("/addStudent")
public class StudentAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();

		int studentId = Integer.parseInt(request.getParameter("id"));
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String street = request.getParameter("street");
		int postcode = Integer.parseInt(request.getParameter("postcode"));
		String postoffice = request.getParameter("postoffice");

		Student student = new Student(studentId, firstname, lastname, street, postcode, postoffice);
		StudentDAO studentDAO = new StudentDAO();

		int errorCode = studentDAO.insertStudent(student);
		if (errorCode == 0) {
			out.println("Student data added.");
		} else if (errorCode == 1) {
			out.println("Nothing added. The id is already in use");
		} else {
			out.println("Nothing added. An unknown error occurred.");
		}
	}
}
