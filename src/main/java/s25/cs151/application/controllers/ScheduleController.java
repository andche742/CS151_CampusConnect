package s25.cs151.application.controllers;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import s25.cs151.application.services.PageNavigator;


public class ScheduleController extends BaseScheduleController {
    public Button backButton;
   @FXML
    @Override
    protected void handleBack() {
        PageNavigator.navigateTo("Home");
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void applyDefaultSorting() {
        scheduleDateColumn.setSortType(TableColumn.SortType.ASCENDING);
        timeSlotColumn.setSortType(TableColumn.SortType.ASCENDING);
        scheduleTable.getSortOrder().addAll(scheduleDateColumn, timeSlotColumn);
        scheduleTable.sort();
    }





}
