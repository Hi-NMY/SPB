package com.example.spb.presenter.impl;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.adapter.SearchUserAdapter;
import com.example.spb.base.BasePresenter;
import com.example.spb.common.RequestListJson;
import com.example.spb.entity.Dto.UserDto;
import com.example.spb.model.implA.UserModelImpl;
import com.example.spb.model.inter.UserModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.ISearchUserPageAPresenter;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.view.Component.ResponseToast;
import com.example.spb.view.inter.ISearchUserPageAView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SearchUserPageAPresenterImpl extends BasePresenter<ISearchUserPageAView> implements ISearchUserPageAPresenter {

    private final UserModel userModel;
    private RecyclerView recyclerView;
    private final Activity activity;

    public SearchUserPageAPresenterImpl(Activity activity) {
        this.activity = activity;
        userModel = new UserModelImpl();
    }

    public void searUser(String key, RecyclerView r) {
        this.recyclerView = r;
        UserDto userDto = new UserDto();
        userDto.setUser_name(key);
        userModel.querySearchUser(key, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<UserDto> requestListJson = new Gson().fromJson(value, new TypeToken<RequestListJson<UserDto>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestListJson.getResultCode())) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                getView().response(null, 0);
                                setData(requestListJson.getDataList());
                            }
                        });
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void setData(List<UserDto> userDtos) {
        SearchUserAdapter searchUserAdapter = new SearchUserAdapter(userDtos, activity);
        recyclerView.setAdapter(searchUserAdapter);
    }
}
