package com.example.spb.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spb.presenter.otherimpl.*;
import com.example.spb.view.Component.FragmentSpbAvtivityBar;

public abstract class BaseMVPActivity<V,T extends BasePresenter<V>> extends AppCompatActivity {

    protected T mPresenter;

    public static String user_account;
    public static DataAttentionTopicPresenter dataAttentionTopicPresenter;
    public static DataCollectBarPresenter dataCollectBarPresenter;
    public static DataDiaryPresenter dataDiaryPresenter;
    public static DataFollowPresenter dataFollowPresenter;
    public static DataFollowedPresenter dataFollowedPresenter;
    public static DataLikePresenter dataLikePresenter;
    public static DataPostBarPresenter dataPostBarPresenter;
    public static DataUserMsgPresenter dataUserMsgPresenter;

    public static String deletePbId = "";

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

    public void initUserData(String account){
        user_account = account;
        setDataAttentionTopicPresenter();
        setDataCollectBarPresenter();
        setDataDiaryPresenter();
        setDataFollowedPresenter();
        setDataFollowPresenter();
        setDataLikePresenter();
        setDataPostBarPresenter();
        setDataUserMsgPresenter();
    }

    public static String getDeletePbId() {
        return deletePbId;
    }

    public static void setDeletePbId(String deletePbId) {
        BaseMVPActivity.deletePbId = deletePbId;
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
        this.dataAttentionTopicPresenter = new DataAttentionTopicPresenter(user_account);
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
        this.dataCollectBarPresenter = new DataCollectBarPresenter(user_account);
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
        this.dataDiaryPresenter = new DataDiaryPresenter(user_account);
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
        this.dataFollowPresenter = new DataFollowPresenter(user_account);
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
        this.dataFollowedPresenter = new DataFollowedPresenter(user_account);
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
        this.dataLikePresenter = new DataLikePresenter(user_account);
    }

    public DataPostBarPresenter getDataPostBarPresenter() {
        if (dataPostBarPresenter == null){
            setDataPostBarPresenter();
            return dataPostBarPresenter;
        }else {
            return dataPostBarPresenter;
        }
    }

    public void setDataPostBarPresenter() {
        this.dataPostBarPresenter = new DataPostBarPresenter(user_account);
    }

    public DataUserMsgPresenter getDataUserMsgPresenter() {
        if (dataUserMsgPresenter == null){
            setDataUserMsgPresenter();
            return dataUserMsgPresenter;
        }else {
            return dataUserMsgPresenter;
        }
    }

    public void setDataUserMsgPresenter() {
        this.dataUserMsgPresenter = new DataUserMsgPresenter(user_account);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.deleteView();
    }
}
