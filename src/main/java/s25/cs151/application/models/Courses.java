package s25.cs151.application.models;

public class Courses {


    // course code
    // course name
    // section number

    private String courseName;
    private String courseCode;
    private String section;

    public Courses(String courseName, String courseCode, String section) {
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.section = section;
    }

    public String getCourseName() {
        return courseName;
    }
    public String getCourseCode() {
        return courseCode;
    }
    public String getSection() {
        return section;
    }

}
