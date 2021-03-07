package com.example.spb.view.littlefun;

import android.content.Context;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.List;

public class RequestForAccess {

    public static final class Group {
        public static final String[] All = new String[]{"android.permission.ACCESS_COARSE_LOCATION"};

        public Group() {
        }
    }

    public static void setNewAccess(Context context,OnReturn onReturn){
        XXPermissions.with(context)
                .permission(Group.All)
                .request(new OnPermissionCallback() {
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
                });
    }

    public interface OnReturn{
        void allTrue();
        void someTrue();
        void allFalse();
        void toTure();
    }
}
