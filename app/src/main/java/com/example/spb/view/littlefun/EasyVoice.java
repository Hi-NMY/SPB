package com.example.spb.view.littlefun;

import android.media.MediaPlayer;
import com.example.spb.presenter.impl.SendNewBarPageAPresenterImpl;

import java.io.IOException;

public class EasyVoice {

    private MediaPlayer mediaPlayer;
    private int voiceTime = 0;
    private int cacheTime = 0;
    private boolean voicePlayerKey = true;
    private DownTime downTime;
    private OnVoice onVoice;
    private String url;

    public EasyVoice(String url,OnVoice o) {
        this.onVoice = o;
        this.url = url;
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            voiceTime = mediaPlayer.getDuration()/1000;
            cacheTime = mediaPlayer.getDuration()/1000;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void returnSet(){
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getVoiceTime() {
        return voiceTime;
    }

    public boolean isVoicePlayerKey() {
        return voicePlayerKey;
    }

    public void startPlayer(){
        returnSet();
        mediaPlayer.start();
        voicePlayerKey = false;
        downTime = new DownTime();
        downTime.start();
    }

    public void stopPlayer(){
        if (mediaPlayer != null){
            mediaPlayer.release();
            voicePlayerKey = true;
            voiceTime = cacheTime;
            onVoice.onStop(cacheTime);
        }
    }

    public void onDestroyVoice(){

    }

    class DownTime extends Thread {
        @Override
        public void run() {
            while (voiceTime != 0 && !voicePlayerKey) {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (voiceTime != 0 && !voicePlayerKey) {
                    voiceTime = voiceTime - 1;
                    onVoice.onStart(voiceTime);
                }
            }
            if (voiceTime == 0) {
                stopPlayer();
            }
        }
    }

    public interface OnVoice{
        void onStart(int time);
        void onStop(int cacheTime);
        void onDestroy();
    }
}
