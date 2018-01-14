package manulife.manulifesop.base;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.EditText;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.reactivex.disposables.CompositeDisposable;
import manulife.manulifesop.util.Utils;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by trinm on 12/01/2018.
 */
public abstract class BasePresenter<T extends BaseMVPView> implements BaseMVPAction {

    protected final T mPresenterView;
    private CompositeDisposable mDisposables;
    //protected Subscription mSubscription;

    public BasePresenter(T presenterView) {
        mPresenterView = checkNotNull(presenterView, "presenterView cannot be null");
    }

    @Override
    public CompositeDisposable getCompositeDisposable() {
        if (mDisposables == null || mDisposables.isDisposed()) {
            mDisposables = new CompositeDisposable();
        }
        return mDisposables;
    }

    @Override
    public void unSubscribeRequests() {

        if(mDisposables != null && !mDisposables.isDisposed()) {
            mDisposables.clear();
            mDisposables.dispose();
        }
    }

    @Override
    public void reconnectNetwork() {
        mPresenterView.showDisconnectNetwork(Utils.isConnectedToNetwork() ? false : true);
    }

    @Override
    public void errorException(Throwable throwable) {
        /*String returnValue = Utils.getString(R.string.error_unknown_error);
        if (throwable instanceof UnknownHostException
                || throwable instanceof SocketTimeoutException
                || throwable instanceof ConnectException) {
            returnValue = Utils.getString(R.string.error_no_internet);
            mPresenterView.showDisconnectNetwork(true);
        } else if (throwable instanceof RuntimeException) {
            returnValue = throwable.getMessage();
        } else if (throwable instanceof HttpException) {
            BaseResponse response = Utils.parseErrorMessage(throwable);
            if (response != null) {
                returnValue = response.getMessage();
            }
        }
        mPresenterView.showMessage(returnValue);*/
    }

    @Override
    public boolean isEmptyField(@NonNull EditText editText, @NonNull String message) {
        boolean returnValue = false;
        if (TextUtils.isEmpty(editText.getText().toString().trim())) {
            returnValue = true;
            //mPresenterView.focusView(editText);
            mPresenterView.showMessage("Thông báo",message, SweetAlertDialog.WARNING_TYPE);
        }
        return returnValue;
    }

    @Override
    public boolean isEmptyField(@NonNull EditText etText, @NonNull int message) {
        //return isEmptyField(etText, Utils.getString(message));
        return isEmptyField(etText, "text from id");
    }
}
