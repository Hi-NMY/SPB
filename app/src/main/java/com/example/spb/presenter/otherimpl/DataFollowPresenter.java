package com.example.spb.presenter.otherimpl;

import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.common.RequestCode;
import com.example.spb.common.RequestEntityJson;
import com.example.spb.common.RequestListJson;
import com.example.spb.model.implA.FollowModelImpl;
import com.example.spb.model.inter.FollowModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.Component.ResponseToast;
import com.example.spb.xserver.AndroidUnicast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DataFollowPresenter {

    private final FollowModel followModel;
    public List<String> followList;
    private final String user_account;
    private final Gson gson;

    public DataFollowPresenter(String userAccount) {
        this.user_account = userAccount;
        followModel = new FollowModelImpl();
        followList = new ArrayList<>();
        gson = new Gson();
        initDate();
    }

    public void initDate() {
        followModel.queryFollowList(user_account, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<String> requestList = gson.fromJson(value, new TypeToken<RequestListJson<String>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestList.getResultCode())) {
                        followList = requestList.getDataList();
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void addFollow(String followedAccount) {
        followModel.addFollow(user_account, followedAccount, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestEntityJson<String> requestEntity = gson.fromJson(value, new TypeToken<RequestEntityJson<String>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestEntity.getResultCode())) {
                        followList.add(0, followedAccount);
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_re_Follow), 0, null);
                        if (!DataVerificationTool.isEmpty(requestEntity.getData())) {
                            toMessage(requestEntity.getData());
                        }
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    private void toMessage(String id) {
        AndroidUnicast unicast = null;
        try {
            unicast = new AndroidUnicast();
            unicast.setDeviceToken(id);
            unicast.setTicker("Android unicast ticker");
            unicast.setTitle(InValues.send(R.string.Push_Title));
            unicast.setText(InValues.send(R.string.Push_Follow_txt));
            unicast.setExtraField(InValues.send(R.string.Push_fun), String.valueOf(AndroidUnicast.PUSHFOLLOWKEY));
            unicast.clientSend(unicast);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeFollow(String followedAccount) {
        followModel.deleteFollow(user_account, followedAccount, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestCode requestCode = gson.fromJson(value, RequestCode.class);
                    if (ResponseToast.toToast(requestCode)) {
                        followList.removeIf(followList -> followList.equals(followedAccount));
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_re_Follow), 1, null);
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public boolean determineFollow(String account) {
        if (followList != null && followList.stream().anyMatch(followList -> followList.equals(account))) {
            return true;
        } else {
            return false;
        }
    }

    public int obtainFollowNum() {
        if (followList != null) {
            return followList.size();
        } else {
            return 0;
        }
    }

    public interface OnReturn {
        void onReturn();
    }
}
