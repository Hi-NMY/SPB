package com.example.spb.presenter.impl;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.common.RequestCode;
import com.example.spb.entity.CityJsonBean;
import com.example.spb.entity.Dto.UserInformationDto;
import com.example.spb.model.implA.UserModelImpl;
import com.example.spb.model.inter.UserModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IChangeInformationPageAPresenter;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MyDateClass;
import com.example.spb.presenter.utils.MyResolve;
import com.example.spb.view.Component.ResponseToast;
import com.example.spb.view.inter.IChangeInformationPageAView;
import com.google.gson.Gson;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChangeInformationPageAPresenterImpl extends BasePresenter<IChangeInformationPageAView> implements IChangeInformationPageAPresenter {

    private final UserModel userModel;
    private List<CityJsonBean> options1Items = new ArrayList<>();
    private final ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private final ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    public String userBirth;
    public String userHome;
    public String userProfile;
    public String userName;
    public StringBuffer userFavorite;
    public List<String> uf;
    public List<String> strings;

    public ChangeInformationPageAPresenterImpl() {
        userModel = new UserModelImpl();
        userFavorite = new StringBuffer();
        uf = new ArrayList<>();
    }

    public void setUserBirth(Date userBirth) {
        this.userBirth = MyDateClass.getStringDate(userBirth);
    }

    public void setUserHome(String userHome) {
        this.userHome = userHome;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void getUserFavorite(String a) {
        if (userFavorite == null || userFavorite.length() == 0) {
            userFavorite.append(a);
        }
    }

    public void addFavorite(String a) {
        uf.add(a);
    }

    public void clearFavorite(String a) {
        uf.remove(a);
    }

    public boolean verificationString(String a) {
        strings = MyResolve.InFaTag(String.valueOf(userFavorite));
        uf = new ArrayList<>();
        uf.addAll(strings);
        if (strings != null && strings.size() != 0) {
            return strings.contains(a);
        } else {
            return false;
        }
    }

    public void setUserFavorite() {
        userFavorite = new StringBuffer();
        for (String a : uf) {
            userFavorite.append(a).append("|");
        }
    }

    public void deleteFa() {
        uf = new ArrayList<>();
    }

    public void updateUser(String userAccount) {
        UserInformationDto user = new UserInformationDto();
        user.setUser_account(userAccount);
        if (userName == null || "".equals(userName)) {
            user.setUser_name(getView().getUser_name());
        } else {
            user.setUser_name(userName);
        }
        if (userBirth == null || "".equals(userBirth)) {
            user.setUser_birth(getView().getUser_birth());
        } else {
            user.setUser_birth(userBirth);
        }
        user.setUser_favorite(String.valueOf(userFavorite));
        if (userProfile == null || "".equals(userProfile)) {
            user.setUser_profile(getView().getUser_profile());
        } else {
            user.setUser_profile(userProfile);
        }
        if (userHome == null || "".equals(userHome)) {
            user.setUser_home(getView().getUser_home());
        } else {
            user.setUser_home(userHome);
        }
        userModel.updateUserPersonalInformation(user, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestCode requestCode = new Gson().fromJson(value, RequestCode.class);
                    boolean b = ResponseToast.toToast(requestCode);
                    if (b) {
                        getView().response(user, requestCode.getCode());
                    } else {
                        getView().response(null, 202);
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void updateRong(String account, String name) {
        UserInfo userInfo = new UserInfo(account, name,
                Uri.parse(InValues.send(R.string.prefix_img) + account + InValues.send(R.string.suffix_head_img)));
        RongIM.getInstance().refreshUserInfoCache(userInfo);
    }

    public List<CityJsonBean> getOptions1Items() {
        return options1Items;
    }

    public ArrayList<ArrayList<String>> getOptions2Items() {
        return options2Items;
    }

    public ArrayList<ArrayList<ArrayList<String>>> getOptions3Items() {
        return options3Items;
    }

    public void initCityJsonData() {

        String jsonData = getJson(MyApplication.getContext());

        ArrayList<CityJsonBean> jsonBean = parseData(jsonData);

        options1Items = jsonBean;

        for (CityJsonBean cityJsonBean : jsonBean) {
            ArrayList<String> cityList = new ArrayList<>();
            ArrayList<ArrayList<String>> provinceAreaList = new ArrayList<>();

            for (int c = 0; c < cityJsonBean.getCityList().size(); c++) {
                String cityName = cityJsonBean.getCityList().get(c).getName();
                cityList.add(cityName);
                ArrayList<String> cityAreaList = new ArrayList<>();

                if (cityJsonBean.getCityList().get(c).getArea() == null
                        || cityJsonBean.getCityList().get(c).getArea().size() == 0) {
                    cityAreaList.add("");
                } else {
                    cityAreaList.addAll(cityJsonBean.getCityList().get(c).getArea());
                }
                provinceAreaList.add(cityAreaList);
            }

            options2Items.add(cityList);
            options3Items.add(provinceAreaList);
        }
    }

    private String getJson(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("CityJson.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private ArrayList<CityJsonBean> parseData(String result) {
        ArrayList<CityJsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                CityJsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), CityJsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }
}
