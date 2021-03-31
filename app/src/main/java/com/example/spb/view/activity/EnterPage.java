package com.example.spb.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.EnterPageAPresenterImpl;
import com.example.spb.view.inter.IEnterPageAView;
import com.example.spb.view.littlefun.GlideRoundTransform;
import com.example.spb.view.littlefun.JumpIntent;
import com.gyf.immersionbar.ImmersionBar;
import org.litepal.LitePal;

public class EnterPage extends BaseMVPActivity<IEnterPageAView, EnterPageAPresenterImpl> implements IEnterPageAView {

    private ImageView mBgimage;
    private ImageView mLogoimage;

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
        mBgimage = (ImageView) findViewById(R.id.bgimage);
        mLogoimage = (ImageView) findViewById(R.id.logoimage);
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
                    Thread.sleep(2000);
                    if (mPresenter.getFirstLogIn()){
                        Intent intent = new Intent(MyApplication.getContext(),FirstPage.class);
                        startActivity(intent);
                    }else {
                        JumpIntent.startNewIntent(HomePage.class);
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
