package com.example.spb.view.activity;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.spb.R;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.AttentionTopicPageAPresenterImpl;
import com.example.spb.presenter.inter.IAttentionTopicPageAPresenter;
import com.example.spb.view.Component.FragmentSpbAvtivityBar;
import com.example.spb.view.inter.IAttentionTopicPageAView;
import com.gyf.immersionbar.ImmersionBar;

public class AttentionTopicPage extends BaseMVPActivity<IAttentionTopicPageAView,AttentionTopicPageAPresenterImpl>
        implements IAttentionTopicPageAView {

    private FragmentSpbAvtivityBar bar;
    private String TITLE = "我关注的话题";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention_topic_page);
        initActView();
    }

    @Override
    protected AttentionTopicPageAPresenterImpl createPresenter() {
        return new AttentionTopicPageAPresenterImpl();
    }

    @Override
    protected void initActView() {
        setActivityBar();
        setBar();
        setMyListener();
        createDialog();
    }

    @Override
    protected void initData() {

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
        bar = setMyActivityBar(R.id.attentiontopic_actbar);
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
        bar.barCentralTxt(TITLE,null);
    }

    @Override
    public void startRefresh() {

    }

    @Override
    public void obtainMoreRefresh() {

    }

    @Override
    public void stopRefresh() {

    }

    @Override
    public void stopMoreRefresh() {

    }
}
