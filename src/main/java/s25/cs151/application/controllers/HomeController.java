package s25.cs151.application.controllers;
import javafx.event.ActionEvent;
import s25.cs151.application.services.PageNavigator;


public class HomeController {
    
    public void goToDefineSemesterOfficeHours() {
        PageNavigator.navigateTo("DefineSemesterOfficeHours");
    }

    public void placeHolder(ActionEvent actionEvent) {
    }
}

