package com.titan.cssl.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by hanyw on 2017/10/30/030.
 *
 */

public class BussUtil {
    /** 获取唯一识表示 mac地址 */
    public static String getWifiMacAddress(Context context) {
        String macAddress = "";
        WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (wifiMgr.isWifiEnabled()) {
            // 如果wifi打开
            // wifiMgr.setWifiEnabled(false);
        } else {
            // 如果wifi关闭
            wifiMgr.setWifiEnabled(true);
        }
        WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
        if (null != info) {
            // 如果wifi关闭的情况下 可能获取不到
            macAddress = info.getMacAddress();
        }
        return macAddress;
    }

}
