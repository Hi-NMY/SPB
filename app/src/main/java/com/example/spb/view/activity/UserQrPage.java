package com.example.spb.view.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.example.spb.R;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.UserQrPageAPresenterImpl;
import com.example.spb.view.Component.FragmentSpbAvtivityBar;
import com.example.spb.view.inter.IUserQrPageAView;
import com.gyf.immersionbar.ImmersionBar;
import com.king.zxing.util.CodeUtils;

public class UserQrPage extends BaseMVPActivity<IUserQrPageAView, UserQrPageAPresenterImpl> implements IUserQrPageAView {

    private Bitmap qrBitmap;
    private ImageView mMyQrCode;
    private FragmentSpbAvtivityBar bar;

    private String TITLETXT = "我的二维码";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_qr_page);
        initActView();
        //生成二维码
        qrBitmap = CodeUtils.createQRCode("123123", 400);
        mMyQrCode.setImageBitmap(qrBitmap);
    }

    @Override
    protected UserQrPageAPresenterImpl createPresenter() {
        return new UserQrPageAPresenterImpl();
    }

    @Override
    protected void initActView() {
        mMyQrCode = (ImageView) findViewById(R.id.my_qrCode);
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
        bar = setMyActivityBar(R.id.userqr_actbar);
        bar.setBarBackground(R.color.TransColor);
        bar.barCentralTxt(TITLETXT,null);
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }
}
