package com.example.spb.presenter.impl;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Bar;
import com.example.spb.presenter.inter.INewPostPageFPresenter;
import com.example.spb.presenter.otherimpl.DataPostBarPresenter;
import com.example.spb.view.activity.HomePage;
import com.example.spb.view.inter.INewPostPageFView;

import java.util.List;

public class NewPostPageFPresenterImpl extends BasePresenter<INewPostPageFView> implements INewPostPageFPresenter {

    private Activity activity;
    private OnReturn onReturn;

    private Handler obtainNewBarhandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onReturn.onReturn();
                }
            });
        }
    };

    public NewPostPageFPresenterImpl(Activity activity) {
        this.activity = activity;
    }

    public void obtainNewBar(HomePage homePage,OnReturn onReturn) {
        this.onReturn = onReturn;
        homePage.getDataPostBarPresenter().obtainNewBar(obtainNewBarhandler);
    }

    public interface OnReturn{
        void onReturn();
    }
}
