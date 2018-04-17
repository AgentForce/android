package manulife.manulifesop.activity.FAGroup.clients.related.contactDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.internal.schedulers.ImmediateThinScheduler;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.related.createEvent.CreateEventActivity;
import manulife.manulifesop.activity.FAGroup.clients.related.signedSuccess.SignedSuccessActivity;
import manulife.manulifesop.activity.FAGroup.clients.related.updateContactInfo.UpdateContactInfoActivity;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.adapter.ObjectData.ContactPerson;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.CustomViewPager;
import manulife.manulifesop.element.callbackInterface.CallBackConfirmDialog;
import manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step1.ContactDetailStep1Fragment;
import manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step1Refuse.ContactDetailStep1RefuseFragment;
import manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step2.ContactDetailStep2Fragment;
import manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step3.ContactDetailStep3Fragment;
import manulife.manulifesop.util.Contants;


public class ContactDetailActivity extends BaseActivity<ContactDetailPresenter> implements ContactDetailContract.View {

    @BindView(R.id.txt_avatar)
    TextView txtAvatar;
    @BindView(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @BindView(R.id.layout_btn_back)
    LinearLayout layoutBackButton;
    @BindView(R.id.status_bar)
    View viewStatusBar;
    @BindView(R.id.img_top_background)
    ImageView imageTop;


    @BindView(R.id.img_start1)
    ImageView imageStart1;
    @BindView(R.id.img_start2)
    ImageView imageStart2;
    @BindView(R.id.img_start3)
    ImageView imageStart3;
    @BindView(R.id.img_start4)
    ImageView imageStart4;
    @BindView(R.id.img_start5)
    ImageView imageStart5;

    @BindView(R.id.txt_title_tyle)
    TextView txtTitleType;
    @BindView(R.id.txt_user_name)
    TextView txtUserName;
    @BindView(R.id.txt_phone)
    TextView txtPhone;

    @BindView(R.id.tabs)
    TabLayout tabMenu;
    @BindView(R.id.view_pager)
    CustomViewPager viewPager;

    //variable for menu
    @BindView(R.id.layout_menu_bot)
    LinearLayout layoutMenuBot;

    @BindView(R.id.layout_menu)
    ViewStub layoutMenu;

    private String mTypeString;
    private String mTypeMenu;
    private int mUserId;

    private CustomViewPagerAdapter mAdapterViewPager;
    private List<BaseFragment> mListFragment;
    private List<String> mTabTitles;

    //variable to detect sm recruit
    private boolean mIsRecruit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTypeString = getIntent().getStringExtra("type");
        mTypeMenu = getIntent().getStringExtra("type_menu");
        mUserId = getIntent().getIntExtra("id", 0);

        //test
        //mUserId = 52;

        if (mTypeString.equals(Contants.REFUSE)) {
            setContentView(R.layout.activity_contact_detail_refuse);
        } else {
            setContentView(R.layout.activity_contact_detail);
        }
        mActionListener = new ContactDetailPresenter(this, this);
        initViews();
        initMenuAfterCall();
        if (mIsRecruit)
            mActionListener.getRecruitDetail(mUserId);
        else
            mActionListener.getContactDetail(mUserId);
    }

    private void initViews() {
        txtTitleType.setText(mTypeString);
    }

