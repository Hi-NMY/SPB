package com.example.spb.model.inter;

import com.example.spb.entity.Diary;
import com.example.spb.presenter.callback.MyCallBack;

import java.io.File;

/**
 * @author nmy
 * @title: DiaryModel
 * @date 2022-01-30 14:55
 */
public interface DiaryModel {

    void queryDiary(String userAccount, MyCallBack callBack);

    void addDiary(Diary diary, File file, MyCallBack callBack);

    void deleteDiary(String userAccount, String id, MyCallBack callBack);

}
