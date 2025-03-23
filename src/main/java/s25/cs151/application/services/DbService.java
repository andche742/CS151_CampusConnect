package s25.cs151.application.services;

import s25.cs151.application.models.ConnectDB;
import s25.cs151.application.models.OfficeHours;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DbService {



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


    public static void dropOfficeHoursTable() {
        String sql = "DROP TABLE IF EXISTS office_hours";

        try (
                Connection conn = ConnectDB.getConnection();
                Statement stmt = conn.createStatement()
        ) {
            stmt.executeUpdate(sql);
            System.out.println("🗑️ Office Hours table dropped.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
