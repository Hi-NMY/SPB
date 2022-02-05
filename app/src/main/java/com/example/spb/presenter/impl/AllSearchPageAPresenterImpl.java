package com.example.spb.presenter.impl;

import android.app.Activity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.adapter.AllSearchUserAdapter;
import com.example.spb.adapter.PostBarAdapter;
import com.example.spb.adapter.RandomTopicAdapter;
import com.example.spb.base.BasePresenter;
import com.example.spb.common.RequestListJson;
import com.example.spb.entity.Bar;
import com.example.spb.entity.Dto.UserDto;
import com.example.spb.entity.Topic;
import com.example.spb.model.implA.PostBarModelImpl;
import com.example.spb.model.implA.TopicModelImpl;
import com.example.spb.model.implA.UserModelImpl;
import com.example.spb.model.inter.PostBarModel;
import com.example.spb.model.inter.TopicModel;
import com.example.spb.model.inter.UserModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IAllSearchPageAPresenter;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.view.Component.ResponseToast;
import com.example.spb.view.activity.AllSearchPage;
import com.example.spb.view.inter.IAllSearchPageAView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AllSearchPageAPresenterImpl extends BasePresenter<IAllSearchPageAView> implements IAllSearchPageAPresenter {

    private final PostBarModel barModel;
    private final TopicModel topicModel;
    private final UserModel userModel;
    private final Activity activity;

    public AllSearchPageAPresenterImpl(Activity activity) {
        this.activity = activity;
        barModel = new PostBarModelImpl();
        userModel = new UserModelImpl();
        topicModel = new TopicModelImpl();
    }

    public void obtainSearch(String search) {
        barModel.queryNoVideoSearchBarListForDate(search, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null && isAttachView()) {
                    RequestListJson<Bar> requestListJson = new Gson().fromJson(value, new TypeToken<RequestListJson<Bar>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestListJson.getResultCode())) {
                        getView().response(requestListJson.getDataList(), AllSearchPage.BAR_SUCCESS);
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
        topicModel.querySearchTopicFullList(search, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null && isAttachView()) {
                    RequestListJson<Topic> requestListJson = new Gson().fromJson(value, new TypeToken<RequestListJson<Topic>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestListJson.getResultCode())) {
                        getView().response(requestListJson.getDataList(), AllSearchPage.TOPIC_SUCCESS);
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
        userModel.querySearchUser(search, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null && isAttachView()) {
                    RequestListJson<UserDto> requestListJson = new Gson().fromJson(value, new TypeToken<RequestListJson<UserDto>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestListJson.getResultCode())) {
                        getView().response(requestListJson.getDataList(), AllSearchPage.USER_SUCCESS);
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void setBarAdapter(List<Bar> bars, GridLayoutManager gridLayoutManager, RecyclerView recyclerView) {
        PostBarAdapter postBarAdapter = new PostBarAdapter(activity, bars);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(postBarAdapter);
        ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.startLayoutAnimation();
    }

    public void setTopicAdapter(List<Topic> topics, GridLayoutManager gridLayoutManager, RecyclerView recyclerView) {
        RandomTopicAdapter randomTopicAdapter = new RandomTopicAdapter(activity, topics);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(randomTopicAdapter);
        ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.startLayoutAnimation();
    }

    public void setUserAdapter(List<UserDto> userDtos, LinearLayoutManager linearLayoutManager, RecyclerView recyclerView) {
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        AllSearchUserAdapter allSearchUserAdapter = new AllSearchUserAdapter(userDtos, activity);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(allSearchUserAdapter);
        ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.startLayoutAnimation();
    }
}
