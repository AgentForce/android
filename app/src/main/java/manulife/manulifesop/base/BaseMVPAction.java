package manulife.manulifesop.base;

import android.support.annotation.NonNull;
import android.widget.EditText;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by trinm on 12/01/2018.
 */
public interface BaseMVPAction {

    void unSubscribeRequests();

    CompositeDisposable getCompositeDisposable();

    void reconnectNetwork();

    void errorException(Throwable throwable);

    boolean isEmptyField(@NonNull EditText editText, @NonNull String message);

    boolean isEmptyField(@NonNull EditText etText, @NonNull int message);

}
