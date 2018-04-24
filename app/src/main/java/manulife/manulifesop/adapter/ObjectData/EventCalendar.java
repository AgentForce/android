package manulife.manulifesop.adapter.ObjectData;

/**
 * Created by ADMIN on 3/13/2018.
 */

public class EventCalendar {
    private int id;
    private String avatar;
    private String name;
    private String date;
    private String location;
    private boolean status;
    private String processStep;

    public EventCalendar(int id,String avatar, String name, String processStep, String date, String location,boolean status) {
        this.id = id;
        this.avatar = avatar;
        this.name = name;
        this.processStep = processStep;
        this.date = date;
        this.location = location;
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public String getProcessStep() {
        return processStep;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }
}
