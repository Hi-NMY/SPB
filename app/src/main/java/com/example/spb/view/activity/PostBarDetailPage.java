package com.example.spb.view.activity;

import android.os.Bundle;
import android.view.View;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.PostBarDetailPageAPresenterImpl;
import com.example.spb.presenter.inter.IPostBarDetailPageAPresenter;
import com.example.spb.view.Component.FragmentSpbAvtivityBar;
import com.example.spb.view.InterComponent.ISpbAvtivityBarFView;
import com.example.spb.view.inter.IPostBarDetailPageAView;
import com.example.spb.view.littlefun.JumpIntent;
import com.gyf.immersionbar.ImmersionBar;

public class PostBarDetailPage extends BaseMVPActivity<IPostBarDetailPageAView, PostBarDetailPageAPresenterImpl> implements IPostBarDetailPageAView {

    private ISpbAvtivityBarFView bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_bar_detail_page);
    }

    @Override
    protected PostBarDetailPageAPresenterImpl createPresenter() {
        return new PostBarDetailPageAPresenterImpl();
    }

    @Override
    protected void initActView() {

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
        bar = setMyActivityBar(R.id.postbar_detail_bar);
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
        bar.barCentralTxt(TITLE,null);
    }
}
