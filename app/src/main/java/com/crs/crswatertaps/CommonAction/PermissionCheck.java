package com.crs.crswatertaps.CommonAction;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionCheck {

    public static final int PERMISSION_REQUEST_CODE = 1010;

    public static boolean checkPermission(Context context){

        return PackageManager.PERMISSION_GRANTED== ContextCompat.checkSelfPermission(context,
                        Manifest.permission.INTERNET) &&
                PackageManager.PERMISSION_GRANTED== ContextCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_WIFI_STATE) &&
                PackageManager.PERMISSION_GRANTED== ContextCompat.checkSelfPermission(context,
                        Manifest.permission.ACCESS_NETWORK_STATE);
    }

    public static void requestPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{
                Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.ACCESS_WIFI_STATE}, PERMISSION_REQUEST_CODE);
    }
}