    private void initMenuAfterCall() {
        if (mTypeMenu != null) {
            switch (mTypeMenu) {
                case Contants.CONTACT_MENU: {
                    layoutMenu.setLayoutResource(R.layout.layout_float_menu_contact);
                    layoutMenu.inflate();

                    processMenuContact listener = new processMenuContact();
                    findViewById(R.id.layout_menu_appointment).setOnClickListener(listener);
                    findViewById(R.id.layout_menu_call_later).setOnClickListener(listener);
                    findViewById(R.id.layout_menu_cancel).setOnClickListener(listener);

                    break;
                }

                case Contants.APPOINTMENT_MENU: {
                    layoutMenu.setLayoutResource(R.layout.layout_float_menu_appointment);
                    layoutMenu.inflate();

                    processMenuAppointment listener = new processMenuAppointment();
                    findViewById(R.id.layout_menu_consultant).setOnClickListener(listener);
                    findViewById(R.id.layout_menu_appointment_event).setOnClickListener(listener);
                    findViewById(R.id.layout_menu_call_later).setOnClickListener(listener);
                    findViewById(R.id.layout_menu_cancel).setOnClickListener(listener);

                    break;
                }
                case Contants.CONSULTANT_MENU: {
                    layoutMenu.setLayoutResource(R.layout.layout_float_menu_consultant);
                    layoutMenu.inflate();

                    processMenuConsultant listener = new processMenuConsultant();
                    findViewById(R.id.layout_menu_signed).setOnClickListener(listener);
                    findViewById(R.id.layout_menu_consultant_appointment).setOnClickListener(listener);
                    findViewById(R.id.layout_menu_call_later).setOnClickListener(listener);
                    findViewById(R.id.layout_menu_cancel).setOnClickListener(listener);

                    break;
                }

                case Contants.SIGNED_MENU: {
                    layoutMenu.setLayoutResource(R.layout.layout_float_menu_signed);
                    layoutMenu.inflate();

                    processMenuSigned listener = new processMenuSigned();
                    findViewById(R.id.layout_menu_sign_BHXH).setOnClickListener(listener);
                    findViewById(R.id.layout_menu_sign_applied).setOnClickListener(listener);
                    findViewById(R.id.layout_menu_waiting_approve).setOnClickListener(listener);
                    findViewById(R.id.layout_menu_sign_succcess).setOnClickListener(listener);
                    break;
                }
                case Contants.INTRODUCE_MENU: {
                    layoutMenu.setLayoutResource(R.layout.layout_float_menu_introduce);
                    layoutMenu.inflate();

                    break;
                }

                case Contants.SURVEY_MENU: {
                    mIsRecruit = true;
                    layoutMenu.setLayoutResource(R.layout.layout_float_menu_survey);
                    layoutMenu.inflate();

                    processMenuSurvey listener = new processMenuSurvey();
                    findViewById(R.id.layout_menu_survey).setOnClickListener(listener);
                    findViewById(R.id.layout_menu_call_later).setOnClickListener(listener);
                    findViewById(R.id.layout_menu_cancel).setOnClickListener(listener);
                    break;
                }

                case Contants.COP_MENU: {
                    mIsRecruit = true;
                    layoutMenu.setLayoutResource(R.layout.layout_float_menu_cop);
                    layoutMenu.inflate();

                    processMenuCOP listener = new processMenuCOP();
                    findViewById(R.id.layout_menu_mit).setOnClickListener(listener);
                    findViewById(R.id.layout_menu_cop_event).setOnClickListener(listener);
                    findViewById(R.id.layout_menu_call_later).setOnClickListener(listener);
                    findViewById(R.id.layout_menu_cancel).setOnClickListener(listener);
                    break;
                }
            }
        }
    }

