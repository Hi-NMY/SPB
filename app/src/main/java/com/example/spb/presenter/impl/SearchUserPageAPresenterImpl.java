package com.example.spb.presenter.impl;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.adapter.SearchUserAdapter;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.User;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.UserModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.ISearchUserPageAPresenter;
import com.example.spb.view.inter.ISearchUserPageAView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class SearchUserPageAPresenterImpl extends BasePresenter<ISearchUserPageAView> implements ISearchUserPageAPresenter {

    private User user;
    private SpbModelBasicInter userModel;
    private RecyclerView recyclerView;
    private Activity activity;

    public SearchUserPageAPresenterImpl(Activity activity) {
        this.activity = activity;
        userModel = new UserModelImpl();
    }

    public void searUser(String key ,RecyclerView r){
        this.recyclerView = r;
        user = new User();
        user.setUser_name(key);
        userModel.selectData(userModel.FIRSTPAGE_THREE, user, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == 200){
                        List<User> users = new Gson().fromJson(a.substring(3),new TypeToken<List<User>>(){}.getType());
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getView().response(null,0);
                                setData(users);
                            }
                        });
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void setData(List<User> users){
        SearchUserAdapter searchUserAdapter = new SearchUserAdapter(users,activity);
        recyclerView.setAdapter(searchUserAdapter);
    }
}
