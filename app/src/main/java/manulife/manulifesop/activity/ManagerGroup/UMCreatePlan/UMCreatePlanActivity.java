package manulife.manulifesop.activity.ManagerGroup.UMCreatePlan;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import manulife.manulifesop.adapter.ObjectData.UMStep6;
import manulife.manulifesop.api.ObjectResponse.UMForcastRecruit;
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
    @BindView(R.id.txt_avatar)
    TextView txtAvatar;

    //view for success layout
    @BindView(R.id.btn_goto_main)
    Button btnGoMain;
    @BindView(R.id.txt_start_date)
    TextView txtStartDate;
    @BindView(R.id.txt_end_date)
    TextView txtEndDate;
    @BindView(R.id.txt_income)
    TextView txtIncome;

    private boolean mIsShowSuccessView = false;

    //data step 1
    private String mStartDate = "";
    private String mEndDate = "";
    private int mMonthNum = 0;
    //data step2
    private UMForcastRecruit mDataStep2;
    //data step3
    private List<Integer> mDataStep3;
    //data step4
    private List<Integer> mDataStep4;
    //data step5
    private List<Integer> mDataStep5;
    //data step6
    private List<UMStep6> mDataStep6;
    //data step7
    private List<Integer> mDataStep7;

    private List<BaseFragment> mListFragment;
    private CustomViewPagerAdapter mAdapter;

    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan_um);
        hideKeyboardOutside(layoutRoot);
        mName = getIntent().getStringExtra("name");
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
                    ((UMCreatePlanStep2Fragment) mAdapter.getItem(position)).getData();
                }else if(position == 7){
                    ((UMCreatePlanStep8Fragment) mAdapter.getItem(position)).getForCastIncome();
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

    @Override
    public void onBackPressed() {
        processBackPressed();
    }

    @Override
    public void showNextFragment() {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
    }

    @Override
    public void setDataStep1(String startDate, String endDate) {
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
    }

    @Override
    public void setDataStep2(UMForcastRecruit data) {
        this.mDataStep2 = data;
    }

    @Override
    public void setDataStep3(int newAgent, int newContact, int FYC) {
        mDataStep3 = new ArrayList<>();
        mDataStep3.add(newAgent);
        mDataStep3.add(newContact);
        mDataStep3.add(FYC);
    }

    @Override
    public void setDataStep4(int currentAgent, int currentContact, int currentFYC, int maintainAgent) {
        mDataStep4 = new ArrayList<>();
        mDataStep4.add(currentAgent);
        mDataStep4.add(currentContact);
        mDataStep4.add(currentFYC);
        mDataStep4.add(maintainAgent);
    }

    @Override
    public void setDataStep5(int numUM, int totalUMProfit) {
        mDataStep5 = new ArrayList<>();
        mDataStep5.add(numUM);
        mDataStep5.add(totalUMProfit);
    }

    @Override
    public void setDataStep6(List<UMStep6> data) {
        mDataStep6 = data;
    }

    @Override
    public void setDataStep7(int contactPerMonth, int FYCPerMonth, int FYC, int RYP) {
        mDataStep7 = new ArrayList<>();
        mDataStep7.add(contactPerMonth);
        mDataStep7.add(FYCPerMonth);
        mDataStep7.add(FYC);
        mDataStep7.add(RYP);
    }

    @Override
    public void setInComeMonthly(String income) {
        txtIncome.setText(income);
    }

    @Override
    public String getStartDate() {
        return mStartDate;
    }

    @Override
    public String getEndDate() {
        return mEndDate;
    }

    @Override
    public int getMonthNumber() {
        return mMonthNum;
    }

    @Override
    public UMForcastRecruit getDataStep2() {
        return mDataStep2;
    }

    @Override
    public List<Integer> getDataStep3() {
        return mDataStep3;
    }

    @Override
    public List<Integer> getDataStep4() {
        return mDataStep4;
    }

    @Override
    public List<Integer> getDataStep5() {
        return mDataStep5;
    }

    @Override
    public List<UMStep6> getDataStep6() {
        return mDataStep6;
    }

    @Override
    public List<Integer> getDataStep7() {
        return mDataStep7;
    }
}
