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
            Toast.makeText(mActivity, "load more", Toast.LENGTH_SHORT).show();
            initActiHistList();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //mActionListener.getDataDashboard();
        initView();
        initViewPager();
        initActiHistList();
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


    }

    private void initViewPager() {
        viewPager.setSwipe(false);

        mListFragment = new ArrayList<>();
        mListFragment.add(CampaignPercentFragment.newInstance("week"));
        mListFragment.add(CampaignPercentFragment.newInstance("month"));
        mListFragment.add(CampaignPercentFragment.newInstance("year"));

        mTabTitles = new ArrayList<>();
        mTabTitles.add("Tuần này");
        mTabTitles.add("Tháng 12");
        mTabTitles.add("Năm 2017");

        mAdapterViewPager = new CustomViewPagerAdapter(getChildFragmentManager(), mListFragment, mTabTitles);
        if (viewPager != null) {
            viewPager.setAdapter(mAdapterViewPager);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    private void initActiHistList() {
        listActiHist.setLayoutManager(mLayoutManager);

        for (int i = 0; i < 10; i++) {
            ActiveHistFA temp = new ActiveHistFA();
            temp.setAvatar("avatar " + i);
            temp.setTitle("title code input " + i);
            temp.setContent("content code input " + i);
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
        listActiHist.addOnScrollListener(new EndlessScrollListenerRecyclerView(
                0, 3, new onLoadingMoreDataTask(), mLayoutManager));

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
