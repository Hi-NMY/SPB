package com.example.spb.presenter.impl;

import android.widget.Toast;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Bar;
import com.example.spb.entity.Topic;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.BarModelImpl;
import com.example.spb.model.impl.TopicModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.ITopicBarPageAPresenter;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.example.spb.view.Component.MyToastClass;
import com.example.spb.view.inter.ITopicBarPageAView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class TopicBarPageAPresenterImpl extends BasePresenter<ITopicBarPageAView> implements ITopicBarPageAPresenter {

    public boolean attentionKey = false;
    private SpbModelBasicInter topicModel;
    private SpbModelBasicInter barModel;
    private Bar cacheBar = null;
    private int hotBarsNum = 0;
    private String newBarsDate = null;
    private String topiCName;

    public void setTopiCName(String topiCName) {
        this.topiCName = topiCName;
    }

    public TopicBarPageAPresenterImpl() {
        topicModel = new TopicModelImpl();
        barModel = new BarModelImpl();
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

    public void obtainTopicInfo(Topic t,Topic cache,OnReturn onReturn) {
        if (cache != null){
            onReturn.onReturn(cache);
        }else {
            topicModel.selectData(topicModel.DATATOPIC_SELECT_FOUR, t, new MyCallBack() {
                @Override
                public void onSuccess(@NotNull Response response) {
                    try {
                        String a = response.body().string();
                        if (Integer.valueOf(a.substring(0,3)) == 200){
                            List<Topic> topics = new Gson().fromJson(a.substring(3),new TypeToken<List<Topic>>()
                            {}.getType());
                            onReturn.onReturn(topics.get(0));
                        }else {

                        }
                    } catch (IOException e) {

                    }
                }

                @Override
                public void onError(int t) {

                }
            });
        }
    }

    public void obtainTopicBarList(Topic topic,boolean fun,StopRefresh stopRefresh){
        cacheBar = new Bar();
        cacheBar.setPb_topic(topic.getTopic_name());
        if (fun){
            cacheBar.setPb_date("1");
            cacheBar.setPb_article("null");
        }else {
            cacheBar.setPb_date(newBarsDate);
            cacheBar.setPb_article(String.valueOf(hotBarsNum));
        }

        barModel.selectData(barModel.DATABAR_SELECT_TWO, cacheBar, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == 200){
                        List<Bar> hotBars = new Gson().fromJson(a.substring(3),new TypeToken<List<Bar>>()
                        {}.getType());
                        if (hotBars != null && hotBars.size() != 0){
                            hotBarsNum = hotBars.get(hotBars.size() - 1).getPb_thumb_num();
                            Thread.sleep(100);
                            if (cacheBar.getPb_article().equals("null")){
                                SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_hottopicbar)
                                        ,0,topiCName,(Serializable)hotBars);
                            }else {
                                SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_hottopicbar)
                                        ,1,topiCName,(Serializable)hotBars);
                            }
                        }
                        if (stopRefresh != null){
                            stopRefresh.stop();
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
        barModel.selectData(barModel.DATABAR_SELECT_THREE, cacheBar, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == 200){
                        List<Bar> newBars = new Gson().fromJson(a.substring(3),new TypeToken<List<Bar>>()
                        {}.getType());
                        if (newBars != null && newBars.size() != 0){
                            newBarsDate = newBars.get(newBars.size() - 1).getPb_date();
                            Thread.sleep(100);
                            if (cacheBar.getPb_date().equals("1")){
                                SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_newtopicbar)
                                        ,0,topiCName,(Serializable)newBars);
                            }else {
                                SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_newtopicbar)
                                        ,1,topiCName,(Serializable)newBars);
                            }
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

    public interface OnReturn{
        void onReturn(Topic t);
    }

    public interface StopRefresh{
        void stop();
    }
}
