package com.example.spb.model.impl;

import com.example.spb.R;
import com.example.spb.entity.CollectBar;
import com.example.spb.entity.Diary;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.littlefun.InValues;
import okhttp3.FormBody;

public class DiaryModelImpl extends SpbModelAbstrate implements SpbModelBasicInter<Diary> {

    @Override
    public void addData(int fun, Diary data, MyCallBack callBack) {

    }

    @Override
    public void selectData(int fun, Diary data, MyCallBack callBack) {
        switch (fun){
            case DATADIARY_SELECT_ONE:
                requestBody = new FormBody.Builder()
                        .add("dia_date", data.getDia_date())
                        .add("user_account", data.getDia_message())
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

    }
}
