package com.example.spb.presenter.impl;

import android.app.Activity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.adapter.PostVideoAdapter;
import com.example.spb.adapter.TopicBarAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Bar;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.BarModelImpl;
import com.example.spb.model.impl.VideoModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IVideoTopicBarFPresenter;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.example.spb.view.activity.TopicBarPage;
import com.example.spb.view.inter.IVideoTopicBarFView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class VideoTopicBarFPresenterImpl extends BasePresenter<IVideoTopicBarFView> implements IVideoTopicBarFPresenter {

    private SpbModelBasicInter videoModel;
    private TopicBarPage topicBarPage;
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
        videoModel = new VideoModelImpl();
    }

    public void addNewTopicList(List<Bar> b, RecyclerView recyclerView, boolean fun){
        if (postVideoAdapter == null || fun){
            postVideoAdapter = new PostVideoAdapter(topicBarPage,b);
            postVideoAdapter.setNowTopicId(gettName());
            recyclerView.setAdapter(postVideoAdapter);
            ((DefaultItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
            recyclerView.startLayoutAnimation();
        }else {
            postVideoAdapter.addMorePostBar(b);
        }
    }

    public void deleteBarData(String id){
        postVideoAdapter.deleteBar(id);
    }

    public void obtainNewTopicBar(){
        Bar cacheBar = new Bar();
        cacheBar.setPb_topic(gettName());
        cacheBar.setPb_date(getCacheDate());
        videoModel.selectData(videoModel.DATAVIDEO_SELECT_THREE, cacheBar, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == 200){
                        List<Bar> newBars = new Gson().fromJson(a.substring(3),new TypeToken<List<Bar>>()
                        {}.getType());
                        if (newBars != null && newBars.size() != 0){
                            newBars.remove(0);
                            SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_newtopicvideo)
                                    ,1,tName,(Serializable)newBars);
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

    public void refreshThumb(int num,String pbId){
        if (postVideoAdapter != null){
            postVideoAdapter.refreshLikeItem(num,pbId);
        }
    }

    public void refreshComment(int num){
        if (postVideoAdapter != null){
            postVideoAdapter.refreshCommentItem(num);
        }
    }

    public void refreshNowComment(int num){
        if (postVideoAdapter != null){
            postVideoAdapter.refreshNowCommentItem(num);
        }
    }
}
