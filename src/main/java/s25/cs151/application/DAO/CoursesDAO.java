package s25.cs151.application.DAO;

import s25.cs151.application.models.Courses;

import java.sql.SQLException;
import java.util.List;

public class CoursesDAO {

    /**
     * Initializes the courses table in the database.
     * TODO: Implement the table initialization logic for courses.
     */
    public static void initializeCoursesTable() {
        // TODO: Implement table initialization for courses
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Inserts a new Course record into the courses table.
     *
     * @param course The Course object to insert.
     * @return true if the course was inserted successfully.
     * @throws SQLException if a database error occurs.
     */
    public static boolean createCourse(Courses course) throws SQLException {
        // TODO: Implement create operation for courses
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Retrieves all Course records from the courses table.
     *
     * @return A List of Course objects.
     */
    public static List<Courses> readCourses() {
        // TODO: Implement read operation for courses
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Updates an existing Course record in the courses table.
     *
     * @param course The Course object with updated data.
     * @return true if the update was successful.
     * @throws SQLException if a database error occurs.
     */
    public static boolean updateCourse(Courses course) throws SQLException {
        // TODO: Implement update operation for courses
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Deletes a Course record from the courses table.
     *
     * @param course The Course object to delete.
     * @return true if the deletion was successful.
     * @throws SQLException if a database error occurs.
     */
    public static boolean deleteCourse(Courses course) throws SQLException {
        // TODO: Implement delete operation for courses
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
