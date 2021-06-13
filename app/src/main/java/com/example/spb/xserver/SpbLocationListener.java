package com.example.spb.xserver;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.example.spb.app.MyApplication;
import com.example.spb.entity.LocationGps;
import com.example.spb.view.Component.MyToastClass;

import java.util.ArrayList;
import java.util.List;

public class SpbLocationListener extends BDAbstractLocationListener {

    private String name = "";
    public List<LocationGps> locationGpsList;
    private LocationGps locationGps;
    public String nowCity = null;
    public int locType = 62;

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        locationGpsList = new ArrayList<>();
        locType = bdLocation.getLocType();

        for (int i = 0 ; i <= 2 ; i++){
            if (i == 0){
                locationGps = new LocationGps();
                locationGps.setLocationName("不显示位置信息");
                locationGps.setLocationDetail("");
                locationGpsList.add(locationGps);
            }else if (i == 1){
                locationGps = new LocationGps();
                nowCity = bdLocation.getCity();
                locationGps.setLocationName(bdLocation.getCity());
                locationGps.setLocationDetail("");
                locationGpsList.add(locationGps);
            }else if (i > 1){
                for (int j = 0 ; j < bdLocation.getPoiList().size() ; j++){
                    locationGps = new LocationGps();
                    locationGps.setLocationName(bdLocation.getPoiList().get(j).getName());
                    locationGps.setLocationDetail(bdLocation.getPoiList().get(j).getAddr());
                    locationGpsList.add(locationGps);
                }
            }
        }
    }
}
