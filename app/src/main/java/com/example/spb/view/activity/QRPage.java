package com.example.spb.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.presenter.utils.DataEncryption;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.view.fragment.FragmentSpbAvtivityBar;
import com.example.spb.view.Component.SelectImage;
import com.example.spb.view.InterTotal.SpbInterOne;
import com.example.spb.view.utils.JumpIntent;
import com.example.spb.view.Component.MyToastClass;
import com.example.spb.xserver.ObtainServerDate;
import com.gyf.immersionbar.ImmersionBar;
import com.king.zxing.CaptureHelper;
import com.king.zxing.Intents;
import com.king.zxing.OnCaptureCallback;
import com.king.zxing.ViewfinderView;
import com.king.zxing.camera.CameraManager;
import com.king.zxing.util.CodeUtils;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class QRPage extends AppCompatActivity implements SpbInterOne, OnCaptureCallback {

    public static final String KEY_RESULT = Intents.Scan.RESULT;

    private SurfaceView surfaceView;
    private ViewfinderView viewfinderView;
    private View ivTorch;
    private FragmentSpbAvtivityBar bar;
    private SelectImage spbSelectImage;
    private String selectImgFile;

    private CaptureHelper mCaptureHelper;

    private String cacheDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_page);
        setDate();
        initUI();
        mCaptureHelper.onCreate();
    }

    public void setDate(){
        ObtainServerDate.obtainDate(new ObtainServerDate.OnReturn() {
            @Override
            public void onReturn(String date) {
                cacheDate = date;
            }
        });
    }

    public String getCacheDate() {
        return cacheDate;
    }

    public void initUI() {
        setBar();
        setActivityBar();
        spbSelectImage = new SelectImage(this);
        ImageView mIvImg =  findViewById(R.id.ivImg);
        surfaceView = findViewById(getSurfaceViewId());
        int viewfinderViewId = getViewfinderViewId();
        if (viewfinderViewId != 0) {
            viewfinderView = findViewById(viewfinderViewId);
        }
        int ivTorchId = getIvTorchId();
        if (ivTorchId != 0) {
            ivTorch = findViewById(ivTorchId);
            ivTorch.setVisibility(View.VISIBLE);
        }
        initCaptureHelper();
        mIvImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpIntent.startMyIntent(UserQrPage.class);
            }
        });
    }

    public void initCaptureHelper() {
        mCaptureHelper = new CaptureHelper(this, surfaceView, viewfinderView, ivTorch);
        mCaptureHelper.setOnCaptureCallback(this);
    }

    public int getViewfinderViewId() {
        return R.id.viewfinderView;
    }

    public int getSurfaceViewId() {
        return R.id.surfaceView;
    }

    public int getIvTorchId() {
        return 0;
    }

    public CaptureHelper getCaptureHelper() {
        return mCaptureHelper;
    }

    @Deprecated
    public CameraManager getCameraManager() {
        return mCaptureHelper.getCameraManager();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCaptureHelper.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCaptureHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCaptureHelper.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mCaptureHelper.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    /**
     * 接收扫码结果回调
     *
     * @param result 扫码结果
     * @return 返回true表示拦截，将不自动执行后续逻辑，为false表示不拦截，默认不拦截
     */
    @Override
    public boolean onResultCallback(String result) {
        try {
            String a = DataEncryption.outData(result);
            String account = a.substring(0,9);
            String date = a.substring(9);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = format.parse(getCacheDate());
            Date old = format.parse(date);
            if (old.getTime() > now.getTime()){
                finish();
                //跳转用户主页
                JumpIntent.startMsgIntent(PersonalSpacePage.class, new JumpIntent.SetMsg() {
                    @Override
                    public void setMessage(Intent intent) {
                        intent.putExtra(InValues.send(R.string.intent_User_account),account);
                    }
                });
            }else {
                long between = now.getTime() - old.getTime();
                long day = between / (24 * 60 * 60 * 1000);
                long hour = (between / (60 * 60 * 1000) - day * 24);
                long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
                long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
                if ((min * 60 + s) > 120){
                    MyToastClass.ShowToast(this,"二维码超时，请重试");
                }else {
                    finish();
                    //跳转用户主页
                    JumpIntent.startMsgIntent(PersonalSpacePage.class, new JumpIntent.SetMsg() {
                        @Override
                        public void setMessage(Intent intent) {
                            intent.putExtra(InValues.send(R.string.intent_User_account),account);
                        }
                    });
                }
            }
        }catch (Exception e){
            MyToastClass.ShowToast(this,"错误，请重试");
        }
        return true;
    }

    @Override
    public void createDialog() {

    }

    @Override
    public void showDialogS(int i) {

    }

    @Override
    public void closeDialog(int i) {

    }

    @Override
    public void setMyListener() {

    }

    @Override
    public void setBar() {
        ImmersionBar.with(this)
                .init();
    }

    @Override
    public void setActivityBar() {
        bar = (FragmentSpbAvtivityBar) getSupportFragmentManager().findFragmentById(R.id.qrpage_bar);
        bar.setBarBackground(R.color.TransColor);
        TextView textView = (TextView)bar.getmBarRightTxt1();
        TextView textView2 = (TextView)bar.getmBarCentralTxt();
        textView.setTextColor(ContextCompat.getColor(this,R.color.beijing));
        textView2.setTextColor(ContextCompat.getColor(this,R.color.beijing));
        String TITLETXT = "扫描二维码";
        bar.barCentralTxt(TITLETXT,null);
        bar.barLeftImg(R.drawable.left_return_white, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
        String RIGHTTXT = "相册";
        bar.barRightTxt1(RIGHTTXT, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                spbSelectImage.selectEasyImg(new OnResultCallbackListener<LocalMedia>(){
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        for (LocalMedia m : result) {
                            selectImgFile = m.getRealPath();
                        }
                        finish();
                        //解析结果
                        MyToastClass.ShowToast(MyApplication.getContext(),CodeUtils.parseCode(selectImgFile));
                    }

                    @Override
                    public void onCancel() {
                        //MyToastClass.ShowToast(MyApplication.getContext(),"出错了");
                    }
                });
            }
        });
    }
}
