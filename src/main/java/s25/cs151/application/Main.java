package s25.cs151.application;
import javafx.application.Application;
import javafx.stage.Stage;
import s25.cs151.application.services.PageNavigator;
import s25.cs151.application.services.DbService;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // init the table for db
        DbService.initializeOfficeHoursTable();

        // load and show the UI
        PageNavigator.setStage(primaryStage);
        PageNavigator.navigateTo("Home");
    }

    public static void main(String[] args) {
        launch(args);
    }
}