package com.example.spb.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.UserRegisteredPageAPresenterImpl;
import com.example.spb.presenter.inter.IUserRegisteredPageAPresenter;
import com.example.spb.view.Component.FragmentSpbAvtivityBar;
import com.example.spb.view.InterComponent.ISpbAvtivityBarFView;
import com.example.spb.view.InterTotal.SpbInterDialog;
import com.example.spb.view.InterTotal.SpbInterInitView;
import com.example.spb.view.inter.IUserRegisteredPageAView;
import com.example.spb.view.littlefun.JumpIntent;
import com.gyf.immersionbar.ImmersionBar;

public class UserRegisteredPage extends BaseMVPActivity<IUserRegisteredPageAView,UserRegisteredPageAPresenterImpl> implements SpbInterDialog, SpbInterInitView,IUserRegisteredPageAView {

    private ISpbAvtivityBarFView bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registered_page);
        initActView();
    }

    @Override
    protected UserRegisteredPageAPresenterImpl createPresenter() {
        return new UserRegisteredPageAPresenterImpl(this);
    }

    @Override
    protected void initActView() {
        createDialog();
        setBar();
        setActivityBar();
        setMyListener();
        initData();
    }

    @Override
    protected void initData() {

    }

    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {

    }

    @Override
    public void createDialog() {

    }

    @Override
    public void showDialogS(int i) {

    }

    @Override
    public void closeDialog(int i) {

    }

    @Override
    public void setMyListener() {

    }

    @Override
    public void setBar() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.beijing)
                .init();
    }

    @Override
    public void setActivityBar() {
        bar = setMyActivityBar(R.id.user_egisterd_actbar);
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                JumpIntent.finishAct(UserRegisteredPage.this);
//                JumpIntent.startSetResultIntent(UserRegisteredPage.this, 1, new JumpIntent.SetMsg() {
//                    @Override
//                    public void setMessage(Intent intent) {
//
//                    }
//                });
            }
        });
    }
}
