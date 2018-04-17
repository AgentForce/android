package manulife.manulifesop.activity.FAGroup.clients.related.updateContactInfo;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.R;
import manulife.manulifesop.adapter.ObjectData.ContactPerson;
import manulife.manulifesop.api.ObjectResponse.ContactDetail;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.element.callbackInterface.CallBackInformDialog;
import manulife.manulifesop.util.Utils;


public class UpdateContactInfoActivity extends BaseActivity<UpdateContactInfoPresenter> implements UpdateContactInfoContract.View {

    @BindView(R.id.layout_root)
    LinearLayout layoutRoot;

    @BindView(R.id.scrollView)
    NestedScrollView scrollView;

    @BindView(R.id.txt_avatar)
    TextView txtAvatar;

    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_phone)
    TextView txtPhone;

    @BindView(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @BindView(R.id.layout_btn_back)
    LinearLayout layoutBackButton;
    @BindView(R.id.status_bar)
    View viewStatusBar;

    @BindView(R.id.expandable_layout_step1)
    ExpandableLayout expandableLayoutStep1;
    @BindView(R.id.layout_title_step1)
    LinearLayout layoutTitleStep1;
    @BindView(R.id.img_step1)
    ImageView imgStep1;
    @BindView(R.id.img_step1_add)
    ImageView imgStep1Add;


    @BindView(R.id.txt_step1_choose)
    TextView txtStep1Choose;
    @BindView(R.id.rg_step1_line1)
    RadioGroup radioGroupStep1Line1;
    @BindView(R.id.rg_step1_line2)
    RadioGroup radioGroupStep1Line2;
    @BindView(R.id.layout_choose_step1)
    LinearLayout layoutChooseStep1;

    @BindView(R.id.expandable_layout_step2)
    ExpandableLayout expandableLayoutStep2;
    @BindView(R.id.layout_title_step2)
    LinearLayout layoutTitleStep2;
    @BindView(R.id.img_step2)
    ImageView imgStep2;
    @BindView(R.id.img_step2_add)
    ImageView imgStep2Add;

    @BindView(R.id.txt_step2_choose)
    TextView txtStep2Choose;
    @BindView(R.id.rg_step2_line1)
    RadioGroup radioGroupStep2Line1;
    @BindView(R.id.rg_step2_line2)
    RadioGroup radioGroupStep2Line2;
    @BindView(R.id.layout_choose_step2)
    LinearLayout layoutChooseStep2;

    @BindView(R.id.expandable_layout_step3)
    ExpandableLayout expandableLayoutStep3;
    @BindView(R.id.layout_title_step3)
    LinearLayout layoutTitleStep3;
    @BindView(R.id.img_step3)
    ImageView imgStep3;
    @BindView(R.id.img_step3_add)
    ImageView imgStep3Add;

    @BindView(R.id.txt_step3_choose)
    TextView txtStep3Choose;
    @BindView(R.id.rg_step3_line1)
    RadioGroup radioGroupStep3Line1;
    @BindView(R.id.rg_step3_line2)
    RadioGroup radioGroupStep3Line2;
    @BindView(R.id.layout_choose_step3)
    LinearLayout layoutChooseStep3;


    @BindView(R.id.expandable_layout_step4)
    ExpandableLayout expandableLayoutStep4;
    @BindView(R.id.layout_title_step4)
    LinearLayout layoutTitleStep4;
    @BindView(R.id.img_step4)
    ImageView imgStep4;
    @BindView(R.id.img_step4_add)
    ImageView imgStep4Add;

    @BindView(R.id.txt_step4_choose)
    TextView txtStep4Choose;
    @BindView(R.id.rg_step4_line1)
    RadioGroup radioGroupStep4Line1;
    @BindView(R.id.rg_step4_line2)
    RadioGroup radioGroupStep4Line2;
    @BindView(R.id.layout_choose_step4)
    LinearLayout layoutChooseStep4;

    @BindView(R.id.expandable_layout_step5)
    ExpandableLayout expandableLayoutStep5;
    @BindView(R.id.layout_title_step5)
    LinearLayout layoutTitleStep5;
    @BindView(R.id.img_step5)
    ImageView imgStep5;
    @BindView(R.id.img_step5_add)
    ImageView imgStep5Add;

    @BindView(R.id.txt_step5_choose)
    TextView txtStep5Choose;
    @BindView(R.id.rg_step5_line1)
    RadioGroup radioGroupStep5Line1;
    @BindView(R.id.rg_step5_line2)
    RadioGroup radioGroupStep5Line2;
    @BindView(R.id.layout_choose_step5)
    LinearLayout layoutChooseStep5;

    @BindView(R.id.edt_note)
    EditText edtNote;

    @BindView(R.id.btn_ok)
    Button btnOK;

    private List<ContactPerson> mDataChoosed;
    private int mPosition = 0;

    //variable for change relead to contact
    private boolean mIsUpdateContact;
    private boolean mIsChangeToContact;
    private int mReleadID;

    //variable for sm recruit
    private boolean mIsRecruit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact_update_info);
        hideKeyboardOutside(layoutRoot);
        mActionListener = new UpdateContactInfoPresenter(this, this);
        mDataChoosed = (List<ContactPerson>) getIntent().getSerializableExtra("data");
        //get data for change relead to contact
        mIsChangeToContact = getIntent().getBooleanExtra("isChangeToContact", false);
        mIsUpdateContact = getIntent().getBooleanExtra("isUpdateContact", false);
        mReleadID = getIntent().getIntExtra("idRelead", 0);

        mIsRecruit = getIntent().getBooleanExtra("isRecruit", false);

        setupSupportForApp();
        initViews();

        if (mIsUpdateContact) {
            initViewsUpdate();
        }
    }

    private void initViewsUpdate() {
        ContactDetail data = ProjectApplication.getInstance().getContactDetail();
        txtStep1Choose.setText(ProjectApplication.getInstance().getAgeString(data.data.age));
        txtStep1Choose.setTag(data.data.age);
        imgStep1.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
        layoutChooseStep1.setVisibility(View.VISIBLE);

        txtStep2Choose.setText(ProjectApplication.getInstance().getIncomeString(data.data.incomeMonthly));
        txtStep2Choose.setTag(data.data.incomeMonthly);
        imgStep2.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
        layoutChooseStep2.setVisibility(View.VISIBLE);

        txtStep3Choose.setText(ProjectApplication.getInstance().getMarriageString(data.data.maritalStatus));
        txtStep3Choose.setTag(data.data.maritalStatus);
        imgStep3.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
        layoutChooseStep3.setVisibility(View.VISIBLE);

        txtStep4Choose.setText(ProjectApplication.getInstance().getRelationshipString(data.data.relationship));
        txtStep4Choose.setTag(data.data.relationship);
        imgStep4.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
        layoutChooseStep4.setVisibility(View.VISIBLE);

        txtStep5Choose.setText(ProjectApplication.getInstance().getRelationshipString(data.data.source));
        txtStep5Choose.setTag(data.data.source);
        imgStep5.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
        layoutChooseStep5.setVisibility(View.VISIBLE);

        edtNote.setText(data.data.description);

    }

    private void initViews() {

        txtAvatar.setText(mDataChoosed.get(mPosition).getName().substring(0, 1));
        txtName.setText(mDataChoosed.get(mPosition).getName());
        txtPhone.setText(mDataChoosed.get(mPosition).getPhone());

        radioGroupStep1Line1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    RadioButton tmp = findViewById(checkedId);
                    txtStep1Choose.setText(tmp.getText());
                    txtStep1Choose.setTag(tmp.getTag());

                    imgStep1.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
                    expandableLayoutStep1.collapse(true);
                    imgStep1Add.setImageResource(R.drawable.ic_add);
                    layoutChooseStep1.setVisibility(View.VISIBLE);

                    initListenerStep1Line2();
                }
            }
        });
        radioGroupStep1Line2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    RadioButton tmp = findViewById(checkedId);
                    txtStep1Choose.setText(tmp.getText());
                    txtStep1Choose.setTag(tmp.getTag());
                    imgStep1.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
                    expandableLayoutStep1.collapse(true);
                    imgStep1Add.setImageResource(R.drawable.ic_add);
                    layoutChooseStep1.setVisibility(View.VISIBLE);
                    initListenerStep1Line1();
                }
            }
        });

        radioGroupStep2Line1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    RadioButton tmp = findViewById(checkedId);
                    txtStep2Choose.setText(tmp.getText());
                    txtStep2Choose.setTag(tmp.getTag());
                    imgStep2.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
                    expandableLayoutStep2.collapse(true);
                    imgStep2Add.setImageResource(R.drawable.ic_add);
                    layoutChooseStep2.setVisibility(View.VISIBLE);
                    initListenerStep2Line2();
                }
            }
        });
        radioGroupStep2Line2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    RadioButton tmp = findViewById(checkedId);
                    txtStep2Choose.setText(tmp.getText());
                    txtStep2Choose.setTag(tmp.getTag());
                    imgStep2.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
                    expandableLayoutStep2.collapse(true);
                    imgStep2Add.setImageResource(R.drawable.ic_add);
                    layoutChooseStep2.setVisibility(View.VISIBLE);
                    initListenerStep2Line1();
                }
            }
        });

        radioGroupStep3Line1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    RadioButton tmp = findViewById(checkedId);
                    txtStep3Choose.setText(tmp.getText());
                    txtStep3Choose.setTag(tmp.getTag());
                    imgStep3.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
                    expandableLayoutStep3.collapse(true);
                    imgStep3Add.setImageResource(R.drawable.ic_add);
                    layoutChooseStep3.setVisibility(View.VISIBLE);
                    initListenerStep3Line2();
                }
            }
        });
        radioGroupStep3Line2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    RadioButton tmp = findViewById(checkedId);
                    txtStep3Choose.setText(tmp.getText());
                    txtStep3Choose.setTag(tmp.getTag());
                    imgStep3.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
                    expandableLayoutStep3.collapse(true);
                    imgStep3Add.setImageResource(R.drawable.ic_add);
                    layoutChooseStep3.setVisibility(View.VISIBLE);
                    initListenerStep3Line1();
                }
            }
        });

        radioGroupStep4Line1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    RadioButton tmp = findViewById(checkedId);
                    txtStep4Choose.setText(tmp.getText());
                    txtStep4Choose.setTag(tmp.getTag());
                    imgStep4.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
                    expandableLayoutStep4.collapse(true);
                    imgStep4Add.setImageResource(R.drawable.ic_add);
                    layoutChooseStep4.setVisibility(View.VISIBLE);
                    initListenerStep4Line2();
                }
            }
        });
        radioGroupStep4Line2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    RadioButton tmp = findViewById(checkedId);
                    txtStep4Choose.setText(tmp.getText());
                    txtStep4Choose.setTag(tmp.getTag());
                    imgStep4.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
                    expandableLayoutStep4.collapse(true);
                    imgStep4Add.setImageResource(R.drawable.ic_add);
                    layoutChooseStep4.setVisibility(View.VISIBLE);
                    initListenerStep4Line1();
                }
            }
        });

        radioGroupStep5Line1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    RadioButton tmp = findViewById(checkedId);
                    txtStep5Choose.setText(tmp.getText());
                    txtStep5Choose.setTag(tmp.getTag());
                    imgStep5.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
                    expandableLayoutStep5.collapse(true);
                    imgStep5Add.setImageResource(R.drawable.ic_add);
                    layoutChooseStep5.setVisibility(View.VISIBLE);
                    initListenerStep5Line2();
                }
            }
        });
        radioGroupStep5Line2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    RadioButton tmp = findViewById(checkedId);
                    txtStep5Choose.setText(tmp.getText());
                    txtStep5Choose.setTag(tmp.getTag());
                    imgStep5.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
                    expandableLayoutStep5.collapse(true);
                    imgStep5Add.setImageResource(R.drawable.ic_add);
                    layoutChooseStep5.setVisibility(View.VISIBLE);
                    initListenerStep5Line1();
                }
            }
        });
    }

    public void initListenerStep1Line1() {
        radioGroupStep1Line1.setOnCheckedChangeListener(null);
        radioGroupStep1Line1.clearCheck();
        radioGroupStep1Line1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton tmp = findViewById(checkedId);
                txtStep1Choose.setText(tmp.getText());
                txtStep1Choose.setTag(tmp.getTag());
                imgStep1.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
                expandableLayoutStep1.collapse(true);
                imgStep1Add.setImageResource(R.drawable.ic_add);
                layoutChooseStep1.setVisibility(View.VISIBLE);
                initListenerStep1Line2();
            }
        });
    }

    public void initListenerStep1Line2() {
        radioGroupStep1Line2.setOnCheckedChangeListener(null);
        radioGroupStep1Line2.clearCheck();
        radioGroupStep1Line2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton tmp = findViewById(checkedId);
                txtStep1Choose.setText(tmp.getText());
                txtStep1Choose.setTag(tmp.getTag());
                imgStep1.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
                expandableLayoutStep1.collapse(true);
                imgStep1Add.setImageResource(R.drawable.ic_add);
                layoutChooseStep1.setVisibility(View.VISIBLE);
                initListenerStep1Line1();
            }
        });
    }

    public void initListenerStep2Line1() {
        radioGroupStep2Line1.setOnCheckedChangeListener(null);
        radioGroupStep2Line1.clearCheck();
        radioGroupStep2Line1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton tmp = findViewById(checkedId);
                txtStep2Choose.setText(tmp.getText());
                txtStep2Choose.setTag(tmp.getTag());
                imgStep2.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
                expandableLayoutStep2.collapse(true);
                imgStep2Add.setImageResource(R.drawable.ic_add);
                layoutChooseStep2.setVisibility(View.VISIBLE);
                initListenerStep2Line2();
            }
        });
    }

    public void initListenerStep2Line2() {
        radioGroupStep2Line2.setOnCheckedChangeListener(null);
        radioGroupStep2Line2.clearCheck();
        radioGroupStep2Line2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton tmp = findViewById(checkedId);
                txtStep2Choose.setText(tmp.getText());
                txtStep2Choose.setTag(tmp.getTag());
                imgStep2.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
                expandableLayoutStep2.collapse(true);
                imgStep2Add.setImageResource(R.drawable.ic_add);
                layoutChooseStep2.setVisibility(View.VISIBLE);
                initListenerStep2Line1();
            }
        });
    }

    public void initListenerStep3Line1() {
        radioGroupStep3Line1.setOnCheckedChangeListener(null);
        radioGroupStep3Line1.clearCheck();
        radioGroupStep3Line1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton tmp = findViewById(checkedId);
                txtStep3Choose.setText(tmp.getText());
                txtStep3Choose.setTag(tmp.getTag());
                imgStep3.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
                expandableLayoutStep3.collapse(true);
                imgStep3Add.setImageResource(R.drawable.ic_add);
                layoutChooseStep3.setVisibility(View.VISIBLE);
                initListenerStep3Line2();
            }
        });
    }

    public void initListenerStep3Line2() {
        radioGroupStep3Line2.setOnCheckedChangeListener(null);
        radioGroupStep3Line2.clearCheck();
        radioGroupStep3Line2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton tmp = findViewById(checkedId);
                txtStep3Choose.setText(tmp.getText());
                txtStep3Choose.setTag(tmp.getTag());
                imgStep3.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
                expandableLayoutStep3.collapse(true);
                imgStep3Add.setImageResource(R.drawable.ic_add);
                layoutChooseStep3.setVisibility(View.VISIBLE);
                initListenerStep3Line1();
            }
        });
    }

    public void initListenerStep4Line1() {
        radioGroupStep4Line1.setOnCheckedChangeListener(null);
        radioGroupStep4Line1.clearCheck();
        radioGroupStep4Line1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton tmp = findViewById(checkedId);
                txtStep4Choose.setText(tmp.getText());
                txtStep4Choose.setTag(tmp.getTag());
                imgStep4.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
                expandableLayoutStep4.collapse(true);
                imgStep4Add.setImageResource(R.drawable.ic_add);
                layoutChooseStep4.setVisibility(View.VISIBLE);
                initListenerStep4Line2();
            }
        });
    }

    public void initListenerStep4Line2() {
        radioGroupStep4Line2.setOnCheckedChangeListener(null);
        radioGroupStep4Line2.clearCheck();
        radioGroupStep4Line2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton tmp = findViewById(checkedId);
                txtStep4Choose.setText(tmp.getText());
                txtStep4Choose.setTag(tmp.getTag());
                imgStep4.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
                expandableLayoutStep4.collapse(true);
                imgStep4Add.setImageResource(R.drawable.ic_add);
                layoutChooseStep4.setVisibility(View.VISIBLE);
                initListenerStep4Line1();
            }
        });
    }

    public void initListenerStep5Line1() {
        radioGroupStep5Line1.setOnCheckedChangeListener(null);
        radioGroupStep5Line1.clearCheck();
        radioGroupStep5Line1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton tmp = findViewById(checkedId);
                txtStep5Choose.setText(tmp.getText());
                txtStep5Choose.setTag(tmp.getTag());
                imgStep5.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
                expandableLayoutStep5.collapse(true);
                imgStep5Add.setImageResource(R.drawable.ic_add);
                layoutChooseStep5.setVisibility(View.VISIBLE);
                initListenerStep5Line2();
            }
        });
    }

    public void initListenerStep5Line2() {
        radioGroupStep5Line2.setOnCheckedChangeListener(null);
        radioGroupStep5Line2.clearCheck();
        radioGroupStep5Line2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton tmp = findViewById(checkedId);
                txtStep5Choose.setText(tmp.getText());
                txtStep5Choose.setTag(tmp.getTag());
                imgStep5.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
                expandableLayoutStep5.collapse(true);
                imgStep5Add.setImageResource(R.drawable.ic_add);
                layoutChooseStep5.setVisibility(View.VISIBLE);
                initListenerStep5Line1();
            }
        });
    }

    private void setupSupportForApp() {
        //txtActionbarTitle.setText(getResources().getString(R.string.activity_create_plan_title_actionbar));
        txtActionbarTitle.setText("Thông tin khách hàng (1/" + mDataChoosed.size() + ")");
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

    @Override
    public boolean validateInput() {
        if (txtStep1Choose.getText().equals("choose")) {
            showMessage("Thông báo", "Chưa chọn độ tuổi!", SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        if (txtStep2Choose.getText().equals("choose")) {
            showMessage("Thông báo", "Chưa chọn thu nhập!", SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        if (txtStep3Choose.getText().equals("choose")) {
            showMessage("Thông báo", "Chưa chọn tình trạng hôn nhân!", SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        if (txtStep4Choose.getText().equals("choose")) {
            showMessage("Thông báo", "Chưa chọn mối quan hệ!", SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        if (txtStep5Choose.getText().equals("choose")) {
            showMessage("Thông báo", "Chưa chọn nguồn!", SweetAlertDialog.WARNING_TYPE);
            return false;
        }
        return true;
    }

    @Override
    public void loadNextContact(int position) {
        if (position == mDataChoosed.size())
            //showMessage("Thông báo", "Đã cập nhật tất cả thông tin!", SweetAlertDialog.SUCCESS_TYPE);
            showInform("Thông báo", "Đã cập nhật tất cả thông tin!", "OK", SweetAlertDialog.SUCCESS_TYPE
                    , new CallBackInformDialog() {
                        @Override
                        public void DiaglogPositive() {
                            setResult(RESULT_OK);
                            finish();
                        }
                    });
        else {
            mPosition = position;

            txtAvatar.setText(mDataChoosed.get(mPosition).getName().substring(0, 1));
            txtName.setText(mDataChoosed.get(mPosition).getName());
            txtPhone.setText(mDataChoosed.get(mPosition).getPhone());

            Animation out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);

            txtActionbarTitle.setText("Thông tin khách hàng (" + (position + 1) + "/" + mDataChoosed.size() + ")");
            txtStep1Choose.setText("choose");
            layoutChooseStep1.startAnimation(out);
            layoutChooseStep1.startAnimation(out);
            layoutChooseStep1.setVisibility(View.GONE);
            expandableLayoutStep1.collapse();
            imgStep1.setBackgroundColor(getResources().getColor(R.color.color_dashboard_introduce));
            imgStep1Add.setImageResource(R.drawable.ic_add);

            initListenerStep1Line1();
            initListenerStep1Line2();

            txtStep2Choose.setText("choose");
            layoutChooseStep2.startAnimation(out);
            layoutChooseStep2.startAnimation(out);
            layoutChooseStep2.setVisibility(View.GONE);
            expandableLayoutStep2.collapse();
            imgStep2.setBackgroundColor(getResources().getColor(R.color.color_dashboard_introduce));
            imgStep2Add.setImageResource(R.drawable.ic_add);

            initListenerStep2Line1();
            initListenerStep2Line2();

            txtStep3Choose.setText("choose");
            layoutChooseStep3.startAnimation(out);
            layoutChooseStep3.startAnimation(out);
            layoutChooseStep3.setVisibility(View.GONE);
            expandableLayoutStep3.collapse();
            imgStep3.setBackgroundColor(getResources().getColor(R.color.color_dashboard_introduce));
            imgStep3Add.setImageResource(R.drawable.ic_add);

            initListenerStep3Line1();
            initListenerStep3Line2();

            txtStep4Choose.setText("choose");
            layoutChooseStep4.startAnimation(out);
            layoutChooseStep4.startAnimation(out);
            layoutChooseStep4.setVisibility(View.GONE);
            expandableLayoutStep4.collapse();
            imgStep4.setBackgroundColor(getResources().getColor(R.color.color_dashboard_introduce));
            imgStep4Add.setImageResource(R.drawable.ic_add);

            initListenerStep4Line1();
            initListenerStep4Line2();

            txtStep5Choose.setText("choose");
            layoutChooseStep5.startAnimation(out);
            layoutChooseStep5.startAnimation(out);
            layoutChooseStep5.setVisibility(View.GONE);
            expandableLayoutStep5.collapse();
            imgStep5.setBackgroundColor(getResources().getColor(R.color.color_dashboard_introduce));
            imgStep5Add.setImageResource(R.drawable.ic_add);

            initListenerStep5Line1();
            initListenerStep5Line2();

            edtNote.setText("");

            Utils.smoothScrollViewToPosition(this, scrollView, 0);

        }
    }

    @Override
    public void finishChangeToContact() {
        setResult(RESULT_OK);
        finish();
    }

    @OnClick({R.id.layout_btn_back, R.id.layout_title_step1, R.id.layout_title_step2,
            R.id.layout_title_step3, R.id.layout_title_step4, R.id.layout_title_step5,
            R.id.btn_ok})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_btn_back: {
                onBackPressed();
                break;
            }
            case R.id.layout_title_step1: {
                if (!expandableLayoutStep1.isExpanded()) {
                    expandableLayoutStep1.expand(true);

                    Animation out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
                    layoutChooseStep1.startAnimation(out);
                    layoutChooseStep1.setVisibility(View.GONE);
                    imgStep1.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
                    imgStep1Add.setImageResource(R.drawable.ic_sub);
                    //Utils.smoothScrollViewToPosition(getApplicationContext(),scrollView,layoutTitleStep2.getBottom());
                } else {
                    expandableLayoutStep1.collapse(true);
                    if (!txtStep1Choose.getText().equals("choose")) {
                        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
                        layoutChooseStep1.startAnimation(in);
                        layoutChooseStep1.setVisibility(View.VISIBLE);
                    } else {
                        imgStep1.setBackgroundColor(getResources().getColor(R.color.color_dashboard_introduce));
                    }
                    imgStep1Add.setImageResource(R.drawable.ic_add);
                }
                break;
            }
            case R.id.layout_title_step2: {
                if (!expandableLayoutStep2.isExpanded()) {
                    expandableLayoutStep2.expand(true);

                    Animation out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
                    layoutChooseStep2.startAnimation(out);
                    layoutChooseStep2.setVisibility(View.GONE);

                    imgStep2.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
                    imgStep2Add.setImageResource(R.drawable.ic_sub);
                    //Utils.smoothScrollViewToPosition(getApplicationContext(),scrollView,layoutTitleStep3.getBottom());
                } else {
                    expandableLayoutStep2.collapse(true);
                    if (!txtStep2Choose.getText().equals("choose")) {
                        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
                        layoutChooseStep2.startAnimation(in);
                        layoutChooseStep2.setVisibility(View.VISIBLE);
                    } else {
                        imgStep2.setBackgroundColor(getResources().getColor(R.color.color_dashboard_introduce));
                    }
                    imgStep2Add.setImageResource(R.drawable.ic_add);
                }
                break;
            }
            case R.id.layout_title_step3: {
                if (!expandableLayoutStep3.isExpanded()) {
                    expandableLayoutStep3.expand(true);

                    Animation out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
                    layoutChooseStep3.startAnimation(out);
                    layoutChooseStep3.setVisibility(View.GONE);

                    imgStep3.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
                    imgStep3Add.setImageResource(R.drawable.ic_sub);
                    //Utils.smoothScrollViewToPosition(getApplicationContext(),scrollView,layoutTitleStep4.getBottom());
                } else {
                    expandableLayoutStep3.collapse(true);
                    if (!txtStep3Choose.getText().equals("choose")) {
                        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
                        layoutChooseStep3.startAnimation(in);
                        layoutChooseStep3.setVisibility(View.VISIBLE);
                    } else {
                        imgStep3.setBackgroundColor(getResources().getColor(R.color.color_dashboard_introduce));
                    }
                    imgStep3Add.setImageResource(R.drawable.ic_add);
                }
                break;
            }
            case R.id.layout_title_step4: {
                if (!expandableLayoutStep4.isExpanded()) {
                    expandableLayoutStep4.expand(true);

                    Animation out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
                    layoutChooseStep4.startAnimation(out);
                    layoutChooseStep4.setVisibility(View.GONE);

                    imgStep4.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
                    imgStep4Add.setImageResource(R.drawable.ic_sub);
                    //Utils.smoothScrollViewToPosition(getApplicationContext(),scrollView,layoutTitleStep5.getBottom());
                } else {
                    expandableLayoutStep4.collapse(true);
                    if (!txtStep4Choose.getText().equals("choose")) {
                        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
                        layoutChooseStep4.startAnimation(in);
                        layoutChooseStep4.setVisibility(View.VISIBLE);
                    } else {
                        imgStep4.setBackgroundColor(getResources().getColor(R.color.color_dashboard_introduce));
                    }
                    imgStep4Add.setImageResource(R.drawable.ic_add);
                }
                break;
            }
            case R.id.layout_title_step5: {
                if (!expandableLayoutStep5.isExpanded()) {
                    expandableLayoutStep5.expand(true);
                    Animation out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
                    layoutChooseStep5.startAnimation(out);
                    layoutChooseStep5.setVisibility(View.GONE);
                    imgStep5.setBackgroundColor(getResources().getColor(R.color.color_dashboard_sign));
                    imgStep5Add.setImageResource(R.drawable.ic_sub);

                    Utils.smoothScrollViewToPosition(getApplicationContext(), scrollView, edtNote.getBottom());

                } else {
                    expandableLayoutStep5.collapse(true);
                    if (!txtStep5Choose.getText().equals("choose")) {
                        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
                        layoutChooseStep5.startAnimation(in);
                        layoutChooseStep5.setVisibility(View.VISIBLE);
                    } else {
                        imgStep5.setBackgroundColor(getResources().getColor(R.color.color_dashboard_introduce));
                    }
                    imgStep5Add.setImageResource(R.drawable.ic_add);
                }
                break;
            }
            case R.id.btn_ok: {
                if (validateInput()) {
                    //check if is process for add contact or change relead to contact
                    if (mIsChangeToContact) {
                        //check is from contact or recruit
                        if (mIsRecruit)
                            mActionListener.changeReleadToRecruit(mReleadID, ProjectApplication.getInstance().getCampaignWeekId(),
                                    Integer.valueOf(txtStep1Choose.getTag().toString()), 0, Integer.valueOf(txtStep2Choose.getTag().toString()),
                                    Integer.valueOf(txtStep3Choose.getTag().toString()),
                                    Integer.valueOf(txtStep4Choose.getTag().toString()),
                                    Integer.valueOf(txtStep5Choose.getTag().toString()),
                                    edtNote.getText().toString());
                        else
                            mActionListener.changeReleadToContact(mReleadID, ProjectApplication.getInstance().getCampaignWeekId(),
                                    Integer.valueOf(txtStep1Choose.getTag().toString()), 0, Integer.valueOf(txtStep2Choose.getTag().toString()),
                                    Integer.valueOf(txtStep3Choose.getTag().toString()),
                                    Integer.valueOf(txtStep4Choose.getTag().toString()),
                                    Integer.valueOf(txtStep5Choose.getTag().toString()),
                                    edtNote.getText().toString());
                    } else if (mIsUpdateContact) {
                        //check is from contact or recruit
                        if (mIsRecruit)
                            mActionListener.updateRecruitInfo(mReleadID, txtName.getText().toString(), Integer.valueOf(txtStep1Choose.getTag().toString()),
                                    0, Integer.valueOf(txtStep2Choose.getTag().toString()), Integer.valueOf(txtStep3Choose.getTag().toString()), Integer.valueOf(txtStep4Choose.getTag().toString()),
                                    Integer.valueOf(txtStep5Choose.getTag().toString()), edtNote.getText().toString());
                        else
                            mActionListener.updateContactInfo(mReleadID, txtName.getText().toString(), Integer.valueOf(txtStep1Choose.getTag().toString()),
                                    0, Integer.valueOf(txtStep2Choose.getTag().toString()), Integer.valueOf(txtStep3Choose.getTag().toString()), Integer.valueOf(txtStep4Choose.getTag().toString()),
                                    Integer.valueOf(txtStep5Choose.getTag().toString()), edtNote.getText().toString());
                    } else {
                        //check is from contact or recruit
                        if (mIsRecruit)
                            mActionListener.addRecruitInfo(mPosition, txtName.getText().toString(), txtPhone.getText().toString(), Integer.valueOf(txtStep1Choose.getTag().toString()),
                                    0, Integer.valueOf(txtStep2Choose.getTag().toString()), Integer.valueOf(txtStep3Choose.getTag().toString()), Integer.valueOf(txtStep4Choose.getTag().toString()),
                                    Integer.valueOf(txtStep5Choose.getTag().toString()), edtNote.getText().toString());
                        else
                            mActionListener.addContactInfo(mPosition, txtName.getText().toString(), txtPhone.getText().toString(), Integer.valueOf(txtStep1Choose.getTag().toString()),
                                    0, Integer.valueOf(txtStep2Choose.getTag().toString()), Integer.valueOf(txtStep3Choose.getTag().toString()), Integer.valueOf(txtStep4Choose.getTag().toString()),
                                    Integer.valueOf(txtStep5Choose.getTag().toString()), edtNote.getText().toString());
                    }
                }
                break;
            }
        }
    }
}
