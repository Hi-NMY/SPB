package com.example.spb.presenter.impl;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.adapter.TopicBarAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.common.RequestListJson;
import com.example.spb.entity.Bar;
import com.example.spb.model.implA.PostBarModelImpl;
import com.example.spb.model.inter.PostBarModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.INewTopicBarFPresenter;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.Component.ResponseToast;
import com.example.spb.view.activity.TopicBarPage;
import com.example.spb.view.inter.INewTopicBarFView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class NewTopicBarFPresenterImpl extends BasePresenter<INewTopicBarFView> implements INewTopicBarFPresenter {

    private final PostBarModel barModel;
    private final TopicBarPage topicBarPage;
    private TopicBarAdapter topicBarAdapter;
    private String tName;
    private String cacheDate = "";

    public String getCacheDate() {
        return cacheDate;
    }

    public void setCacheDate(String cacheDate) {
        this.cacheDate = cacheDate;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String gettName() {
        return tName;
    }

    public NewTopicBarFPresenterImpl(TopicBarPage t) {
        this.topicBarPage = t;
        barModel = new PostBarModelImpl();
    }

    public void addNewTopicList(List<Bar> b, RecyclerView recyclerView, boolean fun) {
        if (topicBarAdapter == null || fun) {
            topicBarAdapter = new TopicBarAdapter(topicBarPage, b);
            topicBarAdapter.setNowTopicId(gettName());
            recyclerView.setAdapter(topicBarAdapter);
            ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
            recyclerView.startLayoutAnimation();
        } else {
            topicBarAdapter.addMoreTopicBar(b);
        }
    }

    public void deleteBarData(String id) {
        topicBarAdapter.deleteBar(id);
    }

    public void obtainNewTopicBar() {
        Bar cacheBar = new Bar();
        cacheBar.setPb_topic(gettName());
        cacheBar.setPb_date(getCacheDate());
        barModel.queryNoVideoTopicBarListForDate(getCacheDate(), gettName(), new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<Bar> requestListJson = new Gson().fromJson(value, new TypeToken<RequestListJson<Bar>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestListJson.getResultCode())) {
                        requestListJson.getDataList().remove(0);
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_newtopicbar)
                                , 1, tName, (Serializable) requestListJson.getDataList());
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void stopVoice() {
        if (topicBarAdapter != null) {
            topicBarAdapter.refreshNoewVoice(-1);
        }
    }

    public void refreshThumb(int num, String pbId) {
        if (topicBarAdapter != null) {
            topicBarAdapter.refreshLikeItem(num, pbId);
        }
    }

    public void refreshComment(int num) {
        if (topicBarAdapter != null) {
            topicBarAdapter.refreshCommentItem(num);
        }
    }

    public void refreshNowComment(int num) {
        if (topicBarAdapter != null) {
            topicBarAdapter.refreshNowCommentItem(num);
        }
    }
}
