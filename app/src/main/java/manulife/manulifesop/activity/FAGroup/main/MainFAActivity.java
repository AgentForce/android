package manulife.manulifesop.activity.FAGroup.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;

import butterknife.BindView;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.createPlan.CreatePlanPresenter;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.fragment.FAGroup.confirmCreatePlan.ConfirmCreatePlanFragment;

/**
 * Created by Chick on 1/23/2018.
 */

public class MainFAActivity extends BaseActivity<MainFAPresenter> implements MainFAContract.View {

    @BindView(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @BindView(R.id.status_bar)
    View viewStatusBar;
    @BindView(R.id.layout_btn_back)
    LinearLayout layoutBackButton;

    @BindView(R.id.bottom_navigation)
    AHBottomNavigation bottomNavigation;

    @BindView(R.id.frame_container)
    FrameLayout frameLayout;

    AHBottomNavigationAdapter mNavigationAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fa);
        mActionListener = new MainFAPresenter(this,this);
        setupSupportForApp();
        setupMenuBot();
        mActionListener.checkIsGetCampaign(getIntent().getExtras()
                .getBoolean("isGetCampaign",false));
    }

    private void setupSupportForApp()
    {
        txtActionbarTitle.setText(getResources().getString(R.string.activity_main_fa_dashboard));
        layoutBackButton.setVisibility(View.GONE);

        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewStatusBar.getLayoutParams();
        params.height = statusBarHeight;
        viewStatusBar.setLayoutParams(params);
    }

    private void setupMenuBot()
    {
        int[] tabColors = getApplicationContext().getResources().getIntArray(R.array.tab_colors);
        mNavigationAdapter = new AHBottomNavigationAdapter(this, R.menu.menu_bot);
        mNavigationAdapter.setupWithBottomNavigation(bottomNavigation, tabColors);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        bottomNavigation.setAccentColor(getResources().getColor(R.color.colorPrimary));
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.botMenuDisable));

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position)
                {
                    case 0:
                    {
                        Toast.makeText(MainFAActivity.this, "position 0", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 1:
                    {
                        Toast.makeText(MainFAActivity.this, "position 1", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 2:
                    {
                        Toast.makeText(MainFAActivity.this, "position 2", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 3:
                    {
                        Toast.makeText(MainFAActivity.this, "position 3", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notify, menu);
        return true;
    }

    @Override
    public void showFragmentConfirmCreatePlan() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //ft.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        ft.replace(R.id.frame_container, ConfirmCreatePlanFragment.newInstance());
        ft.commit();
    }
}
