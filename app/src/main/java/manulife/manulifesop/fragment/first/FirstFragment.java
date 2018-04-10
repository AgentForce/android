package manulife.manulifesop.fragment.first;

import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.first.FirstActivity;
import manulife.manulifesop.activity.login.LoginActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.fragment.login.agencyInput.AgencyContract;
import manulife.manulifesop.fragment.login.agencyInput.AgencyPresent;


/**
 * Created by Chick on 10/27/2017.
 */

public class FirstFragment extends BaseFragment<FirstActivity, FirstPresent> implements FirstContract.View {

    @BindView(R.id.cb_agree)
    AppCompatCheckBox cbAgree;

    @BindView(R.id.layout_permission)
    LinearLayout layoutPermission;
    @BindView(R.id.btn_agree)
    Button btnAgree;

    @BindView(R.id.layout_first1)
    RelativeLayout layoutFirst1;
    @BindView(R.id.layout_first2)
    RelativeLayout layoutFirst2;
    @BindView(R.id.layout_first3)
    RelativeLayout layoutFirst3;

    private int mStep;
    public static FirstFragment newInstance(int step) {
        Bundle args = new Bundle();
        args.putInt("step",step);
        FirstFragment fragment = new FirstFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_first;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new FirstPresent(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mStep = getArguments().getInt("step",1);
        initViews();
    }

    private void initViews(){
        switch (mStep){
            case 1:{
                layoutFirst1.setVisibility(View.VISIBLE);
                layoutPermission.setAlpha(0);
                cbAgree.setAlpha(0);
                btnAgree.setAlpha(0);
                layoutPermission.setClickable(false);
                cbAgree.setClickable(false);
                btnAgree.setClickable(false);
                break;
            }
            case 2:{
                layoutFirst2.setVisibility(View.VISIBLE);
                layoutPermission.setAlpha(0);
                cbAgree.setAlpha(0);
                btnAgree.setAlpha(0);
                layoutPermission.setClickable(false);
                cbAgree.setClickable(false);
                btnAgree.setClickable(false);
                break;
            }
            case 3:{
                layoutFirst3.setVisibility(View.VISIBLE);
                break;
            }
        }
    }


    @OnClick({R.id.btn_agree,R.id.layout_permission})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_agree: {
                if (cbAgree.isChecked())
                    mActivity.fragmentCheckPermission();
                else
                    showMessage("Thông báo", "Cần đồng ý điều khoản cho phép đọc danh bạ!", SweetAlertDialog.WARNING_TYPE);
                break;
            }
            case R.id.layout_permission:{
                cbAgree.setChecked(!cbAgree.isChecked());
                break;
            }
        }
    }
}
