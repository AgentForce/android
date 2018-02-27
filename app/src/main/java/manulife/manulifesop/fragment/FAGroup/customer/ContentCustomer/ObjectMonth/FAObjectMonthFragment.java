package manulife.manulifesop.fragment.FAGroup.customer.ContentCustomer.ObjectMonth;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.main.MainFAActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.fragment.FAGroup.customer.ContentCustomer.ObjectWeek.FAObjectWeekContract;
import manulife.manulifesop.fragment.FAGroup.customer.ContentCustomer.ObjectWeek.FAObjectWeekPresent;

/**
 * Created by Chick on 10/27/2017.
 */

public class FAObjectMonthFragment extends BaseFragment<MainFAActivity, FAObjectMonthPresent> implements FAObjectMonthContract.View {


    public static FAObjectMonthFragment newInstance() {
        Bundle args = new Bundle();
        FAObjectMonthFragment fragment = new FAObjectMonthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_fa_customer_content_month;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new FAObjectMonthPresent(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }



    /*@OnClick(R.id.btn_start)
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.btn_start:
            {
                mActivity.goNextScreen(CreatePlanActivity.class);
                break;
            }
        }
    }*/

}
