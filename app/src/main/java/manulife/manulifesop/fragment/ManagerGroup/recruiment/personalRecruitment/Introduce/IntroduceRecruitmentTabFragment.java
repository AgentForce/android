package manulife.manulifesop.fragment.ManagerGroup.recruiment.personalRecruitment.Introduce;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

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
import manulife.manulifesop.activity.ManagerGroup.Recruitment.introduceRecruitment.IntroduceRecruitmentActivity;
import manulife.manulifesop.adapter.ActiveHistAdapter;
import manulife.manulifesop.adapter.ObjectData.ActiveHistFA;
import manulife.manulifesop.adapter.ObjectData.ContactPerson;
import manulife.manulifesop.api.ObjectResponse.CampaignMonth;
import manulife.manulifesop.api.ObjectResponse.CampaignRecruitMonth;
import manulife.manulifesop.api.ObjectResponse.UsersList;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.callbackInterface.CallBackClickContact;
import manulife.manulifesop.element.callbackInterface.CallBackConfirmDialog;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.EndlessScrollListenerRecyclerView;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class IntroduceRecruitmentTabFragment extends BaseFragment<IntroduceRecruitmentActivity, IntroduceRecruitmentTabPresent> implements IntroduceRecruitmentTabContract.View,
        View.OnClickListener {

    @BindView(R.id.expandable_layout_top)
    ExpandableLayout expandableLayout;
    @BindView(R.id.img_show_add)
    ImageView imgShowAdd;

    @BindView(R.id.edt_search)
    EditText edtSearch;
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

    private CampaignRecruitMonth mCampaignMonth;

    private boolean mIsChangeUserToRecruit = false;

    private int mPositionChange;

    public static IntroduceRecruitmentTabFragment newInstance(String type, int month) {
        Bundle args = new Bundle();
        args.putString("type", type);
        args.putInt("month", month);
        IntroduceRecruitmentTabFragment fragment = new IntroduceRecruitmentTabFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private final class onLoadingMoreDataTask implements EndlessScrollListenerRecyclerView.onActionListViewScroll {

        @Override
        public void onApiLoadMoreTask(int page) {
            String search = edtSearch.getText().toString();
            mActionListener.getUserListProcess(search,mMonth, page);
        }
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_introduce_contact_tab1;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new IntroduceRecruitmentTabPresent(this, getContext());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mType = getArguments().getString("type", "");
        mMonth = getArguments().getInt("month", 0);
        initViews();
        loadContactList(ProjectApplication.getInstance().getIntroduce());
    }

    private void initViews() {
        //type introdure, calllater
        if (!mType.equals("") && mType.equals(Contants.INTRODURE)) {
            txtTitle.setText("Khách hàng giới thiệu");
        }
        //get text after 2 seconds
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                handler.removeCallbacks(workRunnable);
                workRunnable = new Runnable() {
                    @Override
                    public void run() {
                        doSmth(edtSearch.getText().toString());
                    }
                };
                handler.postDelayed(workRunnable, 1000 /*delay*/);
            }

            Handler handler = new Handler(Looper.getMainLooper() /*UI thread*/);
            Runnable workRunnable;


            private final void doSmth(String str) {
                mData.clear();
                mActionListener.getUserListProcess(str,mMonth,1);
            }
        });

        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mData = new ArrayList<>();
        mCampaignMonth = ProjectApplication.getInstance().getCampaignRecruit();
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
                    //Toast.makeText(mActivity, "vi tri " + position + " options " + option, Toast.LENGTH_SHORT).show();
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
                    mActionListener.addIntroduceRecruit(edtPhoneAlert.getText().toString(),
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
                        Integer.valueOf(mCampaignMonth.data.campaigns.get(0).id)
                );
                alertDialog.dismiss();
                if (mIsChangeUserToRecruit)
                    showConfirmChange();
                else
                    showpDialogAddNew();

                break;
            }
            case R.id.txt_week2: {
                ProjectApplication.getInstance().setCampaignWeekId(
                        Integer.valueOf(mCampaignMonth.data.campaigns.get(1).id)
                );
                alertDialog.dismiss();
                if (mIsChangeUserToRecruit)
                    showConfirmChange();
                else
                    showpDialogAddNew();

                break;
            }
            case R.id.txt_week3: {
                ProjectApplication.getInstance().setCampaignWeekId(
                        Integer.valueOf(mCampaignMonth.data.campaigns.get(2).id)
                );
                alertDialog.dismiss();
                if (mIsChangeUserToRecruit)
                    showConfirmChange();
                else
                    showpDialogAddNew();
                break;
            }
            case R.id.txt_week4: {
                ProjectApplication.getInstance().setCampaignWeekId(
                        Integer.valueOf(mCampaignMonth.data.campaigns.get(3).id)
                );
                alertDialog.dismiss();
                if (mIsChangeUserToRecruit)
                    showConfirmChange();
                else
                    showpDialogAddNew();
                break;
            }
        }
    }

    public void showConfirmChange() {
        showConfirm("Xác nhận", "Bạn có đồng ý chuyển sang ứng viên khảo sát?", "Đồng ý",
                "Hủy", SweetAlertDialog.WARNING_TYPE, new CallBackConfirmDialog() {
                    @Override
                    public void DiaglogPositive() {
                        List<ContactPerson> dataInput = new ArrayList<>();
                        dataInput.add(new ContactPerson(false, "",
                                mData.get(mPositionChange).getTitle(), mData.get(mPositionChange).getContent(), 0,false));
                        Bundle data = new Bundle();
                        data.putSerializable("data", (Serializable) dataInput);
                        data.putBoolean("isChangeToContact", true);
                        data.putInt("idRelead", mData.get(mPositionChange).getId());
                        data.putBoolean("isRecruit", true);
                        goNextScreenFragment(UpdateContactInfoActivity.class, data, Contants.CHANGE_TO_CONTACT);
                    }

                    @Override
                    public void DiaglogNegative() {

                    }
                });
    }

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

    @OnClick({R.id.txt_add_new,R.id.layout_show_add})
    public void onClickEvent(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.txt_add_new: {
                showDialogChooseWeek(false);
                break;
            }
            case R.id.layout_show_add:{
                if(expandableLayout.isExpanded()){
                    expandableLayout.collapse(true);
                    imgShowAdd.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_down));
                }else{
                    expandableLayout.expand(true);
                    imgShowAdd.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_up));
                }
                break;
            }
        }
    }

    @Override
    public void showDialogChooseWeek(boolean isChangeContact) {

        this.mIsChangeUserToRecruit = isChangeContact;

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

        if (this.mIsChangeUserToRecruit) {
            ((TextView) view.findViewById(R.id.txt_title)).setText("Chuyển " + mData.get(mPositionChange).getTitle() + " sang DS khảo sát");
        } else {
            ((TextView) view.findViewById(R.id.txt_title)).setText("Thêm ứng viên khảo sát");
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
