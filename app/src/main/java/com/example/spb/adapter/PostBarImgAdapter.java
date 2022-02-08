package com.example.spb.adapter;

import android.app.Activity;
import android.view.*;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.entity.ImageDouble;
import com.example.spb.presenter.utils.InValues;
import com.hitomi.tilibrary.style.index.NumberIndexIndicator;
import com.hitomi.tilibrary.style.progress.ProgressBarIndicator;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
import com.makeramen.roundedimageview.RoundedImageView;
import com.vansz.glideimageloader.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

public class PostBarImgAdapter extends RecyclerView.Adapter<PostBarImgAdapter.ViewHolder> {

    public View view;
    private Activity activity;
    private ViewHolder viewHolder;
    private List<ImageDouble> newBarImageList;
    private ImageDouble imageDouble;
    private Transferee transferee;
    private TransferConfig config;
    private List<String> minImageList;
    private List<String> maxImageList;
    private boolean isDetail = false;

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView mImageItemView;
        ImageView mImageClear;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageItemView = (RoundedImageView)itemView.findViewById(R.id.image_item_view);
            mImageClear = (ImageView)itemView.findViewById(R.id.image_clear);
        }
    }

    public PostBarImgAdapter(Activity activity, List<ImageDouble> newBarImageList) {
        minImageList = new ArrayList<>();
        maxImageList = new ArrayList<>();
        this.activity = activity;
        this.newBarImageList = newBarImageList;
        for (ImageDouble img : newBarImageList){
            minImageList.add(InValues.send(R.string.httpHeadert) + img.getMinPath());
            maxImageList.add(InValues.send(R.string.httpHeadert) + img.getMaxPath());
        }
        config = TransferConfig.build()
                .setSourceUrlList(maxImageList)
                .setProgressIndicator(new ProgressBarIndicator())
                .setIndexIndicator(new NumberIndexIndicator())
                .setImageLoader(GlideImageLoader.with(activity))
                .create();
    }

    public void isDetail(boolean a){
        this.isDetail = a;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newbar_img_list, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mImageClear.setVisibility(View.GONE);
        RelativeLayout.LayoutParams paramses = (RelativeLayout.LayoutParams) holder.mImageItemView.getLayoutParams();
        if (transferee == null){
            transferee = Transferee.getDefault(activity);
        }
        if (isDetail){
            paramses.width = WindowManager.LayoutParams.MATCH_PARENT;
            paramses.height = WindowManager.LayoutParams.MATCH_PARENT;
            holder.mImageItemView.setLayoutParams(paramses);
            Glide.with(MyApplication.getContext())
                    .load(minImageList.get(position))
                    .placeholder(R.drawable.enterbg)
                    .fallback(R.drawable.enterbg)
                    .error(R.drawable.enterbg)
                    .into(holder.mImageItemView);
        }else {
            switch (newBarImageList.size()){
                case 1:
                    paramses.width = 528;
                    paramses.height = 528;
                    holder.mImageItemView.setLayoutParams(paramses);
                    Glide.with(MyApplication.getContext())
                            .load(minImageList.get(position))
                            .placeholder(R.drawable.enterbg)
                            .fallback(R.drawable.enterbg)
                            .error(R.drawable.enterbg)
                            .override(528, 528)
                            .centerCrop()
                            .into(holder.mImageItemView);
                    break;
                case 3:
                    paramses.width = 300;
                    paramses.height = 300;
                    holder.mImageItemView.setLayoutParams(paramses);
                    Glide.with(MyApplication.getContext())
                            .load(minImageList.get(position))
                            .placeholder(R.drawable.enterbg)
                            .fallback(R.drawable.enterbg)
                            .error(R.drawable.enterbg)
                            .override(300, 300)
                            .centerCrop()
                            .into(holder.mImageItemView);
                    break;
                default:
                    paramses.width = 310;
                    paramses.height = 310;
                    holder.mImageItemView.setLayoutParams(paramses);
                    Glide.with(MyApplication.getContext())
                            .load(minImageList.get(position))
                            .placeholder(R.drawable.enterbg)
                            .fallback(R.drawable.enterbg)
                            .error(R.drawable.enterbg)
                            .override(310, 310)
                            .centerCrop()
                            .into(holder.mImageItemView);
                    break;
            }
        }
        holder.mImageItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.setNowThumbnailIndex(position);
                transferee.apply(config).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return maxImageList.size();
    }
}
