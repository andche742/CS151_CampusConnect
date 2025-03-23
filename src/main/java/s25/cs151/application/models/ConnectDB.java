package s25.cs151.application.models;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class ConnectDB {

    private static final String DB_FILE = "CampusConnect.db"; // The actual DB file
    private static final String DB_URL = "jdbc:sqlite:" + DB_FILE;


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

}

