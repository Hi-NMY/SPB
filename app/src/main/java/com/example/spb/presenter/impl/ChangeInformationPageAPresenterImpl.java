package com.example.spb.presenter.impl;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.CityJsonBean;
import com.example.spb.entity.User;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.model.impl.UserModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IChangeInformationPageAPresenter;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.MyDateClass;
import com.example.spb.presenter.littlefun.MyResolve;
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
import java.util.*;

public class ChangeInformationPageAPresenterImpl extends BasePresenter<IChangeInformationPageAView> implements IChangeInformationPageAPresenter {

    private SpbModelBasicInter userModel;
    private List<CityJsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    public String user_birth;
    public String user_home;
    public String user_profile;
    public String user_name;
    public StringBuffer user_favorite;
    public List<String> uf;
    public List<String> strings;

    public ChangeInformationPageAPresenterImpl() {
        userModel = new UserModelImpl();
        user_favorite = new StringBuffer();
        uf = new ArrayList<>();
    }

    public void setUser_birth(Date user_birth) {
        this.user_birth = MyDateClass.getStringDate(user_birth);
    }

    public void setUser_home(String user_home) {
        this.user_home = user_home;
    }

    public void setUser_profile(String user_profile) {
        this.user_profile = user_profile;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void getUser_favorite(String a) {
        if (user_favorite == null || user_favorite.length() == 0){
            user_favorite.append(a);
        }
    }

    public void addFavorite(String a){
        uf.add(a);
    }

    public void clearFavorite(String a){
        uf.remove(a);
    }

    public boolean verificationString(String a){
        strings = MyResolve.InFaTag(String.valueOf(user_favorite));
        uf = new ArrayList<>();
        uf.addAll(strings);
        if (strings != null && strings.size() != 0){
            return strings.contains(a);
        }else {
            return false;
        }
    }

    public void setUser_favorite(){
        user_favorite = new StringBuffer();
        for (String a:uf){
            user_favorite.append(a + "|");
        }
    }

    public void deleteFa(){
        uf = new ArrayList<>();
    }

    public User updateUser(String user_account){
        User user = new User();
        user.setUser_account(user_account);
        if (user_name == null || user_name.equals("")){
            user.setUser_name(getView().getUser_name());
        }else {
            user.setUser_name(user_name);
        }
        if (user_birth == null || user_birth.equals("")){
            user.setUser_birth(getView().getUser_birth());
        }else {
            user.setUser_birth(user_birth);
        }
        user.setUser_favorite(String.valueOf(user_favorite));
        if (user_profile == null || user_profile.equals("")){
            user.setUser_profile(getView().getUser_profile());
        }else {
            user.setUser_profile(user_profile);
        }
        if (user_home == null || user_home.equals("")){
            user.setUser_home(getView().getUser_home());
        }else {
            user.setUser_home(user_home);
        }
        userModel.updateData(userModel.DATAUSER_UPDATE_ONE, user, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    getView().response(user,Integer.valueOf(a));
                } catch (IOException e) {
                    getView().response(null,202);
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int t) {

            }
        });
        return user;
    }

    public void updateRong(String account,String name){
        UserInfo userInfo = new UserInfo(account, name,
                Uri.parse(InValues.send(R.string.httpHeader) + "/UserImageServer/" + account + "/HeadImage/myHeadImage.png"));
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

        String JsonData = getJson(MyApplication.getContext(), "CityJson.json");

        ArrayList<CityJsonBean> jsonBean = parseData(JsonData);

        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {
            ArrayList<String> cityList = new ArrayList<>();
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);
                ArrayList<String> city_AreaList = new ArrayList<>();

                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
              //  city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);
            }

            options2Items.add(cityList);
            options3Items.add(province_AreaList);
        }
    }

    private String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
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
