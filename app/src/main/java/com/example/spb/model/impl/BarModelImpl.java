package com.example.spb.model.impl;

import com.example.spb.R;
import com.example.spb.entity.Bar;
import com.example.spb.entity.ImageDouble;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.MyResolve;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;
import java.util.List;

public class BarModelImpl extends SpbModelAbstrate implements SpbModelBasicInter<Bar> {
    @Override
    public void addData(int fun, Bar data, MyCallBack callBack) {
        switch (fun){
            case DATABAR_ADD_ONE:
                MultipartBody.Builder b = new MultipartBody.Builder();
                b.setType(MultipartBody.FORM)
                        .addFormDataPart("fun",String.valueOf(fun))
                        .addFormDataPart("user_account",data.getUser_account())
                        .addFormDataPart("pb_one_id",data.getPb_one_id())
                        .addFormDataPart("pb_article",data.getPb_article())
                        .addFormDataPart("pb_topic",data.getPb_topic())
                        .addFormDataPart("pb_location",data.getPb_location());
                if (data.getPb_image_url() != null && !data.getPb_image_url().equals("")){
                    List<ImageDouble> i = MyResolve.InDoubleImage(data.getPb_image_url());
                    for (ImageDouble imageDouble : i){
                        File file = new File(imageDouble.getMinPath());
                        File file1 = new File(imageDouble.getMaxPath());
                        b.addFormDataPart("img[]",file.getName(), RequestBody.create(MediaType.parse("image/png"),file))
                                .addFormDataPart("imgA[]",file1.getName(),RequestBody.create(MediaType.parse("image/png"),file1));
                    }
                }
                if (data.getPb_voice() != null && !data.getPb_voice().equals("")){
                    File file = new File(data.getPb_voice());
                    b.addFormDataPart("voice",file.getName(),RequestBody.create(MediaType.parse("audio/m4a"),file));
                }
                requestBody = b.build();
                sendHttp(InValues.send(R.string.SendNewPostBar),requestBody,callBack);
                break;
        }
    }

    @Override
    public void selectData(int fun, Bar data, MyCallBack callBack) {
        switch (fun){
            case DATABAR_SELECT_ONE:
                requestBody = new FormBody.Builder()
                        .add("fun", String.valueOf(fun))
                        .add("pb_date", data.getPb_date())
                        .build();
                sendHttp(InValues.send(R.string.PostBar),requestBody,callBack);
                break;
            case DATABAR_SELECT_TWO:
                requestBody = new FormBody.Builder()
                        .add("fun", String.valueOf(fun))
                        .add("pb_topic",data.getPb_topic())
                        .add("pb_thumb_num",data.getPb_article())
                        .build();
                sendHttp(InValues.send(R.string.PostBar),requestBody,callBack);
                break;
            case DATABAR_SELECT_THREE:
                requestBody = new FormBody.Builder()
                        .add("fun", String.valueOf(fun))
                        .add("pb_topic",data.getPb_topic())
                        .add("pb_date",data.getPb_date())
                        .build();
                sendHttp(InValues.send(R.string.PostBar),requestBody,callBack);
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
