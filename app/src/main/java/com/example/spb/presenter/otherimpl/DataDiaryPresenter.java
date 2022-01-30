package com.example.spb.presenter.otherimpl;

import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.entity.Diary;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.DiaryModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class DataDiaryPresenter {

    private static int SUCCESS = 200;

    private SpbModelBasicInter diaryModel;
    public List<Diary> diaryList;
    private String account;
    private String a;
    private Gson gson;

    public DataDiaryPresenter(String user_account) {
        account = user_account;
        diaryModel = new DiaryModelImpl();
        gson = new Gson();
        initDate();
    }

    public void initDate() {
        Diary diary = new Diary();
        diary.setCacheAccount(account);
        diaryModel.selectData(SpbModelBasicInter.DATADIARY_SELECT_ONE, diary, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == SUCCESS){
                        diaryList = gson.fromJson(a.substring(3),new TypeToken<List<Diary>>()
                        {}.getType());
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_Diary),0,(Serializable) diaryList);
                    }else {

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void addNewDiary(Diary diary){
        diaryModel.addData(diaryModel.DATADIARY_ADD_ONE, diary, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a) == 200){
                        initDate();
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_Diary),2,null);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void removeDiary(Diary diary){
        diary.setCacheAccount(account);
        diaryModel.deleteData(diaryModel.DATADIARY_DELETE_ONE, diary, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a) == 200){
                        diaryList.removeIf(diaryList -> diaryList.getId() == diary.getId());
                      // SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_Diary),1,null);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }
}
