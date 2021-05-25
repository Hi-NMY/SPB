package com.example.spb.view.Component;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.view.*;
import androidx.appcompat.app.AlertDialog;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.view.InterComponent.DialogInter;

import java.util.List;

public class ComponentDialog implements DialogInter {

    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    private View view;
    private Window window;
    private Activity activity;
    public InitDialog initDialog;

    public ComponentDialog(Activity context,int viewId,InitDialog initDialog) {
        activity = context;
        builder = new AlertDialog.Builder(context);
        view = LayoutInflater.from(context).inflate(viewId,null);
        initView(view);
        this.initDialog = initDialog;
        initDialog.initView(view);
        initDialog.initData();
        initDialog.initListener();
        alertDialog = builder.create();
        window = alertDialog.getWindow();
        setBackgroundTransparent();
    }

    public ComponentDialog(Activity context,int viewId,int styleId,InitDialog initDialog) {
        activity = context;
        builder = new AlertDialog.Builder(context,styleId);
        view = LayoutInflater.from(context).inflate(viewId,null);
        initView(view);
        this.initDialog = initDialog;
        initDialog.initView(view);
        initDialog.initData();
        initDialog.initListener();
        alertDialog = builder.create();
        window = alertDialog.getWindow();
        setBackgroundTransparent();
    }

    @Override
    public void showMyDialog() {
        alertDialog.show();
    }

    @Override
    public void closeMyDialog() {
        alertDialog.dismiss();
    }

    @Override
    public View getDialogView() {
        return view;
    }

    @Override
    public void initView(View view) {
        builder.setView(view);
    }

    @Override
    public void initData() {
        initDialog.initData();
    }

    @Override
    public void setCancelable(boolean c) {
        alertDialog.setCancelable(c);
    }

    @Override
    public void setBackgroundTransparent() {
        window.setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Override
    public void setDimAmount() {
        window.setDimAmount(0f);
    }

    @Override
    public void setAnimation(int animId) {
        window.setWindowAnimations(animId);
    }

    @Override
    public void setBottomStyle() {
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0,0,0,0);
        WindowManager.LayoutParams layoutParams=window.getAttributes();
        layoutParams.width= WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height=WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }

    @Override
    public void setCenterGravity() {
        window.setGravity(Gravity.CENTER);
    }

    @Override
    public void setBottomGravity() {
        window.setGravity(Gravity.BOTTOM);
    }

    public interface InitDialog{
        void initView(View view);
        void initData();
        void initListener();
    }

    public void changePosition(View v) {
        WindowManager mWindowManager = (WindowManager) MyApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        mWindowManager.getDefaultDisplay().getSize(point);
        int screenHeight = point.y;
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        int[] location = new int[2];
        v.getLocationInWindow(location); //获取在当前窗体内的绝对坐标
        v.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            DisplayCutout displayCutout = activity.getWindow().getDecorView().getRootWindowInsets().getDisplayCutout();
            if (displayCutout != null) {
                int notificationBar  = Resources.getSystem().getDimensionPixelSize(
                        Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android"));
                layoutParams.x = 0; //对 dialog 设置 x 轴坐标
                layoutParams.y = (int) (-(screenHeight / 2) + location[1] - v.getHeight() * 1.1-notificationBar/2); //对dialog设置y轴坐标
            }
        }else {
            layoutParams.y = (int) (-(screenHeight / 2) + location[1] - v.getHeight() * 1.1); //对dialog设置y轴坐标
        }
        window.setAttributes(layoutParams);
    }
}
