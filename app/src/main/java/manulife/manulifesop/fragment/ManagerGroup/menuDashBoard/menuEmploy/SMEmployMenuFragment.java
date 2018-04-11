package manulife.manulifesop.fragment.ManagerGroup.menuDashBoard.menuEmploy;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.main.MainFAActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.fragment.ManagerGroup.menuDashBoard.menuSale.SMSaleMenuContract;
import manulife.manulifesop.fragment.ManagerGroup.menuDashBoard.menuSale.SMSaleMenuPresent;

/**
 * Created by Chick on 10/27/2017.
 */

public class SMEmployMenuFragment extends BaseFragment<MainFAActivity, SMEmployMenuPresent> implements SMEmployMenuContract.View {


    public static SMEmployMenuFragment newInstance() {
        Bundle args = new Bundle();
        SMEmployMenuFragment fragment = new SMEmployMenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_sm_menu_employ;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new SMEmployMenuPresent(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity.updateActionbarTitle("Tuyển dụng");
    }



    @OnClick({R.id.layout_personal_employ, R.id.layout_manage_employ})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_personal_employ: {
                Toast.makeText(mActivity, "tyen dung ca nhan", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.layout_manage_employ: {
                mActivity.showManageEmploy();
                break;
            }
        }
    }

}
