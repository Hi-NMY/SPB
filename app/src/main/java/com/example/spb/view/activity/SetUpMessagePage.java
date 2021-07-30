package com.example.spb.view.activity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.example.spb.R;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.SetUpMessagePageAPresenterImpl;
import com.example.spb.view.fragment.FragmentSpbAvtivityBar;
import com.example.spb.view.inter.ISetUpMessagePageAView;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

public class SetUpMessagePage extends BaseMVPActivity<ISetUpMessagePageAView, SetUpMessagePageAPresenterImpl> implements ISetUpMessagePageAView {

    private FragmentSpbAvtivityBar bar;
    private Switch mSwitch1;
    private List<Switch> switches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_message_page);
        initActView();
    }

    @Override
    protected SetUpMessagePageAPresenterImpl createPresenter() {
        return new SetUpMessagePageAPresenterImpl();
    }

    @Override
    protected void initActView() {
        switches = new ArrayList<>();
        mSwitch1 = (Switch) findViewById(R.id.switch1);
        switches.add(mSwitch1);
        setActivityBar();
        setBar();
        initData();
        setMyListener();
    }

    @Override
    protected void initData() {
        if (mPresenter.isNotifyAll()){
            switches.get(0).setChecked(true);
        }else {
            switches.get(0).setChecked(false);
        }
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
        for (int i = 0 ; i < switches.size() ; i++){
            int finalI = i;
            switches.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mPresenter.setNotifyAll(isChecked);
                }
            });
        }
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
        bar = setMyActivityBar(R.id.setup_message_actbar);
        bar.barCentralTxt(TITLE, null);
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }
}
