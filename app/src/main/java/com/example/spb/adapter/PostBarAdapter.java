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
import com.bumptech.glide.signature.MediaStoreSignature;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.entity.Bar;
import com.example.spb.entity.Topic;
import com.example.spb.presenter.utils.*;
import com.example.spb.presenter.otherimpl.DataLikePresenter;
import com.example.spb.view.Component.BarMoreOperateDialog;
import com.example.spb.view.Component.ThumbAnima;
import com.example.spb.view.activity.PersonalSpacePage;
import com.example.spb.view.activity.PostBarDetailPage;
import com.example.spb.view.activity.TopicBarPage;
import com.example.spb.view.utils.BarImageInFlater;
import com.example.spb.view.utils.EasyVoice;
import com.example.spb.view.utils.GIFShow;
import com.example.spb.view.utils.JumpIntent;
import com.makeramen.roundedimageview.RoundedImageView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import pl.droidsonroids.gif.GifImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PostBarAdapter extends RecyclerView.Adapter<PostBarAdapter.ViewHolder> {

    private Activity activity;
    private BaseMVPActivity baseMVPActivity;
    private List<Bar> bars;
    private Bar bar;
    private LayoutInflater layoutInflater;
    private GridLayoutManager gridLayoutManager;
    private PostBarImgAdapter postBarImgAdapter;
    private String cacheKey = "";
    private BarMoreOperateDialog barMoreOperateDialog;
    private int cachePosition = -1;
    private String commentIDKey = "";
    private Map<Integer,String> timeMap;

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
        ImageView mItemPostbarUserbadge;

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
            mItemPostbarUserbadge = (ImageView) itemView.findViewById(R.id.item_postbar_userbadge);
        }
    }

    public PostBarAdapter(Activity activity, List<Bar> bars) {
        this.activity = activity;
        this.bars = bars;
        timeMap = new HashMap<>();
        cacheKey = MyDateClass.showNowDate();
        baseMVPActivity = (BaseMVPActivity) activity;
        layoutInflater = activity.getLayoutInflater();
    }

    public void addMorePostBar(List<Bar> moreBars) {
        this.bars.addAll(moreBars);
        notifyItemRangeChanged(bars.size() - moreBars.size(), bars.size() + 1);
    }

    public void refreshLikeItem(int num, String pbId) {
        Bar cachebar = bars.stream().filter(bars -> bars.getPb_one_id().equals(pbId)).findAny().orElse(null);
        if (cachebar != null) {
            int a = bars.indexOf(cachebar);
            if (a != -1) {
                bars.get(a).setPb_thumb_num(bars.get(a).getPb_thumb_num() + num);
                notifyItemChanged(a);
            }
        }
    }

    public void refreshCommentItem(int num) {
        Bar cachebar = bars.stream().filter(bars -> bars.getPb_one_id().equals(commentIDKey)).findAny().orElse(null);
        if (cachebar != null) {
            int a = bars.indexOf(cachebar);
            if (a != -1) {
                bars.get(a).setPb_comment_num(bars.get(a).getPb_comment_num() + num);
                notifyItemChanged(a);
            }
        }
    }

    public void refreshNowCommentItem(int num) {
        Bar cachebar = bars.stream().filter(bars -> bars.getPb_one_id().equals(commentIDKey)).findAny().orElse(null);
        if (cachebar != null) {
            int a = bars.indexOf(cachebar);
            if (a != -1) {
                bars.get(a).setPb_comment_num(num);
                notifyItemChanged(a);
            }
        }
    }

    public void deleteBar(String pbId) {
        if (bars != null && bars.size() != 0) {
            Bar cachebar = bars.stream().filter(bars -> bars.getPb_one_id().equals(pbId)).findAny().orElse(null);
            if (cachebar != null) {
                int a = bars.indexOf(cachebar);
                if (a != -1) {
                    bars.remove(a);
                    notifyItemRemoved(a);
                    notifyItemRangeChanged(a, bars.size() + 1);
                }
            }
        }
    }

    public void refreshVoiceTime(int position,String time){
        timeMap.put(position,time);
        notifyItemChanged(position);
    }

    public void refreshNoewVoice(int position) {
        if (position == -1) {
            notifyItemChanged(cachePosition);
        }else if (cachePosition == position){
            notifyItemChanged(position);
        }else {
            notifyItemChanged(cachePosition);
            notifyItemChanged(position);
        }
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

        if (holder.mItemPostbarUserHeadimg.getTag() == null || !holder.mItemPostbarUserHeadimg.getTag().equals(cacheKey)) {
            Glide.with(activity)
                    .load(InValues.send(R.string.prefix_img) + bar.getUser_account() + InValues.send(R.string.suffix_head_img))
                    .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()), 1, 1))
                    .into(holder.mItemPostbarUserHeadimg);
            holder.mItemPostbarUserHeadimg.setTag(cacheKey);
        }

        if (bar.getUser_badge() == null || bar.getUser_badge().equals("")){
            holder.mItemPostbarUserbadge.setVisibility(View.INVISIBLE);
        }else {
            holder.mItemPostbarUserbadge.setVisibility(View.VISIBLE);
            if (holder.mItemPostbarUserbadge.getTag() == null || !holder.mItemPostbarUserbadge.getTag().equals(cacheKey)){
                //显示徽章！
                Glide.with(activity)
                        .load(InValues.send(R.string.prefix_badge_img) + bar.getUser_badge())
                        .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()), 1, 1))
                        .centerCrop()
                        .into(holder.mItemPostbarUserbadge);
                holder.mItemPostbarUserbadge.setTag(cacheKey);
            }
        }

        if (bar.getPb_article() != null && !bar.getPb_article().equals("")) {
            holder.mItemPostbarTxt.setVisibility(View.VISIBLE);
            holder.mItemPostbarTxt.setText(bar.getPb_article());
        }
        holder.mItemPostbarPostdate.setText(MyDateClass.showDateClass(bar.getPb_date()));

        if (bar.getPb_location() != null && !bar.getPb_location().equals("")) {
            holder.mItemPostbarLocation.setVisibility(View.VISIBLE);
            holder.mItemPostbarLocation.setText(bar.getPb_location());
        }

        if (bar.getPb_comment_num() != 0) {
            holder.mItemPostbarCommentNum.setVisibility(View.VISIBLE);
            holder.mItemPostbarCommentNum.setText(MyDateClass.sendMath(bar.getPb_comment_num()));
        } else {
            holder.mItemPostbarCommentNum.setVisibility(View.INVISIBLE);
        }

        if (bar.getPb_thumb_num() != 0) {
            holder.mItemPostbarLikeNum.setVisibility(View.VISIBLE);
            holder.mItemPostbarLikeNum.setText(MyDateClass.sendMath(bar.getPb_thumb_num()));
        } else {
            holder.mItemPostbarLikeNum.setVisibility(View.INVISIBLE);
        }

        if (baseMVPActivity.getDataLikePresenter().determineLike(bar.getPb_one_id())) {
            holder.mItemPostbarLikeImg.setBackground(MyApplication.getContext().getDrawable(R.drawable.icon_likeal));
        }

        holder.mItemPostbarRA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接跳转动态详细
                JumpIntent.startMsgIntent(PostBarDetailPage.class, new JumpIntent.SetMsg() {
                    @Override
                    public void setMessage(Intent intent) {
                        commentIDKey = bars.get(position).getPb_one_id();
                        intent.putExtra(InValues.send(R.string.intent_Bar), bars.get(position));
                    }
                });
            }
        });

        holder.mItemPostbarUserHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转用户主页
                JumpIntent.startMsgIntent(PersonalSpacePage.class, new JumpIntent.SetMsg() {
                    @Override
                    public void setMessage(Intent intent) {
                        intent.putExtra(InValues.send(R.string.intent_User_account), bars.get(position).getUser_account());
                    }
                });
            }
        });

        holder.mItemPostbarMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示dialog更多功能
                barMoreOperateDialog = new BarMoreOperateDialog(activity);
                barMoreOperateDialog.setData(baseMVPActivity.getDataFollowPresenter().determineFollow(bars.get(position).getUser_account()),
                        baseMVPActivity.getDataCollectBarPresenter().determineCollect(bars.get(position).getPb_one_id()),
                        bars.get(position).getPb_one_id(), bars.get(position).getUser_account(), bars.get(position).getUser_name());
                if (!bars.get(position).getUser_account().equals(baseMVPActivity.getDataUserMsgPresenter().getUser_account())) {
                    barMoreOperateDialog.funChat();
                    barMoreOperateDialog.funCollect();
                    barMoreOperateDialog.funFOllow();
                    barMoreOperateDialog.funReport();
                } else {
                    barMoreOperateDialog.funCollect();
                    barMoreOperateDialog.funReport();
                }
                barMoreOperateDialog.showMyDialog();
            }
        });

        holder.mItemPostbarCommentR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接跳转动态详细
                JumpIntent.startMsgIntent(PostBarDetailPage.class, new JumpIntent.SetMsg() {
                    @Override
                    public void setMessage(Intent intent) {
                        commentIDKey = bars.get(position).getPb_one_id();
                        intent.putExtra(InValues.send(R.string.intent_Bar), bars.get(position));
                        intent.putExtra(InValues.send(R.string.intent_keyboard_start), true);
                    }
                });
            }
        });
        holder.mItemPostbarLikeR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //执行点赞动画。更改数据
                baseMVPActivity.getDataLikePresenter().updateLikeData(bars.get(position).getPb_one_id()
                        , baseMVPActivity.getDataUserMsgPresenter().getUser_account(), bars.get(position).getUser_account(), new DataLikePresenter.OnReturn() {
                            @Override
                            public void removeLike() {
                                holder.mItemPostbarLikeImg.setBackground(MyApplication.getContext().getDrawable(R.drawable.icon_like));
                                SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_refresh_thumb), -1, bars.get(position).getPb_one_id());
                            }

                            @Override
                            public void addLike() {
                                ThumbAnima.thumbAnimation(holder.mItemPostbarLikeImg);
                                SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_refresh_thumb), +1, bars.get(position).getPb_one_id());
                                Task.setNewLikeData(bars.get(position).getPb_one_id());
                            }
                        });
            }
        });

        if (bar.getPb_topic() != null && !bar.getPb_topic().equals("") && !bar.getPb_topic().equals("null")) {
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
                                    intent.putExtra(InValues.send(R.string.intent_Topic), o);
                                    Task.setNewTopicData(o.getTopic_name());
                                }
                            });
                        }
                    });
                    return view;
                }
            });
        }

        if (bar.getPb_image_url() != null && !bar.getPb_image_url().equals("")) {
            holder.mItemPostbarImagelist.setVisibility(View.VISIBLE);
            gridLayoutManager = BarImageInFlater.getInflater(activity, bar.getPb_image_url());
            holder.mItemPostbarImagelist.setLayoutManager(gridLayoutManager);
            postBarImgAdapter = new PostBarImgAdapter(activity, MyResolve.InDoubleImage(bar.getPb_image_url()));
            holder.mItemPostbarImagelist.setAdapter(postBarImgAdapter);
        }

        if (bar.getPb_voice() != null && !bar.getPb_voice().equals("")) {
            holder.mItemPostbarVoice.setVisibility(View.VISIBLE);
            GIFShow gifShow = new GIFShow(holder.mVoiceGif);
            if (timeMap.containsKey(position)){
                holder.mVoiceTime.setText(timeMap.get(position));
            }else {
                EasyVoice.getVoiceTime(InValues.send(R.string.httpHeadert) + bar.getPb_voice(), position, new EasyVoice.TimeReturn() {
                    @Override
                    public void onReturn(int time, int position) {
                        baseMVPActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshVoiceTime(position,String.valueOf(time));
                            }
                        });
                    }
                });
            }
            holder.mItemPostbarVoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (baseMVPActivity.getEasyVoice() == null || position != cachePosition) {
                        if (baseMVPActivity.getEasyVoice() != null) {
                            baseMVPActivity.getEasyVoice().stopPlayer();
                        }
                        baseMVPActivity.toVoice(bars.get(position).getPb_voice(), holder.mVoiceTime, gifShow);
                        cachePosition = position;
                        baseMVPActivity.getEasyVoice().startPlayer();
                    }else {
                        if (baseMVPActivity.getEasyVoice().isVoicePlayerKey()){
                            baseMVPActivity.getEasyVoice().startPlayer();
                        }else {
                            baseMVPActivity.getEasyVoice().stopPlayer();
                        }
                    }
                    refreshNoewVoice(position);
                }
            });
        } else {
            holder.mItemPostbarVoice.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (bars == null) {
            return 0;
        } else {
            return bars.size();
        }
    }
}
