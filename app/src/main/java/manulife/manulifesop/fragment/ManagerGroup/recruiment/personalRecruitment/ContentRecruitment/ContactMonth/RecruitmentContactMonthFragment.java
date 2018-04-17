package manulife.manulifesop.fragment.ManagerGroup.recruiment.personalRecruitment.ContentRecruitment.ContactMonth;

import android.app.Activity;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.related.contactDetail.ContactDetailActivity;
import manulife.manulifesop.activity.FAGroup.clients.related.createEvent.CreateEventActivity;
import manulife.manulifesop.activity.main.MainFAActivity;
import manulife.manulifesop.adapter.ContactAllFAAdapter;
import manulife.manulifesop.adapter.ObjectData.ContactAllFA;
import manulife.manulifesop.base.BaseFragment;
import manulife.manulifesop.element.callbackInterface.CallBackClickContact;
import manulife.manulifesop.util.Contants;
import manulife.manulifesop.util.EndlessScrollListenerRecyclerView;
import manulife.manulifesop.util.Utils;

/**
 * Created by Chick on 10/27/2017.
 */

public class RecruitmentContactMonthFragment extends BaseFragment<MainFAActivity, RecruitmentContactMonthPresent> implements RecruitmentContactMonthContract.View {

    @BindView(R.id.layout_root)
    LinearLayout layoutRoot;

    @BindView(R.id.edt_search)
    EditText edtSearch;
    @BindView(R.id.rcv_contact)
    RecyclerView listContact;

    private ContactAllFAAdapter mAdapter;
    private List<ContactAllFA> mData;
    private LinearLayoutManager mLayoutManager;

    private TextWatcher mTextWatcher;

    private int mMonth;

    private int mCurrentPage, mLastPage;

    private boolean isFromCreatedView = false;

    public static RecruitmentContactMonthFragment newInstance(int month) {
        Bundle args = new Bundle();
        args.putInt("month", month);
        RecruitmentContactMonthFragment fragment = new RecruitmentContactMonthFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private final class onLoadingMoreDataTask implements EndlessScrollListenerRecyclerView.onActionListViewScroll {

        @Override
        public void onApiLoadMoreTask(int page) {
            String search = edtSearch.getText().toString();
            mActionListener.getContactMonthRecruitment(mMonth, search, page);
        }
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_all_contact_month;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new RecruitmentContactMonthPresent(this, getContext());
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMonth = getArguments().getInt("month", 0);
        hideKeyboardOutside(layoutRoot);
        isFromCreatedView = true;
        initViews();
        addTextChangeListener();
        loadFirstData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isFromCreatedView){
            edtSearch.setText("", TextView.BufferType.EDITABLE);
            isFromCreatedView = false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == Contants.CONTACT_DETAIL) {
            String search = edtSearch.getText().toString();
            mData.clear();
            mActionListener.getContactMonthRecruitment(mMonth, search, 1);
        }
    }

    @Override
    public void loadFirstData() {
        mData.clear();
        Observable.just(ProjectApplication.getInstance().getContactMonth())
                .map(data ->
                {
                    mCurrentPage = data.data.page;
                    mLastPage = Utils.genLastPage(data.data.count, Integer.valueOf(data.data.limit));
                    List<ContactAllFA> datatmp = new ArrayList<>();
                    if (data.statusCode == 1 && data.data.count > 0) {
                        ContactAllFA tmp;
                        for (int i = 0; i < data.data.rows.size(); i++) {
                            tmp = new ContactAllFA();
                            tmp.setId(data.data.rows.get(i).id);
                            tmp.setCampaignID(data.data.rows.get(i).campId);
                            tmp.setTitle(data.data.rows.get(i).name);
                            tmp.setContent(data.data.rows.get(i).phone);
                            tmp.setPhone(data.data.rows.get(i).phone);
                            tmp.setProcessStep(data.data.rows.get(i).processStep);
                            tmp.setStatusStep(data.data.rows.get(i).statusProcessStep);

                            datatmp.add(tmp);
                        }
                    }
                    return datatmp;

                }).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contactAllFAS -> {
                    loadContactMonth(contactAllFAS, mCurrentPage, mLastPage);
                });

    }

    private void initViews() {
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        listContact.setLayoutManager(mLayoutManager);
        mData = new ArrayList<>();
    }

    @Override
    public void loadContactMonth(List<ContactAllFA> data, int currentPage, int lastPage) {

        mData.addAll(data);

        mAdapter = new ContactAllFAAdapter(getContext(), mData, new CallBackClickContact() {
            @Override
            public void onClickMenuRight(int position, int option) {
                switch (option) {
                    case 0: {
                        gotoConactDetail(mData.get(position).getId(), mData.get(position).getProcessStep(),
                                mData.get(position).getStatusStep());
                        break;
                    }
                    case 1: {
                        String phone = "tel:" + mData.get(position).getPhone();
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
                gotoConactDetail(mData.get(position).getId(), mData.get(position).getProcessStep(),
                        mData.get(position).getStatusStep());

            }
        });
        listContact.setAdapter(mAdapter);

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
                currentPage
                , lastPage
                , new onLoadingMoreDataTask(), mLayoutManager));
    }

    @Override
    public void gotoConactDetail(int id, int progressStep, int statusstep) {

        String typeString = ProjectApplication.getInstance().getStringProcessStatusName(
                progressStep + "" + statusstep
        );
        String typeMenu;
        switch (progressStep) {
            case 1: {
                typeMenu = Contants.CONTACT_MENU;
                break;
            }
            case 2: {
                typeMenu = Contants.APPOINTMENT_MENU;
                break;
            }
            case 3: {
                typeMenu = Contants.CONSULTANT_MENU;
                break;
            }
            case 4: {
                typeMenu = Contants.SIGNED_MENU;
                break;
            }
            default: {
                typeMenu = Contants.CONTACT_MENU;
                break;
            }
        }

        Bundle data = new Bundle();
        data.putString("type", typeString);
        data.putString("type_menu", typeMenu);
        data.putInt("id", id);
        goNextScreenFragment(ContactDetailActivity.class, data, Contants.CONTACT_DETAIL);
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
                            mData.clear();
                            mActionListener.getContactMonthRecruitment(mMonth, str, 1);
                            if (mTextWatcher != null) {
                                edtSearch.removeTextChangedListener(mTextWatcher);

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
}
