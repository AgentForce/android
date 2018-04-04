package manulife.manulifesop.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.adapter.ObjectData.ContactPerson;
import manulife.manulifesop.element.callbackInterface.CallBackConfirmDialog;
import manulife.manulifesop.element.callbackInterface.CallBackInformDialog;

/**
 * Created by trinm on 12/01/2018.
 */
public abstract class BaseFragment<T extends BaseActivity, P extends BasePresenter> extends Fragment {

    protected T mActivity;
    protected P mActionListener;

    private SweetAlertDialog pDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (T) context;
    }

    @LayoutRes
    public abstract int contentViewLayout();

    public abstract void initializeLayout(View view);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(contentViewLayout(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initializeLayout(view);
    }

    @Override
    public void onStop() {
        if (mActionListener != null) {
            mActionListener.unSubscribeRequests();
        }
        super.onStop();
    }

    public <T extends Activity> void goNextScreenFragment(Class<T> clazz, Bundle bun, int requestCode) {
        Intent intent = new Intent(getContext(), clazz);
        intent.putExtras(bun);
        startActivityForResult(intent, requestCode);
        mActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public <T extends Activity> void goNextScreenFragment(Class<T> clazz, int requestCode) {
        Intent intent = new Intent(getContext(), clazz);
        startActivityForResult(intent, requestCode);
        mActivity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void showMessage(String title, String message, int messageType) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(mActivity, messageType);
        sweetAlertDialog.setTitleText("Thông báo");
        sweetAlertDialog.setContentText(message);
        sweetAlertDialog.setConfirmText("OK");
        sweetAlertDialog.setCanceledOnTouchOutside(true);
        sweetAlertDialog.show();
    }

    public void showInform(String title, String message, String text_button, int msgType, CallBackInformDialog callback) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(mActivity, msgType);
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

    public void showConfirm(String title, String message, String text_pos, String text_neg, int msgType, CallBackConfirmDialog callback) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(mActivity, msgType);
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

    public void backToPrevious(Bundle bundle) {
        mActivity.backToPrevious(bundle);
    }

    public void showDisconnectNetwork(boolean isShow) {
        mActivity.showDisconnectNetwork(isShow);
    }

    public void showLoading(String message) {
        mActivity.showLoading(message);
    }

    public void finishLoading(String message, boolean isSuccess) {
        mActivity.finishLoading(message,isSuccess);
    }

    public void finishLoading() {
        mActivity.finishLoading();
    }
}
