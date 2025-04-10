package s25.cs151.application.models;

import javafx.scene.control.DatePicker;

import java.util.List;

public class Appointment {

    private String appointmentId;
    private String StudentFullName;
    private DatePicker ScheduleDate;
    private List<TimeSlots> TimeSlots;
    private List<Courses> Courses;
    private String Reason;
    private String Comments;


    // getters
    public String getAppointmentId() {return appointmentId;}
    public String getStudentFullName() {
        return StudentFullName;
    }
    public DatePicker getScheduleDate() {return ScheduleDate;}
    public List<TimeSlots> getTimeSlots() {return TimeSlots;}
    public List<Courses> getCourses() {return Courses;}
    public String getReason() {return Reason;}
    public String getComments() {return Comments;}

    // setters
    public void setStudentFullName(String studentFullName) {
        StudentFullName = studentFullName;
    }
    public void setScheduleDate(DatePicker scheduleDate) { ScheduleDate = scheduleDate;}
    public void setTimeSlots(List<TimeSlots> timeSlots) { TimeSlots = timeSlots;}
    public void setCourses(List<Courses> courses) { Courses = courses;}
    public void setReason(String reason) { Reason = reason;}
    public void setComments(String comments) { Comments = comments;}


}
