package manulife.manulifesop.fragment.FAGroup.clients.ContentClient.ObjectWeek;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.warkiz.widget.IndicatorSeekBar;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.main.MainFAActivity;
import manulife.manulifesop.api.ObjectResponse.CampaignMonth;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.fragment.FAGroup.clients.ContentClient.FAContentCustomerFragment;
import manulife.manulifesop.fragment.FAGroup.dashboard.campaignPercent.CampaignPercentFragment;

/**
 * Created by Chick on 10/27/2017.
 */

public class FAObjectWeekFragment extends BaseFragment<MainFAActivity, FAObjectWeekPresent> implements FAObjectWeekContract.View {

    @BindView(R.id.txt_edit_week)
    TextView txtEditWeekObject;

    //variable load data contact
    @BindView(R.id.txt_contact_step1_result)
    TextView txtContact_step1;
    @BindView(R.id.txt_contact_step1_title)
    TextView txtContact_step1_title;
    @BindView(R.id.img_contact_step1_result_ok)
    ImageView imgContact_step1;
    @BindView(R.id.layout_contact_step1)
    LinearLayout layoutContactStep1;

    @BindView(R.id.txt_contact_step2_result)
    TextView txtContact_step2;
    @BindView(R.id.txt_contact_step2_title)
    TextView txtContact_step2_title;
    @BindView(R.id.img_contact_step2_result_ok)
    ImageView imgContact_step2;
    @BindView(R.id.layout_contact_step2)
    LinearLayout layoutContactStep2;

    @BindView(R.id.txt_contact_step3_result)
    TextView txtContact_step3;
    @BindView(R.id.txt_contact_step3_title)
    TextView txtContact_step3_title;
    @BindView(R.id.img_contact_step3_result_ok)
    ImageView imgContact_step3;
    @BindView(R.id.layout_contact_step3)
    LinearLayout layoutContactStep3;

    @BindView(R.id.txt_contact_step4_result)
    TextView txtContact_step4;
    @BindView(R.id.txt_contact_step4_title)
    TextView txtContact_step4_title;
    @BindView(R.id.img_contact_step4_result_ok)
    ImageView imgContact_step4;
    @BindView(R.id.layout_contact_step4)
    LinearLayout layoutContactStep4;

    //variable load data meeting
    @BindView(R.id.txt_meeting_step1_result)
    TextView txtMeeting_step1;
    @BindView(R.id.txt_meeting_step1_title)
    TextView txtMeeting_step1_title;
    @BindView(R.id.img_meeting_step1_result_ok)
    ImageView imgMeeting_step1;
    @BindView(R.id.layout_meeting_step1)
    LinearLayout layoutMeetingStep1;

    @BindView(R.id.txt_meeting_step2_result)
    TextView txtMeeting_step2;
    @BindView(R.id.txt_meeting_step2_title)
    TextView txtMeeting_step2_title;
    @BindView(R.id.img_meeting_step2_result_ok)
    ImageView imgMeeting_step2;
    @BindView(R.id.layout_meeting_step2)
    LinearLayout layoutMeetingStep2;

    @BindView(R.id.txt_meeting_step3_result)
    TextView txtMeeting_step3;
    @BindView(R.id.txt_meeting_step3_title)
    TextView txtMeeting_step3_title;
    @BindView(R.id.img_meeting_step3_result_ok)
    ImageView imgMeeting_step3;
    @BindView(R.id.layout_meeting_step3)
    LinearLayout layoutMeetingStep3;

    @BindView(R.id.txt_meeting_step4_result)
    TextView txtMeeting_step4;
    @BindView(R.id.txt_meeting_step4_title)
    TextView txtMeeting_step4_title;
    @BindView(R.id.img_meeting_step4_result_ok)
    ImageView imgMeeting_step4;
    @BindView(R.id.layout_meeting_step4)
    LinearLayout layoutMeetingStep4;

    //variable load data advisory
    @BindView(R.id.txt_advisory_step1_result)
    TextView txtAdvisory_step1;
    @BindView(R.id.txt_advisory_step1_title)
    TextView txtAdvisory_step1_title;
    @BindView(R.id.img_advisory_step1_result_ok)
    ImageView imgAdvisory_step1;
    @BindView(R.id.layout_advisory_step1)
    LinearLayout layoutAdvisoryStep1;

