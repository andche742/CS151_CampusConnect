package s25.cs151.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import s25.cs151.application.models.Appointment;
//import s25.cs151.application.services.EditFormContext;
import s25.cs151.application.services.PageNavigator;

public class EditOfficeHoursController extends BaseScheduleController {

    @FXML private TextField searchField;
    @FXML private Button editButton;

    private Appointment selectedAppointment;

    @FXML
    public void initialize() {
        super.initialize(); // binds table, loads data, applies sorting

        // Enable edit button only when a row is selected
        scheduleTable.getSelectionModel().selectedItemProperty()
                .addListener((observableValue, oldVal, newVal) -> {
            selectedAppointment = newVal;
            editButton.setDisable(newVal == null);
        });

        // Optional: live search
        searchField.textProperty().addListener((observableValue, oldText, newText) ->
                super.filterStudentbyName(newText));
    }

    @FXML
    public void handleSearch() {
        super.filterStudentbyName(searchField.getText());
    }

    @FXML
    public void onEdit(ActionEvent actionEvent) {
        if (selectedAppointment == null) return;

        // Store selected appointment in shared context
        //EditFormContext.setSelectedAppointment(selectedAppointment);

        // Navigate to the edit form
        PageNavigator.navigateTo("EditAppointmentForm.fxml");
    }

    @FXML
    @Override
    protected void handleBack() {
        PageNavigator.navigateTo("Home");
    }
}
