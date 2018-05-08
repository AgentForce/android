package manulife.manulifesop.activity.FAGroup.personal.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.personal.personalInfo.PersonalInfoContract;
import manulife.manulifesop.activity.FAGroup.personal.personalInfo.PersonalInfoPresenter;
import manulife.manulifesop.activity.FAGroup.personal.setting.changePass.ChangePassActivity;
import manulife.manulifesop.activity.FAGroup.personal.setting.notificationMethod.NotiMethodActivity;
import manulife.manulifesop.api.ObjectResponse.UserProfile;
import manulife.manulifesop.base.BaseActivity;
import manulife.manulifesop.util.SOPSharedPreferences;
import manulife.manulifesop.util.Utils;


public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingContract.View {

    @BindView(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @BindView(R.id.layout_btn_back)
    LinearLayout layoutBackButton;
    @BindView(R.id.status_bar)
    View viewStatusBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mActionListener = new SettingPresenter(this, this);
        setupSupportForApp();
    }

    private void setupSupportForApp() {
        txtActionbarTitle.setText("Cài đặt");
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

    @OnClick({R.id.layout_change_pass,R.id.layout_notification_menu,
            R.id.layout_btn_back})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_btn_back: {
                onBackPressed();
                break;
            }
            case R.id.layout_change_pass: {
                goNextScreen(ChangePassActivity.class);
                break;
            }
            case R.id.layout_notification_menu: {
                goNextScreen(NotiMethodActivity.class);
                break;
            }
        }
    }
}
