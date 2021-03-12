package com.example.spb.view.activity;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.spb.R;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.SetUpPageAPresenterImpl;
import com.example.spb.presenter.inter.ISetUpPageAPresenter;
import com.example.spb.view.Component.FragmentSpbAvtivityBar;
import com.example.spb.view.inter.ISetUpPageAView;
import com.gyf.immersionbar.ImmersionBar;

public class SetUpPage extends BaseMVPActivity<ISetUpPageAView,SetUpPageAPresenterImpl> implements ISetUpPageAView {

    private FragmentSpbAvtivityBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_page);
        initActView();
    }

    @Override
    protected SetUpPageAPresenterImpl createPresenter() {
        return new SetUpPageAPresenterImpl();
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
        bar = setMyActivityBar(R.id.setup_actbar);
        bar.barCentralTxt(TITLE,null);
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }
}
