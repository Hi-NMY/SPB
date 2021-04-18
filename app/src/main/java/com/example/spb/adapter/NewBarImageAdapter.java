package com.example.spb.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.entity.ImageDouble;
import com.example.spb.presenter.littlefun.MyDateClass;
import com.example.spb.view.activity.SendNewBarPage;
import com.example.spb.view.littlefun.GlideRoundTransform;
import com.hitomi.tilibrary.style.index.NumberIndexIndicator;
import com.hitomi.tilibrary.style.progress.ProgressBarIndicator;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
import com.vansz.glideimageloader.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

public class NewBarImageAdapter extends RecyclerView.Adapter<NewBarImageAdapter.ViewHolder> {

    public View view;
    private Activity activity;
    private ViewHolder viewHolder;
    private List<String> newBarImageList;
    private String imageDouble;
    private Transferee transferee;
    private RemoveImg removeImg;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageItemView;
        ImageView mImageClear;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageItemView = (ImageView)itemView.findViewById(R.id.image_item_view);
            mImageClear = (ImageView)itemView.findViewById(R.id.image_clear);
        }
    }

    public NewBarImageAdapter(Activity a,List<ImageDouble> imgList,RemoveImg r){
        this.activity = a;
        this.removeImg = r;
        newBarImageList = new ArrayList<>();
        for (ImageDouble img : imgList){
            newBarImageList.add(img.getMaxPath());
        }
    }

    public void removeImage(int option){
        notifyItemRemoved(option);
        newBarImageList.remove(option);
        notifyDataSetChanged();
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
        if (transferee == null){
            transferee = Transferee.getDefault(activity);
        }
        imageDouble = newBarImageList.get(position);
        String time = MyDateClass.showNowDate();
        Glide.with(MyApplication.getContext())
                .load(imageDouble)
                .override(72, 72)
                .centerCrop()
                .transform(new GlideRoundTransform(5))
                .signature(new MediaStoreSignature(time, 1, 1))
                .into(holder.mImageItemView);

        holder.mImageItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransferConfig config = TransferConfig.build()
                        .setSourceUrlList(newBarImageList)
                        .setProgressIndicator(new ProgressBarIndicator())
                        .setIndexIndicator(new NumberIndexIndicator())
                        .setImageLoader(GlideImageLoader.with(activity))
                        .create();
                config.setNowThumbnailIndex(position);
                transferee.apply(config).show();
            }
        });
        holder.mImageClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeImg.removeImg(position);
            }
        });
    }

    public void destroyTransFeree(){
        transferee.destroy();
    }

    @Override
    public int getItemCount() {
        return newBarImageList.size();
    }

    public interface RemoveImg{
        void removeImg(int option);
    }
}
