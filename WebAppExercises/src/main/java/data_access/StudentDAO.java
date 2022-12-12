package data_access;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.google.gson.Gson;

import java.util.ArrayList;

import model.Student;

/**
 * DAO class for accessing movies. NB! There should be NO user input/output in
 * this class! This class can be used in a stand-alone Java application or as a
 * part of the server-side implementation of a web application. => This code
 * works as it is with all major RDBMSes and SQLite, Excel etc.
 * 
 * @author Kari
 * @version 1.1 2019-11-03
 */
public class StudentDAO {

	public StudentDAO() {
		// In Tomcat 8 environment, the JDBC driver must be explicitly loaded as below
		try {
			Class.forName(ConnectionParameters.jdbcDriver);
		} catch (ClassNotFoundException cnfe) {
			System.out.println(cnfe.getMessage());
		}
	}

	/**
	 * Open a new database connection
	 * 
	 * @throws SQLException
	 */
	private Connection openConnection() throws SQLException {
		return DriverManager.getConnection(ConnectionParameters.connectionString, ConnectionParameters.username,
				ConnectionParameters.password);
	}

	/**
	 * Retrieve all movies from the database
	 * 
	 * @return List<Movie>
	 * @throws SQLException
	 */
	public List<Student> getStudents() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Student> StudentList = new ArrayList<Student>();

