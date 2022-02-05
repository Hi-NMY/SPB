package com.example.spb.view.Component;

import android.os.Handler;
import android.os.Looper;
import com.example.spb.app.MyApplication;
import com.example.spb.common.RequestCode;
import com.example.spb.presenter.utils.DataVerificationTool;

/**
 * @author nmy
 * @title: ResponseToast
 * @date 2022-01-31 10:14
 */
public class ResponseToast {

    public static boolean toToast(RequestCode requestCode){
        if (!DataVerificationTool.isEmpty(requestCode.getMessgae())){
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    MyToastClass.ShowToast(MyApplication.getContext(),requestCode.getMessgae());
                }
            });
        }
        return requestCode.getCode() != RequestCode.ERROR;
    }
}
