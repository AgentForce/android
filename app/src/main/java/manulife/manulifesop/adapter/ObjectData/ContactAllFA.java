package manulife.manulifesop.adapter.ObjectData;

/**
 * Created by Chick on 2/23/2018.
 */

public class ContactAllFA {
    private int id;
    private int campaignID;
    private String title;
    private String content;

    private String phone;
    private int processStep;
    private int statusStep;

    public int getCampaignID() {
        return campaignID;
    }

    public void setCampaignID(int campaignID) {
        this.campaignID = campaignID;
    }

    public int getStatusStep() {
        return statusStep;
    }

    public void setStatusStep(int statusStep) {
        this.statusStep = statusStep;
    }

    public int getProcessStep() {
        return processStep;
    }

    public void setProcessStep(int processStep) {
        this.processStep = processStep;
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
