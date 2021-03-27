package com.example.spb.presenter.otherimpl;

import com.example.spb.entity.AttentionTopic;
import com.example.spb.entity.CollectBar;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.AttentionTopicModelImpl;
import com.example.spb.model.impl.CollectBarModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.littlefun.MyDateClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DataCollectBarPresenter {

    private static int SUCCESS = 200;

    private SpbModelBasicInter collectBarModel;
    public List<CollectBar> collectBarList;
    private String account;
    private String a;
    private Gson gson;

    public DataCollectBarPresenter(String user_account) {
        account = user_account;
        collectBarModel = new CollectBarModelImpl();
        gson = new Gson();
        initDate();
    }

    private void initDate() {
        CollectBar collectBar = new CollectBar();
//        collectBar.setTopic_date(MyDateClass.showNowDate());
//        collectBar.setTopic_name(account);
//        collectBarModel.selectData(SpbModelBasicInter.DATEATTENTIONTOPIC_SELECT_ONE, attentionTopic, new MyCallBack() {
//            @Override
//            public void onSuccess(@NotNull Response response) {
//                try {
//                    a = response.body().string();
//                    if (Integer.valueOf(a.substring(0,3)) == SUCCESS){
//                        collectBarList = gson.fromJson(a.substring(3),new TypeToken<List<AttentionTopic>>()
//                        {}.getType());
//                    }else {
//
//                    }
//                } catch (Exception e) {
//
//                }
//            }
//
//            @Override
//            public void onError(int t) {
//
//            }
//        });
    }
}
