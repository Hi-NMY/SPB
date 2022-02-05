package com.example.spb.model.implA;

import com.example.spb.R;
import com.example.spb.entity.Bar;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.model.inter.PostBarModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.InValues;
import okhttp3.FormBody;
import okhttp3.MultipartBody;

import java.io.File;
import java.util.List;

/**
 * @author nmy
 * @title: PostBarModelImpl
 * @date 2022-01-30 15:04
 */
public class PostBarModelImpl extends SpbModelAbstrate implements PostBarModel {
    @Override
    public void queryNoVideoBarListForDate(String pbDate, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("pb_date", pbDate)
                .build();
        sendHttp(InValues.send(R.string.queryNoVideoBarListForDate), requestBody, callBack);
    }

    @Override
    public void queryNoVideoTopicBarListForThumbNum(String thumbNum, String topicName, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("pb_topic", topicName)
                .add("pb_thumb_num", thumbNum)
                .build();
        sendHttp(InValues.send(R.string.queryNoVideoTopicBarListForThumbNum), requestBody, callBack);
    }

    @Override
    public void queryNoVideoTopicBarListForDate(String pbDate, String topicName, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("pb_topic", topicName)
                .add("pb_date", pbDate)
                .build();
        sendHttp(InValues.send(R.string.queryNoVideoTopicBarListForDate), requestBody, callBack);
    }

    @Override
    public void queryNoVideoUserBarListForDate(String userAccount, String pbDate, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .add("pb_date", pbDate)
                .build();
        sendHttp(InValues.send(R.string.queryNoVideoUserBarListForDate), requestBody, callBack);
    }

    @Override
    public void queryUserBarCount(String userAccount, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .build();
        sendHttp(InValues.send(R.string.queryUserBarCount), requestBody, callBack);
    }

    @Override
    public void queryNoVideoFollowBarListForDate(String userAccount, String pbDate, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .add("pb_date", pbDate)
                .build();
        sendHttp(InValues.send(R.string.queryNoVideoFollowBarListForDate), requestBody, callBack);
    }

    @Override
    public void queryBarDetatilForPbid(String pbid, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("pb_one_id", pbid)
                .build();
        sendHttp(InValues.send(R.string.queryBarDetatilForPbid), requestBody, callBack);
    }

    @Override
    public void queryUserBarLikeCount(String userAccount, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .build();
        sendHttp(InValues.send(R.string.queryUserBarLikeCount), requestBody, callBack);
    }

    @Override
    public void queryNoVideoSearchBarListForDate(String searchArt, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("search_art", searchArt)
                .build();
        sendHttp(InValues.send(R.string.queryNoVideoSearchBarListForDate), requestBody, callBack);
    }

    @Override
    public void queryVideoBarListForDate(String pbDate, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("pb_date", pbDate)
                .build();
        sendHttp(InValues.send(R.string.queryVideoBarListForDate), requestBody, callBack);
    }

    @Override
    public void queryVideoTopicBarListForDate(String pbDate, String topicName, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("pb_topic", topicName)
                .add("pb_date", pbDate)
                .build();
        sendHttp(InValues.send(R.string.queryVideoTopicBarListForDate), requestBody, callBack);
    }

    @Override
    public void queryVideoUserBarListForDate(String userAccount, String pbDate, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .add("pb_date", pbDate)
                .build();
        sendHttp(InValues.send(R.string.queryVideoUserBarListForDate), requestBody, callBack);
    }

    @Override
    public void deleteBar(String pbid, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("pb_one_id", pbid)
                .build();
        sendHttp(InValues.send(R.string.deleteBar), requestBody, callBack);
    }

    @Override
    public void addBar(Bar bar, List<File> image, File voice, File video, MyCallBack callBack) {
        MultipartBody.Builder b = new MultipartBody.Builder();
        b.setType(MultipartBody.FORM)
                .addFormDataPart("user_account", bar.getUser_account())
                .addFormDataPart("pb_article", bar.getPb_article())
                .addFormDataPart("pb_topic", bar.getPb_topic())
                .addFormDataPart("pb_location", bar.getPb_location());


        requestBody = b.build();
        sendHttp(InValues.send(R.string.addBar), requestBody, callBack);
    }
}
