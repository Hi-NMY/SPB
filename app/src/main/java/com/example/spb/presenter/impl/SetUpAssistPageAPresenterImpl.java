package com.example.spb.presenter.impl;

import android.content.SharedPreferences;
import com.example.spb.R;
import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.SetUpAssistPageAModelImpl;
import com.example.spb.model.inter.ISetUpAssistPageAModel;
import com.example.spb.presenter.inter.ISetUpAssistPageAPresenter;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.MySharedPreferences;
import com.example.spb.view.inter.ISetUpAssistPageAView;

public class SetUpAssistPageAPresenterImpl extends BasePresenter<ISetUpAssistPageAView> implements ISetUpAssistPageAPresenter {

    private SharedPreferences.Editor editor;
    private boolean classKey = true;
    private boolean activeKey = true;

    public boolean isClssKey() {
        return classKey;
    }

    public void setClssKey(boolean classKey) {
        this.classKey = classKey;
        editor.putBoolean(InValues.send(R.string.assist_class),classKey);
        editor.apply();
    }

    public boolean isActiveKey() {
        return activeKey;
    }

    public void setActiveKey(boolean activeKey) {
        this.activeKey = activeKey;
        editor.putBoolean(InValues.send(R.string.assist_active),activeKey);
        editor.apply();
    }

    public SetUpAssistPageAPresenterImpl() {
        editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_assist_setup));
        getAssistSetUp();
    }

    public void getAssistSetUp(){
        SharedPreferences sharedPreferences = MySharedPreferences.getShared(InValues.send(R.string.Shared_assist_setup));
        activeKey = sharedPreferences.getBoolean(InValues.send(R.string.assist_active),true);
        classKey = sharedPreferences.getBoolean(InValues.send(R.string.assist_class),true);
    }
}
