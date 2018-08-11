package Students;

import Model.Department;
import Model.Student;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Ahmed Magdy
 */
public class DB {

    Connection con;

    public Connection stablishDBConnection() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/students_db", "root", "12345");
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return con;
    }

    public void addNewStudent(Student student) throws IOException {
        String insertQuery = "INSERT INTO STUDENTS (NAME, SERIAL_NUMBER, DATE_OF_BIRTH, DEPARTMENT, CV)"
                + "VALUES(?, ?, ?, ?, ?)";
        con = stablishDBConnection();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);

            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getSerialNumber());
            preparedStatement.setDate(3, convertUtilToSql(student.getDateOfBirth()));

            preparedStatement.setString(4, student.getDepartment().name());
            preparedStatement.setBytes(5, student.getCv());

            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void uploadCV(UploadedFile CV) {

    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> studentsLs = new LinkedList<Student>();
        String getAllQuery = "SELECT * FROM STUDENTS";
        con = stablishDBConnection();
        Statement statement = con.createStatement();
        ResultSet result = statement.executeQuery(getAllQuery);
        while(result.next()) {
            Student std = new Student();
            std.setName(result.getString("NAME"));
            std.setSerialNumber(result.getString("SERIAL_NUMBER"));
            std.setDateOfBirth(result.getDate("DATE_OF_BIRTH"));
            std.setDepartment(Department.valueOf(result.getString("DEPARTMENT")));
            std.setCv(result.getBytes("CV"));
            
            studentsLs.add(std);
        }
        
        return studentsLs;
    }
    
    private java.sql.Date convertUtilToSql(java.util.Date uDate) {
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        return sDate;
    }
}