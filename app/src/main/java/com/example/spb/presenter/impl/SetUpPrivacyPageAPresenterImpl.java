package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Dto.UserDto;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.UserModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.ISetUpPrivacyPageAPresenter;
import com.example.spb.view.inter.ISetUpPrivacyPageAView;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SetUpPrivacyPageAPresenterImpl extends BasePresenter<ISetUpPrivacyPageAView> implements ISetUpPrivacyPageAPresenter {

    private List<Integer> keys;
    private SpbModelBasicInter userModel;

    public List<Integer> getKeys() {
        return keys;
    }

    public void setKeys(int position,boolean a) {
        if (a){
            getKeys().remove(position);
            getKeys().add(position,1);
        }else {
            getKeys().remove(position);
            getKeys().add(position,2);
        }
    }

    public String getStringPrivacy(){
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0 ; i < getKeys().size() ; i++){
            stringBuffer.append(getKeys().get(i));
        }
        return stringBuffer.toString();
    }

    public SetUpPrivacyPageAPresenterImpl() {
        keys = new ArrayList<>();
        userModel = new UserModelImpl();
    }

    public void setMyPrivacy(String s){
        for (int i = 0 ; i < s.length() ; i++){
            keys.add(Integer.valueOf(s.substring(i,i + 1)));
        }
    }

    public void updateUserPrivacy(String account,int p){
        UserDto userDto = new UserDto();
        userDto.setUser_account(account);
        userDto.setUser_privacy(getStringPrivacy());
        userModel.updateData(userModel.DATAUSER_UPDATE_SIX, userDto, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (isAttachView()){
                        if (Integer.valueOf(a) == 200){
                            getView().response(p,getView().ON_SUCCEED);
                        }else {
                            getView().response(p,getView().ON_ERROR);
                        }
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }
}
