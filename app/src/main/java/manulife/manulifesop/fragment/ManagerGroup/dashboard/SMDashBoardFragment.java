package manulife.manulifesop.fragment.ManagerGroup.dashboard;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.appointment.AppointmentActivity;
import manulife.manulifesop.activity.FAGroup.clients.consultant.ConsultantActivity;
import manulife.manulifesop.activity.FAGroup.clients.contact.ContactPersonActivity;
import manulife.manulifesop.activity.FAGroup.clients.introduceContact.IntroduceContactActivity;
import manulife.manulifesop.activity.FAGroup.clients.related.contactDetail.ContactDetailActivity;
import manulife.manulifesop.activity.FAGroup.clients.related.createEvent.CreateEventActivity;
import manulife.manulifesop.activity.FAGroup.clients.signed.SignedPersonActivity;
import manulife.manulifesop.activity.ManagerGroup.Recruitment.cop.COPActivity;
import manulife.manulifesop.activity.ManagerGroup.Recruitment.grantedCode.GrantedCodeActivity;
import manulife.manulifesop.activity.ManagerGroup.Recruitment.introduceRecruitment.IntroduceRecruitmentActivity;
import manulife.manulifesop.activity.ManagerGroup.Recruitment.mit.MITActivity;
import manulife.manulifesop.activity.ManagerGroup.Recruitment.survey.SurveyActivity;
import manulife.manulifesop.activity.main.MainFAActivity;
import manulife.manulifesop.adapter.ActiveHistAdapter;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.adapter.ObjectData.ActiveHistFA;
import manulife.manulifesop.api.ObjectResponse.DashboardSMResult;
import manulife.manulifesop.api.ObjectResponse.RecruitHistory;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.CustomViewPager;
import manulife.manulifesop.element.callbackInterface.CallBackClickContact;
import manulife.manulifesop.element.callbackInterface.CallBackConfirmDialog;
import manulife.manulifesop.fragment.ManagerGroup.dashboard.SMcampaignPercent.SMCampaignPercentFragment;
import manulife.manulifesop.fragment.dashboard.DashboardFragment;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.EndlessScrollListenerRecyclerView;
import manulife.manulifesop.util.SOPSharedPreferences;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class SMDashBoardFragment extends BaseFragment<MainFAActivity, SMDashBoardPresent> implements SMDashBoardContract.View,
        View.OnClickListener {

    @BindView(R.id.txt_title)
    TextView txtTitle;

    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    CustomViewPager viewPager;
    @BindView(R.id.rcv_log)
    RecyclerView listActiHist;

    @BindView(R.id.txt_step1)
    TextView txtStep1;
    @BindView(R.id.txt_step2)
    TextView txtStep2;
    @BindView(R.id.txt_step3)
    TextView txtStep3;
    @BindView(R.id.txt_step4)
    TextView txtStep4;
    @BindView(R.id.txt_step5)
    TextView txtStep5;

    @BindView(R.id.layout_top)
    LinearLayout layoutTop;
    @BindView(R.id.layout_title_bot)
    LinearLayout layoutTitleBot;

    @BindView(R.id.expandable_layout_mid)
    ExpandableLayout layoutMid;
    @BindView(R.id.expandable_layout_list)
    ExpandableLayout layoutList;
    @BindView(R.id.layout_bot)
    LinearLayout layoutBot;

    @BindView(R.id.img_show_activities)
    ImageView imgShowActivities;


    private CustomViewPagerAdapter mAdapterViewPager;
    private List<BaseFragment> mListFragment;
    private List<String> mTabTitles;

    //variable for activity hist
    private ActiveHistAdapter mAdapterActiveHist;
    private List<ActiveHistFA> mDataActiveHist;
    private LinearLayoutManager mLayoutManager;

    //variable for click event in dashboard
    private int targetStep1 = 0, targetStep2 = 0, targetStep3 = 0, targetStep4 = 0, targetStep5 = 0;
    private int mMonth;
    private AlertDialog alertDialog;
    private int mContactID;
    private String mName;

    private boolean isCreatedHeight = false;

    public static SMDashBoardFragment newInstance() {
        Bundle args = new Bundle();
        SMDashBoardFragment fragment = new SMDashBoardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_sm_dashboard;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new SMDashBoardPresent(this, getContext());
    }

    private final class onLoadingMoreDataTask implements EndlessScrollListenerRecyclerView.onActionListViewScroll {

        @Override
        public void onApiLoadMoreTask(int page) {
            //Toast.makeText(mActivity, "load more", Toast.LENGTH_SHORT).show();
            showLoading("Lấy dữ liệu!");
            mActionListener.getActivities(page);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        isCreatedHeight = false;
        initHeightViaSelected();
    }

    private void initHeightViaSelected() {
        int pageSelected = ((DashboardFragment) getParentFragment()).getSelectedPage();
        if (pageSelected == 0) {
            initViewHeight();
        }
    }

    private void initView() {
        mMonth = Utils.getCurrentMonth(getContext());
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        //init data
        mDataActiveHist = new ArrayList<>();

        if (SOPSharedPreferences.getInstance(getContext()).getIsFA())
            txtTitle.setText("Khách hàng tháng " + (Calendar.getInstance().get(Calendar.MONTH) + 1));
        else txtTitle.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        mDataActiveHist.clear();
        mActionListener.getDataDashboard();
    }

    @Override
    public void finishLoadingMulti() {
        ((DashboardFragment)this.getParentFragment()).finishLoadingChild(0);
    }

    @Override
    public void updateData() {
        mActionListener.getDataDashboard();
    }

    @Override
    public void initViewHeight() {

        if(!isCreatedHeight) {
            isCreatedHeight = true;
            //set margin bottom for viewpager percent
            if (getView() != null) {
                final ViewTreeObserver observer = layoutBot.getViewTreeObserver();
                if (observer.isAlive()) {
                    observer.dispatchOnGlobalLayout(); // In case a previous call is waiting when this call is made
                    observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            observer.removeOnGlobalLayoutListener(this);
                            RelativeLayout.LayoutParams layoutParams =
                                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                            layoutParams.setMargins(0, 0, 0, layoutBot.getHeight());
                            layoutMid.setLayoutParams(layoutParams);
                            //set min height for lisview
                            FrameLayout.LayoutParams layoutParams2 =
                                    new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, layoutMid.getHeight() - layoutTitleBot.getHeight());
                            listActiHist.setLayoutParams(layoutParams2);
                        }
                    });
                }
            }
        }
    }


    @Override
    public void showDataDashboard(DashboardSMResult dataWeekMonth, DashboardSMResult dataYear, RecruitHistory activities) {
        targetStep1 = 0;
        targetStep2 = 0;
        targetStep3 = 0;
        targetStep4 = 0;
        targetStep5 = 0;

        int step1 = 0, step2 = 0, step3 = 0, step4 = 0, step5 = 0;
        if (dataWeekMonth.statusCode == 1) {
            for (int i = 0; i < dataWeekMonth.data.campaigns.size(); i++) {
                targetStep1 += dataWeekMonth.data.campaigns.get(i).targetSurvey;
                step1 += dataWeekMonth.data.campaigns.get(i).currentSurvey;

                targetStep2 += dataWeekMonth.data.campaigns.get(i).targetCop;
                step2 += dataWeekMonth.data.campaigns.get(i).currentCop;

                targetStep3 += dataWeekMonth.data.campaigns.get(i).targetMit;
                step3 += dataWeekMonth.data.campaigns.get(i).currentMit;

                targetStep4 += dataWeekMonth.data.campaigns.get(i).targetAgentCode;
                step4 += dataWeekMonth.data.campaigns.get(i).currentAgentCode;

                targetStep5 += dataWeekMonth.data.campaigns.get(i).targetReLeadRecruit;
                step5 += dataWeekMonth.data.campaigns.get(i).currentReLeadRecruit;
            }
        }
        txtStep1.setText(String.valueOf(step1));
        txtStep2.setText(String.valueOf(step2));
        txtStep3.setText(String.valueOf(step3));
        txtStep4.setText(String.valueOf(step4));
        txtStep5.setText(String.valueOf(step5));

        initViewPager(dataWeekMonth, dataYear);
        showACtivities(activities);
        //check active campaign in this month
        if (dataWeekMonth.data.isRequestActive == 1) {
            showConfirm("Thông báo", "Đồng ý kích hoạt mục tiêu tháng " + mMonth, "Đồng ý",
                    "Hủy", SweetAlertDialog.WARNING_TYPE, new CallBackConfirmDialog() {
                        @Override
                        public void DiaglogPositive() {
                            mActionListener.forwardCampaign();
                        }

                        @Override
                        public void DiaglogNegative() {

                        }
                    });
        }
    }

    private void initViewPager(DashboardSMResult dataWeekMonth, DashboardSMResult dataYear) {
        //viewPager.setSwipe(false);
        if (dataWeekMonth.statusCode == 1 && dataYear.statusCode == 1) {

            int currentStep1 = 0, currentStep2 = 0, currentStep3 = 0,
                    currentStep4 = 0, currentStep5 = 0;
            int targetStep1 = 0, targetStep2 = 0, targetStep3 = 0,
                    targetStep4 = 0, targetStep5 = 0;
            int percentTemp;

            List<Integer> percentCurrentWeek = new ArrayList<>();
            List<Integer> percentMonth = new ArrayList<>();
            List<Integer> percentYear = new ArrayList<>();
            List<Integer> percentYearForcast = new ArrayList<>();
            for (int i = 0; i < dataWeekMonth.data.campaigns.size(); i++) {
                if (dataWeekMonth.data.currentWeek == dataWeekMonth.data.campaigns.get(i).week) {
                    //lien he
                    percentTemp = Math.round((float) (dataWeekMonth.data.campaigns.get(i).currentSurvey * 100) / dataWeekMonth.data.campaigns.get(i).targetSurvey);
                    percentCurrentWeek.add(percentTemp);
                    //hen gap
                    percentTemp = Math.round((float) (dataWeekMonth.data.campaigns.get(i).currentCop * 100) / dataWeekMonth.data.campaigns.get(i).targetCop);
                    percentCurrentWeek.add(percentTemp);
                    //tu van
                    percentTemp = Math.round((float) (dataWeekMonth.data.campaigns.get(i).currentMit * 100) / dataWeekMonth.data.campaigns.get(i).targetMit);
                    percentCurrentWeek.add(percentTemp);
                    //ky hop dong
                    percentTemp = Math.round((float) (dataWeekMonth.data.campaigns.get(i).currentAgentCode * 100) / dataWeekMonth.data.campaigns.get(i).targetAgentCode);
                    percentCurrentWeek.add(percentTemp);
                    //gioi thie
                    percentTemp = Math.round((float) (dataWeekMonth.data.campaigns.get(i).currentReLeadRecruit * 100) / dataWeekMonth.data.campaigns.get(i).targetReLeadRecruit);
                    percentCurrentWeek.add(percentTemp);
                }
                currentStep1 += dataWeekMonth.data.campaigns.get(i).currentSurvey;
                targetStep1 += dataWeekMonth.data.campaigns.get(i).targetSurvey;

                currentStep2 += dataWeekMonth.data.campaigns.get(i).currentCop;
                targetStep2 += dataWeekMonth.data.campaigns.get(i).targetCop;

                currentStep3 += dataWeekMonth.data.campaigns.get(i).currentMit;
                targetStep3 += dataWeekMonth.data.campaigns.get(i).targetMit;

                currentStep4 += dataWeekMonth.data.campaigns.get(i).currentAgentCode;
                targetStep4 += dataWeekMonth.data.campaigns.get(i).targetAgentCode;

                currentStep5 += dataWeekMonth.data.campaigns.get(i).currentReLeadRecruit;
                targetStep5 += dataWeekMonth.data.campaigns.get(i).targetReLeadRecruit;



            }

            //generate pervent month
            percentTemp = Math.round((float) (currentStep1 * 100) / targetStep1);
            percentMonth.add(percentTemp);
            percentTemp = Math.round((float) (currentStep2 * 100) / targetStep2);
            percentMonth.add(percentTemp);
            percentTemp = Math.round((float) (currentStep3 * 100) / targetStep3);
            percentMonth.add(percentTemp);
            percentTemp = Math.round((float) (currentStep4 * 100) / targetStep4);
            percentMonth.add(percentTemp);
            percentTemp = Math.round((float) (currentStep5 * 100) / targetStep5);
            percentMonth.add(percentTemp);

            //generate percent year
            //lien he
            percentTemp = Math.round((float) (dataYear.data.campaigns.get(0).currentSurvey * 100) / dataYear.data.campaigns.get(0).targetSurvey);
            percentYear.add(percentTemp);
            percentTemp = Math.round((float) (dataYear.data.campaigns.get(0).forcastSurvey * 100) / dataYear.data.campaigns.get(0).targetSurvey);
            percentYearForcast.add(percentTemp);
            //hen gap
            percentTemp = Math.round((float) (dataYear.data.campaigns.get(0).currentCop * 100) / dataYear.data.campaigns.get(0).targetCop);
            percentYear.add(percentTemp);
            percentTemp = Math.round((float) (dataYear.data.campaigns.get(0).forcastCop * 100) / dataYear.data.campaigns.get(0).targetCop);
            percentYearForcast.add(percentTemp);
            //tu van
            percentTemp = Math.round((float) (dataYear.data.campaigns.get(0).currentMit * 100) / dataYear.data.campaigns.get(0).targetMit);
            percentYear.add(percentTemp);
            percentTemp = Math.round((float) (dataYear.data.campaigns.get(0).forcastMit * 100) / dataYear.data.campaigns.get(0).targetMit);
            percentYearForcast.add(percentTemp);
            //ky hop dong
            percentTemp = Math.round((float) (dataYear.data.campaigns.get(0).currentAgentCode * 100) / dataYear.data.campaigns.get(0).targetAgentCode);
            percentYear.add(percentTemp);
            percentTemp = Math.round((float) (dataYear.data.campaigns.get(0).forcastAgentCode * 100) / dataYear.data.campaigns.get(0).targetAgentCode);
            percentYearForcast.add(percentTemp);
            //gioi thie
            percentTemp = Math.round((float) (dataYear.data.campaigns.get(0).currentReLeadRecruit * 100) / dataYear.data.campaigns.get(0).targetReLeadRecruit);
            percentYear.add(percentTemp);
            percentTemp = Math.round((float) (dataYear.data.campaigns.get(0).forcastReLeadRecruit * 100) / dataYear.data.campaigns.get(0).targetReLeadRecruit);
            percentYearForcast.add(percentTemp);

            mListFragment = new ArrayList<>();
            mListFragment.add(SMCampaignPercentFragment.newInstance(percentCurrentWeek, null, "week", mMonth, dataWeekMonth));
            mListFragment.add(SMCampaignPercentFragment.newInstance(percentMonth, null, "month", mMonth, dataWeekMonth));
            mListFragment.add(SMCampaignPercentFragment.newInstance(percentYear, percentYearForcast, "year", mMonth, dataWeekMonth));

            mTabTitles = new ArrayList<>();
            mTabTitles.add("Tuần này");
            mTabTitles.add("Tháng " + (Calendar.getInstance().get(Calendar.MONTH) + 1));
            mTabTitles.add("Năm " + Calendar.getInstance().get(Calendar.YEAR));

            mAdapterViewPager = new CustomViewPagerAdapter(getChildFragmentManager(), mListFragment, mTabTitles);
            if (viewPager != null) {
                viewPager.setAdapter(mAdapterViewPager);
                tabLayout.setupWithViewPager(viewPager);
            }
        }
    }

    @Override
    public void showACtivities(RecruitHistory activities) {
        //mDataActiveHist.clear();
        listActiHist.setLayoutManager(mLayoutManager);

        for (int i = 0; i < activities.data.rows.size(); i++) {
            ActiveHistFA temp = new ActiveHistFA();

            temp.setAvatar("avatar " + i);
            temp.setTitle(activities.data.rows.get(i).name);
            temp.setContent(ProjectApplication.getInstance().getStringProcessStatusSM(
                    activities.data.rows.get(i).processStep + "" + activities.data.rows.get(i).statusProcessStep
            ));
            temp.setPhone(activities.data.rows.get(i).phone);
            temp.setProcessStatusName(ProjectApplication.getInstance().getStringProcessStatusNameSM(
                    activities.data.rows.get(i).processStep + "" + activities.data.rows.get(i).statusProcessStep
            ));
            temp.setProcessStep(activities.data.rows.get(i).processStep);
            temp.setId(activities.data.rows.get(i).id);
            mDataActiveHist.add(temp);
        }
        if (mAdapterActiveHist == null) {
            mAdapterActiveHist = new ActiveHistAdapter(getContext(), mDataActiveHist, new CallBackClickContact() {
                @Override
                public void onClickMenuRight(int position, int option) {
                    //Toast.makeText(mActivity, "Vi tri " + position + " option " + option, Toast.LENGTH_SHORT).show();
                    switch (option) {
                        case 0: {
                            Bundle data = new Bundle();
                            data.putString("type", mDataActiveHist.get(position).getProcessStatusName());
                            data.putString("type_menu", getTypeMenu(mDataActiveHist.get(position).getProcessStep()));
                            data.putInt("id", mDataActiveHist.get(position).getId());
                            mActivity.goNextScreen(ContactDetailActivity.class, data, Contants.CONTACT_DETAIL);
                            break;
                        }
                        case 1: {
                            String phone = "tel:" + mDataActiveHist.get(position).getPhone();
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse(phone));
                            startActivity(callIntent);
                            break;
                        }
                        case 2: {
                            mContactID = mDataActiveHist.get(position).getId();
                            mName = mDataActiveHist.get(position).getTitle();
                            showMenuCreateEvent();
                            break;
                        }
                    }
                }

                @Override
                public void onClickMainContent(int position) {
                    Bundle data = new Bundle();
                    data.putString("type", mDataActiveHist.get(position).getProcessStatusName());
                    data.putString("type_menu", getTypeMenu(mDataActiveHist.get(position).getProcessStep()));
                    data.putInt("id", mDataActiveHist.get(position).getId());
                    mActivity.goNextScreen(ContactDetailActivity.class, data, Contants.CONTACT_DETAIL);
                }
            });
            listActiHist.setAdapter(mAdapterActiveHist);
        } else {
            mAdapterActiveHist.notifyDataSetChanged();
        }

        listActiHist.clearOnScrollListeners();
        listActiHist.addOnScrollListener(new EndlessScrollListenerRecyclerView(
                activities.data.page, Utils.genLastPage(activities.data.count, activities.data.limit),
                new onLoadingMoreDataTask(), mLayoutManager));
    }

    private String getTypeMenu(int processStep) {
        String rs;
        switch (processStep) {
            case 1: {
                rs = Contants.SURVEY_MENU;
                break;
            }
            case 2: {
                rs = Contants.COP_MENU;
                break;
            }
            case 3: {
                rs = Contants.MIT_MENU;
                break;
            }
            case 4: {
                rs = Contants.CODE_MENU;
                break;
            }
            case 5: {
                rs = Contants.INTRODUCE_MENU_SM;
                break;
            }
            default:
                rs = Contants.SURVEY_MENU;
        }
        return rs;
    }

    @Override
    public void showMenuCreateEvent() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_create_event_contact_um, null);

        initDialogEvent(dialogView);

        dialogBuilder.setView(dialogView);

        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.getWindow().setGravity(Gravity.BOTTOM);

        alertDialog.show();
    }

    private void initDialogEvent(View view) {
        view.findViewById(R.id.txt_survey).setOnClickListener(this);
        view.findViewById(R.id.txt_cop).setOnClickListener(this);
        view.findViewById(R.id.txt_mit).setOnClickListener(this);
        view.findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.txt_survey: {
                Bundle data = new Bundle();
                data.putInt("typeInt", 7);
                data.putInt("contactID", mContactID);
                data.putString("name", mName);
                data.putBoolean("isRecruit",true);
                alertDialog.dismiss();
                goNextScreenFragment(CreateEventActivity.class, data, Contants.ADD_EVENT);
                break;
            }
            case R.id.txt_cop: {
                Bundle data = new Bundle();
                data.putInt("typeInt", 5);
                data.putInt("contactID", mContactID);
                data.putString("name", mName);
                data.putBoolean("isRecruit",true);
                alertDialog.dismiss();
                goNextScreenFragment(CreateEventActivity.class, data, Contants.ADD_EVENT);
                break;
            }
            case R.id.txt_mit: {
                Bundle data = new Bundle();
                data.putInt("typeInt", 6);
                data.putInt("contactID", mContactID);
                data.putString("name", mName);
                data.putBoolean("isRecruit",true);
                alertDialog.dismiss();
                goNextScreenFragment(CreateEventActivity.class, data, Contants.ADD_EVENT);
                break;
            }
            case R.id.btn_cancel: {
                alertDialog.dismiss();
                break;
            }
        }
    }

    @OnClick({R.id.layout_step1, R.id.layout_step2, R.id.layout_step3, R.id.layout_step4, R.id.layout_step5,
            R.id.layout_bot})
    public void onClickView(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_step1: {
                Bundle data = new Bundle();
                data.putInt("month", mMonth);
                data.putInt("target", targetStep1);
                data.putInt("targetIntroduce", targetStep5);
                mActivity.goNextScreen(SurveyActivity.class, data);
                break;
            }
            case R.id.layout_step2: {
                Bundle data = new Bundle();
                data.putInt("month", mMonth);
                data.putInt("target", targetStep2);
                mActivity.goNextScreen(COPActivity.class, data);
                break;
            }
            case R.id.layout_step3: {
                Bundle data = new Bundle();
                data.putInt("month", mMonth);
                data.putInt("target", targetStep3);
                mActivity.goNextScreen(MITActivity.class, data);
                break;
            }
            case R.id.layout_step4: {
                Bundle data = new Bundle();
                data.putInt("month", mMonth);
                data.putInt("target", targetStep4);
                mActivity.goNextScreen(GrantedCodeActivity.class, data);
                break;
            }
            case R.id.layout_step5: {
                Bundle data = new Bundle();
                data.putInt("month", mMonth);
                data.putInt("target", targetStep5);
                mActivity.goNextScreen(IntroduceRecruitmentActivity.class, data);
                break;
            }
            case R.id.layout_bot: {
                if (layoutMid.isExpanded()) {
                    layoutMid.collapse(true);
                    layoutList.expand(true);
                    imgShowActivities.setBackgroundResource(R.drawable.ic_arrow_down);
                } else {
                    layoutMid.expand(true);
                    layoutList.collapse(true);
                    imgShowActivities.setBackgroundResource(R.drawable.ic_arrow_up);
                }
                break;
            }
        }
    }

}
