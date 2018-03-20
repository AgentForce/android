package manulife.manulifesop.fragment.FAGroup.clients.consultant;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.appointment.AppointmentActivity;
import manulife.manulifesop.activity.FAGroup.clients.consultant.ConsultantActivity;
import manulife.manulifesop.activity.FAGroup.clients.related.contactDetail.ContactDetailActivity;
import manulife.manulifesop.adapter.ActiveHistAdapter;
import manulife.manulifesop.adapter.ObjectData.ActiveHistFA;
import manulife.manulifesop.api.ObjectResponse.UsersList;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.callbackInterface.CallBackClickContact;
import manulife.manulifesop.fragment.FAGroup.clients.appointment.AppointmentContactTabContract;
import manulife.manulifesop.fragment.FAGroup.clients.appointment.AppointmentContactTabPresent;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.EndlessScrollListenerRecyclerView;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class ConsultantContactTabFragment extends BaseFragment<ConsultantActivity, ConsultantContactTabPresent> implements ConsultantContactTabContract.View {

    @BindView(R.id.rcv_contact)
    RecyclerView listContact;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    private int mType;
    private String mTypeString;

    private int mTarget;
    private int mMonth;

    private ActiveHistAdapter mAdapterActiveHist;
    private List<ActiveHistFA> mData;
    private LinearLayoutManager mLayoutManager;


    public static ConsultantContactTabFragment newInstance(int type, String typeString, int target, int month) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putString("typeString", typeString);
        args.putInt("target", target);
        args.putInt("month", month);
        ConsultantContactTabFragment fragment = new ConsultantContactTabFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private final class onLoadingMoreDataTask implements EndlessScrollListenerRecyclerView.onActionListViewScroll {

        @Override
        public void onApiLoadMoreTask(int page) {
            mActionListener.getContact(mMonth, mType, page);
        }
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_appointment_contact_tab1;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new ConsultantContactTabPresent(this,getContext());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mType = getArguments().getInt("type", 1);
        mTypeString = getArguments().getString("typeString", "");
        mTarget = getArguments().getInt("target", 0);
        mMonth = getArguments().getInt("month", 0);
        initViews();
        loadDataContact(getFirstData());
    }

    private void initViews() {
        //type = appointment, seen, calllater

        switch (mType) {
            case Contants.CONSULTANT_NEED: {
                txtTitle.setText("Tư vấn(" +
                        ProjectApplication.getInstance().getConsultantNeed().data.count
                        + "/" + mTarget + ")");
                break;
            }
            case Contants.CONSULTANT_SEEN: {
                txtTitle.setText("Đã hẹn tư vấn");
                break;
            }
            case Contants.CONSULTANT_CALLLATER: {
                txtTitle.setText("Liên hệ sau");
                break;
            }
            case Contants.CONSULTANT_REFUSE: {
                txtTitle.setText("Từ chối");
                break;
            }
        }

        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mData = new ArrayList<>();
    }

    private UsersList getFirstData() {
        UsersList data = new UsersList();
        switch (mType) {
            case Contants.CONSULTANT_NEED: {
                data = ProjectApplication.getInstance().getConsultantNeed();
                break;
            }
            case Contants.CONSULTANT_SEEN: {
                data = ProjectApplication.getInstance().getConsultantSeen();
                break;
            }
            case Contants.CONSULTANT_CALLLATER: {
                data = ProjectApplication.getInstance().getConsultantCallLater();
                break;
            }
            case Contants.CONSULTANT_REFUSE: {
                data = ProjectApplication.getInstance().getConsultantRefuse();
                break;
            }
        }
        return data;
    }

    @Override
    public void loadDataContact(UsersList data) {
        listContact.setLayoutManager(mLayoutManager);

        for (int i = 0; i < data.data.rows.size(); i++) {
            ActiveHistFA temp = new ActiveHistFA();
            temp.setId(data.data.rows.get(i).id);
            temp.setAvatar("avatar " + i);
            temp.setTitle(data.data.rows.get(i).name);
            temp.setContent(data.data.rows.get(i).phone);
            mData.add(temp);
        }


        mAdapterActiveHist = new ActiveHistAdapter(getContext(), mData, new CallBackClickContact() {
            @Override
            public void onClickMenuRight(int position, int option) {
                Toast.makeText(mActivity, "vi tri " + position + " options " + option, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClickMainContent(int position) {
                gotoConactDetail(mData.get(position).getId());
            }
        });
        listContact.setAdapter(mAdapterActiveHist);

        //set space between two items
        int[] ATTRS = new int[]{android.R.attr.listDivider};
        TypedArray a = getContext().obtainStyledAttributes(ATTRS);
        Drawable divider = a.getDrawable(0);
        int insetLeft = getResources().getDimensionPixelSize(R.dimen.margin_left_DividerItemDecoration);
        int insetRight = getResources().getDimensionPixelSize(R.dimen.margin_right_DividerItemDecoration);
        InsetDrawable insetDivider = new InsetDrawable(divider, insetLeft, 0, insetRight, 0);
        a.recycle();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(listContact.getContext(),
                mLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(insetDivider);
        listContact.addItemDecoration(dividerItemDecoration);


        listContact.clearOnScrollListeners();
        listContact.addOnScrollListener(new EndlessScrollListenerRecyclerView(
                Integer.valueOf(data.data.page)
                , Utils.genLastPage(data.data.count,
                Integer.valueOf(data.data.limit))
                , new onLoadingMoreDataTask(), mLayoutManager));
    }

    @Override
    public void gotoConactDetail(int id) {
        Bundle data = new Bundle();
        data.putString("type", mTypeString);
        data.putString("type_menu", Contants.CONSULTANT_MENU);
        data.putInt("id", id);
        mActivity.goNextScreen(ContactDetailActivity.class, data, Contants.CONTACT_DETAIL);
    }
}