    @BindView(R.id.txt_advisory_step2_result)
    TextView txtAdvisory_step2;
    @BindView(R.id.txt_advisory_step2_title)
    TextView txtAdvisory_step2_title;
    @BindView(R.id.img_advisory_step2_result_ok)
    ImageView imgAdvisory_step2;
    @BindView(R.id.layout_advisory_step2)
    LinearLayout layoutAdvisoryStep2;

    @BindView(R.id.txt_advisory_step3_result)
    TextView txtAdvisory_step3;
    @BindView(R.id.txt_advisory_step3_title)
    TextView txtAdvisory_step3_title;
    @BindView(R.id.img_advisory_step3_result_ok)
    ImageView imgAdvisory_step3;
    @BindView(R.id.layout_advisory_step3)
    LinearLayout layoutAdvisoryStep3;

    @BindView(R.id.txt_advisory_step4_result)
    TextView txtAdvisory_step4;
    @BindView(R.id.txt_advisory_step4_title)
    TextView txtAdvisory_step4_title;
    @BindView(R.id.img_advisory_step4_result_ok)
    ImageView imgAdvisory_step4;
    @BindView(R.id.layout_advisory_step4)
    LinearLayout layoutAdvisoryStep4;

    //variable load data sign
    @BindView(R.id.txt_sign_step1_result)
    TextView txtSign_step1;
    @BindView(R.id.txt_sign_step1_title)
    TextView txtSign_step1_title;
    @BindView(R.id.img_sign_step1_result_ok)
    ImageView imgSign_step1;
    @BindView(R.id.layout_sign_step1)
    LinearLayout layoutaSignStep1;

    @BindView(R.id.txt_sign_step2_result)
    TextView txtSign_step2;
    @BindView(R.id.txt_sign_step2_title)
    TextView txtSign_step2_title;
    @BindView(R.id.img_sign_step2_result_ok)
    ImageView imgSign_step2;
    @BindView(R.id.layout_sign_step2)
    LinearLayout layoutaSignStep2;

    @BindView(R.id.txt_sign_step3_result)
    TextView txtSign_step3;
    @BindView(R.id.txt_sign_step3_title)
    TextView txtSign_step3_title;
    @BindView(R.id.img_sign_step3_result_ok)
    ImageView imgSign_step3;
    @BindView(R.id.layout_sign_step3)
    LinearLayout layoutaSignStep3;

    @BindView(R.id.txt_sign_step4_result)
    TextView txtSign_step4;
    @BindView(R.id.txt_sign_step4_title)
    TextView txtSign_step4_title;
    @BindView(R.id.img_sign_step4_result_ok)
    ImageView imgSign_step4;
    @BindView(R.id.layout_sign_step4)
    LinearLayout layoutaSignStep4;

    //variable load data introduce
    @BindView(R.id.txt_introduce_step1_result)
    TextView txtIntroduce_step1;
    @BindView(R.id.txt_introduce_step1_title)
    TextView txtIntroduce_step1_title;
    @BindView(R.id.img_introduce_step1_result_ok)
    ImageView imgIntroduce_step1;
    @BindView(R.id.layout_introduce_step1)
    LinearLayout layoutaIntroduceStep1;

    @BindView(R.id.txt_introduce_step2_result)
    TextView txtIntroduce_step2;
    @BindView(R.id.txt_introduce_step2_title)
    TextView txtIntroduce_step2_title;
    @BindView(R.id.img_introduce_step2_result_ok)
    ImageView imgIntroduce_step2;
    @BindView(R.id.layout_introduce_step2)
    LinearLayout layoutaIntroduceStep2;

    @BindView(R.id.txt_introduce_step3_result)
    TextView txtIntroduce_step3;
    @BindView(R.id.txt_introduce_step3_title)
    TextView txtIntroduce_step3_title;
    @BindView(R.id.img_introduce_step3_result_ok)
    ImageView imgIntroduce_step3;
    @BindView(R.id.layout_introduce_step3)
    LinearLayout layoutaIntroduceStep3;

