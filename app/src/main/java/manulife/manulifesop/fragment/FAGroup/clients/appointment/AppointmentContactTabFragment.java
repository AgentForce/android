package manulife.manulifesop.fragment.FAGroup.clients.appointment;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Rect;
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
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.appointment.AppointmentActivity;
import manulife.manulifesop.activity.FAGroup.clients.related.contactDetail.ContactDetailActivity;
import manulife.manulifesop.activity.FAGroup.clients.related.createEvent.CreateEventActivity;
import manulife.manulifesop.adapter.ActiveHistAdapter;
import manulife.manulifesop.adapter.ObjectData.ActiveHistFA;
import manulife.manulifesop.api.ObjectResponse.UsersList;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.callbackInterface.CallBackClickContact;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.EndlessScrollListenerRecyclerView;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class AppointmentContactTabFragment extends BaseFragment<AppointmentActivity, AppointmentContactTabPresent> implements AppointmentContactTabContract.View {

    @BindView(R.id.layout_root)
    LinearLayout layoutRoot;

    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.rcv_contact)
    RecyclerView listContact;
    @BindView(R.id.txt_title)
    TextView txtTitle;

    private int mType;
    private String mTypeString;
    private int mMonth;
    private int mTarget;

    private ActiveHistAdapter mAdapterActiveHist;
    private List<ActiveHistFA> mData;
    private LinearLayoutManager mLayoutManager;

    private TextWatcher mTextWatcher;


    public static AppointmentContactTabFragment newInstance(int type, String typeString, int target, int month) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putString("typeString", typeString);
        args.putInt("target", target);
        args.putInt("month", month);
        AppointmentContactTabFragment fragment = new AppointmentContactTabFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private final class onLoadingMoreDataTask implements EndlessScrollListenerRecyclerView.onActionListViewScroll {

        @Override
        public void onApiLoadMoreTask(int page) {
            String search = edtSearch.getText().toString();
            mActionListener.getContact(mMonth, mType, page, search);
        }
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_appointment_contact_tab1;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new AppointmentContactTabPresent(this, getContext());
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
        addTextChangeListener();
    }

    private UsersList getFirstData() {
        UsersList data = new UsersList();
        switch (mType) {
            case Contants.APPOINTMENT_NEED: {
                data = ProjectApplication.getInstance().getAppointMentNeed();
                break;
            }
            case Contants.APPOINTMENT_SEEN: {
                data = ProjectApplication.getInstance().getAppointMentSeen();
                break;
            }
            case Contants.APPOINTMENT_CALLLATER: {
                data = ProjectApplication.getInstance().getAppointMentCallLater();
                break;
            }
            case Contants.APPOINTMENT_REFUSE: {
                data = ProjectApplication.getInstance().getAppointMentRefuse();
                break;
            }
        }
        return data;
    }

    private void initViews() {
        //type = appointment, seen, calllater
        switch (mType) {
            case Contants.APPOINTMENT_NEED: {
                txtTitle.setText("Hẹn gặp(" +
                        ProjectApplication.getInstance().getAppointMentNeed().data.count +
                        "/" + mTarget + ")");
                break;
            }
            case Contants.APPOINTMENT_SEEN: {
                txtTitle.setText("Đã hẹn gặp");
                break;
            }
            case Contants.APPOINTMENT_CALLLATER: {
                txtTitle.setText("Liên hệ sau");
                break;
            }
            case Contants.APPOINTMENT_REFUSE: {
                txtTitle.setText("Từ chối");
                break;
            }
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
                            handler.postDelayed(workRunnable, 1000 /*delay*/);
                        }

                        Handler handler = new Handler(Looper.getMainLooper() /*UI thread*/);
                        Runnable workRunnable;

                        private final void doSmth(String str) {
                            if (mActivity.getSelectedType() == mType) {
                                mData.clear();
                                mActionListener.getContact(mMonth, mType, 1, str);
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
        if(mAdapterActiveHist == null) {
            mAdapterActiveHist = new ActiveHistAdapter(getContext(), mData, new CallBackClickContact() {
                @Override
                public void onClickMenuRight(int position, int option) {
                    switch (option) {
                        case 0: {
                            gotoConactDetail(mData.get(position).getId());
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
                            data.putInt("typeInt", 1);
                            data.putInt("contactID", mData.get(position).getId());
                            data.putString("name", mData.get(position).getTitle());
                            mActivity.goNextScreen(CreateEventActivity.class, data);
                            break;
                        }
                    }
                }

                @Override
                public void onClickMainContent(int position) {
                    gotoConactDetail(mData.get(position).getId());
                }
            });
            listContact.setAdapter(mAdapterActiveHist);
        }else{
            mAdapterActiveHist.notifyDataSetChanged();
        }

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
        data.putString("type_menu", Contants.APPOINTMENT_MENU);
        data.putInt("id", id);
        mActivity.goNextScreen(ContactDetailActivity.class, data, Contants.CONTACT_DETAIL);
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
                mActivity.goNextScreen(IntroduceRecruitmentActivity.class);
                break;
            }
        }
    }*/

}
