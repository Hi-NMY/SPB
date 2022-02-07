package com.example.spb.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.presenter.impl.SignInTaskFPresenterImpl;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MySharedPreferences;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.presenter.utils.Task;
import com.example.spb.view.Component.ComponentDialog;
import com.example.spb.view.Component.EasyDialog;
import com.example.spb.view.InterComponent.DialogInter;
import com.example.spb.view.activity.SendNewBarPage;
import com.example.spb.view.activity.SendNewVideoPage;
import com.example.spb.view.activity.SignInPage;
import com.example.spb.view.inter.ISignInTaskFView;
import com.example.spb.view.utils.JumpIntent;

import java.util.HashMap;
import java.util.Map;

public class FragmentSignInTask extends BaseMVPFragment<ISignInTaskFView,SignInTaskFPresenterImpl> implements ISignInTaskFView, View.OnClickListener {

    private Button mTostart;
    private Button mTostart1;
    private Button mTostart2;
    private Button mTostart3;
    private Button mTostart4;
    private Map<Integer,Button> btnMap;
    private ObtainSignData obtainSignData;
    private AddNewSign addNewSign;
    private SignInPage signInPage;
    private DialogInter loadingDialog;
    private DialogInter coinDialog;
    private ImageView mClose;
    private TextView mCoinSize;
    private int TASKKEY = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signInPage = (SignInPage) getActivity();
        obtainSignData = new ObtainSignData();
        addNewSign = new AddNewSign();
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_sign), addNewSign);
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_sign_data), obtainSignData);
    }

    @Override
    protected SignInTaskFPresenterImpl createPresenter() {
        return new SignInTaskFPresenterImpl();
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.frag_sign_in_task;
    }

    @Override
    protected void initFragView(View view) {
        btnMap = new HashMap<>();
        mTostart = (Button) view.findViewById(R.id.tostart);
        mTostart1 = (Button) view.findViewById(R.id.tostart1);
        mTostart2 = (Button) view.findViewById(R.id.tostart2);
        mTostart3 = (Button) view.findViewById(R.id.tostart3);
        mTostart4 = (Button) view.findViewById(R.id.tostart4);
        btnMap.put(1,mTostart);
        btnMap.put(2,mTostart1);
        btnMap.put(3,mTostart2);
        btnMap.put(4,mTostart3);
        btnMap.put(5,mTostart4);
        setMyListener();
        createDialog();
    }

    @Override
    protected void initData() {

    }

    public void initTaskData(){
        switch (mPresenter.getInSignKey()){
            case 1:
                setReceiveTask(1);
                break;
            case 2:
                setAlreadyTask(1);
                break;
        }
        switch (mPresenter.getInBarKey()){
            case 1:
                setReceiveTask(2);
                break;
            case 2:
                setAlreadyTask(2);
                break;
        }
        switch (mPresenter.getInlikeKey()){
            case 1:
                setReceiveTask(3);
                break;
            case 2:
                setAlreadyTask(3);
                break;
        }
        switch (mPresenter.getIntolikeKey()){
            case 1:
                setReceiveTask(4);
                break;
            case 2:
                setAlreadyTask(4);
                break;
        }
        switch (mPresenter.getInvideoKey()){
            case 1:
                setReceiveTask(5);
                break;
            case 2:
                setAlreadyTask(5);
                break;
        }
    }

    public void setReceiveTask(int a){
        btnMap.get(a).setText("领取");
        btnMap.get(a).setBackgroundResource(R.drawable.task_obtain_bg);
    }

    public void setAlreadyTask(int a){
        btnMap.get(a).setText("已完成");
        btnMap.get(a).setTextColor(ContextCompat.getColor(MyApplication.getContext(),R.color.qihei));
        btnMap.get(a).setBackgroundResource(R.drawable.task_already_bg);
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
        loadingDialog = new EasyDialog(signInPage, R.drawable.loading);
        coinDialog = new ComponentDialog(signInPage, R.layout.dialog_coin, new ComponentDialog.InitDialog() {
            @Override
            public void initView(View view) {
                mClose = (ImageView) view.findViewById(R.id.close);
                mCoinSize = (TextView) view.findViewById(R.id.coin_size);
            }

            @Override
            public void initData() {
                mCoinSize.setText("+10");
                mCoinSize.postInvalidate();
            }

            @Override
            public void initListener() {
                mClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeDialog(COINDIALOG);
                    }
                });
            }
        });
        coinDialog.setNoBg();
    }

    @Override
    public void showDialogS(int i) {
        switch (i){
            case LOADINGDIALOG:
                loadingDialog.showMyDialog();
                break;
            case COINDIALOG:
                coinDialog.showMyDialog();
                break;
        }
    }

    @Override
    public void closeDialog(int i) {
        switch (i){
            case LOADINGDIALOG:
                loadingDialog.closeMyDialog();
                break;
            case COINDIALOG:
                coinDialog.closeMyDialog();
                break;
        }
    }

    @Override
    public void setMyListener() {
        mTostart.setOnClickListener(this);
        mTostart1.setOnClickListener(this);
        mTostart2.setOnClickListener(this);
        mTostart3.setOnClickListener(this);
        mTostart4.setOnClickListener(this);
    }

    @Override
    public void setBar() {

    }

    @Override
    public void setActivityBar() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SpbBroadcast.destroyBrc(obtainSignData);
        SpbBroadcast.destroyBrc(addNewSign);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tostart:
                switch (mPresenter.getInSignKey()){
                    case 0:

                        break;
                    case 1:
                        TASKKEY = 1;
                        obtainCoin();
                        break;
                }
                break;
            case R.id.tostart1:
                switch (mPresenter.getInBarKey()){
                    case 0:
                        signInPage.finish();
                        JumpIntent.startMyIntent(SendNewBarPage.class);
                        break;
                    case 1:
                        TASKKEY = 2;
                        obtainCoin();
                        break;
                }
                break;
            case R.id.tostart2:
                switch (mPresenter.getInlikeKey()){
                    case 0:
                        signInPage.finish();
                        break;
                    case 1:
                        TASKKEY = 3;
                        obtainCoin();
                        break;
                }
                break;
            case R.id.tostart3:
                switch (mPresenter.getIntolikeKey()){
                    case 0:
                        signInPage.finish();
                        break;
                    case 1:
                        TASKKEY = 4;
                        obtainCoin();
                        break;
                }
                break;
            case R.id.tostart4:
                switch (mPresenter.getInvideoKey()){
                    case 0:
                        signInPage.finish();
                        JumpIntent.startMyIntent(SendNewVideoPage.class);
                        break;
                    case 1:
                        TASKKEY = 5;
                        obtainCoin();
                        break;
                }
                break;
        }
    }

    public void obtainCoin(){
        showDialogS(LOADINGDIALOG);
        mPresenter.alreadyTask(signInPage.getDataUserMsgPresenter().getUser_account(), new SignInTaskFPresenterImpl.OnReturn() {
            @Override
            public void onReturn() {
                signInPage.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeDialog(LOADINGDIALOG);
                        showDialogS(COINDIALOG);
                        SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_sign_task));
                        switch (TASKKEY){
                            case 1:
                                setAlreadyTask(1);
                                mPresenter.setInSignKey(2);
                                editor.putInt(InValues.send(R.string.sign_task_daysign),2);
                                editor.commit();
                                break;
                            case 2:
                                setAlreadyTask(2);
                                mPresenter.setInBarKey(2);
                                editor.putInt(InValues.send(R.string.sign_task_bar),2);
                                editor.commit();
                                break;
                            case 3:
                                setAlreadyTask(3);
                                mPresenter.setInlikeKey(2);
                                editor.putInt(InValues.send(R.string.sign_task_like),2);
                                editor.commit();
                                break;
                            case 4:
                                setAlreadyTask(4);
                                mPresenter.setIntolikeKey(2);
                                editor.putInt(InValues.send(R.string.sign_task_tolike),2);
                                editor.commit();
                                break;
                            case 5:
                                setAlreadyTask(5);
                                mPresenter.setInvideoKey(2);
                                editor.putInt(InValues.send(R.string.sign_task_video),2);
                                editor.commit();
                                break;
                        }
                    }
                });
            }
        });
    }

    class ObtainSignData extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean likeKey = Task.getLikeData();
            boolean topicKey = Task.getTopicData();
            SharedPreferences sharedPreferences = MySharedPreferences.getShared(InValues.send(R.string.Shared_sign_task));
            mPresenter.setInBarKey(sharedPreferences.getInt(InValues.send(R.string.sign_task_bar),0));
            mPresenter.setInlikeKey(sharedPreferences.getInt(InValues.send(R.string.sign_task_like),0));
            mPresenter.setInSignKey(sharedPreferences.getInt(InValues.send(R.string.sign_task_daysign),0));
            mPresenter.setIntolikeKey(sharedPreferences.getInt(InValues.send(R.string.sign_task_tolike),0));
            mPresenter.setInvideoKey(sharedPreferences.getInt(InValues.send(R.string.sign_task_video),0));
            if (likeKey && mPresenter.getInlikeKey() == 0){
                mPresenter.setInlikeKey(1);
            }
            if (topicKey && mPresenter.getIntolikeKey() == 0){
                mPresenter.setIntolikeKey(1);
            }
            initTaskData();
        }
    }

    class AddNewSign extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //通知已签到。修改领取奖励
            int coin = intent.getIntExtra("key_one", 0);
            if (coin != 10){
                setReceiveTask(1);
                mPresenter.setInSignKey(1);
                SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_sign_task));
                editor.putInt(InValues.send(R.string.sign_task_daysign),1);
                editor.commit();
            }
        }
    }
}
