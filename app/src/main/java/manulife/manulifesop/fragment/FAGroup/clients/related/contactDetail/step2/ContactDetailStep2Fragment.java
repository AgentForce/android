package manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.related.contactDetail.ContactDetailActivity;
import manulife.manulifesop.activity.FAGroup.clients.related.createEvent.CreateEventActivity;
import manulife.manulifesop.activity.FAGroup.clients.related.eventDetail.EventDetailActivity;
import manulife.manulifesop.adapter.EventAdapter;
import manulife.manulifesop.adapter.ObjectData.EventData;
import manulife.manulifesop.api.ObjectResponse.ContactActivity;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.callbackInterface.CallBackClickContact;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.EndlessScrollListenerRecyclerView;
import manulife.manulifesop.util.Utils;


public class ContactDetailStep2Fragment extends BaseFragment<ContactDetailActivity, ContactDetailStep2Present> implements ContactDetailStep2Contract.View, View.OnClickListener {

    @BindView(R.id.btn_create_event)
    Button btnCreateEvent;
    @BindView(R.id.rcv_data)
    RecyclerView rcvEvent;

    private EventAdapter mAdapter;
    private List<EventData> mData;
    private LinearLayoutManager mLayoutManager;

    private AlertDialog alertDialog;
    private int mContactID;

    public static ContactDetailStep2Fragment newInstance(int contactID) {
        Bundle args = new Bundle();
        args.putInt("contactID",contactID);
        ContactDetailStep2Fragment fragment = new ContactDetailStep2Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    private final class onLoadingMoreDataTask implements EndlessScrollListenerRecyclerView.onActionListViewScroll {

        @Override
        public void onApiLoadMoreTask(int page) {
            Toast.makeText(mActivity, "load more", Toast.LENGTH_SHORT).show();
            //loadData();
        }
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_contact_detail_step2;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new ContactDetailStep2Present(this,getContext());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContactID = getArguments().getInt("contactID",0);
        initViews();
        loadContactActivities(ProjectApplication.getInstance().getContactActivity());
    }

    private void initViews() {
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mData = new ArrayList<>();
    }

    @Override
    public void loadContactActivities(ContactActivity data) {
        rcvEvent.setLayoutManager(mLayoutManager);

        EventData temp;
        for (int i = 0; i < data.data.size(); i++) {
            temp = new EventData();
            temp.setAvatar("avatar " + i);
            temp.setEventID(data.data.get(i).id);
            temp.setProcessStep(data.data.get(i).processStep);
            temp.setName(data.data.get(i).manulifeLead.name);
            temp.setTypeEvent(ProjectApplication.getInstance().getEventStringFromType(
                    data.data.get(i).type
            ));
            temp.setDateTime(Utils.convertStringDateToStringDate(data.data.get(i).startDate,
                    "yyyy-MM-dd'T'HH:mm:ss.sss'Z'","dd/MM/yyyy HH:mm:ss"));
            mData.add(temp);
        }
        if (mAdapter == null) {
            mAdapter = new EventAdapter(getContext(), mData, new CallBackClickContact() {
                @Override
                public void onClickMenuRight(int position, int option) {
                    Toast.makeText(mActivity, "vi tri " + position + " options " + option, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onClickMainContent(int position) {
                    //Toast.makeText(mActivity, "Main click", Toast.LENGTH_SHORT).show();
                    Bundle data = new Bundle();
                    data.putSerializable("data",mData.get(position));
                    goNextScreenFragment(EventDetailActivity.class,data, Contants.EVENT_DETAIL);
                }
            });
            rcvEvent.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

        //set space between two items
        int[] ATTRS = new int[]{android.R.attr.listDivider};
        TypedArray a = getContext().obtainStyledAttributes(ATTRS);
        Drawable divider = a.getDrawable(0);
        int insetLeft = getResources().getDimensionPixelSize(R.dimen.margin_left_DividerItemDecoration);
        int insetRight = getResources().getDimensionPixelSize(R.dimen.margin_right_DividerItemDecoration);
        InsetDrawable insetDivider = new InsetDrawable(divider, insetLeft, 0, insetRight, 0);
        a.recycle();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rcvEvent.getContext(),
                mLayoutManager.getOrientation());
        dividerItemDecoration.setDrawable(insetDivider);
        rcvEvent.addItemDecoration(dividerItemDecoration);


        /*rcvEvent.clearOnScrollListeners();
        rcvEvent.addOnScrollListener(new EndlessScrollListenerRecyclerView(
                0, 3, new onLoadingMoreDataTask(), mLayoutManager));*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK &&
                (requestCode == Contants.EVENT_DETAIL || requestCode == Contants.ADD_EVENT)){
            mData.clear();
            mActionListener.getEvents(mContactID);
        }
    }

    @OnClick({R.id.btn_create_event})
    public void onClickEvent(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_create_event: {
                showMenuCreateEvent();
                break;
            }
        }
    }

    @Override
    public void showMenuCreateEvent() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_create_event_contact, null);

        initDialogEvent(dialogView);

        dialogBuilder.setView(dialogView);

        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.getWindow().setGravity(Gravity.BOTTOM);

        alertDialog.show();
    }

    private void initDialogEvent(View view) {
        view.findViewById(R.id.txt_first_meet).setOnClickListener(this);
        view.findViewById(R.id.txt_advisory).setOnClickListener(this);
        view.findViewById(R.id.txt_sign).setOnClickListener(this);
        view.findViewById(R.id.txt_different).setOnClickListener(this);
        view.findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.txt_first_meet: {
                Bundle data = new Bundle();
                data.putInt("typeInt",1);
                data.putInt("contactID",mContactID);
                alertDialog.dismiss();
                goNextScreenFragment(CreateEventActivity.class, data,Contants.ADD_EVENT);
                break;
            }
            case R.id.txt_advisory: {
                Bundle data = new Bundle();
                data.putInt("typeInt",2);
                data.putInt("contactID",mContactID);
                alertDialog.dismiss();
                goNextScreenFragment(CreateEventActivity.class, data,Contants.ADD_EVENT);
                break;
            }
            case R.id.txt_sign: {
                Bundle data = new Bundle();
                data.putInt("typeInt",3);
                data.putInt("contactID",mContactID);
                alertDialog.dismiss();
                goNextScreenFragment(CreateEventActivity.class, data,Contants.ADD_EVENT);
                break;
            }
            case R.id.txt_different: {
                Bundle data = new Bundle();
                data.putInt("typeInt",4);
                data.putInt("contactID",mContactID);
                alertDialog.dismiss();
                goNextScreenFragment(CreateEventActivity.class, data,Contants.ADD_EVENT);
                break;
            }
            case R.id.btn_cancel: {
                alertDialog.dismiss();
                break;
            }
        }
    }
}
