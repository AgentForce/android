package manulife.manulifesop.fragment.FAGroup.contactPerson.tab1;

import android.os.Bundle;
import android.view.View;

import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.main.MainFAActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.fragment.FAGroup.confirmCreatePlan.ConfirmCreatePlanContract;
import manulife.manulifesop.fragment.FAGroup.confirmCreatePlan.ConfirmCreatePlanPresent;

/**
 * Created by Chick on 10/27/2017.
 */

public class ContactPersonTab1Fragment extends BaseFragment<MainFAActivity,ContactPersonTab1Present> implements ContactPersonTab1Contract.View {


    public static ContactPersonTab1Fragment newInstance() {
        Bundle args = new Bundle();
        ContactPersonTab1Fragment fragment = new ContactPersonTab1Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_contact_person_tab1;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new ContactPersonTab1Present(this);
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
