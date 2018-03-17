package manulife.manulifesop.util;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.SyncStateContract;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    public static void hideSoftKeyboard(View view) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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

    public static Date convertStringToDate(String strDate,String inputFormat) {
        Date convertDate = new Date();
        SimpleDateFormat df = new SimpleDateFormat(inputFormat);
        try {
            convertDate = df.parse(strDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return convertDate;
    }

    public static String convertDateToString(Date date,String outputFormat) {
        String convertString = "";
        SimpleDateFormat df = new SimpleDateFormat(outputFormat);
        try {
            convertString = df.format(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return convertString;
    }

    public static String convertStringDateToStringDate(String inputDate, String inputFormat, String outputFormat) {
        String result = "";
        Date converDate = new Date();
        SimpleDateFormat df = new SimpleDateFormat(inputFormat);
        SimpleDateFormat df2 = new SimpleDateFormat(outputFormat);
        try {
            converDate = df.parse(inputDate);
            result = df2.format(converDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void smoothScrollViewToPosition(final Context context, final NestedScrollView scrollView, final int Yposition){
        ObjectAnimator.ofInt(scrollView, "scrollY",  Yposition).setDuration(1000).start();
    }

    public static int getCurrentMonth(final Context context){
       return (Calendar.getInstance().get(Calendar.MONTH) +1);
    }

    public static int genLastPage(int count, int limit){
        int rs;
        if(count%limit > 0){
            rs = (count/limit) + 1;
        }else{
            rs = count/limit;
        }
        return rs;
    }
}
