package manulife.manulifesop.activity.ManagerGroup.Recruitment.cop;

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
import manulife.manulifesop.fragment.ManagerGroup.recruiment.personalRecruitment.COP.COPTabFragment;
import manulife.manulifesop.util.Contants;


public class COPActivity extends BaseActivity<COPPresenter> implements COPContract.View {

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
        mActionListener = new COPPresenter(this, this);
        mMonth = getIntent().getIntExtra("month", 0);
        mTarget = getIntent().getIntExtra("target", 0);
        hideKeyboardOutside(layoutRoot);
        setupSupportForApp();
        mActionListener.loadAllData(mMonth);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            mActionListener.loadAllData(mMonth);
        }
    }

    @Override
    public void initViewPager() {
        mListFragment = new ArrayList<>();
        //type = appointment, seen, calllater, refuse
        mListFragment.add(COPTabFragment.newInstance(Contants.COP_ADDED,Contants.COP,mTarget,mMonth));
        mListFragment.add(COPTabFragment.newInstance(Contants.COP_CALLATER,Contants.COP_APPOINTMENT_STRING,mTarget,mMonth));
        mListFragment.add(COPTabFragment.newInstance(Contants.COP_REFUSE,Contants.REFUSE,mTarget,mMonth));
        mListFragment.add(COPTabFragment.newInstance(Contants.COP_DONE,Contants.COP_DONE_STRING,mTarget,mMonth));

        mTabTitles = new ArrayList<>();
        mTabTitles.add("Dự COP(" +
                ProjectApplication.getInstance().getCOPAdded().data.count +
                "/" + mTarget + ")");
        mTabTitles.add("Gọi lại sau(" +
                ProjectApplication.getInstance().getCOPCallLater().data.count
                + ")");
        mTabTitles.add("Từ chối(" +
                ProjectApplication.getInstance().getCOPRefuse().data.count
                + ")");
        mTabTitles.add("Hoàn thành COP(" +
                ProjectApplication.getInstance().getCOPDone().data.count
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
                return Contants.APPOINTMENT_NEED;
            }
            case 1:{
                return Contants.APPOINTMENT_SEEN;
            }
            case 2:{
                return Contants.APPOINTMENT_CALLLATER;
            }
            case 3:{
                return Contants.APPOINTMENT_REFUSE;
            }
        }
        return Contants.APPOINTMENT_NEED;
    }

    private void setupSupportForApp() {
        //txtActionbarTitle.setText(getResources().getString(R.string.activity_create_plan_title_actionbar));
        txtActionbarTitle.setText("Danh sách học COP " + mMonth);
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
