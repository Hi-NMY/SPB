package com.example.spb.presenter.impl;

import android.app.Activity;
import android.net.Uri;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.adapter.MyBadgeAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.common.RequestCode;
import com.example.spb.common.RequestEntityJson;
import com.example.spb.common.RequestListJson;
import com.example.spb.entity.Bar;
import com.example.spb.entity.Dto.UserBadgeDto;
import com.example.spb.entity.Dto.UserDto;
import com.example.spb.model.implA.*;
import com.example.spb.model.inter.*;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IPersonalSpacePageAPresenter;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MyResolve;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.Component.ResponseToast;
import com.example.spb.view.activity.PersonalSpacePage;
import com.example.spb.view.inter.IPersonalSpacePageAView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.entity.LocalMedia;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PersonalSpacePageAPresenterImpl extends BasePresenter<IPersonalSpacePageAView> implements IPersonalSpacePageAPresenter {

    private String headImgPath;
    private String bgImgPath;
    private final UserModel userModel;
    private final AccountSecurityModel accountSecurityModel;
    private final FollowedModel followedModel;
    private final FollowModel followModel;
    private final PostBarModel barModel;
    private final SignModel signModel;
    private boolean userFollowKey = false;
    private String nowBadge;
    private List<String> badgelist;
    private final List<Integer> keys;


    public List<Integer> getKeys() {
        return keys;
    }

    public void setMyPrivacy(String s) {
        for (int i = 0; i < s.length(); i++) {
            keys.add(Integer.valueOf(s.substring(i, i + 1)));
        }
    }

    public PersonalSpacePageAPresenterImpl() {
        barModel = new PostBarModelImpl();
        userModel = new UserModelImpl();
        followedModel = new FollowedModelImpl();
        followModel = new FollowModelImpl();
        signModel = new SignModelImpl();
        accountSecurityModel = new AccountSecurityModelImpl();
        keys = new ArrayList<>();
    }

    public String getNowBadge() {
        return nowBadge;
    }

    public void setNowBadge(String nowBadge) {
        this.nowBadge = nowBadge;
    }

    public boolean isUserFollowKey() {
        return userFollowKey;
    }

    public void setUserFollowKey(boolean userFollowKey) {
        this.userFollowKey = userFollowKey;
    }

    public void getHeadImage(String account, List<LocalMedia> result) {
        for (LocalMedia media : result) {
            if (media.isCompressed()) {
                headImgPath = media.getCompressPath();
            } else {
                headImgPath = media.getCutPath();
            }
        }
        userModel.updateUserHeadImage(new File(headImgPath), account, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestCode requestCode = new Gson().fromJson(value, RequestCode.class);
                    if (ResponseToast.toToast(requestCode)) {
                        getView().response(headImgPath, PersonalSpacePage.RESPONSE_SUCCESS);
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void getBgImage(String account, List<LocalMedia> result) {
        for (LocalMedia media : result) {
            if (media.isCompressed()) {
                bgImgPath = media.getCompressPath();
            } else {
                bgImgPath = media.getCutPath();
            }
        }
        userModel.updateUserBgImage(new File(bgImgPath), account, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestCode requestCode = new Gson().fromJson(value, RequestCode.class);
                    if (ResponseToast.toToast(requestCode)) {
                        getView().response(bgImgPath, PersonalSpacePage.RESPONSE_SUCCESS);
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void getUser(String account, OnReturn onReturn) {
        accountSecurityModel.queryVerifyAndUserFull(account, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestEntityJson<UserDto> requestEntityJson = new Gson().fromJson(value, new TypeToken<RequestEntityJson<UserDto>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestEntityJson.getResultCode())) {
                        setMyPrivacy(requestEntityJson.getData().getUser_privacy());
                        onReturn.onReturn(requestEntityJson.getData());
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void getFollowNum(String account, OnReturnNum onReturn) {
        followModel.queryFollowCount(account, new MyCallBack() {
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

    public void getFollowedNum(String account, OnReturnNum onReturn) {
        followedModel.queryFollowedCount(account, new MyCallBack() {
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

    public void obtainPersonalBar(String account, OnReturnBar onReturnBar) {
        barModel.queryNoVideoUserBarListForDate(account, "", new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<Bar> requestListJson = new Gson().fromJson(value, new TypeToken<RequestListJson<Bar>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestListJson.getResultCode())) {
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_personal_bar)
                                , 0, account, (Serializable) requestListJson.getDataList());
                        onReturnBar.onReturn();
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });

        barModel.queryVideoUserBarListForDate(account, "", new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<Bar> requestListJson = new Gson().fromJson(value, new TypeToken<RequestListJson<Bar>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestListJson.getResultCode())) {
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_personal_videobar)
                                , 0, account, (Serializable) requestListJson.getDataList());
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void obtainUserBadge(String account, OnReturnBar onReturnBar) {
        signModel.queryUserBadge(account, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestEntityJson<UserBadgeDto> requestEntityJson = new Gson().fromJson(value, new TypeToken<RequestEntityJson<UserBadgeDto>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestEntityJson.getResultCode())) {
                        badgelist = obtainMyBadgeList(requestEntityJson.getData());
                        onReturnBar.onReturn();
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void updateUserBadge(String account, String badge) {
        userModel.updateUserBadgeImage(badge, account, null);
    }

    public List<String> obtainMyBadgeList(UserBadgeDto s) {
        List<String> badges = new ArrayList<>();
        if (DataVerificationTool.isEmpty(s.getSign_star_badge())) {
            badges.add(s.getSign_star_badge());
        }
        if (s.getSign_ct_badge() == 0) {
            badges.add(InValues.send(R.string.certification_badge));
        }
        if (DataVerificationTool.isEmpty(s.getSign_like_badge())) {
            badges.addAll(MyResolve.InBadge(s.getSign_like_badge()));
        }
        if (DataVerificationTool.isEmpty(s.getSign_task_badge())) {
            badges.addAll(MyResolve.InBadge(s.getSign_task_badge()));
        }
        return badges;
    }

    public void setBadgeAdapter(Activity activity, boolean account, RecyclerView recyclerView, GridLayoutManager gridLayoutManager) {
        MyBadgeAdapter myBadgeAdapter = new MyBadgeAdapter(badgelist, account, getNowBadge(), activity);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(myBadgeAdapter);
    }

    public void updateRong(String account, String name) {
        UserInfo userInfo = new UserInfo(account, name,
                Uri.parse(InValues.send(R.string.prefix_img) + account + InValues.send(R.string.suffix_head_img)));
        RongIM.getInstance().refreshUserInfoCache(userInfo);
    }

    public interface OnReturn {
        void onReturn(UserDto userDto);
    }

    public interface OnReturnNum {
        void onReturn(int num);
    }

    public interface OnReturnBar {
        void onReturn();
    }
}
