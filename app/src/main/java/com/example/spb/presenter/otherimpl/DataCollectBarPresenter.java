package com.example.spb.presenter.otherimpl;

import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.entity.Bar;
import com.example.spb.entity.CollectBar;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.CollectBarModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataCollectBarPresenter {

    private static int SUCCESS = 200;

    private SpbModelBasicInter collectBarModel;
    public List<CollectBar> collectList;
    private String account;
    private String a;
    private Gson gson;

    public DataCollectBarPresenter(String user_account) {
        account = user_account;
        collectBarModel = new CollectBarModelImpl();
        gson = new Gson();
        initDate();
    }

    private void initDate() {
        CollectBar collectBar = new CollectBar();
        collectBar.setUser_account(account);
        collectBarModel.selectData(collectBarModel.DATACOLLECTBAR_SELECT_ONE, collectBar, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == SUCCESS){
                        collectList = gson.fromJson(a.substring(3),new TypeToken<List<CollectBar>>()
                        {}.getType());
                    }else {

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void obtainCollectBar(String account){
        CollectBar collectBar = new CollectBar();
        collectBar.setUser_account(account);
        collectBarModel.selectData(collectBarModel.DATACOLLECTBAR_SELECT_TWO, collectBar, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == SUCCESS){
                        List<Bar> collectBars = gson.fromJson(a.substring(3),new TypeToken<List<Bar>>()
                        {}.getType());
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_CollectBar),0,(Serializable) collectBars);
                    }else {

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void addCollectBar(String account,String pbid){
        CollectBar collectBar = new CollectBar();
        collectBar.setCache_account(account);
        collectBar.setUser_account(this.account);
        collectBar.setPb_one_id(pbid);
        collectBarModel.addData(collectBarModel.DATACOLLECTBAR_ADD_ONE, collectBar, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    a = response.body().string();
                    if (Integer.valueOf(a) == SUCCESS){
                        collectList.add(0,collectBar);
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void removeCollectBar(String account,String pbid){
        CollectBar collectBar = new CollectBar();
        collectBar.setCache_account(account);
        collectBar.setUser_account(this.account);
        collectBar.setPb_one_id(pbid);
        collectBarModel.deleteData(collectBarModel.DATACOLLECTBAR_DELETE_ONE, collectBar, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    a = response.body().string();
                    if (Integer.valueOf(a) == SUCCESS){
                        collectList.removeIf(collectList -> collectList.getPb_one_id().equals(collectBar.getPb_one_id()));
                        List<Bar> cacheBars = new ArrayList<>();
                        Bar bar = new Bar();
                        bar.setPb_one_id(pbid);
                        cacheBars.add(bar);
                        SpbBroadcast.sendReceiver(MyApplication.getContext(),InValues.send(R.string.Bcr_add_CollectBar),2,(Serializable)cacheBars);
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public boolean determineCollect(String pbid){
        if (collectList != null && collectList.stream().anyMatch(collectList -> collectList.getPb_one_id().equals(pbid))){
            return true;
        }else {
            return false;
        }
    }

    public int obtainCollectNum(){
        if (collectList != null){
            return collectList.size();
        }else {
            return 0;
        }
    }
}
