package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.UserHomeSpaceAModelImpl;
import com.example.spb.model.inter.IUserHomeSpaceAModel;
import com.example.spb.presenter.inter.IPersonalSpacePageAPresenter;
import com.example.spb.view.inter.IPersonalSpacePageAView;
import com.example.spb.view.inter.IUserRegisteredPageAView;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

public class PersonalSpacePageAPresenterImpl extends BasePresenter<IPersonalSpacePageAView> implements IPersonalSpacePageAPresenter {

    private IUserHomeSpaceAModel mIUserHomeSpaceAModel;
    private String headImgPath;

    public PersonalSpacePageAPresenterImpl() {
        mIUserHomeSpaceAModel = new UserHomeSpaceAModelImpl();
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
            getView().response(headImgPath, IUserRegisteredPageAView.RESPONSE_SUCCESS);
        }
    }
}
