package s25.cs151.application.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Model class for Courses.
 * Represents a course with code, name, and section number.
 */
public class Courses {
    private final StringProperty courseCode;
    private final StringProperty courseName;
    private final StringProperty sectionNumber;

    /**
     * Constructor with all properties.
     *
     * @param courseCode The course code (e.g., CS151)
     * @param courseName The course name (e.g., Object-Oriented Design)
     * @param sectionNumber The section number (e.g., 01)
     */
    public Courses(String courseCode, String courseName, String sectionNumber) {
        this.courseCode = new SimpleStringProperty(courseCode);
        this.courseName = new SimpleStringProperty(courseName);
        this.sectionNumber = new SimpleStringProperty(sectionNumber);
    }

    /**
     * Get the course code.
     *
     * @return The course code
     */
    public String getCourseCode() {
        return courseCode.get();
    }

    /**
     * Set the course code.
     *
     * @param courseCode The course code to set
     */
    public void setCourseCode(String courseCode) {
        this.courseCode.set(courseCode);
    }

    /**
     * Get the course code property.
     *
     * @return The course code property
     */
    public StringProperty courseCodeProperty() {
        return courseCode;
    }

    /**
     * Get the course name.
     *
     * @return The course name
     */
    public String getCourseName() {
        return courseName.get();
    }

    /**
     * Set the course name.
     *
     * @param courseName The course name to set
     */
    public void setCourseName(String courseName) {
        this.courseName.set(courseName);
    }

    /**
     * Get the course name property.
     *
     * @return The course name property
     */
    public StringProperty courseNameProperty() {
        return courseName;
    }

    /**
     * Get the section number.
     *
     * @return The section number
     */
    public String getSectionNumber() {
        return sectionNumber.get();
    }

    /**
     * Set the section number.
     *
     * @param sectionNumber The section number to set
     */
    public void setSectionNumber(String sectionNumber) {
        this.sectionNumber.set(sectionNumber);
    }

    /**
     * Get the section number property.
     *
     * @return The section number property
     */
    public StringProperty sectionNumberProperty() {
        return sectionNumber;
    }

    /**
     * String representation of a course.
     *
     * @return String representation
     */
    @Override
    public String toString() {
        return  courseName.get();
    }
}