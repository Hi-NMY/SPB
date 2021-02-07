package com.example.spb.view.Component;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import androidx.appcompat.app.AlertDialog;
import com.example.spb.R;
import com.example.spb.view.InterComponent.DialogInter;

public class ComponentDialog implements DialogInter {

    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    private View view;
    private Window window;

    public ComponentDialog(Activity context,int viewId,InitDialog initDialog) {
        builder = new AlertDialog.Builder(context);
        view = LayoutInflater.from(context).inflate(viewId,null);
        initView(view);
        alertDialog = builder.create();
        window = alertDialog.getWindow();
        setBackgroundTransparent();
        initDialog.initView(view);
        initDialog.initData();
        initDialog.initListener();
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

    }

    @Override
    public void initData() {

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
}
