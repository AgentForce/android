package manulife.manulifesop.activity.Login;

import android.content.Context;
import android.widget.EditText;

import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.util.SOPSharedPreferences;

/**
 * Created by Chick on 10/27/2017.
 */

public class LoginPresent extends BasePresenter<LoginContract.View> implements LoginContract.Action {
    private Context mContest;
    public LoginPresent(LoginContract.View presenterView, Context context) {
        super(presenterView);
        this.mContest = context;
    }

    @Override
    public void loginApp(EditText user, EditText pass) {
        //check null
        if (user.getText().toString().length() < 1)
            mPresenterView.showMessage("Thông báo", "Nhập Email!", SweetAlertDialog.WARNING_TYPE);
        else if (pass.getText().toString().length() < 1)
            mPresenterView.showMessage("Thông báo", "Nhập Password!", SweetAlertDialog.WARNING_TYPE);
        else {
            mPresenterView.showMessage("Thông báo", "Đăng nhập thành công", SweetAlertDialog.SUCCESS_TYPE);
            if (!SOPSharedPreferences.getInstance(mContest).isCreatedPlan()) {
                mPresenterView.showWelcomeUser();
            } else {
                mPresenterView.showMainDashboard();
            }
        }
    }
}
