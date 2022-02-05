package com.example.spb.model.implA;

import com.example.spb.R;
import com.example.spb.entity.Diary;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.model.inter.DiaryModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.InValues;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;

/**
 * @author nmy
 * @title: DiaryModelImpl
 * @date 2022-01-30 14:56
 */
public class DiaryModelImpl extends SpbModelAbstrate implements DiaryModel {
    @Override
    public void queryDiary(String userAccount, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .build();
        sendHttp(InValues.send(R.string.queryDiary), requestBody, callBack);
    }

    @Override
    public void addDiary(Diary diary, File file, MyCallBack callBack) {
        builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("user_account", diary.getUser_account())
                .addFormDataPart("dia_date", diary.getDia_date())
                .addFormDataPart("dia_message", diary.getDia_message())
                .addFormDataPart("dia_weather", String.valueOf(diary.getDia_weather()));
        if (file != null) {
            builder.addFormDataPart("file", file.getName(), RequestBody.Companion.create(file,
                    MediaType.Companion.parse("image/png")));
        }
        requestBody = builder.build();
        sendHttp(InValues.send(R.string.addDiary), requestBody, callBack);
    }

    @Override
    public void deleteDiary(String userAccount, String id, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .add("id", id)
                .build();
        sendHttp(InValues.send(R.string.deleteDiary), requestBody, callBack);
    }
}
