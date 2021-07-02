package com.example.spb.presenter.impl;

import android.app.Activity;
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
import com.example.spb.presenter.inter.IHotTopicBarFPresenter;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.example.spb.view.Component.MyToastClass;
import com.example.spb.view.activity.TopicBarPage;
import com.example.spb.view.inter.IHotTopicBarFView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class HotTopicBarFPresenterImpl extends BasePresenter<IHotTopicBarFView> implements IHotTopicBarFPresenter {

    private SpbModelBasicInter barModel;
    private TopicBarPage topicBarPage;
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
        barModel = new BarModelImpl();
    }

    public void addHotTopicList(List<Bar> b, RecyclerView recyclerView,boolean fun){
        if (topicBarAdapter == null || fun){
            topicBarAdapter = new TopicBarAdapter(topicBarPage,b);
            topicBarAdapter.setNowTopicId(gettName());
            recyclerView.setAdapter(topicBarAdapter);
            ((DefaultItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
            recyclerView.startLayoutAnimation();
        }else {
            //获取更多
            topicBarAdapter.addMoreTopicBar(b);
        }
    }

    public void deleteBarData(String id){
        topicBarAdapter.deleteBar(id);
    }

    public void obtainMoreHotTopicBar(){
        Bar cacheBar = new Bar();
        cacheBar.setPb_topic(tName);
        cacheBar.setPb_article(String.valueOf(cacheNum));
        barModel.selectData(barModel.DATABAR_SELECT_TWO, cacheBar, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == 200){
                        List<Bar> hotBars = new Gson().fromJson(a.substring(3),new TypeToken<List<Bar>>()
                        {}.getType());
                        if (hotBars != null && hotBars.size() != 0){
                            SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_hottopicbar)
                                    ,1,tName,(Serializable)hotBars);
                        }
                    }else {

                    }
                } catch (Exception e) {
                    MyToastClass.ShowToast(MyApplication.getContext(),"此话题还没有帖子");
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
