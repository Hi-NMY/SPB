package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Bar;
import com.example.spb.entity.Sign;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.BarModelImpl;
import com.example.spb.model.impl.SignInModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.ISignInBadgeFPresenter;
import com.example.spb.view.inter.ISignInBadgeFView;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class SignInBadgeFPresenterImpl extends BasePresenter<ISignInBadgeFView> implements ISignInBadgeFPresenter {

    private int likeBadgeNum = 0;
    private int signBadgeNum = 0;
    private String starBadge = "";
    private int ctSign = 1;
    private SpbModelBasicInter barModel;
    private SpbModelBasicInter signModel;
    private String likeBadge;
    private String signBadge;

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
        barModel = new BarModelImpl();
        signModel = new SignInModelImpl();
    }

    public void obtainBarLike(String account,OnReturn onReturn){
        Bar bar = new Bar();
        bar.setUser_account(account);
        barModel.selectData(barModel.DATABAR_SELECT_EIGHT, bar, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    onReturn.onReturn(Integer.valueOf(a));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void obtainStarBadge(String account,OnReturn onReturn){
        Sign sign = new Sign();
        sign.setUser_account(account);
        sign.setSign_star_badge(getStarBadge());
        signModel.updateData(signModel.DATASIGN_UPDATE_SIX, sign, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a) == 200){
                        onReturn.onReturn(0);
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void obtainLikeBadge(String account,int num,OnReturn onReturn){
        if (num == 1){
            likeBadge = "like_badge_one.png|";
        }else if (num == 2){
            likeBadge = "like_badge_one.png|like_badge_two.png|";
        }else {
            likeBadge = "like_badge_one.png|like_badge_two.png|like_badge_three.png|";
        }
        Sign sign = new Sign();
        sign.setUser_account(account);
        sign.setSign_like_badge(likeBadge);
        signModel.updateData(signModel.DATASIGN_UPDATE_SEVEN, sign, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a) == 200){
                        onReturn.onReturn(num);
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void obtainSignBadge(String account,int num,OnReturn onReturn){
        if (num == 1){
            signBadge = "sign_badge_one.png|";
        }else if (num == 2){
            signBadge = "sign_badge_one.png|sign_badge_two.png|";
        }else {
            signBadge = "sign_badge_one.png|sign_badge_two.png|sign_badge_three.png|";
        }
        Sign sign = new Sign();
        sign.setUser_account(account);
        sign.setSign_task_badge(signBadge);
        signModel.updateData(signModel.DATASIGN_UPDATE_EIGHT, sign, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a) == 200){
                        onReturn.onReturn(num);
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public interface OnReturn{
        void onReturn(int likeNum);
    }
}
