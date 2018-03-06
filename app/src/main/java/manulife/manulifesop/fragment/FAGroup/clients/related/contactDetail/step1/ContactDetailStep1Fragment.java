package manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step1;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.related.contactDetail.ContactDetailActivity;
import manulife.manulifesop.base.BaseFragment;

/**
 * Created by Chick on 10/27/2017.
 */

public class ContactDetailStep1Fragment extends BaseFragment<ContactDetailActivity, ContactDetailStep1Present> implements ContactDetailStep1Contract.View {

    @BindView(R.id.layout_call)
    LinearLayout layoutCall;
    @BindView(R.id.layout_right)
    LinearLayout layoutMenuRight;


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
    }


    @OnClick({R.id.layout_call, R.id.layout_right})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_call: {
                Toast.makeText(mActivity, "call", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.layout_right: {
                mActivity.showHideMenuAfterCall();
                break;
            }
        }
    }

}
