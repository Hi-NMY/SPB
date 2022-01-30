package com.example.spb.presenter.impl;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Looper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.adapter.LocationAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Bar;
import com.example.spb.entity.LocationGps;
import com.example.spb.entity.Topic;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.TopicModelImpl;
import com.example.spb.model.impl.VideoModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.ISendNewVideoAPresenter;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MyResolve;
import com.example.spb.presenter.utils.MySharedPreferences;
import com.example.spb.presenter.utils.ObtainUserShared;
import com.example.spb.view.inter.ISendNewVideoAView;
import com.example.spb.xserver.SpbLocationServer;
import com.example.spb.xserver.SpbSearchLocation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SendNewVideoAPresenterImpl extends BasePresenter<ISendNewVideoAView> implements ISendNewVideoAPresenter {

    private SpbModelBasicInter topicModel;
    private SpbModelBasicInter videoModel;
    private Gson gson;
    public List<Topic> topics;
    public Bar newBar;
    private BaseMVPActivity baseMVPActivity;
    public List<Topic> hotTopics;
    public List<Topic> searchTopics;
    private SpbLocationServer spbLocationServer;
    public String newVideoTxt = "";
    public String locationName = "";
    private int obtainInterval = 500;
    private List<LocationGps> locationGpsS;
    private LocationAdapter locationAdapter;
    private SpbSearchLocation spbSearchLocation;
    private Topic topic;
    private StringBuffer topicZ;
    private String videoPath = "";
    private String videoImagePath = "";

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getNewVideoTxt() {
        return newVideoTxt;
    }

    public void setNewVideoTxt(String newVideoTxt) {
        this.newVideoTxt = newVideoTxt;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getVideoImagePath() {
        return videoImagePath;
    }

    public void setVideoImagePath(String videoImagePath) {
        this.videoImagePath = videoImagePath;
    }

    public SendNewVideoAPresenterImpl(Activity activity) {
        this.baseMVPActivity = (BaseMVPActivity)activity;
        topicModel = new TopicModelImpl();
        videoModel = new VideoModelImpl();
        gson = new Gson();
        hotTopics = new ArrayList<>();
        searchTopics = new ArrayList<>();
        topics = new ArrayList<>();
        newBar = new Bar();
        obtainHotTopic();
    }

    public void obtainVideo(List<LocalMedia> result){
        for (LocalMedia media : result) {
            if (media.isCompressed()){
                setVideoPath(PictureFileUtils.getPath(MyApplication.getContext(),Uri.parse(media.getCompressPath())));
                getView().response(null,getView().VIDEO_OBTAIN);
            }else {
                setVideoPath(PictureFileUtils.getPath(MyApplication.getContext(),Uri.parse(media.getPath())));
                getView().response(null,getView().VIDEO_OBTAIN);
            }
        }
    }

    public void sendNewMessage(){
        newBar.setUser_account(ObtainUserShared.getUserAccount());
        newBar.setPb_video(getVideoPath()); //////写入Video地址
        newBar.setPb_image_url(getVideoImagePath()); /////写入Video预览图
        newBar.setPb_topic(topicZ == null ? "":String.valueOf(topicZ));
        newBar.setPb_one_id(ObtainUserShared.getUserAccount());
        newBar.setPb_location(getLocationName());
        newBar.setPb_article(getNewVideoTxt());
        videoModel.addData(videoModel.DATAVIDEO_ADD_ONE, newBar, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    switch (Integer.valueOf(a.substring(0,3))) {
                        case 200:
                            getView().response(null,getView().RESPONSE_SUCCESS);
                            SharedPreferences sharedPreferences = MySharedPreferences.getShared(InValues.send(R.string.Shared_userBar_Num));
                            SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_userBar_Num));
                            editor.putInt(InValues.send(R.string.userBar_num),sharedPreferences.getInt(InValues.send(R.string.userBar_num),0) + 1);
                            editor.apply();
                            SharedPreferences.Editor e = MySharedPreferences.saveShared(InValues.send(R.string.Shared_sign_task));
                            e.putInt(InValues.send(R.string.sign_task_bar),1);
                            e.apply();
                            break;
                    }
                } catch (IOException e) {
                    getView().response(null,getView().RESPONSE_SUCCESS);
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void obtainHotTopic(){
        topicModel.selectData(topicModel.DATATOPIC_SELECT_ONE, null, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    hotTopics = gson.fromJson(a.substring(3),new TypeToken<List<Topic>>()
                    {}.getType());
                    getView().response(null,getView().RESPONSE_ONE);
                } catch (IOException e) {

                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void addTopic(String topicName){
        if (topics != null && !topics.stream().anyMatch(topic -> topic.getTopic_name().equals(topicName))){
            Topic topic = new Topic();
            topic.setTopic_name(topicName);
            topics.add(topic);
            topicZ = MyResolve.OutTopic(topics);
        }
    }

    public void removeTopic(int position){
        topics.remove(position);
    }

    public void searchTopic(String search){
        if (topic == null){
            topic = new Topic();
        }
        topic.setTopic_name(search);
        topicModel.selectData(topicModel.DATATOPIC_SELECT_TWO, topic, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    searchTopics = gson.fromJson(a.substring(3),new TypeToken<List<Topic>>()
                    {}.getType());
                    if (searchTopics != null && (!searchTopics.stream()
                            .anyMatch(searchTopics -> searchTopics.getTopic_name().equals("#" + search)) || searchTopics.size() == 0)){
                        topic.setTopic_name("新增" + " #" + search);
                        searchTopics.add(0,topic);
                    }
                    getView().response(null,getView().RESPONSE_TWO);
                } catch (IOException e) {

                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void searchLocation(String search , Activity activity, LinearLayoutManager linearLayoutManager, RecyclerView recyclerView){
        if (spbLocationServer.obtainNowCity() != null){
            spbSearchLocation = new SpbSearchLocation(spbLocationServer.obtainNowCity(),search);
            spbSearchLocation.search(new SpbSearchLocation.OnReturn() {
                @Override
                public void onSuccess(List<LocationGps> locationGpsList) {
                    locationGpsS = locationGpsList;
                    setLocationList(activity, linearLayoutManager, recyclerView);
                }

                @Override
                public void onError() {

                }
            });
        }
    }

    public void initLocationList(Activity activity, LinearLayoutManager linearLayoutManager, RecyclerView recyclerView){
        spbLocationServer = new SpbLocationServer(MyApplication.getContext());
        obtainInterval = 1000;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    while ((spbLocationServer == null || spbLocationServer.obtainListener().locationGpsList == null) && obtainInterval < 2500){
                        spbLocationServer.startGps();
                        Thread.sleep(obtainInterval);
                        if (spbLocationServer.obtainListener().locType == 62){
                            break;
                        }else {
                            if (spbLocationServer.obtainListener().locationGpsList != null){
                                locationGpsS = spbLocationServer.obtainListener().locationGpsList;
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        setLocationList(activity, linearLayoutManager, recyclerView);
                                    }
                                });
                                getView().response(null,getView().RESPONSE_SEVEN);
                            }else {
                                obtainInterval += 500;
                            }
                        }
                    }
                    if (((spbLocationServer == null || spbLocationServer.obtainListener().locationGpsList == null) && obtainInterval >= 2500)
                            || spbLocationServer.obtainListener().locType == 62){
                        getView().response(null,getView().RESPONSE_EIGHT);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Looper.loop();
            }
        }).start();
    }

    public void setLocationList(Activity activity, LinearLayoutManager linearLayoutManager, RecyclerView recyclerView){
        locationAdapter = new LocationAdapter(locationGpsS,activity);
        if (recyclerView.getLayoutManager() == null){
            recyclerView.setLayoutManager(linearLayoutManager);
        }
        recyclerView.setAdapter(locationAdapter);
    }

    public void onStopGps(){
        if (spbLocationServer != null){
            spbLocationServer.stopGps();
        }
    }
}
