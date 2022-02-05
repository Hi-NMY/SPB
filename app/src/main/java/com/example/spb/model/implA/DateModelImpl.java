package com.example.spb.model.implA;

import com.example.spb.R;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.model.inter.DateModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.InValues;

/**
 * @author nmy
 * @title: DateModelImpl
 * @date 2022-01-30 14:54
 */
public class DateModelImpl extends SpbModelAbstrate implements DateModel {
    @Override
    public void dateTime(MyCallBack callBack) {
        sendHttp(InValues.send(R.string.dateTime), null, callBack);
    }
}
