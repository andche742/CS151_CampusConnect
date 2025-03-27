package s25.cs151.application.models;

import java.util.ArrayList;
import java.util.List;

public class OfficeHours {

    private final String semester;
    private final int year;
    private final List<String> days;

    public String getSemester() {
        return semester;
    }

    public int getYear() {
        return year;
    }

    public List<String> getDays() {
        return days;
    }

    public OfficeHours(String semester, int year, List<String> days) {
        this.semester = semester;
        this.year = year;
        this.days = days;
    }


}
