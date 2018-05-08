package manulife.manulifesop.activity.FAGroup.personal.setting.notificationMethod;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.FAGroup.personal.setting.SettingContract;
import manulife.manulifesop.activity.FAGroup.personal.setting.SettingPresenter;
import manulife.manulifesop.activity.FAGroup.personal.setting.changePass.ChangePassActivity;
import manulife.manulifesop.api.ObjectResponse.NotiConfig;
import manulife.manulifesop.base.BaseActivity;


public class NotiMethodActivity extends BaseActivity<NotiMethodPresenter> implements NotiMethodContract.View {

    @BindView(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @BindView(R.id.layout_btn_back)
    LinearLayout layoutBackButton;
    @BindView(R.id.status_bar)
    View viewStatusBar;

    @BindView(R.id.switch_sms)
    SwitchCompat switchSms;
    @BindView(R.id.switch_mail)
    SwitchCompat switchMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noti_method);
        mActionListener = new NotiMethodPresenter(this, this);
        setupSupportForApp();
        mActionListener.getNotiConfig();
    }

    private void setupSupportForApp() {
        txtActionbarTitle.setText("Phương thức nhận thông báo");
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
    public void showData(NotiConfig data) {
        if(data.data.isEmail == 1)
            switchMail.setChecked(true);
        else
            switchMail.setChecked(false);

        if(data.data.isSMS == 1)
            switchSms.setChecked(true);
        else
            switchSms.setChecked(false);
    }

    @OnClick({R.id.layout_btn_back,R.id.btn_ok})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layout_btn_back: {
                onBackPressed();
                break;
            }
            case R.id.btn_ok:{
                mActionListener.updateNotiConfig(switchSms.isChecked(),switchMail.isChecked());
                break;
            }
        }
    }
}
