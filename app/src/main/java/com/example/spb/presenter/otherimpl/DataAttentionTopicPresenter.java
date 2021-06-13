package com.example.spb.presenter.otherimpl;

import com.example.spb.entity.Topic;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.AttentionTopicModelImpl;
import com.example.spb.model.impl.TopicModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.littlefun.MyDateClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DataAttentionTopicPresenter {

    private static int SUCCESS = 200;

    private SpbModelBasicInter attentionTopicModel;
    private SpbModelBasicInter topicModel;
    public static List<Topic> attentionTopicList;
    public static List<Topic> topics;
    private static String account;
    private String a;
    private Gson gson;

    public DataAttentionTopicPresenter(String user_account) {
        account = user_account;
        attentionTopicModel = new AttentionTopicModelImpl();
        topicModel = new TopicModelImpl();
        gson = new Gson();
        initDate();
        obtainRandomTopic(null);
    }

    public void initDate(){
        Topic attentionTopic = new Topic();
        attentionTopic.setTopic_date(MyDateClass.showNowDate());
        attentionTopic.setTopic_name(account);
        attentionTopicModel.selectData(SpbModelBasicInter.DATEATTENTIONTOPIC_SELECT_ONE, attentionTopic, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == SUCCESS){
                        attentionTopicList = gson.fromJson(a.substring(3),new TypeToken<List<Topic>>()
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

    public void obtainRandomTopic(HotTopic hotTopic){
        topicModel.selectData(SpbModelBasicInter.DATATOPIC_SELECT_THREE, null, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == SUCCESS){
                        topics = gson.fromJson(a.substring(3),new TypeToken<List<Topic>>()
                        {}.getType());
                        if (hotTopic != null){
                            hotTopic.onReturn();
                        }
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

    public interface HotTopic{
        void onReturn();
    }
}
