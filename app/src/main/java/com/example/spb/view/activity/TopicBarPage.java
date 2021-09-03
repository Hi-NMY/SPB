package com.example.spb.view.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.spb.R;
import com.example.spb.adapter.FragmentViewPageAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.entity.Topic;
import com.example.spb.presenter.impl.TopicBarPageAPresenterImpl;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.MyDateClass;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.example.spb.presenter.otherimpl.DataAttentionTopicPresenter;
import com.example.spb.view.Component.MyToastClass;
import com.example.spb.view.fragment.FragmentSpbAvtivityBar;
import com.example.spb.view.Component.MySmartRefresh;
import com.example.spb.view.fragment.topicbarpage.HotTopicBar;
import com.example.spb.view.fragment.topicbarpage.NewTopicBar;
import com.example.spb.view.fragment.topicbarpage.VideoTopicBar;
import com.example.spb.view.inter.ITopicBarPageAView;
import com.example.spb.view.littlefun.ScaleTransitionPagerTitleView;
import com.google.android.material.appbar.AppBarLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.makeramen.roundedimageview.RoundedImageView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import pl.droidsonroids.gif.GifImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TopicBarPage extends BaseMVPActivity<ITopicBarPageAView, TopicBarPageAPresenterImpl> implements ITopicBarPageAView, View.OnClickListener {

    private FragmentSpbAvtivityBar bar;
    private SimplePagerTitleView simplePagerTitleView;
    private static final String[] title = new String[]{"最新", "最热", "视频"};
    private List<String> myTitleList = Arrays.asList(title);
    private ArrayList<Fragment> fragments;
    private FragmentManager fragmentManager;
    private FragmentViewPageAdapter pagerAdapter;
    private CommonNavigator commonNavigator;
    private MagicIndicator mTopicbarIdt;
    private ViewPager mTopicbarViewpager;
    private RelativeLayout mTopicbarBarR;
    private AppBarLayout mTopicbarAppbarlayout;
    private Topic topic;
    private RoundedImageView mTopicbarImg;
    private TextView mTopicbarName;
    private TextView mTopicbarAttentionNum;
    private TextView mTopicbarBarNum;
    private TextView mTopicbarSlogan;
    private Button mTopicbarAttentionBtn;
    private RelativeLayout mTopicbarRBg;
    private GifImageView mTopicbarRefreshGif;
    private SmartRefreshLayout mTopicbarRefresh;
    private MySmartRefresh mySmartRefresh;
    private RelativeLayout mExcessR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_bar_page);
        topic = (Topic) getIntent().getSerializableExtra(InValues.send(R.string.intent_Topic));
        initActView();
    }

    @Override
    protected TopicBarPageAPresenterImpl createPresenter() {
        return new TopicBarPageAPresenterImpl();
    }

    @Override
    protected void initActView() {
        mExcessR = (RelativeLayout) findViewById(R.id.excess_r);
        mTopicbarIdt = (MagicIndicator) findViewById(R.id.topicbar_idt);
        mTopicbarViewpager = (ViewPager) findViewById(R.id.topicbar_viewpager);
        mTopicbarBarR = (RelativeLayout) findViewById(R.id.topicbar_bar_R);
        mTopicbarAppbarlayout = (AppBarLayout) findViewById(R.id.topicbar_appbarlayout);
        mTopicbarImg = (RoundedImageView) findViewById(R.id.topicbar_img);
        mTopicbarName = (TextView) findViewById(R.id.topicbar_name);
        mTopicbarAttentionNum = (TextView) findViewById(R.id.topicbar_attention_num);
        mTopicbarBarNum = (TextView) findViewById(R.id.topicbar_bar_num);
        mTopicbarSlogan = (TextView) findViewById(R.id.topicbar_slogan);
        mTopicbarAttentionBtn = (Button) findViewById(R.id.topicbar_attention_btn);
        mTopicbarRBg = (RelativeLayout) findViewById(R.id.topicbar_R_bg);
        mTopicbarRefreshGif = (GifImageView) findViewById(R.id.topicbar_refresh_gif);
        mTopicbarRefresh = (SmartRefreshLayout) findViewById(R.id.topicbar_refresh);
        mySmartRefresh = new MySmartRefresh(mTopicbarRefresh, mTopicbarRefreshGif, null);
        intFollowViewPager();
        setActivityBar();
        setBar();
        setMyListener();
        createDialog();
        createRefresh();
        mPresenter.obtainTopicInfo(topic,getDataAttentionTopicPresenter().determineTopic(topic), new TopicBarPageAPresenterImpl.OnReturn() {
            @Override
            public void onReturn(Topic t) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        topic = t;
                        mPresenter.setTopiCName(topic.getTopic_name());
                        mPresenter.returnAttentionKey(getDataAttentionTopicPresenter().determineAttention(topic.getId()));
                        mExcessR.setVisibility(View.GONE);
                        initData();
                    }
                });
            }
        });
    }

    @Override
    protected void initData() {
        mTopicbarName.setText(topic.getTopic_name());
        mTopicbarAttentionNum.setText(MyDateClass.sendMath(topic.getTopic_attentionnum()));
        mTopicbarBarNum.setText(MyDateClass.sendMath(topic.getTopic_barnum()));
        mTopicbarSlogan.setText(topic.getTopic_slogan());
        if (mPresenter.attentionKey) {
            yesAtt();
        } else {
            noAtt();
        }
        Glide.with(this)
                .load(InValues.send(R.string.httpHeadert) + topic.getTopic_image())
                .into(mTopicbarImg);
        Glide.with(this)
                .load(InValues.send(R.string.httpHeadert) + mPresenter.obtainBlurImg(topic.getTopic_image()))
                .into(new CustomViewTarget<RelativeLayout, Drawable>(mTopicbarRBg) {
                    @Override
                    protected void onResourceCleared(@Nullable Drawable placeholder) {

                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {

                    }

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        mTopicbarRBg.setBackground(resource);
                    }
                });
    }

    public void yesAtt() {
        mTopicbarAttentionBtn.setBackground(getDrawable(R.drawable.already_attention));
        mTopicbarAttentionBtn.setText("已关注");
    }

    public void noAtt() {
        mTopicbarAttentionBtn.setBackground(getDrawable(R.drawable.enter_next_login));
        mTopicbarAttentionBtn.setText("关注");
    }

    private void intFollowViewPager() {
        fragments = new ArrayList<>();
        fragments.add(new NewTopicBar());
        fragments.add(new HotTopicBar());
        fragments.add(new VideoTopicBar());

        fragmentManager = getSupportFragmentManager();

        pagerAdapter = new FragmentViewPageAdapter(fragmentManager, fragments, 3);
        mTopicbarViewpager.setOffscreenPageLimit(2);
        mTopicbarViewpager.setAdapter(pagerAdapter);
        highViewPager();
    }

    private void highViewPager() {
        commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return myTitleList == null ? 0 : myTitleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(myTitleList.get(index));
                simplePagerTitleView.setTextSize(18);
                simplePagerTitleView.setNormalColor(Color.parseColor("#808080"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#000000"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mTopicbarViewpager.setCurrentItem(index);
                    }
                });

                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 4));
                indicator.setLineWidth(UIUtil.dip2px(context, 12));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(Color.parseColor("#46B3E6"));
                return indicator;
            }
        });

        mTopicbarIdt.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mTopicbarIdt, mTopicbarViewpager);
        mTopicbarViewpager.setCurrentItem(1);
        mTopicbarViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                SpbBroadcast.sendReceiver(MyApplication.getContext(),InValues.send(R.string.Bcr_stop_voice),0,null);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private AppBarLayout.OnOffsetChangedListener listenViewMove() {
        AppBarLayout.OnOffsetChangedListener onOffsetChangedListener = new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                int persent = -i * 3 / 4;
                if (persent > 255) {
                    persent = 255;
                    bar.barCentralTxt(topic.getTopic_name(), new FragmentSpbAvtivityBar.OnMyClick() {
                        @Override
                        public void onClick() {
                            mTopicbarAppbarlayout.setExpanded(true);
                        }
                    });
                    bar.barLeftImg(R.drawable.left_return, null);
                } else {
                    bar.barCentralTxt("", null);
                    bar.barLeftImg(R.drawable.left_return_white, null);
                }
                int color = Color.argb(persent, 249, 249, 249);
                mTopicbarBarR.setBackgroundColor(color);
            }
        };
        return onOffsetChangedListener;
    }

    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MyToastClass.ShowToast(MyApplication.getContext(),"huidiao");
            }
        });
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
        mTopicbarAttentionBtn.setOnClickListener(this);
        mTopicbarAppbarlayout.addOnOffsetChangedListener(listenViewMove());
    }

    @Override
    public void setBar() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    public void setActivityBar() {
        bar = setMyActivityBar(R.id.topicbar_bar);
        bar.setBarBackground(R.color.picture_color_transparent);
        bar.barLeftImg(R.drawable.left_return_white, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }

    @Override
    public void createRefresh() {
        mySmartRefresh.setMyRefreshListener(new MySmartRefresh.MyRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.obtainTopicBarList(topic, true, new TopicBarPageAPresenterImpl.StopRefresh() {
                    @Override
                    public void stop() {
                        finishRRefresh(0);
                    }
                });
                if (getEasyVoice() != null){
                    getEasyVoice().stopPlayer();
                }
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }
        });
        mySmartRefresh.autoRefresh();
        mySmartRefresh.closeLoadMore();
    }

    @Override
    public void finishRRefresh(int num) {
        mySmartRefresh.finishMyRefresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.topicbar_attention_btn:
                if (mPresenter.attentionKey) {
                    noAtt();
                    getDataAttentionTopicPresenter().removeAttentionTopic(mPresenter.addAttentionAccount
                            (getDataUserMsgPresenter().getUser_account(), topic), new DataAttentionTopicPresenter.ReturnTopic() {
                        @Override
                        public void onReturn() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mTopicbarAttentionNum.setText(MyDateClass.sendMath(Integer.valueOf(mTopicbarAttentionNum.getText().toString()) - 1));
                                    mTopicbarAttentionNum.postInvalidate();
                                    SpbBroadcast.sendReceiver(MyApplication.getContext(),InValues.send(R.string.Bcr_reAttTopic),1,null);
                                    SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_refresh_topic), 0, null);
                                }
                            });
                            mPresenter.returnAttentionKey(getDataAttentionTopicPresenter().determineAttention(topic.getId()));
                        }
                    });
                } else {
                    yesAtt();
                    getDataAttentionTopicPresenter().addAttentionTopic(topic, mPresenter.addAttentionAccount
                            (getDataUserMsgPresenter().getUser_account(), topic), new DataAttentionTopicPresenter.ReturnTopic() {
                        @Override
                        public void onReturn() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mTopicbarAttentionNum.setText(MyDateClass.sendMath(Integer.valueOf(mTopicbarAttentionNum.getText().toString()) + 1));
                                    mTopicbarAttentionNum.postInvalidate();
                                    SpbBroadcast.sendReceiver(MyApplication.getContext(),InValues.send(R.string.Bcr_reAttTopic),1,null);
                                    SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_refresh_topic), 0, null);
                                }
                            });
                            mPresenter.returnAttentionKey(getDataAttentionTopicPresenter().determineAttention(topic.getId()));
                        }
                    });
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpbBroadcast.sendReceiver(this,InValues.send(R.string.Bcr_stop_voice),0,null);
        if (getEasyVoice() != null){
            getEasyVoice().stopPlayer();
            setEasyVoice(null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (getEasyVoice() != null){
            getEasyVoice().stopPlayer();
            setEasyVoice(null);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SpbBroadcast.sendReceiver(this,InValues.send(R.string.Bcr_stop_voice),0,null);
    }
}
