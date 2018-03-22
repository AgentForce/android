package manulife.manulifesop.activity.FAGroup.clients.introduceContact;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.createPlan.CreatePlanActivity;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.CustomViewPager;
import manulife.manulifesop.fragment.FAGroup.clients.contactIntroduce.IntroduceContactTabFragment;
import manulife.manulifesop.util.Contants;


public class IntroduceContactActivity extends BaseActivity<IntroduceContactPresenter> implements IntroduceContactContract.View {

    @BindView(R.id.layout_root)
    LinearLayout layoutRoot;

    @BindView(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @BindView(R.id.layout_btn_back)
    LinearLayout layoutBackButton;
    @BindView(R.id.status_bar)
    View viewStatusBar;

    @BindView(R.id.tabs_menu_options)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    CustomViewPager viewPager;

    private CustomViewPagerAdapter mAdapterViewPager;
    private List<BaseFragment> mListFragment;
    private List<String> mTabTitles;

    private int mTarget;
    private int mMonth;

    //variable is open from contact activity
    private boolean mIsFromContactActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_person);
        mActionListener = new IntroduceContactPresenter(this, this);
        hideKeyboardOutside(layoutRoot);
        mTarget = getIntent().getIntExtra("target", 0);
        mMonth = getIntent().getIntExtra("month", 0);

        //variable is open from contact activity
        mIsFromContactActivity = getIntent().getBooleanExtra("isFromContact",false);

        setupSupportForApp();
        mActionListener.getAllIntroduces(mMonth, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            mActionListener.getAllIntroduces(mMonth, 1);
        }
    }

    @Override
    public void reloadData() {
        mActionListener.getAllIntroduces(mMonth, 1);
    }

    public void initViewPager() {
        mListFragment = new ArrayList<>();
        mListFragment.add(IntroduceContactTabFragment.newInstance(Contants.INTRODURE, 3, mIsFromContactActivity));

        mTabTitles = new ArrayList<>();
        mTabTitles.add("KH giới thiệu(" +
                ProjectApplication.getInstance().getIntroduce().data.count + "/" + mTarget + ")");
        mAdapterViewPager = new CustomViewPagerAdapter(getSupportFragmentManager(), mListFragment, mTabTitles);
        if (viewPager != null) {
            viewPager.setAdapter(mAdapterViewPager);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    private void setupSupportForApp() {
        //txtActionbarTitle.setText(getResources().getString(R.string.activity_create_plan_title_actionbar));
        txtActionbarTitle.setText("Danh sách KH giới thiệu T12");
        layoutBackButton.setVisibility(View.VISIBLE);

        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewStatusBar.getLayoutParams();
        params.height = statusBarHeight;
        viewStatusBar.setLayoutParams(params);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.setClickable(false);
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
