package manulife.manulifesop.activity.FAGroup.createPlan;

import android.content.Context;
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

public class CreatePlanPresenter extends BasePresenter<CreatePlanContract.View> implements CreatePlanContract.Action {

    private Context mContext;

    public CreatePlanPresenter(CreatePlanContract.View presenterView, Context context) {
        super(presenterView);
        this.mContext = context;
    }

}
