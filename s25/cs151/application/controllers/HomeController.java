import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Pane root = fxmlLoader.load();
        root.setStyle("-fx-background-color: blue;");

        // Add "Campus Connect" label at the top left
        Label titleLabel = new Label("Campus Connect");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");
        titleLabel.setLayoutX(10);
        titleLabel.setLayoutY(10);
        root.getChildren().add(titleLabel);

        Scene scene = new Scene(root, 320, 240);
        stage.setTitle("Campus Connect");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

