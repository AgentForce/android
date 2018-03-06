package manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step3;

import android.os.Bundle;
import android.view.View;

import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.related.contactDetail.ContactDetailActivity;
import manulife.manulifesop.base.BaseFragment;

/**
 * Created by Chick on 10/27/2017.
 */

public class ContactDetailStep3Fragment extends BaseFragment<ContactDetailActivity, ContactDetailStep3Present> implements ContactDetailStep3Contract.View {


    public static ContactDetailStep3Fragment newInstance() {
        Bundle args = new Bundle();
        ContactDetailStep3Fragment fragment = new ContactDetailStep3Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_contact_detail_step3;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new ContactDetailStep3Present(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    /*@OnClick({R.id.txt_add_from_telephone, R.id.txt_add_new, R.id.txt_add_from_introduce})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.txt_add_from_telephone: {
                mActivity.goNextScreen(AddContactPersonActivity.class);
                break;
            }
            case R.id.txt_add_new: {
                Toast.makeText(mActivity, "txt_add_new", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.txt_add_from_introduce: {
                Toast.makeText(mActivity, "txt_add_from_introduce", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }*/

}
