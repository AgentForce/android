package manulife.manulifesop.activity.FAGroup.personal.activityNewsSetting;

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
import manulife.manulifesop.adapter.ActiveNewsSettingAdapter;
import manulife.manulifesop.adapter.ObjectData.ActiveNewsSetting;
import manulife.manulifesop.base.BaseActivity;


public class ActivityNewsSettingActivity extends BaseActivity<ActivityNewsSettingPresenter> implements ActivityNewsSettingContract.View {

    @BindView(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @BindView(R.id.layout_btn_back)
    LinearLayout layoutBackButton;
    @BindView(R.id.status_bar)
    View viewStatusBar;

    @BindView(R.id.list_data)
    RecyclerView listData;

    private ActiveNewsSettingAdapter mAdapter;
    private List<ActiveNewsSetting> mData;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_hist);
        mActionListener = new ActivityNewsSettingPresenter(this, this);
        setupSupportForApp();
        initViews();

        test();
    }
    private void initViews(){
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mData = new ArrayList<>();
        listData.setLayoutManager(mLayoutManager);
    }
    private void test(){

        for (int i = 0; i < 5; i++) {
            ActiveNewsSetting temp = new ActiveNewsSetting();
            temp.setTitle("Title " + i);
            temp.setName("Name " + i);
            temp.setTime("Time " + i);
            mData.add(temp);
        }
        if(mAdapter == null) {
            mAdapter = new ActiveNewsSettingAdapter(getApplicationContext(), mData);
            listData.setAdapter(mAdapter);
        }else{
            mAdapter.notifyDataSetChanged();
        }
    }


    private void setupSupportForApp() {
        txtActionbarTitle.setText("Tin tức hoạt động");
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
