package manulife.manulifesop.fragment.login.phoneInput;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.login.LoginActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.fragment.login.agencyInput.AgencyContract;
import manulife.manulifesop.fragment.login.agencyInput.AgencyPresent;


/**
 * Created by Chick on 10/27/2017.
 */

public class PhoneFragment extends BaseFragment<LoginActivity, PhonePresent> implements PhoneContract.View {


    @BindView(R.id.btn_next)
    Button btnNext;

    @BindView(R.id.edt_phone)
    EditText edtPhone;

    public static PhoneFragment newInstance() {
        Bundle args = new Bundle();
        PhoneFragment fragment = new PhoneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_login_phone;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new PhonePresent(this,getContext());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void showFragmentOTPInput() {
        mActivity.showFragmentOTPInput();
    }

    @Override
    public void showFragmentPassInput() {
        mActivity.showFragmentPassInput();
    }

    @OnClick(R.id.btn_next)
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_next: {
                if (edtPhone.getText().toString().length() > 0) {
                    mActivity.setPhoneInputed(edtPhone.getText().toString());
                    String phone = "84" + edtPhone.getText().toString().substring(1);
                    mActionListener.checkUser(mActivity.getmAgencyID(),phone);
                } else {
                    mActivity.showMessage("Thông báo", "Chưa nhập số điện thoại!", SweetAlertDialog.WARNING_TYPE);
                }
                break;
            }
        }
    }
}
