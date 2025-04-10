package s25.cs151.application.controllers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import s25.cs151.application.DAO.CoursesDAO;
import s25.cs151.application.DAO.TimeSlotsDAO;
import s25.cs151.application.models.Appointment;
import s25.cs151.application.models.Courses;
import s25.cs151.application.models.TimeSlots;
import s25.cs151.application.services.PageNavigator;
import java.time.LocalDate;
import java.util.List;

public class DefineAppointmentsController {


    public TextField studentNameTextField;
    public DatePicker scheduleDatePicker;
    public ComboBox<TimeSlots> timeSlotComboBox;
    public ComboBox<Courses> courseComboBox;
    public TextField reasonTextField;
    public TextField commentTextField;
    public Label errorMessageLabel;

    @FXML
    public void initialize() {
        scheduleDatePicker.setValue(LocalDate.now());
        List<Courses> coursesList = CoursesDAO.getCourseList();
        ObservableList<Courses> coursesData = FXCollections.observableArrayList(coursesList);
        courseComboBox.setItems(coursesData);

        // Show only course code in dropdown
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

        //courseComboBox.getItems().addAll();
        timeSlotComboBox.getSelectionModel().selectFirst();
        courseComboBox.getSelectionModel().selectFirst();

    }

    @FXML
    public void handleCancel() {
        PageNavigator.navigateTo("Home");
    }

    private void validateInputs(Appointment appointment) {

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
            return;
        }

        if (date == null) {
            scheduleDatePicker.setStyle("-fx-border-color: red;");
            errorMessageLabel.setText("Schedule date is required.");
            return;
        }

        if (timeSlot == null ) {
            timeSlotComboBox.setStyle("-fx-border-color: red;");
            errorMessageLabel.setText("Time slot is required.");
            return;
        }

        if (course == null ) {
            courseComboBox.setStyle("-fx-border-color: red;");
            errorMessageLabel.setText("Course is required.");
            return;
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

    }


    @FXML
    public void handleSubmit() {
        errorMessageLabel.setText("");

        // validates the boxes and returns a new appointment in param
        Appointment appointment = new Appointment();
        validateInputs(appointment);

    }


}
