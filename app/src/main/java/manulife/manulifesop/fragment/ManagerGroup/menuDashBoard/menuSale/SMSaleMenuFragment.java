package manulife.manulifesop.fragment.ManagerGroup.menuDashBoard.menuSale;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.ManagerGroup.SMCreatePlan.SMCreatePlanActivity;
import manulife.manulifesop.activity.main.MainFAActivity;
import manulife.manulifesop.adapter.ObjectData.SpinnerObject;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.fragment.ManagerGroup.SMCreateCampaign.step1.SMCreatePlanStep1Contract;
import manulife.manulifesop.fragment.ManagerGroup.SMCreateCampaign.step1.SMCreatePlanStep1Present;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class SMSaleMenuFragment extends BaseFragment<MainFAActivity, SMSaleMenuPresent> implements SMSaleMenuContract.View {


    public static SMSaleMenuFragment newInstance() {
        Bundle args = new Bundle();
        SMSaleMenuFragment fragment = new SMSaleMenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_sm_menu_sale;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new SMSaleMenuPresent(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.showHideActionbar(true);
        mActivity.updateActionbarTitle("Bán hàng");
    }



    @OnClick({R.id.layout_personal_sale, R.id.layout_manage_sale})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_personal_sale: {
                mActivity.showCustomer("Bán hàng cá nhân");
                break;
            }
            case R.id.layout_manage_sale: {
                Toast.makeText(mActivity, "Quan ly ban hang", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

}
