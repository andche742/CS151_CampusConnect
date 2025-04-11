package s25.cs151.application;
import javafx.application.Application;
import javafx.stage.Stage;
import s25.cs151.application.DAO.AppointmentDAO;
import s25.cs151.application.DAO.CoursesDAO;
import s25.cs151.application.DAO.TimeSlotsDAO;
import s25.cs151.application.services.PageNavigator;
import s25.cs151.application.DAO.OfficeHoursDAO;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {

        // load and show the UI
        PageNavigator.setStage(primaryStage);
        PageNavigator.navigateTo("Home");
    }

    public static void main(String[] args) {
        // init the table for db
        OfficeHoursDAO.initializeOfficeHoursTable();
        TimeSlotsDAO.initializeTimeSlotsTable();
        CoursesDAO.initializeCoursesTable();
        AppointmentDAO.initializeAppointmentsTable();
        launch(args);
    }
}