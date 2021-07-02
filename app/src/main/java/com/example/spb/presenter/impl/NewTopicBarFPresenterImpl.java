package com.example.spb.presenter.impl;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.adapter.TopicBarAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Bar;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.BarModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.INewTopicBarFPresenter;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.example.spb.view.Component.MyToastClass;
import com.example.spb.view.activity.TopicBarPage;
import com.example.spb.view.inter.INewTopicBarFView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class NewTopicBarFPresenterImpl extends BasePresenter<INewTopicBarFView> implements INewTopicBarFPresenter {

    private SpbModelBasicInter barModel;
    private TopicBarPage topicBarPage;
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
        barModel = new BarModelImpl();
    }

    public void addNewTopicList(List<Bar> b, RecyclerView recyclerView, boolean fun){
        if (topicBarAdapter == null || fun){
            topicBarAdapter = new TopicBarAdapter(topicBarPage,b);
            topicBarAdapter.setNowTopicId(gettName());
            recyclerView.setAdapter(topicBarAdapter);
            ((DefaultItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
            recyclerView.startLayoutAnimation();
        }else {
            topicBarAdapter.addMoreTopicBar(b);
        }
    }

    public void deleteBarData(String id){
        topicBarAdapter.deleteBar(id);
    }

    public void obtainNewTopicBar(){
        Bar cacheBar = new Bar();
        cacheBar.setPb_topic(tName);
        cacheBar.setPb_date(cacheDate);
        barModel.selectData(barModel.DATABAR_SELECT_THREE, cacheBar, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == 200){
                        List<Bar> newBars = new Gson().fromJson(a.substring(3),new TypeToken<List<Bar>>()
                        {}.getType());
                        if (newBars != null && newBars.size() != 0){
                            newBars.remove(0);
                            SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_newtopicbar)
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
        if (topicBarAdapter != null){
            topicBarAdapter.refreshLikeItem(num,pbId);
        }
    }
}
