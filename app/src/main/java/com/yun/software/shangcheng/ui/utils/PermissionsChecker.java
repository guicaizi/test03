package com.yun.software.shangcheng.ui.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * 检查权限的工具类
 * <p/>
 * Created by wangchenlong on 16/1/26.
 */
public class PermissionsChecker {
    private final Context mContext;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    public PermissionsChecker(Context context) {
        mContext = context.getApplicationContext();
    }

    // 判断权限集合
    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
//            Log.i("permission","permission"+permission);
            if (lacksPermission(permission)) {
                Log.i("permission","permission"+permission);
//                String[] permission2=new String[]{permission};
//
//                ActivityCompat.requestPermissions((MainActivity)mContext, permission2,
//                        REQUEST_EXTERNAL_STORAGE);
                return true;
            }
        }
        return false;
    }

    // 判断是否缺少权限
    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) ==
                PackageManager.PERMISSION_DENIED;
    }
}
