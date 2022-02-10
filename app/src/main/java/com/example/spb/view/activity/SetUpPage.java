package com.example.spb.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.SetUpPageAPresenterImpl;
import com.example.spb.view.Component.AppVersion;
import com.example.spb.view.fragment.FragmentSpbAvtivityBar;
import com.example.spb.view.inter.ISetUpPageAView;
import com.example.spb.view.utils.JumpIntent;
import com.example.spb.xserver.ObtainVersion;
import com.gyf.immersionbar.ImmersionBar;

public class SetUpPage extends BaseMVPActivity<ISetUpPageAView, SetUpPageAPresenterImpl> implements ISetUpPageAView, View.OnClickListener {

    private FragmentSpbAvtivityBar bar;
    private RelativeLayout mR1;
    private RelativeLayout mR2;
    private RelativeLayout mR3;
    private RelativeLayout mR4;
    private RelativeLayout mR5;
    private RelativeLayout mQuitLogin;
    private TextView mVersionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_page);
        initActView();
    }

    @Override
    protected SetUpPageAPresenterImpl createPresenter() {
        return new SetUpPageAPresenterImpl();
    }

    @Override
    protected void initActView() {
        mR1 = (RelativeLayout) findViewById(R.id.r1);
        mR2 = (RelativeLayout) findViewById(R.id.r2);
        mR3 = (RelativeLayout) findViewById(R.id.r3);
        mR4 = (RelativeLayout) findViewById(R.id.r4);
        mR5 = (RelativeLayout) findViewById(R.id.r5);
        mQuitLogin = (RelativeLayout) findViewById(R.id.quit_login);
        mVersionText = (TextView) findViewById(R.id.version_text);
        initData();
        setActivityBar();
        setBar();
        setMyListener();
        createDialog();
    }

    @Override
    protected void initData() {
        mVersionText.setText(ObtainVersion.versionName(this));
        mVersionText.postInvalidate();
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
        mR1.setOnClickListener(this);
        mR2.setOnClickListener(this);
        mR3.setOnClickListener(this);
        mR4.setOnClickListener(this);
        mR5.setOnClickListener(this);
        mQuitLogin.setOnClickListener(this);
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
        bar = setMyActivityBar(R.id.setup_actbar);
        bar.barCentralTxt(TITLE, null);
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.r1:
                JumpIntent.startMyIntent(SetUpUpdatePasswordPage.class);
                break;
            case R.id.r2:
                JumpIntent.startMyIntent(SetUpMessagePage.class);
                break;
            case R.id.r3:
                JumpIntent.startMyIntent(SetUpPrivacyPage.class);
                break;
            case R.id.r4:
                JumpIntent.startMyIntent(SetUpAssistPage.class);
                break;
            case R.id.r5:
                AppVersion appVersion = new AppVersion(MyApplication.getContext(),this);
                appVersion.startVersion(String.valueOf(ObtainVersion.versionCode(this)),true);
                break;
            case R.id.quit_login:
                mPresenter.loginOut(getDataUserMsgPresenter().getUser_account(), new SetUpPageAPresenterImpl.OnReturn() {
                    @Override
                    public void onReturn() {
                        JumpIntent.startNewIntent(EnterPage.class);
                    }
                });
                break;
        }
    }
}
