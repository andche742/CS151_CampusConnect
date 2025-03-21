package s25.cs151.application;
import javafx.application.Application;
import javafx.stage.Stage;
import s25.cs151.application.services.PageNavigator;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        PageNavigator.setStage(primaryStage);
        PageNavigator.navigateTo("Home");
    }

    public static void main(String[] args) {
        launch(args);
    }
}