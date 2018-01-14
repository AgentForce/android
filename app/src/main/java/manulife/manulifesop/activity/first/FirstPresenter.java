package manulife.manulifesop.activity.first;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;

import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.api.ApiService;
import manulife.manulifesop.base.BasePresenter;
import manulife.manulifesop.element.callbackInterface.CallBackInformDialog;
import manulife.manulifesop.util.SOPSharedPreferences;
import manulife.manulifesop.util.Utils;

import static java.lang.Thread.sleep;

/**
 * Created by trinm on 12/01/2018.
 */

public class FirstPresenter extends BasePresenter<FirstContract.View> implements FirstContract.Action {

    public FirstPresenter(FirstContract.View presenterView) {
        super(presenterView);
    }


    @Override
    public void checkInternetViaPingServer() {
        if (Utils.isConnectedToNetwork()) {
            new TestInternet().execute(3000);
        }
    }

    @Override
    public void clickAgreeButton() {
        SOPSharedPreferences.getInstance().saveFirstUsing();
        mPresenterView.showLogin();
    }

    public void checkFirstUserLoading() {
        if(!SOPSharedPreferences.getInstance().isFirstUsing())
        {
            mPresenterView.showWelcome();
        }else
        {
            mPresenterView.showLogin();
        }
    }

    class TestInternet extends AsyncTask<Integer, Void, Boolean> {
        private boolean responded = false;

        @Override
        protected Boolean doInBackground(Integer... params) {
            new Thread() {
                @Override
                public void run() {
                    int TIMEOUT_VALUE = params[0];
                    try {
                        URL testUrl = new URL(ApiService.BASE_URL);

                        URLConnection testConnection = testUrl.openConnection();
                        testConnection.setConnectTimeout(TIMEOUT_VALUE);
                        testConnection.setReadTimeout(TIMEOUT_VALUE);
                        BufferedReader in = new BufferedReader(new InputStreamReader(testConnection.getInputStream()));
                        String inputLine;

                        while ((inputLine = in.readLine()) != null) {
                            responded = true;
                            break;
                        }
                        in.close();
                    } catch (SocketTimeoutException e) {

                    } catch (MalformedURLException e) {
                        e.printStackTrace();

                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                }
            }.start();

            try {
                int timeout = params[0];
                int waited = 0;
                while (!responded && (waited < timeout)) {
                    sleep(100);
                    if (!responded) {
                        waited += 100;
                    }
                }
            } catch (InterruptedException e) {
            } // do nothing
            finally {
                if (!responded) {
                    return false;
                } else {
                    return true;
                }
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (!result) { // code if not connected
                mPresenterView.showInform("Thông báo", "Kiểm tra lại kết nối mạng.", "Thử lại", SweetAlertDialog.WARNING_TYPE, new CallBackInformDialog() {
                    @Override
                    public void DiaglogPositive() {
                        new TestInternet().execute(3000);
                    }
                });
            } else { // code if connected
                checkFirstUserLoading();
            }
        }
    }
}
