package com.example.spb.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.entity.Bar;
import com.example.spb.entity.Topic;
import com.example.spb.presenter.utils.*;
import com.example.spb.presenter.otherimpl.DataLikePresenter;
import com.example.spb.view.Component.BarMoreOperateDialog;
import com.example.spb.view.Component.MyToastClass;
import com.example.spb.view.Component.ThumbAnima;
import com.example.spb.view.activity.PersonalSpacePage;
import com.example.spb.view.activity.PostBarDetailPage;
import com.example.spb.view.activity.TopicBarPage;
import com.example.spb.view.utils.JumpIntent;
import com.makeramen.roundedimageview.RoundedImageView;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import java.util.List;

public class PostVideoAdapter extends RecyclerView.Adapter<PostVideoAdapter.ViewHolder> {

    private Activity activity;
    private BaseMVPActivity baseMVPActivity;
    private List<Bar> videoList;
    private Bar videoBar;
    private String cacheKey = "";
    private String commentIDKey = "";
    private LayoutInflater layoutInflater;
    private BarMoreOperateDialog barMoreOperateDialog;
    private String nowTopicName;

    public PostVideoAdapter(Activity activity, List<Bar> videoList) {
        this.activity = activity;
        this.videoList = videoList;
        this.baseMVPActivity = (BaseMVPActivity)activity;
        cacheKey = MyDateClass.showNowDate();
        layoutInflater = activity.getLayoutInflater();
    }

    public void setNowTopicId(String a){
        this.nowTopicName = a;
    }

