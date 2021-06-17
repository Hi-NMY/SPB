package com.example.spb.presenter.otherimpl;

import android.os.Handler;
import com.example.spb.entity.Bar;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.BarModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.littlefun.SendHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class DataPostBarPresenter {

    private SpbModelBasicInter barModel;
    private String user_account;
    private Gson gson;
    public List<Bar> bars;

    public DataPostBarPresenter(String user_account) {
        this.user_account = user_account;
        barModel = new BarModelImpl();
        gson = new Gson();
        obtainNewBar(null);
    }

    public void obtainNewBar(Handler handler){
        Bar bar = new Bar();
        bar.setPb_date("1");
        barModel.selectData(barModel.DATABAR_SELECT_ONE, bar, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (a.substring(0,3).equals("200")){
                        bars = gson.fromJson(a.substring(3),new TypeToken<List<Bar>>(){}.getType());
                        if (handler != null){
                            handler.sendMessage(SendHandler.setMessage(0,null));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void obtainAttentionBar(){

    }

    public void obtainUserBar(){

    }

    public interface OnCallBack{
        void callBack(List<Bar> bars);
    }
}
