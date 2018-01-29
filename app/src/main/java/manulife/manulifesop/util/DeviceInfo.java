package manulife.manulifesop.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * Class: 			DEVICE INFO
 * @description: 	get device infomation: sim imei, device imei, android sdk version, model number
 * @author: 		vandn
 * @created on: 	13/08/2013
 * */
public class DeviceInfo {
	public static String DEVICEIMEI;
	public static String SIMIMEI;
	public static int ANDROID_SDK_VERSION;
	public static String ANDROID_OS_VERSION;
	public static String MODEL_NUMBER;
	public static String IP_ADDRESS;
	public static String DEVICE_NAME;
	public static String NETWORK_TYPE;

	private TelephonyManager mTelephonyMgr;

	public DeviceInfo(Context mContext){
		this.mTelephonyMgr = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
		DEVICEIMEI = GetDeviceIMEI();
		SIMIMEI = GetSIMIMEI();
		ANDROID_SDK_VERSION = getDeviceAndroidSDKVersion();
		MODEL_NUMBER = getDeviceModelNumber();
		ANDROID_OS_VERSION = getDeviceAndroidOSVersion();
		DEVICE_NAME = getDeviceName();
		NETWORK_TYPE = getNetworkClass(mContext);
	}

	/**
	 * get net work type
	 * */
	public static String getNetworkClass(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if(info==null || !info.isConnected())
			return "-"; //not connected
		if(info.getType() == ConnectivityManager.TYPE_WIFI)
			return "WIFI";
		if(info.getType() == ConnectivityManager.TYPE_MOBILE){
			int networkType = info.getSubtype();
			switch (networkType) {
				case TelephonyManager.NETWORK_TYPE_GPRS:
				case TelephonyManager.NETWORK_TYPE_EDGE:
				case TelephonyManager.NETWORK_TYPE_CDMA:
				case TelephonyManager.NETWORK_TYPE_1xRTT:
				case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
					return "2G";
				case TelephonyManager.NETWORK_TYPE_UMTS:
				case TelephonyManager.NETWORK_TYPE_EVDO_0:
				case TelephonyManager.NETWORK_TYPE_EVDO_A:
				case TelephonyManager.NETWORK_TYPE_HSDPA:
				case TelephonyManager.NETWORK_TYPE_HSUPA:
				case TelephonyManager.NETWORK_TYPE_HSPA:
				case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
				case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
				case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
					return "3G";
				case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
					return "4G";
				default:
					return "?";
			}
		}
		return "?";
	}

	/**
	 * get device name
	 * */
	public String getDeviceName() {
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		if (model.startsWith(manufacturer)) {
			return capitalize(model);
		} else {
			return capitalize(manufacturer) + " " + model;
		}
	}

	private String capitalize(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		char first = s.charAt(0);
		if (Character.isUpperCase(first)) {
			return s;
		} else {
			return Character.toUpperCase(first) + s.substring(1);
		}
	}

	/**
	 * get sim imei number
	 * */
	public String GetSIMIMEI() {
		try {
			@SuppressLint("MissingPermission") String simIMEI = mTelephonyMgr.getSubscriberId();
			// String imei =
			if (TextUtils.isEmpty(simIMEI)) {
				return "-1";
			} else {
				return simIMEI;
			}
		} catch (Exception e) {
			return "-1";
		}
	}

	/**
	 * get device imei number
	 * */
	public String GetDeviceIMEI() {
		try {
			@SuppressLint("MissingPermission") String deviceImei = mTelephonyMgr.getDeviceId(); //*** use for mobiles
			
			if(deviceImei == null)
				deviceImei = Build.SERIAL; //*** use for tablets
			
			if (TextUtils.isEmpty(deviceImei)) {
				return "-1";
			} else {
				return deviceImei;
			}
		} catch (Exception e) {
			return "-1";
		}
	}
	
	/**
	 * get device's android sdk version
	 * */
	public int getDeviceAndroidSDKVersion() {
		int androidVer = -1;
		try{
			androidVer = Build.VERSION.SDK_INT;
		}
		catch (Exception e){
			androidVer = -1;
		}
		return androidVer;
	}
	/**
	 * get device's model number
	 * */
	public String getDeviceModelNumber(){
		String modelNum = "-1";
		try{
			modelNum = Build.MODEL;
		}
		catch(Exception e){
			modelNum = "-1";
		}
		return modelNum;
	}
	
	/**
	 * get device's android os version
	 * added by vandn, on 14/08/2013
	 * */
	public String getDeviceAndroidOSVersion() {
		String androidVer = "-1";
		try{
			androidVer = Build.VERSION.RELEASE;
		}
		catch (Exception e){
			androidVer = "-1";
		}
		return androidVer;
	}

}
