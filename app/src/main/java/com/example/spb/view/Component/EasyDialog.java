package com.example.spb.view.Component;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import androidx.appcompat.app.AlertDialog;
import com.example.spb.R;
import com.example.spb.view.InterComponent.DialogInter;
import pl.droidsonroids.gif.GifImageView;

public class EasyDialog implements DialogInter {

    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    private int mloadingId;
    private Window window;

    public EasyDialog(Activity context,int loadingId) {
        builder = new AlertDialog.Builder(context);
        mloadingId = loadingId;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading,null);
        initView(view);
        alertDialog = builder.create();
        window = alertDialog.getWindow();
        setBackgroundTransparent();
        setDimAmount();
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
    public AlertDialog returnAlertDialog() {
        return alertDialog;
    }

    @Override
    public View getDialogView() {
        return null;
    }

    @Override
    public void initView(View view) {
        GifImageView gifImageView = view.findViewById(R.id.loading_gif);
        gifImageView.setImageResource(mloadingId);
        builder.setView(view);
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
    public void setAnimation(int animId) {

    }

    @Override
    public void setBottomStyle() {

    }

    @Override
    public void setNoBg() {

    }

    @Override
    public void setCenterGravity() {
        window.setGravity(Gravity.CENTER);
    }

    @Override
    public void setBottomGravity() {
        window.setGravity(Gravity.BOTTOM);
    }

    @Override
    public void changePosition(View v) {

    }
}
