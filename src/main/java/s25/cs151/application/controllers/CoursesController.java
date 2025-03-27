package s25.cs151.application.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import s25.cs151.application.models.Courses;
import s25.cs151.application.models.ConnectDB;
import s25.cs151.application.services.PageNavigator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    
    private ObservableList<Courses> coursesList = FXCollections.observableArrayList();
    
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
        
        // Load courses from database
        loadCoursesFromDatabase();
        
        // Sort the table by course code in descending order
        courseCodeColumn.setSortType(TableColumn.SortType.DESCENDING);
        coursesTableView.getSortOrder().add(courseCodeColumn);
        coursesTableView.sort();
    }
    
    /**
     * Load courses from the database.
     * Clears the current list and loads all courses from the database.
     */
    private void loadCoursesFromDatabase() {
        coursesList.clear();
        
        try (Connection conn = ConnectDB.getConnection()) {
            String query = "SELECT * FROM courses ORDER BY course_code DESC";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Courses course = new Courses(
                    rs.getString("course_code"),
                    rs.getString("course_name"),
                    rs.getString("section_number")
                );
                coursesList.add(course);
            }
            
            coursesTableView.setItems(coursesList);
            
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", 
                     "Failed to load courses from database", e.getMessage());
        }
    }
    
    /**
     * Handle adding a new course.
     * Validates input and adds the course to the database.
     */
    @FXML
    private void handleAddCourse() {
        String courseCode = courseCodeField.getText().trim();
        String courseName = courseNameField.getText().trim();
        String sectionNumber = sectionNumberField.getText().trim();
        
        // Validate inputs
        if (courseCode.isEmpty() || courseName.isEmpty() || sectionNumber.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Missing Information", 
                     "Please fill in all fields", "Course code, name, and section number are required.");
            return;
        }
        
        // Check for duplicate entries
        if (isDuplicate(courseCode, courseName, sectionNumber)) {
            showAlert(Alert.AlertType.WARNING, "Duplicate Entry", 
                     "This course already exists", 
                     "A course with the same code, name, and section number already exists.");
            return;
        }
        
        // Add to database
        try (Connection conn = ConnectDB.getConnection()) {
            String query = "INSERT INTO courses (course_code, course_name, section_number) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, courseCode);
            stmt.setString(2, courseName);
            stmt.setString(3, sectionNumber);
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                // Add to table
                Courses newCourse = new Courses(courseCode, courseName, sectionNumber);
                coursesList.add(newCourse);
                
                // Re-sort the table
                coursesTableView.sort();
                
                // Clear the form
                handleClearForm();
                
                showAlert(Alert.AlertType.INFORMATION, "Success", 
                         "Course added successfully", "The course has been added to the database.");
            }
            
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", 
                     "Failed to add course to database", e.getMessage());
        }
    }
    
    /**
     * Check if a course with the same code, name, and section already exists.
     * 
     * @param courseCode The course code to check
     * @param courseName The course name to check
     * @param sectionNumber The section number to check
     * @return true if a duplicate exists, false otherwise
     */
    private boolean isDuplicate(String courseCode, String courseName, String sectionNumber) {
        try (Connection conn = ConnectDB.getConnection()) {
            String query = "SELECT COUNT(*) FROM courses WHERE course_code = ? AND course_name = ? AND section_number = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, courseCode);
            stmt.setString(2, courseName);
            stmt.setString(3, sectionNumber);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
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
     * Handle deleting a course.
     * Deletes the selected course from the database.
     */
    @FXML
    private void handleDeleteCourse() {
        Courses selectedCourse = coursesTableView.getSelectionModel().getSelectedItem();
        
        if (selectedCourse == null) {
            showAlert(Alert.AlertType.WARNING, "No Selection", 
                     "No course selected", "Please select a course to delete.");
            return;
        }
        
        // Confirm deletion
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Delete Course");
        confirmAlert.setContentText("Are you sure you want to delete the selected course?");
        
        Optional<ButtonType> result = confirmAlert.showAndWait();
        
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Delete from database
            try (Connection conn = ConnectDB.getConnection()) {
                String query = "DELETE FROM courses WHERE course_code = ? AND course_name = ? AND section_number = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, selectedCourse.getCourseCode());
                stmt.setString(2, selectedCourse.getCourseName());
                stmt.setString(3, selectedCourse.getSectionNumber());
                
                int rowsAffected = stmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    // Remove from table
                    coursesList.remove(selectedCourse);
                    
                    showAlert(Alert.AlertType.INFORMATION, "Success", 
                             "Course deleted successfully", "The course has been removed from the database.");
                }
                
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Database Error", 
                         "Failed to delete course from database", e.getMessage());
            }
        }
    }
    
    /**
     * Handle returning to the home page.
     * Navigates back to the Home.fxml page.
     */
    @FXML
    private void handleReturnHome() {
        try {
            PageNavigator.navigateTo("Home.fxml");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Navigation Error", 
                     "Failed to return to home page", e.getMessage());
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
