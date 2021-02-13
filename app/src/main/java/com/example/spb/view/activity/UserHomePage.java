package com.example.spb.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.spb.R;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.UserHomePageAPresenterImpl;
import com.example.spb.presenter.inter.IUserHomePageAPresenter;
import com.example.spb.view.fragment.ui.messagepage.MessagePage;
import com.example.spb.view.fragment.ui.postbarpage.PostBarPage;
import com.example.spb.view.fragment.ui.userpage.UserPage;
import com.example.spb.view.fragment.ui.videopage.VideoPage;
import com.example.spb.view.inter.IUserHomePageAView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.gyf.immersionbar.ImmersionBar;


public class UserHomePage extends BaseMVPActivity<IUserHomePageAView,UserHomePageAPresenterImpl> implements IUserHomePageAView {

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

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home_page);
        initActView();
        selectionFragment(1);
    }

    @Override
    protected UserHomePageAPresenterImpl createPresenter() {
        return new UserHomePageAPresenterImpl();
    }

    @Override
    protected void initActView() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.homepage_bottom_btn);

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
        switch (index) {
            case 1:
                if (postBarPage == null) {
                    postBarPage = new PostBarPage();
                    fragmentTransaction.add(R.id.homepage_fragment, postBarPage);
                    PAGENUMBER = 1;
                } else {
                    fragmentTransaction.show(postBarPage);
                    PAGENUMBER = 1;
                }
                break;
            case 2:
                if (videoPage == null) {
                    videoPage = new VideoPage();
                    fragmentTransaction.add(R.id.homepage_fragment, videoPage);
                    PAGENUMBER = 2;
                } else {
                    fragmentTransaction.show(videoPage);
                    PAGENUMBER = 2;
                }
                break;
            case 3:
                if (messagePage == null) {
                    messagePage = new MessagePage();
                    fragmentTransaction.add(R.id.homepage_fragment, messagePage);
                    PAGENUMBER = 3;
                } else {
                    fragmentTransaction.show(messagePage);
                    PAGENUMBER = 3;
                }
                break;
            case 4:
                if (userPage == null) {
                    userPage = new UserPage();
                    fragmentTransaction.add(R.id.homepage_fragment, userPage);
                    PAGENUMBER = 4;
                } else {
                    fragmentTransaction.show(userPage);
                    PAGENUMBER = 4;
                }
                break;
        }
        setActivityBar();
        fragmentTransaction.commitAllowingStateLoss();
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

    public BottomNavigationView.OnNavigationItemSelectedListener getItemListener(){
        BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
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
        bottomNavigationView.setOnNavigationItemSelectedListener(getItemListener());
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
        switch (PAGENUMBER){
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
        }
    }
}
