package com.example.spb.presenter.impl;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.adapter.UserDiaryAdapter;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Diary;
import com.example.spb.presenter.inter.IUserDiaryPageAPresenter;
import com.example.spb.presenter.utils.MyDateClass;
import com.example.spb.view.inter.IUserDiaryPageAView;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

public class UserDiaryPageAPresenterImpl extends BasePresenter<IUserDiaryPageAView> implements IUserDiaryPageAPresenter {

    private String date;
    private String messgae;
    private String imagePath;
    private int weather;
    private BaseMVPActivity baseMVPActivity;

    public UserDiaryPageAPresenterImpl(Activity activity) {
        baseMVPActivity = (BaseMVPActivity)activity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessgae() {
        return messgae;
    }

    public void setMessgae(String messgae) {
        this.messgae = messgae;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getWeather() {
        return weather;
    }

    public void setWeather(int weather) {
        this.weather = weather;
    }

    public void obtainDate(OnReturn onReturn){
        setDate(MyDateClass.showNowDate());
        String week = MyDateClass.showWeekTable(MyDateClass.showNowDate(),0);
        String timeString = MyDateClass.obtainPeriod(MyDateClass.showNowTime());
        String year = String.valueOf(MyDateClass.showYear());
        String month = String.valueOf(MyDateClass.showMonth());
        String monthday = String.valueOf(MyDateClass.showMonthDay());
        if (Integer.valueOf(month) < 10){
            month = "0" + month;
        }
        if (Integer.valueOf(monthday) < 10){
            monthday = "0" + monthday;
        }
        onReturn.onReturn(monthday,month,year,week,timeString);
    }

    public void getHeadImage(List<LocalMedia> result,ImageReturn imageReturn){
        for (LocalMedia media : result) {
            if (media.isCut() && media.isCompressed()){
                setImagePath(media.getCompressPath());
            }else {
                setImagePath(media.getCutPath());
            }
        }

        if (isAttachView()){
            imageReturn.onReturn();
        }
    }

    public void sendNewDiary(){
        Diary diary = new Diary();
        diary.setCacheAccount(baseMVPActivity.getDataUserMsgPresenter().getUser_account());
        diary.setDia_date(getDate());
        diary.setDia_image(getImagePath());
        diary.setDia_message(getMessgae());
        diary.setDia_weather(getWeather());
        baseMVPActivity.getDataDiaryPresenter().addNewDiary(diary);
        setImagePath("");
        setMessgae("");
    }

    public void addDiary(List<Diary> d, RecyclerView recyclerView){
        UserDiaryAdapter userDiaryAdapter = new UserDiaryAdapter(d,baseMVPActivity);
        recyclerView.setAdapter(userDiaryAdapter);
    }

    public void removeDiary(){

    }

    public interface OnReturn{
        void onReturn(String monthDay,String month,String year,String week,String timeString);
    }

    public interface ImageReturn{
        void onReturn();
    }
}
