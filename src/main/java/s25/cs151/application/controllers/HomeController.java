package s25.cs151.application.controllers;
import javafx.event.ActionEvent;
import s25.cs151.application.services.PageNavigator;


public class HomeController {


    public void goToTimeSlots() {
        PageNavigator.navigateTo("TimeSlots");
    }

    public void goToOfficeHours() {
        PageNavigator.navigateTo("OfficeHours");
    }

    public void goToCourses() {
        PageNavigator.navigateTo("Courses");
    }

    public void goToSearch(){ PageNavigator.navigateTo("Search"); }

    public void placeHolder() {
    }

    public void goToDefineAppointments() { PageNavigator.navigateTo("DefineAppointments"); }
}

