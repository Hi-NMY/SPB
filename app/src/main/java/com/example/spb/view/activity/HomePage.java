package com.example.spb.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.UserHomePageAPresenterImpl;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.RequestForAccess;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.example.spb.view.Component.AppVersion;
import com.example.spb.view.Component.ComponentDialog;
import com.example.spb.view.fragment.FragmentSpbAvtivityBar;
import com.example.spb.view.Component.MyToastClass;
import com.example.spb.view.InterComponent.DialogInter;
import com.example.spb.view.InterComponent.ISpbAvtivityBarFView;
import com.example.spb.view.fragment.homepage.messagepage.MessagePage;
import com.example.spb.view.fragment.homepage.postbarpage.PostBarPage;
import com.example.spb.view.fragment.homepage.userpage.UserPage;
import com.example.spb.view.fragment.homepage.videopage.VideoPage;
import com.example.spb.view.inter.IUserHomePageAView;
import com.example.spb.view.littlefun.JumpIntent;
import com.example.spb.xserver.ObtainVersion;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gyf.immersionbar.ImmersionBar;
import com.shuyu.gsyvideoplayer.GSYVideoManager;


public class HomePage extends BaseMVPActivity<IUserHomePageAView, UserHomePageAPresenterImpl> implements IUserHomePageAView, View.OnClickListener {

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private PostBarPage postBarPage;
    private VideoPage videoPage;
    private MessagePage messagePage;
    private UserPage userPage;
    private int PAGENUMBER = 1;
    private int POSTBARPAGE = 1;
    private int VIDEOPAGE = 2;
    private int MESSAGEPAGE = 3;
    private int USERPAGE = 4;

    private ISpbAvtivityBarFView bar;
    private DialogInter dialogHomeSend;

    private BottomNavigationView bottomNavigationView;
    private RelativeLayout mHomepageSend;
    private RelativeLayout mPostbarRlt;
    private RelativeLayout mVideoRlt;
    private RelativeLayout mGoodsRlt;
    private View bar2;

