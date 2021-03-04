package com.example.spb.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.example.spb.R;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.ChangeInformationPageAPresenterImpl;
import com.example.spb.view.Component.ComponentDialog;
import com.example.spb.view.Component.FragmentSpbAvtivityBar;
import com.example.spb.view.InterComponent.DialogInter;
import com.example.spb.view.inter.IChangeInformationPageAView;
import com.gyf.immersionbar.ImmersionBar;

public class ChangeInformationPage extends BaseMVPActivity<IChangeInformationPageAView, ChangeInformationPageAPresenterImpl>
        implements IChangeInformationPageAView, View.OnClickListener {

    private FragmentSpbAvtivityBar bar;
    private String TITLE = "修改资料";
    private String RIGHTTXT = "保存";

    private DialogInter bottomDialog;

    private RelativeLayout mRFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_information_page);
        initActView();
    }

    @Override
    protected ChangeInformationPageAPresenterImpl createPresenter() {
        return new ChangeInformationPageAPresenterImpl();
    }

    @Override
    protected void initActView() {
        mRFavorite = (RelativeLayout) findViewById(R.id.r_favorite);
        setMyListener();
        createDialog();
        setBar();
        setActivityBar();
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
        bottomDialog = new ComponentDialog(this, R.layout.dialog_favorite, R.style.dialogHomeSend, new ComponentDialog.InitDialog() {
            @Override
            public void initView(View view) {

            }

            @Override
            public void initData() {

            }

            @Override
            public void initListener() {

            }
        });
        bottomDialog.setBottomStyle();
        bottomDialog.setAnimation(R.style.bottomdialog_animStyle);
    }

    @Override
    public void showDialogS(int i) {
        bottomDialog.showMyDialog();
    }

    @Override
    public void closeDialog(int i) {

    }

    @Override
    public void setMyListener() {
        mRFavorite.setOnClickListener(this);
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
        bar = setMyActivityBar(R.id.changeinformation_actbar);
        TextView rightTxt = bar.getmBarRightTxt1();
        rightTxt.setTextColor(ContextCompat.getColor(this, R.color.theme_color));
        bar.barCentralTxt(TITLE, null);
        bar.barLeftImg(R.drawable.close_black, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
        bar.barRightTxt1(RIGHTTXT, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.r_favorite:
                showDialogS(0);
                break;
        }
    }
}
