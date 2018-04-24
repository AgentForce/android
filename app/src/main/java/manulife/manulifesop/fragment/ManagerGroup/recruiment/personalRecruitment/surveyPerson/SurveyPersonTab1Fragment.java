package manulife.manulifesop.fragment.ManagerGroup.recruiment.personalRecruitment.surveyPerson;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.net.Uri;
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
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.introduceContact.IntroduceContactActivity;
import manulife.manulifesop.activity.FAGroup.clients.related.addContact.AddContactPersonActivity;
import manulife.manulifesop.activity.FAGroup.clients.related.contactDetail.ContactDetailActivity;
import manulife.manulifesop.activity.FAGroup.clients.related.createEvent.CreateEventActivity;
import manulife.manulifesop.activity.FAGroup.clients.related.updateContactInfo.UpdateContactInfoActivity;
import manulife.manulifesop.activity.ManagerGroup.Recruitment.introduceRecruitment.IntroduceRecruitmentActivity;
import manulife.manulifesop.activity.ManagerGroup.Recruitment.survey.SurveyActivity;
import manulife.manulifesop.adapter.ActiveHistAdapter;
import manulife.manulifesop.adapter.ObjectData.ActiveHistFA;
import manulife.manulifesop.adapter.ObjectData.ContactPerson;
import manulife.manulifesop.api.ObjectResponse.CampaignMonth;
import manulife.manulifesop.api.ObjectResponse.CampaignRecruitMonth;
import manulife.manulifesop.api.ObjectResponse.UsersList;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.callbackInterface.CallBackClickContact;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.EndlessScrollListenerRecyclerView;
import manulife.manulifesop.util.Utils;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Chick on 10/27/2017.
 */

