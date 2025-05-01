package s25.cs151.application.services;

import s25.cs151.application.models.Appointment;

// this class is a utility class
// it stores the selected appointment in the edit office hours
// when javafx changes scenes, the local controller loses the state
// set it before navigation, use it after fxml load
// the methods are all static inside edit form context


public class EditFormContext {
        private static Appointment selectedAppointment;

        public static void setSelectedAppointment(Appointment appt) {
            selectedAppointment = appt;
        }

        public static Appointment getSelectedAppointment() {
            return selectedAppointment;
        }

        public static void clear() {
            selectedAppointment = null;
        }
    }



