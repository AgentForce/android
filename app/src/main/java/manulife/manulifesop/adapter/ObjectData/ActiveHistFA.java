package manulife.manulifesop.adapter.ObjectData;

/**
 * Created by Chick on 2/23/2018.
 */

public class ActiveHistFA {
    private int id;
    private String avatar;
    private String title;
    private String content;

    private String phone;
    private String processStatusName;
    private int processStep;

    public int getProcessStep() {
        return processStep;
    }

    public void setProcessStep(int processStep) {
        this.processStep = processStep;
    }

    public String getProcessStatusName() {
        return processStatusName;
    }

    public void setProcessStatusName(String processStatusName) {
        this.processStatusName = processStatusName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
