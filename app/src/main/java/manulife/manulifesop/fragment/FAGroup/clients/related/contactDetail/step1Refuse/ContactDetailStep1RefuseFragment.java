package manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step1Refuse;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.related.contactDetail.ContactDetailActivity;
import manulife.manulifesop.api.ObjectResponse.ContactDetail;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step1.ContactDetailStep1Contract;
import manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step1.ContactDetailStep1Present;

/**
 * Created by Chick on 10/27/2017.
 */

public class ContactDetailStep1RefuseFragment extends BaseFragment<ContactDetailActivity, ContactDetailStep1RefusePresent> implements ContactDetailStep1RefuseContract.View {

    @BindView(R.id.btn_add_appointment)
    Button btnAddAppointment;

    @BindView(R.id.txt_age)
    TextView txtAge;
    @BindView(R.id.txt_imcome)
    TextView txtIncome;
    @BindView(R.id.txt_marriage)
    TextView txtMarriage;
    @BindView(R.id.txt_relationship)
    TextView txtRelation;
    @BindView(R.id.txt_source)
    TextView txtSource;
    @BindView(R.id.txt_note)
    TextView txtNote;


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
        initViews();
    }

    @Override
    public void initViews() {
        ContactDetail data = ProjectApplication.getInstance().getContactDetail();
        if(data.statusCode==1) {
            txtAge.setText(ProjectApplication.getInstance()
                    .getAgeString(data.data.age));
            txtIncome.setText(ProjectApplication.getInstance()
                    .getIncomeString(data.data.incomeMonthly));
            txtMarriage.setText(ProjectApplication.getInstance()
                    .getMarriageString(data.data.maritalStatus));
            txtRelation.setText(ProjectApplication.getInstance()
                    .getRelationshipString(data.data.relationship));
            txtSource.setText(ProjectApplication.getInstance()
                    .getSourceString(data.data.source));
            txtNote.setText(data.data.description);
        }
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
