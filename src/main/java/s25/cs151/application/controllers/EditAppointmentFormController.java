package s25.cs151.application.controllers;

import static javafx.collections.FXCollections.sort;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import s25.cs151.application.DAO.AppointmentDAO;
import s25.cs151.application.DAO.CoursesDAO;
import s25.cs151.application.DAO.TimeSlotsDAO;
import s25.cs151.application.models.Appointment;
import s25.cs151.application.models.Courses;
import s25.cs151.application.models.TimeSlots;
import s25.cs151.application.services.EditFormContext;
import s25.cs151.application.services.PageNavigator;

public class EditAppointmentFormController {
    public TextField studentNameTextField;
    public DatePicker scheduleDatePicker;
    public ComboBox<TimeSlots> timeSlotComboBox;
    public ComboBox<Courses> courseComboBox;
    public TextField reasonTextField;
    public TextField commentTextField;
    public Label errorMessageLabel;

    @FXML
    public void initialize() {
        List<Courses> coursesList = CoursesDAO.getCourseList();
        ObservableList<Courses> coursesData = FXCollections.observableArrayList(coursesList);
        courseComboBox.setItems(coursesData);

         //Show only course code in dropdown
        courseComboBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Courses course, boolean empty) {
                super.updateItem(course, empty);
                setText(empty || course == null ? null : course.getCourseCode());
            }
        });

        // Also show course code in the selected item display
        courseComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Courses course, boolean empty) {
                super.updateItem(course, empty);
                setText(empty || course == null ? null : course.getCourseCode());
            }
        });

        List<TimeSlots> timeSlotsList = TimeSlotsDAO.getTimeSlots();
        ObservableList<TimeSlots> timeSlotsdata = FXCollections.observableArrayList(timeSlotsList);
        timeSlotComboBox.setItems(timeSlotsdata);

        Appointment selectedAppointment = EditFormContext.getSelectedAppointment();
        prefillAppointment(selectedAppointment);

    }

    @FXML
    public void handleCancel() {
        PageNavigator.navigateTo("Home");
    }

    private boolean validateInputs(Appointment appointment) {

        // get the variables
        String name = studentNameTextField.getText();
        LocalDate date = scheduleDatePicker.getValue();
        TimeSlots timeSlot = timeSlotComboBox.getValue();
        Courses course = courseComboBox.getValue();
        String reason = reasonTextField.getText();
        String comment = commentTextField.getText();

        // validate the variables
        // Simple validation
        if (name == null || name.trim().isEmpty()) {
            studentNameTextField.setStyle("-fx-text-fill: red;");
            errorMessageLabel.setText("Student name is required.");
            return false;
        }

        if (date == null) {
            scheduleDatePicker.setStyle("-fx-border-color: red;");
            errorMessageLabel.setText("Schedule date is required.");
            return false;
        }

        if (timeSlot == null ) {
            timeSlotComboBox.setStyle("-fx-border-color: red;");
            errorMessageLabel.setText("Time slot is required.");
            return false;
        }

        if (course == null ) {
            courseComboBox.setStyle("-fx-border-color: red;");
            errorMessageLabel.setText("Course is required.");
            return false;
        }

        // create office hours

        appointment.setStudentFullName(name);
        appointment.setScheduleDate(date);
        appointment.setCourses(course);
        appointment.setTimeSlots(timeSlot);
        appointment.setReason(reason);
        appointment.setComments(comment);

        // printing just in case
        // Optional fields are allowed to be empty
        System.out.println("Saving appointment:");
        System.out.println("Name: " + name);
        System.out.println("Date: " + date);
        System.out.println("Time Slot: " + timeSlot);
        System.out.println("Course: " + course);
        System.out.println("Reason: " + reason);
        System.out.println("Comment: " + comment);
        return true;
    }


    @FXML
    public void handleSubmit() {
        errorMessageLabel.setText("");

        // validates the boxes and returns a new appointment in param
        Appointment appointment = EditFormContext.getSelectedAppointment();
        System.out.println(appointment.getCourses());
        System.out.println(appointment.getCourses().getCourseCode());

        if (validateInputs(appointment)) {

            boolean saved;

            try {
                saved = AppointmentDAO.updateAppointments(appointment);
            }
            catch (SQLException e) {
                showAlert("Database Error", "Something went wrong: \n" + e.getMessage());
                e.printStackTrace();
                return;
            }

            if (saved) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Success");
                confirm.setHeaderText("Appointment successfully defined.");

                Optional<ButtonType> result = confirm.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                  PageNavigator.navigateTo("Schedule");
                } else {
                    // User clicked Cancel, navigate to Home
                    PageNavigator.navigateTo("Home");
                }
            } else {
                errorMessageLabel.setText("Error saving appointment. Please try again.");
            }
        }

    }

    public void prefillAppointment(Appointment appt) {
        studentNameTextField.setText(appt.getStudentFullName());
        scheduleDatePicker.setValue(appt.getScheduleDate());
        timeSlotComboBox.getSelectionModel().select(appt.getTimeSlots());
        courseComboBox.getSelectionModel().select(appt.getCourses());
        reasonTextField.setText(appt.getReason());
        commentTextField.setText(appt.getComments());
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


    public void handleViewAppointments() {
        PageNavigator.navigateTo("Schedule");
    }
}