public class SurveyPersonTab1Fragment extends BaseFragment<SurveyActivity, SurveyPersonTab1Present> implements SurveyPersonTab1Contract.View,
        View.OnClickListener {

    @BindView(R.id.expandable_layout_top)
    ExpandableLayout expandableLayout;
    @BindView(R.id.img_show_add)
    ImageView imgShowAdd;

    @BindView(R.id.layout_root)
    LinearLayout layoutRoot;

    @BindView(R.id.edt_search)
    EditText edtSearch;

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

    private int mType;
    private String mTypeString;

    private ActiveHistAdapter mAdapterActiveHist;
    private List<ActiveHistFA> mData;
    private LinearLayoutManager mLayoutManager;

    private AlertDialog alertDialog;
    private EditText edtPhoneAlert;
    private EditText edtNameAlert;

    private int mMonth;
    private int mTargetIntroduce;

    private CampaignRecruitMonth mCampaignMonth;
    private boolean misAddFromPhone = false;

    private TextWatcher mTextWatcher;

    public static SurveyPersonTab1Fragment newInstance(int type, String typeString, UsersList usersList, int month, int targetIntroduce) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putString("typeString", typeString);
        args.putSerializable("data", usersList);
        args.putInt("month", month);
        args.putInt("targetIntroduce", targetIntroduce);
        SurveyPersonTab1Fragment fragment = new SurveyPersonTab1Fragment();
        fragment.setArguments(args);
        return fragment;
    }


    private final class onLoadingMoreDataTask implements EndlessScrollListenerRecyclerView.onActionListViewScroll {

        @Override
        public void onApiLoadMoreTask(int page) {
            String search = edtSearch.getText().toString();
            mActionListener.getUserListProcess(mMonth, mType, page, search);
        }
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_contact_person_tab1;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new SurveyPersonTab1Present(this, getContext());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mType = getArguments().getInt("type", 1);
        mTypeString = getArguments().getString("typeString", "");
        mMonth = getArguments().getInt("month", 0);
        mTargetIntroduce = getArguments().getInt("targetIntroduce", 0);
        mCampaignMonth = ProjectApplication.getInstance().getCampaignRecruit();
        initViews();
        //loadDataContact();
        UsersList data = (UsersList) getArguments().getSerializable("data");
        loadContactList(data);
        addTextChangeListener();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mActivity.reloadData();
        }
    }

    private void initViews() {
        //type = contact, calllater
        if (mType == Contants.SURVEY_ADDED) {
            txtTitle.setText("Khảo sát");
        } else if (mType == Contants.SURVEY_CALLLATER) {
            txtTitle.setText("Gọi lại sau");
        } else {
            txtTitle.setText("Từ chối");
        }
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mData = new ArrayList<>();
    }

    @Override
    public void onResume() {
        super.onResume();
        edtSearch.setText("", TextView.BufferType.EDITABLE);
    }

    @Override
    public void addTextChangeListener() {

        layoutRoot.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                layoutRoot.getWindowVisibleDisplayFrame(r);
                int screenHeight = layoutRoot.getRootView().getHeight();

                // r.bottom is the position above soft keypad or device button.
                // if keypad is shown, the r.bottom is smaller than that before.
                int keypadHeight = screenHeight - r.bottom;

                if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                    // keyboard is opened
                    if (mTextWatcher != null) {
                        edtSearch.removeTextChangedListener(mTextWatcher);
                    }
                    mTextWatcher = new TextWatcher() {
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
                            handler.postDelayed(workRunnable, 1000);
                        }

                        Handler handler = new Handler(Looper.getMainLooper());
                        Runnable workRunnable;

                        private final void doSmth(String str) {
                            if (mActivity.getSelectedType() == mType) {
                                mData.clear();
                                mActionListener.getUserListProcess(mMonth, mType, 1, str);
                                if (mTextWatcher != null) {
                                    edtSearch.removeTextChangedListener(mTextWatcher);
                                }
                            }
                        }
                    };
                    //get text after 2 seconds
                    edtSearch.addTextChangedListener(mTextWatcher);
                } else {
                    // keyboard is closed
                    if (mTextWatcher != null) {
                        edtSearch.removeTextChangedListener(mTextWatcher);
                    }
                }
            }
        });

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
        if(mAdapterActiveHist == null) {
            mAdapterActiveHist = new ActiveHistAdapter(getContext(), mData, new CallBackClickContact() {
                @Override
                public void onClickMenuRight(int position, int option) {
                    switch (option) {
                        case 0: {
                            gotoContactDetail(mData.get(position).getId());
                            break;
                        }
                        case 1: {
                            String phone = "tel:" + mData.get(position).getContent();
                            Intent callIntent = new Intent(Intent.ACTION_CALL);
                            callIntent.setData(Uri.parse(phone));
                            startActivity(callIntent);
                            break;
                        }
                        case 2: {
                            Bundle data = new Bundle();
                            data.putInt("typeInt", 7);
                            data.putInt("contactID", mData.get(position).getId());
                            data.putString("name", mData.get(position).getTitle());
                            data.putBoolean("isRecruit",true);
                            mActivity.goNextScreen(CreateEventActivity.class, data);
                            break;
                        }
                    }
                }

                @Override
                public void onClickMainContent(int position) {
                    gotoContactDetail(mData.get(position).getId());
                }
            });
            listContact.setAdapter(mAdapterActiveHist);
        }else{
            mAdapterActiveHist.notifyDataSetChanged();
        }

        listContact.clearOnScrollListeners();
        listContact.addOnScrollListener(new EndlessScrollListenerRecyclerView(
                Integer.valueOf(data.data.page),
                Utils.genLastPage(data.data.count,
                        Integer.valueOf(data.data.limit)), new onLoadingMoreDataTask(), mLayoutManager));
    }

    @Override
    public void gotoContactDetail(int id) {
        Bundle data = new Bundle();
        data.putString("type", mTypeString);
        data.putString("type_menu", Contants.SURVEY_MENU);
        data.putInt("id", id);
        //data.putString("type_menu",Contants.SIGNED_MENU);
        mActivity.goNextScreen(ContactDetailActivity.class, data, Contants.CONTACT_DETAIL);
    }

    private void showpDialogAddNew() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_new_contact, null);

        initDialogEvent(dialogView);
        hideKeyboardOutside(dialogView, mActivity);
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
        switch (id) {
            //create event
            case R.id.txt_cancel: {
                alertDialog.dismiss();
                break;
            }
            case R.id.txt_add: {
                if (validateInputAddContact()) {
                    List<ContactPerson> dataInput = new ArrayList<>();
                    dataInput.add(new ContactPerson(false, "",
                            edtNameAlert.getText().toString(), edtPhoneAlert.getText().toString(), 0, false));
                    Bundle data = new Bundle();
                    data.putSerializable("data", (Serializable) dataInput);
                    data.putBoolean("isRecruit", true);
                    mActivity.goNextScreen(UpdateContactInfoActivity.class, data, Contants.ADD_CONTACT);
                    alertDialog.dismiss();
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
                if (misAddFromPhone) {
                    alertDialog.dismiss();
                    Bundle data = new Bundle();
                    data.putBoolean("isRecruit", true);
                    mActivity.goNextScreen(AddContactPersonActivity.class,data,Contants.ADD_CONTACT);
                } else {
                    alertDialog.dismiss();
                    showpDialogAddNew();
                }
                break;
            }
            case R.id.txt_week2: {
                ProjectApplication.getInstance().setCampaignWeekId(
                        Integer.valueOf(mCampaignMonth.data.campaigns.get(1).id)
                );
                if (misAddFromPhone) {
                    alertDialog.dismiss();
                    Bundle data = new Bundle();
                    data.putBoolean("isRecruit", true);
                    mActivity.goNextScreen(AddContactPersonActivity.class, data,Contants.ADD_CONTACT);
                } else {
                    alertDialog.dismiss();
                    showpDialogAddNew();
                }
                break;
            }
            case R.id.txt_week3: {
                ProjectApplication.getInstance().setCampaignWeekId(
                        Integer.valueOf(mCampaignMonth.data.campaigns.get(2).id)
                );
                if (misAddFromPhone) {
                    alertDialog.dismiss();
                    Bundle data = new Bundle();
                    data.putBoolean("isRecruit", true);
                    mActivity.goNextScreen(AddContactPersonActivity.class, data,Contants.ADD_CONTACT);
                } else {
                    alertDialog.dismiss();
                    showpDialogAddNew();
                }
                break;
            }
            case R.id.txt_week4: {
                ProjectApplication.getInstance().setCampaignWeekId(
                        Integer.valueOf(mCampaignMonth.data.campaigns.get(3).id)
                );
                if (misAddFromPhone) {
                    alertDialog.dismiss();
                    Bundle data = new Bundle();
                    data.putBoolean("isRecruit", true);
                    mActivity.goNextScreen(AddContactPersonActivity.class, data,Contants.ADD_CONTACT);
                } else {
                    alertDialog.dismiss();
                    showpDialogAddNew();
                }
                break;
            }
        }
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

    @OnClick({R.id.txt_add_from_telephone, R.id.txt_add_new,
            R.id.txt_add_from_introduce, R.id.layout_show_add})
    public void onClickEvent(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.txt_add_from_telephone: {
                misAddFromPhone = true;
                showDialogChooseWeek();
                break;
            }
            case R.id.txt_add_new: {
                misAddFromPhone = false;
                showDialogChooseWeek();
                break;
            }
            case R.id.txt_add_from_introduce: {
                Bundle data = new Bundle();
                data.putInt("target", mTargetIntroduce);
                data.putInt("month", mMonth);
                data.putBoolean("isFromRecruit", true);
                mActivity.goNextScreen(IntroduceRecruitmentActivity.class, data, Contants.ADD_INTRODUCE_FROM_CONTACT);
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

    private void initDialogEventAddContact(View view) {
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
