package com.haijie.haijielibrary;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;

import com.haijie.utilslibrary.internet.common.InternetUtils;
import com.haijie.utilslibrary.permission.listener.OnPermissionCallbackListener;
import com.haijie.utilslibrary.permission.soulpermission.SoulPermissionHelper;

class UtilsLibraryTest {

    @SuppressLint("StaticFieldLeak")
    private static UtilsLibraryTest mInstance;

    private UtilsLibraryTest() {

    }

    static UtilsLibraryTest getInstance() {
        if (null == mInstance) {
            synchronized (UtilsLibraryTest.class) {
                if (null == mInstance) {
                    mInstance = new UtilsLibraryTest();
                }
            }
        }
        return mInstance;
    }

    boolean InternetUtilsIsNetworkAvailableTest(Context context) {
        return InternetUtils.isNetworkAvailable(context);
    }

    void SoulPermissionCheckAndRequestPermissionTest() {
        SoulPermissionHelper.getInstance().setPermissionCallbackListener(new OnPermissionCallbackListener() {
            @Override
            public void onAllPermissionOk() {

            }

            @Override
            public void onPermissionDenied() {

            }
        }).checkAndRequestPermission(
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    void SoulPermissionGoPermissionSettingsTest() {
        SoulPermissionHelper.getInstance().goPermissionSettings();
    }
}
