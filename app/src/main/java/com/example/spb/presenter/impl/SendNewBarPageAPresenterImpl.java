package com.example.spb.presenter.impl;

import android.app.Activity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.adapter.NewBarImageAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.ImageDouble;
import com.example.spb.model.impl.SendNewBarPageAModelImpl;
import com.example.spb.model.inter.ISendNewBarPageAModel;
import com.example.spb.presenter.inter.ISendNewBarPageAPresenter;
import com.example.spb.view.inter.ISendNewBarPageAView;
import com.hitomi.tilibrary.transfer.Transferee;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

public class SendNewBarPageAPresenterImpl extends BasePresenter<ISendNewBarPageAView> implements ISendNewBarPageAPresenter {

    private ISendNewBarPageAModel mISendNewBarPageAModel;
    public List<ImageDouble> barImage;
    public NewBarImageAdapter newBarImageAdapter;

    public SendNewBarPageAPresenterImpl() {
        mISendNewBarPageAModel = new SendNewBarPageAModelImpl();
        if (barImage == null){
            barImage = new ArrayList<>();
        }
    }

    public void obtainImage(List<LocalMedia> result){
        for (LocalMedia media : result) {
            ImageDouble imageDouble = new ImageDouble();
            if (media.isCut() && media.isCompressed()){
                imageDouble.setMinPath(media.getCompressPath());
                imageDouble.setMaxPath(media.getCutPath());
                barImage.add(imageDouble);
            }else {

            }
        }
    }

    public void setImageList(Activity activity, LinearLayoutManager linearLayoutManager, RecyclerView recyclerView){
        newBarImageAdapter = new NewBarImageAdapter(activity, barImage, new NewBarImageAdapter.RemoveImg() {
            @Override
            public void removeImg(int option) {
                removeImage(option);
                newBarImageAdapter.removeImage(option);
            }
        });
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(newBarImageAdapter);
    }

    public void removeImage(int option){
        barImage.remove(option);
        getView().changeIcon(getView().HAVEIMAGE);
    }
}
