package manulife.manulifesop.activity.first;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

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

    private Context mContext;
    public FirstPresenter(FirstContract.View presenterView,Context context) {
        super(presenterView);
        this.mContext = context;
    }


    @Override
    public void checkInternetViaPingServer() {
        if (Utils.isConnectedToNetwork()) {
            new TestInternet().execute(5000);
            //checkFirstUserLoading();
        }/*else{
            mPresenterView.showInform("Thông báo", "Kiểm tra lại kết nối mạng.", "Thử lại", SweetAlertDialog.WARNING_TYPE, new CallBackInformDialog() {
                @Override
                public void DiaglogPositive() {

                }
            });
        }*/
    }

    @Override
    public void clickAgreeButton() {
        SOPSharedPreferences.getInstance(mContext).saveFirstUsing();
        mPresenterView.showLogin();
    }

    public void checkFirstUserLoading() {
        if(!SOPSharedPreferences.getInstance(mContext).isFirstUsing())
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
                        //URL testUrl = new URL(ApiService.BASE_URL);
                        URL testUrl = new URL("https://vnexpress.net");

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
                        new TestInternet().execute(5000);
                    }
                });
            } else { // code if connected
                checkFirstUserLoading();
            }
        }
    }

    @Override
    public void checkPermissionGranted() {
        if (isPermissionGranted()) {
            clickAgreeButton();
        }
    }

    public boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            ArrayList<String> temp = new ArrayList<>();
            //READ_PHONE_STATE
            if (mContext.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                temp.add(Manifest.permission.READ_PHONE_STATE);
            }
            //READ_CONTACTS
            if (mContext.checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                temp.add(Manifest.permission.READ_CONTACTS);
            }
            //CALL_PHONE
            if (mContext.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                temp.add(Manifest.permission.CALL_PHONE);
            }

            if (temp.size() > 0) {
                String permissions[] = new String[temp.size()];
                for (int i = 0; i < temp.size(); i++) {
                    permissions[i] = temp.get(i);
                }
                ActivityCompat.requestPermissions((Activity) mContext, permissions, 2);
                return false;
            }
        }
        return true;
    }
}
