package manulife.manulifesop.activity.FAGroup.personal.activityHistSetting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.clients.related.createEvent.CreateEventActivity;
import manulife.manulifesop.adapter.ActiveHistAdapter;
import manulife.manulifesop.adapter.ActiveHistSettingAdapter;
import manulife.manulifesop.adapter.ObjectData.ActiveHistSetting;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.element.callbackInterface.CallBackClickContact;
import manulife.manulifesop.fragment.FAGroup.dashboardv2.FADashBoardFragment;
import manulife.manulifesop.util.EndlessScrollListenerRecyclerView;
import manulife.manulifesop.util.Utils;


public class ActivityHistSettingActivity extends BaseActivity<ActivityHistSettingPresenter> implements ActivityHistSettingContract.View {

    @BindView(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @BindView(R.id.layout_btn_back)
    LinearLayout layoutBackButton;
    @BindView(R.id.status_bar)
    View viewStatusBar;

    @BindView(R.id.list_data)
    RecyclerView listData;

    private ActiveHistSettingAdapter mAdapter;
    private List<ActiveHistSetting> mData;
    private LinearLayoutManager mLayoutManager;

    private final class onLoadingMoreDataTask implements EndlessScrollListenerRecyclerView.onActionListViewScroll {

        @Override
        public void onApiLoadMoreTask(int page) {
            mActionListener.getActivitiHist(page,10);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_hist);
        mActionListener = new ActivityHistSettingPresenter(this, this);
        setupSupportForApp();
        initViews();
        mActionListener.getActivitiHist(1,10);
    }
    private void initViews(){
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mData = new ArrayList<>();
        listData.setLayoutManager(mLayoutManager);
    }

    @Override
    public void showData(List<ActiveHistSetting> data,int currentPage, int lastPage) {
        mData.addAll(data);
        if(mAdapter == null) {
            mAdapter = new ActiveHistSettingAdapter(getApplicationContext(), mData);
            listData.setAdapter(mAdapter);
        }else{
            mAdapter.notifyDataSetChanged();
        }
        listData.clearOnScrollListeners();
        listData.addOnScrollListener(new EndlessScrollListenerRecyclerView(
                currentPage,lastPage,
                new onLoadingMoreDataTask(), mLayoutManager));
    }

    private void setupSupportForApp() {
        txtActionbarTitle.setText("Nhật ký hoạt động");
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

    @OnClick({R.id.layout_btn_back})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_btn_back: {
                onBackPressed();
                break;
            }
        }
    }
}
