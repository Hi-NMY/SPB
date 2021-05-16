package com.example.spb.xserver;

import android.content.Context;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.example.spb.app.MyApplication;

public class SpbLocationServer {

    public LocationClient locationClient = null;
    public SpbLocationListener locationListener = new SpbLocationListener();

    public SpbLocationServer() {
    }

    public SpbLocationServer(Context context){
        SDKInitializer.initialize(context);
        locationClient = new LocationClient(context);
        locationClient.registerLocationListener(locationListener);
        LocationClientOption locationClientOption = new LocationClientOption();
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        locationClientOption.setCoorType("GCJ02");
        locationClientOption.setIsNeedAddress(true);
        locationClientOption.setNeedNewVersionRgc(true);
        locationClientOption.setIsNeedLocationPoiList(true);
        locationClientOption.setOpenGps(true);
        locationClient.setLocOption(locationClientOption);
        locationClient.start();
    }

    public SpbLocationListener obtainListener(){
        return locationListener;
    }

    public String obtainNowCity(){
        return locationListener.nowCity;
    }

}
