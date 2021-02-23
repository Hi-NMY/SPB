package com.example.spb.view.fragment.personalspace;

import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import com.example.spb.R;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.presenter.impl.BasicInformationFPresenterImpl;
import com.example.spb.presenter.inter.IBasicInformationFPresenter;
import com.example.spb.view.inter.IBasicInformationFView;

public class BasicInformation extends BaseMVPFragment<IBasicInformationFView,BasicInformationFPresenterImpl> implements IBasicInformationFView {

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

    }

    @Override
    protected void initData() {

    }
}
