package com.example.spb.presenter.impl;

import com.example.spb.R;
import com.example.spb.base.BasePresenter;
import com.example.spb.common.RequestCode;
import com.example.spb.common.RequestEntityJson;
import com.example.spb.entity.Bar;
import com.example.spb.model.implA.PostBarModelImpl;
import com.example.spb.model.implA.SignModelImpl;
import com.example.spb.model.inter.PostBarModel;
import com.example.spb.model.inter.SignModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.ISignInBadgeFPresenter;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.view.Component.ResponseToast;
import com.example.spb.view.inter.ISignInBadgeFView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

public class SignInBadgeFPresenterImpl extends BasePresenter<ISignInBadgeFView> implements ISignInBadgeFPresenter {

    private int likeBadgeNum = 0;
    private int signBadgeNum = 0;
    private String starBadge = "";
    private int ctSign = 1;
    private final PostBarModel barModel;
    private final SignModel signModel;

    public int getLikeBadgeNum() {
        return likeBadgeNum;
    }

    public void setLikeBadgeNum(int likeBadgeNum) {
        this.likeBadgeNum = likeBadgeNum;
    }

    public int getSignBadgeNum() {
        return signBadgeNum;
    }

    public void setSignBadgeNum(int signBadgeNum) {
        this.signBadgeNum = signBadgeNum;
    }

    public String getStarBadge() {
        return starBadge;
    }

    public void setStarBadge(String starBadge) {
        this.starBadge = starBadge;
    }

    public int getCtSign() {
        return ctSign;
    }

    public void setCtSign(int ctSign) {
        this.ctSign = ctSign;
    }

    public SignInBadgeFPresenterImpl() {
        barModel = new PostBarModelImpl();
        signModel = new SignModelImpl();
    }

    public void obtainBarLike(String account, OnReturn onReturn) {
        Bar bar = new Bar();
        bar.setUser_account(account);
        barModel.queryUserBarLikeCount(account, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestEntityJson<String> requestEntityJson = new Gson().fromJson(value, new TypeToken<RequestEntityJson<String>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestEntityJson.getResultCode())) {
                        onReturn.onReturn(Integer.parseInt(requestEntityJson.getData()));
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void obtainStarBadge(String account, OnReturn onReturn) {
        signModel.updateSignStarBadge(account, getStarBadge(), new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestEntityJson<String> requestEntityJson = new Gson().fromJson(value, new TypeToken<RequestEntityJson<String>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestEntityJson.getResultCode())) {
                        onReturn.onReturn(0);
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void obtainLikeBadge(String account, int num, OnReturn onReturn) {
        String likeBadge;
        if (num == 1) {
            likeBadge = InValues.send(R.string.like_badge_one);
        } else if (num == 2) {
            likeBadge = InValues.send(R.string.like_badge_two);
        } else {
            likeBadge = InValues.send(R.string.like_badge_three);
        }
        signModel.updateSignLikeBadge(account, likeBadge, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestCode requestCode = new Gson().fromJson(value, RequestCode.class);
                    if (ResponseToast.toToast(requestCode)) {
                        onReturn.onReturn(num);
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void obtainSignBadge(String account, int num, OnReturn onReturn) {
        String signBadge;
        if (num == 1) {
            signBadge = InValues.send(R.string.sign_badge_one);
        } else if (num == 2) {
            signBadge = InValues.send(R.string.sign_badge_two);
        } else {
            signBadge = InValues.send(R.string.sign_badge_three);
        }
        signModel.updateSignTaskBadge(account, signBadge, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestCode requestCode = new Gson().fromJson(value, RequestCode.class);
                    if (ResponseToast.toToast(requestCode)) {
                        onReturn.onReturn(num);
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public interface OnReturn {
        void onReturn(int likeNum);
    }
}
