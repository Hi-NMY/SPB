package com.example.spb.presenter.impl;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Looper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.adapter.LocationAdapter;
import com.example.spb.adapter.NewBarImageAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Bar;
import com.example.spb.entity.ImageDouble;
import com.example.spb.entity.LocationGps;
import com.example.spb.entity.Topic;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.BarModelImpl;
import com.example.spb.model.impl.TopicModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.ISendNewBarPageAPresenter;
import com.example.spb.presenter.littlefun.*;
import com.example.spb.view.activity.SendNewBarPage;
import com.example.spb.view.inter.ISendNewBarPageAView;
import com.example.spb.xserver.SpbLocationServer;
import com.example.spb.xserver.SpbSearchLocation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.entity.LocalMedia;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SendNewBarPageAPresenterImpl extends BasePresenter<ISendNewBarPageAView> implements ISendNewBarPageAPresenter {

    private SpbModelBasicInter topicModel;
    private SpbModelBasicInter barModel;
    public List<ImageDouble> barImage;
    public List<Topic> topics;
    private StringBuffer topicZ;
    public String path = null;
    public String voicePath = "";
    public List<Topic> hotTopics;
    public List<Topic> searchTopics;
    public Gson gson;
    public Topic topic;
    public NewBarImageAdapter newBarImageAdapter;
    public LocationAdapter locationAdapter;
    public MediaRecorder mediaRecorder;
    private VoiceObtain voiceObtain;
    private float totalProgress = 100;
    private float currentProgress;
    private ProgressRunable progressRunable;
    private boolean voiceEndKey = false;
    private MediaPlayer player;
    private boolean voicePlayerKey = false;
    private DownTime downTime;
    public SpbLocationServer spbLocationServer;
    private List<LocationGps> locationGpsS;
    private int obtainInterval = 500;
    public String locationName = "";
    public String newBarTxt = "";
    public Bar newBar;
    private SpbSearchLocation spbSearchLocation;

    public SendNewBarPageAPresenterImpl() {
        topicModel = new TopicModelImpl();
        barModel = new BarModelImpl();
        gson = new Gson();
        hotTopics = new ArrayList<>();
        searchTopics = new ArrayList<>();
        topics = new ArrayList<>();
        newBar = new Bar();
        obtainHotTopic();
        if (barImage == null){
            barImage = new ArrayList<>();
        }
    }
    public void sendNewMessage(){
        newBar.setUser_account(ObtainUserShared.getUserAccount());
        newBar.setPb_voice(voicePath);
        newBar.setPb_topic(topicZ == null ? "":String.valueOf(topicZ));
        newBar.setPb_one_id(ObtainUserShared.getUserAccount());
        newBar.setPb_location(locationName);
        newBar.setPb_image_url(String.valueOf(MyResolve.OutDoubleImage(barImage)));
        newBar.setPb_article(newBarTxt);
        barModel.addData(barModel.DATABAR_ADD_ONE, newBar, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    switch (Integer.valueOf(a.substring(0,3))) {
                        case 200:
                            try {
                                newBar = MyResolve.InBar(newBar,a.substring(3));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            getView().response(null,getView().SUCCESS_BAR);
                            SharedPreferences sharedPreferences = MySharedPreferences.getShared(InValues.send(R.string.Shared_userBar_Num));
                            SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_userBar_Num));
                            editor.putInt(InValues.send(R.string.userBar_num),sharedPreferences.getInt(InValues.send(R.string.userBar_num),0) + 1);
                            editor.apply();
                            SharedPreferences.Editor e = MySharedPreferences.saveShared(InValues.send(R.string.Shared_sign_task));
                            e.putInt(InValues.send(R.string.sign_task_bar),1);
                            e.apply();
                            break;
                        default:
                            getView().response(null,getView().ERROR_BAR);
                            break;
                    }
                } catch (IOException e) {
                    getView().response(null,getView().ERROR_BAR);
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void setString(String s){
        newBarTxt = s;
    }

    public void setLoc(String s){
        locationName = s;
    }

    public void obtainImage(List<LocalMedia> result){
        for (LocalMedia media : result) {
            ImageDouble imageDouble = new ImageDouble();
            if (media.isCut() && media.isCompressed()){
                imageDouble.setMinPath(media.getCompressPath());
                imageDouble.setMaxPath(media.getCutPath());
                barImage.add(imageDouble);
            }else {

            }
        }
    }

    public void setImageList(Activity activity, LinearLayoutManager linearLayoutManager, RecyclerView recyclerView){
        newBarImageAdapter = new NewBarImageAdapter(activity, barImage, new NewBarImageAdapter.RemoveImg() {
            @Override
            public void removeImg(int option) {
                removeImage(option);
                newBarImageAdapter.removeImage(option);
            }
        });
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(newBarImageAdapter);
    }

    public void removeImage(int option){
        barImage.remove(option);
        getView().changeIcon(getView().HAVEIMAGE);
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

    public boolean startVoice(){
        mediaRecorder = new MediaRecorder();
        voiceObtain = new VoiceObtain(mediaRecorder);
        voiceEndKey = false;
        progressRunable = new ProgressRunable();
        progressRunable.start();
        return voiceObtain.startVoice();
    }

    public String oneTime;
    public void endVoice(String s){
        oneTime = s;
        path = voiceObtain.stopVoice();
        voiceEndKey = true;
    }

    public void deleteVoice(){
        path = null;
        voicePath = null;
        currentProgress = 0;
    }

    public void setVoice(){
        voicePath = path;
    }

    private int timeString = 0;
    public void startPlayer(int time){
        timeString = time;
        player = new MediaPlayer();
        try {
            player.setDataSource(path);
            player.prepare();
            player.start();
            voicePlayerKey = false;
            downTime = new DownTime();
            downTime.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopPlayer(){
        if (player != null){
            player.release();
            voicePlayerKey = true;
        }
    }

    class ProgressRunable extends Thread {
        @Override
        public void run() {
            currentProgress = 0;
            while (currentProgress < totalProgress && !voiceEndKey) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (currentProgress < totalProgress && !voiceEndKey) {
                    currentProgress += 2.0;
                    getView().response(currentProgress,getView().RESPONSE_THREE);
                }
            }
            if (currentProgress == 100){
                getView().response(null,getView().RESPONSE_FOUR);
            }
        }
    }

    class DownTime extends Thread {
        @Override
        public void run() {
            while (timeString != 0 && !voicePlayerKey) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (timeString != 0 && !voicePlayerKey) {
                    timeString = timeString - 1;
                    getView().response(timeString, getView().RESPONSE_FIVE);
                }
            }
            if (timeString == 0) {
                getView().response(null, getView().RESPONSE_SIX);
            }
        }
    }

}


