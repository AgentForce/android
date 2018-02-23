package manulife.manulifesop.fragment.FAGroup.dashboard.campaignPercent;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chick.indicator.CircleIndicatorPager;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.createPlan.CreatePlanActivity;
import manulife.manulifesop.activity.FAGroup.main.MainFAActivity;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.fragment.FAGroup.confirmCreatePlan.ConfirmCreatePlanContract;
import manulife.manulifesop.fragment.FAGroup.confirmCreatePlan.ConfirmCreatePlanFragment;
import manulife.manulifesop.fragment.FAGroup.confirmCreatePlan.ConfirmCreatePlanPresent;
import manulife.manulifesop.fragment.FAGroup.dashboard.campaignPercent.onePercent.OnePercentFragment;

/**
 * Created by Chick on 10/27/2017.
 */

public class CampaignPercentFragment extends BaseFragment<MainFAActivity,CampaignPercentPresent> implements CampaignPercentContract.View {

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.circle_indicator_pager)
    CircleIndicatorPager indicator;

    private CustomViewPagerAdapter mAdapter;
    private List<BaseFragment> mListFragment;

    public static CampaignPercentFragment newInstance() {
        Bundle args = new Bundle();
        CampaignPercentFragment fragment = new CampaignPercentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_dashboard_fa_percent_campaign;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new CampaignPercentPresent(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewpager();
    }

    private void initViewpager()
    {
        mListFragment = new ArrayList<>();
        mListFragment.add(OnePercentFragment.newInstance("Liên hệ",60,getResources().getColor(R.color.color_dashboard_contact)));
        mListFragment.add(OnePercentFragment.newInstance("Hẹn gặp",50,getResources().getColor(R.color.color_dashboard_meeting)));
        mListFragment.add(OnePercentFragment.newInstance("Tư vấn",40,getResources().getColor(R.color.color_dashboard_advisory)));
        mListFragment.add(OnePercentFragment.newInstance("Ký hợp đồng",30,getResources().getColor(R.color.color_dashboard_sign)));
        mListFragment.add(OnePercentFragment.newInstance("Giới thiệu",20,getResources().getColor(R.color.color_dashboard_introduce)));

        //create list color indicator
        List<Integer> listBackground = new ArrayList<>();
        listBackground.add(R.drawable.step1_radius);
        listBackground.add(R.drawable.step2_radius);
        listBackground.add(R.drawable.step3_radius);
        listBackground.add(R.drawable.step4_radius);
        listBackground.add(R.drawable.step5_radius);

        mAdapter = new CustomViewPagerAdapter(getChildFragmentManager(), mListFragment);

        if (viewPager != null) {
            viewPager.setAdapter(mAdapter);
        }
        if (indicator != null) {
            indicator.setViewPager(viewPager,listBackground);
        }
    }

}
