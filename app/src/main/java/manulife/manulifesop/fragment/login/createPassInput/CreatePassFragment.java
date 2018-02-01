package manulife.manulifesop.fragment.login.createPassInput;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.login.LoginActivity;
import manulife.manulifesop.adapter.PasswordAdapter;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.fragment.login.phoneInput.PhoneContract;
import manulife.manulifesop.fragment.login.phoneInput.PhonePresent;


/**
 * Created by Chick on 10/27/2017.
 */

public class CreatePassFragment extends BaseFragment<LoginActivity, CreatePassPresent> implements CreatePassContract.View {


    /*@BindView(R.id.btn_next)
    Button btnNext;*/
    @BindView(R.id.list_pass)
    RecyclerView listPass;
    @BindView(R.id.edt_pass)
    EditText edtPass;

    List<Boolean> mDataList;
    PasswordAdapter mAdapter;

    public static CreatePassFragment newInstance() {
        Bundle args = new Bundle();
        CreatePassFragment fragment = new CreatePassFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_login_createpass;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new CreatePassPresent(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPasswordView();
    }

    public void initPasswordView()
    {
        //init list password
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        listPass.setLayoutManager(layoutManager);

        mDataList = new ArrayList<>();
        mDataList.add(false);
        mDataList.add(false);
        mDataList.add(false);
        mDataList.add(false);
        mDataList.add(false);
        mDataList.add(false);

        mAdapter = new PasswordAdapter(getContext(),mDataList);
        listPass.setAdapter(mAdapter);

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

                for(int i=1;i<=mDataList.size();i++)
                {
                    if(i <= tmpText.length()) {
                        mDataList.set(i-1, true);
                    }else
                    {
                        mDataList.set(i-1,false);
                    }
                }
                mAdapter.notifyDataSetChanged();
                if(tmpText.length()>=6)
                {
                    mActivity.showFragmentConfirmPass(tmpText);
                }
            }
        });

    }

    /*@OnClick(R.id.btn_next)
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_next: {
                if (edtPass.getText().toString().length() >= 6) {
                    //mActivity.showFragmentPhoneInput(edtAgencyName.getText().toString());
                    //mActivity.showCheckingUser(edtPhone.getText().toString());
                    //mActivity.showCheckOTP(edtOTP.getText().toString());

                } else {
                    mActivity.showMessage("Thông báo", "Mật khẩu có 6 ký tự!", SweetAlertDialog.WARNING_TYPE);
                }
                break;
            }
        }
    }*/
}
