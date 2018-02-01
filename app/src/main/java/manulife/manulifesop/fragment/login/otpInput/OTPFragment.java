package manulife.manulifesop.fragment.login.otpInput;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.R;
import manulife.manulifesop.activity.login.LoginActivity;
import manulife.manulifesop.base.BaseFragment;


/**
 * Created by Chick on 10/27/2017.
 */

public class OTPFragment extends BaseFragment<LoginActivity, OTPPresent> implements OTPContract.View {


    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.txt_resent_otp)
    TextView txtResentOTP;
    @BindView(R.id.edt_otp)
    EditText edtOTP;
    @BindView(R.id.btn_request_otp)
    Button btnRequestOTP;
    @BindView(R.id.txt_otp_title)
    TextView txtOTPTitle;

    @BindView(R.id.layout_root)
    RelativeLayout layoutRoot;
    @BindView(R.id.layout_top)
    LinearLayout layoutTop;

    public static OTPFragment newInstance() {
        Bundle args = new Bundle();
        OTPFragment fragment = new OTPFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int contentViewLayout() {
        return R.layout.fragment_login_otp;
    }

    @Override
    public void initializeLayout(View view) {
        mActionListener = new OTPPresent(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        showHideViewWhenSoftKeyActive();
    }

    @Override
    public void onResume() {
        String title = getResources().getString(R.string.activity_login_otp_title) + " <b>" + mActivity.getPhoneInputed() + "</b>";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
            txtOTPTitle.setText((Html.fromHtml(title, Html.FROM_HTML_MODE_COMPACT)));
        else
            txtOTPTitle.setText((Html.fromHtml(title)));
        super.onResume();
    }

    private void showHideViewWhenSoftKeyActive() {
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
                    //Animation out = AnimationUtils.loadAnimation(mActivity, R.anim.fade_out);
                    //layoutTop.startAnimation(out);
                    layoutTop.setVisibility(View.GONE);
                } else {
                    // keyboard is closed
                    Animation in = AnimationUtils.loadAnimation(mActivity, R.anim.fade_in);
                    layoutTop.startAnimation(in);
                    layoutTop.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick(R.id.btn_next)
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_next: {
                if (edtOTP.getText().toString().length() > 0) {
                    //mActivity.showFragmentPhoneInput(edtAgencyName.getText().toString());
                    //mActivity.showCheckingUser(edtPhone.getText().toString());
                    mActivity.showCheckOTP(edtOTP.getText().toString());

                } else {
                    mActivity.showMessage("Thông báo", "Chưa nhập mã xác thực!", SweetAlertDialog.WARNING_TYPE);
                }
                break;
            }
        }
    }
}
