package s25.cs151.application.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import s25.cs151.application.DAO.TimeSlotsDAO;
import s25.cs151.application.models.TimeSlots;

import s25.cs151.application.services.PageNavigator;

import java.sql.SQLException;
import java.util.Optional;

/**
 * Controller for the Create Time Slot page
 * 
 * @author Team 2
 */
public class DefineTimeSlotsController {


    @FXML
    private ComboBox<String> fromHourComboBox;
    
    @FXML
    private ComboBox<String> fromMinuteComboBox;
    
    @FXML
    private ComboBox<String> toHourComboBox;

    @FXML
    private ComboBox<String> toMinuteComboBox;
    
    @FXML
    private Button submitButton, cancelButton;

    @FXML
    private Label errorMessageLabel;
    
    /**
     * Initialize method called after FXML fields are populated
     */
    @FXML
    public void initialize() {
        // Setup the time inputs
        for (int i = 0; i <= 24; i++) {
            fromHourComboBox.getItems().add(String.format("%02d", i));
            toHourComboBox.getItems().add(String.format("%02d", i));
        }
        for (int i = 0; i < 12; i++) {
            fromMinuteComboBox.getItems().add(String.format("%02d", i*5));
            toMinuteComboBox.getItems().add(String.format("%02d", i*5));
        }
        
        errorMessageLabel.setText("");
    }
    
    /**
     * Handler for the submit button
     */
    @FXML
    private void handleSubmit() {
        // Clear any previous error messages
        errorMessageLabel.setText("");
        
        // Validate inputs before proceeding
        if (validateInputs()) {
            // Create the time slots object from form data
            TimeSlots timeSlots = createTimeSlotsObject();

            boolean saved;

            try {
                saved = TimeSlotsDAO.saveTimeSlots(timeSlots); //this method returns a boolean
            } catch (SQLException e) {
                showAlert("Database Error", "Something went wrong:\n" + e.getMessage());
                e.printStackTrace();
                return; // Prevents proceeding if an exception was caught
            }

            if (saved) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Success");
                confirm.setHeaderText("Time slot successfully defined.");

                Optional<ButtonType> result = confirm.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                  PageNavigator.navigateTo("TimeSlots");
                } 
            } else {
                errorMessageLabel.setText("Error saving time slot. Please try again.");
            }
        }
    }

    /**
     * Handler for the cancel button
     */
    @FXML
    private void handleCancel() {
        // Navigate back to home page without saving
        PageNavigator.navigateTo("Home");
    }
    
    /**
     * Validates user inputs and displays appropriate error messages
     * 
     * @return true if all inputs are valid, false otherwise
     */
    private boolean validateInputs() {
        // Check if all fields filled
        if (fromHourComboBox.getValue() == null || fromMinuteComboBox.getValue() == null || toHourComboBox.getValue() == null || toMinuteComboBox.getValue() == null) {
            errorMessageLabel.setText("Please fill all fields");
            return false;
        }
        
        // Ensure valid start/end times
        if (Integer.parseInt(fromHourComboBox.getValue()) > Integer.parseInt(toHourComboBox.getValue()) || (Integer.parseInt(fromHourComboBox.getValue()) == Integer.parseInt(toHourComboBox.getValue()) && Integer.parseInt(fromMinuteComboBox.getValue()) >= Integer.parseInt(toMinuteComboBox.getValue()))) {
            errorMessageLabel.setText("End time must be after start time");
            return false;
        }
        
        // All validations passed
        return true;
    }
    
    /**
     * Creates a TimeSlots object from the form data
     * @return the created TimeSlots object
     */
    private TimeSlots createTimeSlotsObject() {
        return new TimeSlots(Integer.parseInt(fromHourComboBox.getValue()), Integer.parseInt(fromMinuteComboBox.getValue()), Integer.parseInt(toHourComboBox.getValue()), Integer.parseInt(toMinuteComboBox.getValue()));
    }


    
    /**
     * Shows an alert dialog with the given title and message
     * 
     * @param title the alert title
     * @param message the alert message
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
