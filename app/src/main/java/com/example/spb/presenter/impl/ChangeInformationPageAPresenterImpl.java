package com.example.spb.presenter.impl;

import android.content.Context;
import android.content.res.AssetManager;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.CityJsonBean;
import com.example.spb.presenter.inter.IChangeInformationPageAPresenter;
import com.example.spb.view.inter.IChangeInformationPageAView;
import com.google.gson.Gson;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ChangeInformationPageAPresenterImpl extends BasePresenter<IChangeInformationPageAView> implements IChangeInformationPageAPresenter {

    private List<CityJsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    public ChangeInformationPageAPresenterImpl() {
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
