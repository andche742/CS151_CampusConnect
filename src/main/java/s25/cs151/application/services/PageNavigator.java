package s25.cs151.application.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class PageNavigator {
    private static Stage stage;

    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    // use this to navigate between pages ex: navigateTo("Home")
    public static void navigateTo(String page) {
        try {
            FXMLLoader loader = new FXMLLoader(PageNavigator.class.getResource("/s25/cs151/application/pages/" + page + ".fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.setTitle(getPageTitle(page)); // Set title dynamically
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: Could not load page " + page);
        }
    }

    // Get dynamic titles for different pages
    private static String getPageTitle(String page) {
        return switch (page) {
            case "Home" -> "Campus Connect";
            case "DefineSemesterOfficeHours" -> "Define Semester's Office Hours";
            case "OfficeHours" -> "Office Hours Table";
            case "Courses" -> "Define Courses";
            default -> "Application";
        };
    }
}