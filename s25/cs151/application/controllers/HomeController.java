package controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;



public class HomeController extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HomeController.class.getResource("/pages/Home.fxml"));
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

    public void onHelloButtonClick(ActionEvent actionEvent) {
       // System.out.println("Hello button clicked!");

    }

    @FXML
    private void goToDefineSemesterOfficeHours(ActionEvent event) {
        try {
            // Load Define Semester’s Office Hours Page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pages/DefineSemesterOfficeHours.fxml"));
            Parent root = loader.load();

            // Get current stage and switch scene
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Define Semester's Office Hours");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error loading DefineSemester.fxml");
        }
    }

}

