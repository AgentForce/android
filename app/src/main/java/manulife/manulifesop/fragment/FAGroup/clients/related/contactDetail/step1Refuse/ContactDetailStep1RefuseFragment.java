package manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step1Refuse;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.related.contactDetail.ContactDetailActivity;
import manulife.manulifesop.api.ObjectResponse.ContactDetail;
import manulife.manulifesop.api.ObjectResponse.EventsMonth;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.callbackInterface.CallBackConfirmDialog;
import manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step1.ContactDetailStep1Contract;
import manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step1.ContactDetailStep1Present;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.Utils;

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

    private String mProcessStep;
    private int mContactID;

    //variable to detect sm recruit
    private boolean mIsRecruit = false;


    public static ContactDetailStep1RefuseFragment newInstance(String processStep, int contactID) {
        Bundle args = new Bundle();
        args.putString("processStep", processStep);
        args.putInt("contactID", contactID);
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
        mActionListener = new ContactDetailStep1RefusePresent(this, getContext());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProcessStep = getArguments().getString("processStep", "");
        mContactID = getArguments().getInt("contactID", 0);
        initViews();
    }

    @Override
    public void finishUpdateStatus() {
        mActivity.setResult(Activity.RESULT_OK);
        mActivity.finish();
    }

    @Override
    public void initViews() {
        ContactDetail data = ProjectApplication.getInstance().getContactDetail();
        if (data.statusCode == 1) {
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

            //set title for button
            setTitleForButtonChange();
        }
    }

    private void setTitleForButtonChange() {
        switch (mProcessStep) {
            case Contants.CONTACT_MENU: {
                btnAddAppointment.setText("Đưa vào danh sách cần liên hệ");
                break;
            }
            case Contants.APPOINTMENT_MENU: {
                btnAddAppointment.setText("Đưa vào danh sách hẹn gặp");
                break;
            }
            case Contants.CONSULTANT_MENU: {
                btnAddAppointment.setText("Đưa vào danh sách tư vấn");
                break;
            }
            case Contants.SURVEY_MENU: {
                btnAddAppointment.setText("Đưa vào danh sách khảo sát");
                mIsRecruit = true;
                break;
            }
        }
    }

    @OnClick({R.id.btn_add_appointment})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_add_appointment: {
                //Toast.makeText(mActivity, "add to appointment", Toast.LENGTH_SHORT).show();
                showConfirm("Xác nhận", btnAddAppointment.getText().toString(), "Đồng ý",
                        "Hủy", SweetAlertDialog.WARNING_TYPE, new CallBackConfirmDialog() {
                            @Override
                            public void DiaglogPositive() {
                                if (mIsRecruit)
                                    mActionListener.changeStatusRecruitToOne(mContactID, false, 1);
                                else
                                    mActionListener.changeStatusToOne(mContactID, false, 1);
                            }

                            @Override
                            public void DiaglogNegative() {

                            }
                        });
                break;
            }
        }
    }

}
