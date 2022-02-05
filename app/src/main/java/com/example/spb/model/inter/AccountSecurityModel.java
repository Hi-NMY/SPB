package com.example.spb.model.inter;

import com.example.spb.entity.Dto.UpdatePasswordDto;
import com.example.spb.entity.Dto.VerifyPasswordDto;
import com.example.spb.presenter.callback.MyCallBack;

/**
 * @author nmy
 * @title: AccountSecurityModel
 * @date 2022-01-30 14:38
 */
public interface AccountSecurityModel {

    void updateUserPassword(UpdatePasswordDto updatePasswordDto, MyCallBack callBack);

    void queryVerifyAndUserFull(String userAccount, MyCallBack callBack);

    void queryVerifyUserPassword(VerifyPasswordDto verifyPasswordDto, MyCallBack callBack);

}
