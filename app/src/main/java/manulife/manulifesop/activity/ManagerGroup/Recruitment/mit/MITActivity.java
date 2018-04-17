package manulife.manulifesop.activity.ManagerGroup.Recruitment.mit;

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
import manulife.manulifesop.fragment.FAGroup.clients.consultant.ConsultantContactTabFragment;
import manulife.manulifesop.fragment.ManagerGroup.recruiment.personalRecruitment.MIT.MITTabFragment;
import manulife.manulifesop.util.Contants;


public class MITActivity extends BaseActivity<MITPresenter> implements MITContract.View {

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

    private int mMonth;
    private int mTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_person);
        mActionListener = new MITPresenter(this, this);
        mMonth = getIntent().getIntExtra("month", 0);
        mTarget = getIntent().getIntExtra("target", 0);
        hideKeyboardOutside(layoutRoot);
        setupSupportForApp();
        mActionListener.getAllData(mMonth);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            mActionListener.getAllData(mMonth);
        }
    }

    @Override
    public void initViewPager() {
        mListFragment = new ArrayList<>();
        //type = appointment, seen, calllater, refuse
        mListFragment.add(MITTabFragment.newInstance(Contants.MIT_ADDED,Contants.MIT_STRING,mTarget,mMonth));
        mListFragment.add(MITTabFragment.newInstance(Contants.MIT_RELEARN,Contants.MIT_RELEARN_STRING,mTarget,mMonth));
        mListFragment.add(MITTabFragment.newInstance(Contants.MIT_REFUSE,Contants.REFUSE,mTarget,mMonth));
        mListFragment.add(MITTabFragment.newInstance(Contants.MIT_DONE,Contants.MIT_DONE_STRING,mTarget,mMonth));

        mTabTitles = new ArrayList<>();
        mTabTitles.add("Học MIT(" +
                ProjectApplication.getInstance().getMITAdded().data.count +
                "/" + mTarget + ")");
        mTabTitles.add("Học lại(" +
                ProjectApplication.getInstance().getMITRelearn().data.count + ")");
        mTabTitles.add("Từ chối(" +
                ProjectApplication.getInstance().getMITRefuse().data.count
                + ")");
        mTabTitles.add("Hoàn thành MIT(" +
                ProjectApplication.getInstance().getMITDone().data.count
                + ")");

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
                return Contants.CONSULTANT_NEED;
            }
            case 1:{
                return Contants.CONSULTANT_SEEN;
            }
            case 2:{
                return Contants.CONSULTANT_CALLLATER;
            }
            case 3:{
                return Contants.CONSULTANT_REFUSE;
            }
        }
        return Contants.CONSULTANT_NEED;
    }

    private void setupSupportForApp() {
        //txtActionbarTitle.setText(getResources().getString(R.string.activity_create_plan_title_actionbar));
        txtActionbarTitle.setText("Danh sách học MIT " + mMonth);
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
