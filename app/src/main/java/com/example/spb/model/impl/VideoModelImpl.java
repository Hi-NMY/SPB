package com.example.spb.model.impl;

import com.example.spb.R;
import com.example.spb.entity.Bar;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.littlefun.InValues;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;

public class VideoModelImpl extends SpbModelAbstrate implements SpbModelBasicInter<Bar> {
    @Override
    public void addData(int fun, Bar data, MyCallBack callBack) {
        switch (fun){
            case DATAVIDEO_ADD_ONE:
                try {
                    MultipartBody.Builder b = new MultipartBody.Builder();
                    b.setType(MultipartBody.FORM)
                            .addFormDataPart("fun",String.valueOf(fun))
                            .addFormDataPart("user_account",data.getUser_account())
                            .addFormDataPart("pb_one_id",data.getPb_one_id())
                            .addFormDataPart("pb_article",data.getPb_article())
                            .addFormDataPart("pb_topic",data.getPb_topic())
                            .addFormDataPart("pb_location",data.getPb_location());

                    if (data.getPb_video() != null && !data.getPb_video().equals("")){
                        MediaType mediaType = MediaType.Companion.parse("video/mp4");
                        File file = new File(data.getPb_video());
                        RequestBody fileBody = RequestBody.Companion.create(file,mediaType);
                        b.addFormDataPart("video",file.getName(),fileBody);
                    }

                    if (data.getPb_image_url() != null && !data.getPb_image_url().equals("")){
                        MediaType mediaType = MediaType.Companion.parse("image/png");
                        File file = new File(data.getPb_image_url());
                        RequestBody fileBody = RequestBody.Companion.create(file,mediaType);
                        b.addFormDataPart("img",file.getName(),fileBody);
                    }
                    requestBody = b.build();
                    sendHttp(InValues.send(R.string.SendNewVideo),requestBody,callBack);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void selectData(int fun, Bar data, MyCallBack callBack) {
        switch (fun){
            case DATAVIDEO_SELECT_ONE:
                requestBody = new FormBody.Builder()
                        .add("fun", String.valueOf(fun))
                        .add("pb_date", data.getPb_date())
                        .build();
                sendHttp(InValues.send(R.string.Video),requestBody,callBack);
                break;
            case DATAVIDEO_SELECT_THREE:
                requestBody = new FormBody.Builder()
                        .add("fun", String.valueOf(fun))
                        .add("pb_topic",data.getPb_topic())
                        .add("pb_date",data.getPb_date())
                        .build();
                sendHttp(InValues.send(R.string.Video),requestBody,callBack);
                break;
            case DATAVIDEO_SELECT_FOUR:
                requestBody = new FormBody.Builder()
                        .add("fun", String.valueOf(fun))
                        .add("user_account",data.getUser_account())
                        .add("pb_date",data.getPb_date())
                        .build();
                sendHttp(InValues.send(R.string.Video),requestBody,callBack);
                break;
        }
    }

    @Override
    public void updateData(int fun, Bar data, MyCallBack callBack) {

    }

    @Override
    public void deleteData(int fun, Bar data, MyCallBack callBack) {

    }
}
