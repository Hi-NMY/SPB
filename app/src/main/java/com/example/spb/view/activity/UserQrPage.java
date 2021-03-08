package com.example.spb.view.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import com.example.spb.R;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.UserQrPageAPresenterImpl;
import com.example.spb.view.inter.IUserQrPageAView;
import com.king.zxing.util.CodeUtils;

public class UserQrPage extends BaseMVPActivity<IUserQrPageAView, UserQrPageAPresenterImpl> implements IUserQrPageAView {

    private Bitmap qrBitmap;
    private ImageView mMyQrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_qr_page);
        initView();
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

    private void initView() {
        mMyQrCode = (ImageView) findViewById(R.id.my_qrCode);
    }
}
