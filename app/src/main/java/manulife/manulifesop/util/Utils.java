package manulife.manulifesop.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.SyncStateContract;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import manulife.manulifesop.ProjectApplication;


/**
 * Created by trinm on 12/01/2018.
 */
public class Utils {

    private static String SECRETKEY = "abc";

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    public static boolean isConnectedToNetwork() {
        ConnectivityManager cm = (ConnectivityManager) ProjectApplication.getInstance()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static String getSignature(String data)
    {
        String base64 = Base64(data) + SECRETKEY;
        String md5 = MD5(base64);
        return md5.toLowerCase(Locale.getDefault());
    }

    private static String Base64(String data)
    {
        return android.util.Base64.encodeToString(data.getBytes(),android.util.Base64.NO_WRAP);
    }
    private static final String MD5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
