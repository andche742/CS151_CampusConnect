package s25.cs151.application.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

public class ScheduleController {
    public Button backButton;

    @FXML private TableView<Appointment> scheduleTable;
    @FXML private TableColumn<Appointment, String> studentFullNameColumn;
    @FXML private TableColumn<Appointment, LocalDate> scheduleDateColumn;
    @FXML private TableColumn<Appointment, TimeSlots> timeSlotColumn;
    @FXML private TableColumn<Appointment, Courses> courseColumn;
    @FXML private TableColumn<Appointment, String> reasonColumn;
    @FXML private TableColumn<Appointment, String> commentsColumn;


    public void handleBack() {
        PageNavigator.navigateTo("Home");
    }

    public void initialize() {
        studentFullNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentFullName"));
        scheduleDateColumn.setCellValueFactory(new PropertyValueFactory<>("scheduleDate"));
        timeSlotColumn.setCellValueFactory(new PropertyValueFactory<>("timeSlots"));
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("courses"));
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));
        commentsColumn.setCellValueFactory(new PropertyValueFactory<>("comments"));

        List<Appointment> AppointmentList = getAppointmentList();
        ObservableList<Appointment> data = FXCollections.observableArrayList(AppointmentList);
        scheduleTable.setItems(data);
    }

    private List<Appointment> getAppointmentList() {
        return AppointmentDAO.getAppointmentList();
    }




}
