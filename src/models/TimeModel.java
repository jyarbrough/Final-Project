package models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeModel {

    private Date today = new Date();
    private SimpleDateFormat dayFormatter = new SimpleDateFormat("EEE MMM d");
    private SimpleDateFormat timeFormatter = new SimpleDateFormat("h:mm a");
    private String dayOfTheWeek = dayFormatter.format(today);
    private String currentTime = timeFormatter.format(today);

    public String getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setDayOfTheWeek(String dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}
