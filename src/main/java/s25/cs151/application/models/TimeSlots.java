package s25.cs151.application.models;

import java.time.LocalTime;

public class TimeSlots {

    private LocalTime fromTime;
    private LocalTime toTime;

    public String getFromTime() {
        return fromTime.toString();
    }

    public String getToTime() {
        return toTime.toString();
    }

    public int getFromHour() {
        return fromTime.getHour();
    }

    public int getFromMinute() {
        return fromTime.getHour();
    }

    public int getToHour() {
        return toTime.getHour();
    }

    public int getToMinute() {
        return toTime.getHour();
    }
    
    @Override
    public String toString() {
        return getFromTime() + " to " + getToTime();
    }
    public TimeSlots(int fromHour, int fromMinute, int toHour, int toMinute) {
        this.fromTime = LocalTime.of(fromHour, fromMinute);
        this.toTime = LocalTime.of(toHour, toMinute);
    }
}
