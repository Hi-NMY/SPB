package com.example.spb.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.SendNewBarPageAPresenterImpl;
import com.example.spb.view.Component.FragmentSpbAvtivityBar;
import com.example.spb.view.inter.ISendNewBarPageAView;
import com.example.spb.view.littlefun.HideKeyboard;
import com.gyf.immersionbar.ImmersionBar;
import com.makeramen.roundedimageview.RoundedImageView;
import com.zhy.view.flowlayout.TagFlowLayout;

public class SendNewBarPage extends BaseMVPActivity<ISendNewBarPageAView, SendNewBarPageAPresenterImpl> implements ISendNewBarPageAView, View.OnClickListener {

    private FragmentSpbAvtivityBar bar;
    private EditText mSendnewbarTxt;
    private RecyclerView mSendnewbarImageList;
    private RoundedImageView mSendnewbarImageAdd;
    private TagFlowLayout mBottomFlowlayout;
    private ImageView mBottomAddImage;
    private ImageView mBottomAddVoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_new_bar_page);
        initActView();
    }

    @Override
    protected SendNewBarPageAPresenterImpl createPresenter() {
        return new SendNewBarPageAPresenterImpl();
    }

    @Override
    protected void initActView() {
        mSendnewbarTxt = (EditText) findViewById(R.id.sendnewbar_txt);
        mSendnewbarImageList = (RecyclerView) findViewById(R.id.sendnewbar_image_list);
        mSendnewbarImageAdd = (RoundedImageView) findViewById(R.id.sendnewbar_image_add);
        mBottomFlowlayout = (TagFlowLayout) findViewById(R.id.bottom_flowlayout);
        mBottomAddImage = (ImageView) findViewById(R.id.bottom_add_image);
        mBottomAddVoice = (ImageView) findViewById(R.id.bottom_add_voice);
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
        mSendnewbarImageAdd.setOnClickListener(this);
        mBottomAddImage.setOnClickListener(this);
        mBottomAddVoice.setOnClickListener(this);
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
        bar = setMyActivityBar(R.id.sendnewbar_actbar);
        bar.barLeftImg(R.drawable.close_black, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
        bar.getmBarRightTxt1().setTextColor(ContextCompat.getColor(this, R.color.theme_color));
        bar.barRightTxt1(SENDTITLE, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {

            }
        });
        bar.barCentralTxt(TITLE, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sendnewbar_image_add:

                break;
            case R.id.bottom_add_image:

                break;
            case R.id.bottom_add_voice:

                break;
        }
    }

    public void changeIcon(int a){
        HideKeyboard.hideboard(mSendnewbarTxt);
//        switch (a){
//            case :
//
//                break;
//        }
    }
}
