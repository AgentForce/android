package manulife.manulifesop.activity.FAGroup.clients.appointment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.contact.ContactPersonContract;
import manulife.manulifesop.activity.FAGroup.clients.contact.ContactPersonPresenter;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.CustomViewPager;
import manulife.manulifesop.fragment.FAGroup.clients.appointment.AppointmentContactTabFragment;
import manulife.manulifesop.util.Contants;


public class AppointmentActivity extends BaseActivity<AppointmentPresenter> implements AppointmentContract.View {

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
        mActionListener = new AppointmentPresenter(this,this);
        hideKeyboardOutside(layoutRoot,this);
        setupSupportForApp();
        initViewPager();
    }

    private void initViewPager(){
        mListFragment = new ArrayList<>();
        //type = appointment, seen, calllater, refuse
        mListFragment.add(AppointmentContactTabFragment.newInstance(Contants.APPOINTMENT));
        mListFragment.add(AppointmentContactTabFragment.newInstance(Contants.SEEN));
        mListFragment.add(AppointmentContactTabFragment.newInstance(Contants.CALLLATER));
        mListFragment.add(AppointmentContactTabFragment.newInstance(Contants.REFUSE));

        mTabTitles = new ArrayList<>();
        mTabTitles.add("Hẹn gặp");
        mTabTitles.add("Đã hẹn gặp");
        mTabTitles.add("Liên hệ sau");
        mTabTitles.add("Từ chối");

        mAdapterViewPager = new CustomViewPagerAdapter(getSupportFragmentManager(), mListFragment, mTabTitles);
        if (viewPager != null) {
            viewPager.setAdapter(mAdapterViewPager);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

    private void setupSupportForApp() {
        //txtActionbarTitle.setText(getResources().getString(R.string.activity_create_plan_title_actionbar));
        txtActionbarTitle.setText("Danh sách liên hệ tháng 12");
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
