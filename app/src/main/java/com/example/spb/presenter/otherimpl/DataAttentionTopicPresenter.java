package com.example.spb.presenter.otherimpl;

import com.example.spb.common.RequestCode;
import com.example.spb.common.RequestListJson;
import com.example.spb.entity.Topic;
import com.example.spb.model.implA.AttentionModelImpl;
import com.example.spb.model.implA.TopicModelImpl;
import com.example.spb.model.inter.AttentionModel;
import com.example.spb.model.inter.TopicModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.view.Component.ResponseToast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DataAttentionTopicPresenter {

    private final AttentionModel attentionTopicModel;
    private final TopicModel topicModel;
    public List<Topic> attentionTopicList;
    public List<Topic> topics;
    public List<Integer> attentionNum;
    private int topicId = 0;
    private final String account;
    private final Gson gson;

    public DataAttentionTopicPresenter(String userAccount) {
        this.account = userAccount;
        attentionNum = new ArrayList<>();
        attentionTopicModel = new AttentionModelImpl();
        topicModel = new TopicModelImpl();
        gson = new Gson();
        initDate(null);
        obtainRandomTopic(null);
    }

    public void initDate(ReturnTopic returnTopic) {
        attentionTopicModel.queryAttentionTopic(account, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<Topic> requestListJson = gson.fromJson(value, new TypeToken<RequestListJson<Topic>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestListJson.getResultCode())) {
                        attentionTopicList = requestListJson.getDataList();
                        if (attentionTopicList != null && attentionTopicList.size() != 0) {
                            attentionNum = new ArrayList<>();
                            for (Topic t : attentionTopicList) {
                                attentionNum.add(t.getId());
                            }
                        }
                        if (returnTopic != null) {
                            returnTopic.onReturn();
                        }
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void obtainRandomTopic(HotTopic hotTopic) {
        topicModel.queryRundomTopicFullList(new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<Topic> requestListJson = gson.fromJson(value, new TypeToken<RequestListJson<Topic>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestListJson.getResultCode())) {
                        topics = requestListJson.getDataList();
                    }
                    if (hotTopic != null) {
                        hotTopic.onReturn();
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void addAttentionTopic(Topic topic, ReturnTopic returnTopic) {
        topicId = topic.getId();
        attentionTopicModel.addAttentionTopic(account, String.valueOf(topicId), topic.getTopic_name(), new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestCode requestCode = gson.fromJson(value, RequestCode.class);
                    if (ResponseToast.toToast(requestCode)) {
                        attentionNum.add(topicId);
                        attentionTopicList.add(0, topic);
                        returnTopic.onReturn();
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void removeAttentionTopic(Topic topic, ReturnTopic returnTopic) {
        topicId = topic.getId();
        attentionTopicModel.deleteAttentionTopicById(String.valueOf(topicId), account, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestCode requestCode = gson.fromJson(value, RequestCode.class);
                    if (ResponseToast.toToast(requestCode)) {
                        attentionNum.removeIf(attentionNum -> attentionNum == topicId);
                        attentionTopicList.removeIf(attentionTopicList -> attentionTopicList.getId() == topicId);
                        returnTopic.onReturn();
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public boolean determineAttention(int id) {
        return attentionNum != null && attentionNum.stream().anyMatch(attentionNum -> attentionNum.equals(id));
    }

    public Topic determineTopic(Topic t) {
        Topic c = attentionTopicList.stream().filter(attentionTopicList -> attentionTopicList.getTopic_name().equals(t.getTopic_name())).findAny().orElse(null);
        if (c == null) {
            c = topics.stream().filter(attentionTopicList -> attentionTopicList.getTopic_name().equals(t.getTopic_name())).findAny().orElse(null);
        }
        return c;
    }

    public int obtainAttentionTopicNum() {
        if (attentionTopicList == null) {
            return 0;
        } else {
            {
                return attentionTopicList.size();
            }
        }
    }

    public interface HotTopic {
        void onReturn();
    }

    public interface ReturnTopic {
        void onReturn();
    }
}
