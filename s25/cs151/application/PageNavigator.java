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
            FXMLLoader loader = new FXMLLoader(PageNavigator.class.getResource("/pages/" + page + ".fxml"));
            Parent root = loader.load();

            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: Could not load page " + page);
        }
    }
}