package manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step3;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.related.contactDetail.ContactDetailActivity;
import manulife.manulifesop.adapter.ActiveHistAdapter;
import manulife.manulifesop.adapter.ContactHistAdapter;
import manulife.manulifesop.adapter.ObjectData.ActiveHistFA;
import manulife.manulifesop.api.ObjectResponse.ContactHistory;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.callbackInterface.CallBackClickContact;
import manulife.manulifesop.fragment.FAGroup.clients.appointment.AppointmentContactTabFragment;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.EndlessScrollListenerRecyclerView;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class ContactDetailStep3Fragment extends BaseFragment<ContactDetailActivity, ContactDetailStep3Present> implements ContactDetailStep3Contract.View {

    @BindView(R.id.rcv_data)
    RecyclerView listData;

    private ContactHistAdapter mAdapter;
    private List<ActiveHistFA> mData;
    private LinearLayoutManager mLayoutManager;

    public static ContactDetailStep3Fragment newInstance() {
        Bundle args = new Bundle();
        ContactDetailStep3Fragment fragment = new ContactDetailStep3Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    private final class onLoadingMoreDataTask implements EndlessScrollListenerRecyclerView.onActionListViewScroll {

        @Override
        public void onApiLoadMoreTask(int page) {
            Toast.makeText(mActivity, "Load more", Toast.LENGTH_SHORT).show();
        }
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
        initViews();
        loadData(ProjectApplication.getInstance().getContactHistory());
    }

    public void initViews(){
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mData = new ArrayList<>();
    }

    @Override
    public void loadData(ContactHistory data) {
        listData.setLayoutManager(mLayoutManager);

        /*for (int i = 0; i < data.data.rows.size(); i++) {
            ActiveHistFA temp = new ActiveHistFA();
            temp.setId(data.data.rows.get(i).id);
            temp.setAvatar("avatar " + i);
            temp.setTitle(data.data.rows.get(i).name);
            temp.setContent(data.data.rows.get(i).phone);
            mData.add(temp);
        }*/
        for (int i = 0; i < 10; i++) {
            ActiveHistFA temp = new ActiveHistFA();
            temp.setTitle("Title " + i);
            temp.setContent("Content " + i);
            mData.add(temp);
        }

        mAdapter = new ContactHistAdapter(getContext(), mData);
        listData.setAdapter(mAdapter);

        /*listData.clearOnScrollListeners();
        listData.addOnScrollListener(new EndlessScrollListenerRecyclerView(
                Integer.valueOf(data.data.page)
                , Utils.genLastPage(data.data.count,
                Integer.valueOf(data.data.limit))
                , new onLoadingMoreDataTask(), mLayoutManager));*/

        listData.clearOnScrollListeners();
        listData.addOnScrollListener(new EndlessScrollListenerRecyclerView(
                Integer.valueOf(data.data.page)
                , 3
                , new onLoadingMoreDataTask(), mLayoutManager));

    }
}
