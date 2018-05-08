package manulife.manulifesop.activity.FAGroup.personal.personalGoal.SM;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chick.indicator.CircleIndicatorPager;
import manulife.manulifesop.R;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.api.ObjectResponse.ForcastIncomeUM;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.CustomViewPager;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step8.screen.screen1.UMCreatePlanStep8Screen1Fragment;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step8.screen.screen2.UMCreatePlanStep8Screen2Fragment;


public class PersonalGoalSMActivity extends BaseActivity<PersonalGoalSMPresenter> implements PersonalGoalSMContract.View {

    @BindView(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @BindView(R.id.layout_btn_back)
    LinearLayout layoutBackButton;
    @BindView(R.id.status_bar)
    View viewStatusBar;

    @BindView(R.id.txt_start_date)
    TextView txtStartDate;
    @BindView(R.id.txt_end_date)
    TextView txtEndDate;
    @BindView(R.id.txt_num_campaign)
    TextView txtNumCampaign;
    @BindView(R.id.total_income)
    TextView txtTotalIncome;
    @BindView(R.id.total_income_monthly)
    TextView txtTotalIncomeMonthly;
    @BindView(R.id.view_pager)
    CustomViewPager viewPager;
    @BindView(R.id.circle_indicator_pager)
    CircleIndicatorPager indicator;

    private List<BaseFragment> mListFragment;
    private CustomViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_goal_sm);
        mActionListener = new PersonalGoalSMPresenter(this, this);
        setupSupportForApp();
        test();
    }

    private void test(){
        ForcastIncomeUM data = new ForcastIncomeUM();
        data.statusCode= 0;
        data.msg = "thanh cong";

        mListFragment = new ArrayList<>();
        mListFragment.add(UMCreatePlanStep8Screen1Fragment.newInstance(data));
        mListFragment.add(UMCreatePlanStep8Screen2Fragment.newInstance(data));

        mAdapter = new CustomViewPagerAdapter(getSupportFragmentManager(), mListFragment);
        if (viewPager != null) {
            viewPager.setAdapter(mAdapter);
        }
        if (indicator != null) {
            indicator.setViewPager(viewPager);
        }
    }

    private void setupSupportForApp() {
        txtActionbarTitle.setText("Mục tiêu cá nhân");
        layoutBackButton.setVisibility(View.VISIBLE);

        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewStatusBar.getLayoutParams();
        params.height = statusBarHeight;
        viewStatusBar.setLayoutParams(params);
    }

    @OnClick({R.id.layout_btn_back})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_btn_back: {
                onBackPressed();
                break;
            }
        }
    }
}
