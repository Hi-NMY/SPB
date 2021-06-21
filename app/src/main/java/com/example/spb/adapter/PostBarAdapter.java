package com.example.spb.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.entity.Bar;
import com.example.spb.entity.Topic;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.MyDateClass;
import com.example.spb.presenter.littlefun.MyResolve;
import com.example.spb.view.Component.MyToastClass;
import com.example.spb.view.Component.ThumbAnima;
import com.example.spb.view.activity.HomePage;
import com.example.spb.view.activity.TopicBarPage;
import com.example.spb.view.littlefun.BarImageInFlater;
import com.example.spb.view.littlefun.EasyVoice;
import com.example.spb.view.littlefun.GIFShow;
import com.example.spb.view.littlefun.JumpIntent;
import com.makeramen.roundedimageview.RoundedImageView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import pl.droidsonroids.gif.GifImageView;

import java.util.List;

public class PostBarAdapter extends RecyclerView.Adapter<PostBarAdapter.ViewHolder> {

    private Activity activity;
    private HomePage homePage;
    private List<Bar> bars;
    private Bar bar;
    private LayoutInflater layoutInflater;
    private GridLayoutManager gridLayoutManager;
    private PostBarImgAdapter postBarImgAdapter;

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView mItemPostbarUserHeadimg;
        TextView mItemPostbarUsername;
        TextView mItemPostbarPostdate;
        ImageView mItemPostbarMore;
        RecyclerView mItemPostbarImagelist;
        TextView mItemPostbarTxt;
        GifImageView mVoiceGif;
        TextView mVoiceTime;
        RelativeLayout mItemPostbarVoice;
        TagFlowLayout mItemPostbarTopic;
        TextView mItemPostbarLocation;
        TextView mItemPostbarCommentNum;
        RelativeLayout mItemPostbarCommentR;
        TextView mItemPostbarLikeNum;
        RelativeLayout mItemPostbarLikeR;
        RelativeLayout mItemPostbarRA;
        ImageView mItemPostbarLikeImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemPostbarUserHeadimg = (RoundedImageView) itemView.findViewById(R.id.item_postbar_user_headimg);
            mItemPostbarUsername = (TextView) itemView.findViewById(R.id.item_postbar_username);
            mItemPostbarPostdate = (TextView) itemView.findViewById(R.id.item_postbar_postdate);
            mItemPostbarMore = (ImageView) itemView.findViewById(R.id.item_postbar_more);
            mItemPostbarImagelist = (RecyclerView) itemView.findViewById(R.id.item_postbar_imagelist);
            mItemPostbarTxt = (TextView) itemView.findViewById(R.id.item_postbar_txt);
            mVoiceGif = (GifImageView) itemView.findViewById(R.id.voice_gif);
            mVoiceTime = (TextView) itemView.findViewById(R.id.voice_time);
            mItemPostbarVoice = (RelativeLayout) itemView.findViewById(R.id.item_postbar_voice);
            mItemPostbarTopic = (TagFlowLayout) itemView.findViewById(R.id.item_postbar_topic);
            mItemPostbarLocation = (TextView) itemView.findViewById(R.id.item_postbar_location);
            mItemPostbarCommentNum = (TextView) itemView.findViewById(R.id.item_postbar_comment_num);
            mItemPostbarCommentR = (RelativeLayout) itemView.findViewById(R.id.item_postbar_comment_R);
            mItemPostbarLikeNum = (TextView) itemView.findViewById(R.id.item_postbar_like_num);
            mItemPostbarLikeR = (RelativeLayout) itemView.findViewById(R.id.item_postbar_like_R);
            mItemPostbarRA = (RelativeLayout) itemView.findViewById(R.id.item_postbar_RA);
            mItemPostbarLikeImg = (ImageView) itemView.findViewById(R.id.item_postbar_like_img);
        }
    }

    public PostBarAdapter(Activity activity, List<Bar> bars) {
        this.activity = activity;
        this.bars = bars;
        homePage = (HomePage)activity;
        layoutInflater = activity.getLayoutInflater();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_bar_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        bar = bars.get(position);
        holder.mItemPostbarUsername.setText(bar.getUser_name());
        Glide.with(activity)
                .load(InValues.send(R.string.httpHeader) + "/UserImageServer/" + bar.getUser_account() + "/HeadImage/myHeadImage.png")
                .into(holder.mItemPostbarUserHeadimg);
        if (bar.getPb_article() != null && !bar.getPb_article().equals("")){
            holder.mItemPostbarTxt.setVisibility(View.VISIBLE);
            holder.mItemPostbarTxt.setText(bar.getPb_article());
        }
        holder.mItemPostbarPostdate.setText(MyDateClass.showDateClass(bar.getPb_date()));

        if (bar.getPb_location() != null && !bar.getPb_location().equals("")){
            holder.mItemPostbarLocation.setVisibility(View.VISIBLE);
            holder.mItemPostbarLocation.setText(bar.getPb_location());
        }

        if (bar.getPb_comment_num() != 0){
            holder.mItemPostbarCommentNum.setVisibility(View.VISIBLE);
            holder.mItemPostbarCommentNum.setText(MyDateClass.sendMath(bar.getPb_comment_num()));
        }

        if (bar.getPb_thumb_num() != 0){
            holder.mItemPostbarLikeNum.setVisibility(View.VISIBLE);
            holder.mItemPostbarLikeNum.setText(MyDateClass.sendMath(bar.getPb_thumb_num()));
        }

        if (homePage.getDataLikePresenter().determineLike(bar.getPb_one_id())){
            holder.mItemPostbarLikeImg.setBackground(MyApplication.getContext().getDrawable(R.drawable.icon_likeal));
        }

        holder.mItemPostbarRA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接跳转动态详细
            }
        });

        holder.mItemPostbarUserHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转用户主页
            }
        });

        holder.mItemPostbarMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示dialog更多功能
            }
        });

        holder.mItemPostbarCommentR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转动态详细并拉起键盘评论
            }
        });
        holder.mItemPostbarLikeR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //执行点赞动画。更改数据
                ThumbAnima.thumbAnimation(holder.mItemPostbarLikeImg);
            }
        });

        if (bar.getPb_topic() != null && !bar.getPb_topic().equals("") && !bar.getPb_topic().equals("null")){
            holder.mItemPostbarTopic.setAdapter(new TagAdapter<Topic>(MyResolve.InTopic(bar.getPb_topic())) {
                @Override
                public View getView(FlowLayout parent, int position, Topic o) {
                    View view = layoutInflater.inflate(R.layout.item_tag_two, holder.mItemPostbarTopic, false);
                    TextView textView = (TextView) view.findViewById(R.id.text);
                    ImageView imageView = (ImageView) view.findViewById(R.id.delete_tag);
                    imageView.setVisibility(View.GONE);
                    textView.setText(o.getTopic_name());
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //跳转话题详细页
                            JumpIntent.startMsgIntent(TopicBarPage.class, new JumpIntent.SetMsg() {
                                @Override
                                public void setMessage(Intent intent) {
                                    intent.putExtra(InValues.send(R.string.intent_Topic),o);
                                }
                            });
                        }
                    });
                    return view;
                }
            });
        }

        if (bar.getPb_image_url() != null && !bar.getPb_image_url().equals("")){
            holder.mItemPostbarImagelist.setVisibility(View.VISIBLE);
            gridLayoutManager = BarImageInFlater.getInflater(activity,bar.getPb_image_url());
            holder.mItemPostbarImagelist.setLayoutManager(gridLayoutManager);
            postBarImgAdapter = new PostBarImgAdapter(activity,MyResolve.InDoubleImage(bar.getPb_image_url()));
            holder.mItemPostbarImagelist.setAdapter(postBarImgAdapter);
        }

        final GIFShow[] gifShow = new GIFShow[1];
        if(bar.getPb_voice() != null && !bar.getPb_voice().equals("")){
            holder.mItemPostbarVoice.setVisibility(View.VISIBLE);
            gifShow[0] = new GIFShow(holder.mVoiceGif);
            EasyVoice easyVoice = new EasyVoice(InValues.send(R.string.httpHeadert) + bar.getPb_voice(), new EasyVoice.OnVoice() {
                @Override
                public void onStart(int time) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            holder.mVoiceTime.setText(String.valueOf(time));
                            holder.mVoiceTime.postInvalidate();
                            gifShow[0].startGif();
                        }
                    });
                }

                @Override
                public void onStop(int cacheTime) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            holder.mVoiceTime.setText(String.valueOf(cacheTime));
                            gifShow[0] = new GIFShow(holder.mVoiceGif);
                        }
                    });
                }

                @Override
                public void onDestroy() {

                }
            });
            holder.mVoiceTime.setText(String.valueOf(easyVoice.getVoiceTime()));
            holder.mItemPostbarVoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (easyVoice.isVoicePlayerKey()){
                        easyVoice.startPlayer();
                    }else {
                        easyVoice.stopPlayer();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return bars.size();
    }
}
