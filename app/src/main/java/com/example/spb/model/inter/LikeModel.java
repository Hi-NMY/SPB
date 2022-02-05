package com.example.spb.model.inter;

import com.example.spb.presenter.callback.MyCallBack;

/**
 * @author nmy
 * @title: LikeModel
 * @date 2022-01-30 15:00
 */
public interface LikeModel {

    void queryLike(String userAccount, MyCallBack callBack);

    void addLike(String pbId, String userAccount, String cacheAccount, MyCallBack callBack);

    void deleteLike(String pbId, String userAccount, MyCallBack callBack);
}