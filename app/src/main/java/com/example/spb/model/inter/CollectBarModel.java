package com.example.spb.model.inter;

import com.example.spb.entity.Dto.CollectBarDto;
import com.example.spb.presenter.callback.MyCallBack;

/**
 * @author nmy
 * @title: CollectBarModel
 * @date 2022-01-30 14:46
 */
public interface CollectBarModel {

    void queryCollectBarList(String userAccount, MyCallBack callBack);

    void queryCollectBarFullList(String userAccount, MyCallBack callBack);

    void addCollectBar(CollectBarDto collectBarDto, String cacheAccount, MyCallBack callBack);

    void deleteCollectBar(CollectBarDto collectBarDto, MyCallBack callBack);
}
