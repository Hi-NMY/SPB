package com.example.spb.view.activity;

import android.content.Context;
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
import com.example.spb.view.Component.FragmentSpbAvtivityBar;
import com.example.spb.view.Component.SelectImage;
import com.example.spb.view.InterTotal.SpbInterOne;
import com.example.spb.view.littlefun.MyToastClass;
import com.example.spb.view.littlefun.RequestForAccess;
import com.gyf.immersionbar.ImmersionBar;
import com.king.zxing.CaptureHelper;
import com.king.zxing.Intents;
import com.king.zxing.OnCaptureCallback;
import com.king.zxing.ViewfinderView;
import com.king.zxing.camera.CameraManager;
import com.king.zxing.util.CodeUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;

import java.io.File;
import java.util.List;

public class QRPage extends AppCompatActivity implements SpbInterOne, OnCaptureCallback {

    public static final String KEY_RESULT = Intents.Scan.RESULT;

    private SurfaceView surfaceView;
    private ViewfinderView viewfinderView;
    private View ivTorch;
    private FragmentSpbAvtivityBar bar;
    private SelectImage spbSelectImage;
    private String selectImgFile;

    private String TITLETXT = "扫描二维码";
    private String RIGHTTXT = "相册";

    private CaptureHelper mCaptureHelper;
    private ImageView mIvImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_page);
        initUI();
        mCaptureHelper.onCreate();
    }

    /**
     * 初始化
     */
    public void initUI() {
        setBar();
        setActivityBar();
        spbSelectImage = new SelectImage(this);
        mIvImg = (ImageView) findViewById(R.id.ivImg);
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

            }
        });
    }

    public void initCaptureHelper() {
        mCaptureHelper = new CaptureHelper(this, surfaceView, viewfinderView, ivTorch);
        mCaptureHelper.setOnCaptureCallback(this);
    }

    /**
     * {@link #viewfinderView} 的 ID
     *
     * @return 默认返回{@code R.id.viewfinderView}, 如果不需要扫码框可以返回0
     */
    public int getViewfinderViewId() {
        return R.id.viewfinderView;
    }


    /**
     * 预览界面{@link #surfaceView} 的ID
     *
     * @return
     */
    public int getSurfaceViewId() {
        return R.id.surfaceView;
    }

    /**
     * 获取 {@link #ivTorch} 的ID
     *
     * @return 默认返回{@code R.id.ivTorch}, 如果不需要手电筒按钮可以返回0
     */
    public int getIvTorchId() {
        return 0;
    }

    /**
     * Get {@link CaptureHelper}
     *
     * @return {@link #mCaptureHelper}
     */
    public CaptureHelper getCaptureHelper() {
        return mCaptureHelper;
    }

    /**
     * Get {@link CameraManager} use {@link #getCaptureHelper()#getCameraManager()}
     *
     * @return {@link #mCaptureHelper#getCameraManager()}
     */
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
        finish();
        MyToastClass.ShowToast(MyApplication.getContext(),result);
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
        bar.barCentralTxt(TITLETXT,null);
        bar.barLeftImg(R.drawable.left_return_white, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
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
