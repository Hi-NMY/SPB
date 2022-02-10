package com.example.spb.presenter.otherimpl;

import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.common.RequestCode;
import com.example.spb.common.RequestListJson;
import com.example.spb.entity.Diary;
import com.example.spb.model.implA.DiaryModelImpl;
import com.example.spb.model.inter.DiaryModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.Component.ResponseToast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class DataDiaryPresenter {

    private final DiaryModel diaryModel;
    private final String account;
    private final Gson gson;

    public List<Diary> diaryList;

    public DataDiaryPresenter(String userAccount) {
        account = userAccount;
        diaryModel = new DiaryModelImpl();
        gson = new Gson();
        initDate();
    }

    public void initDate() {
        diaryModel.queryDiary(account, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<Diary> requestList = gson.fromJson(value, new TypeToken<RequestListJson<Diary>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestList.getResultCode())) {
                        diaryList = requestList.getDataList();
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_Diary), 0, (Serializable) diaryList);
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void addNewDiary(Diary diary, File file) {
        diary.setUser_account(account);
        diaryModel.addDiary(diary, file, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestCode requestCode = gson.fromJson(value, RequestCode.class);
                    if (ResponseToast.toToast(requestCode)) {
                        initDate();
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_Diary), 2, null);
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void removeDiary(int diaryId) {
        diaryModel.deleteDiary(account, String.valueOf(diaryId), new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestCode requestCode = gson.fromJson(value, RequestCode.class);
                    if (ResponseToast.toToast(requestCode)) {
                        diaryList.removeIf(diaryList -> diaryList.getId() == diaryId);
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }
}
