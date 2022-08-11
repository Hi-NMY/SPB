package com.example.spb.model.implA;

import com.example.spb.R;
import com.example.spb.entity.Bar;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.model.inter.PostBarModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.InValues;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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
        StringBuffer stringBuffer = new StringBuffer("?");
        stringBuffer.append("&").append("pb_date").append("=").append(pbDate);
        sendHttp(InValues.send(R.string.queryNoVideoBarListForDate) + stringBuffer, GET, requestBody, callBack);
    }

    @Override
    public void queryNoVideoTopicBarListForThumbNum(String thumbNum, String topicName, MyCallBack callBack) {
        StringBuffer stringBuffer = new StringBuffer("?");
        stringBuffer.append("&").append("topic_name").append("=").append(topicName);
        stringBuffer.append("&").append("pb_thumb_num").append("=").append(thumbNum);
        sendHttp(InValues.send(R.string.queryNoVideoTopicBarListForThumbNum) + stringBuffer, GET, requestBody, callBack);
    }

    @Override
    public void queryNoVideoTopicBarListForDate(String pbDate, String topicName, MyCallBack callBack) {
        StringBuffer stringBuffer = new StringBuffer("?");
        stringBuffer.append("&").append("topic_name").append("=").append(topicName);
        stringBuffer.append("&").append("pb_date").append("=").append(pbDate);
        sendHttp(InValues.send(R.string.queryNoVideoTopicBarListForDate) + stringBuffer, GET, requestBody, callBack);
    }

    @Override
    public void queryNoVideoUserBarListForDate(String userAccount, String pbDate, MyCallBack callBack) {
        StringBuffer stringBuffer = new StringBuffer("?");
        stringBuffer.append("&").append("user_account").append("=").append(userAccount);
        stringBuffer.append("&").append("pb_date").append("=").append(pbDate);
        sendHttp(InValues.send(R.string.queryNoVideoUserBarListForDate) + stringBuffer, GET, requestBody, callBack);
    }

    @Override
    public void queryUserBarCount(String userAccount, MyCallBack callBack) {
        StringBuffer stringBuffer = new StringBuffer("?");
        stringBuffer.append("&").append("user_account").append("=").append(userAccount);
        sendHttp(InValues.send(R.string.queryUserBarCount) + stringBuffer, GET, requestBody, callBack);
    }

    @Override
    public void queryNoVideoFollowBarListForDate(String userAccount, String pbDate, MyCallBack callBack) {
        StringBuffer stringBuffer = new StringBuffer("?");
        stringBuffer.append("&").append("user_account").append("=").append(userAccount);
        stringBuffer.append("&").append("pb_date").append("=").append(pbDate);
        sendHttp(InValues.send(R.string.queryNoVideoFollowBarListForDate) + stringBuffer, GET, requestBody, callBack);
    }

    @Override
    public void queryBarDetatilForPbid(String pbid, MyCallBack callBack) {
        StringBuffer stringBuffer = new StringBuffer("?");
        stringBuffer.append("&").append("pb_one_id").append("=").append(pbid);
        sendHttp(InValues.send(R.string.queryBarDetatilForPbid) + stringBuffer, GET, requestBody, callBack);
    }

    @Override
    public void queryUserBarLikeCount(String userAccount, MyCallBack callBack) {
        StringBuffer stringBuffer = new StringBuffer("?");
        stringBuffer.append("&").append("user_account").append("=").append(userAccount);
        sendHttp(InValues.send(R.string.queryUserBarLikeCount) + stringBuffer, GET, requestBody, callBack);
    }

    @Override
    public void queryNoVideoSearchBarListForDate(String searchArt, MyCallBack callBack) {
        StringBuffer stringBuffer = new StringBuffer("?");
        stringBuffer.append("&").append("search_art").append("=").append(searchArt);
        sendHttp(InValues.send(R.string.queryNoVideoSearchBarListForDate) + stringBuffer, GET, requestBody, callBack);
    }

    @Override
    public void queryVideoBarListForDate(String pbDate, MyCallBack callBack) {
        StringBuffer stringBuffer = new StringBuffer("?");
        stringBuffer.append("&").append("pb_date").append("=").append(pbDate);
        sendHttp(InValues.send(R.string.queryVideoBarListForDate) + stringBuffer, GET, requestBody, callBack);
    }

    @Override
    public void queryVideoTopicBarListForDate(String pbDate, String topicName, MyCallBack callBack) {
        StringBuffer stringBuffer = new StringBuffer("?");
        stringBuffer.append("&").append("topic_name").append("=").append(topicName);
        stringBuffer.append("&").append("pb_date").append("=").append(pbDate);
        sendHttp(InValues.send(R.string.queryVideoTopicBarListForDate) + stringBuffer, GET, requestBody, callBack);
    }

    @Override
    public void queryVideoUserBarListForDate(String userAccount, String pbDate, MyCallBack callBack) {
        StringBuffer stringBuffer = new StringBuffer("?");
        stringBuffer.append("&").append("user_account").append("=").append(userAccount);
        stringBuffer.append("&").append("pb_date").append("=").append(pbDate);
        sendHttp(InValues.send(R.string.queryVideoUserBarListForDate) + stringBuffer, GET, requestBody, callBack);
    }

    @Override
    public void deleteBar(String pbid, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("pb_one_id", pbid)
                .build();
        sendHttp(InValues.send(R.string.deleteBar), POST, requestBody, callBack);
    }

    @Override
    public void addBar(Bar bar, List<File> image, File voice, MyCallBack callBack) {
        builder = setBarBuilder(bar);
        if (image != null && image.size() != 0) {
            for (File file : image) {
                builder.addFormDataPart("image_file", file.getName()
                        , RequestBody.Companion.create(file, MediaType.Companion.parse("image/png")));
            }
        }
        if (voice != null) {
            builder.addFormDataPart("voice_file", voice.getName()
                    , RequestBody.Companion.create(voice, MediaType.Companion.parse("audio/m4a")));
        }

        requestBody = builder.build();
        sendHttp(InValues.send(R.string.addBar), POST, requestBody, callBack);
    }

    @Override
    public void addBarVideo(Bar bar, File video, File videoImg, MyCallBack callBack) {
        builder = setBarBuilder(bar);
        if (video != null) {
            this.builder.addFormDataPart("video_file", video.getName()
                    , RequestBody.Companion.create(video, MediaType.Companion.parse("video/mp4")));
        }
        if (videoImg != null) {
            this.builder.addFormDataPart("video_image_file", videoImg.getName()
                    , RequestBody.Companion.create(videoImg, MediaType.Companion.parse("image/png")));
        }
        requestBody = this.builder.build();
        sendHttp(InValues.send(R.string.addBar), POST, requestBody, callBack);
    }

    private MultipartBody.Builder setBarBuilder(Bar bar) {
        return new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("user_account", bar.getUser_account())
                .addFormDataPart("pb_article", bar.getPb_article())
                .addFormDataPart("pb_topic", bar.getPb_topic())
                .addFormDataPart("pb_location", bar.getPb_location());
    }
}
