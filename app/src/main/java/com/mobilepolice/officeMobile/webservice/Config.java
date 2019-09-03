package com.mobilepolice.officeMobile.webservice;


import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;

import com.mobilepolice.officeMobile.R;


public class Config {
    public static final String SERVICE_URL = "http://www.freetk.cn:10006/ws/bussService?wsdl";
    public static final String SERVICE_NAME_SPACE = "http://impl.service.jit.com/";

    /**
     * get version number
     *
     * @param context
     * @return
     */
    public static int getVerCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            // TODO: handle exception
            //Log.e(TAG, e.getMessage());
        }
        return verCode;
    }

    /**
     * get version number
     *
     * @param context
     * @return
     */
    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            // TODO: handle exception
            //Log.e(TAG, e.getMessage());
        }
        return verName;
    }

    /**
     * get apkname
     *
     * @param context
     * @return
     */
    public static String getAppName(Context context) {
        String verName = context.getResources().getText(R.string.app_name).toString();
        return verName;
    }
}