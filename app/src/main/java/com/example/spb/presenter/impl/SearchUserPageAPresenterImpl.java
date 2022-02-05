package com.example.spb.presenter.impl;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.adapter.SearchUserAdapter;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Dto.UserDto;
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

    private UserDto userDto;
    private SpbModelBasicInter userModel;
    private RecyclerView recyclerView;
    private Activity activity;

    public SearchUserPageAPresenterImpl(Activity activity) {
        this.activity = activity;
        userModel = new UserModelImpl();
    }

    public void searUser(String key ,RecyclerView r){
        this.recyclerView = r;
        userDto = new UserDto();
        userDto.setUser_name(key);
        userModel.selectData(userModel.FIRSTPAGE_THREE, userDto, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == 200){
                        List<UserDto> userDtos = new Gson().fromJson(a.substring(3),new TypeToken<List<UserDto>>(){}.getType());
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getView().response(null,0);
                                setData(userDtos);
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

    public void setData(List<UserDto> userDtos){
        SearchUserAdapter searchUserAdapter = new SearchUserAdapter(userDtos,activity);
        recyclerView.setAdapter(searchUserAdapter);
    }
}
