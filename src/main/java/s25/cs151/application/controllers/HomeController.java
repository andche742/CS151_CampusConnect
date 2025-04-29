package s25.cs151.application.controllers;
import javafx.event.ActionEvent;
import s25.cs151.application.services.PageNavigator;


public class HomeController {

    // navigate to certain pages
    public void goToTimeSlots() {PageNavigator.navigateTo("TimeSlots");}
    public void goToOfficeHours() {
        PageNavigator.navigateTo("OfficeHours");
    }
    public void goToCourses() {
        PageNavigator.navigateTo("Courses");
    }
    public void goToSearch(){ PageNavigator.navigateTo("Search"); }
    public void goToDefineAppointments() { PageNavigator.navigateTo("DefineAppointments"); }
    public void goToSchedule() { PageNavigator.navigateTo("Schedule");}
}

