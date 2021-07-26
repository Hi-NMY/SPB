package com.example.spb.view.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.spb.R;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.SetUpPageAPresenterImpl;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.MySharedPreferences;
import com.example.spb.view.fragment.FragmentSpbAvtivityBar;
import com.example.spb.view.inter.ISetUpPageAView;
import com.gyf.immersionbar.ImmersionBar;
import io.rong.imkit.RongIM;

public class SetUpPage extends BaseMVPActivity<ISetUpPageAView, SetUpPageAPresenterImpl> implements ISetUpPageAView {

    private FragmentSpbAvtivityBar bar;
    private TextView mTuichu;

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
        mTuichu = (TextView) findViewById(R.id.tuichu);
        mTuichu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_FirstLogIn));
                editor.putBoolean(InValues.send(R.string.FirstLogIn_login),true);
                editor.commit();
                RongIM.getInstance().logout();
                //退出登录时加入逻辑：删除user_ip。使用户无法收到其他用户推送的消息
            }
        });
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
        bar.barCentralTxt(TITLE, null);
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }
}
