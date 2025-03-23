package s25.cs151.application.controllers;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuButton;
import s25.cs151.application.services.PageNavigator;


public class HomeController {

    public MenuButton officeHoursMenuButton;

    public void goToOfficeHours() {
        PageNavigator.navigateTo("OfficeHours");
    }

    public void placeHolder(ActionEvent actionEvent) {
    }
}

