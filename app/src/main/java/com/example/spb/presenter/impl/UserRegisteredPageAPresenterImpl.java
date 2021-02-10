package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.UserRegisteredPageAModelImpl;
import com.example.spb.presenter.inter.IUserRegisteredPageAPresenter;
import com.example.spb.view.inter.IUserRegisteredPageAView;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

public class UserRegisteredPageAPresenterImpl extends BasePresenter<IUserRegisteredPageAView> implements IUserRegisteredPageAPresenter {

    private SpbModelBasicInter mIUserRegisteredPageAModel;
    private String headImgPath;

    public UserRegisteredPageAPresenterImpl() {
        mIUserRegisteredPageAModel = new UserRegisteredPageAModelImpl();
    }

    public void getHeadImage(List<LocalMedia> result){
        for (LocalMedia media : result) {
            if (media.isCompressed()){
                headImgPath = media.getCompressPath();
            }else {
                headImgPath = media.getCutPath();
            }
        }

        if (isAttachView()){
            getView().response(headImgPath,IUserRegisteredPageAView.RESPONSE_ONE);
        }
    }
}
