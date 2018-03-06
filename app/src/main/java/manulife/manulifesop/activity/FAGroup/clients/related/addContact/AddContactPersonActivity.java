package manulife.manulifesop.activity.FAGroup.clients.related.addContact;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.related.updateContactInfo.UpdateContactInfoActivity;
import manulife.manulifesop.adapter.ContactPersonAdapter;
import manulife.manulifesop.adapter.ObjectData.ContactPerson;
import manulife.manulifesop.base.BaseActivity;


public class AddContactPersonActivity extends BaseActivity<AddContactPersonPresenter> implements AddContactPersonContract.View {

    @BindView(R.id.layout_root)
    LinearLayout layoutRoot;

    @BindView(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @BindView(R.id.layout_btn_back)
    LinearLayout layoutBackButton;
    @BindView(R.id.status_bar)
    View viewStatusBar;

    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.edt_search)
    EditText edtSearch;

    @BindView(R.id.rcv_contact_phone)
    RecyclerView listContact;

    private StickyHeaderDecoration decor;
    private ContactPersonAdapter adapter;
    private List<ContactPerson> mDataAll;
    private List<ContactPerson> mDataChoosed;

    private List<ContactPerson> mDataSearchResult;
    private LinearLayoutManager mLayoutManager;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        hideKeyboardOutside(layoutRoot, this);
        mContext = this;
        mDataChoosed = new ArrayList<>();
        mActionListener = new AddContactPersonPresenter(this, this);
        setupSupportForApp();
        initViews();
        mActionListener.readAllContacts();
    }



    private void initViews() {

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        listContact.setLayoutManager(mLayoutManager);

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
                handler.postDelayed(workRunnable, 2000 /*delay*/);
            }

            Handler handler = new Handler(Looper.getMainLooper() /*UI thread*/);
            Runnable workRunnable;


            private final void doSmth(String str) {
                mDataSearchResult = new ArrayList<>();
                for (int i = 0; i < mDataAll.size(); i++) {
                    String tmp = mDataAll.get(i).getName().toLowerCase();
                    if (tmp.contains(str.toLowerCase())) {
                        mDataSearchResult.add(mDataAll.get(i));
                    }
                }
                adapter = new ContactPersonAdapter(mContext, mDataSearchResult);
                decor = new StickyHeaderDecoration(adapter);
                listContact.setAdapter(adapter);
                listContact.removeItemDecoration(listContact.getItemDecorationAt(0));
                listContact.addItemDecoration(decor);
            }
        });
    }

    private void setupSupportForApp() {
        //txtActionbarTitle.setText(getResources().getString(R.string.activity_create_plan_title_actionbar));
        txtActionbarTitle.setText("Thêm liên hệ (0/10)");
        layoutBackButton.setVisibility(View.VISIBLE);

        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewStatusBar.getLayoutParams();
        params.height = statusBarHeight;
        viewStatusBar.setLayoutParams(params);
    }

    @Override
    public void loadDateList(List<ContactPerson> data) {

        mDataAll = new ArrayList<>();
        mDataAll.addAll(data);

        if (adapter == null) {
            adapter = new ContactPersonAdapter(this, mDataAll);
            decor = new StickyHeaderDecoration(adapter);
            listContact.setAdapter(adapter);
            listContact.addItemDecoration(decor);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updateTitleBar(int num) {
        txtActionbarTitle.setText("Thêm liên hệ (" + num + "/10)");
    }

    @Override
    public void setCheckStusList(int position, boolean checked) {
        mDataAll.get(position).setChecked(checked);
    }

    @Override
    public void updateContactChoosed(ContactPerson data, boolean isAdd) {
        if (isAdd) {
            mDataChoosed.add(data);
        } else {
            mDataChoosed.remove(data);
        }
        updateTitleBar(mDataChoosed.size());
    }

    @Override
    public int getSizeChoosed() {
        return mDataChoosed.size();
    }

    @OnClick({R.id.btn_add,R.id.layout_btn_back})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_add: {
                if (adapter != null) {
                    //List<ContactPerson> data = adapter.getDataChoosed();
                    if (mDataChoosed.size() > 0) {
                        //showMessage("Thông báo", mDataChoosed.size() + mDataChoosed.get(0).getName(), SweetAlertDialog.WARNING_TYPE);
                        Bundle data = new Bundle();
                        data.putSerializable("data", (Serializable) mDataChoosed);
                        goNextScreen(UpdateContactInfoActivity.class, data);
                    } else {
                        showMessage("Thông báo", "Chọn ít nhất một số điện thoại", SweetAlertDialog.WARNING_TYPE);
                    }
                }
                break;
            }
            case R.id.layout_btn_back: {
                onBackPressed();
                break;
            }
        }
    }
}
