package com.example.spb.view.activity;

import android.os.Bundle;
import com.example.spb.R;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.SecondHandStorePageAPresenterImpl;
import com.example.spb.view.fragment.FragmentSpbAvtivityBar;
import com.example.spb.view.inter.ISecondHandStorePageAView;
import com.gyf.immersionbar.ImmersionBar;

public class SecondHandStorePage extends BaseMVPActivity<ISecondHandStorePageAView,SecondHandStorePageAPresenterImpl>
        implements ISecondHandStorePageAView {

    private FragmentSpbAvtivityBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_hand_store_page);
        initActView();
    }

    @Override
    protected SecondHandStorePageAPresenterImpl createPresenter() {
        return new SecondHandStorePageAPresenterImpl();
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
        bar = setMyActivityBar(R.id.secondhandstore_actbar);
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
        bar.barCentralTxt(TITLE,null);
    }

    @Override
    public void createRefresh() {

    }

    @Override
    public void finishRRefresh(int num) {

    }

}
