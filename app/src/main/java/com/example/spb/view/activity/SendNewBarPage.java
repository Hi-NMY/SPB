package com.example.spb.view.activity;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.spb.R;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.SendNewBarPageAPresenterImpl;
import com.example.spb.presenter.inter.ISendNewBarPageAPresenter;
import com.example.spb.view.Component.FragmentSpbAvtivityBar;
import com.example.spb.view.inter.ISendNewBarPageAView;
import com.gyf.immersionbar.ImmersionBar;

public class SendNewBarPage extends BaseMVPActivity<ISendNewBarPageAView,SendNewBarPageAPresenterImpl> implements ISendNewBarPageAView {

    private FragmentSpbAvtivityBar bar;

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
        bar = setMyActivityBar(R.id.sendnewbar_actbar);
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
        bar.getmBarRightTxt1().setTextColor(ContextCompat.getColor(this,R.color.theme_color));
        bar.barRightTxt1(SENDTITLE, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {

            }
        });
        bar.barCentralTxt(TITLE,null);
    }
}
