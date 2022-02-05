package com.example.spb.model.inter;

import com.example.spb.entity.Dto.UserInformationDto;
import com.example.spb.presenter.callback.MyCallBack;

import java.io.File;

/**
 * @author nmy
 * @title: UserModel
 * @date 2022-01-30 15:09
 */
public interface UserModel {

    void querySchoolTable(MyCallBack callBack);

    void querySearchUser(String search, MyCallBack callBack);

    void updateUserPersonalInformation(UserInformationDto info, MyCallBack callBack);

    void updateUserIp(String userAccount, String ip, MyCallBack callBack);

    void updateUserToken(String userAccount, String token, MyCallBack callBack);

    void updateUserHeadImage(File file, String userAccount, MyCallBack callBack);

    void updateUserBgImage(File file, String userAccount, MyCallBack callBack);

    void updateUserBadgeImage(String userBadge, String userAccount, MyCallBack callBack);

    void updateUserPrivacy(String userPrivacy, String userAccount, MyCallBack callBack);

    void deleteUserIp(String userAccount, MyCallBack callBack);

}
