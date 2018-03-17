package manulife.manulifesop.fragment.FAGroup.clients.contactPerson;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.contact.ContactPersonActivity;
import manulife.manulifesop.activity.FAGroup.clients.related.addContact.AddContactPersonActivity;
import manulife.manulifesop.activity.FAGroup.clients.related.contactDetail.ContactDetailActivity;
import manulife.manulifesop.activity.FAGroup.clients.related.updateContactInfo.UpdateContactInfoActivity;
import manulife.manulifesop.activity.FAGroup.clients.introduceContact.IntroduceContactActivity;
import manulife.manulifesop.adapter.ActiveHistAdapter;
import manulife.manulifesop.adapter.ObjectData.ActiveHistFA;
import manulife.manulifesop.adapter.ObjectData.ContactPerson;
import manulife.manulifesop.api.ObjectResponse.CampaignMonth;
import manulife.manulifesop.api.ObjectResponse.UsersList;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.callbackInterface.CallBackClickContact;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.EndlessScrollListenerRecyclerView;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class ContactPersonTab1Fragment extends BaseFragment<ContactPersonActivity, ContactPersonTab1Present> implements ContactPersonTab1Contract.View,
View.OnClickListener{

    @BindView(R.id.rcv_contact)
    RecyclerView listContact;
    @BindView(R.id.txt_add_from_telephone)
    TextView txtAddFromTelephone;
    @BindView(R.id.txt_add_new)
    TextView txtAddNew;
    @BindView(R.id.txt_add_from_introduce)
    TextView txtAddFromIntroduce;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    private String mType;

    private ActiveHistAdapter mAdapterActiveHist;
    private List<ActiveHistFA> mData;
    private LinearLayoutManager mLayoutManager;

    private AlertDialog alertDialog;
    private EditText edtPhoneAlert;
    private EditText edtNameAlert;

    private int mMonth;
    private int mTargetIntroduce;

    private CampaignMonth mCampaignMonth;
    private boolean misAddFromPhone = false;

    public static ContactPersonTab1Fragment newInstance(String type, UsersList usersList,int month, int targetIntroduce) {
        Bundle args = new Bundle();
        args.putString("type",type);
        args.putSerializable("data",usersList);
        args.putInt("month",month);
        args.putInt("targetIntroduce", targetIntroduce);
        ContactPersonTab1Fragment fragment = new ContactPersonTab1Fragment();
        fragment.setArguments(args);
        return fragment;
    }


    private final class onLoadingMoreDataTask implements EndlessScrollListenerRecyclerView.onActionListViewScroll {

        @Override
        public void onApiLoadMoreTask(int page) {
            mActionListener.getUserListProcess(mMonth,(mType.equals(Contants.CONTACT)) ? 1 : 2,page);
        }
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_contact_person_tab1;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new ContactPersonTab1Present(this,getContext());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mType = getArguments().getString("type","");
        mMonth = getArguments().getInt("month",0);
        mTargetIntroduce = getArguments().getInt("targetIntroduce",0);
        initViews();
        //loadDataContact();
        loadContactList((UsersList) getArguments().getSerializable("data"));
    }

    private void initViews() {
        //type = contact, calllater
        if(!mType.equals("") && mType.equals(Contants.CONTACT)){
            txtTitle.setText("Liên hệ");
        }else{
            txtTitle.setText("Gọi lại sau");
        }
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mData = new ArrayList<>();
    }

    @Override
    public void loadContactList(UsersList data) {
        listContact.setLayoutManager(mLayoutManager);

        for (int i = 0; i < data.data.rows.size(); i++) {
            ActiveHistFA temp = new ActiveHistFA();
            temp.setId(data.data.rows.get(i).id);
            temp.setAvatar("avatar " + i);
            temp.setTitle(data.data.rows.get(i).name);
            temp.setContent(data.data.rows.get(i).phone);
            mData.add(temp);
        }
        if (mAdapterActiveHist == null) {
            mAdapterActiveHist = new ActiveHistAdapter(getContext(), mData, new CallBackClickContact() {
                @Override
                public void onClickMenuRight(int position, int option) {
                    Toast.makeText(mActivity, "vi tri " + position + " options " + option, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onClickMainContent(int position) {
                    gotoContactDetail(mData.get(position).getId());
                }
            });
            listContact.setAdapter(mAdapterActiveHist);
        } else {
            mAdapterActiveHist.notifyDataSetChanged();
        }

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
                Integer.valueOf(data.data.page),
                Utils.genLastPage(data.data.count,
                        Integer.valueOf(data.data.limit)), new onLoadingMoreDataTask(), mLayoutManager));
    }

    @Override
    public void gotoContactDetail(int id) {
        Bundle data = new Bundle();
        data.putString("type",mType);
        data.putString("type_menu",Contants.CONTACT_MENU);
        data.putInt("id",id);
        //data.putString("type_menu",Contants.SIGNED_MENU);
        mActivity.goNextScreen(ContactDetailActivity.class,data);
    }

    private void showpDialogAddNew() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_new_contact, null);

        initDialogEvent(dialogView);
        hideKeyboardOutside(dialogView,mActivity);
        dialogBuilder.setView(dialogView);

        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        alertDialog.show();
    }

    private void initDialogEvent(View view) {
        view.findViewById(R.id.txt_cancel).setOnClickListener(this);
        view.findViewById(R.id.txt_add).setOnClickListener(this);
        edtPhoneAlert = view.findViewById(R.id.edt_phone);
        edtNameAlert = view.findViewById(R.id.edt_name);
    }

    protected void hideKeyboardOutside(View view, final Activity activity) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(
                    (v, event) -> {
                        //Utils.hideSoftKeyboard(activity);
                        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        v.clearFocus();
                        return false;
                    });
        }
        //If a layout container, iterate over children
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                hideKeyboardOutside(innerView, activity);
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            //create event
            case R.id.txt_cancel:{
                alertDialog.dismiss();
                break;
            }
            case R.id.txt_add:{
                if(validateInputAddContact()){
                    List<ContactPerson> dataInput = new ArrayList<>();
                    dataInput.add(new ContactPerson(false,"",
                            edtNameAlert.getText().toString(),edtPhoneAlert.getText().toString(),0));
                    Bundle data = new Bundle();
                    data.putSerializable("data", (Serializable) dataInput);
                    goNextScreenFragment(UpdateContactInfoActivity.class,data,Contants.ADD_CONTACT);
                    alertDialog.dismiss();
                }
                break;
            }

            //choose week add contact
            case R.id.btn_cancel:
            {
                alertDialog.dismiss();
                break;
            }
            case R.id.txt_week1:{
                ProjectApplication.getInstance().setCampaignWeekId(
                        mCampaignMonth.data.campaigns.get(0).id
                );
                if(misAddFromPhone) {
                    alertDialog.dismiss();
                    //mActivity.goNextScreen(AddContactPersonActivity.class);
                    goNextScreenFragment(AddContactPersonActivity.class,Contants.ADD_CONTACT);
                }else
                {
                    alertDialog.dismiss();
                    showpDialogAddNew();
                }
                break;
            }
            case R.id.txt_week2:{
                ProjectApplication.getInstance().setCampaignWeekId(
                        mCampaignMonth.data.campaigns.get(1).id
                );
                if(misAddFromPhone) {
                    alertDialog.dismiss();
                    //mActivity.goNextScreen(AddContactPersonActivity.class);
                    goNextScreenFragment(AddContactPersonActivity.class,Contants.ADD_CONTACT);
                }else
                {
                    alertDialog.dismiss();
                    showpDialogAddNew();
                }
                break;
            }
            case R.id.txt_week3:{
                ProjectApplication.getInstance().setCampaignWeekId(
                        mCampaignMonth.data.campaigns.get(2).id
                );
                if(misAddFromPhone) {
                    alertDialog.dismiss();
                    //mActivity.goNextScreen(AddContactPersonActivity.class);
                    goNextScreenFragment(AddContactPersonActivity.class,Contants.ADD_CONTACT);
                }else
                {
                    alertDialog.dismiss();
                    showpDialogAddNew();
                }
                break;
            }
            case R.id.txt_week4:{
                ProjectApplication.getInstance().setCampaignWeekId(
                        mCampaignMonth.data.campaigns.get(3).id
                );
                if(misAddFromPhone) {
                    alertDialog.dismiss();
                    //mActivity.goNextScreen(AddContactPersonActivity.class);
                    goNextScreenFragment(AddContactPersonActivity.class,Contants.ADD_CONTACT);
                }else
                {
                    alertDialog.dismiss();
                    showpDialogAddNew();
                }
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
        //if(requestCode == Contants.ADD_CONTACT && resultCode == Activity.RESULT_OK){
            mData.clear();
            mActionListener.getUserListProcess(mMonth,(mType.equals(Contants.CONTACT)) ? 1 : 2,1);
        }
    }

    private boolean validateInputAddContact(){
        if(edtPhoneAlert.getText().length() <= 0){
            edtPhoneAlert.setError("Nhập số điện thoại");
            return false;
        }
        if(edtNameAlert.getText().length() <= 0){
            edtNameAlert.setError("Nhập họ tên");
            return false;
        }
        return true;
    }

    @OnClick({R.id.txt_add_from_telephone, R.id.txt_add_new, R.id.txt_add_from_introduce})
    public void onClickEvent(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.txt_add_from_telephone: {
                //Toast.makeText(mActivity, "choose current week", Toast.LENGTH_SHORT).show();
                mCampaignMonth = ProjectApplication.getInstance().getCampaign();
                //mActivity.goNextScreen(AddContactPersonActivity.class);
                misAddFromPhone = true;
                showDialogChooseWeek();
                break;
            }
            case R.id.txt_add_new: {
                mCampaignMonth = ProjectApplication.getInstance().getCampaign();
                misAddFromPhone = false;
                showDialogChooseWeek();
                break;
            }
            case R.id.txt_add_from_introduce: {
                Bundle data = new Bundle();
                data.putInt("target",mTargetIntroduce);
                data.putInt("month",mMonth);
                data.putBoolean("isFromContact",true);
                //mActivity.goNextScreen(IntroduceContactActivity.class,data);
                goNextScreenFragment(IntroduceContactActivity.class,data,Contants.ADD_INTRODUCE_FROM_CONTACT);
                break;
            }
        }
    }

    @Override
    public void showDialogChooseWeek() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_choose_week, null);

        initDialogEventAddContact(dialogView);

        dialogBuilder.setView(dialogView);

        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(true);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //alertDialog.getWindow().setGravity(Gravity.BOTTOM);
        alertDialog.show();
    }
    private void initDialogEventAddContact(View view){
        view.findViewById(R.id.btn_cancel).setOnClickListener(this);
        view.findViewById(R.id.txt_week1).setOnClickListener(this);
        view.findViewById(R.id.txt_week2).setOnClickListener(this);
        view.findViewById(R.id.txt_week3).setOnClickListener(this);
        view.findViewById(R.id.txt_week4).setOnClickListener(this);

        if(mCampaignMonth.data.currentWeek == 4){
            view.findViewById(R.id.txt_week1).setAlpha(0.5f);
            view.findViewById(R.id.txt_week1).setClickable(false);
            view.findViewById(R.id.txt_week2).setAlpha(0.5f);
            view.findViewById(R.id.txt_week2).setClickable(false);
            view.findViewById(R.id.txt_week3).setAlpha(0.5f);
            view.findViewById(R.id.txt_week3).setClickable(false);
        }else if(mCampaignMonth.data.currentWeek == 3){
            view.findViewById(R.id.txt_week1).setAlpha(0.5f);
            view.findViewById(R.id.txt_week1).setClickable(false);
            view.findViewById(R.id.txt_week2).setAlpha(0.5f);
            view.findViewById(R.id.txt_week2).setClickable(false);
        }else if(mCampaignMonth.data.currentWeek == 2){
            view.findViewById(R.id.txt_week1).setAlpha(0.5f);
            view.findViewById(R.id.txt_week1).setClickable(false);
        }
    }
}
