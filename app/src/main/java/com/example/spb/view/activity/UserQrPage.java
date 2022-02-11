package com.example.spb.view.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.example.spb.R;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.UserQrPageAPresenterImpl;
import com.example.spb.presenter.utils.DataEncryption;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MyDateClass;
import com.example.spb.view.fragment.FragmentSpbAvtivityBar;
import com.example.spb.view.inter.IUserQrPageAView;
import com.gyf.immersionbar.ImmersionBar;
import com.king.zxing.util.CodeUtils;
import com.makeramen.roundedimageview.RoundedImageView;

public class UserQrPage extends BaseMVPActivity<IUserQrPageAView, UserQrPageAPresenterImpl> implements IUserQrPageAView {

    private Bitmap qrBitmap;
    private ImageView mMyQrCode;

    private int height;
    private String cacheDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_qr_page);
        cacheDate = MyDateClass.showNowDate();
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  //宽度（PX）
        height = metric.heightPixels;  //高度（PX）
        if (height < 2000) {
            height = 500;
        } else {
            height = 650;
        }
        initActView();
        //生成二维码
        mPresenter.obtainDate(new UserQrPageAPresenterImpl.OnReturn() {
            @Override
            public void onReturn() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        qrBitmap = CodeUtils.createQRCode(DataEncryption.intoData(getDataUserMsgPresenter().getUser_account() + mPresenter.getDate()), height);
                        mMyQrCode.setImageBitmap(qrBitmap);
                    }
                });
            }
        });
    }

    @Override
    protected UserQrPageAPresenterImpl createPresenter() {
        return new UserQrPageAPresenterImpl();
    }

    @Override
    protected void initActView() {
        mMyQrCode = findViewById(R.id.my_qrCode);
        RoundedImageView mUserHeadimg = findViewById(R.id.user_headimg);
        if (mUserHeadimg.getTag() == null || !mUserHeadimg.getTag().equals(cacheDate)) {
            Glide.with(this)
                    .load(InValues.send(R.string.prefix_img) + getDataUserMsgPresenter().getUser_account() + InValues.send(R.string.suffix_head_img))
                    .placeholder(R.drawable.logo2)
                    .error(R.drawable.logo2)
                    .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()), 1, 1))
                    .centerCrop()
                    .into(mUserHeadimg);
            mUserHeadimg.setTag(cacheDate);
        }
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
                .init();
    }

    @Override
    public void setActivityBar() {
        FragmentSpbAvtivityBar bar = setMyActivityBar(R.id.userqr_actbar);
        bar.setBarBackground(R.color.TransColor);
        String TITLETXT = "我的二维码";
        bar.barCentralTxt(TITLETXT, null);
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }
}
