package com.titan.cssl;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.provider.Settings.Secure;


import com.tencent.bugly.crashreport.CrashReport;
import com.titan.cssl.util.BussUtil;
import com.titan.cssl.util.ConnectionChangeReceiver;
import com.titan.cssl.util.NetUtil;
import com.titan.cssl.util.ScreenTool;
import com.titan.cssl.util.ToastUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MyApplication extends Application{

	public static MyApplication mApplication;
	//用户存储
	public static SharedPreferences sharedPreferences;

	public static ScreenTool.Screen screen;

	/** 移动设备唯一号 */
	public static String macAddress;
	/** 移动设备序列号 */
	public static String mobileXlh;
	/** 移动设备型号 */
	public static String mobileType;
	/** 注册网络 */
	private ConnectionChangeReceiver mNetworkStateReceiver;

	private static MyApplication instance = null;

	public static MyApplication getInstance(){
		if (instance==null){
			instance = new MyApplication();
		}
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		/** Bugly SDK初始化
		 * 参数1：上下文对象
		 * 参数2：APPID，平台注册时得到,注意替换成你的appId
		 * 参数3：是否开启调试模式，调试模式下会输出'CrashReport'tag的日志
		 * 发布新版本时需要修改以及bugly isbug需要改成false等部分
		 * 腾讯bugly 在android 4.4版本上有bug 启动报错
		 */
		CrashReport.initCrashReport(getApplicationContext(), "c979af8785", true);
		instance = this;
		/** 注册网络监听 */
		//initNetworkReceiver();

		mApplication = this;
		sharedPreferences = getSharedPreferences("userinfo", MODE_PRIVATE);

		/** 获取设备信息 */
		getMbInfo();
	}

	/*@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(base);
		//MultiDex.install(this);
	}*/

	/** 获取设备信息 */
	public void getMbInfo() {
		/* 获取mac地址 作为设备唯一号 */
		String mac = BussUtil.getWifiMacAddress(this);
		if (mac != null && !mac.equals("")) {
			macAddress = mac;
			sharedPreferences.edit().putString("mac", macAddress).apply();
		}
		mobileXlh = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);
		mobileType = android.os.Build.MANUFACTURER + "——"
				+ android.os.Build.MODEL;// SM-P601 型号
		// android.os.Build.MANUFACTURER;// samsung 厂商
		/* 获取屏幕分辨率 */
		screen = ScreenTool.getScreenPix(this);
	}


	/**检查网络连接是否正常*/
    public boolean netWorkTip(){
        if (NetUtil.getNetworkState(instance) == NetUtil.NETWORN_NONE) {
            //Toast.makeText(instance,"网络未连接",Toast.LENGTH_SHORT).show();
			ToastUtil.setToast(instance,"网络未连接");
            return false;
        } else {
            return true;
        }
    }

    /**检查网络连接是否正常*/
    public boolean hasNetWork(){
        if (NetUtil.getNetworkState(instance) == NetUtil.NETWORN_NONE) {
            return false;
        } else {
            return true;
        }
    }
}
