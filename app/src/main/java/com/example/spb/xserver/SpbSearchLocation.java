package com.example.spb.xserver;

import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.example.spb.entity.LocationGps;

import java.util.ArrayList;
import java.util.List;

public class SpbSearchLocation {

    private static String city;
    private static String search;
    private static SuggestionSearch suggestionSearch;
    public static List<LocationGps> locationGpsList;
    private static LocationGps locationGps;
    public static void Search(String c, String s,OnReturn onReturn){
        city = c;
        search = s;
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
                            locationGps.setLocationDetail(suggestionResult.getAllSuggestions().get(j).getCity());
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

    interface OnReturn{
        void onSuccess(List<LocationGps> locationGpsList);
        void onError();
    }
}
