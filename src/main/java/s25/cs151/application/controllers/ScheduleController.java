package s25.cs151.application.controllers;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import s25.cs151.application.DAO.AppointmentDAO;
import s25.cs151.application.services.PageNavigator;


public class ScheduleController extends BaseScheduleController {
    public Button backButton;

    public void handleBack() {
        PageNavigator.navigateTo("DefineAppointments");
    }

    @Override
    protected void loadAppointments() {
        appointments.setAll(AppointmentDAO.getAppointmentList());
    }

    @Override
    protected void applyDefaultSorting() {
        scheduleDateColumn.setSortType(TableColumn.SortType.ASCENDING);
        timeSlotColumn.setSortType(TableColumn.SortType.ASCENDING);
        scheduleTable.getSortOrder().addAll(scheduleDateColumn, timeSlotColumn);
        scheduleTable.sort();
    }





}
