package com.example.spb.base;

import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseMVPFragment<V,T extends BasePresenter<V>> extends Fragment {

    protected T mPresenter;
    private View myView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(getLayoutViewId(),container,false);

        mPresenter = createPresenter();

        mPresenter.attachView((V) myView);

        initFragView(myView);

        initData();

        return myView;

    }

    protected abstract T createPresenter();

    /**
     *返回view  id
     * @Auther  nmynmy
     * @Date  2021-02-01  19:19
     */
    protected abstract int getLayoutViewId();

    /**
     *初始化view
     * @Auther  nmynmy
     * @Date  2021-02-01  19:19
     */
    protected abstract void initFragView(View view);

    /**
     *初始化数据
     * @Auther  nmynmy
     * @Date  2021-02-01  19:20
     */
    protected abstract void initData();

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.deleteView();
    }
}
