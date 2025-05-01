package s25.cs151.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import s25.cs151.application.DAO.AppointmentDAO;
import s25.cs151.application.models.Appointment;
import s25.cs151.application.services.PageNavigator;

import java.sql.SQLException;

public class SearchController extends BaseScheduleController {

    @FXML private TextField studentNameField;
    @FXML private Button deleteButton;

    private Appointment selectedAppointment;

    @FXML
    public void initialize() {
        super.initialize();

        // Select a row to enable the delete button
        scheduleTable.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldVal, newVal) -> {
            selectedAppointment = newVal;
            deleteButton.setDisable(newVal == null);
        });

        // Optional: live search
        studentNameField.textProperty().addListener((obs, oldText, newText) -> {
            filterStudentbyName(newText);
        });
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        filterStudentbyName(studentNameField.getText());
    }

    @FXML
    private void handleDelete(ActionEvent event) {
        if (selectedAppointment == null) return;
        try {
            boolean deleted = AppointmentDAO.deleteAppointment(selectedAppointment);
            if (deleted) {
                appointments.remove(selectedAppointment);
                selectedAppointment = null;
                deleteButton.setDisable(true);
                System.out.println("Appointment deleted.");
            } else {
                System.out.println("Failed to delete appointment.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error during deletion.");
        }
    }

    @FXML
    @Override
    protected void handleBack() {
        PageNavigator.navigateTo("Home");
    }
}
