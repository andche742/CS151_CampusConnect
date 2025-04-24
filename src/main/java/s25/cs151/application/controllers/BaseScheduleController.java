package s25.cs151.application.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import s25.cs151.application.models.Appointment;
import s25.cs151.application.models.Courses;
import s25.cs151.application.models.TimeSlots;

import java.time.LocalDate;

import static javafx.collections.FXCollections.*;

public abstract class BaseScheduleController {

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


    protected void bindColumns() {
        studentFullNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentFullName"));
        scheduleDateColumn.setCellValueFactory(new PropertyValueFactory<>("scheduleDate"));
        timeSlotColumn.setCellValueFactory(new PropertyValueFactory<>("timeSlots"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("courses"));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));
        commentsColumn.setCellValueFactory(new PropertyValueFactory<>("comments"));
    }

    protected abstract void loadAppointments() ;
    protected abstract void applyDefaultSorting();
    protected abstract void handleBack();
    protected void secondaryLoading() {
        // override in subclasses if needed
        // this is for adding delete in search table
    }


}
