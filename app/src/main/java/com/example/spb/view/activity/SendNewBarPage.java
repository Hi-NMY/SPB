package com.example.spb.view.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.SendNewBarPageAPresenterImpl;
import com.example.spb.view.Component.FragmentSpbAvtivityBar;
import com.example.spb.view.Component.SelectImage;
import com.example.spb.view.inter.ISendNewBarPageAView;
import com.example.spb.view.littlefun.HideKeyboard;
import com.gyf.immersionbar.ImmersionBar;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.vansz.glideimageloader.GlideImageLoader;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Arrays;
import java.util.List;

public class SendNewBarPage extends BaseMVPActivity<ISendNewBarPageAView, SendNewBarPageAPresenterImpl> implements ISendNewBarPageAView, View.OnClickListener {

    private String[] titleTag = new String[]{"我的位置", "添加话题"};
    private List<String> titleTagList = Arrays.asList(titleTag);
    private FragmentSpbAvtivityBar bar;
    private EditText mSendnewbarTxt;
    private RecyclerView mSendnewbarImageList;
    private RoundedImageView mSendnewbarImageAdd;
    private TagFlowLayout mBottomFlowlayout;
    private ImageView mBottomAddImage;
    private ImageView mBottomAddVoice;
    private SelectImage selectImage;
    private String headImgPath;

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
        selectImage = new SelectImage(this);
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
        initData();
    }

    @Override
    protected void initData() {
        LayoutInflater mInflater = LayoutInflater.from(this);
        mBottomFlowlayout.setAdapter(new TagAdapter<String>(titleTagList) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                View v = mInflater.inflate(R.layout.other_newbar_tag, mBottomFlowlayout, false);
                TextView t = (TextView) v.findViewById(R.id.text);
                ImageView i = (ImageView) v.findViewById(R.id.image);
                if (position == 0) {
                    i.setBackground(getDrawable(R.drawable.icon_location));
                    if (!o.equals("我的位置")){
                        t.setTextColor(getColor(R.color.theme_color));
                    }else {
                        t.setTextColor(getColor(R.color.qihei));
                    }
                } else {
                    i.setBackground(getDrawable(R.drawable.icon_topic));
                }
                t.setText(o);
                return v;
            }
        });
        mBottomFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (position == 0){

                }
                if (position == 1){

                }
                return true;
            }
        });
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
        HideKeyboard.hideboard(mBottomAddImage);
        switch (v.getId()){
            case R.id.sendnewbar_image_add:
                selectImage();
                break;
            case R.id.bottom_add_image:
                selectImage();
                break;
            case R.id.bottom_add_voice:

                break;
        }
    }

    public void selectImage(){
        selectImage.selectMoreImg(System.currentTimeMillis() +
                (int) (Math.random() * 1000) + ".png",MAXPOSITION - mPresenter.barImage.size()
                ,new OnResultCallbackListener<LocalMedia>() {
            @Override
            public void onResult(List<LocalMedia> result) {
                mPresenter.obtainImage(result);
                mPresenter.setImageList(SendNewBarPage.this,
                        new LinearLayoutManager(MyApplication.getContext()),mSendnewbarImageList);
                mSendnewbarImageList.setVisibility(View.VISIBLE);
                if (mPresenter.barImage.size() == 4){
                    changeIcon(MAXIMAGE);
                }else {
                    changeIcon(HAVEIMAGE);
                }
            }
            @Override
            public void onCancel() {

            }
        });
    }

    public void changeIcon(int a){
        if (mPresenter.barImage == null||mPresenter.barImage.size() == 0){
            mBottomAddVoice.setClickable(true);
            mBottomAddVoice.setBackground(ContextCompat.getDrawable(this,R.drawable.icon_newbar_voice1));
        }else {
            mBottomAddVoice.setBackground(ContextCompat.getDrawable(this,R.drawable.icon_newbar_voice_no));
            mBottomAddVoice.setClickable(false);
        }
        switch (a){
            case MAXIMAGE:
                mSendnewbarImageAdd.setVisibility(View.GONE);
                mBottomAddImage.setBackground(ContextCompat.getDrawable(this,R.drawable.icon_newbar_img_no));
                mBottomAddImage.setClickable(false);
                break;
            case STARTVOICE:
                mBottomAddVoice.setBackground(ContextCompat.getDrawable(this,R.drawable.icon_newbar_voice2));
                break;
            case STARTVOICE1:
                mBottomAddVoice.setBackground(ContextCompat.getDrawable(this,R.drawable.icon_newbar_voice1));
                break;
            case HAVEIMAGE:
                mSendnewbarImageAdd.setVisibility(View.VISIBLE);
                mBottomAddImage.setBackground(ContextCompat.getDrawable(this,R.drawable.icon_newbar_img));
                mBottomAddImage.setClickable(true);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.newBarImageAdapter.destroyTransFeree();
    }
}