    private NewMessage newMessage;
    private View mViewNewMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);
        newMessage = new NewMessage();
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_new_messasge), newMessage);
        initActView();
        selectionFragment(1);
    }

    @Override
    protected UserHomePageAPresenterImpl createPresenter() {
        return new UserHomePageAPresenterImpl();
    }

    @Override
    protected void initActView() {
        bar2 = (View) findViewById(R.id.homepage_bar);
        mViewNewMessage = (View) findViewById(R.id.view_newMessage);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.homepage_bottom_btn);
        mHomepageSend = (RelativeLayout) findViewById(R.id.homepage_send);
        mHomepageSend.bringToFront();
        bottomNavigationView.setItemIconTintList(null);

        setActivityBar();
        createDialog();
        setMyListener();
        setBar();
        initData();
    }

    @Override
    protected void initData() {

    }

    public void selectionFragment(int index) {
        if (fragmentManager == null) {
            fragmentManager = this.getSupportFragmentManager();
        }
        fragmentTransaction = fragmentManager.beginTransaction();
        hideFragments(fragmentTransaction);
        if (userPage == null || videoPage == null || messagePage == null || postBarPage == null) {
            userPage = new UserPage();
            fragmentTransaction.add(R.id.homepage_fragment, userPage);
            videoPage = new VideoPage();
            fragmentTransaction.add(R.id.homepage_fragment, videoPage);
            messagePage = new MessagePage();
            fragmentTransaction.add(R.id.homepage_fragment, messagePage);
            postBarPage = new PostBarPage();
            fragmentTransaction.add(R.id.homepage_fragment, postBarPage);
        }

        if (index != PAGENUMBER) {
            SpbBroadcast.sendReceiver(this, InValues.send(R.string.Bcr_stop_voice), 0, null);
        }

        switch (index) {
            case 1:
                fragmentTransaction.show(postBarPage);
                PAGENUMBER = 1;
                break;
            case 2:
                fragmentTransaction.show(videoPage);
                PAGENUMBER = 2;
                break;
            case 3:
                fragmentTransaction.show(messagePage);
                PAGENUMBER = 3;
                break;
            case 4:
                fragmentTransaction.show(userPage);
                PAGENUMBER = 4;
                SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_reUserPage_Datanum), 0, null);
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
        if (index == 1) {
            bar2.setVisibility(View.GONE);
            bar = null;
        } else {
            bar2.setVisibility(View.VISIBLE);
            setActivityBar();
        }
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (postBarPage != null) {
            transaction.hide(postBarPage);
        }
        if (videoPage != null) {
            transaction.hide(videoPage);
        }
        if (messagePage != null) {
            transaction.hide(messagePage);
        }
        if (userPage != null) {
            transaction.hide(userPage);
        }
    }

    public BottomNavigationView.OnNavigationItemSelectedListener getItemListener() {
        BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.postbar_page:
                        selectionFragment(POSTBARPAGE);
                        break;
                    case R.id.video_page:
                        selectionFragment(VIDEOPAGE);
                        break;
                    case R.id.message_page:
                        selectionFragment(MESSAGEPAGE);
                        break;
                    case R.id.user_page:
                        selectionFragment(USERPAGE);
                        break;
                }
                return true;
            }
        };
        return listener;
    }

    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.homepage_send:
                selectionFragment(POSTBARPAGE);
                bottomNavigationView.setSelectedItemId(R.id.postbar_page);
                showDialogS(0);
                break;
        }
    }

    @Override
    public void createDialog() {
        dialogHomeSend = new ComponentDialog(this, R.layout.dialog_homesend, R.style.dialogHomeSend, new ComponentDialog.InitDialog() {
            @Override
            public void initView(View view) {
                mPostbarRlt = (RelativeLayout) view.findViewById(R.id.postbar_Rlt);
                mVideoRlt = (RelativeLayout) view.findViewById(R.id.video_Rlt);
                mGoodsRlt = (RelativeLayout) view.findViewById(R.id.goods_Rlt);
            }

            @Override
            public void initData() {

            }

            @Override
            public void initListener() {
                mPostbarRlt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        JumpIntent.startMyIntent(SendNewBarPage.class);
                        closeDialog(0);
                    }
                });
                mVideoRlt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        JumpIntent.startMyIntent(SendNewVideoPage.class);
                        closeDialog(0);
                    }
                });
                mGoodsRlt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
        dialogHomeSend.setCancelable(true);
    }

    @Override
    public void showDialogS(int i) {
        dialogHomeSend.changePosition(bottomNavigationView);
        dialogHomeSend.showMyDialog();
    }

    @Override
    public void closeDialog(int i) {
        dialogHomeSend.closeMyDialog();
    }

    @Override
    public void setMyListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(getItemListener());
        mHomepageSend.setOnClickListener(this);
    }

    @Override
    public void setBar() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.beijing)
                .init();
    }

    @Override
    public void setActivityBar() {
        if (bar == null) {
            bar = setMyActivityBar(R.id.homepage_bar);
        }
        bar.setBarBackground(R.color.qianbai);
        switch (PAGENUMBER) {
            case 2:
                bar.setBarBackground(R.color.beijing);
                bar.barSearchView(new FragmentSpbAvtivityBar.OnMyClick() {
                    @Override
                    public void onClick() {
                        JumpIntent.startMyIntent(AllSearchPage.class);
                    }
                });
                break;
            case 3:
                bar.setBarBackground(R.color.beijing);
                bar.barCentralTxt(TITLE, null);
                bar.barRightImg1(R.drawable.adduser_icon, new FragmentSpbAvtivityBar.OnMyClick() {
                    @Override
                    public void onClick() {
                        JumpIntent.startMyIntent(SearchUserPage.class);
                    }
                });
                bar.barRightImg2(R.drawable.scancode_icon, new FragmentSpbAvtivityBar.OnMyClick() {
                    @Override
                    public void onClick() {
                        RequestForAccess.setCameraAccess(HomePage.this, new RequestForAccess.OnReturn() {
                            @Override
                            public void allTrue() {
                                startActivityForResult(new Intent(MyApplication.getContext(), QRPage.class), 1);
                            }

                            @Override
                            public void someTrue() {

                            }

                            @Override
                            public void allFalse() {
                                finish();
                                MyToastClass.ShowToast(MyApplication.getContext(), STRINGACCESS);
                            }

                            @Override
                            public void toTure() {

                            }

                            @Override
                            public void low() {
                                startActivityForResult(new Intent(MyApplication.getContext(), QRPage.class), 1);
                            }
                        });
                    }
                });
                break;
            case 4:
                bar.setBarBackground(R.color.beijing);
                bar.barRightImg1(R.drawable.install_icon, new FragmentSpbAvtivityBar.OnMyClick() {
                    @Override
                    public void onClick() {
                        JumpIntent.startMyIntent(SetUpPage.class);
                    }
                });
                bar.barRightImg2(R.drawable.qr_icon, new FragmentSpbAvtivityBar.OnMyClick() {
                    @Override
                    public void onClick() {
                        JumpIntent.startMyIntent(UserQrPage.class);
                    }
                });
                break;
        }
        bar.searchShow(null, 1);
    }

    private long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && PAGENUMBER != 1) {
            selectionFragment(1);
            bottomNavigationView.setSelectedItemId(R.id.postbar_page);
        }
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && PAGENUMBER == 1) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            MyToastClass.ShowToast(this, RETURN);
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public void onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(MyApplication.getContext())) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        SpbBroadcast.sendReceiver(this, InValues.send(R.string.Bcr_stop_voice), 0, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpbBroadcast.destroyBrc(newMessage);
        SpbBroadcast.sendReceiver(this, InValues.send(R.string.Bcr_stop_voice), 0, null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDataNoticePresenter();
        AppVersion appVersion = new AppVersion(MyApplication.getContext(),this);
        appVersion.startVersion(String.valueOf(ObtainVersion.versionCode(this)),false);
    }

    class NewMessage extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            getDataNoticePresenter().obtainNotice(false);
            int key = intent.getIntExtra("key_one",0);
            if (key == 0){
                mViewNewMessage.setVisibility(View.VISIBLE);
            }else{
                mViewNewMessage.setVisibility(View.INVISIBLE);
            }
        }
    }
}
