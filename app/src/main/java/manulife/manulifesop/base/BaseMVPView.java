package manulife.manulifesop.base;

import android.content.Context;
import android.os.Bundle;

import manulife.manulifesop.element.callbackInterface.CallBackConfirmDialog;
import manulife.manulifesop.element.callbackInterface.CallBackInformDialog;

/**
 * Created by trinm on 12/01/2018.
 */
public interface BaseMVPView {

    void showDisconnectNetwork(boolean isShow);
    void showMessage(String title,String message,int messageType);
    void showInform(String title , String message, String text_button, int msgType, CallBackInformDialog callback);
    void showConfirm(String title, String message, String text_pos, String text_neg, int msgType,CallBackConfirmDialog callback);
    void backToPrevious(Bundle bundle);
    void showLoading(String message);
    void finishLoading(String message, boolean isSuccess);
    void finishLoading();
}
