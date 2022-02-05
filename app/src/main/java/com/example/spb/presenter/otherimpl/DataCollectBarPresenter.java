package com.example.spb.presenter.otherimpl;

import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.common.RequestCode;
import com.example.spb.common.RequestEntityJson;
import com.example.spb.common.RequestListJson;
import com.example.spb.entity.Bar;
import com.example.spb.entity.Dto.CollectBarDto;
import com.example.spb.model.implA.CollectBarModelImpl;
import com.example.spb.model.inter.CollectBarModel;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataCollectBarPresenter {

    private final CollectBarModel collectBarModel;
    public List<String> collectList;
    private final String account;
    private final Gson gson;
    private String cacheAccount = "";

    public DataCollectBarPresenter(String user_account) {
        account = user_account;
        collectBarModel = new CollectBarModelImpl();
        gson = new Gson();
        initDate();
    }

    private void initDate() {
        collectBarModel.queryCollectBarList(account, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<String> requestListJson = gson.fromJson(value, new TypeToken<RequestListJson<String>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestListJson.getResultCode())) {
                        collectList = requestListJson.getDataList();
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void obtainCollectBar(String account) {
        collectBarModel.queryCollectBarFullList(account, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<Bar> requestListJson = gson.fromJson(value, new TypeToken<RequestListJson<Bar>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestListJson.getResultCode())) {
                        SpbBroadcast.sendReceiver(MyApplication.getContext()
                                , InValues.send(R.string.Bcr_add_CollectBar), 0, (Serializable) requestListJson.getDataList());
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void addCollectBar(String acc, String pbid) {
        if (!acc.equals(account)) {
            cacheAccount = acc;
        }
        CollectBarDto collectBarDto = new CollectBarDto();
        collectBarDto.setPb_one_id(pbid);
        collectBarDto.setUser_account(account);
        collectBarModel.addCollectBar(collectBarDto, cacheAccount, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestEntityJson<String> requestEntityJson = gson.fromJson(value, new TypeToken<RequestEntityJson<String>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestEntityJson.getResultCode())) {
                        collectList.add(0, pbid);
                        toMessage(requestEntityJson.getData(), pbid);
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    private void toMessage(String userIp, String pbId) {
        AndroidUnicast unicast = null;
        try {
            unicast = new AndroidUnicast();
            unicast.setDeviceToken(userIp);
            unicast.setTicker("Android unicast ticker");
            unicast.setTitle(InValues.send(R.string.Push_Title));
            unicast.setText(InValues.send(R.string.Push_Collect_txt));
            unicast.setExtraField(InValues.send(R.string.Push_fun), String.valueOf(AndroidUnicast.PUSHCOLLECTKEY));
            unicast.setExtraField(InValues.send(R.string.Push_pbid_key), pbId);
            unicast.clientSend(unicast);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeCollectBar(String pbid) {
        CollectBarDto collectBarDto = new CollectBarDto();
        collectBarDto.setPb_one_id(pbid);
        collectBarDto.setUser_account(account);
        collectBarModel.deleteCollectBar(collectBarDto, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestCode requestCode = gson.fromJson(value, RequestCode.class);
                    if (ResponseToast.toToast(requestCode)) {
                        collectList.removeIf(collectList -> collectList.equals(pbid));
                        List<Bar> cacheBars = new ArrayList<>();
                        Bar bar = new Bar();
                        bar.setPb_one_id(pbid);
                        cacheBars.add(bar);
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_CollectBar), 2, (Serializable) cacheBars);
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public boolean determineCollect(String pbid) {
        if (collectList != null && collectList.stream().anyMatch(collectList -> collectList.equals(pbid))) {
            return true;
        } else {
            return false;
        }
    }

    public int obtainCollectNum() {
        if (collectList != null) {
            return collectList.size();
        } else {
            return 0;
        }
    }
}
