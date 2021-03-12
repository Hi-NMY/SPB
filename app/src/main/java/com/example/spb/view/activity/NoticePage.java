package com.example.spb.view.activity;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spb.R;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.NoticePageAPresenterImpl;
import com.example.spb.presenter.inter.INoticePageAPresenter;
import com.example.spb.view.Component.FragmentSpbAvtivityBar;
import com.example.spb.view.inter.INoticePageAView;
import com.gyf.immersionbar.ImmersionBar;

public class NoticePage extends BaseMVPActivity<INoticePageAView,NoticePageAPresenterImpl> implements INoticePageAView {

    private FragmentSpbAvtivityBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_page);
        initActView();
    }

    @Override
    protected NoticePageAPresenterImpl createPresenter() {
        return new NoticePageAPresenterImpl();
    }

    @Override
    protected void initActView() {
        setActivityBar();
        setBar();
        setMyListener();
        createDialog();
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
        bar = setMyActivityBar(R.id.notice_actbar);
        bar.barCentralTxt(TITLE,null);
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }
}
