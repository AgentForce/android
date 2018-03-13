package manulife.manulifesop.adapter.ObjectData;

/**
 * Created by ADMIN on 3/13/2018.
 */

public class EventCalendar {
    private String avatar;
    private String name;
    private int processStep;
    private String date;
    private String location;

    public EventCalendar(String avatar, String name, int processStep, String date, String location) {
        this.avatar = avatar;
        this.name = name;
        this.processStep = processStep;
        this.date = date;
        this.location = location;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public int getProcessStep() {
        return processStep;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }
}
