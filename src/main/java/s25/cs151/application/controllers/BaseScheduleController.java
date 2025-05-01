package s25.cs151.application.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import s25.cs151.application.DAO.AppointmentDAO;
import s25.cs151.application.models.Appointment;
import s25.cs151.application.models.Courses;
import s25.cs151.application.models.TimeSlots;
import s25.cs151.application.services.PageNavigator;

import java.time.LocalDate;
import java.util.List;

import static javafx.collections.FXCollections.*;

public class BaseScheduleController {

    @FXML protected TableView<Appointment> scheduleTable;
    @FXML protected TableColumn<Appointment, String> studentFullNameColumn;
    @FXML protected TableColumn<Appointment, LocalDate> scheduleDateColumn;
    @FXML protected TableColumn<Appointment, TimeSlots> timeSlotColumn;
    @FXML protected TableColumn<Appointment, Courses> courseColumn;
    @FXML protected TableColumn<Appointment, String> reasonColumn;
    @FXML protected TableColumn<Appointment, String> commentsColumn;
    protected ObservableList<Appointment> appointments = observableArrayList();

    @FXML
    protected void initialize() {
        bindColumns();
        loadAppointments();
        scheduleTable.setItems(appointments);
        applyDefaultSorting();
        secondaryLoading();
    }

    // parent methods
    protected void bindColumns() {
        studentFullNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentFullName"));
        scheduleDateColumn.setCellValueFactory(new PropertyValueFactory<>("scheduleDate"));
        timeSlotColumn.setCellValueFactory(new PropertyValueFactory<>("timeSlots"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("courses"));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));
        commentsColumn.setCellValueFactory(new PropertyValueFactory<>("comments"));
    }
    protected void loadAppointments() {
        appointments.setAll(AppointmentDAO.getAppointmentList());
    }

    @SuppressWarnings("unchecked")
    protected void applyDefaultSorting(){
        scheduleDateColumn.setSortType(TableColumn.SortType.DESCENDING);
        timeSlotColumn.setSortType(TableColumn.SortType.DESCENDING);
        scheduleTable.getSortOrder().setAll(scheduleDateColumn, timeSlotColumn);
        scheduleTable.sort();
    };
    protected void filterStudentbyName(String input) {
        if ( input == null || input.isBlank() ) {
            loadAppointments();
        } else {
            String studentName = input.toLowerCase().trim();
            List<Appointment> filteredList = AppointmentDAO.getAppointmentList().stream()
                    .filter(appt -> appt.getStudentFullName().toLowerCase().trim()
                            .contains(input)).toList();
            appointments.setAll(filteredList);
        }
        scheduleTable.setItems(appointments);
        applyDefaultSorting();
    }
    protected void handleBack() {
        PageNavigator.navigateTo("Home");
    };


    protected void secondaryLoading() {
        // override in subclasses if needed
        // this is for adding delete in search table
        // also for edit option
    }


}
