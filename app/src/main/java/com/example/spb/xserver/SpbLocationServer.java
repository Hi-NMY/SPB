package com.example.spb.xserver;

import android.content.Context;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;

public class SpbLocationServer {

    public LocationClient locationClient = null;
    public SpbLocationListener locationListener = new SpbLocationListener();

    public SpbLocationServer() {
    }

    public SpbLocationServer(Context context) {
        SDKInitializer.initialize(context);
        locationClient = new LocationClient(context);
        locationClient.registerLocationListener(locationListener);
        LocationClientOption locationClientOption = new LocationClientOption();
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        locationClientOption.setCoorType("GCJ02");
        locationClientOption.setIsNeedAddress(true);
        locationClientOption.setNeedNewVersionRgc(true);
        locationClientOption.setIsNeedLocationPoiList(true);
        locationClientOption.setOpenGps(true);
        locationClient.setLocOption(locationClientOption);
    }

    public SpbLocationListener obtainListener() {
        return locationListener;
    }

    public void stopGps() {
        locationListener.nowCity = null;
        locationListener.locType = 62;
        locationListener.locationGpsList = null;
        locationClient.stop();
    }

    public void startGps() {
        locationClient.start();
    }

    public String obtainNowCity() {
        return locationListener.nowCity;
    }
}