    @Override
    public void finishChangeStatus() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Contants.ADD_EVENT || requestCode == Contants.UPDATE_CONTACT) {
                if (mIsRecruit)
                    mActionListener.getRecruitDetail(mUserId);
                else
                    mActionListener.getContactDetail(mUserId);
            } else if (requestCode == Contants.SIGN_SUCCESS) {
                finishChangeStatus();
            }
        }
    }


    private class processMenuContact implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.layout_menu_appointment: {
                    mActionListener.updateStatusProcess(mUserId, true, 1);
                    break;
                }
                case R.id.layout_menu_call_later: {
                    mActionListener.updateStatusProcess(mUserId, false, Contants.USER_CALLLATER);
                    break;
                }
                case R.id.layout_menu_cancel: {
                    mActionListener.updateStatusProcess(mUserId, false, Contants.USER_REFUSE);
                    break;
                }
            }
        }
    }

    private class processMenuAppointment implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.layout_menu_consultant: {
                    mActionListener.updateStatusProcess(mUserId, true, 1);
                    break;
                }
                case R.id.layout_menu_appointment_event: {
                    Bundle data = new Bundle();
                    data.putInt("typeInt", 1);
                    data.putInt("contactID", mUserId);
                    data.putString("name", txtUserName.getText().toString());
                    showHideMenuAfterCall();
                    goNextScreen(CreateEventActivity.class, data, Contants.ADD_EVENT);
                    break;
                }
                case R.id.layout_menu_call_later: {
                    mActionListener.updateStatusProcess(mUserId, false, Contants.APPOINTMENT_CALLLATER);
                    break;
                }
                case R.id.layout_menu_cancel: {
                    mActionListener.updateStatusProcess(mUserId, false, Contants.APPOINTMENT_REFUSE);
                    break;
                }
            }
        }
    }

    private class processMenuConsultant implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.layout_menu_signed: {
                    mActionListener.updateStatusProcess(mUserId, true, 1);
                    break;
                }
                case R.id.layout_menu_consultant_appointment: {
                    Bundle data = new Bundle();
                    data.putInt("typeInt", 1);
                    data.putInt("contactID", mUserId);
                    data.putString("name", txtUserName.getText().toString());
                    showHideMenuAfterCall();
                    goNextScreen(CreateEventActivity.class, data, Contants.ADD_EVENT);
                    break;
                }
                case R.id.layout_menu_call_later: {
                    mActionListener.updateStatusProcess(mUserId, false, Contants.CONSULTANT_CALLLATER);
                    break;
                }
                case R.id.layout_menu_cancel: {
                    mActionListener.updateStatusProcess(mUserId, false, Contants.CONSULTANT_REFUSE);
                    break;
                }
            }
        }
    }

    private class processMenuSigned implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.layout_menu_sign_BHXH: {
                    mActionListener.updateStatusProcess(mUserId, false, Contants.SIGNED_BHXH);
                    break;
                }
                case R.id.layout_menu_sign_applied: {
                    mActionListener.updateStatusProcess(mUserId, false, Contants.SIGNED_APPLIED);
                    break;
                }
                case R.id.layout_menu_waiting_approve: {
                    mActionListener.updateStatusProcess(mUserId, false, Contants.SIGNED_WAIT_APPROVE);
                    break;
                }
                case R.id.layout_menu_sign_succcess: {
                    //mActionListener.updateStatusProcess(mUserId,false,Contants.SIGNED_SUCCESS);
                    Bundle data = new Bundle();
                    data.putInt("leadID", mUserId);
                    goNextScreen(SignedSuccessActivity.class, data, Contants.SIGN_SUCCESS);
                    break;
                }
            }
        }

    }

    private class processMenuSurvey implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.layout_menu_survey: {
                    mActionListener.updateStatusProcessRecruit(mUserId, true, 1);
                    break;
                }
                case R.id.layout_menu_call_later: {
                    mActionListener.updateStatusProcessRecruit(mUserId, false, Contants.SURVEY_CALLLATER);
                    break;
                }
                case R.id.layout_menu_cancel: {
                    mActionListener.updateStatusProcessRecruit(mUserId, false, Contants.SURVEY_REFUSE);
                    break;
                }
            }
        }
    }

    private class processMenuCOP implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.layout_menu_mit: {
                    mActionListener.updateStatusProcessRecruit(mUserId, true, 1);
                    break;
                }
                case R.id.layout_menu_cop_event: {
                    Bundle data = new Bundle();
                    data.putInt("typeInt", 1);
                    data.putInt("contactID", mUserId);
                    data.putString("name", txtUserName.getText().toString());
                    showHideMenuAfterCall();
                    goNextScreen(CreateEventActivity.class, data, Contants.ADD_EVENT);
                    break;
                }
                case R.id.layout_menu_call_later: {
                    mActionListener.updateStatusProcessRecruit(mUserId, false, Contants.COP_CALLATER);
                    break;
                }
                case R.id.layout_menu_cancel: {
                    mActionListener.updateStatusProcessRecruit(mUserId, false, Contants.COP_REFUSE);
                    break;
                }
            }
        }
    }

    private void initScore(int score) {
        switch (score) {
            case 1: {
                imageStart1.setImageResource(R.drawable.ic_start_active);
                break;
            }
            case 2: {
                imageStart1.setImageResource(R.drawable.ic_start_active);
                imageStart2.setImageResource(R.drawable.ic_start_active);
                break;
            }
            case 3: {
                imageStart1.setImageResource(R.drawable.ic_start_active);
                imageStart2.setImageResource(R.drawable.ic_start_active);
                imageStart3.setImageResource(R.drawable.ic_start_active);
                break;
            }
            case 4: {
                imageStart1.setImageResource(R.drawable.ic_start_active);
                imageStart2.setImageResource(R.drawable.ic_start_active);
                imageStart3.setImageResource(R.drawable.ic_start_active);
                imageStart4.setImageResource(R.drawable.ic_start_active);
                break;
            }
            case 5: {
                imageStart1.setImageResource(R.drawable.ic_start_active);
                imageStart2.setImageResource(R.drawable.ic_start_active);
                imageStart3.setImageResource(R.drawable.ic_start_active);
                imageStart4.setImageResource(R.drawable.ic_start_active);
                imageStart5.setImageResource(R.drawable.ic_start_active);
                break;
            }
        }
    }

    @Override
    public void initViewPager() {
        txtAvatar.setText(ProjectApplication.getInstance().getContactDetail().data.name.substring(0, 1));
        txtUserName.setText(ProjectApplication.getInstance().getContactDetail().data.name);
        txtPhone.setText(ProjectApplication.getInstance().getContactDetail().data.phone);
        initScore(ProjectApplication.getInstance().getContactDetail().data.score);


        mListFragment = new ArrayList<>();
        if (mTypeString.equals(Contants.REFUSE)) {
            mListFragment.add(ContactDetailStep1RefuseFragment.newInstance(mTypeMenu, mUserId));
        } else {
            mListFragment.add(ContactDetailStep1Fragment.newInstance());
        }
        mListFragment.add(ContactDetailStep2Fragment.newInstance(mUserId));
        mListFragment.add(ContactDetailStep3Fragment.newInstance());

        mTabTitles = new ArrayList<>();
        mTabTitles.add("Cá nhân");
        mTabTitles.add("Sự kiện");
        mTabTitles.add("Hoạt động");

        mAdapterViewPager = new CustomViewPagerAdapter(getSupportFragmentManager(), mListFragment, mTabTitles);
        //if (viewPager != null)
        {
            viewPager.setAdapter(mAdapterViewPager);
            tabMenu.setupWithViewPager(viewPager);
        }
    }

    @Override
    public void showMenuOption() {
        Animation in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        layoutMenuBot.startAnimation(in);
        layoutMenuBot.setVisibility(View.VISIBLE);
        if (mAdapterViewPager.getItem(0) instanceof ContactDetailStep1Fragment) {
            ((ContactDetailStep1Fragment) mAdapterViewPager.getItem(0)).showXLetter(true);
        }
        enableViewTop(false);
    }

    @Override
    public void showHideMenuAfterCall() {
        //not show menu signed when user is signed success
        if (!mTypeString.equals(Contants.SIGNED_SUCCESS_STRING)) {
            if (layoutMenuBot.getVisibility() == View.VISIBLE) {
                Animation out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
                layoutMenuBot.startAnimation(out);
                layoutMenuBot.setVisibility(View.GONE);

                if (mAdapterViewPager.getItem(0) instanceof ContactDetailStep1Fragment) {
                    ((ContactDetailStep1Fragment) mAdapterViewPager.getItem(0)).showXLetter(false);
                }

                enableViewTop(true);
            } else {
                Animation in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
                layoutMenuBot.startAnimation(in);
                layoutMenuBot.setVisibility(View.VISIBLE);
                if (mAdapterViewPager.getItem(0) instanceof ContactDetailStep1Fragment) {
                    ((ContactDetailStep1Fragment) mAdapterViewPager.getItem(0)).showXLetter(true);
                }
                enableViewTop(false);
            }
        }
    }

    private void enableViewTop(boolean isEnable) {
        viewPager.setSwipe(isEnable);
        LinearLayout tabStrip = ((LinearLayout) tabMenu.getChildAt(0));
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return !isEnable;
                }
            });
        }
    }

    @OnClick({R.id.layout_btn_back, R.id.layout_btn_edit})
    public void onClickView(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_btn_back: {
                onBackPressed();
                break;
            }
            case R.id.layout_btn_edit: {
                List<ContactPerson> dataInput = new ArrayList<>();
                dataInput.add(new ContactPerson(false, "",
                        ProjectApplication.getInstance().getContactDetail().data.name, ProjectApplication.getInstance().getContactDetail().data.phone, 0, false));
                Bundle data = new Bundle();
                data.putSerializable("data", (Serializable) dataInput);
                data.putInt("idRelead", mUserId);
                data.putBoolean("isUpdateContact", true);
                if (mIsRecruit)
                    data.putBoolean("isRecruit", true);
                goNextScreen(UpdateContactInfoActivity.class, data, Contants.UPDATE_CONTACT);
                break;
            }
        }
    }



    /*@OnClick({R.id.layout_menu_appointment, R.id.layout_menu_call_later, R.id.layout_menu_cancel})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_menu_appointment: {
                showConfirm("Xác nhận", "Xác nhận chuyển KH sang DS hẹn gặp",
                        "Đồng ý", "Quay lại", SweetAlertDialog.WARNING_TYPE, new CallBackConfirmDialog() {
                            @Override
                            public void DiaglogPositive() {
                                showHideMenuAfterCall();
                            }

                            @Override
                            public void DiaglogNegative() {

                            }
                        });
                break;
            }
            case R.id.layout_menu_call_later: {
                showConfirm("Xác nhận", "Bạn muốn chuyển KH sang DS gọi lại sau",
                        "Đồng ý", "Quay lại", SweetAlertDialog.WARNING_TYPE, new CallBackConfirmDialog() {
                            @Override
                            public void DiaglogPositive() {
                                showHideMenuAfterCall();
                            }

                            @Override
                            public void DiaglogNegative() {

                            }
                        });
                break;
            }
            case R.id.layout_menu_cancel: {
                showConfirm("Xác nhận", "Bạn muốn chuyển KH sang DS từ chối",
                        "Đồng ý", "Quay lại", SweetAlertDialog.WARNING_TYPE, new CallBackConfirmDialog() {
                            @Override
                            public void DiaglogPositive() {
                                showHideMenuAfterCall();
                            }

                            @Override
                            public void DiaglogNegative() {

                            }
                        });
                break;
            }

        }
    }*/
}
