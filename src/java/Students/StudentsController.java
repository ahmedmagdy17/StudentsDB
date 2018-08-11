package Students;

import Model.Student;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.mail.MessagingException;
import javax.mail.Part;

/**
 *
 * @author Ahmed Magdy
 */
@ManagedBean(name = "studentController")
@ViewScoped
public class StudentsController implements Serializable {

    private Student student;
    private DB db;
    private List<Student> studentsList;
    private String day;
    private String month;
    private String year;
    private Part cv;

    @PostConstruct
    public void init() {
        clearStudent();
        try {
            getAllStudents();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void clearStudent() {
        student = new Student();
        day = "";
        month = "";
        year = "";
        db = new DB();
    }

    public void addNewStudent() throws SQLException, ParseException, IOException {

        String newDate = day + '-' + month + '-' + year;
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-mm-yyyy");
        java.util.Date date = sdf1.parse(newDate);
        student.setDateOfBirth(new java.sql.Date(date.getTime()));

        db.addNewStudent(student);
        clearStudent();
        getAllStudents();
    }

    public void uploadFile() throws IOException, MessagingException {
        InputStream input = cv.getInputStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int count;
        while ((count = input.read(buffer)) != -1) {
            output.write(buffer, 0, count);
        }
        student.setCv(output.toByteArray());
    }

    public void getAllStudents() throws SQLException {
        this.studentsList = db.getAllStudents();
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public List<Student> getStudentsList() {
        return studentsList;
    }

    public void setStudentsList(List<Student> studentsList) {
        this.studentsList = studentsList;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Part getCv() {
        return cv;
    }

    public void setCv(Part cv) {
        this.cv = cv;
    }

    public DB getDb() {
        return db;
    }

    public void setDb(DB db) {
        this.db = db;
    }
}
