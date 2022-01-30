package com.example.spb.presenter.utils;

import android.app.Activity;
import android.os.Build;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.XXPermissions;

import java.util.List;

public class RequestForAccess {

    public static final class Group {
        public static final String[] All = new String[]{"android.permission.WRITE_EXTERNAL_STORAGE","android.permission.ACCESS_COARSE_LOCATION"};

        public Group() {
        }
    }

    public static final class CameraGroup {
        public static final String[] All = new String[]{"android.permission.CAMERA","android.permission.READ_EXTERNAL_STORAGE"};

        public CameraGroup() {
        }
    }

    public static final class VoiceCameraGroup {
        public static final String[] All = new String[]{"android.permission.RECORD_AUDIO","android.permission.CAMERA","android.permission.ACCESS_FINE_LOCATION"};

        public VoiceCameraGroup() {
        }
    }

    public static void setNewAccess(Activity context,OnReturn onReturn){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            XXPermissions.with(context)
                    .permission(Group.All)
                    .request(setCallBack(context, onReturn));
        }else {
            onReturn.low();
        }
    }

    public static void setCameraAccess(Activity context,OnReturn onReturn){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            XXPermissions.with(context)
                    .permission(CameraGroup.All)
                    .request(setCallBack(context, onReturn));
        }else {
            onReturn.low();
        }
    }

    public static void setSendNewBarAccess(Activity context,OnReturn onReturn){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            XXPermissions.with(context)
                    .permission(VoiceCameraGroup.All)
                    .request(setCallBack(context, onReturn));
        }else {
            onReturn.low();
        }
    }

    private static OnPermissionCallback setCallBack(Activity context, OnReturn onReturn){
        return new OnPermissionCallback() {
            @Override
            public void onGranted(List<String> permissions, boolean all) {
                if (all) {
                    onReturn.allTrue();
                } else {
                    onReturn.someTrue();
                }
            }

            @Override
            public void onDenied(List<String> permissions, boolean never) {
                if (never) {
                    onReturn.toTure();
                    // 如果是被永久拒绝就跳转到应用权限系统设置页面
                    XXPermissions.startPermissionActivity(context, permissions);
                } else {
                    onReturn.allFalse();
                }
            }
        };
    }

    public interface OnReturn{
        void allTrue();
        void someTrue();
        void allFalse();
        void toTure();
        void low();
    }
}
