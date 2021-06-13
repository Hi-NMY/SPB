package com.example.spb.view.InterComponent;

import android.view.View;
import androidx.appcompat.app.AlertDialog;

public interface DialogInter {

    public void showMyDialog();

    public void closeMyDialog();

    public AlertDialog returnAlertDialog();

    public View getDialogView();

    public void initView(View view);

    public void initData();

    public void setCancelable(boolean c);

    public void setBackgroundTransparent();

    public void setDimAmount();

    public void setAnimation(int animId);

    public void setBottomStyle();

    public void setCenterGravity();

    public void setBottomGravity();

    public void changePosition(View v);

}
