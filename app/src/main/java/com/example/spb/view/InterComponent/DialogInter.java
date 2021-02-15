package com.example.spb.view.InterComponent;

import android.view.View;

public interface DialogInter {

    public void showMyDialog();

    public void closeMyDialog();

    public View getDialogView();

    public void initView(View view);

    public void initData();

    public void setCancelable(boolean c);

    public void setBackgroundTransparent();

    public void setDimAmount();

    public void setCenterGravity();

    public void setBottomGravity();

    public void changePosition(View v);

}
