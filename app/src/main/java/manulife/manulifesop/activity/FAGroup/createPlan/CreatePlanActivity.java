package manulife.manulifesop.activity.FAGroup.createPlan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

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
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.circle_indicator_pager)
    CircleIndicatorPager indicator;

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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
