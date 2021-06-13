package com.example.spb.xserver;

import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.example.spb.entity.LocationGps;

import java.util.ArrayList;
import java.util.List;

public class SpbSearchLocation {

    private String city;
    private String search;
    private SuggestionSearch suggestionSearch;
    public List<LocationGps> locationGpsList;
    private LocationGps locationGps;

    public SpbSearchLocation(String city, String search) {
        this.city = city;
        this.search = search;
    }

    public void search(OnReturn onReturn){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(200);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        suggestionSearch = SuggestionSearch.newInstance();
        SuggestionSearchOption suggestionSearchOption = new SuggestionSearchOption();
        suggestionSearchOption.mCityLimit = true;
        suggestionSearch.setOnGetSuggestionResultListener(new OnGetSuggestionResultListener() {
            @Override
            public void onGetSuggestionResult(SuggestionResult suggestionResult) {
                locationGpsList = new ArrayList<>();
                try {
                    if (suggestionResult != null && suggestionResult.getAllSuggestions().size() != 0){
                        for (int j = 0 ; j < suggestionResult.getAllSuggestions().size() ; j++){
                            locationGps = new LocationGps();
                            locationGps.setLocationName(suggestionResult.getAllSuggestions().get(j).getKey());
                            locationGps.setLocationDetail(suggestionResult.getAllSuggestions().get(j).getAddress());
                            locationGpsList.add(locationGps);
                        }
                    }
                    onReturn.onSuccess(locationGpsList);
                }catch (Exception e){
                    onReturn.onError();
                }
            }
        });

        suggestionSearch.requestSuggestion(new SuggestionSearchOption()
                .city(city)
                .keyword(search));
    }

    public void stopSearchLoc(){
        suggestionSearch.destroy();
    }

    public interface OnReturn{
        void onSuccess(List<LocationGps> locationGpsList);
        void onError();
    }
}
