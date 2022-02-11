package com.example.spb.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.EnterPageAPresenterImpl;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.view.inter.IEnterPageAView;
import com.example.spb.view.utils.GlideRoundTransform;
import com.example.spb.view.utils.JumpIntent;
import com.gyf.immersionbar.ImmersionBar;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;
import org.litepal.LitePal;

public class EnterPage extends BaseMVPActivity<IEnterPageAView, EnterPageAPresenterImpl> implements IEnterPageAView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enterpage_main);
        LitePal.getDatabase();
        initActView();
    }

    @Override
    protected EnterPageAPresenterImpl createPresenter() {
        return new EnterPageAPresenterImpl();
    }

    @Override
    protected void initActView() {
        ImmersionBar.with(this).init();
        ImageView mLogoimage = findViewById(R.id.logoimage);
        Glide.with(this)
                .load(R.drawable.mylogo)
                .transform(new GlideRoundTransform(5))
                .into(mLogoimage);
        initData();
    }

    @Override
    protected void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    if (mPresenter.getFirstLogIn()) {
                        Intent intent = new Intent(MyApplication.getContext(), FirstPage.class);
                        startActivity(intent);
                    } else {
                        mPresenter.setUserIp();
                        mPresenter.initDate(EnterPage.this, new EnterPageAPresenterImpl.Jump() {
                            @Override
                            public void toJump() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                RongIM.connect(getDataUserMsgPresenter().getUser_token(), new RongIMClient.ConnectCallback() {
                                    @Override
                                    public void onSuccess(String s) {
                                        UserInfo userInfo = new UserInfo(getDataUserMsgPresenter().getUser_account()
                                                , getDataUserMsgPresenter().getUser_name()
                                                , Uri.parse(InValues.send(R.string.prefix_img) + getDataUserMsgPresenter().getUser_account() + InValues.send(R.string.suffix_head_img)));
                                        RongIM.getInstance().setCurrentUserInfo(userInfo);
                                        Log.d("rongLink", "true");
                                    }

                                    @Override
                                    public void onError(RongIMClient.ConnectionErrorCode connectionErrorCode) {

                                    }

                                    @Override
                                    public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus databaseOpenStatus) {
                                        JumpIntent.startNewIntent(HomePage.class);
                                        finish();
                                    }
                                });
                            }
                        });
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {

    }
}
