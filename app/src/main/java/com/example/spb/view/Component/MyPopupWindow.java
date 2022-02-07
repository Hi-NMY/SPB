package com.example.spb.view.Component;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import com.example.spb.view.InterComponent.MyPopupWindowInter;

public class MyPopupWindow extends PopupWindow implements MyPopupWindowInter {

    private Context mcontext;
    private View mview;

    public MyPopupWindow(Context context) {
        super(context);
        mcontext = context;
    }

    public void init(int viewId, InitWindow initWindow) {
        mview = LayoutInflater.from(mcontext).inflate(viewId, null);
        initWindow.initView(mview);
        initWindow.initData();
        initWindow.initListener();
        this.setContentView(mview);
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));
    }

    public interface InitWindow {
        void initView(View view);

        void initData();

        void initListener();
    }
}