		try {
			connection = openConnection();

			String sqlText = "SELECT id, firstname, lastname, streetaddress, postcode, postoffice FROM Student ORDER BY lastname";

			preparedStatement = connection.prepareStatement(sqlText);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				int id = resultSet.getInt("id");
				String firstname = resultSet.getString("firstname");
				String lastname = resultSet.getString("lastname");
				String streetaddress = resultSet.getString("streetaddress");
				int postcode = resultSet.getInt("postcode");
				String postoffice = resultSet.getString("postoffice");
				StudentList.add(new Student(id, firstname, lastname, streetaddress, postcode, postoffice));
			}

		} catch (SQLException sqle) {
			System.out.println("\n[ERROR] MovieDAO: getMovies() failed. " + sqle.getMessage() + "\n");
			StudentList = null;

		} finally {
			DbUtils.closeQuietly(resultSet, preparedStatement, connection);
		}

		return StudentList;
	}

	public List<Student> getStudentsJSON() {
		Gson gson = new Gson();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Student> StudentList = new ArrayList<Student>();

		try {
			connection = openConnection();

			String sqlText = "SELECT id, firstname, lastname, streetaddress, postcode, postoffice FROM Student ORDER BY lastname";

			preparedStatement = connection.prepareStatement(sqlText);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				int id = resultSet.getInt("id");
				String firstname = resultSet.getString("firstname");
				String lastname = resultSet.getString("lastname");
				String streetaddress = resultSet.getString("streetaddress");
				int postcode = resultSet.getInt("postcode");
				String postoffice = resultSet.getString("postoffice");
				StudentList.add(new Student(id, firstname, lastname, streetaddress, postcode, postoffice));

			}
			String jsonString = gson.toJson(StudentList);
			System.out.println(jsonString);
		} catch (SQLException sqle) {
			System.out.println("\n[ERROR] MovieDAO: getMovies() failed. " + sqle.getMessage() + "\n");
			StudentList = null;

		} finally {
			DbUtils.closeQuietly(resultSet, preparedStatement, connection);
		}
		return StudentList;

	}

	/**
	 * Retrieve all movies from the given year from the database
	 * 
	 * @param givenYear - the year to be used as the filter in the query
	 * @return List<Movie>
	 * @throws SQLException
	 */
	public List<Student> getStudentByID(int studentid) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Student> StudentList = new ArrayList<Student>();

		try {
			connection = openConnection();

			String sqlText = "SELECT id, firstname, lastname, streetaddress, postcode, postoffice FROM Student WHERE id = ? ORDER BY firstname";

			preparedStatement = connection.prepareStatement(sqlText);
			preparedStatement.setInt(1, studentid);

			resultSet = preparedStatement.executeQuery();
			boolean rowFound = false;
			while (resultSet.next()) {
				rowFound = true;
				int id = resultSet.getInt("id");
				String firstname = resultSet.getString("firstname");
				String lastname = resultSet.getString("lastname");
				String streetaddress = resultSet.getString("streetaddress");
				int postcode = resultSet.getInt("postcode");
				String postoffice = resultSet.getString("postoffice");
				StudentList.add(new Student(id, firstname, lastname, streetaddress, postcode, postoffice));
			}
			if (rowFound == false) {
				System.out.println("Unknown student id" + " (" + studentid + ")");
			}
		} catch (SQLException sqle) {
			System.out.println("[ERROR] MovieDAO: getMoviesFromGivenYear() failed. " + sqle.getMessage() + "\n");
			StudentList = null;

		} finally {
			DbUtils.closeQuietly(resultSet, preparedStatement, connection);
		}

		return StudentList;

	}

	/**
	 * Insert a movie into the database
	 * 
	 * @return 0 = Ok | 1 = Duplicate id | -1 = Other error
	 * @throws SQLException
	 */
	public int insertStudent(Student student) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int errorCode = -1;

		try {
			connection = openConnection();

			String sqlText = "INSERT INTO Student (id, firstname, lastname, streetaddress, postcode,\r\n"
					+ "postoffice) VALUES (?, ?, ?, ?, ?, ?)";

			preparedStatement = connection.prepareStatement(sqlText);
			preparedStatement.setInt(1, student.getId());
			preparedStatement.setString(2, student.getFirstname());
			preparedStatement.setString(3, student.getLastname());
			preparedStatement.setString(4, student.getStreetaddress());
			preparedStatement.setInt(5, student.getPostcode());
			preparedStatement.setString(6, student.getPostoffice());

			preparedStatement.executeUpdate();
			errorCode = 0;

		} catch (SQLException sqle) {
			if (sqle.getErrorCode() == ConnectionParameters.PK_VIOLATION_ERROR) {
				errorCode = 1;
			} else {
				System.out.println("\n[ERROR] StudentDAO: insertStudent() failed. " + sqle.getMessage() + "\n");
				errorCode = -1;
			}

		} finally {
			DbUtils.closeQuietly(preparedStatement, connection);
		}
		System.out.println(student);
		return errorCode;
	}

	public int deleteStudent(int studentId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int errorCode = -1;

		try {
			connection = openConnection();

			String sqlText = "DELETE FROM Student WHERE id = ?";
			preparedStatement = connection.prepareStatement(sqlText);
			preparedStatement.setInt(1, studentId);

			preparedStatement.executeUpdate();
			errorCode = 0;

		} catch (SQLException sqle) {
			if (sqle.getErrorCode() == ConnectionParameters.PK_VIOLATION_ERROR) {
				errorCode = 1;
			} else {
				System.out.println("\n[ERROR] StudentDAO: deleteStudent() failed. " + sqle.getMessage() + "\n");
				errorCode = -1;
			}

		} finally {
			DbUtils.closeQuietly(preparedStatement, connection);
		}

		return errorCode;
	}

	public int updateStudent(Student student) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		int errorCode = -1;

		try {
			connection = openConnection();

			String sqlText = "UPDATE Student SET id = ?, firstname = ?, lastname = ?, streetaddress = ?, postcode = ?, postoffice = ? WHERE id = ?";

			preparedStatement = connection.prepareStatement(sqlText);
			preparedStatement.setInt(1, student.getId());
			preparedStatement.setString(2, student.getFirstname());
			preparedStatement.setString(3, student.getLastname());
			preparedStatement.setString(4, student.getStreetaddress());
			preparedStatement.setInt(5, student.getPostcode());
			preparedStatement.setString(6, student.getPostoffice());
			preparedStatement.setInt(7, student.getId());
			preparedStatement.executeUpdate();
			errorCode = 0;

		} catch (SQLException sqle) {
			if (sqle.getErrorCode() == ConnectionParameters.PK_VIOLATION_ERROR) {
				errorCode = 1;
			} else {
				System.out.println("\n[ERROR] StudentDAO: updateStudent() failed. " + sqle.getMessage() + "\n");
				errorCode = -1;
			}

		} finally {
			DbUtils.closeQuietly(preparedStatement, connection);
		}
		
		if (errorCode == 0) {
			System.out.println("Student data updated");
		} else {
			System.out.println("Unknown student id" + " (" + student.getId() + ")");
		};
		return errorCode;
	}
}