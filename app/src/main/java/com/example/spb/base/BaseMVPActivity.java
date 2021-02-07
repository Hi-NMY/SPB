package com.example.spb.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spb.view.Component.FragmentSpbAvtivityBar;

public abstract class BaseMVPActivity<V,T extends BasePresenter<V>> extends AppCompatActivity {

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V)this);
    }

    protected abstract T createPresenter();

    /**
     *初始化view
     * @Auther  nmynmy
     * @Date  2021-02-01  19:19
     */
    protected abstract void initActView();

    /**
     *初始化数据
     * @Auther  nmynmy
     * @Date  2021-02-01  19:20
     */
    protected abstract void initData();

    public FragmentSpbAvtivityBar setMyActivityBar(int barID){
        return (FragmentSpbAvtivityBar) getSupportFragmentManager().findFragmentById(barID);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.deleteView();
    }
}
