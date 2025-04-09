package s25.cs151.application.controllers;

import javafx.event.ActionEvent;
import s25.cs151.application.services.PageNavigator;

public class DefineAppointmentsController {



    public void handleCancel() {
        PageNavigator.navigateTo("Home");
    }

    public void handleSubmit() {
    }
}
