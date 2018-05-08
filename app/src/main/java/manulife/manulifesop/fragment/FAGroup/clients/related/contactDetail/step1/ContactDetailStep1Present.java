package manulife.manulifesop.fragment.FAGroup.clients.related.contactDetail.step1;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import manulife.manulifesop.ProjectApplication;
import manulife.manulifesop.base.BasePresenter;

/**
 * Created by Chick on 10/27/2017.
 */

public class ContactDetailStep1Present extends BasePresenter<ContactDetailStep1Contract.View> implements ContactDetailStep1Contract.Action {

    private Context mContext;

    public ContactDetailStep1Present(ContactDetailStep1Contract.View presenterView, Context context) {
        super(presenterView);
        mContext = context;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void callContact(String phone) {
        String phoneStr = "tel:" + phone;
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse(phoneStr));
        mContext.startActivity(callIntent);
    }
}
