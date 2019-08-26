package com.haijie.utilslibrary.permission.soulpermission;

import com.haijie.utilslibrary.permission.listener.OnPermissionCallbackListener;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.bean.Permissions;
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener;

public class SoulPermissionHelper {

    private static SoulPermissionHelper mInstance;
    private OnPermissionCallbackListener mCallbackListener;

    private SoulPermissionHelper() {

    }

    /**
     * 获取实例
     *
     * @return 实例对象
     */
    public static SoulPermissionHelper getInstance() {
        if (null == mInstance) {
            synchronized (SoulPermissionHelper.class) {
                if (null == mInstance) {
                    mInstance = new SoulPermissionHelper();
                }
            }
        }
        return mInstance;
    }

    /**
     * 设置回调接口
     *
     * @param callbackListener 回调接口
     * @return 实例对象
     */
    public SoulPermissionHelper setPermissionCallbackListener(OnPermissionCallbackListener callbackListener) {
        mCallbackListener = callbackListener;
        return mInstance;
    }

    /**
     * 一次性请求多条权限
     *
     * @param permissions 权限组
     */
    public void checkAndRequestPermission(String... permissions) {
        SoulPermission.getInstance().checkAndRequestPermissions(Permissions.build(permissions),
                new CheckRequestPermissionsListener() {
                    @Override
                    public void onAllPermissionOk(Permission[] allPermissions) {
                        mCallbackListener.onAllPermissionOk();
                    }

                    @Override
                    public void onPermissionDenied(Permission[] refusedPermissions) {
                        if (refusedPermissions.length > 0 && refusedPermissions[0].shouldRationale()) {
                            mCallbackListener.onPermissionDenied();
                        }
                    }
                });
    }

    /**
     * 跳转到设置页
     */
    public void goPermissionSettings() {
        SoulPermission.getInstance().goPermissionSettings();
    }
}
