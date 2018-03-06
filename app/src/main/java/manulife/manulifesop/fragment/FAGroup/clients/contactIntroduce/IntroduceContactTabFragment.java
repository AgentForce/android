package manulife.manulifesop.fragment.FAGroup.clients.contactIntroduce;

import android.app.AlertDialog;
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
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.related.addContact.AddContactPersonActivity;
import manulife.manulifesop.activity.FAGroup.clients.related.contactDetail.ContactDetailActivity;
import manulife.manulifesop.activity.FAGroup.clients.related.updateContactInfo.UpdateContactInfoActivity;
import manulife.manulifesop.activity.FAGroup.clients.introduceContact.IntroduceContactActivity;
import manulife.manulifesop.adapter.ActiveHistAdapter;
import manulife.manulifesop.adapter.ObjectData.ActiveHistFA;
import manulife.manulifesop.adapter.ObjectData.ContactPerson;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.callbackInterface.CallBackClickContact;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.EndlessScrollListenerRecyclerView;

/**
 * Created by Chick on 10/27/2017.
 */

public class IntroduceContactTabFragment extends BaseFragment<IntroduceContactActivity, IntroduceContactTabPresent> implements IntroduceContactTabContract.View,
View.OnClickListener{

    @BindView(R.id.rcv_contact)
    RecyclerView listContact;
    @BindView(R.id.txt_add_from_telephone)
    TextView txtAddFromTelephone;
    @BindView(R.id.txt_add_new)
    TextView txtAddNew;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    private String mType;

    private ActiveHistAdapter mAdapterActiveHist;
    private List<ActiveHistFA> mData;
    private LinearLayoutManager mLayoutManager;

    private AlertDialog alertDialog;
    private EditText edtPhoneAlert;
    private EditText edtNameAlert;

    public static IntroduceContactTabFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString("type",type);
        IntroduceContactTabFragment fragment = new IntroduceContactTabFragment();
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
        return R.layout.fragment_introduce_contact_tab1;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new IntroduceContactTabPresent(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mType = getArguments().getString("type","");
        initViews();
        loadDataContact();
    }

    private void initViews() {
        //type introdure, calllater
        if(!mType.equals("") && mType.equals(Contants.INTRODURE)){
            txtTitle.setText("Khách hàng giới thiệu");
        }else{
            txtTitle.setText("Gọi lại sau");
        }
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mData = new ArrayList<>();
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
        if (mAdapterActiveHist == null) {
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
                0, 3, new onLoadingMoreDataTask(), mLayoutManager));
    }

    @Override
    public void gotoConactDetail() {
        Bundle data = new Bundle();
        data.putString("type",mType);
        mActivity.goNextScreen(ContactDetailActivity.class,data);
    }

    private void showpDialogAddNew() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_new_contact, null);

        initDialogEvent(dialogView);

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

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
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
                    mActivity.goNextScreen(UpdateContactInfoActivity.class,data);
                }
            }
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

    @OnClick({R.id.txt_add_from_telephone, R.id.txt_add_new})
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
    }

}