    @BindView(R.id.txt_introduce_step4_result)
    TextView txtIntroduce_step4;
    @BindView(R.id.txt_introduce_step4_title)
    TextView txtIntroduce_step4_title;
    @BindView(R.id.img_introduce_step4_result_ok)
    ImageView imgIntroduce_step4;
    @BindView(R.id.layout_introduce_step4)
    LinearLayout layoutaIntroduceStep4;

    private int mMonth;

    public static FAObjectWeekFragment newInstance(CampaignMonth data,int month) {
        Bundle args = new Bundle();
        args.putSerializable("data", data);
        args.putInt("month",month);
        FAObjectWeekFragment fragment = new FAObjectWeekFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_fa_customer_content_week;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new FAObjectWeekPresent(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CampaignMonth data = (CampaignMonth) getArguments().getSerializable("data");
        mMonth = getArguments().getInt("month",0);
        initViews(data);
    }
    private void initViews(CampaignMonth data) {
        layoutContactStep1.post(new Runnable() {
            @Override
            public void run() {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layoutContactStep1.getLayoutParams();
                params.height = layoutContactStep1.getWidth();

                layoutContactStep1.setLayoutParams(params);
                layoutMeetingStep1.setLayoutParams(params);
                layoutAdvisoryStep1.setLayoutParams(params);
                layoutaSignStep1.setLayoutParams(params);
                layoutaIntroduceStep1.setLayoutParams(params);

            }
        });

        if (data != null && data.statusCode == 1) {
            //load step1 for 4 week
            loadStep1(data);
            loadStep2(data);
            loadStep3(data);
            loadStep4(data);
            loadStep5(data);
        }
    }

    private void loadStep1(CampaignMonth data) {
        //load step1 for 4 week
        txtContact_step1.setText(data.data.campaigns.get(0).currentCallSale + "/"
                + data.data.campaigns.get(0).targetCallSale);
        txtContact_step2.setText(data.data.campaigns.get(1).currentCallSale + "/"
                + data.data.campaigns.get(1).targetCallSale);
        txtContact_step3.setText(data.data.campaigns.get(2).currentCallSale + "/"
                + data.data.campaigns.get(2).targetCallSale);
        txtContact_step4.setText(data.data.campaigns.get(3).currentCallSale + "/"
                + data.data.campaigns.get(3).targetCallSale);

        switch (data.data.currentWeek) {
            case 1: {
                txtContact_step1.setTextColor(Color.parseColor("#FFFFFF"));
                txtContact_step1_title.setTextColor(Color.parseColor("#FFFFFF"));

                layoutContactStep1.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_present));
                layoutContactStep2.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                layoutContactStep3.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                layoutContactStep4.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                break;
            }
            case 2: {
                txtContact_step2.setTextColor(Color.parseColor("#FFFFFF"));
                txtContact_step2_title.setTextColor(Color.parseColor("#FFFFFF"));

                if (data.data.campaigns.get(0).targetCallSale == data.data.campaigns.get(0).currentCallSale) {
                    imgContact_step1.setAlpha(1f);
                }
                layoutContactStep1.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_previous));
                layoutContactStep2.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_present));
                layoutContactStep3.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                layoutContactStep4.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                break;
            }
            case 3: {
                txtContact_step3.setTextColor(Color.parseColor("#FFFFFF"));
                txtContact_step3_title.setTextColor(Color.parseColor("#FFFFFF"));

                if (data.data.campaigns.get(0).targetCallSale == data.data.campaigns.get(0).currentCallSale) {
                    imgContact_step1.setAlpha(1f);
                }
                if (data.data.campaigns.get(1).targetCallSale == data.data.campaigns.get(1).currentCallSale) {
                    imgContact_step2.setAlpha(1f);
                }
                layoutContactStep1.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_previous));
                layoutContactStep2.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_previous));
                layoutContactStep3.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_present));
                layoutContactStep4.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                break;
            }
            case 4: {
                txtContact_step4.setTextColor(Color.parseColor("#FFFFFF"));
                txtContact_step4_title.setTextColor(Color.parseColor("#FFFFFF"));
                if (data.data.campaigns.get(0).targetCallSale == data.data.campaigns.get(0).currentCallSale) {
                    imgContact_step1.setAlpha(1f);
                }
                if (data.data.campaigns.get(1).targetCallSale == data.data.campaigns.get(1).currentCallSale) {
                    imgContact_step2.setAlpha(1f);
                }
                if (data.data.campaigns.get(2).targetCallSale == data.data.campaigns.get(2).currentCallSale) {
                    imgContact_step3.setAlpha(1f);
                }
                layoutContactStep1.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_previous));
                layoutContactStep2.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_previous));
                layoutContactStep3.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_previous));
                layoutContactStep4.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_present));
                break;
            }
        }
    }

    private void loadStep2(CampaignMonth data) {
        //load step2 for 4 week
        txtMeeting_step1.setText(data.data.campaigns.get(0).currentMetting + "/"
                + data.data.campaigns.get(0).targetMetting);
        txtMeeting_step2.setText(data.data.campaigns.get(1).currentMetting + "/"
                + data.data.campaigns.get(1).targetMetting);
        txtMeeting_step3.setText(data.data.campaigns.get(2).currentMetting + "/"
                + data.data.campaigns.get(2).targetMetting);
        txtMeeting_step4.setText(data.data.campaigns.get(3).currentMetting + "/"
                + data.data.campaigns.get(3).targetMetting);

        switch (data.data.currentWeek) {
            case 1: {
                txtMeeting_step1.setTextColor(Color.parseColor("#FFFFFF"));
                txtMeeting_step1_title.setTextColor(Color.parseColor("#FFFFFF"));

                layoutMeetingStep1.setBackground(getResources().getDrawable(R.drawable.cus_step2_background_present));
                layoutMeetingStep2.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                layoutMeetingStep3.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                layoutMeetingStep4.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                break;
            }
            case 2: {
                txtMeeting_step2.setTextColor(Color.parseColor("#FFFFFF"));
                txtMeeting_step2_title.setTextColor(Color.parseColor("#FFFFFF"));

                if (data.data.campaigns.get(0).targetMetting == data.data.campaigns.get(0).currentMetting) {
                    imgMeeting_step1.setAlpha(1f);
                }
                layoutMeetingStep1.setBackground(getResources().getDrawable(R.drawable.cus_step2_background_previous));
                layoutMeetingStep2.setBackground(getResources().getDrawable(R.drawable.cus_step2_background_present));
                layoutMeetingStep3.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                layoutMeetingStep4.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                break;
            }
            case 3: {
                txtMeeting_step3.setTextColor(Color.parseColor("#FFFFFF"));
                txtMeeting_step3_title.setTextColor(Color.parseColor("#FFFFFF"));

                if (data.data.campaigns.get(0).targetMetting == data.data.campaigns.get(0).currentMetting) {
                    imgMeeting_step1.setAlpha(1f);
                }
                if (data.data.campaigns.get(1).targetMetting == data.data.campaigns.get(1).currentMetting) {
                    imgMeeting_step2.setAlpha(1f);
                }
                layoutMeetingStep1.setBackground(getResources().getDrawable(R.drawable.cus_step2_background_previous));
                layoutMeetingStep2.setBackground(getResources().getDrawable(R.drawable.cus_step2_background_previous));
                layoutMeetingStep3.setBackground(getResources().getDrawable(R.drawable.cus_step2_background_present));
                layoutMeetingStep4.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                break;
            }
            case 4: {
                txtMeeting_step4.setTextColor(Color.parseColor("#FFFFFF"));
                txtMeeting_step4_title.setTextColor(Color.parseColor("#FFFFFF"));

                if (data.data.campaigns.get(0).targetMetting == data.data.campaigns.get(0).currentMetting) {
                    imgMeeting_step1.setAlpha(1f);
                }
                if (data.data.campaigns.get(1).targetMetting == data.data.campaigns.get(1).currentMetting) {
                    imgMeeting_step2.setAlpha(1f);
                }
                if (data.data.campaigns.get(2).targetMetting == data.data.campaigns.get(2).currentMetting) {
                    imgMeeting_step3.setAlpha(1f);
                }
                layoutMeetingStep1.setBackground(getResources().getDrawable(R.drawable.cus_step2_background_previous));
                layoutMeetingStep2.setBackground(getResources().getDrawable(R.drawable.cus_step2_background_previous));
                layoutMeetingStep3.setBackground(getResources().getDrawable(R.drawable.cus_step2_background_previous));
                layoutMeetingStep4.setBackground(getResources().getDrawable(R.drawable.cus_step2_background_present));
                break;
            }
        }
    }

    private void loadStep3(CampaignMonth data) {
        //load step3 for 4 week
        txtAdvisory_step1.setText(data.data.campaigns.get(0).currentPresentation + "/"
                + data.data.campaigns.get(0).targetPresentation);
        txtAdvisory_step2.setText(data.data.campaigns.get(1).currentPresentation + "/"
                + data.data.campaigns.get(1).targetPresentation);
        txtAdvisory_step3.setText(data.data.campaigns.get(2).currentPresentation + "/"
                + data.data.campaigns.get(2).targetPresentation);
        txtAdvisory_step4.setText(data.data.campaigns.get(3).currentPresentation + "/"
                + data.data.campaigns.get(3).targetPresentation);

        switch (data.data.currentWeek) {
            case 1: {
                txtAdvisory_step1.setTextColor(Color.parseColor("#FFFFFF"));
                txtAdvisory_step1_title.setTextColor(Color.parseColor("#FFFFFF"));

                layoutAdvisoryStep1.setBackground(getResources().getDrawable(R.drawable.cus_step3_background_present));
                layoutAdvisoryStep2.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                layoutAdvisoryStep3.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                layoutAdvisoryStep4.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                break;
            }
            case 2: {
                txtAdvisory_step2.setTextColor(Color.parseColor("#FFFFFF"));
                txtAdvisory_step2_title.setTextColor(Color.parseColor("#FFFFFF"));

                if (data.data.campaigns.get(0).targetPresentation == data.data.campaigns.get(0).currentPresentation) {
                    imgAdvisory_step1.setAlpha(1f);
                }
                layoutAdvisoryStep1.setBackground(getResources().getDrawable(R.drawable.cus_step3_background_previous));
                layoutAdvisoryStep2.setBackground(getResources().getDrawable(R.drawable.cus_step3_background_present));
                layoutAdvisoryStep3.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                layoutAdvisoryStep4.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                break;
            }
            case 3: {
                txtAdvisory_step3.setTextColor(Color.parseColor("#FFFFFF"));
                txtAdvisory_step3_title.setTextColor(Color.parseColor("#FFFFFF"));

                if (data.data.campaigns.get(0).targetPresentation == data.data.campaigns.get(0).currentPresentation) {
                    imgAdvisory_step1.setAlpha(1f);
                }
                if (data.data.campaigns.get(1).targetPresentation == data.data.campaigns.get(1).currentPresentation) {
                    imgAdvisory_step2.setAlpha(1f);
                }
                layoutAdvisoryStep1.setBackground(getResources().getDrawable(R.drawable.cus_step3_background_previous));
                layoutAdvisoryStep2.setBackground(getResources().getDrawable(R.drawable.cus_step3_background_previous));
                layoutAdvisoryStep3.setBackground(getResources().getDrawable(R.drawable.cus_step3_background_present));
                layoutAdvisoryStep4.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                break;
            }
            case 4: {
                txtAdvisory_step4.setTextColor(Color.parseColor("#FFFFFF"));
                txtAdvisory_step4_title.setTextColor(Color.parseColor("#FFFFFF"));
                if (data.data.campaigns.get(0).targetPresentation == data.data.campaigns.get(0).currentPresentation) {
                    imgAdvisory_step1.setAlpha(1f);
                }
                if (data.data.campaigns.get(1).targetPresentation == data.data.campaigns.get(1).currentPresentation) {
                    imgAdvisory_step2.setAlpha(1f);
                }
                if (data.data.campaigns.get(2).targetPresentation == data.data.campaigns.get(2).currentPresentation) {
                    imgAdvisory_step3.setAlpha(1f);
                }
                layoutAdvisoryStep1.setBackground(getResources().getDrawable(R.drawable.cus_step3_background_previous));
                layoutAdvisoryStep2.setBackground(getResources().getDrawable(R.drawable.cus_step3_background_previous));
                layoutAdvisoryStep3.setBackground(getResources().getDrawable(R.drawable.cus_step3_background_previous));
                layoutAdvisoryStep4.setBackground(getResources().getDrawable(R.drawable.cus_step3_background_present));
                break;
            }
        }
    }

    private void loadStep4(CampaignMonth data) {
        //load step4 for 4 week
        txtSign_step1.setText(data.data.campaigns.get(0).currentContract + "/"
                + data.data.campaigns.get(0).targetContractSale);
        txtSign_step2.setText(data.data.campaigns.get(1).currentContract + "/"
                + data.data.campaigns.get(1).targetContractSale);
        txtSign_step3.setText(data.data.campaigns.get(2).currentContract + "/"
                + data.data.campaigns.get(2).targetContractSale);
        txtSign_step4.setText(data.data.campaigns.get(3).currentContract + "/"
                + data.data.campaigns.get(3).targetContractSale);

        switch (data.data.currentWeek) {
            case 1: {
                txtSign_step1.setTextColor(Color.parseColor("#FFFFFF"));
                txtSign_step1_title.setTextColor(Color.parseColor("#FFFFFF"));

                layoutaSignStep1.setBackground(getResources().getDrawable(R.drawable.cus_step4_background_present));
                layoutaSignStep2.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                layoutaSignStep3.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                layoutaSignStep4.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                break;
            }
            case 2: {
                txtSign_step2.setTextColor(Color.parseColor("#FFFFFF"));
                txtSign_step2_title.setTextColor(Color.parseColor("#FFFFFF"));
                if (data.data.campaigns.get(0).targetContractSale == data.data.campaigns.get(0).currentContract) {
                    imgSign_step1.setAlpha(1f);
                }
                layoutaSignStep1.setBackground(getResources().getDrawable(R.drawable.cus_step4_background_previous));
                layoutaSignStep2.setBackground(getResources().getDrawable(R.drawable.cus_step4_background_present));
                layoutaSignStep3.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                layoutaSignStep4.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                break;
            }
            case 3: {
                txtSign_step3.setTextColor(Color.parseColor("#FFFFFF"));
                txtSign_step3_title.setTextColor(Color.parseColor("#FFFFFF"));
                if (data.data.campaigns.get(0).targetContractSale == data.data.campaigns.get(0).currentContract) {
                    imgSign_step1.setAlpha(1f);
                }
                if (data.data.campaigns.get(1).targetContractSale == data.data.campaigns.get(1).currentContract) {
                    imgSign_step2.setAlpha(1f);
                }
                layoutaSignStep1.setBackground(getResources().getDrawable(R.drawable.cus_step4_background_previous));
                layoutaSignStep2.setBackground(getResources().getDrawable(R.drawable.cus_step4_background_previous));
                layoutaSignStep3.setBackground(getResources().getDrawable(R.drawable.cus_step4_background_present));
                layoutaSignStep4.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                break;
            }
            case 4: {
                txtSign_step4.setTextColor(Color.parseColor("#FFFFFF"));
                txtSign_step4_title.setTextColor(Color.parseColor("#FFFFFF"));
                if (data.data.campaigns.get(0).targetContractSale == data.data.campaigns.get(0).currentContract) {
                    imgSign_step1.setAlpha(1f);
                }
                if (data.data.campaigns.get(1).targetContractSale == data.data.campaigns.get(1).currentContract) {
                    imgSign_step2.setAlpha(1f);
                }
                if (data.data.campaigns.get(2).targetContractSale == data.data.campaigns.get(2).currentContract) {
                    imgSign_step3.setAlpha(1f);
                }
                layoutaSignStep1.setBackground(getResources().getDrawable(R.drawable.cus_step4_background_previous));
                layoutaSignStep2.setBackground(getResources().getDrawable(R.drawable.cus_step4_background_previous));
                layoutaSignStep3.setBackground(getResources().getDrawable(R.drawable.cus_step4_background_previous));
                layoutaSignStep4.setBackground(getResources().getDrawable(R.drawable.cus_step4_background_present));
                break;
            }
        }
    }

    private void loadStep5(CampaignMonth data) {
        //load step5 for 4 week
        txtIntroduce_step1.setText(data.data.campaigns.get(0).currentReLead + "/"
                + data.data.campaigns.get(0).targetReLead);
        txtIntroduce_step2.setText(data.data.campaigns.get(1).currentReLead + "/"
                + data.data.campaigns.get(1).targetReLead);
        txtIntroduce_step3.setText(data.data.campaigns.get(2).currentReLead + "/"
                + data.data.campaigns.get(2).targetReLead);
        txtIntroduce_step4.setText(data.data.campaigns.get(3).currentReLead + "/"
                + data.data.campaigns.get(3).targetReLead);

        switch (data.data.currentWeek) {
            case 1: {
                txtIntroduce_step1.setTextColor(Color.parseColor("#FFFFFF"));
                txtIntroduce_step1_title.setTextColor(Color.parseColor("#FFFFFF"));

                layoutaIntroduceStep1.setBackground(getResources().getDrawable(R.drawable.cus_step5_background_present));
                layoutaIntroduceStep2.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                layoutaIntroduceStep3.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                layoutaIntroduceStep4.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                break;
            }
            case 2: {
                txtIntroduce_step2.setTextColor(Color.parseColor("#FFFFFF"));
                txtIntroduce_step2_title.setTextColor(Color.parseColor("#FFFFFF"));
                if (data.data.campaigns.get(0).targetReLead == data.data.campaigns.get(0).currentReLead) {
                    imgIntroduce_step1.setAlpha(1f);
                }
                layoutaIntroduceStep1.setBackground(getResources().getDrawable(R.drawable.cus_step5_background_previous));
                layoutaIntroduceStep2.setBackground(getResources().getDrawable(R.drawable.cus_step5_background_present));
                layoutaIntroduceStep3.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                layoutaIntroduceStep4.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                break;
            }
            case 3: {
                txtIntroduce_step3.setTextColor(Color.parseColor("#FFFFFF"));
                txtIntroduce_step3_title.setTextColor(Color.parseColor("#FFFFFF"));
                if (data.data.campaigns.get(0).targetReLead == data.data.campaigns.get(0).currentReLead) {
                    imgIntroduce_step1.setAlpha(1f);
                }
                if (data.data.campaigns.get(1).targetReLead == data.data.campaigns.get(1).currentReLead) {
                    imgIntroduce_step2.setAlpha(1f);
                }
                layoutaIntroduceStep1.setBackground(getResources().getDrawable(R.drawable.cus_step5_background_previous));
                layoutaIntroduceStep2.setBackground(getResources().getDrawable(R.drawable.cus_step5_background_previous));
                layoutaIntroduceStep3.setBackground(getResources().getDrawable(R.drawable.cus_step5_background_present));
                layoutaIntroduceStep4.setBackground(getResources().getDrawable(R.drawable.cus_step1_background_next));
                break;
            }
            case 4: {
                txtIntroduce_step4.setTextColor(Color.parseColor("#FFFFFF"));
                txtIntroduce_step4_title.setTextColor(Color.parseColor("#FFFFFF"));
                if (data.data.campaigns.get(0).targetReLead == data.data.campaigns.get(0).currentReLead) {
                    imgIntroduce_step1.setAlpha(1f);
                }
                if (data.data.campaigns.get(1).targetReLead == data.data.campaigns.get(1).currentReLead) {
                    imgIntroduce_step2.setAlpha(1f);
                }
                if (data.data.campaigns.get(2).targetReLead == data.data.campaigns.get(2).currentReLead) {
                    imgIntroduce_step3.setAlpha(1f);
                }
                layoutaIntroduceStep1.setBackground(getResources().getDrawable(R.drawable.cus_step5_background_previous));
                layoutaIntroduceStep2.setBackground(getResources().getDrawable(R.drawable.cus_step5_background_previous));
                layoutaIntroduceStep3.setBackground(getResources().getDrawable(R.drawable.cus_step5_background_previous));
                layoutaIntroduceStep4.setBackground(getResources().getDrawable(R.drawable.cus_step5_background_present));
                break;
            }
        }
    }

    @OnClick(R.id.txt_edit_week)
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.txt_edit_week: {
                ((FAContentCustomerFragment)this.getParentFragment()).showDialogEditObjectMonth();
                break;
            }
        }
    }
}
