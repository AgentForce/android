package manulife.manulifesop.activity.FAGroup.clients.related.contactDetail;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.internal.schedulers.ImmediateThinScheduler;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.related.createEvent.CreateEventActivity;
import manulife.manulifesop.activity.FAGroup.clients.related.signedSuccess.SignedSuccessActivity;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.CustomViewPager;
import manulife.manulifesop.element.callbackInterface.CallBackConfirmDialog;
import manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step1.ContactDetailStep1Fragment;
import manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step1Refuse.ContactDetailStep1RefuseFragment;
import manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step2.ContactDetailStep2Fragment;
import manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step3.ContactDetailStep3Fragment;
import manulife.manulifesop.util.Contants;


public class ContactDetailActivity extends BaseActivity<ContactDetailPresenter> implements ContactDetailContract.View,
        View.OnClickListener{

    @BindView(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @BindView(R.id.layout_btn_back)
    LinearLayout layoutBackButton;
    @BindView(R.id.status_bar)
    View viewStatusBar;
    @BindView(R.id.img_top_background)
    ImageView imageTop;

    @BindView(R.id.txt_title_tyle)
    TextView txtTitleType;

    @BindView(R.id.tabs)
    TabLayout tabMenu;
    @BindView(R.id.view_pager)
    CustomViewPager viewPager;

    //variable for menu
    @BindView(R.id.layout_menu_bot)
    LinearLayout layoutMenuBot;

    @BindView(R.id.layout_menu)
    ViewStub layoutMenu;

    private String mType;
    private String mTypeMenu;

    private CustomViewPagerAdapter mAdapterViewPager;
    private List<BaseFragment> mListFragment;
    private List<String> mTabTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getIntent().getStringExtra("type");
        mTypeMenu = getIntent().getStringExtra("type_menu");

        if (mType.equals(Contants.REFUSE)) {
            setContentView(R.layout.activity_contact_detail_refuse);
        } else {
            setContentView(R.layout.activity_contact_detail);
        }
        mActionListener = new ContactDetailPresenter(this, this);
        initViews();
        initMenuAfterCall();
        initViewPager();
    }

    private void initViews() {
        if (mType != null) {
            switch (mType) {
                case Contants.APPOINTMENT: {
                    txtTitleType.setText("Khách hàng hẹn gặp");
                    break;
                }
                case Contants.CALLLATER: {
                    txtTitleType.setText("Khách hàng gọi lại sau");
                    break;
                }
                case Contants.CONTACT: {
                    txtTitleType.setText("Khách hàng liên hệ");
                    break;
                }
                case Contants.INTRODURE: {
                    txtTitleType.setText("Khách hàng giới thiệu");
                    break;
                }
                case Contants.REFUSE: {
                    txtTitleType.setText("Khách hàng từ chối");
                    break;
                }
                case Contants.SEEN: {
                    txtTitleType.setText("Khách hàng đã hẹn gặp");
                    break;
                }
                case Contants.CONSULTANT: {
                    txtTitleType.setText("Khách hàng tư vấn");
                    break;
                }
                case Contants.CONSULTATION_APPOINTMENT: {
                    txtTitleType.setText("Khách hàng đã hẹn tư vấn");
                    break;
                }
            }
        }
    }

    private void initMenuAfterCall() {
        if(mTypeMenu != null){
            switch (mTypeMenu){
                case Contants.APPOINTMENT_MENU:{
                    layoutMenu.setLayoutResource(R.layout.layout_float_menu_appointment);
                    layoutMenu.inflate();

                    findViewById(R.id.layout_menu_consultant).setOnClickListener(this);
                    findViewById(R.id.layout_menu_appointment_event).setOnClickListener(this);
                    findViewById(R.id.layout_menu_call_later).setOnClickListener(this);
                    findViewById(R.id.layout_menu_cancel).setOnClickListener(this);

                    break;
                }
                case Contants.CONSULTANT_MENU:{
                    layoutMenu.setLayoutResource(R.layout.layout_float_menu_consultant);
                    layoutMenu.inflate();

                    findViewById(R.id.layout_menu_signed).setOnClickListener(this);
                    findViewById(R.id.layout_menu_consultant_appointment).setOnClickListener(this);
                    findViewById(R.id.layout_menu_call_later).setOnClickListener(this);
                    findViewById(R.id.layout_menu_cancel).setOnClickListener(this);

                    break;
                }
                case Contants.CONTACT_MENU:{
                    layoutMenu.setLayoutResource(R.layout.layout_float_menu_contact);
                    layoutMenu.inflate();

                    findViewById(R.id.layout_menu_appointment).setOnClickListener(this);
                    findViewById(R.id.layout_menu_call_later).setOnClickListener(this);
                    findViewById(R.id.layout_menu_cancel).setOnClickListener(this);

                    break;
                }
                case Contants.SIGNED_MENU:{
                    layoutMenu.setLayoutResource(R.layout.layout_float_menu_signed);
                    layoutMenu.inflate();

                    findViewById(R.id.layout_menu_sign_success).setOnClickListener(this);
                    findViewById(R.id.layout_menu_waiting_approve).setOnClickListener(this);
                    findViewById(R.id.layout_menu_applied).setOnClickListener(this);

                    break;
                }
                case Contants.INTRODUCE_MENU:{
                    layoutMenu.setLayoutResource(R.layout.layout_float_menu_introduce);
                    layoutMenu.inflate();

                    findViewById(R.id.layout_menu_contact).setOnClickListener(this);
                    findViewById(R.id.layout_menu_call_later).setOnClickListener(this);
                    findViewById(R.id.layout_menu_cancel).setOnClickListener(this);

                    break;
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            //menu chung
            case R.id.layout_menu_call_later:{
                Toast.makeText(this, "Cân nhắc gọi lại sau", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.layout_menu_cancel:{
                Toast.makeText(this, "Từ chối", Toast.LENGTH_SHORT).show();
                break;
            }
            //menu khách hàng hẹn gặp
            case R.id.layout_menu_consultant:{
                Toast.makeText(this, "Chuyển sang tư vấn", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.layout_menu_appointment_event:{
                //Toast.makeText(this, "Thêm event hẹn gặp", Toast.LENGTH_SHORT).show();
                Bundle data = new Bundle();
                data.putString("type", "Thêm event từ khách hàng hẹn gặp");
                goNextScreen(CreateEventActivity.class,data);
                break;
            }

            //menu khách hàng tư vấn
            case R.id.layout_menu_signed:{
                Toast.makeText(this, "Chuyển sang tư vấn", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.layout_menu_consultant_appointment:{
                Toast.makeText(this, "Hẹn tư vấn", Toast.LENGTH_SHORT).show();
                break;
            }

            //menu khách hàng ký hợp đồng
            case R.id.layout_menu_sign_success:{
                //Toast.makeText(this, "Ký hợp đồng thành công", Toast.LENGTH_SHORT).show();
                goNextScreen(SignedSuccessActivity.class);
                break;
            }
            case R.id.layout_menu_waiting_approve:{
                Toast.makeText(this, "Chờ duyệt", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.layout_menu_applied:{
                Toast.makeText(this, "Đã nộp hồ sơ", Toast.LENGTH_SHORT).show();
                break;
            }

            //menu khách hàng giới thiệu
            case R.id.layout_menu_contact:{
                Toast.makeText(this, "chuyển sang liên hệ", Toast.LENGTH_SHORT).show();
                break;
            }

            //menu liên hệ
            case R.id.layout_menu_appointment:{
                Toast.makeText(this, "Chuyển sang hẹn gặp", Toast.LENGTH_SHORT).show();
                break;
            }


        }
    }

    private void initViewPager() {
        mListFragment = new ArrayList<>();
        if (mType.equals(Contants.REFUSE)) {
            mListFragment.add(ContactDetailStep1RefuseFragment.newInstance());
        } else {
            mListFragment.add(ContactDetailStep1Fragment.newInstance());
        }
        mListFragment.add(ContactDetailStep2Fragment.newInstance());
        mListFragment.add(ContactDetailStep3Fragment.newInstance());

        mTabTitles = new ArrayList<>();
        mTabTitles.add("Cá nhân");
        mTabTitles.add("Sự kiện");
        mTabTitles.add("Hoạt động");

        mAdapterViewPager = new CustomViewPagerAdapter(getSupportFragmentManager(), mListFragment, mTabTitles);
        if (viewPager != null) {
            viewPager.setAdapter(mAdapterViewPager);
            tabMenu.setupWithViewPager(viewPager);
        }

    }

    @Override
    public void showHideMenuAfterCall() {
        if (layoutMenuBot.getVisibility() == View.VISIBLE) {
            Animation out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
            layoutMenuBot.startAnimation(out);
            layoutMenuBot.setVisibility(View.GONE);

            enableViewTop(true);
        } else {
            Animation in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
            layoutMenuBot.startAnimation(in);
            layoutMenuBot.setVisibility(View.VISIBLE);

            enableViewTop(false);
        }
    }

    private void enableViewTop(boolean isEnable){
        viewPager.setSwipe(isEnable);
        LinearLayout tabStrip = ((LinearLayout)tabMenu.getChildAt(0));
        for(int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return !isEnable;
                }
            });
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
