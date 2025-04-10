package s25.cs151.application.models;

import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.util.List;

public class Appointment {

    private String appointmentId;
    private String StudentFullName;
    private LocalDate ScheduleDate;
    private TimeSlots TimeSlot;
    private Courses Courses;
    private String Reason;
    private String Comments;


    // getters
    public String getAppointmentId() {return appointmentId;}
    public String getStudentFullName() {
        return StudentFullName;
    }
    public LocalDate getScheduleDate() {return ScheduleDate;}
    public TimeSlots getTimeSlots() {return TimeSlot;}
    public Courses getCourses() {return Courses;}
    public String getReason() {return Reason;}
    public String getComments() {return Comments;}

    // setters
    public void setStudentFullName(String studentFullName) {
        StudentFullName = studentFullName;
    }
    public void setScheduleDate(LocalDate scheduleDate) { ScheduleDate = scheduleDate;}
    public void setTimeSlots(TimeSlots timeSlots) { TimeSlot = timeSlots;}
    public void setCourses(Courses courses) { Courses = courses;}
    public void setReason(String reason) { Reason = reason;}
    public void setComments(String comments) { Comments = comments;}


}
