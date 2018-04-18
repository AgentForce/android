package manulife.manulifesop.activity.ManagerGroup.Recruitment.grantedCode;

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
import manulife.manulifesop.fragment.ManagerGroup.recruiment.personalRecruitment.grantedCode.GrantedCodeTabFragment;
import manulife.manulifesop.util.Contants;


public class GrantedCodeActivity extends BaseActivity<GrantedCodePresenter> implements GrantedCodeContract.View {

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
        mActionListener = new GrantedCodePresenter(this,this);
        mMonth = getIntent().getIntExtra("month", 0);
        mTarget = getIntent().getIntExtra("target", 0);
        hideKeyboardOutside(layoutRoot);
        setupSupportForApp();
        mActionListener.getAllData(mMonth);
    }

    private void setupSupportForApp() {

        txtActionbarTitle.setText("Danh sách ký hợp đồng T" +mMonth);
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
        mListFragment.add(GrantedCodeTabFragment.newInstance(Contants.CODE_CHANGED_TO_APPLY,Contants.CODE_STRING_,mTarget,mMonth));
        mListFragment.add(GrantedCodeTabFragment.newInstance(Contants.CODE_APPLIED_DOCUMENT_AGENT,Contants.CODE_APPLIED_AGENT_STRING,mTarget,mMonth));
        mListFragment.add(GrantedCodeTabFragment.newInstance(Contants.CODE_APPLIED_DONE,Contants.CODE_APPLIED_DONE_STRING,mTarget,mMonth));
        mListFragment.add(GrantedCodeTabFragment.newInstance(Contants.CODE_WAITING_APPROVE,Contants.CODE_WAIT_APPROVE_STRING,mTarget,mMonth));
        mListFragment.add(GrantedCodeTabFragment.newInstance(Contants.CODE_GRANTED_CODE,Contants.CODE_GRANTED_STRING,mTarget,mMonth));

        mTabTitles = new ArrayList<>();
        mTabTitles.add("Chưa nộp hồ sơ(" +
                ProjectApplication.getInstance().getCodeAdded().data.count + ")");
        mTabTitles.add("Hoàn tất HSUV(" +
                ProjectApplication.getInstance().getCodeAppliedAgent().data.count
                + ")");
        mTabTitles.add("Đã nộp hồ sơ(" +
                ProjectApplication.getInstance().getCodeAppliedDone().data.count
                + ")");
        mTabTitles.add("Chờ duyệt(" +
                ProjectApplication.getInstance().getCodeWaitApprove().data.count
                + ")");
        mTabTitles.add("Đã được cấp mã(" +
                ProjectApplication.getInstance().getCodeGranted().data.count +
                "/" + mTarget + ")");

        mAdapterViewPager = new CustomViewPagerAdapter(getSupportFragmentManager(), mListFragment, mTabTitles);
        if (viewPager != null) {
            viewPager.setAdapter(mAdapterViewPager);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    @Override
    public int getSelectedType() {
        int index = viewPager.getCurrentItem();
        switch (index){
            case 0:{
                return Contants.SIGNED_NOT_APPLIED;
            }
            case 1:{
                return Contants.SIGNED_BHXH;
            }
            case 2:{
                return Contants.SIGNED_APPLIED;
            }
            case 3:{
                return Contants.SIGNED_WAIT_APPROVE;
            }
            case 4:{
                return Contants.SIGNED_SUCCESS;
            }
        }
        return Contants.SIGNED_NOT_APPLIED;
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
