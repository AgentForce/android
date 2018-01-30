package manulife.manulifesop.activity.first;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.OnClick;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.login.LoginActivity;
import manulife.manulifesop.base.BaseActivity;


public class FirstActivity extends BaseActivity<FirstPresenter> implements FirstContract.View {

    @BindView(R.id.layout_welcome)
    RelativeLayout layoutWelcome;
    @BindView(R.id.btn_agree)
    Button btnAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        mActionListener = new FirstPresenter(this,this);
    }

    @Override
    public void showWelcome() {
        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        layoutWelcome.startAnimation(in);
        layoutWelcome.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLogin() {
        goNextScreen(LoginActivity.class);
    }

    @OnClick({R.id.btn_agree})
    public void onClick(View view)
    {
        int id = view.getId();
        switch (id)
        {
            case R.id.btn_agree:{
                mActionListener.clickAgreeButton();
                break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mActionListener.checkInternetViaPingServer();
    }
}
