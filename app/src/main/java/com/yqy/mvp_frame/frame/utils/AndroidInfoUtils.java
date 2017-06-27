/**
 *COPYRIGHT NOTICE
 *Copyright (c) 2012～2020, 19e
 *All rights reserved.
 * @author wjf
 * @file AndroidInfoUtils.java
 * @brief TODO
 * @version
 * @date 2012-12-21 下午2:14:24
 *
 */
package com.yqy.mvp_frame.frame.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Parcelable;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.yqy.mvp_frame.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 *COPYRIGHT NOTICE
 *Copyright (c) 2012～2020, 19e
 *All rights reserved.
 * @author wjf
 * @file AndroidInfoUtils.java
 * @brief TODO
 * @version
 * @date 2012-12-21 下午2:14:24
 *
 */
public class AndroidInfoUtils {
	public static final int NETWORKWIFI = 2; //手机
	public static final int NETWORKMOBILE = 1; //wifi
	public static final int NETWORKNULL = 0; //
	public static final int NETWORKNO = -1; //无网络链接

	/**
	 * 判断当前网络类型
	 * @return 1:手机  2:wifi  0:无网络  -1:无网络连接
     */
	public static int checkNetworkConnection(Context mContext) {
		// BEGIN_INCLUDE(connect)
		ConnectivityManager connMgr =
				(ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();
		if (activeInfo != null && activeInfo.isConnected()) {
			boolean wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
			boolean mobileConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
			if (wifiConnected) {
				return NETWORKWIFI;
			} else if (mobileConnected) {
				return NETWORKMOBILE;
			}
		} else {
			return NETWORKNULL;
		}
		return NETWORKNO;
	}

	/**
	 * 获取手机IMEI code
	 *
	 * @param context
	 * @return
	 */
	public static String getImeiCode(Context context) {
		final TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		final String res = tm.getDeviceId();
		if (res == null || "".equals(res)) {
			return "";
		}
		return res;
	}

	/**
	 * 获取手机IMSI code
	 *
	 * @param context
	 *            Context
	 * @return IMSI String
	 */
	public static String getImsiCode(Context context) {
		final TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		final String res = tm.getSubscriberId();
		if (res == null || "".equals(res)) {
			return "";
		}
		return res;
	}
	/**
	 *
	 * @author wjf
	 * @brief TODO
	 * @version
	 * @date 2012-12-23
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static String getTerminalCode(Context context){
		String terminal = "";
		try {
			terminal = MD5.getMd5(getImeiCode(context)
					+ getImsiCode(context)
					+getAndroidId(context));
			if(ModelUtils.hasLength(terminal)){
				terminal = terminal.toUpperCase();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!ModelUtils.hasLength(terminal))return terminal;
		return terminal.substring(terminal.length() - 10, terminal.length());
	}
	public static String getTerminalCodeCommon(Context context){
		String terminal = "";
		try {
			terminal = MD5.getMd5(getImeiCode(context)
					+ getImsiCode(context)
					+getAndroidId(context));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!ModelUtils.hasLength(terminal))return terminal;
		return terminal.substring(terminal.length() - 10, terminal.length());
	}
	/**
	 *
	 * @author wjf
	 * @brief 系统版本
	 * @version
	 * @date 2012-12-23
	 * @return
	 */
	public static String getOs() {

		String os = Build.VERSION.RELEASE;

		return os;
	}
	/**
	 *
	 * @author wjf
	 * @brief 获取手机MacAddress
	 * @version
	 * @date 2012-12-19
	 * @param context
	 * @return
	 */
	public static String getLocalMacAddress(Context context) {
		final WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		final WifiInfo info = wifiManager.getConnectionInfo();
		String res = "";
		if(info != null )res = info.getMacAddress();
		if (res == null || "".equals(res)) {
			return "00:00:00:00:00:00";
		}
		return res;
	}
	/**
	 *
	 * @author wjf
	 * @brief 获取手机android id
	 * @version
	 * @date 2012-12-19
	 * @param context
	 * @return
	 */
	public static String getAndroidId(Context context) {
		String androidId = Secure.getString(context.getContentResolver(),
				Secure.ANDROID_ID);
		return androidId;
	}

	/**
	 * 判断是否连接到互联网
	 *
	 * @param context
	 * @return
	 */
	public static boolean isNetConnected(Context context) {
		try {
			ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo network = connectivityManager.getActiveNetworkInfo();
			if (connectivityManager != null) {
				if (network != null && network.isConnected()) {
					if (network.getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 *
	 * @author wjf
	 * @brief 当前应用的versionName
	 * @version
	 * @date 2012-12-23
	 * @param context
	 * @return
	 * @throws Exception
	 */
	public static String getVersionName(Context context) {
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		String version = "";

		try {
			PackageInfo packInfo;
			packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
			version = packInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}

	/**
	 * 获取手机的尺寸.
	 *
	 * @param context 容器对象
	 * @return 手机的尺寸
	 */
	public static String getDisplay(Context context) {

		final DisplayMetrics metric = new DisplayMetrics();
		final WindowManager manager = (WindowManager) context
				.getSystemService("window");
		manager.getDefaultDisplay().getMetrics(metric);
		final int width = metric.widthPixels;
		final int height = metric.heightPixels;
		final int densityDpi = metric.densityDpi;
		// 对角线的长度
		final Double diagonal = Math.sqrt(width * width + height * height);
		final Double d = diagonal / densityDpi;
		String c = Double.toString(d);
		if (c != null && c.indexOf(".") != -1) {
			c = c.substring(0, c.indexOf(".") + 2);
		}

		return c;
	}

	/**
	 *
	 * @author wjf
	 * @brief TODO
	 * @version
	 * @date 2012-12-23
	 * @param context
	 * @return
	 */
	public static String getResolution(Context context){
		final DisplayMetrics metric = new DisplayMetrics();
		final WindowManager manager = (WindowManager) context
				.getSystemService("window");
		manager.getDefaultDisplay().getMetrics(metric);
		final int width = metric.widthPixels;
		final int height = metric.heightPixels;
		return width+"X"+height;
	}

	/**
	 * 获取本机号码.
	 * @return String
	 */
	public static String getTelephoneNum(Context context) {
		final TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		final String res = tm.getLine1Number();
		if (res == null || "".equals(res)) {
			return "";
		}
		return res;
	}

	/**
	 * 获取手机型号
	 *
	 * @return String
	 */
	public static String getModel() {

		String model = Build.MODEL;
		return model;
	}

	/**
	 *
	 * @author wjf
	 * @brief 创建快捷方式
	 * @version
	 * @date 2013-1-28
	 * @param cx
	 */
	public static void addShortcut(Context cx) {
		// 创建快捷方式的Intent
		Intent shortcutintent = new Intent(
				"com.android.launcher.action.INSTALL_SHORTCUT");
		// 不允许重复创建
		shortcutintent.putExtra("duplicate", false);
		// 需要现实的名称
		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME,
				cx.getString(R.string.app_name));
		// 快捷图片
		Parcelable icon = Intent.ShortcutIconResource.fromContext(
				cx.getApplicationContext(), R.mipmap.ic_launcher);
		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
		// 点击快捷图片，运行的程序主入口
//		shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(
//				cx.getApplicationContext(), SplashActivity.class));
		// 发送广播。OK
		cx.sendBroadcast(shortcutintent);
	}

	/**
	 *
	 * @author wjf
	 * @brief 判断应用是否debug
	 * @version
	 * @date 2013-11-22
	 * @param context
	 * @return
	 */
	public static boolean isApkDebugable(Context context) {
		try {
			ApplicationInfo info= context.getApplicationInfo();
			return (info.flags& ApplicationInfo.FLAG_DEBUGGABLE)!=0;
		} catch (Exception e) {

		}
		return false;
	}
	/**
	 * 获得android sdk版本号
	 * @author zhuangjingye
	 * @brief TODO
	 * @version
	 * @date 2013-12-10
	 * @return
	 */
	public static int getAndroidSDKVersion() {
		int version = 0;
		try{
			version = Integer.valueOf(Build.VERSION.SDK);
		}catch(Exception e){

		}
		return version;
	}

	/**
	 *
	 * SHA1加密签名
	 * @param s
	 * */
	public static String SHA1(String s) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();
			return toHexString(messageDigest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
	public static String toHexString(byte[] keyData) {
		if (keyData == null) {
			return null;
		}
		int expectedStringLen = keyData.length * 2;
		StringBuilder sb = new StringBuilder(expectedStringLen);
		for (int i = 0; i < keyData.length; i++) {
			String hexStr = Integer.toString(keyData[i] & 0x00FF,16);
			if (hexStr.length() == 1) {
				hexStr = "0" + hexStr;
			}
			sb.append(hexStr);
		}
		return sb.toString();
	}

}
