package com.example.spb.xserver;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.Poi;
import com.example.spb.entity.LocationGps;

import java.util.ArrayList;
import java.util.List;

public class SpbLocationListener extends BDAbstractLocationListener {

    private Poi poi;
    private String name = "";
    public List<LocationGps> locationGpsList;
    private LocationGps locationGps;
    public String nowCity;

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        locationGpsList = new ArrayList<>();

//        double latitude = bdLocation.getLatitude();    //获取纬度信息
//        double longitude = bdLocation.getLongitude();    //获取经度信息
//        float radius = bdLocation.getRadius();    //获取定位精度，默认值为0.0f
//
//        String coorType = bdLocation.getCoorType();
//        //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
//
//        int errorCode = bdLocation.getLocType();
        //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
//
//        PoiSearch mPoiSearch = PoiSearch.newInstance();
//        mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
//            @Override
//            public void onGetPoiResult(PoiResult poiResult) {
//                Log.d("baidugpsaaaa","a");
//
//
//            }
//
//            @Override
//            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
//                Log.d("baidugpsaaaa","b");
//            }
//
//            @Override
//            public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
//                Log.d("baidugpsaaaa","c");
//            }
//
//            @Override
//            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
//                Log.d("baidugpsaaaa","d");
//            }
//        });

//        PoiNearbySearchOption poiNearbySearchOption = new PoiNearbySearchOption();
//        poiNearbySearchOption.location(new LatLng(latitude,longitude));
//        poiNearbySearchOption.radius(600);
//        poiNearbySearchOption.pageNum(1);
//        poiNearbySearchOption.pageCapacity(20);
//        mPoiSearch.searchNearby(poiNearbySearchOption);
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
