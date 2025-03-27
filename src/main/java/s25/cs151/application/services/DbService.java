package s25.cs151.application.services;

import s25.cs151.application.models.ConnectDB;
import s25.cs151.application.models.OfficeHours;
import s25.cs151.application.models.TimeSlots;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DbService {

    // OFFICE HOUR METHODS
    public static void initializeOfficeHoursTable() {
        String sql = """
        CREATE TABLE IF NOT EXISTS office_hours (
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

    // insert office hours
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

    // TIME SLOT METHODS
    public static void initializeTimeSlotsTable() {
        String sql = """
        CREATE TABLE IF NOT EXISTS time_slots (
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

    // insert office hours
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
}
