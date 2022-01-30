package com.example.spb.model.impl;

import com.example.spb.R;
import com.example.spb.entity.Diary;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.InValues;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;

public class DiaryModelImpl extends SpbModelAbstrate implements SpbModelBasicInter<Diary> {

    @Override
    public void addData(int fun, Diary data, MyCallBack callBack) {
        switch (fun){
            case DATADIARY_ADD_ONE:
                builder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("fun",String.valueOf(fun))
                        .addFormDataPart("user_account",data.getCacheAccount())
                        .addFormDataPart("dia_date",data.getDia_date())
                        .addFormDataPart("dia_message",data.getDia_message())
                        .addFormDataPart("dia_weather",String.valueOf(data.getDia_weather()));
                if (data.getDia_image() !=null && !data.getDia_image().equals("")){
                    File file = new File(data.getDia_image());
                    builder.addFormDataPart("dia_image",file.getName(), RequestBody.Companion.create(file
                            , MediaType.Companion.parse("image/png")));
                }
                requestBody = builder.build();
                sendHttp(InValues.send(R.string.Diary),requestBody,callBack);
                break;
        }
    }

    @Override
    public void selectData(int fun, Diary data, MyCallBack callBack) {
        switch (fun){
            case DATADIARY_SELECT_ONE:
                requestBody = new FormBody.Builder()
                        .add("user_account", data.getCacheAccount())
                        .add("fun", String.valueOf(fun))
                        .build();
                sendHttp(InValues.send(R.string.Diary),requestBody,callBack);
                break;
        }
    }

    @Override
    public void updateData(int fun, Diary data, MyCallBack callBack) {

    }

    @Override
    public void deleteData(int fun, Diary data, MyCallBack callBack) {
        switch (fun){
            case DATADIARY_DELETE_ONE:
                requestBody = new FormBody.Builder()
                        .add("user_account", data.getCacheAccount())
                        .add("id", String.valueOf(data.getId()))
                        .add("fun", String.valueOf(fun))
                        .build();
                sendHttp(InValues.send(R.string.Diary),requestBody,callBack);
                break;
        }
    }
}
