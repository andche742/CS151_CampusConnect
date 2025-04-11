package s25.cs151.application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import s25.cs151.application.models.Appointment;
import s25.cs151.application.models.ConnectDB;
import s25.cs151.application.models.Courses;
import s25.cs151.application.models.TimeSlots;

public class AppointmentDAO {
    public static void initializeAppointmentsTable() {
        String sql = """
        CREATE TABLE IF NOT EXISTS appointments (
            appointment_id INT PRIMARY KEY,
            student_name TEXT NOT NULL,
            date TEXT NOT NULL,
            course_id INT NOT NULL,
            time_slot_id INT NOT NULL,
            reason TEXT,
            comment TEXT,
            FOREIGN KEY (course_id) REFERENCES courses(course_id),
            FOREIGN KEY (time_slot_id) REFERENCES time_slots(time_slot_id)

        );
        """;
        try (
                Connection conn = ConnectDB.getConnection();  // Get a new connection
                Statement stmt = conn.createStatement()         // Create a statement
        ) {
            stmt.execute(sql);  // Execute the SQL statement to create the table
            System.out.println("Appointments table initialized.");
        } catch (SQLException e) {
            System.err.println("Failed to initialize Appointments table: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Inserts a new Appointment record into the appointments table.
     *
     * @param course The Appointment object to insert.
     * @return true if the appointment was inserted successfully.
     * @throws SQLException if a database error occurs.
     */
    public static boolean saveAppointments(Appointment appointment) throws SQLException {
        // Add to database
        try (Connection conn = ConnectDB.getConnection()) {
            String query = "INSERT INTO appointments (student_name, date, course_id, time_slot_id, reason, comment) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, appointment.getStudentFullName());
            stmt.setString(2, appointment.getScheduleDate().toString());
            stmt.setInt(3, CoursesDAO.getCourseID(conn, appointment.getCourses().getCourseCode()));
            stmt.setInt(4, TimeSlotsDAO.getTimeSlotID(conn, appointment.getTimeSlots().getFromTime(), appointment.getTimeSlots().getToTime()));
            stmt.setString(5, appointment.getReason());
            stmt.setString(6, appointment.getComments());

            stmt.executeUpdate();
            System.out.println("Appointment inserted successfully.");
            return true;

        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Retrieves all Appointment records from the Appointment table.
     * @return A List of Appointment objects.
     */
    public static List<Appointment> getAppointmentList() {

        List<Appointment> appointmentList = new ArrayList<>();
        // SQL query selects all appointments, ordered chronologically.
        String sql = "SELECT * FROM appointments ORDER BY date";

        try (
                Connection conn = ConnectDB.getConnection();       // Obtain a new connection
                PreparedStatement stmt = conn.prepareStatement(sql); // Prepare the SQL statement
                ResultSet rs = stmt.executeQuery()                   // Execute query and get results
        ) {
            while (rs.next()) {
                // Retrieve values from the result set for each column
                String name = rs.getString("student_name");
                String dateStr = rs.getString("date");
                int timeSlotID = rs.getInt("time_slot_id");
                int courseID = rs.getInt("course_id");
                String reason = rs.getString("reason");
                String comment = rs.getString("comment");

                LocalDate date = LocalDate.parse(dateStr);
                TimeSlots timeSlot = TimeSlotsDAO.getTimeSlotByID(conn, timeSlotID);
                Courses course = CoursesDAO.getCourseByID(conn, courseID);
                // Create a new Courses object and add it to the list
                Appointment appt = new Appointment(name, date, timeSlot, course, reason, comment);
                appointmentList.add(appt);
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Handle any SQL errors that occur
        }
        return appointmentList;
    }

    /**
     * Deletes a Appointment record from the appointments table.
     *
     * @param course The Appointment object to delete.
     * @return true if the deletion was successful.
     * @throws SQLException if a database error occurs.
     */
    public static boolean deleteAppointment(Appointment appointment) throws SQLException {
        // TODO: Implement delete operation for courses
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
