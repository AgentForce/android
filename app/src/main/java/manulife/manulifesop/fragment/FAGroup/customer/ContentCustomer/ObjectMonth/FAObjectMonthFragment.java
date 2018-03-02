package manulife.manulifesop.fragment.FAGroup.customer.ContentCustomer.ObjectMonth;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.contact.ContactPersonActivity;
import manulife.manulifesop.activity.FAGroup.main.MainFAActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.fragment.FAGroup.customer.ContentCustomer.ObjectWeek.FAObjectWeekContract;
import manulife.manulifesop.fragment.FAGroup.customer.ContentCustomer.ObjectWeek.FAObjectWeekPresent;

/**
 * Created by Chick on 10/27/2017.
 */

public class FAObjectMonthFragment extends BaseFragment<MainFAActivity, FAObjectMonthPresent> implements FAObjectMonthContract.View {

    @BindView(R.id.layout_contact)
    RelativeLayout layoutContact;
    @BindView(R.id.layout_meeting)
    RelativeLayout layoutMeeting;
    @BindView(R.id.layout_advisory)
    RelativeLayout layoutAdvisory;
    @BindView(R.id.layout_sign)
    RelativeLayout layoutSign;
    @BindView(R.id.layout_introduce)
    RelativeLayout layoutIntroduce;


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



    @OnClick({R.id.layout_contact,R.id.layout_meeting,R.id.layout_advisory,R.id.layout_sign,R.id.layout_introduce})
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.layout_contact:
            {
                //Toast.makeText(mActivity, "layout_contact", Toast.LENGTH_SHORT).show();
                mActivity.goNextScreen(ContactPersonActivity.class);
                break;
            }
            case R.id.layout_meeting:
            {
                Toast.makeText(mActivity, "layout_meeting", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.layout_advisory:
            {
                Toast.makeText(mActivity, "layout_advisory", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.layout_introduce:
            {
                Toast.makeText(mActivity, "layout_introduce", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.layout_sign:
            {
                Toast.makeText(mActivity, "layout_sign", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

}
