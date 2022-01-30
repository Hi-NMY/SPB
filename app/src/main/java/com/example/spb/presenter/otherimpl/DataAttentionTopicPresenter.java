package com.example.spb.presenter.otherimpl;

import com.example.spb.entity.Topic;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.AttentionTopicModelImpl;
import com.example.spb.model.impl.TopicModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataAttentionTopicPresenter {

    private static final int SUCCESS = 200;

    private final SpbModelBasicInter<Topic> attentionTopicModel;
    private final SpbModelBasicInter<Topic> topicModel;
    public static List<Topic> attentionTopicList;
    public static List<Topic> topics;
    public static List<Integer> attentionNum;
    private int topicId = 0;
    private static String account;
    private String a;
    private final Gson gson;

    public DataAttentionTopicPresenter(String userAccount) {
        account = userAccount;
        attentionNum = new ArrayList<>();
        attentionTopicModel = new AttentionTopicModelImpl();
        topicModel = new TopicModelImpl();
        gson = new Gson();
        initDate(null);
        obtainRandomTopic(null);
    }

    public void initDate(ReturnTopic returnTopic){
        Topic attentionTopic = new Topic();
        attentionTopic.setTopic_name(account);
        attentionTopicModel.selectData(SpbModelBasicInter.DATAATTENTIONTOPIC_SELECT_ONE, attentionTopic, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    a = response.body().string();
                    if (Integer.parseInt(a.substring(0,3)) == SUCCESS){
                        attentionTopicList = gson.fromJson(a.substring(3),new TypeToken<List<Topic>>()
                        {}.getType());
                        if (attentionTopicList != null && attentionTopicList.size() != 0){
                            attentionNum = new ArrayList<>();
                            for (Topic t:attentionTopicList){
                                attentionNum.add(t.getId());
                            }
                        }
                        if (returnTopic != null){
                            returnTopic.onReturn();
                        }
                    }
                } catch (Exception ignored) {

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
                    if (Integer.parseInt(a.substring(0,3)) == SUCCESS){
                        topics = gson.fromJson(a.substring(3),new TypeToken<List<Topic>>()
                        {}.getType());
                        if (hotTopic != null){
                            hotTopic.onReturn();
                        }
                    }
                } catch (Exception ignored) {

                }
            }
            @Override
            public void onError(int t) {

            }
        });
    }

    public void addAttentionTopic(Topic topic,Topic t,ReturnTopic returnTopic){
        topicId = t.getId();
        attentionTopicModel.addData(SpbModelBasicInter.DATAATTENTIONTOPIC_ADD_ONE, t, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if ("200".equals(a)) {
                        if (topicId != 0){
                            attentionNum.add(topicId);
                            attentionTopicList.add(0,topic);
                            returnTopic.onReturn();
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
        Topic c = t;
        c.setTopic_slogan("true");
        topicModel.updateData(SpbModelBasicInter.DATATOPIC_UPDATE_ONE, c,null);
    }

    public void removeAttentionTopic(Topic topic,ReturnTopic returnTopic){
        topicId = topic.getId();
        attentionTopicModel.deleteData(SpbModelBasicInter.DATAATTENTIONTOPIC_DELETE_ONE, topic, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if ("200".equals(a)){
                        attentionNum.removeIf(attentionNum -> attentionNum == topicId);
                        attentionTopicList.removeIf(attentionTopicList -> attentionTopicList.getId() == topicId);
                        returnTopic.onReturn();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int t) {

            }
        });
        Topic c = topic;
        c.setTopic_slogan("false");
        topicModel.updateData(SpbModelBasicInter.DATATOPIC_UPDATE_ONE, c,null);
    }

    public boolean determineAttention(int id){
        return attentionNum != null && attentionNum.stream().anyMatch(attentionNum -> attentionNum.equals(id));
    }

    public Topic determineTopic(Topic t){
        Topic c = attentionTopicList.stream().filter(attentionTopicList -> attentionTopicList.getTopic_name().equals(t.getTopic_name())).findAny().orElse(null);
        if (c == null){
            c = topics.stream().filter(attentionTopicList -> attentionTopicList.getTopic_name().equals(t.getTopic_name())).findAny().orElse(null);
        }
        return c;
    }

    public int obtainAttentionTopicNum(){
        if (attentionTopicList == null){
            return 0;
        }else {{
            return attentionTopicList.size();
        }}
    }

    public interface HotTopic{
        void onReturn();
    }

    public interface ReturnTopic{
        void onReturn();
    }
}
