package manulife.manulifesop.fragment.FAGroup.clients.ContentClient.ObjectMonth;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.appointment.AppointmentActivity;
import manulife.manulifesop.activity.FAGroup.clients.consultant.ConsultantActivity;
import manulife.manulifesop.activity.FAGroup.clients.contact.ContactPersonActivity;
import manulife.manulifesop.activity.FAGroup.clients.introduceContact.IntroduceContactActivity;
import manulife.manulifesop.activity.FAGroup.clients.signed.SignedPersonActivity;
import manulife.manulifesop.activity.FAGroup.main.MainFAActivity;
import manulife.manulifesop.api.ObjectResponse.CampaignMonth;
import manulife.manulifesop.base.BaseFragment;

/**
 * Created by Chick on 10/27/2017.
 */

public class FAObjectMonthFragment extends BaseFragment<MainFAActivity, FAObjectMonthPresent> implements FAObjectMonthContract.View {

    @BindView(R.id.layout_contact)
    RelativeLayout layoutContact;
    @BindView(R.id.layout_meeting)
    RelativeLayout layoutMeeting;
    @BindView(R.id.layout_advisory)
    RelativeLayout layoutAdvisory;
    @BindView(R.id.layout_sign)
    RelativeLayout layoutSign;
    @BindView(R.id.layout_introduce)
    RelativeLayout layoutIntroduce;

    //variables load number of campaign
    @BindView(R.id.txt_step1_result)
    TextView txtStep1;
    @BindView(R.id.txt_step2_result)
    TextView txtStep2;
    @BindView(R.id.txt_step3_result)
    TextView txtStep3;
    @BindView(R.id.txt_step4_result)
    TextView txtStep4;
    @BindView(R.id.txt_step5_result)
    TextView txtStep5;

    private CampaignMonth nData;
    private int mMonth;

    private int targetStep1 = 0, targetStep2 = 0, targetStep3 = 0, targetStep4 = 0, targetStep5 = 0;



    public static FAObjectMonthFragment newInstance(CampaignMonth data,int month) {
        Bundle args = new Bundle();
        args.putSerializable("data", data);
        args.putInt("month",month);
        FAObjectMonthFragment fragment = new FAObjectMonthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_fa_customer_content_month;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new FAObjectMonthPresent(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nData = (CampaignMonth) getArguments().getSerializable("data");
        initViews(nData);
        mMonth = getArguments().getInt("month",0);
    }

    private void initViews(CampaignMonth data) {
        if (data != null) {
            //int targetStep1 = 0, targetStep2 = 0, targetStep3 = 0, targetStep4 = 0, targetStep5 = 0;
            int currentStep1 = 0, currentStep2 = 0, currentStep3 = 0, currentStep4 = 0, currentStep5 = 0;
            for (int i = 0; i < data.data.campaigns.size(); i++) {
                targetStep1 += data.data.campaigns.get(i).targetCallSale;
                currentStep1 += data.data.campaigns.get(i).currentCallSale;

                targetStep2 += data.data.campaigns.get(i).targetMetting;
                currentStep2 += data.data.campaigns.get(i).currentMetting;

                targetStep3 += data.data.campaigns.get(i).targetPresentation;
                currentStep3 += data.data.campaigns.get(i).currentPresentation;

                targetStep4 += data.data.campaigns.get(i).targetContractSale;
                currentStep4 += data.data.campaigns.get(i).currentContract;

                targetStep5 += data.data.campaigns.get(i).targetReLead;
                currentStep5 += data.data.campaigns.get(i).currentReLead;
            }

            txtStep1.setText(currentStep1 + "/" + targetStep1);
            txtStep2.setText(currentStep2 + "/" + targetStep2);
            txtStep3.setText(currentStep3 + "/" + targetStep3);
            txtStep4.setText(currentStep4 + "/" + targetStep4);
            txtStep5.setText(currentStep5 + "/" + targetStep5);
        }
    }

    @OnClick({R.id.layout_contact, R.id.layout_meeting, R.id.layout_advisory, R.id.layout_sign, R.id.layout_introduce})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_contact: {
                //Toast.makeText(mActivity, "layout_contact", Toast.LENGTH_SHORT).show();
                Bundle data = new Bundle();
                data.putInt("month",mMonth);
                data.putInt("target",targetStep1);
                data.putInt("targetIntroduce",targetStep5);
                ProjectApplication.getInstance().setCampaign(nData);
                mActivity.goNextScreen(ContactPersonActivity.class,data);
                break;
            }
            case R.id.layout_meeting: {
                //Toast.makeText(mActivity, "layout_meeting", Toast.LENGTH_SHORT).show();
                Bundle data = new Bundle();
                data.putInt("month",mMonth);
                data.putInt("target",targetStep2);
                mActivity.goNextScreen(AppointmentActivity.class,data);
                break;
            }
            case R.id.layout_advisory: {
                //Toast.makeText(mActivity, "layout_advisory", Toast.LENGTH_SHORT).show();
                Bundle data = new Bundle();
                data.putInt("month",mMonth);
                data.putInt("target",targetStep3);
                mActivity.goNextScreen(ConsultantActivity.class,data);
                break;
            }
            case R.id.layout_sign: {
                //Toast.makeText(mActivity, "layout_sign", Toast.LENGTH_SHORT).show();
                Bundle data = new Bundle();
                data.putInt("month",mMonth);
                data.putInt("target",targetStep4);
                mActivity.goNextScreen(SignedPersonActivity.class,data);
                break;
            }
            case R.id.layout_introduce: {
                //Toast.makeText(mActivity, "layout_introduce", Toast.LENGTH_SHORT).show();
                Bundle data = new Bundle();
                data.putInt("month",mMonth);
                data.putInt("target",targetStep5);
                ProjectApplication.getInstance().setCampaign(nData);
                mActivity.goNextScreen(IntroduceContactActivity.class,data);
                break;
            }

        }
    }

}
