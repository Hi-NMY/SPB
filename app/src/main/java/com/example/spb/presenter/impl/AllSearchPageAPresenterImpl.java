package com.example.spb.presenter.impl;

import android.app.Activity;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.adapter.AllSearchUserAdapter;
import com.example.spb.adapter.PostBarAdapter;
import com.example.spb.adapter.RandomTopicAdapter;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Bar;
import com.example.spb.entity.Topic;
import com.example.spb.entity.User;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.AllSearchPageAModelImpl;
import com.example.spb.model.impl.BarModelImpl;
import com.example.spb.model.impl.TopicModelImpl;
import com.example.spb.model.impl.UserModelImpl;
import com.example.spb.model.inter.IAllSearchPageAModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IAllSearchPageAPresenter;
import com.example.spb.view.inter.IAllSearchPageAView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class AllSearchPageAPresenterImpl extends BasePresenter<IAllSearchPageAView> implements IAllSearchPageAPresenter {

    private SpbModelBasicInter barModel;
    private SpbModelBasicInter topicModel;
    private SpbModelBasicInter userModel;
    private RandomTopicAdapter randomTopicAdapter;
    private PostBarAdapter postBarAdapter;
    private AllSearchUserAdapter allSearchUserAdapter;
    private Activity activity;

    public AllSearchPageAPresenterImpl(Activity activity) {
        this.activity = activity;
        barModel = new BarModelImpl();
        userModel = new UserModelImpl();
        topicModel = new TopicModelImpl();
    }

    public void obtainSearch(String search){
        User user = new User();
        user.setUser_name(search);
        Topic topic = new Topic();
        topic.setTopic_name(search);
        Bar bar = new Bar();
        bar.setPb_article(search);
        barModel.selectData(barModel.DATABAR_SELECT_NINE, bar, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) throws IOException {
                String a = response.body().string();
                if (Integer.valueOf(a.substring(0,3)) == 200){
                    List<Bar> bars = new Gson().fromJson(a.substring(3),new TypeToken<List<Bar>>(){}.getType());
                    if (isAttachView()){
                        if (bars == null || bars.size() == 0){
                            getView().response(null,getView().BAR_SUCCESS);
                        }else {
                            getView().response(bars,getView().BAR_SUCCESS);
                        }
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
        topicModel.selectData(topicModel.DATATOPIC_SELECT_FIVE, topic, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) throws IOException  {
                String a = response.body().string();
                if (Integer.valueOf(a.substring(0,3)) == 200){
                    List<Topic> topics = new Gson().fromJson(a.substring(3),new TypeToken<List<Topic>>(){}.getType());
                    if (isAttachView()){
                        if (topics == null || topics.size() == 0){
                            getView().response(null,getView().TOPIC_SUCCESS);
                        }else {
                            getView().response(topics,getView().TOPIC_SUCCESS);
                        }
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
        userModel.selectData(userModel.DATAUSER_SELECT_FOUR, user, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) throws IOException  {
                String a = response.body().string();
                if (Integer.valueOf(a.substring(0,3)) == 200) {
                    List<User> users = new Gson().fromJson(a.substring(3), new TypeToken<List<User>>() {
                    }.getType());
                    if (isAttachView()) {
                        if (users == null || users.size() == 0){
                            getView().response(null, getView().USER_SUCCESS);
                        }else {
                            getView().response(users, getView().USER_SUCCESS);
                        }
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void setBarAdapter(List<Bar> bars, GridLayoutManager gridLayoutManager, RecyclerView recyclerView){
        postBarAdapter = new PostBarAdapter(activity,bars);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(postBarAdapter);
        ((DefaultItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.startLayoutAnimation();
    }

    public void setTopicAdapter(List<Topic> topics, GridLayoutManager gridLayoutManager, RecyclerView recyclerView){
        randomTopicAdapter = new RandomTopicAdapter(activity,topics);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(randomTopicAdapter);
        ((DefaultItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.startLayoutAnimation();
    }

    public void setUserAdapter(List<User> users, LinearLayoutManager linearLayoutManager, RecyclerView recyclerView){
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        allSearchUserAdapter = new AllSearchUserAdapter(users,activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(allSearchUserAdapter);
        ((DefaultItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.startLayoutAnimation();
    }
}
