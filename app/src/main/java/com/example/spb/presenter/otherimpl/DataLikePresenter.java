package com.example.spb.presenter.otherimpl;

import com.example.spb.R;
import com.example.spb.common.RequestEntityJson;
import com.example.spb.common.RequestListJson;
import com.example.spb.entity.Like;
import com.example.spb.model.implA.LikeModelImpl;
import com.example.spb.model.inter.LikeModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.view.Component.ResponseToast;
import com.example.spb.xserver.AndroidUnicast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DataLikePresenter {

    private final LikeModel likeModel;
    public List<Like> likeList;
    private final String account;
    private final Gson gson;

    public DataLikePresenter(String user_account) {
        account = user_account;
        likeModel = new LikeModelImpl();
        gson = new Gson();
        initDate();
    }

    private void initDate() {
        likeModel.queryLike(account, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<Like> requestList = gson.fromJson(value, new TypeToken<RequestListJson<Like>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestList.getResultCode())) {
                        likeList = requestList.getDataList();
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void updateLikeData(String barId, String account, String oaccount, OnReturn onReturn) {
        Like like = new Like();
        like.setPb_one_id(barId);
        like.setUser_account(account);
        if (account.equals(oaccount)) {
            oaccount = "";
        }
        if (determineLike(barId)) {
            onReturn.removeLike();
            likeList.removeIf(likeList -> likeList.getPb_one_id().equals(barId));
            likeModel.deleteLike(barId, account, null);
        } else {
            onReturn.addLike();
            likeList.add(like);
            likeModel.addLike(barId, account, oaccount, new MyCallBack() {
                @Override
                public void onSuccess(@NotNull Response response) {
                    String value = DataVerificationTool.isEmpty(response);
                    if (value != null) {
                        RequestEntityJson<String> requestEntity = gson.fromJson(value, new TypeToken<RequestEntityJson<String>>() {
                        }.getType());
                        if (ResponseToast.toToast(requestEntity.getResultCode())) {
                            toMessage(requestEntity.getData(), barId);
                        }
                    }
                }

                @Override
                public void onError(int t) {

                }
            });
        }
    }

    private void toMessage(String a, String barId) {
        AndroidUnicast unicast = null;
        try {
            unicast = new AndroidUnicast();
            unicast.setDeviceToken(a);
            unicast.setTicker("Android unicast ticker");
            unicast.setTitle(InValues.send(R.string.Push_Title));
            unicast.setText(InValues.send(R.string.Push_Like_txt));
            unicast.setExtraField(InValues.send(R.string.Push_fun), String.valueOf(unicast.PUSHLIKEKEY));
            unicast.setExtraField(InValues.send(R.string.Push_pbid_key), barId);
            unicast.clientSend(unicast);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean determineLike(String id) {
        if (likeList != null && likeList.stream().anyMatch(likeList -> likeList.getPb_one_id().equals(id))) {
            return true;
        } else {
            return false;
        }
    }

    public interface OnReturn {
        void removeLike();

        void addLike();
    }
}
