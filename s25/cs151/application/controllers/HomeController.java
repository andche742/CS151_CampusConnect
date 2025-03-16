package controllers;


import javafx.event.ActionEvent;
import services.PageNavigator;


public class HomeController {
    
    public void goToDefineSemesterOfficeHours() {
        PageNavigator.navigateTo("DefineSemesterOfficeHours");
    }

}

