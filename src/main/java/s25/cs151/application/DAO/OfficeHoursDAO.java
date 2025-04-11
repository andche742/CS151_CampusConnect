package s25.cs151.application.DAO;

import s25.cs151.application.models.ConnectDB;
import s25.cs151.application.models.OfficeHours;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class OfficeHoursDAO {

    // OFFICE HOUR METHODS

    /**
     * Initializes the office_hours table.
     */
    public static void initializeOfficeHoursTable() {
        String sql = """
        CREATE TABLE IF NOT EXISTS office_hours (
            office_hour_key INTEGER PRIMARY KEY,
            semester TEXT NOT NULL,
            year INTEGER NOT NULL,
            days TEXT NOT NULL,
            UNIQUE(semester, year)
        );
    """;
        try (
                Connection conn = ConnectDB.getConnection();         // fresh connection
                Statement stmt = conn.createStatement()
        ) {
            stmt.execute(sql);
            System.out.println("Office Hours table initialized.");
        } catch (SQLException e) {
            System.err.println("Failed to initialize Office Hours table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Creates a new OfficeHours record.
     *
     * @param officeHours The OfficeHours object to insert.
     * @return true if the insert was successful; otherwise, false.
     * @throws SQLException if a database error occurs.
     */
    public static boolean saveOfficeHours(OfficeHours officeHours) throws SQLException {
        String sql = "INSERT INTO office_hours (semester, year, days) VALUES (?, ?, ?)";

        try (
                Connection conn = ConnectDB.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, officeHours.getSemester());
            pstmt.setInt(2, officeHours.getYear());
            pstmt.setString(3, String.join(",", officeHours.getDays()));
            pstmt.executeUpdate();
            System.out.println("Office hour inserted.");
            return true;
        } catch (SQLException e) {
            throw e;
        }
    }


    /**
     * Retrieves all OfficeHours records
     * @return A List of OfficeHours objects.
     */
    public static List<OfficeHours> getOfficeHours() {
        List<OfficeHours> list = new ArrayList<>();
        String sql = """
                    SELECT * FROM office_hours
                     ORDER BY
                    year DESC,
                    CASE semester
                            WHEN 'Summer' THEN 4
                            WHEN 'Spring' THEN 3
                            WHEN 'Fall' THEN 2
                            WHEN 'Winter' THEN 1
                            ELSE 0
                END DESC
                """;
        try (
                Connection conn = ConnectDB.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet resultSet = stmt.executeQuery()
        ) {
            while (resultSet.next()) {
                String semester = resultSet.getString("semester");
                int year = resultSet.getInt("year");
                List<String> days = Arrays.asList(resultSet.getString("days").split(","));
                list.add(new OfficeHours(semester, year, days));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Updates an existing OfficeHours record.
     *
     * @param officeHours The OfficeHours object with updated data.
     * @return true if the update was successful; otherwise, false.
     * @throws SQLException if a database error occurs.
     */
    public static boolean updateOfficeHours(OfficeHours officeHours) throws SQLException {
        // TODO: Implement update operation
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Deletes an OfficeHours record.
     *
     * @param officeHours The OfficeHours object to delete.
     * @return true if the deletion was successful; otherwise, false.
     * @throws SQLException if a database error occurs.
     */
    public static boolean deleteOfficeHours(OfficeHours officeHours) throws SQLException {
        // TODO: Implement delete operation
        throw new UnsupportedOperationException("Not implemented yet");
    }


}
