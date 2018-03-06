package manulife.manulifesop.activity.FAGroup.clients.related.contactDetail;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.R;
import manulife.manulifesop.adapter.CustomViewPagerAdapter;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.CustomViewPager;
import manulife.manulifesop.element.callbackInterface.CallBackConfirmDialog;
import manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step1.ContactDetailStep1Fragment;
import manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step2.ContactDetailStep2Fragment;
import manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step3.ContactDetailStep3Fragment;
import manulife.manulifesop.util.Contants;


public class ContactDetailActivity extends BaseActivity<ContactDetailPresenter> implements ContactDetailContract.View {

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
    @BindView(R.id.layout_menu)
    RelativeLayout layoutMenu;
    @BindView(R.id.layout_menu_appointment)
    LinearLayout layoutMenuAppointent;
    @BindView(R.id.layout_menu_call_later)
    LinearLayout layoutMenuCallLater;
    @BindView(R.id.layout_menu_cancel)
    LinearLayout layoutMenuCancel;

    private String mType;

    private CustomViewPagerAdapter mAdapterViewPager;
    private List<BaseFragment> mListFragment;
    private List<String> mTabTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        mType = getIntent().getStringExtra("type");
        mActionListener = new ContactDetailPresenter(this, this);
        initViews();
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
            }
        }
        //set background for refuse customer
        if (mType.equals(Contants.REFUSE)) {
            viewStatusBar.setBackground(getResources().getDrawable(R.color.backgroundGrey));
            imageTop.setBackground(getResources().getDrawable(R.drawable.login_header_bg_disable));
        }
    }

    private void initViewPager() {
        mListFragment = new ArrayList<>();
        mListFragment.add(ContactDetailStep1Fragment.newInstance());
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
        if (layoutMenu.getVisibility() == View.VISIBLE) {
            Animation out = AnimationUtils.loadAnimation(this, R.anim.fade_out);
            layoutMenu.startAnimation(out);
            layoutMenu.setVisibility(View.GONE);
        } else {
            Animation in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
            layoutMenu.startAnimation(in);
            layoutMenu.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.layout_menu_appointment, R.id.layout_menu_call_later, R.id.layout_menu_cancel})
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
    }
}
