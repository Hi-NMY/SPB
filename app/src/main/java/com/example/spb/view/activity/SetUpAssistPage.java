package com.example.spb.view.activity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.SetUpAssistPageAPresenterImpl;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.fragment.FragmentSpbAvtivityBar;
import com.example.spb.view.inter.ISetUpAssistPageAView;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

public class SetUpAssistPage extends BaseMVPActivity<ISetUpAssistPageAView, SetUpAssistPageAPresenterImpl> implements ISetUpAssistPageAView {

    private FragmentSpbAvtivityBar bar;
    private List<Switch> switches;
    private Switch mSwitch1;
    private Switch mSwitch2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_assist_page);
        initActView();
    }

    @Override
    protected SetUpAssistPageAPresenterImpl createPresenter() {
        return new SetUpAssistPageAPresenterImpl();
    }

    @Override
    protected void initActView() {
        switches = new ArrayList<>();
        mSwitch1 = (Switch) findViewById(R.id.switch1);
        mSwitch2 = (Switch) findViewById(R.id.switch2);
        switches.add(mSwitch1);
        switches.add(mSwitch2);
        initData();
        setActivityBar();
        setBar();
        setMyListener();
    }

    @Override
    protected void initData() {
        if (mPresenter.isClssKey()){
            switches.get(0).setChecked(true);
        }else {
            switches.get(0).setChecked(false);
        }
        if (mPresenter.isActiveKey()){
            switches.get(1).setChecked(true);
        }else {
            switches.get(1).setChecked(false);
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
                    switch(finalI){
                        case 0:
                            mPresenter.setClssKey(isChecked);
                            break;
                        case 1:
                            mPresenter.setActiveKey(isChecked);
                            break;
                    }
                    SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_assist_card),0,null);
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
        bar = setMyActivityBar(R.id.setup_assist_actbar);
        bar.barCentralTxt(TITLE, null);
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }
}
