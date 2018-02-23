package manulife.manulifesop.fragment.FAGroup.dashboard.campaignPercent.onePercent;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import chick.circular_progress_bar.CircularProgressBar;
import chick.indicator.CircleIndicatorPager;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.main.MainFAActivity;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.fragment.FAGroup.confirmCreatePlan.ConfirmCreatePlanFragment;
import manulife.manulifesop.fragment.FAGroup.dashboard.campaignPercent.CampaignPercentContract;
import manulife.manulifesop.fragment.FAGroup.dashboard.campaignPercent.CampaignPercentPresent;

/**
 * Created by Chick on 10/27/2017.
 */

public class OnePercentFragment extends BaseFragment<MainFAActivity,OnePercentPresent> implements OnePercentContract.View {

    @BindView(R.id.circular_progress)
    CircularProgressBar circularProgressBar;

    private int mPercent;
    private String mSubTitle;
    private int mColor;


    public static OnePercentFragment newInstance(String subTitle,int percent,int color) {
        Bundle args = new Bundle();
        args.putInt("percent",percent);
        args.putString("subtitle",subTitle);
        args.putInt("color",color);
        OnePercentFragment fragment = new OnePercentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.item_percent_circle;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new OnePercentPresent(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mPercent = getArguments().getInt("percent",0);
        mSubTitle = getArguments().getString("subtitle","");
        mColor = getArguments().getInt("color", Color.parseColor("#bada55"));

        circularProgressBar.setTitle(mPercent + "%");
        circularProgressBar.setSubTitle(mSubTitle);
        circularProgressBar.setProgress(mPercent);
        circularProgressBar.setProgressColor(mColor);
    }

}