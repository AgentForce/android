package manulife.manulifesop.adapter.ObjectData;

/**
 * Created by Chick on 2/23/2018.
 */

public class RecruitmentDirectlyData {
    private int id;
    private String fullName;
    private int reportTo;
    private int currentStep1,currentStep2,currentStep3,currentStep4,currentStep5;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getReportTo() {
        return reportTo;
    }

    public void setReportTo(int reportTo) {
        this.reportTo = reportTo;
    }

    public int getCurrentStep1() {
        return currentStep1;
    }

    public void setCurrentStep1(int currentStep1) {
        this.currentStep1 = currentStep1;
    }

    public int getCurrentStep2() {
        return currentStep2;
    }

    public void setCurrentStep2(int currentStep2) {
        this.currentStep2 = currentStep2;
    }

    public int getCurrentStep3() {
        return currentStep3;
    }

    public void setCurrentStep3(int currentStep3) {
        this.currentStep3 = currentStep3;
    }

    public int getCurrentStep4() {
        return currentStep4;
    }

    public void setCurrentStep4(int currentStep4) {
        this.currentStep4 = currentStep4;
    }

    public int getCurrentStep5() {
        return currentStep5;
    }

    public void setCurrentStep5(int currentStep5) {
        this.currentStep5 = currentStep5;
    }
}
