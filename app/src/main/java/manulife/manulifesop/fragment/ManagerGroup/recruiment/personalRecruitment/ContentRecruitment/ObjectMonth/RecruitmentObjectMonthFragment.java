package manulife.manulifesop.fragment.ManagerGroup.recruiment.personalRecruitment.ContentRecruitment.ObjectMonth;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.appointment.AppointmentActivity;
import manulife.manulifesop.activity.FAGroup.clients.consultant.ConsultantActivity;
import manulife.manulifesop.activity.FAGroup.clients.contact.ContactPersonActivity;
import manulife.manulifesop.activity.FAGroup.clients.introduceContact.IntroduceContactActivity;
import manulife.manulifesop.activity.FAGroup.clients.signed.SignedPersonActivity;
import manulife.manulifesop.activity.ManagerGroup.Recruitment.cop.COPActivity;
import manulife.manulifesop.activity.ManagerGroup.Recruitment.grantedCode.GrantedCodeActivity;
import manulife.manulifesop.activity.ManagerGroup.Recruitment.introduceRecruitment.IntroduceRecruitmentActivity;
import manulife.manulifesop.activity.ManagerGroup.Recruitment.mit.MITActivity;
import manulife.manulifesop.activity.ManagerGroup.Recruitment.survey.SurveyActivity;
import manulife.manulifesop.activity.main.MainFAActivity;
import manulife.manulifesop.api.ObjectResponse.CampaignMonth;
import manulife.manulifesop.api.ObjectResponse.CampaignRecruitMonth;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class RecruitmentObjectMonthFragment extends BaseFragment<MainFAActivity, RecruitmentObjectMonthPresent> implements RecruitmentObjectMonthContract.View {

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

    private CampaignRecruitMonth mData;
    private int mMonth;

    private int targetStep1 = 0, targetStep2 = 0, targetStep3 = 0, targetStep4 = 0, targetStep5 = 0;


    public static RecruitmentObjectMonthFragment newInstance(CampaignRecruitMonth data, int month) {
        Bundle args = new Bundle();
        args.putSerializable("data", data);
        args.putInt("month", month);
        RecruitmentObjectMonthFragment fragment = new RecruitmentObjectMonthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_sm_recruitment_content_month;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new RecruitmentObjectMonthPresent(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mData = (CampaignRecruitMonth) getArguments().getSerializable("data");
        mMonth = getArguments().getInt("month", 0);
        initViews(mData);
    }

    private void initViews(CampaignRecruitMonth data) {

        targetStep1 = 0; targetStep2 = 0; targetStep3 = 0; targetStep4 = 0; targetStep5 = 0;
        if (data != null && data.statusCode == 1) {
            //int currentStep1 = 0, currentStep2 = 0, currentStep3 = 0, currentStep4 = 0, currentStep5 = 0;
            io.reactivex.Observable.just(data)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.newThread())
                    .map(CampaignMonth -> {
                        List<Integer> dataRS = new ArrayList<>();
                        dataRS.add(0);
                        dataRS.add(0);
                        dataRS.add(0);
                        dataRS.add(0);
                        dataRS.add(0);
                        for (int i = 0; i < data.data.campaigns.size(); i++) {
                            targetStep1 += data.data.campaigns.get(i).targetSurvey;
                            dataRS.set(0, dataRS.get(0) + data.data.campaigns.get(i).currentSurvey);

                            targetStep2 += data.data.campaigns.get(i).targetCop;
                            dataRS.set(1, dataRS.get(1) + data.data.campaigns.get(i).currentCop);

                            targetStep3 += data.data.campaigns.get(i).targetMit;
                            dataRS.set(2, dataRS.get(2) + data.data.campaigns.get(i).currentMit);

                            targetStep4 += data.data.campaigns.get(i).targetAgentCode;
                            dataRS.set(3, dataRS.get(3) + data.data.campaigns.get(i).currentAgentCode);

                            targetStep5 += data.data.campaigns.get(i).targetReLeadRecruit;
                            dataRS.set(4, dataRS.get(4) + data.data.campaigns.get(i).currentReleadRecruit);
                        }
                        return dataRS;
                    }).subscribe(integers -> {
                txtStep1.setText(integers.get(0) + "/" + targetStep1);
                txtStep2.setText(integers.get(1) + "/" + targetStep2);
                txtStep3.setText(integers.get(2) + "/" + targetStep3);
                txtStep4.setText(integers.get(3) + "/" + targetStep4);
                txtStep5.setText(integers.get(4) + "/" + targetStep5);
            });
        }
    }

    @OnClick({R.id.layout_step1, R.id.layout_step2, R.id.layout_step3, R.id.layout_step4, R.id.layout_step5})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_step1: {
                if (Utils.getCurrentMonth(getContext()) == mMonth) {
                    Bundle data = new Bundle();
                    data.putInt("month", mMonth);
                    data.putInt("target", targetStep1);
                    data.putInt("targetIntroduce", targetStep5);
                    mActivity.goNextScreen(SurveyActivity.class, data);
                }
                break;
            }
            case R.id.layout_step2: {
                if (Utils.getCurrentMonth(getContext()) == mMonth) {
                    Bundle data = new Bundle();
                    data.putInt("month", mMonth);
                    data.putInt("target", targetStep2);
                    mActivity.goNextScreen(COPActivity.class, data);
                }
                break;
            }
            case R.id.layout_step3: {
                if (Utils.getCurrentMonth(getContext()) == mMonth) {
                    Bundle data = new Bundle();
                    data.putInt("month", mMonth);
                    data.putInt("target", targetStep3);
                    mActivity.goNextScreen(MITActivity.class, data);
                }
                break;
            }
            case R.id.layout_step4: {
                if (Utils.getCurrentMonth(getContext()) == mMonth) {
                    Bundle data = new Bundle();
                    data.putInt("month", mMonth);
                    data.putInt("target", targetStep4);
                    mActivity.goNextScreen(GrantedCodeActivity.class, data);
                }
                break;
            }
            case R.id.layout_step5: {
                if (Utils.getCurrentMonth(getContext()) == mMonth) {
                    Bundle data = new Bundle();
                    data.putInt("month", mMonth);
                    data.putInt("target", targetStep5);
                    mActivity.goNextScreen(IntroduceRecruitmentActivity.class, data);
                }
                break;
            }

        }
    }

}
