package com.example.spb.model.inter;

import com.example.spb.entity.Dto.UserRegisteredDto;
import com.example.spb.presenter.callback.MyCallBack;

import java.io.File;

/**
 * @author nmy
 * @title: UserRegisteredModel
 * @date 2022-01-30 15:12
 */
public interface UserRegisteredModel {

    void userRegistered(UserRegisteredDto userRegisteredDto, File file, MyCallBack callBack);

}
