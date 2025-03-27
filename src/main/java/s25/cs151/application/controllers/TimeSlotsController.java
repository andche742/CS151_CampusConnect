package s25.cs151.application.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import s25.cs151.application.DAO.TimeSlotsDAO;
import s25.cs151.application.models.TimeSlots;
import s25.cs151.application.services.PageNavigator;

import java.util.List;

public class TimeSlotsController {

    // for table view
    @FXML
    private TableView<TimeSlots> timeSlotsTable;
    @FXML
    private TableColumn<TimeSlots, String> fromColumn;
    @FXML
    private TableColumn<TimeSlots, String> toColumn;

    public void initialize() {
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("fromTime"));
        toColumn.setCellValueFactory(new PropertyValueFactory<>("toTime"));
        List<TimeSlots> timeSlotsList = TimeSlotsDAO.getTimeSlots();
        ObservableList<TimeSlots> data = FXCollections.observableArrayList(timeSlotsList);
        timeSlotsTable.setItems(data);
    }

    @FXML
    private void handleHomeButton() {
        PageNavigator.navigateTo("Home");
    }

    public void goToDefineTimeSlots() {
        PageNavigator.navigateTo("DefineTimeSlots");
    }    

}
