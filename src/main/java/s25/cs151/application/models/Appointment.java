package s25.cs151.application.models;
import java.time.LocalDate;

public class Appointment {

    private int appointmentId;
    private String StudentFullName;
    private LocalDate ScheduleDate;
    private TimeSlots TimeSlot;
    private Courses Courses;
    private String Reason;
    private String Comments;

    
    public Appointment(String name, LocalDate date, TimeSlots timeslot,
                       Courses course, String reason, String comment) {
        setStudentFullName(name);
        setScheduleDate(date);
        setTimeSlots(timeslot);
        setCourses(course);
        setReason(reason);
        setComments(comment);
    }

    // no arg constr
    public Appointment() {}

    // getters
    public String getStudentFullName() {
        return StudentFullName;
    }
    public LocalDate getScheduleDate() {return ScheduleDate;}
    public TimeSlots getTimeSlots() {return TimeSlot;}
    public Courses getCourses() {return Courses;}
    public String getReason() {return Reason;}
    public String getComments() {return Comments;}
    public int getAppointmentId() {return appointmentId; }
    // setters
    public void setStudentFullName(String studentFullName) {
        StudentFullName = studentFullName;
    }
    public void setScheduleDate(LocalDate scheduleDate) { ScheduleDate = scheduleDate;}
    public void setTimeSlots(TimeSlots timeSlots) { TimeSlot = timeSlots;}
    public void setCourses(Courses courses) { Courses = courses;}
    public void setReason(String reason) { Reason = reason;}
    public void setComments(String comments) { Comments = comments;}
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId;}


}
