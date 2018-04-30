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

    private boolean mIsRecruitment;

    public static ContactDetailStep3Fragment newInstance(boolean isRecruitment) {
        Bundle args = new Bundle();
        args.putBoolean("isRecruit", isRecruitment);
        ContactDetailStep3Fragment fragment = new ContactDetailStep3Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    private final class onLoadingMoreDataTask implements EndlessScrollListenerRecyclerView.onActionListViewScroll {

        @Override
        public void onApiLoadMoreTask(int page) {
            //Toast.makeText(mActivity, "Load more", Toast.LENGTH_SHORT).show();
            mActionListener.getContactHistory(mData.get(0).getId(), page);
        }
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_contact_detail_step3;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new ContactDetailStep3Present(this, getContext());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIsRecruitment = getArguments().getBoolean("isRecruit", false);
        initViews();
        loadData(ProjectApplication.getInstance().getContactHistory());
    }

    public void initViews() {
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mData = new ArrayList<>();
    }

    @Override
    public void loadData(ContactHistory data) {
        listData.setLayoutManager(mLayoutManager);

        ActiveHistFA temp;
        for (int i = 0; i < data.data.count; i++) {
            temp = new ActiveHistFA();
            temp.setId(data.data.rows.get(i).leadID);
            if (mIsRecruitment)
                temp.setTitle(ProjectApplication.getInstance().getStringProcessStatusSM(
                        data.data.rows.get(i).processStep + "" + data.data.rows.get(i).statusProcessStep
                ));
            else
                temp.setTitle(ProjectApplication.getInstance().getStringProcessStatus(
                        data.data.rows.get(i).processStep + "" + data.data.rows.get(i).statusProcessStep
                ));
            temp.setContent(Utils.convertStringTimeZoneDateToStringDate(
                    data.data.rows.get(i).createdAt, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                    "dd/MM/yyyy HH:mm:ss"
            ));
            mData.add(temp);
        }
        mAdapter = new ContactHistAdapter(getContext(), mData);
        listData.setAdapter(mAdapter);

        listData.clearOnScrollListeners();
        /*listData.addOnScrollListener(new EndlessScrollListenerRecyclerView(
                Integer.valueOf(data.data.page)
                , Utils.genLastPage(data.data.count,
                Integer.valueOf(data.data.limit))
                , new onLoadingMoreDataTask(), mLayoutManager));*/
        listData.addOnScrollListener(new EndlessScrollListenerRecyclerView(
                Integer.valueOf(data.data.page)
                , 3
                , new onLoadingMoreDataTask(), mLayoutManager));

    }
}
