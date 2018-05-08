package manulife.manulifesop.activity.FAGroup.personal.setting.changePass;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.personal.setting.SettingContract;
import manulife.manulifesop.activity.FAGroup.personal.setting.SettingPresenter;
import manulife.manulifesop.adapter.PasswordAdapter;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.element.RecyclerItemClickListener;
import manulife.manulifesop.util.SOPSharedPreferences;


public class ChangePassActivity extends BaseActivity<ChangePassPresenter> implements ChangePassContract.View {

    @BindView(R.id.layout_root)
    LinearLayout layoutRoot;

    @BindView(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @BindView(R.id.layout_btn_back)
    LinearLayout layoutBackButton;
    @BindView(R.id.status_bar)
    View viewStatusBar;

    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_employee_id)
    TextView txtUserID;


    @BindView(R.id.list_old_pass)
    RecyclerView listOldPass;
    @BindView(R.id.edt_old_pass)
    EditText edtOldPass;
    List<Boolean> mDataListOldPass;
    PasswordAdapter mAdapterOldPass;

    @BindView(R.id.list_pass)
    RecyclerView listPass;
    @BindView(R.id.edt_pass)
    EditText edtPass;
    List<Boolean> mDataListPass;
    PasswordAdapter mAdapterPass;

    @BindView(R.id.list_pass_confirm)
    RecyclerView listPassConfirm;
    @BindView(R.id.edt_pass_confirm)
    EditText edtPassConfirm;
    List<Boolean> mDataListPassConfirm;
    PasswordAdapter mAdapterPassConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        mActionListener = new ChangePassPresenter(this, this);
        hideKeyboardOutside(layoutRoot);
        initViews();
        setupSupportForApp();
        initOldPasswordView();
        initPasswordView();
        initPasswordViewConfirm();
    }

    private void initViews(){
        txtName.setText(ProjectApplication.getInstance().getUserFullName());
        txtUserID.setText(SOPSharedPreferences.getInstance(this).getUserName());
    }

    private void setupSupportForApp() {
        txtActionbarTitle.setText("Thay đổi mật khẩu");
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

    private void initOldPasswordView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        listOldPass.setLayoutManager(layoutManager);

        mDataListOldPass = new ArrayList<>();
        mDataListOldPass.add(false);
        mDataListOldPass.add(false);
        mDataListOldPass.add(false);
        mDataListOldPass.add(false);
        mDataListOldPass.add(false);
        mDataListOldPass.add(false);

        mAdapterOldPass = new PasswordAdapter(getApplicationContext(), mDataListOldPass);
        listOldPass.setAdapter(mAdapterOldPass);

        //list pass listener
        listOldPass.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), listOldPass, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edtOldPass, InputMethodManager.SHOW_IMPLICIT);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        //add listener txt_pass
        edtOldPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String tmpText = editable.toString();

                for (int i = 1; i <= mDataListOldPass.size(); i++) {
                    if (i <= tmpText.length()) {
                        mDataListOldPass.set(i - 1, true);
                    } else {
                        mDataListOldPass.set(i - 1, false);
                    }
                }
                mAdapterOldPass.notifyDataSetChanged();
            }
        });
    }

    private void initPasswordView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        listPass.setLayoutManager(layoutManager);

        mDataListPass = new ArrayList<>();
        mDataListPass.add(false);
        mDataListPass.add(false);
        mDataListPass.add(false);
        mDataListPass.add(false);
        mDataListPass.add(false);
        mDataListPass.add(false);

        mAdapterPass = new PasswordAdapter(getApplicationContext(), mDataListPass);
        listPass.setAdapter(mAdapterPass);

        //list pass listener
        listPass.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), listPass, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edtPass, InputMethodManager.SHOW_IMPLICIT);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        //add listener txt_pass
        edtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String tmpText = editable.toString();

                for (int i = 1; i <= mDataListPass.size(); i++) {
                    if (i <= tmpText.length()) {
                        mDataListPass.set(i - 1, true);
                    } else {
                        mDataListPass.set(i - 1, false);
                    }
                }
                mAdapterPass.notifyDataSetChanged();
            }
        });
    }

    private void initPasswordViewConfirm() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        listPassConfirm.setLayoutManager(layoutManager);

        mDataListPassConfirm = new ArrayList<>();
        mDataListPassConfirm.add(false);
        mDataListPassConfirm.add(false);
        mDataListPassConfirm.add(false);
        mDataListPassConfirm.add(false);
        mDataListPassConfirm.add(false);
        mDataListPassConfirm.add(false);

        mAdapterPassConfirm = new PasswordAdapter(getApplicationContext(), mDataListPassConfirm);
        listPassConfirm.setAdapter(mAdapterPassConfirm);

        //list pass listener
        listPassConfirm.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), listPassConfirm, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(edtPassConfirm, InputMethodManager.SHOW_IMPLICIT);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        //add listener txt_pass
        edtPassConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String tmpText = editable.toString();

                for (int i = 1; i <= mDataListPassConfirm.size(); i++) {
                    if (i <= tmpText.length()) {
                        mDataListPassConfirm.set(i - 1, true);
                    } else {
                        mDataListPassConfirm.set(i - 1, false);
                    }
                }
                mAdapterPassConfirm.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void finishChangePass() {
        finish();
    }

    @OnClick({R.id.layout_btn_back, R.id.btn_ok})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_btn_back: {
                onBackPressed();
                break;
            }
            case R.id.btn_ok: {
                if (mActionListener.validateData(
                        edtOldPass.getText().toString(),
                        edtPass.getText().toString(),
                        edtPassConfirm.getText().toString())) {
                    mActionListener.changePass(
                            edtOldPass.getText().toString(),
                            edtPass.getText().toString());
                }
                break;
            }
        }
    }
}
