package servlet_exercises;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;





@WebServlet("/json")
public class JSONServlet extends HttpServlet {
	public class Student {
		private int id;
		private String firstname;
		private String lastname;
		private String streetaddress;
		private int postcode;
		private String postoffice;

		public Student() {
			id = -1;
			firstname = "";
			lastname = "";
			streetaddress = "";
			postcode = -1;
			postoffice = "";
		}

		public Student(int id, String firstname, String lastname, String streetaddress, int postcode, String postoffice ) {
			this.id = id;
			this.firstname = firstname;
			this.lastname = lastname;
			this.streetaddress = streetaddress;
			this.postcode = postcode;
			this.postoffice = postoffice;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getFirstname() {
			return firstname;
		}

		public void setFirstname(String firstname) {
			this.firstname = firstname;
		}

		public String getLastname() {
			return lastname;
		}

		public void setLastname(String lastname) {
			this.lastname = lastname;
		}
		
		public String getStreetaddress() {
			return streetaddress;
		}

		public void setStreetaddress(String streetaddress) {
			this.streetaddress = streetaddress;
		}
	 
		public int getPostcode() {
			return postcode;
		}

		public void setPostcoded(int postcode) {
			this.postcode = postcode;
		}
		
		public String getPostoffice() {
			return postoffice;
		}

		public void setPostoffice(String postoffice) {
			this.postoffice = postoffice;
		}

		@Override
		public String toString() {
			return id + ": " + firstname + " " + lastname + ", " + streetaddress + ", " + postcode + ", " + postoffice ;
		}
	}
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		
		// Set the response data type to JSON and the character encoding to UTF-8
		response.setContentType("text/json");
		response.setCharacterEncoding("UTF-8");
				
		
		List<Student> studentList =  new ArrayList<Student>();

		studentList.add(new Student(10, "Khang","Duong", "Malmi", 00700, "HELSINKI"));
		studentList.add(new Student(20, "Jack","Grealish", "Kannelmaki", 22700, "HELSINKI"));
		

		Gson gson = new Gson();
		String jsonString = gson.toJson(studentList); 
						
		out.println(jsonString);
	}
}
// End
