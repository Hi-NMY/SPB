package com.example.spb.view.fragment.homepage.postbarpage;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.example.spb.R;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.presenter.impl.NewPostPageFPresenterImpl;
import com.example.spb.view.Component.ThumbAnima;
import com.example.spb.view.inter.INewPostPageFView;
import com.example.spb.view.littlefun.GIFShow;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class NewPostPage extends BaseMVPFragment<INewPostPageFView, NewPostPageFPresenterImpl> implements INewPostPageFView {

    private GifImageView mGifImageView;
    private ImageView mItemPostbarLikeImg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {

    }

    @Override
    protected NewPostPageFPresenterImpl createPresenter() {
        return new NewPostPageFPresenterImpl();
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_postbar_new_post_page;
    }

    @Override
    protected void initFragView(View view) {
        mItemPostbarLikeImg = (ImageView)view.findViewById(R.id.item_postbar_like_img);
        mGifImageView = (GifImageView) view.findViewById(R.id.item_postbar_voice);
        GIFShow gifShow = new GIFShow(mGifImageView);
        mGifImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gifShow.startGif();
            }
        });
        mItemPostbarLikeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThumbAnima.thumbAnimation(mItemPostbarLikeImg);
            }
        });
    }

    @Override
    protected void initData() {

    }
}