    public void refreshBitmap(Bitmap bitmap,int position){
        videoList.get(position).setVideoBitmap(bitmap);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                notifyItemChanged(position);
            }
        });
    }

    public void addMorePostBar(List<Bar> moreBars){
        this.videoList.addAll(moreBars);
        notifyItemRangeChanged(videoList.size() - moreBars.size(), videoList.size() + 1);
    }

    public void refreshLikeItem(int num,String pbId){
        Bar cachebar = videoList.stream().filter(videoList -> videoList.getPb_one_id().equals(pbId)).findAny().orElse(null);
        if (cachebar != null){
            int a = videoList.indexOf(cachebar);
            if (a != -1){
                videoList.get(a).setPb_thumb_num(videoList.get(a).getPb_thumb_num() + num);
                notifyItemChanged(a);
            }
        }
    }

    public void refreshCommentItem(int num){
        Bar cachebar = videoList.stream().filter(videoList -> videoList.getPb_one_id().equals(commentIDKey)).findAny().orElse(null);
        if (cachebar != null){
            int a = videoList.indexOf(cachebar);
            if (a != -1){
                videoList.get(a).setPb_comment_num(videoList.get(a).getPb_comment_num() + num);
                notifyItemChanged(a);
            }
        }
    }

    public void refreshNowCommentItem(int num){
        Bar cachebar = videoList.stream().filter(videoList -> videoList.getPb_one_id().equals(commentIDKey)).findAny().orElse(null);
        if (cachebar != null){
            int a = videoList.indexOf(cachebar);
            if (a != -1){
                videoList.get(a).setPb_comment_num(num);
                notifyItemChanged(a);
            }
        }
    }

    public void deleteBar(String pbId){
        if (videoList != null && videoList.size() != 0){
            Bar cachebar = videoList.stream().filter(videoList -> videoList.getPb_one_id().equals(pbId)).findAny().orElse(null);
            if (cachebar != null){
                int a = videoList.indexOf(cachebar);
                if (a != -1){
                    videoList.remove(a);
                    notifyItemRemoved(a);
                    notifyItemRangeChanged(a, videoList.size() + 1);
                }
            }
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView mItemPostvideoUserHeadimg;
        TextView mItemPostvideoUsername;
        ImageView mItemPostvideoUserbadge;
        TextView mItemPostvideoPostdate;
        ImageView mItemPostvideoMore;
        StandardGSYVideoPlayer mDetailPlayer;
        CardView mVideoCard;
        TextView mItemPostvideoTxt;
        TagFlowLayout mItemPostvideoTopic;
        TextView mItemPostvideoLocation;
        ImageView mItemPostvideoCommentImg;
        TextView mItemPostvideoCommentNum;
        RelativeLayout mItemPostvideoCommentR;
        ImageView mItemPostvideoLikeImg;
        TextView mItemPostvideoLikeNum;
        RelativeLayout mItemPostvideoLikeR;
        RelativeLayout mItemPostvideoRA;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemPostvideoUserHeadimg = (RoundedImageView) itemView.findViewById(R.id.item_postvideo_user_headimg);
            mItemPostvideoUsername = (TextView) itemView.findViewById(R.id.item_postvideo_username);
            mItemPostvideoUserbadge = (ImageView) itemView.findViewById(R.id.item_postvideo_userbadge);
            mItemPostvideoPostdate = (TextView)itemView.findViewById(R.id.item_postvideo_postdate);
            mItemPostvideoMore  = (ImageView) itemView.findViewById(R.id.item_postvideo_more);
            mDetailPlayer = (StandardGSYVideoPlayer)itemView.findViewById(R.id.detail_player);
            mVideoCard  = (CardView)itemView.findViewById(R.id.video_card);
            mItemPostvideoTxt = (TextView)itemView.findViewById(R.id.item_postvideo_txt);
            mItemPostvideoTopic  = (TagFlowLayout)itemView.findViewById(R.id.item_postvideo_topic);
            mItemPostvideoLocation = (TextView)itemView.findViewById(R.id.item_postvideo_location);
            mItemPostvideoCommentImg  = (ImageView) itemView.findViewById(R.id.item_postvideo_comment_img);
            mItemPostvideoCommentNum = (TextView)itemView.findViewById(R.id.item_postvideo_comment_num);
            mItemPostvideoCommentR  = (RelativeLayout) itemView.findViewById(R.id.item_postvideo_comment_R);
            mItemPostvideoLikeImg = (ImageView) itemView.findViewById(R.id.item_postvideo_like_img);
            mItemPostvideoLikeNum  = (TextView)itemView.findViewById(R.id.item_postvideo_like_num);
            mItemPostvideoLikeR = (RelativeLayout) itemView.findViewById(R.id.item_postvideo_like_R);
            mItemPostvideoRA = (RelativeLayout)itemView.findViewById(R.id.item_postvideo_RA);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_video_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        videoBar = videoList.get(position);
        holder.mItemPostvideoUsername.setText(videoBar.getUser_name());
        holder.mItemPostvideoPostdate.setText(MyDateClass.showDateClass(videoBar.getPb_date()));

        if(holder.mItemPostvideoUserHeadimg.getTag() == null || !holder.mItemPostvideoUserHeadimg.getTag().equals(cacheKey)){
            Glide.with(activity)
                    .load(InValues.send(R.string.httpHeader) + "/UserImageServer/" + videoBar.getUser_account() + "/HeadImage/myHeadImage.png")
                    .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()),1,1))
                    .into(holder.mItemPostvideoUserHeadimg);
            holder.mItemPostvideoUserHeadimg.setTag(cacheKey);
        }

        if (videoBar.getUser_badge() == null || videoBar.getUser_badge().equals("")){
            holder.mItemPostvideoUserbadge.setVisibility(View.INVISIBLE);
        }else {
            holder.mItemPostvideoUserbadge.setVisibility(View.VISIBLE);
            //显示徽章！！
            Glide.with(activity)
                    .load(InValues.send(R.string.httpHeader) + "/UserImageServer/badge/" + videoBar.getUser_badge())
                    .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()), 1, 1))
                    .centerCrop()
                    .into(holder.mItemPostvideoUserbadge);
        }

        if (videoBar.getPb_article() != null && !videoBar.getPb_article().equals("")){
            holder.mItemPostvideoTxt.setVisibility(View.VISIBLE);
            holder.mItemPostvideoTxt.setText(videoBar.getPb_article());
        }

        if (videoBar.getPb_location() != null && !videoBar.getPb_location().equals("")){
            holder.mItemPostvideoLocation.setVisibility(View.VISIBLE);
            holder.mItemPostvideoLocation.setText(videoBar.getPb_location());
        }else {
            holder.mItemPostvideoLocation.setVisibility(View.GONE);
        }

        if (videoBar.getPb_comment_num() != 0){
            holder.mItemPostvideoCommentNum.setVisibility(View.VISIBLE);
            holder.mItemPostvideoCommentNum.setText(MyDateClass.sendMath(videoBar.getPb_comment_num()));
        }else {
            holder.mItemPostvideoCommentNum.setVisibility(View.INVISIBLE);
        }

        if (videoBar.getPb_thumb_num() != 0){
            holder.mItemPostvideoLikeNum.setVisibility(View.VISIBLE);
            holder.mItemPostvideoLikeNum.setText(MyDateClass.sendMath(videoBar.getPb_thumb_num()));
        }else {
            holder.mItemPostvideoLikeNum.setVisibility(View.INVISIBLE);
        }

        if (baseMVPActivity.getDataLikePresenter().determineLike(videoBar.getPb_one_id())){
            holder.mItemPostvideoLikeImg.setBackground(MyApplication.getContext().getDrawable(R.drawable.icon_likeal));
        }

        holder.mItemPostvideoRA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                //直接跳转动态详细
                JumpIntent.startMsgIntent(PostBarDetailPage.class, new JumpIntent.SetMsg() {
                    @Override
                    public void setMessage(Intent intent) {
                        //用于修改评论数
                        commentIDKey = videoList.get(position).getPb_one_id();
                        videoList.get(position).setVideoBitmap(null);
                        intent.putExtra(InValues.send(R.string.intent_Bar),videoList.get(position));
                    }
                });
            }
        });

        holder.mItemPostvideoUserHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转用户主页
                JumpIntent.startMsgIntent(PersonalSpacePage.class, new JumpIntent.SetMsg() {
                    @Override
                    public void setMessage(Intent intent) {
                        intent.putExtra(InValues.send(R.string.intent_User_account),videoList.get(position).getUser_account());
                    }
                });
            }
        });

        holder.mItemPostvideoMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示dialog更多功能
                barMoreOperateDialog = new BarMoreOperateDialog(activity);
                barMoreOperateDialog.setData(baseMVPActivity.getDataFollowPresenter().determineFollow(videoList.get(position).getUser_account()),
                        baseMVPActivity.getDataCollectBarPresenter().determineCollect(videoList.get(position).getPb_one_id()),
                        videoList.get(position).getPb_one_id(),videoList.get(position).getUser_account(),videoList.get(position).getUser_name());
                if (!videoList.get(position).getUser_account().equals(baseMVPActivity.getDataUserMsgPresenter().getUser_account())){
                    barMoreOperateDialog.funChat();
                    barMoreOperateDialog.funCollect();
                    barMoreOperateDialog.funFOllow();
                    barMoreOperateDialog.funReport();
                }else {
                    barMoreOperateDialog.funCollect();
                    barMoreOperateDialog.funReport();
                }
                barMoreOperateDialog.showMyDialog();
            }
        });

        holder.mItemPostvideoCommentR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接跳转动态详细
                JumpIntent.startMsgIntent(PostBarDetailPage.class, new JumpIntent.SetMsg() {
                    @Override
                    public void setMessage(Intent intent) {
                        commentIDKey = videoList.get(position).getPb_one_id();
                        videoList.get(position).setVideoBitmap(null);
                        intent.putExtra(InValues.send(R.string.intent_Bar),videoList.get(position));
                        //跳转并升起键盘
                        intent.putExtra(InValues.send(R.string.intent_keyboard_start),true);
                    }
                });
            }
        });

        holder.mItemPostvideoLikeR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //执行点赞动画。更改数据
                baseMVPActivity.getDataLikePresenter().updateLikeData(videoList.get(position).getPb_one_id()
                        , baseMVPActivity.getDataUserMsgPresenter().getUser_account(),videoList.get(position).getUser_account(), new DataLikePresenter.OnReturn() {
                            @Override
                            public void removeLike() {
                                holder.mItemPostvideoLikeImg.setBackground(MyApplication.getContext().getDrawable(R.drawable.icon_like));
                                SpbBroadcast.sendReceiver(MyApplication.getContext(),InValues.send(R.string.Bcr_refresh_thumb),-1,videoList.get(position).getPb_one_id());
                            }

                            @Override
                            public void addLike() {
                                ThumbAnima.thumbAnimation(holder.mItemPostvideoLikeImg);
                                SpbBroadcast.sendReceiver(MyApplication.getContext(),InValues.send(R.string.Bcr_refresh_thumb),+1,videoList.get(position).getPb_one_id());
                                Task.setNewLikeData(videoList.get(position).getPb_one_id());
                            }
                        });
            }
        });

        if (videoBar.getPb_topic() != null && !videoBar.getPb_topic().equals("") && !videoBar.getPb_topic().equals("null")){
            holder.mItemPostvideoTopic.setVisibility(View.VISIBLE);
            holder.mItemPostvideoTopic.setAdapter(new TagAdapter<Topic>(MyResolve.InTopic(videoBar.getPb_topic())) {
                @Override
                public View getView(FlowLayout parent, int position, Topic o) {
                    View view = layoutInflater.inflate(R.layout.item_tag_two, holder.mItemPostvideoTopic, false);
                    TextView textView = (TextView) view.findViewById(R.id.text);
                    ImageView imageView = (ImageView) view.findViewById(R.id.delete_tag);
                    imageView.setVisibility(View.GONE);
                    textView.setText(o.getTopic_name());
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (nowTopicName != null && o.getTopic_name().equals(nowTopicName)) {
                                MyToastClass.ShowToast(MyApplication.getContext(),"正在浏览该话题噢");
                            }else {
                                //跳转话题详细页
                                JumpIntent.startMsgIntent(TopicBarPage.class, new JumpIntent.SetMsg() {
                                    @Override
                                    public void setMessage(Intent intent) {
                                        intent.putExtra(InValues.send(R.string.intent_Topic),o);
                                        Task.setNewTopicData(o.getTopic_name());
                                    }
                                });
                            }
                        }
                    });
                    return view;
                }
            });
        }else {
            holder.mItemPostvideoTopic.setVisibility(View.INVISIBLE);
        }

        if (videoBar.getVideoBitmap() == null){
            VideoTool.gethttpBitmap(MyApplication.getContext(), position
                    , InValues.send(R.string.httpHeadert) + videoBar.getPb_video(), new VideoTool.OnReturnBitmap() {
                        @Override
                        public void onReturn(Bitmap bitmap, int position) {
                            refreshBitmap(bitmap,position);
                        }
                    });
        }else {
            VideoTool videoTool = new VideoTool(baseMVPActivity,MyApplication.getContext(),holder.mDetailPlayer);
            videoTool.creatVideo(InValues.send(R.string.httpHeadert) + videoBar.getPb_video(),videoBar.getVideoBitmap());
        }
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }
}
