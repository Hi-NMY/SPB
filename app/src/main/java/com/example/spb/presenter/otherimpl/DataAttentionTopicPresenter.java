package com.example.spb.presenter.otherimpl;

import com.example.spb.entity.AttentionTopic;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.model.impl.AttentionTopicModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.littlefun.MyDateClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class DataAttentionTopicPresenter {

    private static int SUCCESS = 200;

    private SpbModelBasicInter attentionTopicModel;
    public List<AttentionTopic> attentionTopics;
    private String account;
    private String a;
    private Gson gson;

    public DataAttentionTopicPresenter(String user_account) {
        account = user_account;
        attentionTopicModel = new AttentionTopicModelImpl();
        gson = new Gson();
        initDate();
    }

    public void initDate(){
        AttentionTopic attentionTopic = new AttentionTopic();
        attentionTopic.setTopic_date(MyDateClass.showNowDate());
        attentionTopic.setTopic_name(account);
        attentionTopicModel.selectData(SpbModelBasicInter.DATEATTENTIONTOPIC_SELECT_ONE, attentionTopic, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == SUCCESS){
                        attentionTopics = gson.fromJson(a.substring(3),new TypeToken<List<AttentionTopic>>()
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

}
