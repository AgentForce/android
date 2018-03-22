package manulife.manulifesop.activity.FAGroup.clients.signed;

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
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.CustomViewPager;
import manulife.manulifesop.fragment.FAGroup.clients.signed.SignedContactTabFragment;
import manulife.manulifesop.util.Contants;


public class SignedPersonActivity extends BaseActivity<SignedPersonPresenter> implements SignedPersonContract.View {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_person);
        mActionListener = new SignedPersonPresenter(this,this);
        mMonth = getIntent().getIntExtra("month", 0);
        mTarget = getIntent().getIntExtra("target", 0);
        hideKeyboardOutside(layoutRoot);
        setupSupportForApp();
        mActionListener.getAllData(mMonth);
    }

    private void setupSupportForApp() {

        txtActionbarTitle.setText("KH ký hợp đồng T12");
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            mActionListener.getAllData(mMonth);
        }
    }

    @Override
    public void initViewPager() {
        mListFragment = new ArrayList<>();
        //type = appointment, seen, calllater, refuse
        mListFragment.add(SignedContactTabFragment.newInstance(Contants.SIGNED_NOT_APPLIED,Contants.SIGNED_NOT_APPLY_STRING,mTarget,mMonth));
        mListFragment.add(SignedContactTabFragment.newInstance(Contants.SIGNED_BHXH,Contants.SIGNED_BHXH_STRING,mTarget,mMonth));
        mListFragment.add(SignedContactTabFragment.newInstance(Contants.SIGNED_APPLIED,Contants.SIGNED_APPLIED_STRING,mTarget,mMonth));
        mListFragment.add(SignedContactTabFragment.newInstance(Contants.SIGNED_WAIT_APPROVE,Contants.SIGNED_WAIT_APPROVE_STRING,mTarget,mMonth));
        mListFragment.add(SignedContactTabFragment.newInstance(Contants.SIGNED_SUCCESS,Contants.SIGNED_SUCCESS_STRING,mTarget,mMonth));

        mTabTitles = new ArrayList<>();
        mTabTitles.add("Chưa nộp hồ sơ(" +
                ProjectApplication.getInstance().getSignNotApply().data.count + ")");
        mTabTitles.add("Hoàn tất BHXH(" +
                ProjectApplication.getInstance().getSignBHXH().data.count
                + ")");
        mTabTitles.add("Đã nộp hồ sơ(" +
                ProjectApplication.getInstance().getSignApplied().data.count
                + ")");
        mTabTitles.add("Chờ duyệt(" +
                ProjectApplication.getInstance().getSignWaitApprove().data.count
                + ")");
        mTabTitles.add("Ký HĐ thành công(" +
                ProjectApplication.getInstance().getSignSuccess().data.count +
                "/" + mTarget + ")");

        mAdapterViewPager = new CustomViewPagerAdapter(getSupportFragmentManager(), mListFragment, mTabTitles);
        if (viewPager != null) {
            viewPager.setAdapter(mAdapterViewPager);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @OnClick({R.id.layout_btn_back})
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.layout_btn_back:{
                onBackPressed();
                break;
            }
        }
    }
}
