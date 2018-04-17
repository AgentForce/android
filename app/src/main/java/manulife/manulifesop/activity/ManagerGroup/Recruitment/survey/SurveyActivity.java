package manulife.manulifesop.activity.ManagerGroup.Recruitment.survey;

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
import manulife.manulifesop.R;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.api.ObjectResponse.UsersList;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.CustomViewPager;
import manulife.manulifesop.fragment.FAGroup.clients.contactPerson.ContactPersonTab1Fragment;
import manulife.manulifesop.fragment.ManagerGroup.recruiment.personalRecruitment.surveyPerson.SurveyPersonTab1Fragment;
import manulife.manulifesop.util.Contants;


public class SurveyActivity extends BaseActivity<SurveyPresenter> implements SurveyContract.View {

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
    private int mTargetIntroduce;
    private int mMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_person);
        mActionListener = new SurveyPresenter(this, this);
        hideKeyboardOutside(layoutRoot);
        mTarget = getIntent().getIntExtra("target", 0);
        mTargetIntroduce = getIntent().getIntExtra("targetIntroduce", 0);
        mMonth = getIntent().getIntExtra("month", 0);
        setupSupportForApp();
        mActionListener.getAllContactPerson(mMonth, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            mActionListener.getAllContactPerson(mMonth, 1);
        }
    }

    @Override
    public void reloadData() {
        mActionListener.getAllContactPerson(mMonth, 1);
    }

    @Override
    public void initViewPager(UsersList contact, UsersList callLater,UsersList refuse) {

        viewPager.setSaveFromParentEnabled(false);

        mListFragment = new ArrayList<>();
        mListFragment.add(SurveyPersonTab1Fragment.newInstance(Contants.SURVEY_ADDED,Contants.SURVEY, contact, mMonth, mTargetIntroduce));
        mListFragment.add(SurveyPersonTab1Fragment.newInstance(Contants.SURVEY_CALLLATER,Contants.CALLLATER, callLater, mMonth, mTargetIntroduce));
        mListFragment.add(SurveyPersonTab1Fragment.newInstance(Contants.SURVEY_REFUSE,Contants.REFUSE, refuse, mMonth, mTargetIntroduce));

        mTabTitles = new ArrayList<>();
        mTabTitles.add("Khảo sát(" + contact.data.count + "/" + mTarget + ")");
        mTabTitles.add("Gọi lại sau(" + callLater.data.count + ")");
        mTabTitles.add("Từ chối(" + refuse.data.count + ")");

        mAdapterViewPager = new CustomViewPagerAdapter(getSupportFragmentManager(), mListFragment, mTabTitles);
        viewPager.setAdapter(mAdapterViewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public int getSelectedType() {
        int index = viewPager.getCurrentItem();
        switch (index){
            case 0:{
                return Contants.USER_CONTACT;
            }
            case 1:{
                return Contants.USER_CALLLATER;
            }
            case 2:{
                return Contants.USER_REFUSE;
            }
        }
        return Contants.USER_CONTACT;
    }

    private void setupSupportForApp() {
        //txtActionbarTitle.setText(getResources().getString(R.string.activity_create_plan_title_actionbar));
        txtActionbarTitle.setText("Danh sách khảo sát tháng " + mMonth);
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
