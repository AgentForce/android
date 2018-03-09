package manulife.manulifesop.fragment.FAGroup.clients.appointment;

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
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.appointment.AppointmentActivity;
import manulife.manulifesop.activity.FAGroup.clients.related.contactDetail.ContactDetailActivity;
import manulife.manulifesop.adapter.ActiveHistAdapter;
import manulife.manulifesop.adapter.ObjectData.ActiveHistFA;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.callbackInterface.CallBackClickContact;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.EndlessScrollListenerRecyclerView;

/**
 * Created by Chick on 10/27/2017.
 */

public class AppointmentContactTabFragment extends BaseFragment<AppointmentActivity, AppointmentContactTabPresent> implements AppointmentContactTabContract.View {

    @BindView(R.id.rcv_contact)
    RecyclerView listContact;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    private String mType;

    private ActiveHistAdapter mAdapterActiveHist;
    private List<ActiveHistFA> mData;
    private LinearLayoutManager mLayoutManager;


    public static AppointmentContactTabFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString("type", type);
        AppointmentContactTabFragment fragment = new AppointmentContactTabFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private final class onLoadingMoreDataTask implements EndlessScrollListenerRecyclerView.onActionListViewScroll {

        @Override
        public void onApiLoadMoreTask(int page) {
            Toast.makeText(mActivity, "load more", Toast.LENGTH_SHORT).show();
            loadDataContact();
        }
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_appointment_contact_tab1;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new AppointmentContactTabPresent(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mType = getArguments().getString("type", "");
        initViews();
        //loadDataContact();
    }

    private void initViews() {
        //type = appointment, seen, calllater
        if (mType != null && !mType.equals("")) {
            switch (mType) {
                case Contants.APPOINTMENT: {
                    txtTitle.setText("Hẹn gặp");
                    break;
                }
                case Contants.SEEN: {
                    txtTitle.setText("Đã hẹn gặp");
                    break;
                }
                case Contants.CALLLATER: {
                    txtTitle.setText("Liên hệ sau");
                    break;
                }
                case Contants.REFUSE:{
                    txtTitle.setText("Từ chối");
                    break;
                }
            }
        }
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mData = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDataContact();
    }

    private void loadDataContact() {
        listContact.setLayoutManager(mLayoutManager);

        for (int i = 0; i < 10; i++) {
            ActiveHistFA temp = new ActiveHistFA();
            temp.setAvatar("avatar " + i);
            temp.setTitle("title code input " + i);
            temp.setContent("content code input " + i);
            mData.add(temp);
        }


        mAdapterActiveHist = new ActiveHistAdapter(getContext(), mData, new CallBackClickContact() {
            @Override
            public void onClickMenuRight(int position, int option) {
                Toast.makeText(mActivity, "vi tri " + position + " options " + option, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClickMainContent(int position) {
                gotoConactDetail();
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
                0, 3, new onLoadingMoreDataTask(), mLayoutManager));
    }

    @Override
    public void gotoConactDetail() {
        Bundle data = new Bundle();
        data.putString("type",mType);
        data.putString("type_menu",Contants.APPOINTMENT_MENU);
        mActivity.goNextScreen(ContactDetailActivity.class,data);
    }


    /*@OnClick({R.id.txt_add_from_telephone, R.id.txt_add_new})
    public void onClickEvent(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.txt_add_from_telephone: {
                mActivity.goNextScreen(AddContactPersonActivity.class);
                break;
            }
            case R.id.txt_add_new: {
                showpDialogAddNew();
                break;
            }
            case R.id.txt_add_from_introduce: {
                mActivity.goNextScreen(IntroduceContactActivity.class);
                break;
            }
        }
    }*/

}
