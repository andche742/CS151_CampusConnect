package s25.cs151.application.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import s25.cs151.application.DAO.OfficeHoursDAO;
import s25.cs151.application.models.OfficeHours;

import s25.cs151.application.services.PageNavigator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Controller for the Define Semester's Office Hours page
 * 
 * @author Team 2
 */
public class DefineOfficeHoursController {


    @FXML
    private ComboBox<String> semesterComboBox;
    
    @FXML
    private TextField yearTextField;
    
    @FXML
    private CheckBox mondayCheckBox, tuesdayCheckBox, wednesdayCheckBox, 
                     thursdayCheckBox, fridayCheckBox;
    
    @FXML
    private Button submitButton, cancelButton;

    @FXML
    private Label errorMessageLabel;

    //private OfficeHoursService officeHoursService = new OfficeHoursService();
    
    /**
     * Initialize method called after FXML fields are populated
     */
    @FXML
    public void initialize() {
        // Set up the semester combo box with required options
        semesterComboBox.getItems().addAll("Spring", "Summer", "Fall", "Winter");
        semesterComboBox.setValue("Spring");
        // Clear any error messages on load
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
            // Create the office hours object from form data
            OfficeHours officeHours = createOfficeHoursObject();

            boolean saved;

            try {
                saved = OfficeHoursDAO.saveOfficeHours(officeHours); // Assume this method returns a boolean
            } catch (SQLException e) {
                if (e.getMessage().contains("UNIQUE")) {
                    showAlert("Duplicate Entry", "This office hour entry already exists.");
                } else {
                    showAlert("Database Error", "Something went wrong:\n" + e.getMessage());
                    e.printStackTrace();
                }
                return; // Prevents proceeding if an exception was caught
            }

            if (saved) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Success");
                confirm.setHeaderText("Office hours successfully defined.");
                confirm.setContentText("Do you want to view all saved office hours now?");

                Optional<ButtonType> result = confirm.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // User wants to view the table
                  PageNavigator.navigateTo("OfficeHours");
                } else {
                    // User clicked Cancel, navigate to Home
                    PageNavigator.navigateTo("Home");
                }
            } else {
                errorMessageLabel.setText("Error saving office hours. Please try again.");
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
        // Check if semester is selected
        if (semesterComboBox.getValue() == null) {
            errorMessageLabel.setText("Please select a semester");
            return false;
        }
        
        // Validate year input
        String yearText = yearTextField.getText().trim();
        if (yearText.isEmpty()) {
            errorMessageLabel.setText("Please enter a year");
            return false;
        }
        
        try {
            int year = Integer.parseInt(yearText);
            if (year < 1000 || year > 9999) {
                errorMessageLabel.setText("Please enter a valid year");
                return false;
            }
        } catch (NumberFormatException e) {
            errorMessageLabel.setText("Please enter a valid 4-digit year");
            return false;
        }
        
        // Check if at least one day is selected
        if (!mondayCheckBox.isSelected() && !tuesdayCheckBox.isSelected() && 
            !wednesdayCheckBox.isSelected() && !thursdayCheckBox.isSelected() && 
            !fridayCheckBox.isSelected()) {
            
            errorMessageLabel.setText("Please select at least one day");
            return false;
        }
        
        // All validations passed
        return true;
    }
    
    /**
     * Creates an OfficeHours object from the form data
     * @return the created OfficeHours object
     */
    private OfficeHours createOfficeHoursObject() {
        String semester = semesterComboBox.getValue();
        int year = Integer.parseInt(yearTextField.getText().trim());

        List<String> days = new ArrayList<>();
        if (mondayCheckBox.isSelected()) days.add("Mon");
        if (tuesdayCheckBox.isSelected()) days.add("Tues");
        if (wednesdayCheckBox.isSelected()) days.add("Wed");
        if (thursdayCheckBox.isSelected()) days.add("Thurs");
        if (fridayCheckBox.isSelected()) days.add("Fri");
        
        return new OfficeHours(semester, year, days);
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
