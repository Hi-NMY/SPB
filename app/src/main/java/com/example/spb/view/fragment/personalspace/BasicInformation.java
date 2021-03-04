package com.example.spb.view.fragment.personalspace;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.example.spb.R;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.presenter.impl.BasicInformationFPresenterImpl;
import com.example.spb.view.activity.ChangeInformationPage;
import com.example.spb.view.inter.IBasicInformationFView;
import com.example.spb.view.littlefun.JumpIntent;

public class BasicInformation extends BaseMVPFragment<IBasicInformationFView, BasicInformationFPresenterImpl> implements IBasicInformationFView, View.OnClickListener {

    private TextView mBasicinformationChange;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {

    }

    @Override
    protected BasicInformationFPresenterImpl createPresenter() {
        return new BasicInformationFPresenterImpl();
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_personalspace_basic_information;
    }

    @Override
    protected void initFragView(View view) {
        mBasicinformationChange = (TextView)view.findViewById(R.id.basicinformation_change);
        setMyListener();
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.basicinformation_change:
                JumpIntent.startMyIntent(ChangeInformationPage.class);
                break;
        }
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
        mBasicinformationChange.setOnClickListener(this);
    }

    @Override
    public void setBar() {

    }

    @Override
    public void setActivityBar() {

    }
}
