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
import com.example.spb.common.RequestEntityJson;
import com.example.spb.common.RequestListJson;
import com.example.spb.entity.Bar;
import com.example.spb.entity.ImageDouble;
import com.example.spb.entity.LocationGps;
import com.example.spb.entity.Topic;
import com.example.spb.model.implA.PostBarModelImpl;
import com.example.spb.model.implA.TopicModelImpl;
import com.example.spb.model.inter.PostBarModel;
import com.example.spb.model.inter.TopicModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.ISendNewBarPageAPresenter;
import com.example.spb.presenter.utils.*;
import com.example.spb.view.Component.ResponseToast;
import com.example.spb.view.activity.SendNewBarPage;
import com.example.spb.view.inter.ISendNewBarPageAView;
import com.example.spb.xserver.SpbLocationServer;
import com.example.spb.xserver.SpbSearchLocation;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.entity.LocalMedia;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SendNewBarPageAPresenterImpl extends BasePresenter<ISendNewBarPageAView> implements ISendNewBarPageAPresenter {

    private final TopicModel topicModel;
    private final PostBarModel barModel;
    public List<ImageDouble> barImage;
    public List<Topic> topics;
    private StringBuffer topicZ;
    public String path = null;
    public String voicePath = "";
    public List<String> hotTopics;
    public List<String> searchTopics;
    public Gson gson;
    public Topic topic;
    public NewBarImageAdapter newBarImageAdapter;
    public LocationAdapter locationAdapter;
    public MediaRecorder mediaRecorder;
    private VoiceObtain voiceObtain;
    private float currentProgress;
    private boolean voiceEndKey = false;
    private MediaPlayer player;
    private boolean voicePlayerKey = false;
    public SpbLocationServer spbLocationServer;
    private List<LocationGps> locationGpsS;
    private int obtainInterval = 500;
    public String locationName = "";
    public String newBarTxt = "";
    private Bar newBar;


    public SendNewBarPageAPresenterImpl() {
        topicModel = new TopicModelImpl();
        barModel = new PostBarModelImpl();
        gson = new Gson();
        newBar = new Bar();
        hotTopics = new ArrayList<>();
        searchTopics = new ArrayList<>();
        topics = new ArrayList<>();
        obtainHotTopic();
        if (barImage == null) {
            barImage = new ArrayList<>();
        }
    }

    public void sendNewMessage() {
        newBar.setUser_account(ObtainUserShared.getUserAccount());
        newBar.setPb_topic(topicZ == null ? "" : String.valueOf(topicZ));
        newBar.setPb_location(locationName);
        newBar.setPb_article(newBarTxt);
        List<File> imgList = new ArrayList<>();
        File voiceFile = null;
        for (ImageDouble imageDouble : barImage) {
            imgList.add(new File(imageDouble.getMaxPath()));
        }
        if (!"".equals(voicePath)) {
            voiceFile = new File(voicePath);
        }
        barModel.addBar(newBar, imgList, voiceFile, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestEntityJson<Bar> requestEntityJson = new Gson().fromJson(value, new TypeToken<RequestEntityJson<Bar>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestEntityJson.getResultCode())) {
                        newBar = requestEntityJson.getData();
                        getView().response(null, SendNewBarPage.SUCCESS_BAR);
                        changeShare();
                    } else {
                        getView().response(null, SendNewBarPage.ERROR_BAR);
                    }
                }
            }

            @Override
            public void onError(int t) {
                getView().response(null, SendNewBarPage.ERROR_BAR);
            }
        });
    }

    private void changeShare() {
        SharedPreferences sharedPreferences = MySharedPreferences.getShared(InValues.send(R.string.Shared_userBar_Num));
        SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_userBar_Num));
        editor.putInt(InValues.send(R.string.userBar_num), sharedPreferences.getInt(InValues.send(R.string.userBar_num), 0) + 1);
        editor.apply();
        SharedPreferences.Editor e = MySharedPreferences.saveShared(InValues.send(R.string.Shared_sign_task));
        e.putInt(InValues.send(R.string.sign_task_bar), 1);
        e.apply();
    }

    public void setString(String s) {
        newBarTxt = s;
    }

    public void setLoc(String s) {
        locationName = s;
    }

    public void obtainImage(List<LocalMedia> result) {
        for (LocalMedia media : result) {
            ImageDouble imageDouble = new ImageDouble();
            if (media.isCut() && media.isCompressed()) {
                imageDouble.setMinPath(media.getCompressPath());
                imageDouble.setMaxPath(media.getCutPath());
                barImage.add(imageDouble);
            } else {

            }
        }
    }

    public void setImageList(Activity activity, LinearLayoutManager linearLayoutManager, RecyclerView recyclerView) {
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

    public void removeImage(int option) {
        barImage.remove(option);
        getView().changeIcon(SendNewBarPage.HAVEIMAGE);
    }


    public void obtainHotTopic() {
        topicModel.queryTopicNameList(new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<String> requestListJson = new Gson().fromJson(value, new TypeToken<RequestListJson<String>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestListJson.getResultCode())) {
                        hotTopics = requestListJson.getDataList();
                        getView().response(null, SendNewBarPage.RESPONSE_ONE);
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void searchTopic(String search) {
        topicModel.querySearchTopicNameList(search, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<String> requestListJson = new Gson().fromJson(value, new TypeToken<RequestListJson<String>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestListJson.getResultCode())) {
                        searchTopics = requestListJson.getDataList();
                        if (searchTopics != null && searchTopics.stream().noneMatch(searchTopics -> searchTopics.equals("#" + search))) {
                            searchTopics.add(0, "新增 #" + search);
                        }
                        getView().response(null, SendNewBarPage.RESPONSE_TWO);
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void addTopic(String topicName) {
        if (topics != null && topics.stream().noneMatch(topic -> topic.getTopic_name().equals(topicName))) {
            Topic topic = new Topic();
            topic.setTopic_name(topicName);
            topics.add(topic);
            topicZ = MyResolve.OutTopic(topics);
        }
    }

    public void removeTopic(int position) {
        topics.remove(position);
    }

    public void initLocationList(Activity activity, LinearLayoutManager linearLayoutManager, RecyclerView recyclerView) {
        spbLocationServer = new SpbLocationServer(MyApplication.getContext());
        obtainInterval = 1000;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                try {
                    while ((spbLocationServer == null || spbLocationServer.obtainListener().locationGpsList == null) && obtainInterval < 2500) {
                        spbLocationServer.startGps();
                        Thread.sleep(obtainInterval);
                        if (spbLocationServer.obtainListener().locType == 62) {
                            break;
                        } else {
                            if (spbLocationServer.obtainListener().locationGpsList != null) {
                                locationGpsS = spbLocationServer.obtainListener().locationGpsList;
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        setLocationList(activity, linearLayoutManager, recyclerView);
                                    }
                                });
                                getView().response(null, SendNewBarPage.RESPONSE_SEVEN);
                            } else {
                                obtainInterval += 500;
                            }
                        }
                    }
                    if (((spbLocationServer == null || spbLocationServer.obtainListener().locationGpsList == null) && obtainInterval >= 2500)
                            || spbLocationServer.obtainListener().locType == 62) {
                        getView().response(null, SendNewBarPage.RESPONSE_EIGHT);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Looper.loop();
            }
        }).start();
    }

    public void searchLocation(String search, Activity activity, LinearLayoutManager linearLayoutManager, RecyclerView recyclerView) {
        if (spbLocationServer.obtainNowCity() != null) {
            SpbSearchLocation spbSearchLocation = new SpbSearchLocation(spbLocationServer.obtainNowCity(), search);
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

    public void setLocationList(Activity activity, LinearLayoutManager linearLayoutManager, RecyclerView recyclerView) {
        locationAdapter = new LocationAdapter(locationGpsS, activity);
        if (recyclerView.getLayoutManager() == null) {
            recyclerView.setLayoutManager(linearLayoutManager);
        }
        recyclerView.setAdapter(locationAdapter);
    }

    public void onStopGps() {
        if (spbLocationServer != null) {
            spbLocationServer.stopGps();
        }
    }

    public boolean startVoice() {
        mediaRecorder = new MediaRecorder();
        voiceObtain = new VoiceObtain(mediaRecorder);
        voiceEndKey = false;
        ProgressRunable progressRunable = new ProgressRunable();
        progressRunable.start();
        return voiceObtain.startVoice();
    }

    public String oneTime;

    public void endVoice(String s) {
        oneTime = s;
        path = voiceObtain.stopVoice();
        voiceEndKey = true;
    }

    public void deleteVoice() {
        path = null;
        voicePath = null;
        currentProgress = 0;
    }

    public void setVoice() {
        voicePath = path;
    }

    private int timeString = 0;

    public void startPlayer(int time) {
        timeString = time;
        player = new MediaPlayer();
        try {
            player.setDataSource(path);
            player.prepare();
            player.start();
            voicePlayerKey = false;
            DownTime downTime = new DownTime();
            downTime.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopPlayer() {
        if (player != null) {
            player.release();
            voicePlayerKey = true;
        }
    }

    class ProgressRunable extends Thread {
        @Override
        public void run() {
            currentProgress = 0;
            float totalProgress = 100;
            while (currentProgress < totalProgress && !voiceEndKey) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (currentProgress < totalProgress && !voiceEndKey) {
                    currentProgress += 2.0;
                    getView().response(currentProgress, SendNewBarPage.RESPONSE_THREE);
                }
            }
            if (currentProgress == 100) {
                getView().response(null, SendNewBarPage.RESPONSE_FOUR);
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
                    getView().response(timeString, SendNewBarPage.RESPONSE_FIVE);
                }
            }
            if (timeString == 0) {
                getView().response(null, SendNewBarPage.RESPONSE_SIX);
            }
        }
    }

}


