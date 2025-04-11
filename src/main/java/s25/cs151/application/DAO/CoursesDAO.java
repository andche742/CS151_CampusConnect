package s25.cs151.application.DAO;
import s25.cs151.application.models.ConnectDB;
import s25.cs151.application.models.Courses;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursesDAO {

    /**
     * Initializes the courses table in the database
     */
    public static void initializeCoursesTable() {
        String sql = """
        CREATE TABLE IF NOT EXISTS courses (
            course_id INT PRIMARY KEY,
            course_code TEXT NOT NULL,
            course_name TEXT NOT NULL,
            section_number TEXT NOT NULL,
            UNIQUE(course_code, section_number)
        );
    """;
        try (
                Connection conn = ConnectDB.getConnection();  // Get a new connection
                Statement stmt = conn.createStatement()         // Create a statement
        ) {
            stmt.execute(sql);  // Execute the SQL statement to create the table
            System.out.println("Courses table initialized.");
        } catch (SQLException e) {
            System.err.println("Failed to initialize Courses table: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Inserts a new Course record into the courses table.
     *
     * @param course The Course object to insert.
     * @return true if the course was inserted successfully.
     * @throws SQLException if a database error occurs.
     */
    public static boolean saveCourses(Courses course) throws SQLException {
        // Add to database
        try (Connection conn = ConnectDB.getConnection()) {
            String query = "INSERT INTO courses (course_code, course_name, section_number) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, course.getCourseCode());
            stmt.setString(2, course.getCourseName());
            stmt.setString(3, course.getSectionNumber());

            stmt.executeUpdate();
            System.out.println("Course inserted successfully.");
            return true;

        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Retrieves all Course records from the courses table.
     * @return A List of Course objects.
     */
    public static List<Courses> getCourseList() {

        List<Courses> courseList = new ArrayList<>();
        // SQL query selects all courses and sorts them by course_code in descending order.
        String sql = "SELECT * FROM courses ORDER BY course_code DESC";

        try (
                Connection conn = ConnectDB.getConnection();       // Obtain a new connection
                PreparedStatement stmt = conn.prepareStatement(sql); // Prepare the SQL statement
                ResultSet rs = stmt.executeQuery()                   // Execute query and get results
        ) {
            while (rs.next()) {
                // Retrieve values from the result set for each column
                String courseCode = rs.getString("course_code");
                String courseName = rs.getString("course_name");
                String sectionNumber = rs.getString("section_number");

                // Create a new Courses object and add it to the list
                Courses course = new Courses(courseCode, courseName, sectionNumber);
                courseList.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle any SQL errors that occur
        }
        return courseList;
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

    /**
     * Returns course_id from courses table
     * 
     * @param course_code Course code to search
     * @return course_id as int
     * @throws SQLException if error occurs.
     */
    public static int getCourseID(Connection conn, String courseCode) throws SQLException {
        String sql = "SELECT course_id FROM courses HWERE course_code = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, courseCode);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("course_id");
                } else {
                    throw new SQLException("course_id not found");
                }
            }
        }
    }

    public static Courses getCourseByID(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM course WHERE course_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Courses(
                        Integer.toString(rs.getInt("course_id")),
                        rs.getString("course_code"),
                        rs.getString("section_number"));
                }
                else {
                    System.out.println("course_id: " + id + " not found");
                    return null;
                }
            }
        }
    }
}
