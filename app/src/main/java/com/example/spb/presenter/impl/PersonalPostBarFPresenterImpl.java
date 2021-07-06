package com.example.spb.presenter.impl;

import android.app.Activity;
import android.os.Looper;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.adapter.PersonalSpaceBarAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Bar;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.BarModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IPersonalPostBarFPresenter;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.example.spb.view.Component.MyToastClass;
import com.example.spb.view.activity.PersonalSpacePage;
import com.example.spb.view.inter.IPersonalPostBarFView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class PersonalPostBarFPresenterImpl extends BasePresenter<IPersonalPostBarFView> implements IPersonalPostBarFPresenter {

    private PersonalSpacePage personalSpacePage;
    private PersonalSpaceBarAdapter personalSpaceBarAdapter;
    private String cacheDate = "1";
    private SpbModelBasicInter barModel;

    public PersonalPostBarFPresenterImpl(PersonalSpacePage p) {
        this.personalSpacePage = p;
        barModel = new BarModelImpl();
    }

    public String getCacheDate() {
        return cacheDate;
    }

    public void setCacheDate(String cacheDate) {
        this.cacheDate = cacheDate;
    }

    public void addPersonalBarList(List<Bar> b, RecyclerView recyclerView, boolean fun){
        if (personalSpaceBarAdapter == null || fun){
            personalSpaceBarAdapter = new PersonalSpaceBarAdapter(personalSpacePage,b);
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

    public void obtainMorePersonalBar(String account){
        Bar bar = new Bar();
        bar.setUser_account(account);
        bar.setPb_date(getCacheDate());
        barModel.selectData(barModel.DATABAR_SELECT_FOUR, bar, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
//                Looper.prepare();
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == 200){
                        List<Bar> bars = new Gson().fromJson(a.substring(3),new TypeToken<List<Bar>>(){}.getType());
                        bars.remove(0);
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_personal_bar)
                                ,1,account,(Serializable)bars);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
//                    MyToastClass.ShowToast(MyApplication.getContext(),"数据错误");
//                    getView().finishRRefresh(0);
                }
//                Looper.loop();
            }

            @Override
            public void onError(int t) {
//                switch (t){
//                    case ERROR_CONNECTION:
//                        MyToastClass.ShowToast(MyApplication.getContext(),"连接错误");
//                        break;
//                    case ERROR_LONGTIME:
//                        MyToastClass.ShowToast(MyApplication.getContext(),"连接超时，请重试");
//                        break;
//                }
//                getView().finishRRefresh(0);
            }
        });
    }

    public void stopvoice(){
        if (personalSpaceBarAdapter != null){
            personalSpaceBarAdapter.refreshNoewVoice(-1);
        }
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
