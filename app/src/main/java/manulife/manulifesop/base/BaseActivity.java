package manulife.manulifesop.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.element.callbackInterface.CallBackConfirmDialog;
import manulife.manulifesop.element.callbackInterface.CallBackInformDialog;
import manulife.manulifesop.util.Utils;

/**
 * Created by trinm on 12/01/2018.
 */
public class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements BaseMVPView {

    protected ProgressDialog mProgressDialog;
    protected P mActionListener;
    protected FrameLayout flMain;
    protected RelativeLayout rlDisconnect;

    private SweetAlertDialog pDialog;


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.registerReceiver(receiver, filter);
        if (isFirstInit) {
            isFirstInit = false;
        } else {
            if (mActionListener != null) {
                mActionListener.reconnectNetwork();
            }
        }*/
        if (mActionListener != null) {
            mActionListener.reconnectNetwork();
        }
    }

    @Override
    public void showDisconnectNetwork(boolean isShow) {
        if (isShow) {
            showInform("Thông báo", "Không có mạng, không chạy được chương trình!",
                    "OK", SweetAlertDialog.WARNING_TYPE, new CallBackInformDialog() {
                        @Override
                        public void DiaglogPositive() {
                            System.exit(0);
                        }
                    });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        broadcastManager.unregisterReceiver(receiver);*/
    }

    @Override
    protected void onStop() {
        if (mActionListener != null) {
            mActionListener.unSubscribeRequests();
        }

        super.onStop();
    }

    public String getTag() {
        return this.getLocalClassName();
    }



    protected void hideKeyboardOutside(View view, final Activity activity) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(
                    (v, event) -> {
                        Utils.hideSoftKeyboard(activity);
                        v.clearFocus();
                        return false;
                    });
        }
        //If a layout container, iterate over children
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                hideKeyboardOutside(innerView, activity);
            }
        }
    }

    public <T extends Activity> void goNextScreen(Class<T> clazz) {
        goNextScreen(clazz, null, false);
    }

    public <T extends Activity> void goNextScreen(Class<T> clazz, Bundle bun) {
        goNextScreen(clazz, bun, false);
    }

    public <T extends Activity> void goNextScreen(Class<T> clazz, boolean isFinishAll) {
        goNextScreen(clazz, null, isFinishAll);
    }

    public <T extends Activity> void goNextScreen(Class<T> clazz, Bundle bun, boolean isFinishAll) {
        Intent intent = new Intent(this, clazz);
        if (bun != null) {
            intent.putExtras(bun);
        }
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        if (isFinishAll) {
            finishAffinity();
        }
    }

    public void restartActivity(Bundle bun) {
        Intent intent = getIntent();
        intent.putExtras(bun);
        finish();
        startActivity(intent);
    }

    public <T extends Activity> void goNextScreen(Class<T> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public <T extends Activity> void goNextScreen(Class<T> clazz, Bundle bun, int requestCode) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(bun);
        startActivityForResult(intent, requestCode);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void showMessage(String title,String message,int messageType) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this,messageType);
        sweetAlertDialog.setTitleText(title);
        sweetAlertDialog.setContentText(message);
        sweetAlertDialog.setConfirmText("OK");
        sweetAlertDialog.setCanceledOnTouchOutside(true);
        sweetAlertDialog.show();
    }

    @Override
    public void showInform(String title, String message, String text_button, int msgType,CallBackInformDialog callback) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this,msgType);
        sweetAlertDialog.setTitleText(title);
        sweetAlertDialog.setContentText(message);
        sweetAlertDialog.setConfirmText(text_button);
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismiss();
                callback.DiaglogPositive();
            }
        });
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.show();
    }

    @Override
    public void showConfirm(String title, String message, String text_pos, String text_neg, int msgType, CallBackConfirmDialog callback) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this,msgType);
        sweetAlertDialog.setTitleText(title);
        sweetAlertDialog.setContentText(message);
        sweetAlertDialog.setConfirmText(text_pos);
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
                callback.DiaglogPositive();
            }
        });
        sweetAlertDialog.setCancelText(text_neg);
        sweetAlertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
                callback.DiaglogNegative();
            }
        });
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.show();
    }

    @Override
    public void backToPrevious(Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void showLoading(String message) {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(message);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    public void finishLoading(String message, boolean isSuccess) {
        pDialog.setCanceledOnTouchOutside(true);
        pDialog.setTitleText(message);
        pDialog.setConfirmText("OK");
        if(isSuccess) {
            pDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
        }else
        {
            pDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
        }
    }

    @Override
    public void finishLoading() {
        pDialog.dismissWithAnimation();
    }
}
