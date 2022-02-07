package com.example.spb.presenter.impl;

import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.common.RequestEntityJson;
import com.example.spb.common.RequestListJson;
import com.example.spb.entity.Bar;
import com.example.spb.entity.Topic;
import com.example.spb.model.implA.PostBarModelImpl;
import com.example.spb.model.implA.TopicModelImpl;
import com.example.spb.model.inter.PostBarModel;
import com.example.spb.model.inter.TopicModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.ITopicBarPageAPresenter;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.Component.ResponseToast;
import com.example.spb.view.inter.ITopicBarPageAView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class TopicBarPageAPresenterImpl extends BasePresenter<ITopicBarPageAView> implements ITopicBarPageAPresenter {

    public boolean attentionKey = false;
    private final TopicModel topicModel;
    private final PostBarModel barModel;
    private String topiCName;

    public void setTopiCName(String topiCName) {
        this.topiCName = topiCName;
    }

    public TopicBarPageAPresenterImpl() {
        topicModel = new TopicModelImpl();
        barModel = new PostBarModelImpl();
    }

    public void returnAttentionKey(boolean b) {
        this.attentionKey = b;
    }

    public String obtainBlurImg(String path) {
        StringBuffer stringBuffer = new StringBuffer(path);
        stringBuffer.insert(path.length() - 4, "A");
        return String.valueOf(stringBuffer);
    }

    public Topic addAttentionAccount(String user_account, Topic topic) {
        topic.setTopic_slogan(user_account);
        return topic;
    }

    public void obtainTopicInfo(Topic t, Topic cache, OnReturn onReturn) {
        if (cache != null) {
            onReturn.onReturn(cache);
        } else {
            topicModel.queryTopicFull(t.getTopic_name(), new MyCallBack() {
                @Override
                public void onSuccess(@NotNull Response response) {
                    String value = DataVerificationTool.isEmpty(response);
                    if (value != null) {
                        RequestEntityJson<Topic> requestEntityJson = new Gson().fromJson(value, new TypeToken<RequestEntityJson<Topic>>() {
                        }.getType());
                        if (ResponseToast.toToast(requestEntityJson.getResultCode())) {
                            onReturn.onReturn(requestEntityJson.getData());
                        }
                    }
                }

                @Override
                public void onError(int t) {

                }
            });
        }
    }

    public void obtainTopicBarList(Topic topic, StopRefresh stopRefresh) {
        barModel.queryNoVideoTopicBarListForThumbNum("", topic.getTopic_name(), new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<Bar> requestListJson = new Gson().fromJson(value, new TypeToken<RequestListJson<Bar>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestListJson.getResultCode())) {
                        List<Bar> b = requestListJson.getDataList();
                        if (b != null && b.size() != 0) {
                            SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_hottopicbar)
                                    , 0, topiCName, (Serializable) b);
                        }
                        if (stopRefresh != null) {
                            stopRefresh.stop();
                        }
                    }
                }
            }

            @Override
            public void onError(int t) {
                getView().response(null, 0);
            }
        });
        barModel.queryNoVideoTopicBarListForDate("", topic.getTopic_name(), new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<Bar> requestListJson = new Gson().fromJson(value, new TypeToken<RequestListJson<Bar>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestListJson.getResultCode())) {
                        List<Bar> b = requestListJson.getDataList();
                        if (b != null && b.size() != 0) {
                            SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_newtopicbar)
                                    , 0, topiCName, (Serializable) b);
                        }
                        if (stopRefresh != null) {
                            stopRefresh.stop();
                        }
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
        barModel.queryVideoTopicBarListForDate("", topic.getTopic_name(), new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<Bar> requestListJson = new Gson().fromJson(value, new TypeToken<RequestListJson<Bar>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestListJson.getResultCode())) {
                        List<Bar> b = requestListJson.getDataList();
                        if (b != null && b.size() != 0) {
                            SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_newtopicvideo)
                                    , 0, topiCName, (Serializable) b);
                        }
                        if (stopRefresh != null) {
                            stopRefresh.stop();
                        }
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public interface OnReturn {
        void onReturn(Topic t);
    }

    public interface StopRefresh {
        void stop();
    }
}
