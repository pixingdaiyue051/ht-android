package com.tequeno.rdm;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PermissionUtil {

    private final static String TAG = "PermissionUtil";

    public static boolean checkPermission(Activity activity, PermissionEnum permissionEnum) {
        boolean granted = checkPermission1(activity, permissionEnum);
        if (!granted) {
            ActivityCompat.requestPermissions(activity, new String[]{permissionEnum.getPermission()}, permissionEnum.getCode());
        }
        return granted;
    }

    public static boolean checkPermission(Activity activity, List<PermissionEnum> permissionEnumList) {
        List<String> list = permissionEnumList.stream().filter(p -> !checkPermission1(activity, p))
                .map(PermissionEnum::getPermission)
                .collect(Collectors.toList());
        if (list.isEmpty()) {
            return true;
        }
        String[] permissionArray = new String[list.size()];
        Arrays.setAll(permissionArray, list::get);
        ActivityCompat.requestPermissions(activity, permissionArray, PermissionEnum.COMBINE.getCode());
        return false;
    }

    private static boolean checkPermission1(Activity activity, PermissionEnum permissionEnum) {
        int granted = ContextCompat.checkSelfPermission(activity, permissionEnum.getPermission());
        Log.d(TAG, "checkPermission: " + granted);
        return PackageManager.PERMISSION_GRANTED == granted;
    }

    public static boolean checkGrant() {
        return true;
    }

}