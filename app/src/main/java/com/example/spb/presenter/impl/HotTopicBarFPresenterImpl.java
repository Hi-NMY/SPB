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
import com.example.spb.presenter.inter.IHotTopicBarFPresenter;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.Component.ResponseToast;
import com.example.spb.view.activity.TopicBarPage;
import com.example.spb.view.inter.IHotTopicBarFView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class HotTopicBarFPresenterImpl extends BasePresenter<IHotTopicBarFView> implements IHotTopicBarFPresenter {

    private final PostBarModel barModel;
    private final TopicBarPage topicBarPage;
    private TopicBarAdapter topicBarAdapter;
    private String tName;
    private int cacheNum;

    public int getCacheNum() {
        return cacheNum;
    }

    public void setCacheNum(int cacheNum) {
        this.cacheNum = cacheNum;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String gettName() {
        return tName;
    }

    public HotTopicBarFPresenterImpl(TopicBarPage t) {
        this.topicBarPage = t;
        barModel = new PostBarModelImpl();
    }

    public void addHotTopicList(List<Bar> b, RecyclerView recyclerView, boolean fun) {
        if (topicBarAdapter == null || fun) {
            topicBarAdapter = new TopicBarAdapter(topicBarPage, b);
            topicBarAdapter.setNowTopicId(gettName());
            recyclerView.setAdapter(topicBarAdapter);
            ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
            recyclerView.startLayoutAnimation();
        } else {
            //获取更多
            topicBarAdapter.addMoreTopicBar(b);
        }
    }

    public void deleteBarData(String id) {
        topicBarAdapter.deleteBar(id);
    }

    public void obtainMoreHotTopicBar() {
        barModel.queryNoVideoTopicBarListForThumbNum(String.valueOf(getCacheNum()), gettName(), new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<Bar> requestListJson = new Gson().fromJson(value, new TypeToken<RequestListJson<Bar>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestListJson.getResultCode())) {
                        requestListJson.getDataList().remove(0);
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_hottopicbar)
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
