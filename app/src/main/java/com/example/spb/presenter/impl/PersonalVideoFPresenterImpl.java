package com.example.spb.presenter.impl;

import android.app.Activity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.adapter.PersonalSpaceBarAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Bar;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.VideoModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IPersonalVideoFPresenter;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.inter.IPersonalVideoFView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class PersonalVideoFPresenterImpl extends BasePresenter<IPersonalVideoFView> implements IPersonalVideoFPresenter {

    private BaseMVPActivity baseMVPActivity;
    private PersonalSpaceBarAdapter personalSpaceBarAdapter;
    private String cacheDate = "1";
    private SpbModelBasicInter videoModel;

    public PersonalVideoFPresenterImpl(Activity activity) {
        this.baseMVPActivity = (BaseMVPActivity) activity;
        videoModel = new VideoModelImpl();
    }

    public String getCacheDate() {
        return cacheDate;
    }

    public void setCacheDate(String cacheDate) {
        this.cacheDate = cacheDate;
    }

    public void addPersonalBarList(List<Bar> b, RecyclerView recyclerView, boolean fun){
        if (personalSpaceBarAdapter == null || fun){
            personalSpaceBarAdapter = new PersonalSpaceBarAdapter(baseMVPActivity,b);
            recyclerView.setAdapter(personalSpaceBarAdapter);
            ((DefaultItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
            recyclerView.startLayoutAnimation();
        }else {
            personalSpaceBarAdapter.addMorePersonalBar(b);
        }
    }

    public void deleteBarData(String id){
        personalSpaceBarAdapter.deleteBar(id);
    }

    public void obtainMorePersonalVideoBar(String account){
        Bar bar = new Bar();
        bar.setUser_account(account);
        bar.setPb_date(getCacheDate());
        videoModel.selectData(videoModel.DATABAR_SELECT_FOUR, bar, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == 200){
                        List<Bar> bars = new Gson().fromJson(a.substring(3),new TypeToken<List<Bar>>(){}.getType());
                        bars.remove(0);
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_personal_videobar)
                                ,1,account,(Serializable)bars);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int t) {
            }
        });
    }

    public void refreshThumb(int num,String pbId){
        if (personalSpaceBarAdapter != null){
            personalSpaceBarAdapter.refreshLikeItem(num,pbId);
        }
    }

    public void refreshComment(int num){
        if (personalSpaceBarAdapter != null){
            personalSpaceBarAdapter.refreshCommentItem(num);
        }
    }

    public void refreshNowComment(int num){
        if (personalSpaceBarAdapter != null){
            personalSpaceBarAdapter.refreshNowCommentItem(num);
        }
    }
}
