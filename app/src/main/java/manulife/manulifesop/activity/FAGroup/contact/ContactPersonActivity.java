package manulife.manulifesop.activity.FAGroup.contact;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.confirmCreatePlan.ConfirmCreatePlanContract;
import manulife.manulifesop.activity.FAGroup.confirmCreatePlan.ConfirmCreatePlanPresenter;
import manulife.manulifesop.activity.FAGroup.createPlan.CreatePlanActivity;
import manulife.manulifesop.activity.FAGroup.main.MainFAActivity;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.CustomViewPager;
import manulife.manulifesop.fragment.FAGroup.contactPerson.tab1.ContactPersonTab1Fragment;
import manulife.manulifesop.fragment.FAGroup.dashboard.campaignPercent.CampaignPercentFragment;


public class ContactPersonActivity extends BaseActivity<ContactPersonPresenter> implements ContactPersonContract.View {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_person);
        mActionListener = new ContactPersonPresenter(this,this);
        hideKeyboardOutside(layoutRoot,this);
        setupSupportForApp();
        initViewPager();
    }

    private void initViewPager(){
        mListFragment = new ArrayList<>();
        mListFragment.add(ContactPersonTab1Fragment.newInstance());
        mListFragment.add(ContactPersonTab1Fragment.newInstance());

        mTabTitles = new ArrayList<>();
        mTabTitles.add("KH giới thiệu (0/50)");
        mTabTitles.add("Gọi lại sau");

        mAdapterViewPager = new CustomViewPagerAdapter(getSupportFragmentManager(), mListFragment, mTabTitles);
        if (viewPager != null) {
            viewPager.setAdapter(mAdapterViewPager);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    private void setupSupportForApp() {
        //txtActionbarTitle.setText(getResources().getString(R.string.activity_create_plan_title_actionbar));
        txtActionbarTitle.setText("Danh sách KH giới thiệu tháng 12");
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

    /*@OnClick({R.id.btn_start, R.id.txt_go_main})
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.btn_start:{
                goNextScreen(CreatePlanActivity.class);
                break;
            }
            case R.id.txt_go_main:{
                Bundle data = new Bundle();
                data.putBoolean("isGetCampaign",false);
                goNextScreen(MainFAActivity.class,data);
                break;
            }
        }
    }*/
}
