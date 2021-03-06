package manulife.manulifesop.activity.FAGroup.createPlan;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chick.indicator.CircleIndicatorPager;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.main.MainFAActivity;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.api.ObjectResponse.CampaignForcastTarget;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.CustomViewPager;
import manulife.manulifesop.fragment.FAGroup.createPlane.step1.CreatePlanStep1Fragment;
import manulife.manulifesop.fragment.FAGroup.createPlane.step2.CreatePlanStep2Fragment;
import manulife.manulifesop.fragment.FAGroup.createPlane.step3.CreatePlanStep3Fragment;
import manulife.manulifesop.fragment.FAGroup.createPlane.step4.CreatePlanStep4Fragment;
import manulife.manulifesop.util.Utils;


public class CreatePlanActivity extends BaseActivity<CreatePlanPresenter> implements CreatePlanContract.View {
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

    @BindView(R.id.txt_avatar)
    TextView txtAvatar;

    private boolean mIsShowSuccessView = false;

    private int mContractNum = 0;
    private int mIncome = 55;
    private int mContractPrice = 0;
    private int mProfit;
    private String mStartDate = "";
    private String mEndDate = "";
    private int mMonthNum = 0;


    private List<BaseFragment> mListFragment;
    private CustomViewPagerAdapter mAdapter;

    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan);
        hideKeyboardOutside(layoutRoot);
        mName = getIntent().getStringExtra("name");
        mActionListener = new CreatePlanPresenter(this, this);
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
        mListFragment.add(CreatePlanStep1Fragment.newInstance());
        mListFragment.add(CreatePlanStep2Fragment.newInstance());
        mListFragment.add(CreatePlanStep3Fragment.newInstance());
        mListFragment.add(CreatePlanStep4Fragment.newInstance());

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
                switch (position){
                    case 1:{
                        ((CreatePlanStep2Fragment) mAdapter.getItem(position)).setProgressIncome(mIncome);
                        break;
                    }
                    case 2:{
                        ((CreatePlanStep3Fragment) mAdapter.getItem(position)).updateDate(mContractNum, mMonthNum);
                        break;
                    }
                    case 3:{
                        mActionListener.getCampaignForcast(mIncome, mProfit, mContractPrice,mStartDate,mEndDate);
                        break;
                    }
                }
                /*if (position == 2) {
                    ((CreatePlanStep3Fragment) mAdapter.getItem(position)).updateDate(mContractNum, mMonthNum);
                } else if (position == 3) {
                    mActionListener.getCampaignForcast(mIncome, mProfit, mContractPrice,mStartDate,mEndDate);
                }*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void showForcast(CampaignForcastTarget data) {
        ((CreatePlanStep4Fragment) mAdapter.getItem(3)).showData(mIncome, data,mMonthNum);
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
        //super.onBackPressed();
        processBackPressed();
    }

    @Override
    public void showNextFragment(int contractNum, String startDate, String endDate, int income, int contractPrice, int profit) {
        if (contractNum > 0) {
            this.mContractNum = contractNum;
            txtContractNum.setText(String.valueOf(contractNum));
        }
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
        if (income > 0) {
            this.mIncome = income;
            txtIncome.setText(String.valueOf(income));
        }
        if (contractPrice > 0) {
            this.mContractPrice = contractPrice;
        }
        if (profit > 0) {
            this.mProfit = profit;
        }

        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
    }

    @Override
    public void showCreateCampaign() {
        showLoading("Xử lý dữ liệu!");
        mActionListener.createCampaign(mStartDate, mEndDate, mProfit, mContractPrice, mIncome);
    }

    @Override
    public void showSuccessView() {
        this.mIsShowSuccessView = true;
        layoutBackButton.setVisibility(View.GONE);

        txtAvatar.setText(mName.substring(0,1));

        Animation in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        layoutStep.startAnimation(out);
        layoutStep.setVisibility(View.GONE);
        layoutSuccess.startAnimation(in);
        layoutSuccess.setVisibility(View.VISIBLE);
    }
}
