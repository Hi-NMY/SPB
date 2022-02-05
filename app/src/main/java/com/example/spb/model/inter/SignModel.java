package com.example.spb.model.inter;

import com.example.spb.presenter.callback.MyCallBack;

/**
 * @author nmy
 * @title: SignModel
 * @date 2022-01-30 15:05
 */
public interface SignModel {

    void queryUserSign(String userAccount, MyCallBack callBack);

    void queryUserBadge(String userAccount, MyCallBack callBack);

    void updateSignDay(String userAccount, MyCallBack callBack);

    void updateSignRight(String userAccount, MyCallBack callBack);

    void updateSignDayAndRight(String userAccount, MyCallBack callBack);

    void updateSignDayAndRightAndCoin(String userAccount, String signDay, String coin, MyCallBack callBack);

    void updateSignCoin(String userAccount, String coin, MyCallBack callBack);

    void updateSignStarBadge(String userAccount, String starBadge, MyCallBack callBack);

    void updateSignLikeBadge(String userAccount, String likeBadge, MyCallBack callBack);

    void updateSignTaskBadge(String userAccount, String taskBadge, MyCallBack callBack);

}
