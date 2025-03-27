package s25.cs151.application.models;

import java.util.ArrayList;
import java.util.List;

public class OfficeHours {

    private String semester;
    private int year;
    private List<String> days;
    private String daysAsString;

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<String> getDays() {
        return days;
    }

    public String getDaysAsString() {
        String daysAsString = "";
        for (String day : days) {
            daysAsString += day + ", ";
        }
        return daysAsString.substring(0, daysAsString.length() - 2);
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public OfficeHours() {
        this.semester = "";
        this.year = 2000;
        this.days = new ArrayList<>();
    }

    public OfficeHours(String semester, int year, List<String> days) {
        this.semester = semester;
        this.year = year;
        this.days = days;
        this.daysAsString = getDaysAsString();
    }


}