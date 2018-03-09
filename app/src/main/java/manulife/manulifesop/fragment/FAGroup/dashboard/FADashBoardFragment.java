package manulife.manulifesop.fragment.FAGroup.dashboard;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.main.MainFAActivity;
import manulife.manulifesop.adapter.ActiveHistAdapter;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.adapter.ObjectData.ActiveHistFA;
import manulife.manulifesop.api.ObjectResponse.ActivitiHist;
import manulife.manulifesop.api.ObjectResponse.DashboardResult;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.CustomViewPager;
import manulife.manulifesop.element.callbackInterface.CallBackClickContact;
import manulife.manulifesop.fragment.FAGroup.dashboard.campaignPercent.CampaignPercentFragment;
import manulife.manulifesop.util.EndlessScrollListenerRecyclerView;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class FADashBoardFragment extends BaseFragment<MainFAActivity, FADashBoardPresent> implements FADashBoardContract.View {

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

    private CustomViewPagerAdapter mAdapterViewPager;
    private List<BaseFragment> mListFragment;
    private List<String> mTabTitles;

    //variable for activity hist
    private ActiveHistAdapter mAdapterActiveHist;
    private List<ActiveHistFA> mDataActiveHist;
    private LinearLayoutManager mLayoutManager;


    public static FADashBoardFragment newInstance() {
        Bundle args = new Bundle();
        FADashBoardFragment fragment = new FADashBoardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_fa_dashboard;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new FADashBoardPresent(this);
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
        //mActionListener.getDataDashboard();
        mActivity.showHideActionbar(true);
        mActivity.updateActionbarTitle("Trang chủ");
        initView();
        mActionListener.getDataDashboard();
        //initViewPager();
        //initActiHistList();
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        //init data
        mDataActiveHist = new ArrayList<>();
    }

    @Override
    public void showDataDashboard(DashboardResult dataWeekMonth, DashboardResult dataYear, ActivitiHist activities) {
        int step1 = 0, step2 = 0, step3 = 0, step4 = 0, step5 = 0;
        for (int i = 0; i < dataWeekMonth.data.campaign.size(); i++) {
            step1 += dataWeekMonth.data.campaign.get(i).currentCallSale;
            step2 += dataWeekMonth.data.campaign.get(i).currentMetting;
            step3 += dataWeekMonth.data.campaign.get(i).currentPresentation;
            step4 += dataWeekMonth.data.campaign.get(i).currentContract;
            step5 += dataWeekMonth.data.campaign.get(i).currentReLead;
        }
        txtStep1.setText(String.valueOf(step1));
        txtStep2.setText(String.valueOf(step2));
        txtStep3.setText(String.valueOf(step3));
        txtStep4.setText(String.valueOf(step4));
        txtStep5.setText(String.valueOf(step5));

        initViewPager(dataWeekMonth,dataYear);
        showACtivities(activities);
    }

    private void initViewPager(DashboardResult dataWeekMonth, DashboardResult dataYear) {
        viewPager.setSwipe(false);

        int currentCallSale = 0,currentMetting =0, currentPresentation =0,
                currentContractSale =0,currentReLead =0;
        int targetCallSale = 0,targetMetting =0, targetPresentation =0,
                targetContractSale =0,targetReLead =0;
        int percentTemp;

        List<Integer> percentCurrentWeek = new ArrayList<>();
        List<Integer> percentMonth = new ArrayList<>();
        List<Integer> percentYear = new ArrayList<>();
        for(int i=0;i<dataWeekMonth.data.campaign.size();i++)
        {
            if(dataWeekMonth.data.currentWeek == dataWeekMonth.data.campaign.get(i).week){
                //lien he
                percentTemp = Math.round((float)(dataWeekMonth.data.campaign.get(i).currentCallSale*100)/dataWeekMonth.data.campaign.get(i).targetCallSale);
                percentCurrentWeek.add(percentTemp);
                //hen gap
                percentTemp = Math.round((float)(dataWeekMonth.data.campaign.get(i).currentMetting*100)/dataWeekMonth.data.campaign.get(i).targetMetting);
                percentCurrentWeek.add(percentTemp);
                //tu van
                percentTemp = Math.round((float)(dataWeekMonth.data.campaign.get(i).currentPresentation*100)/dataWeekMonth.data.campaign.get(i).targetPresentation);
                percentCurrentWeek.add(percentTemp);
                //ky hop dong
                percentTemp = Math.round((float)(dataWeekMonth.data.campaign.get(i).currentContract*100)/dataWeekMonth.data.campaign.get(i).targetContractSale);
                percentCurrentWeek.add(percentTemp);
                //gioi thie
                percentTemp = Math.round((float)(dataWeekMonth.data.campaign.get(i).currentReLead*100)/dataWeekMonth.data.campaign.get(i).targetReLead);
                percentCurrentWeek.add(percentTemp);
            }
            currentCallSale += dataWeekMonth.data.campaign.get(i).currentCallSale;
            targetCallSale += dataWeekMonth.data.campaign.get(i).targetCallSale;

            currentMetting += dataWeekMonth.data.campaign.get(i).currentMetting;
            targetMetting += dataWeekMonth.data.campaign.get(i).targetMetting;

            currentPresentation += dataWeekMonth.data.campaign.get(i).currentPresentation;
            targetPresentation += dataWeekMonth.data.campaign.get(i).targetPresentation;

            currentContractSale += dataWeekMonth.data.campaign.get(i).currentContract;
            targetContractSale += dataWeekMonth.data.campaign.get(i).targetContractSale;

            currentReLead += dataWeekMonth.data.campaign.get(i).currentReLead;
            targetReLead += dataWeekMonth.data.campaign.get(i).targetReLead;

        }

        //generate pervent month
        percentTemp = Math.round((float)(currentCallSale*100)/targetCallSale);
        percentMonth.add(percentTemp);
        percentTemp = Math.round((float)(currentMetting*100)/targetMetting);
        percentMonth.add(percentTemp);
        percentTemp = Math.round((float)(currentPresentation*100)/targetPresentation);
        percentMonth.add(percentTemp);
        percentTemp = Math.round((float)(currentContractSale*100)/targetContractSale);
        percentMonth.add(percentTemp);
        percentTemp = Math.round((float)(currentReLead*100)/targetReLead);
        percentMonth.add(percentTemp);

        //generate percent year
        //lien he
        percentTemp = Math.round((float)(dataYear.data.campaign.get(0).currentCallSale*100)/dataYear.data.campaign.get(0).targetCallSale);
        percentYear.add(percentTemp);
        //hen gap
        percentTemp = Math.round((float)(dataYear.data.campaign.get(0).currentMetting*100)/dataYear.data.campaign.get(0).targetMetting);
        percentYear.add(percentTemp);
        //tu van
        percentTemp = Math.round((float)(dataYear.data.campaign.get(0).currentPresentation*100)/dataYear.data.campaign.get(0).targetPresentation);
        percentYear.add(percentTemp);
        //ky hop dong
        percentTemp = Math.round((float)(dataYear.data.campaign.get(0).currentContract*100)/dataYear.data.campaign.get(0).targetContractSale);
        percentYear.add(percentTemp);
        //gioi thie
        percentTemp = Math.round((float)(dataYear.data.campaign.get(0).currentReLead*100)/dataYear.data.campaign.get(0).targetReLead);
        percentYear.add(percentTemp);

        mListFragment = new ArrayList<>();
        mListFragment.add(CampaignPercentFragment.newInstance("week",percentCurrentWeek));
        mListFragment.add(CampaignPercentFragment.newInstance("month",percentMonth));
        mListFragment.add(CampaignPercentFragment.newInstance("year",percentYear));

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

    @Override
    public void showACtivities(ActivitiHist activities) {
        listActiHist.setLayoutManager(mLayoutManager);

        String dateCreated;

        for (int i = 0; i < activities.data.rows.size(); i++) {
            ActiveHistFA temp = new ActiveHistFA();

            dateCreated = Utils.convertStringDateToStringDate(activities.data.rows.get(i).createdAt,
                    "yyyy-MM-dd'T'HH:mm:ss.sss'Z'","dd-MM-yyyy HH:mm:ss");

            temp.setAvatar("avatar " + i);
            temp.setTitle(activities.data.rows.get(i).name);
            temp.setContent(activities.data.rows.get(i).description + " - " +dateCreated);
            mDataActiveHist.add(temp);
        }
        if (mAdapterActiveHist == null) {
            mAdapterActiveHist = new ActiveHistAdapter(getContext(), mDataActiveHist, new CallBackClickContact() {
                @Override
                public void onClickMenuRight(int position, int option) {
                    Toast.makeText(mActivity, "Vi tri " + position + " option " + option, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onClickMainContent(int position) {
                    Toast.makeText(mActivity, mDataActiveHist.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            listActiHist.setAdapter(mAdapterActiveHist);
        } else {
            mAdapterActiveHist.notifyDataSetChanged();
        }

        //set space between two items
        int[] ATTRS = new int[]{android.R.attr.listDivider};
        TypedArray a = getContext().obtainStyledAttributes(ATTRS);
        Drawable divider = a.getDrawable(0);
        int insetLeft = getResources().getDimensionPixelSize(R.dimen.margin_left_DividerItemDecoration);
        int insetRight = getResources().getDimensionPixelSize(R.dimen.margin_right_DividerItemDecoration);
        InsetDrawable insetDivider = new InsetDrawable(divider, insetLeft, 0, insetRight, 0);
        a.recycle();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(listActiHist.getContext(),
                mLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(insetDivider);
        listActiHist.addItemDecoration(dividerItemDecoration);

        listActiHist.clearOnScrollListeners();
        /*listActiHist.addOnScrollListener(new EndlessScrollListenerRecyclerView(
                activities.data.page, genLastPage(activities.data.count,activities.data.limit), new onLoadingMoreDataTask(), mLayoutManager));*/
        listActiHist.addOnScrollListener(new EndlessScrollListenerRecyclerView(
                activities.data.page, 3, new onLoadingMoreDataTask(), mLayoutManager));
    }

    private int genLastPage(int count, int limit){
        int rs;
        if(count%limit > 0){
            rs = (count/limit) + 1;
        }else{
            rs = count/limit;
        }
        return rs;
    }

    /*@OnClick(R.id.btn_start)
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.btn_start:
            {
                mActivity.goNextScreen(CreatePlanActivity.class);
                break;
            }
        }
    }*/

}
