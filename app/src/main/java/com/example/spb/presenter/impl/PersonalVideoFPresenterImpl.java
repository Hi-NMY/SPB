package com.example.spb.presenter.impl;

import android.app.Activity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.adapter.PersonalSpaceBarAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.base.BasePresenter;
import com.example.spb.common.RequestListJson;
import com.example.spb.entity.Bar;
import com.example.spb.model.implA.PostBarModelImpl;
import com.example.spb.model.inter.PostBarModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IPersonalVideoFPresenter;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.Component.ResponseToast;
import com.example.spb.view.inter.IPersonalVideoFView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class PersonalVideoFPresenterImpl extends BasePresenter<IPersonalVideoFView> implements IPersonalVideoFPresenter {

    private final BaseMVPActivity baseMVPActivity;
    private PersonalSpaceBarAdapter personalSpaceBarAdapter;
    private String cacheDate = "";
    private final PostBarModel postBarModel;

    public PersonalVideoFPresenterImpl(Activity activity) {
        this.baseMVPActivity = (BaseMVPActivity) activity;
        postBarModel = new PostBarModelImpl();
    }

    public String getCacheDate() {
        return cacheDate;
    }

    public void setCacheDate(String cacheDate) {
        this.cacheDate = cacheDate;
    }

    public void addPersonalBarList(List<Bar> b, RecyclerView recyclerView, boolean fun) {
        if (personalSpaceBarAdapter == null || fun) {
            personalSpaceBarAdapter = new PersonalSpaceBarAdapter(baseMVPActivity, b);
            recyclerView.setAdapter(personalSpaceBarAdapter);
            ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
            recyclerView.startLayoutAnimation();
        } else {
            personalSpaceBarAdapter.addMorePersonalBar(b);
        }
    }

    public void deleteBarData(String id) {
        personalSpaceBarAdapter.deleteBar(id);
    }

    public void obtainMorePersonalVideoBar(String account) {
        Bar bar = new Bar();
        bar.setUser_account(account);
        bar.setPb_date(getCacheDate());
        postBarModel.queryVideoBarListForDate(cacheDate, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<Bar> requestListJson = new Gson().fromJson(value, new TypeToken<RequestListJson<Bar>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestListJson.getResultCode())) {
                        requestListJson.getDataList().remove(0);
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_personal_videobar)
                                , 1, account, (Serializable) requestListJson.getDataList());
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void refreshThumb(int num, String pbId) {
        if (personalSpaceBarAdapter != null) {
            personalSpaceBarAdapter.refreshLikeItem(num, pbId);
        }
    }

    public void refreshComment(int num) {
        if (personalSpaceBarAdapter != null) {
            personalSpaceBarAdapter.refreshCommentItem(num);
        }
    }

    public void refreshNowComment(int num) {
        if (personalSpaceBarAdapter != null) {
            personalSpaceBarAdapter.refreshNowCommentItem(num);
        }
    }
}
