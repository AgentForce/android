package manulife.manulifesop.activity.ManagerGroup.UMCreatePlan;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chick.indicator.CircleIndicatorPager;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.main.MainFAActivity;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.CustomViewPager;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step1.UMCreatePlanStep1Fragment;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step2.UMCreatePlanStep2Fragment;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step3.UMCreatePlanStep3Fragment;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step4.UMCreatePlanStep4Fragment;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step5.UMCreatePlanStep5Fragment;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step6.UMCreatePlanStep6Fragment;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step7.UMCreatePlanStep7Fragment;
import manulife.manulifesop.fragment.ManagerGroup.UMCreateCampaign.step8.UMCreatePlanStep8Fragment;
import manulife.manulifesop.util.Utils;


public class UMCreatePlanActivity extends BaseActivity<UMCreatePlanPresenter> implements UMCreatePlanContract.View {
    @BindView(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @BindView(R.id.layout_btn_back)
    LinearLayout layoutBackButton;
    @BindView(R.id.actionbar_custom_all)
    RelativeLayout actionBarAll;

    @BindView(R.id.view_pager)
    CustomViewPager viewPager;
    @BindView(R.id.circle_indicator_pager)
    CircleIndicatorPager indicator;
    @BindView(R.id.status_bar)
    View viewStatusBar;

    @BindView(R.id.layout_root)
    View layoutRoot;

    @BindView(R.id.layout_step)
    LinearLayout layoutStep;
    @BindView(R.id.layout_success)
    RelativeLayout layoutSuccess;

    //view for success layout
    @BindView(R.id.btn_goto_main)
    Button btnGoMain;
    @BindView(R.id.txt_start_date)
    TextView txtStartDate;
    @BindView(R.id.txt_end_date)
    TextView txtEndDate;
    @BindView(R.id.txt_income)
    TextView txtIncome;
    @BindView(R.id.txt_contract_num)
    TextView txtContractNum;

    private boolean mIsShowSuccessView = false;

    private int mSupportMoney;
    private String mStartDate = "";
    private String mEndDate = "";
    private int mMonthNum = 0;


    private List<BaseFragment> mListFragment;
    private CustomViewPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);
        hideKeyboardOutside(layoutRoot);
        mActionListener = new UMCreatePlanPresenter(this, this);
        setupSupportForApp();
        initViewPager();
    }

    private void setupSupportForApp() {
        txtActionbarTitle.setText(getResources().getString(R.string.activity_create_plan_title_actionbar));
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

    private void initViewPager() {
        viewPager.setSwipe(false);

        mListFragment = new ArrayList<>();
        mListFragment.add(UMCreatePlanStep1Fragment.newInstance());
        mListFragment.add(UMCreatePlanStep2Fragment.newInstance());
        mListFragment.add(UMCreatePlanStep3Fragment.newInstance());
        mListFragment.add(UMCreatePlanStep4Fragment.newInstance());
        mListFragment.add(UMCreatePlanStep5Fragment.newInstance());
        mListFragment.add(UMCreatePlanStep6Fragment.newInstance());
        mListFragment.add(UMCreatePlanStep7Fragment.newInstance());
        mListFragment.add(UMCreatePlanStep8Fragment.newInstance());

        mAdapter = new CustomViewPagerAdapter(getSupportFragmentManager(), mListFragment);
        if (viewPager != null) {
            viewPager.setAdapter(mAdapter);
        }
        if (indicator != null) {
            indicator.setViewPager(viewPager);
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    Toast.makeText(UMCreatePlanActivity.this, "call api", Toast.LENGTH_SHORT).show();
                    //((CreatePlanStep3Fragment) mAdapter.getItem(position)).updateDate(mContractNum, mMonthNum);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.layout_btn_back, R.id.btn_goto_main})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_btn_back: {
                processBackPressed();
                break;
            }
            case R.id.btn_goto_main: {
                goNextScreen(MainFAActivity.class);
                break;
            }
        }
    }

    private void processBackPressed() {
        if (viewPager.getCurrentItem() > 0 && !mIsShowSuccessView) {
            int backPosition = viewPager.getCurrentItem() - 1;
            viewPager.setCurrentItem(backPosition, true);
        } else {
            backToPrevious(new Bundle());
        }
    }

    @Override
    public void onBackPressed() {
        processBackPressed();
    }

    @Override
    public void showNextFragment(String startDate, String endDate) {
        if (startDate.length() > 0) {
            this.mStartDate = startDate;
            txtStartDate.setText(startDate);
        }
        if (endDate.length() > 0) {
            this.mEndDate = endDate;
            txtEndDate.setText(endDate);
            this.mMonthNum = (Utils.getMonthFromStringDate(mEndDate,"dd/MM/yyyy")
                    - Utils.getMonthFromStringDate(mStartDate,"dd/MM/yyyy")) + 1;
        }
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
    }
}
