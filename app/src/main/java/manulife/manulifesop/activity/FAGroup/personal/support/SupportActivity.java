package manulife.manulifesop.activity.FAGroup.personal.support;

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
import manulife.manulifesop.activity.FAGroup.personal.activityHistSetting.ActivityHistSettingContract;
import manulife.manulifesop.activity.FAGroup.personal.activityHistSetting.ActivityHistSettingPresenter;
import manulife.manulifesop.adapter.ActiveHistSettingAdapter;
import manulife.manulifesop.adapter.ObjectData.ActiveHistSetting;
import manulife.manulifesop.base.BaseActivity;


public class SupportActivity extends BaseActivity<SupportPresenter> implements SupportContract.View {

    @BindView(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @BindView(R.id.layout_btn_back)
    LinearLayout layoutBackButton;
    @BindView(R.id.status_bar)
    View viewStatusBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);
        mActionListener = new SupportPresenter(this, this);
        setupSupportForApp();
    }

    private void setupSupportForApp() {
        txtActionbarTitle.setText("Hỗ trợ");
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
