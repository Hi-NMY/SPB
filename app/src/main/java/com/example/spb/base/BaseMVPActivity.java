package com.example.spb.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spb.presenter.otherimpl.*;
import com.example.spb.view.Component.FragmentSpbAvtivityBar;

public abstract class BaseMVPActivity<V,T extends BasePresenter<V>> extends AppCompatActivity {

    protected T mPresenter;

    public DataAttentionTopicPresenter dataAttentionTopicPresenter;
    public DataCollectBarPresenter dataCollectBarPresenter;
    public DataDiaryPresenter dataDiaryPresenter;
    public DataFollowPresenter dataFollowPresenter;
    public DataFollowedPresenter dataFollowedPresenter;
    public DataLikePresenter dataLikePresenter;

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

    public void initUserData(){
        setDataAttentionTopicPresenter();
        setDataCollectBarPresenter();
        setDataDiaryPresenter();
        setDataFollowedPresenter();
        setDataFollowPresenter();
        setDataLikePresenter();
    }

    public DataAttentionTopicPresenter getDataAttentionTopicPresenter() {
        if (dataAttentionTopicPresenter == null){
            setDataAttentionTopicPresenter();
            return dataAttentionTopicPresenter;
        }else {
            return dataAttentionTopicPresenter;
        }
    }

    public void setDataAttentionTopicPresenter() {
        this.dataAttentionTopicPresenter = new DataAttentionTopicPresenter();
    }

    public DataCollectBarPresenter getDataCollectBarPresenter() {
        if (dataCollectBarPresenter == null){
            setDataCollectBarPresenter();
            return dataCollectBarPresenter;
        }else {
            return dataCollectBarPresenter;
        }
    }

    public void setDataCollectBarPresenter() {
        this.dataCollectBarPresenter = new DataCollectBarPresenter();
    }

    public DataDiaryPresenter getDataDiaryPresenter() {
        if (dataDiaryPresenter == null){
            setDataDiaryPresenter();
            return dataDiaryPresenter;
        }else {
            return dataDiaryPresenter;
        }
    }

    public void setDataDiaryPresenter() {
        this.dataDiaryPresenter = new DataDiaryPresenter();
    }

    public DataFollowPresenter getDataFollowPresenter() {
        if (dataFollowPresenter == null){
            setDataFollowPresenter();
            return dataFollowPresenter;
        }else {
            return dataFollowPresenter;
        }
    }

    public void setDataFollowPresenter() {
        this.dataFollowPresenter = new DataFollowPresenter();
    }

    public DataFollowedPresenter getDataFollowedPresenter() {
        if (dataFollowedPresenter == null){
            setDataFollowedPresenter();
            return dataFollowedPresenter;
        }else {
            return dataFollowedPresenter;
        }
    }

    public void setDataFollowedPresenter() {
        this.dataFollowedPresenter = new DataFollowedPresenter();
    }

    public DataLikePresenter getDataLikePresenter() {
        if (dataLikePresenter == null){
            setDataLikePresenter();
            return dataLikePresenter;
        }else {
            return dataLikePresenter;
        }
    }

    public void setDataLikePresenter() {
        this.dataLikePresenter = new DataLikePresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.deleteView();
    }
}
