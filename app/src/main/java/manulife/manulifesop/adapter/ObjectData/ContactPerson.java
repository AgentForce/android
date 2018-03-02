package manulife.manulifesop.adapter.ObjectData;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by ADMIN on 3/1/2018.
 */

public class ContactPerson implements Serializable{
    private boolean isChecked;
    private String avatar;
    private String name;
    private String phone;

    private int headerGroup;

    public ContactPerson(boolean isChecked, String avatar, String name, String phone, int headerGroup) {
        this.isChecked = isChecked;
        this.avatar = avatar;
        this.name = name;
        this.phone = phone;
        this.headerGroup = headerGroup;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getHeaderGroup() {
        return headerGroup;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
