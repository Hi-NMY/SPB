package com.example.spb.presenter.otherimpl;

import com.example.spb.entity.AttentionTopic;
import com.example.spb.entity.Diary;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.AttentionTopicModelImpl;
import com.example.spb.model.impl.DiaryModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.littlefun.MyDateClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

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

    private void initDate() {
        Diary diary = new Diary();
        diary.setDia_date(MyDateClass.showNowDate());
        diary.setDia_message(account);
        diaryModel.selectData(SpbModelBasicInter.DATADIARY_SELECT_ONE, diary, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == SUCCESS){
                        diaryList = gson.fromJson(a.substring(3),new TypeToken<List<Diary>>()
                        {}.getType());
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
}
