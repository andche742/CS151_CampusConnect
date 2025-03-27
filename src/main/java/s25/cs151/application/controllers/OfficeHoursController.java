package s25.cs151.application.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import s25.cs151.application.DAO.OfficeHoursDAO;
import s25.cs151.application.models.OfficeHours;
import s25.cs151.application.services.PageNavigator;

import java.util.List;

public class OfficeHoursController {

    // for table view
    @FXML
    private TableView<OfficeHours> officeHoursTable;
    @FXML
    private TableColumn<OfficeHours, Integer> yearColumn;
    @FXML
    private TableColumn<OfficeHours, String> semesterColumn;
    @FXML
    private TableColumn<OfficeHours, String> daysColumn;

    public void initialize() {
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));
        daysColumn.setCellValueFactory(new PropertyValueFactory<>("daysAsString"));
        List<OfficeHours> officeHoursList = getOfficeHours();
        ObservableList<OfficeHours> data = FXCollections.observableArrayList(officeHoursList);
        officeHoursTable.setItems(data);
    }

    @FXML
    private void handleHomeButton() {
        PageNavigator.navigateTo("Home");
    }

    public void goToDefineSemesterOfficeHours() {
        PageNavigator.navigateTo("DefineSemesterOfficeHours");
    }    

    private List<OfficeHours> getOfficeHours() {
        //  DB logic
        return OfficeHoursDAO.getOfficeHours();
    }

}
