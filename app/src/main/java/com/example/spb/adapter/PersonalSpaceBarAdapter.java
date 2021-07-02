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
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.entity.Bar;
import com.example.spb.entity.Topic;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.MyDateClass;
import com.example.spb.presenter.littlefun.MyResolve;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.example.spb.presenter.otherimpl.DataLikePresenter;
import com.example.spb.view.Component.BarMoreOperateDialog;
import com.example.spb.view.Component.ThumbAnima;
import com.example.spb.view.activity.PersonalSpacePage;
import com.example.spb.view.activity.PostBarDetailPage;
import com.example.spb.view.activity.TopicBarPage;
import com.example.spb.view.littlefun.BarImageInFlater;
import com.example.spb.view.littlefun.JumpIntent;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import pl.droidsonroids.gif.GifImageView;

import java.util.List;

public class PersonalSpaceBarAdapter extends RecyclerView.Adapter<PersonalSpaceBarAdapter.ViewHolder> {

    private List<Bar> bars;
    private Bar bar;
    private View view;
    private Activity activity;
    private LayoutInflater layoutInflater;
    private GridLayoutManager gridLayoutManager;
    private PostBarImgAdapter postBarImgAdapter;
    private PersonalSpacePage personalSpacePage;
    private BarMoreOperateDialog barMoreOperateDialog;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mItemUserspaceBarPostdate;
        ImageView mItemUserspaceBarMore;
        RecyclerView mItemUserspaceBarImagelist;
        TextView mItemUserspaceBarTxt;
        GifImageView mVoiceGif;
        TextView mVoiceTime;
        RelativeLayout mItemUserspaceBarVoice;
        TagFlowLayout mItemUserspaceBarTopic;
        TextView mItemUserspaceBarLocation;
        ImageView mItemPostbarDiscussImg;
        TextView mItemPostbarDiscussNum;
        RelativeLayout mItemPostbarDiscussR;
        ImageView mItemPostbarLikeImg;
        TextView mItemPostbarLikeNum;
        RelativeLayout mItemPostbarLikeR;
        RelativeLayout mItempostBarRa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemUserspaceBarPostdate = (TextView)itemView.findViewById(R.id.item_userspace_bar_postdate);
            mItemUserspaceBarMore = (ImageView)itemView.findViewById(R.id.item_userspace_bar_more);
            mItemUserspaceBarImagelist = (RecyclerView)itemView.findViewById(R.id.item_userspace_bar_imagelist);
            mItemUserspaceBarTxt = (TextView)itemView.findViewById(R.id.item_userspace_bar_txt);
            mVoiceGif = (GifImageView)itemView.findViewById(R.id.voice_gif);
            mVoiceTime = (TextView)itemView.findViewById(R.id.voice_time);
            mItemUserspaceBarVoice = (RelativeLayout)itemView.findViewById(R.id.item_userspace_bar_voice);
            mItemUserspaceBarTopic = (TagFlowLayout)itemView.findViewById(R.id.item_userspace_bar_topic);
            mItemUserspaceBarLocation = (TextView)itemView.findViewById(R.id.item_userspace_bar_location);
            mItemPostbarDiscussImg = (ImageView)itemView.findViewById(R.id.item_postbar_discuss_img);
            mItemPostbarDiscussNum = (TextView)itemView.findViewById(R.id.item_postbar_discuss_num);
            mItemPostbarDiscussR = (RelativeLayout)itemView.findViewById(R.id.item_postbar_discuss_R);
            mItemPostbarLikeImg = (ImageView)itemView.findViewById(R.id.item_postbar_like_img);
            mItemPostbarLikeNum = (TextView)itemView.findViewById(R.id.item_postbar_like_num);
            mItemPostbarLikeR = (RelativeLayout)itemView.findViewById(R.id.item_postbar_like_R);
            mItempostBarRa = (RelativeLayout)itemView.findViewById(R.id.item_postbar_RA);
        }
    }

    public PersonalSpaceBarAdapter(Activity activity, List<Bar> bars) {
        this.activity = activity;
        this.bars = bars;
        personalSpacePage = (PersonalSpacePage)activity;
        layoutInflater = activity.getLayoutInflater();
        notifyDataSetChanged();
    }

    public void addMorePersonalBar(List<Bar> moreBars){
        this.bars.addAll(moreBars);
        notifyItemRangeChanged(bars.size() - moreBars.size(), bars.size() + 1);
    }

    public void refreshLikeItem(int num,String pbId){
        Bar cachebar = bars.stream().filter(bars -> bars.getPb_one_id().equals(pbId)).findAny().orElse(null);
        if (cachebar != null){
            int a = bars.indexOf(cachebar);
            if (a != -1){
                bars.get(a).setPb_thumb_num(bars.get(a).getPb_thumb_num() + num);
                notifyItemChanged(a);
            }
        }
    }

    public void deleteBar(String pbId){
        Bar cachebar = bars.stream().filter(bars -> bars.getPb_one_id().equals(pbId)).findAny().orElse(null);
        if (cachebar != null){
            int a = bars.indexOf(cachebar);
            if (a != -1){
                bars.remove(a);
                notifyItemRemoved(a);
                notifyItemRangeChanged(a, bars.size() + 1);
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_userspace_bar_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        bar = bars.get(position);
        if (bar.getPb_article() != null && !bar.getPb_article().equals("")){
            holder.mItemUserspaceBarTxt.setVisibility(View.VISIBLE);
            holder.mItemUserspaceBarTxt.setText(bar.getPb_article());
        }else {
            holder.mItemUserspaceBarTxt.setVisibility(View.GONE);
            holder.mItemUserspaceBarTxt.setText("");
        }
        holder.mItemUserspaceBarPostdate.setText(MyDateClass.showDateClass(bar.getPb_date()));

        if (bar.getPb_location() != null && !bar.getPb_location().equals("")){
            holder.mItemUserspaceBarLocation.setVisibility(View.VISIBLE);
            holder.mItemUserspaceBarLocation.setText(bar.getPb_location());
        }else {
            holder.mItemUserspaceBarLocation.setVisibility(View.GONE);
            holder.mItemUserspaceBarLocation.setText("");
        }

        if (bar.getPb_comment_num() != 0){
            holder.mItemPostbarDiscussNum.setVisibility(View.VISIBLE);
            holder.mItemPostbarDiscussNum.setText(MyDateClass.sendMath(bar.getPb_comment_num()));
        }else {
            holder.mItemPostbarDiscussNum.setVisibility(View.INVISIBLE);
        }

        if (bar.getPb_thumb_num() != 0){
            holder.mItemPostbarLikeNum.setVisibility(View.VISIBLE);
            holder.mItemPostbarLikeNum.setText(MyDateClass.sendMath(bar.getPb_thumb_num()));
        }else {
            holder.mItemPostbarLikeNum.setVisibility(View.INVISIBLE);
        }

        if (personalSpacePage.getDataLikePresenter().determineLike(bar.getPb_one_id())){
            holder.mItemPostbarLikeImg.setBackground(MyApplication.getContext().getDrawable(R.drawable.icon_likeal));
        }else {
            holder.mItemPostbarLikeImg.setBackground(MyApplication.getContext().getDrawable(R.drawable.icon_like));
        }

        holder.mItempostBarRa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接跳转动态详细
                JumpIntent.startMsgIntent(PostBarDetailPage.class, new JumpIntent.SetMsg() {
                    @Override
                    public void setMessage(Intent intent) {
                        intent.putExtra(InValues.send(R.string.intent_Bar),bars.get(position));
                    }
                });
            }
        });
        holder.mItemUserspaceBarMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示dialog更多功能
                barMoreOperateDialog = new BarMoreOperateDialog(activity);
                barMoreOperateDialog.setData(personalSpacePage.getDataFollowPresenter().determineFollow(bars.get(position).getUser_account()),
                        personalSpacePage.getDataCollectBarPresenter().determineCollect(bars.get(position).getPb_one_id()),bars.get(position).getPb_one_id(),bars.get(position).getUser_account());
                if (!bars.get(position).getUser_account().equals(personalSpacePage.getDataUserMsgPresenter().getUser_account())){
                    barMoreOperateDialog.funChat();
                    barMoreOperateDialog.funCollect();
                    barMoreOperateDialog.funFOllow();
                    barMoreOperateDialog.funReport();
                }else {
                    barMoreOperateDialog.funDeleteBar(null);
                    barMoreOperateDialog.funCollect();
                    barMoreOperateDialog.funReport();
                }
                barMoreOperateDialog.showMyDialog();
            }
        });

        holder.mItemPostbarDiscussR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转动态详细并拉起键盘评论
            }
        });
        holder.mItemPostbarLikeR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //执行点赞动画。更改数据
                personalSpacePage.getDataLikePresenter().updateLikeData(bars.get(position).getPb_one_id()
                        , personalSpacePage.getDataUserMsgPresenter().getUser_account(),bars.get(position).getUser_account(), new DataLikePresenter.OnReturn() {
                            @Override
                            public void removeLike() {
                                holder.mItemPostbarLikeImg.setBackground(MyApplication.getContext().getDrawable(R.drawable.icon_like));
                                SpbBroadcast.sendReceiver(MyApplication.getContext(),InValues.send(R.string.Bcr_refresh_thumb),-1,bars.get(position).getPb_one_id());
                            }

                            @Override
                            public void addLike() {
                                ThumbAnima.thumbAnimation(holder.mItemPostbarLikeImg);
                                SpbBroadcast.sendReceiver(MyApplication.getContext(),InValues.send(R.string.Bcr_refresh_thumb),+1,bars.get(position).getPb_one_id());
                            }
                        });
            }
        });

        if (bar.getPb_topic() != null && !bar.getPb_topic().equals("") && !bar.getPb_topic().equals("null")){
            holder.mItemUserspaceBarTopic.setAdapter(new TagAdapter<Topic>(MyResolve.InTopic(bar.getPb_topic())) {
                @Override
                public View getView(FlowLayout parent, int position, Topic o) {
                    View view = layoutInflater.inflate(R.layout.item_tag_two, holder.mItemUserspaceBarTopic, false);
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
            holder.mItemUserspaceBarImagelist.setVisibility(View.VISIBLE);
            gridLayoutManager = BarImageInFlater.getInflater(activity,bar.getPb_image_url());
            holder.mItemUserspaceBarImagelist.setLayoutManager(gridLayoutManager);
            postBarImgAdapter = new PostBarImgAdapter(activity,MyResolve.InDoubleImage(bar.getPb_image_url()));
            holder.mItemUserspaceBarImagelist.setAdapter(postBarImgAdapter);
        }else {
            holder.mItemUserspaceBarImagelist.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return bars.size();
    }
}
