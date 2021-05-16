package com.example.spb.presenter.littlefun;

import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.text.format.DateFormat;
import com.yalantis.ucrop.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

public class VoiceObtain {
    private MediaRecorder mediaRecorder;
    private static String filePath;
    private static String fileName;
    private static String n;

    public VoiceObtain(MediaRecorder m) {
        this.mediaRecorder = m;
    }

    public boolean startVoice(){
            try {
                //设置麦克风
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                //设置输出格式
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                //设置编码
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                fileName = DateFormat.format("yyyyMMdd_HHmmss", Calendar.getInstance(Locale.CHINA)) + ".m4a";
                String audioSaveDir = isExisDir("mygoodvoice") + fileName;
                if (!FileUtils.isExternalStorageDocument(Uri.parse(FileUtils.getCreateFileName(audioSaveDir)))) {
                    FileUtils.rename(audioSaveDir);
                }
                filePath = audioSaveDir;
                //准备
                mediaRecorder.setOutputFile(filePath);
                mediaRecorder.prepare();
                //开始
                mediaRecorder.start();
                return true;
            } catch (IOException e) {
                return false;
            }
    }

    public String stopVoice(){
        n = filePath;
        try {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            filePath = "";
        }catch (Exception e){
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;

            File file = new File(filePath);
            if (file.exists()){
                file.delete();
                filePath = "";
            }
        }
        return n;
    }

    public String isExisDir(String saveDir) throws IOException {
        File downloadFile = new File(Environment.getExternalStorageDirectory(),saveDir);
        if (!downloadFile.mkdirs()){
            downloadFile.createNewFile();
        }

        String savePath = downloadFile.getAbsolutePath();
        return savePath;
    }
}
