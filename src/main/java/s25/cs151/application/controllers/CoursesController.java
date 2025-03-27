package s25.cs151.application.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import s25.cs151.application.DAO.CoursesDAO;
import s25.cs151.application.DAO.OfficeHoursDAO;
import s25.cs151.application.models.Courses;
import s25.cs151.application.models.ConnectDB;
import s25.cs151.application.models.TimeSlots;
import s25.cs151.application.services.PageNavigator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Controller for the Courses page.
 * Manages the interaction between the Courses view and the Courses model.
 * Handles adding, displaying, and deleting courses.
 */
public class CoursesController {

    @FXML
    private TextField courseCodeField;
    
    @FXML
    private TextField courseNameField;
    
    @FXML
    private TextField sectionNumberField;
    
    @FXML
    private Button addButton;
    
    @FXML
    private Button clearButton;
    
    @FXML
    private Button deleteButton;
    
    @FXML
    private Button returnButton;
    
    @FXML
    private TableView<Courses> coursesTableView;
    
    @FXML
    private TableColumn<Courses, String> courseCodeColumn;
    
    @FXML
    private TableColumn<Courses, String> courseNameColumn;
    
    @FXML
    private TableColumn<Courses, String> sectionNumberColumn;

    @FXML
    private Label errorMessageLabel;


    
    /**
     * Initialize the controller.
     * Sets up table columns and loads data from the database.
     */
    @FXML
    public void initialize() {
        // Set up the table columns
        courseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        sectionNumberColumn.setCellValueFactory(new PropertyValueFactory<>("sectionNumber"));
        
        List<Courses> coursesList = CoursesDAO.getCourseList();
        ObservableList<Courses> data = FXCollections.observableArrayList(coursesList);
        
        // Sort the table by course code in descending order
        courseCodeColumn.setSortType(TableColumn.SortType.DESCENDING);
        coursesTableView.getSortOrder().add(courseCodeColumn);
        coursesTableView.sort();
    }


    private Courses createCourseObject() {
        String courseCode = courseCodeField.getText().trim();
        String courseName = courseNameField.getText().trim();
        String sectionNumber = sectionNumberField.getText().trim();
        return new Courses(courseCode, courseName, sectionNumber);
    }
    /**
     * Handle adding a new course.
     * Validates input and adds the course to the database.
     */
    @FXML
    private void handleAddCourse() {

        Courses course = createCourseObject();

        // Validate inputs
        if (course.getCourseCode().isEmpty() ||
            course.getCourseName().isEmpty() ||
            course.getSectionNumber().isEmpty())
        {
            showAlert(Alert.AlertType.WARNING,
                    "Missing Information",
                     "Please fill in all fields",
                    "Course code, name, and section number are required.");
            return;
        }
        boolean saved;
        try {
            saved = CoursesDAO.saveCourses(course); // this method returns a boolean
        } catch (SQLException e) {
            showAlert(Alert.AlertType.INFORMATION,
                    "DUPLICATE ENTRY" ,
                    "This course already exists",
                    "Adding course failed.");
            e.printStackTrace();
            return; // Prevents proceeding if an exception was caught
        }

        if (saved) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Success");
            confirm.setHeaderText("Course successfully defined.");

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                PageNavigator.navigateTo("Courses");
            }
        } else {
            errorMessageLabel.setText("Error courses. Please try again.");
        }
    }
    

    
    /**
     * Handle clearing the form.
     * Clears all text fields.
     */
    @FXML
    private void handleClearForm() {
        courseCodeField.clear();
        courseNameField.clear();
        sectionNumberField.clear();
        courseCodeField.requestFocus();
    }
    

    /**
     * Handle returning to the home page.
     * Navigates back to the Home.fxml page.
     */
    @FXML
    private void handleReturnHome() {
        try {
            PageNavigator.navigateTo("Home");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", 
                     "Failed to return to home page", "Exit application.");
        }
    }

    /**
     * Show an alert dialog.
     *
     * @param alertType The type of alert to show
     * @param title The title of the alert
     * @param header The header text of the alert
     * @param content The content text of the alert
     */
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
