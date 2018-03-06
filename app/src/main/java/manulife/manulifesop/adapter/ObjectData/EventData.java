package manulife.manulifesop.adapter.ObjectData;

import java.io.Serializable;

/**
 * Created by Chick on 2/23/2018.
 */

public class EventData implements Serializable{
    private String avatar;
    private String name;
    private String typeEvent;
    private String dateTime;

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(String typeEvent) {
        this.typeEvent = typeEvent;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
