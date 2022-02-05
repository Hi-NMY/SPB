package com.example.spb.presenter.otherimpl;

import android.content.ContentValues;
import android.content.SharedPreferences;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.entity.Notice;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MySharedPreferences;
import com.example.spb.presenter.utils.SpbBroadcast;
import org.litepal.LitePal;

import java.util.List;

public class DataNoticePresenter {

    public List<Notice> notices;
    public List<Notice> systemNotices;

    public DataNoticePresenter() {
        obtainNotice(true);
    }

    public List<Notice> obtainNotice(boolean key){
        SharedPreferences sharedPreferences = MySharedPreferences.getShared(InValues.send(R.string.Shared_Pushseenum));
        SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_Pushseenum));
        int num = sharedPreferences.getInt(InValues.send(R.string.num), 0);
        notices = LitePal.where("push_fun != ?","5").order("notice_date desc").find(Notice.class);
        if (key){
            if (num != notices.size()){
                SpbBroadcast.sendReceiver(MyApplication.getContext(),InValues.send(R.string.Bcr_new_messasge),0,null);
            }else {
                SpbBroadcast.sendReceiver(MyApplication.getContext(),InValues.send(R.string.Bcr_new_messasge),1,null);
            }
        }
        return notices;
    }

    public List<Notice> obtainSystemNotice(SystemMessage systemMessage){
        systemNotices = LitePal.where("push_fun = ?","5").order("notice_date desc").find(Notice.class);
        Notice n = systemNotices.stream().filter(systemNotices -> systemNotices.getSee() == 1).findAny().orElse(null);
        if (n != null){
            systemMessage.onReturn();
        }
        return systemNotices;
    }

    public void updateSystemSee(){
        ContentValues values = new ContentValues();
        values.put("see", 0);
        LitePal.updateAll(Notice.class, values, "push_fun = ?", "5");
    }

    public void updateSee(String date){
        ContentValues values = new ContentValues();
        values.put("see", 0);
        LitePal.updateAll(Notice.class, values, "notice_date = ?", date);
        SharedPreferences sharedPreferences = MySharedPreferences.getShared(InValues.send(R.string.Shared_Pushseenum));
        SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_Pushseenum));
        editor.putInt(InValues.send(R.string.num),sharedPreferences.getInt(InValues.send(R.string.num),0) + 1);
        editor.apply();
    }

    public void alreadySee(){
        List<Notice> n = LitePal.where("see = ?", "1").find(Notice.class);
        if (n == null || n.size() == 0){
            SpbBroadcast.sendReceiver(MyApplication.getContext(),InValues.send(R.string.Bcr_new_messasge),1,null);
        }
    }

    public interface SystemMessage{
        void onReturn();
    }
}
