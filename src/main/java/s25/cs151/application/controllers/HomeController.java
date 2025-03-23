package s25.cs151.application.controllers;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuButton;
import s25.cs151.application.services.PageNavigator;


public class HomeController {

    public MenuButton officeHoursMenuButton;

    public void goToDefineSemesterOfficeHours() {
        PageNavigator.navigateTo("DefineSemesterOfficeHours");
    }

    public void goToTable() {
        PageNavigator.navigateTo("TableView");
    }

    public void placeHolder(ActionEvent actionEvent) {
    }
}

