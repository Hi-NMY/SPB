package com.example.spb.view.activity;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.SetUpPrivacyPageAPresenterImpl;
import com.example.spb.view.Component.MyToastClass;
import com.example.spb.view.fragment.FragmentSpbAvtivityBar;
import com.example.spb.view.inter.ISetUpPrivacyPageAView;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.List;

public class SetUpPrivacyPage extends BaseMVPActivity<ISetUpPrivacyPageAView, SetUpPrivacyPageAPresenterImpl> implements ISetUpPrivacyPageAView {

    private List<Switch> switches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_privacy_page);
        mPresenter.setMyPrivacy(getDataUserMsgPresenter().getUser_privacy());
        initActView();
    }

    @Override
    protected SetUpPrivacyPageAPresenterImpl createPresenter() {
        return new SetUpPrivacyPageAPresenterImpl();
    }

    @Override
    protected void initActView() {
        switches = new ArrayList<>();
        Switch mSwitch1 = findViewById(R.id.switch1);
        Switch mSwitch2 = findViewById(R.id.switch2);
        Switch mSwitch3 = findViewById(R.id.switch3);
        Switch mSwitch4 = findViewById(R.id.switch4);
        Switch mSwitch5 = findViewById(R.id.switch5);
        Switch mSwitch6 = findViewById(R.id.switch6);
        Switch mSwitch7 = findViewById(R.id.switch7);
        Switch mSwitch8 = findViewById(R.id.switch8);
        switches.add(mSwitch1);
        switches.add(mSwitch2);
        switches.add(mSwitch3);
        switches.add(mSwitch4);
        switches.add(mSwitch5);
        switches.add(mSwitch6);
        switches.add(mSwitch7);
        switches.add(mSwitch8);
        initData();
        setMyListener();
        setActivityBar();
        setBar();
    }

    @Override
    protected void initData() {
        for (int i = 0; i < mPresenter.getKeys().size(); i++) {
            switches.get(i).setChecked(mPresenter.getKeys().get(i) == 1);
        }
    }

    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (responseFlag) {
                    case ON_SUCCEED:
                        getDataUserMsgPresenter().setUser_privacy(mPresenter.getStringPrivacy());
                        break;
                    case ON_ERROR:
                        switches.get((Integer) response).setChecked(!switches.get((Integer) response).isChecked());
                        MyToastClass.ShowToast(MyApplication.getContext(), "错误，请重试");
                        break;
                }
            }
        });
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
        for (int i = 0; i < switches.size(); i++) {
            int finalI = i;
            switches.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mPresenter.setKeys(finalI, isChecked);
                    mPresenter.updateUserPrivacy(getDataUserMsgPresenter().getUser_account(), finalI);
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
        FragmentSpbAvtivityBar bar = setMyActivityBar(R.id.setup_privacy_actbar);
        bar.barCentralTxt(TITLE, null);
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }
}
