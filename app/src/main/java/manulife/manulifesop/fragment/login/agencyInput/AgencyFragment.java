package manulife.manulifesop.fragment.login.agencyInput;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.login.LoginActivity;
import manulife.manulifesop.adapter.PasswordAdapter;
import manulife.manulifesop.base.BaseFragment;


/**
 * Created by Chick on 10/27/2017.
 */

public class AgencyFragment extends BaseFragment<LoginActivity, AgencyPresent> implements AgencyContract.View {


    @BindView(R.id.btn_next)
    Button btnNext;

    @BindView(R.id.edt_agency_name)
    EditText edtAgencyName;

    public static AgencyFragment newInstance() {
        Bundle args = new Bundle();
        AgencyFragment fragment = new AgencyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_login_agency;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new AgencyPresent(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @OnClick(R.id.btn_next)
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_next: {
                if (edtAgencyName.getText().toString().length() > 0) {
                    mActivity.showFragmentPhoneInput(edtAgencyName.getText().toString());
                } else {
                    mActivity.showMessage("Thông báo", "Chưa nhập mã đại lý!", SweetAlertDialog.WARNING_TYPE);
                }
                break;
            }
        }
    }
}
