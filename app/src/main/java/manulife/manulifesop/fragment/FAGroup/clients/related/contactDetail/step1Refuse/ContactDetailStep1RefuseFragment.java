package manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step1Refuse;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.related.contactDetail.ContactDetailActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step1.ContactDetailStep1Contract;
import manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step1.ContactDetailStep1Present;

/**
 * Created by Chick on 10/27/2017.
 */

public class ContactDetailStep1RefuseFragment extends BaseFragment<ContactDetailActivity, ContactDetailStep1RefusePresent> implements ContactDetailStep1RefuseContract.View {

    @BindView(R.id.btn_add_appointment)
    Button btnAddAppointment;



    public static ContactDetailStep1RefuseFragment newInstance() {
        Bundle args = new Bundle();
        ContactDetailStep1RefuseFragment fragment = new ContactDetailStep1RefuseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_contact_detail_step1_refuse;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new ContactDetailStep1RefusePresent(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @OnClick({R.id.btn_add_appointment})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_add_appointment: {
                Toast.makeText(mActivity, "add to appointment", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

}
