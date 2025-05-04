package s25.cs151.application.controllers;
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
    public void goToSchedule() { PageNavigator.navigateTo("Schedule");}
    public void goToEditOfficeHours() { PageNavigator.navigateTo("EditOfficeHours"); }
    public void goToDefineAppointment() { PageNavigator.navigateTo("DefineAppointments"); }
}

