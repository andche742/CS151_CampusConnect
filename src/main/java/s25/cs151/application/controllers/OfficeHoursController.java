package s25.cs151.application.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import s25.cs151.application.models.OfficeHours;
import s25.cs151.application.services.OfficeHoursService;
import s25.cs151.application.services.PageNavigator;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the Define Semester's Office Hours page
 * 
 * @author Team 2
 */
public class OfficeHoursController {
    
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

    private OfficeHoursService officeHoursService = new OfficeHoursService();
    
    /**
     * Initialize method called after FXML fields are populated
     */
    @FXML
    public void initialize() {
        // Setup the semester combo box with required options
        semesterComboBox.getItems().addAll("Spring", "Summer", "Fall", "Winter");
        
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



            // saveOfficeHours to be implemented later in officehours service
            /* boolean saved = officeHoursService.saveOfficeHours(officeHours);
            
            if (saved) {
            showAlert("Success", "Office hours defined successfully");
            } else {
            // This case is only reached if saving is attempted but fails
            errorMessageLabel.setText("Error saving office hours. Please try again.");
            return;
            } */
            
            // Return to home page
            showAlert("Success", "Office hours defined successfully");
            PageNavigator.navigateTo("Home");
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
            if (year < 2000 || year > 3000) {
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
     * 
     * @return the created OfficeHours object
     */
    private OfficeHours createOfficeHoursObject() {
        String semester = semesterComboBox.getValue();
        int year = Integer.parseInt(yearTextField.getText().trim());
        
        List<String> days = new ArrayList<>();
        if (mondayCheckBox.isSelected()) days.add("M");
        if (tuesdayCheckBox.isSelected()) days.add("T");
        if (wednesdayCheckBox.isSelected()) days.add("W");
        if (thursdayCheckBox.isSelected()) days.add("Th");
        if (fridayCheckBox.isSelected()) days.add("F");
        
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
