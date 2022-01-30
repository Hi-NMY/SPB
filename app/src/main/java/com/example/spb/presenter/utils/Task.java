package com.example.spb.presenter.utils;

import android.content.SharedPreferences;
import com.example.spb.R;

import java.util.List;

public class Task {

    private static List<String> id;
    private static List<String> topic;

    public static void initTaskLike(){
        SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_task_like));
        editor.putString(InValues.send(R.string.task_pbid),"");
        editor.apply();
    }

    public static void initTaskTopic(){
        SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_task_Topic));
        editor.putString(InValues.send(R.string.task_topic),"");
        editor.apply();
    }

    public static void setNewLikeData(String pbID){
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuffer stringBuffer = new StringBuffer();
                SharedPreferences sharedPreferences = MySharedPreferences.getShared(InValues.send(R.string.Shared_task_like));
                String pid = sharedPreferences.getString(InValues.send(R.string.task_pbid),"");
                if (pid == null || pid.equals("")){
                    stringBuffer.append(pbID + "&");
                }else {
                    id = MyResolve.InSignLike(pid);
                    if (id.stream().filter(id -> id.equals(pbID)).findAny().orElse(null) == null){
                        stringBuffer.append(pid).append(pbID + "&");
                    }else {
                        stringBuffer.append(pid);
                    }
                }
                SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_task_like));
                editor.putString(InValues.send(R.string.task_pbid),String.valueOf(stringBuffer));
                editor.apply();
            }
        }).start();
    }

    public static boolean getLikeData(){
        SharedPreferences sharedPreferences = MySharedPreferences.getShared(InValues.send(R.string.Shared_task_like));
        String pid = sharedPreferences.getString(InValues.send(R.string.task_pbid),"");
        if (pid == null || pid.equals("")){
            return false;
        }else {
            id = MyResolve.InSignLike(pid);
            if (id.size() >=10 ){
                return true;
            }else {
                return false;
            }
        }
    }

    public static void setNewTopicData(String topicName){
        new Thread(new Runnable() {
            @Override
            public void run() {
                StringBuffer stringBuffer = new StringBuffer();
                SharedPreferences sharedPreferences = MySharedPreferences.getShared(InValues.send(R.string.Shared_task_Topic));
                String topicid = sharedPreferences.getString(InValues.send(R.string.task_topic),"");
                if (topicid == null || topicid.equals("")){
                    stringBuffer.append(topicName + "&");
                }else {
                    topic = MyResolve.InSignTopic(topicid);
                    if (topic.stream().filter(topic -> topic.equals(topicName)).findAny().orElse(null) == null){
                        stringBuffer.append(topicid).append(topicName + "&");
                    }else {
                        stringBuffer.append(topicid);
                    }
                }
                SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_task_Topic));
                editor.putString(InValues.send(R.string.task_topic),String.valueOf(stringBuffer));
                editor.apply();
            }
        }).start();
    }

    public static boolean getTopicData(){
        SharedPreferences sharedPreferences = MySharedPreferences.getShared(InValues.send(R.string.Shared_task_Topic));
        String topicid = sharedPreferences.getString(InValues.send(R.string.task_topic),"");
        if (topicid == null || topicid.equals("")){
            return false;
        }else {
            topic = MyResolve.InSignTopic(topicid);
            if (topic.size() >= 5 ){
                return true;
            }else {
                return false;
            }
        }
    }



}
