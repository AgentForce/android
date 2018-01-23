package manulife.manulifesop.activity.FAGroup.createPlan;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import chick.indicator.CircleIndicatorPager;
import manulife.manulifesop.R;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.fragment.FAGroup.createPlane.CreatePlanStep1Fragment;


public class CreatePlanActivity extends BaseActivity<CreatePlanPresenter> implements CreatePlanContract.View {
    @BindView(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.circle_indicator_pager)
    CircleIndicatorPager indicator;
    @BindView(R.id.status_bar)
    View viewStatusBar;



    private List<BaseFragment> mListFragment;
    private CustomViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);
        mActionListener = new CreatePlanPresenter(this,this);
        setupSupportForApp();
        initViewPager();
    }

    private void setupSupportForApp() {
        txtActionbarTitle.setText(getResources().getString(R.string.activity_main_fa_dashboard));

        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewStatusBar.getLayoutParams();
        params.height = statusBarHeight;
        viewStatusBar.setLayoutParams(params);

    }
    private void initViewPager()
    {
        mListFragment = new ArrayList<>();
        mListFragment.add(CreatePlanStep1Fragment.newInstance());
        mListFragment.add(CreatePlanStep1Fragment.newInstance());
        mListFragment.add(CreatePlanStep1Fragment.newInstance());

        mAdapter = new CustomViewPagerAdapter(getSupportFragmentManager(), mListFragment);
        if (viewPager != null) {
            viewPager.setAdapter(mAdapter);
        }
        if (indicator != null) {
            indicator.setViewPager(viewPager);
        }
    }
}
