package manulife.manulifesop.fragment.FAGroup.clients.contactIntroduce;

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
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.introduceContact.IntroduceContactActivity;
import manulife.manulifesop.activity.FAGroup.clients.related.updateContactInfo.UpdateContactInfoActivity;
import manulife.manulifesop.adapter.ActiveHistAdapter;
import manulife.manulifesop.adapter.ObjectData.ActiveHistFA;
import manulife.manulifesop.adapter.ObjectData.ContactPerson;
import manulife.manulifesop.api.ObjectResponse.CampaignMonth;
import manulife.manulifesop.api.ObjectResponse.UsersList;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.callbackInterface.CallBackClickContact;
import manulife.manulifesop.element.callbackInterface.CallBackConfirmDialog;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.EndlessScrollListenerRecyclerView;
import manulife.manulifesop.util.Utils;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Chick on 10/27/2017.
 */

public class IntroduceContactTabFragment extends BaseFragment<IntroduceContactActivity, IntroduceContactTabPresent> implements IntroduceContactTabContract.View,
        View.OnClickListener {

    @BindView(R.id.rcv_contact)
    RecyclerView listContact;
    @BindView(R.id.txt_add_new)
    TextView txtAddNew;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    private String mType;
    private int mMonth;

    private ActiveHistAdapter mAdapterActiveHist;
    private List<ActiveHistFA> mData;
    private LinearLayoutManager mLayoutManager;

    private AlertDialog alertDialog;
    private EditText edtPhoneAlert;
    private EditText edtNameAlert;

    private CampaignMonth mCampaignMonth;

    private boolean mIsChangeUserToContact = false;
    private boolean mIsFromContact = false;

    private int mPositionChange;

    public static IntroduceContactTabFragment newInstance(String type, int month, boolean isfromContact) {
        Bundle args = new Bundle();
        args.putString("type", type);
        args.putInt("month", month);
        args.putBoolean("isFromContact", isfromContact);
        IntroduceContactTabFragment fragment = new IntroduceContactTabFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private final class onLoadingMoreDataTask implements EndlessScrollListenerRecyclerView.onActionListViewScroll {

        @Override
        public void onApiLoadMoreTask(int page) {
            mActionListener.getUserListProcess(mMonth, page);
        }
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_introduce_contact_tab1;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new IntroduceContactTabPresent(this, getContext());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mType = getArguments().getString("type", "");
        mMonth = getArguments().getInt("month", 0);
        mIsFromContact = getArguments().getBoolean("isFromContact", false);
        initViews();
        loadContactList(ProjectApplication.getInstance().getIntroduce());
    }

    private void initViews() {
        //type introdure, calllater
        if (!mType.equals("") && mType.equals(Contants.INTRODURE)) {
            txtTitle.setText("Khách hàng giới thiệu");
        }
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mData = new ArrayList<>();
        mCampaignMonth = ProjectApplication.getInstance().getCampaign();
    }

    @Override
    public void reloadData() {
        mActivity.reloadData();
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
                    mPositionChange = position;
                    showDialogChooseWeek(true);
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

    private void showpDialogAddNew() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_new_contact, null);

        initDialogEvent(dialogView);
        dialogBuilder.setView(dialogView);
        hideKeyboardOutside(dialogView, mActivity);

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
        switch (id) {
            case R.id.txt_cancel: {
                alertDialog.dismiss();
                break;
            }
            case R.id.txt_add: {
                if (validateInputAddContact()) {
                    alertDialog.dismiss();
                    mActionListener.addIntroduceContact(edtPhoneAlert.getText().toString(),
                            edtNameAlert.getText().toString(),
                            ProjectApplication.getInstance().getCampaignWeekId());
                }
                break;
            }
            //choose week add contact
            case R.id.btn_cancel: {
                alertDialog.dismiss();
                break;
            }
            case R.id.txt_week1: {
                ProjectApplication.getInstance().setCampaignWeekId(
                        mCampaignMonth.data.campaigns.get(0).id
                );
                alertDialog.dismiss();
                if (mIsChangeUserToContact)
                    showConfirmChange();
                else
                    showpDialogAddNew();

                break;
            }
            case R.id.txt_week2: {
                ProjectApplication.getInstance().setCampaignWeekId(
                        mCampaignMonth.data.campaigns.get(1).id
                );
                alertDialog.dismiss();
                if (mIsChangeUserToContact)
                    showConfirmChange();
                else
                    showpDialogAddNew();

                break;
            }
            case R.id.txt_week3: {
                ProjectApplication.getInstance().setCampaignWeekId(
                        mCampaignMonth.data.campaigns.get(2).id
                );
                alertDialog.dismiss();
                if (mIsChangeUserToContact)
                    showConfirmChange();
                else
                    showpDialogAddNew();
                break;
            }
            case R.id.txt_week4: {
                ProjectApplication.getInstance().setCampaignWeekId(
                        mCampaignMonth.data.campaigns.get(3).id
                );
                alertDialog.dismiss();
                if (mIsChangeUserToContact)
                    showConfirmChange();
                else
                    showpDialogAddNew();
                break;
            }
        }
    }

    public void showConfirmChange() {
        showConfirm("Xác nhận", "Bạn có đồng ý chuyển sang KH liên hệ?", "Đồng ý",
                "Hủy", SweetAlertDialog.WARNING_TYPE, new CallBackConfirmDialog() {
                    @Override
                    public void DiaglogPositive() {
                        List<ContactPerson> dataInput = new ArrayList<>();
                        dataInput.add(new ContactPerson(false, "",
                                mData.get(mPositionChange).getTitle(), mData.get(mPositionChange).getContent(), 0));
                        Bundle data = new Bundle();
                        data.putSerializable("data", (Serializable) dataInput);
                        data.putBoolean("isChangeToContact", true);
                        data.putInt("idRelead", mData.get(mPositionChange).getId());
                        goNextScreenFragment(UpdateContactInfoActivity.class, data, Contants.CHANGE_TO_CONTACT);
                    }

                    @Override
                    public void DiaglogNegative() {

                    }
                });
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Contants.CHANGE_TO_CONTACT && resultCode == RESULT_OK) {
            if (!mIsFromContact) {
                mData.clear();
                mActionListener.getUserListProcess(mMonth, 1);
            } else {
                mActivity.setResult(RESULT_OK);
                mActivity.finish();
            }
        }
    }*/

    private boolean validateInputAddContact() {
        if (edtPhoneAlert.getText().length() <= 0) {
            edtPhoneAlert.setError("Nhập số điện thoại");
            return false;
        }
        if (edtNameAlert.getText().length() <= 0) {
            edtNameAlert.setError("Nhập họ tên");
            return false;
        }
        return true;
    }

    @OnClick({R.id.txt_add_new})
    public void onClickEvent(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.txt_add_new: {
                showDialogChooseWeek(false);
                break;
            }
        }
    }

    @Override
    public void showDialogChooseWeek(boolean isChangeContact) {

        this.mIsChangeUserToContact = isChangeContact;

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

    private void initDialogEventAddContact(View view) {

        if (this.mIsChangeUserToContact) {
            ((TextView) view.findViewById(R.id.txt_title)).setText("Chuyển " + mData.get(mPositionChange).getTitle() + " sang DS liên hệ");
        } else {
            ((TextView) view.findViewById(R.id.txt_title)).setText("Thêm người giới thiệu");
        }

        view.findViewById(R.id.btn_cancel).setOnClickListener(this);
        view.findViewById(R.id.txt_week1).setOnClickListener(this);
        view.findViewById(R.id.txt_week2).setOnClickListener(this);
        view.findViewById(R.id.txt_week3).setOnClickListener(this);
        view.findViewById(R.id.txt_week4).setOnClickListener(this);

        if (mCampaignMonth.data.currentWeek == 4) {
            view.findViewById(R.id.txt_week1).setAlpha(0.5f);
            view.findViewById(R.id.txt_week1).setClickable(false);
            view.findViewById(R.id.txt_week2).setAlpha(0.5f);
            view.findViewById(R.id.txt_week2).setClickable(false);
            view.findViewById(R.id.txt_week3).setAlpha(0.5f);
            view.findViewById(R.id.txt_week3).setClickable(false);
        } else if (mCampaignMonth.data.currentWeek == 3) {
            view.findViewById(R.id.txt_week1).setAlpha(0.5f);
            view.findViewById(R.id.txt_week1).setClickable(false);
            view.findViewById(R.id.txt_week2).setAlpha(0.5f);
            view.findViewById(R.id.txt_week2).setClickable(false);
        } else if (mCampaignMonth.data.currentWeek == 2) {
            view.findViewById(R.id.txt_week1).setAlpha(0.5f);
            view.findViewById(R.id.txt_week1).setClickable(false);
        }
    }


}
