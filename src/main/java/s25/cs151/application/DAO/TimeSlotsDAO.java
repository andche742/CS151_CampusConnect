package s25.cs151.application.DAO;

import s25.cs151.application.models.ConnectDB;
import s25.cs151.application.models.Courses;
import s25.cs151.application.models.TimeSlots;
import java.sql.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class TimeSlotsDAO {

    /**
     * Initializes the timeslots table.
     */
    public static void initializeTimeSlotsTable() {
        String sql = """
        CREATE TABLE IF NOT EXISTS time_slots (
            time_slot_id INT PRIMARY KEY,
            fromTime TEXT NOT NULL,
            toTime TEXT NOT NULL,
            fromHour INT NOT NULL,
            fromMinute INT NOT NULL,
            toHour INT NOT NULL,
            toMinute INT NOT NULL
        );
        """;

        try (
                Connection conn = ConnectDB.getConnection();         // fresh connection
                Statement stmt = conn.createStatement()
        ) {
            stmt.execute(sql);
            System.out.println("Time Slots table initialized.");
        } catch (SQLException e) {
            System.err.println("Failed to initialize Time Slots table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Creates a new Timeslot record.
     *
     * @param timeSlots The Timeslot object to insert.
     * @return true if the insert was successful; otherwise, false.
     * @throws SQLException if a database error occurs.
     */
    public static boolean saveTimeSlots(TimeSlots timeSlots) throws SQLException {
        String sql = "INSERT INTO time_slots (fromTime, toTime, fromHour, fromMinute, toHour, toMinute) VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection conn = ConnectDB.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, timeSlots.getFromTime());
            pstmt.setString(2, timeSlots.getToTime());
            pstmt.setInt(3, timeSlots.getFromHour());
            pstmt.setInt(4, timeSlots.getFromMinute());
            pstmt.setInt(5, timeSlots.getToHour());
            pstmt.setInt(6, timeSlots.getToMinute());

            pstmt.executeUpdate();
            System.out.println("Time slot inserted.");
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }

    /**
     * Retrieves all Timeslot records.
     *
     * @return A List of Timeslot objects.
     */
    public static List<TimeSlots> getTimeSlots() {
        List<TimeSlots> list = new ArrayList<>();
        String sql = """
                    SELECT * FROM time_slots
                     ORDER BY
                    fromTime ASC
                """;
        try (
                Connection conn = ConnectDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet resultSet = stmt.executeQuery()
        ) {
            while (resultSet.next()) {
                int fromHour = resultSet.getInt("fromHour");
                int fromMinute = resultSet.getInt("fromMinute");
                int toHour = resultSet.getInt("toHour");
                int toMinute = resultSet.getInt("toMinute");
                list.add(new TimeSlots(fromHour, fromMinute, toHour, toMinute));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Updates an existing Timeslot record.
     *
     * @param timeslot The Timeslot object with updated data.
     * @return true if the update was successful; otherwise, false.
     * @throws SQLException if a database error occurs.
     */
    public static boolean updateTimeslot(TimeSlots timeslot) throws SQLException {
        // TODO: Implement update operation for timeslots
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Deletes a Timeslot record.
     *
     * @param timeslot The Timeslot object to delete.
     * @return true if the deletion was successful; otherwise, false.
     * @throws SQLException if a database error occurs.
     */
    public static boolean deleteTimeslot(TimeSlots timeslot) throws SQLException {
        // TODO: Implement delete operation for timeslots
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Returns course_id from courses table
     * 
     * @param course_code Course code to search
     * @return course_id as int
     * @throws SQLException if error occurs.
     */
    public static int getTimeSlotID(Connection conn, String fromTime, String toTime) throws SQLException {
        String sql = "SELECT course_id FROM courses WHERE fromTime = ? AND toTime = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, fromTime);
            stmt.setString(2, toTime);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("time_slot_id");
                } else {
                    throw new SQLException("time_slot_id not found");
                }
            }
        }
    }

    public static TimeSlots getTimeSlotByID(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM time_slots WHERE time_slot_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new TimeSlots(
                        LocalTime.parse(rs.getString("fromTime")),
                        LocalTime.parse(rs.getString("toTime")));
                }
                else {
                    System.out.println("time_slot_id: " + id + " not found");
                    return null;
                }
            }
        }
    }

}
