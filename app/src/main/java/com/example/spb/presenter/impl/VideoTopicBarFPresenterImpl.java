package com.example.spb.presenter.impl;

import android.app.Activity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.adapter.PostVideoAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.common.RequestListJson;
import com.example.spb.entity.Bar;
import com.example.spb.model.implA.PostBarModelImpl;
import com.example.spb.model.inter.PostBarModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IVideoTopicBarFPresenter;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.Component.ResponseToast;
import com.example.spb.view.activity.TopicBarPage;
import com.example.spb.view.inter.IVideoTopicBarFView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class VideoTopicBarFPresenterImpl extends BasePresenter<IVideoTopicBarFView> implements IVideoTopicBarFPresenter {

    private final PostBarModel postBarModel;
    private final TopicBarPage topicBarPage;
    private PostVideoAdapter postVideoAdapter;
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

    public VideoTopicBarFPresenterImpl(Activity activity) {
        this.topicBarPage = (TopicBarPage) activity;
        postBarModel = new PostBarModelImpl();
    }

    public void addNewTopicList(List<Bar> b, RecyclerView recyclerView, boolean fun) {
        if (postVideoAdapter == null || fun) {
            postVideoAdapter = new PostVideoAdapter(topicBarPage, b);
            postVideoAdapter.setNowTopicId(gettName());
            recyclerView.setAdapter(postVideoAdapter);
            ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
            recyclerView.startLayoutAnimation();
        } else {
            postVideoAdapter.addMorePostBar(b);
        }
    }

    public void deleteBarData(String id) {
        postVideoAdapter.deleteBar(id);
    }

    public void obtainNewTopicBar() {
        Bar cacheBar = new Bar();
        cacheBar.setPb_topic(gettName());
        cacheBar.setPb_date(getCacheDate());
        postBarModel.queryVideoTopicBarListForDate(getCacheDate(), gettName(), new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<Bar> requestListJson = new Gson().fromJson(value, new TypeToken<RequestListJson<Bar>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestListJson.getResultCode())) {
                        List<Bar> newBars = requestListJson.getDataList();
                        if (newBars != null && newBars.size() != 0) {
                            newBars.remove(0);
                            SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_newtopicvideo)
                                    , 1, tName, (Serializable) newBars);
                        }
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void refreshThumb(int num, String pbId) {
        if (postVideoAdapter != null) {
            postVideoAdapter.refreshLikeItem(num, pbId);
        }
    }

    public void refreshComment(int num) {
        if (postVideoAdapter != null) {
            postVideoAdapter.refreshCommentItem(num);
        }
    }

    public void refreshNowComment(int num) {
        if (postVideoAdapter != null) {
            postVideoAdapter.refreshNowCommentItem(num);
        }
    }
}
