package manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.related.contactDetail.ContactDetailActivity;
import manulife.manulifesop.api.ObjectResponse.ContactDetail;
import manulife.manulifesop.base.BaseFragment;

/**
 * Created by Chick on 10/27/2017.
 */

public class ContactDetailStep1Fragment extends BaseFragment<ContactDetailActivity, ContactDetailStep1Present> implements ContactDetailStep1Contract.View {

    @BindView(R.id.layout_call)
    LinearLayout layoutCall;
    @BindView(R.id.layout_right)
    LinearLayout layoutMenuRight;

    @BindView(R.id.menu_right)
    LinearLayout menuRight;
    @BindView(R.id.img_xletter)
    ImageView imgXLetter;

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

    private boolean isCalling = false;

    public static ContactDetailStep1Fragment newInstance() {
        Bundle args = new Bundle();
        ContactDetailStep1Fragment fragment = new ContactDetailStep1Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_contact_detail_step1;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new ContactDetailStep1Present(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
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
        }
    }

    @OnClick({R.id.layout_call, R.id.layout_right})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_call: {

                String phone = "tel:" + ProjectApplication.getInstance().getContactDetail().data.phone;
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse(phone));
                startActivity(callIntent);
                isCalling = true;

                break;
            }
            case R.id.layout_right: {
                mActivity.showHideMenuAfterCall();
                break;
            }
        }
    }

    @Override
    public void showXLetter(boolean isShow) {
        if(isShow){
            imgXLetter.setVisibility(View.VISIBLE);
            menuRight.setVisibility(View.GONE);
        }else{
            imgXLetter.setVisibility(View.GONE);
            menuRight.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isCalling) {
            mActivity.showHideMenuAfterCall();
            isCalling = false;
        }
    }
}
