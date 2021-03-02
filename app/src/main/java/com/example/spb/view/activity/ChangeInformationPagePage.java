package com.example.spb.view.activity;

import android.os.Bundle;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.example.spb.R;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.ChangeInformationPageAPresenterImpl;
import com.example.spb.view.Component.FragmentSpbAvtivityBar;
import com.example.spb.view.inter.IChangeInformationPageAView;
import com.gyf.immersionbar.ImmersionBar;

public class ChangeInformationPagePage extends BaseMVPActivity<IChangeInformationPageAView, ChangeInformationPageAPresenterImpl> implements IChangeInformationPageAView {

    private FragmentSpbAvtivityBar bar;
    private String TITLE = "修改资料";
    private String RIGHTTXT = "保存";

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
        bar = setMyActivityBar(R.id.changeinformation_actbar);
        TextView rightTxt = bar.getmBarRightTxt1();
        rightTxt.setTextColor(ContextCompat.getColor(this,R.color.theme_color2));
        bar.barCentralTxt(TITLE,null);
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
}
