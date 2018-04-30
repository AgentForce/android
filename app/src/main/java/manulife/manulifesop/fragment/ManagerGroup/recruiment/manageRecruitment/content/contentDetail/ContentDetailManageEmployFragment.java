package manulife.manulifesop.fragment.ManagerGroup.recruiment.manageRecruitment.content.contentDetail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.main.MainFAActivity;
import manulife.manulifesop.adapter.ObjectData.RecruitmentDirectlyData;
import manulife.manulifesop.adapter.RecruitDirectlytAdapter;
import manulife.manulifesop.api.ObjectResponse.RecruitmentDashboard;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.callbackInterface.CallBackClickContact;
import manulife.manulifesop.fragment.ManagerGroup.recruiment.manageRecruitment.content.ContentManageEmployFragment;
import manulife.manulifesop.util.EndlessScrollListenerRecyclerView;
import manulife.manulifesop.util.SOPSharedPreferences;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class ContentDetailManageEmployFragment extends BaseFragment<MainFAActivity, ContentDetailManageEmployPresent> implements ContentDetailManageEmployContract.View {

    //varialbe
    @BindView(R.id.expandable_layout_mid)
    ExpandableLayout expandableLayoutMid;
    @BindView(R.id.expandable_layout_list)
    ExpandableLayout expandableLayoutList;
    @BindView(R.id.rcv_data)
    RecyclerView rcvData;
    @BindView(R.id.layout_bot)
    LinearLayout layoutBot;
    @BindView(R.id.layout_mid)
    LinearLayout layoutMid;
    @BindView(R.id.layout_mid_include)
    LinearLayout layoutMidInclude;
    @BindView(R.id.layout_title_bot)
    LinearLayout layoutTitleBot;
    @BindView(R.id.img_show_contacts)
    ImageView imgShowContacts;

    @BindView(R.id.txt_survey_progress)
    TextView txtSurveyProgress;
    @BindView(R.id.txt_cop_progress)
    TextView txtCopProgress;
    @BindView(R.id.txt_mit_progress)
    TextView txtMitProgress;
    @BindView(R.id.txt_code_progress)
    TextView txtCodeProgress;
    @BindView(R.id.txt_introduce_progress)
    TextView txtIntroduceProgress;

    @BindView(R.id.progress_survey)
    ProgressBar progressSurvey;
    @BindView(R.id.progress_cop)
    ProgressBar progressCop;
    @BindView(R.id.progress_mit)
    ProgressBar progressMit;
    @BindView(R.id.progress_code)
    ProgressBar progressCode;
    @BindView(R.id.progress_introduce)
    ProgressBar progressIntroduce;


    private int mProcessStep;
    private String mType;

    private boolean isCreatedHeight = false;

    private RecruitDirectlytAdapter mAdapter;
    private List<RecruitmentDirectlyData> mData;
    private LinearLayoutManager mLayoutManager;

    private int mUserID;
    private int mUserIDParent;
    private int mUserIDProcessing;

    public static ContentDetailManageEmployFragment newInstance(int processStep, String type) {
        Bundle args = new Bundle();
        args.putInt("processStep", processStep);
        args.putString("type", type);
        ContentDetailManageEmployFragment fragment = new ContentDetailManageEmployFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private final class onLoadingMoreDataTask implements EndlessScrollListenerRecyclerView.onActionListViewScroll {

        @Override
        public void onApiLoadMoreTask(int page) {
            Toast.makeText(mActivity, "load more", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_sm_employ_content;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new ContentDetailManageEmployPresent(this, getContext());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProcessStep = getArguments().getInt("processStep", 1);
        mType = getArguments().getString("type", "");
        isCreatedHeight = false;
        initHeightViaSelected();
        initViews();

        mData.clear();
        mAdapter = null;
        getDataViaType(SOPSharedPreferences.getInstance(getContext()).getUserID(), mType, mProcessStep);
    }

    private void getDataViaType(int userID, String type, int processStep) {
        int month, week;
        if (type.equals("month")) {
            month = Utils.getCurrentMonth(getContext());
            week = processStep;
        } else if (type.equals("year")) {
            month = processStep;
            week = -1;
        } else {
            month = -1;
            week = -1;
        }
        mUserID = userID;
        mActionListener.getRecruitDashBoard(userID, month, week);
    }

    private void initViews() {
        mData = new ArrayList<>();
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvData.setLayoutManager(mLayoutManager);
    }

    @Override
    public void showRecruitDashboard(RecruitmentDashboard data) {
        mData.clear();
        mAdapter = null;

        mUserIDParent = data.data.reportTo;
        mUserIDProcessing = mUserID;

        ((ContentManageEmployFragment) this.getParentFragment()).setManagerName(data.data.fullName);
        txtSurveyProgress.setText(data.data.currentSurvey + "/" + data.data.targetSurvey);
        txtCopProgress.setText(data.data.currentCop + "/" + data.data.targetCop);
        txtMitProgress.setText(data.data.currentMit + "/" + data.data.targetMit);
        txtCodeProgress.setText(data.data.currentAgentCode + "/" + data.data.targetAgentCode);
        txtIntroduceProgress.setText(data.data.currentReLeadRecruit + "/" + data.data.targetReLeadRecruit);

        int temp = (data.data.targetSurvey > 0) ? (data.data.currentSurvey / data.data.targetSurvey) * 100 : 0;
        progressSurvey.setProgress(temp);

        temp = (data.data.targetCop > 0) ? (data.data.currentCop / data.data.targetCop) * 100 : 0;
        progressCop.setProgress(temp);

        temp = (data.data.targetMit > 0) ? (data.data.currentMit / data.data.targetMit) * 100 : 0;
        progressMit.setProgress(temp);

        temp = (data.data.targetAgentCode > 0) ? (data.data.currentAgentCode / data.data.targetAgentCode) * 100 : 0;
        progressCode.setProgress(temp);

        temp = (data.data.targetReLeadRecruit > 0) ? (data.data.currentReLeadRecruit / data.data.targetReLeadRecruit) * 100 : 0;
        progressIntroduce.setProgress(temp);
    }

    @Override
    public void showRecruitDashboardDirectly(List<RecruitmentDirectlyData> data, int currentPage, int lastPage) {
        mData.addAll(data);
        if (mAdapter == null) {
            mAdapter = new RecruitDirectlytAdapter(getContext(), data, true ,new CallBackClickContact() {
                @Override
                public void onClickMenuRight(int position, int option) {

                }

                @Override
                public void onClickMainContent(int position) {
                    //Toast.makeText(mActivity, "click " + position + mData.get(position).getId(), Toast.LENGTH_SHORT).show();
                    int id = mData.get(position).getId();
                    getDataViaType(id, mType, mProcessStep);
                }
            });
            rcvData.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

        rcvData.clearOnScrollListeners();
        rcvData.addOnScrollListener(new EndlessScrollListenerRecyclerView(
                currentPage
                , lastPage
                , new onLoadingMoreDataTask(), mLayoutManager));
    }

    @Override
    public void initHeightViaSelected() {
        int pageSelected = ((ContentManageEmployFragment) getParentFragment()).getParrentSelectedPage();
        switch (mType) {
            case "month": {
                if (pageSelected == 0) {
                    initviewsHeight();
                }
                break;
            }
            case "year": {
                if (pageSelected == 1) {
                    initviewsHeight();
                }
                break;
            }
            case "campaign": {
                if (pageSelected == 2) {
                    initviewsHeight();
                }
                break;
            }
        }
    }

    @Override
    public void initviewsHeight() {
        if (!isCreatedHeight) {
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
                            LinearLayout.LayoutParams layoutParams2 =
                                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, layoutMidInclude.getHeight() - layoutTitleBot.getHeight());
                            rcvData.setLayoutParams(layoutParams2);
                        }
                    });
                }
            }
        }
    }

    @OnClick({R.id.layout_title_bot})
    public void onClickView(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_title_bot: {
                if (expandableLayoutMid.isExpanded()) {
                    expandableLayoutMid.collapse(true);
                    expandableLayoutList.expand(true);
                    imgShowContacts.setBackgroundResource(R.drawable.ic_arrow_down);
                } else {
                    expandableLayoutMid.expand(true);
                    expandableLayoutList.collapse(true);
                    imgShowContacts.setBackgroundResource(R.drawable.ic_arrow_up);
                }
                break;
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        if(event.equals("back to report to in ContentDetailManageEmployFragment"))
        {
            int pageSelected = ((ContentManageEmployFragment) getParentFragment()).getParrentSelectedPage();
            if(pageSelected == 0 && mType.equals("month")) {
                getDataViaType(mUserIDParent, mType, mProcessStep);
            }else if(pageSelected == 1 && mType.equals("year")){
                getDataViaType(mUserIDParent, mType, mProcessStep);
            }else if(pageSelected == 2 && mType.equals("campaign")){
                getDataViaType(mUserIDParent, mType, mProcessStep);
            }
        }
    }

    @Override
    public int getUserIDProcessing() {
        return mUserIDProcessing;
    }
}
