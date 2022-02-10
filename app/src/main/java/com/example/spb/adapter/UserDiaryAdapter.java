package com.example.spb.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.spb.R;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.entity.Diary;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MyDateClass;
import com.example.spb.view.Component.ComponentDialog;
import com.hitomi.tilibrary.style.index.NumberIndexIndicator;
import com.hitomi.tilibrary.style.progress.ProgressBarIndicator;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
import com.makeramen.roundedimageview.RoundedImageView;
import com.vansz.glideimageloader.GlideImageLoader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class UserDiaryAdapter extends RecyclerView.Adapter<UserDiaryAdapter.ViewHolder> {

    private final List<Diary> diaryList;
    private final BaseMVPActivity baseMVPActivity;
    private final TransferConfig config;
    private final List<String> stringspath;
    private Diary diary;
    private Transferee transferee;
    private ComponentDialog componentDialog;
    private Button mButtonRight;
    private Button mButtonClose;
    private TextView mtxt;
    private TextView mtitle;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mDiaryDateYear;
        TextView mDiaryDateMonth;
        TextView mDiaryDateWeek;
        TextView mDiaryDateTime;
        ImageView mDiaryWeather;
        TextView mDiaryMessage;
        RoundedImageView mDiaryImg;
        RelativeLayout mDiaryItemR;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mDiaryDateYear = itemView.findViewById(R.id.diary_date_year);
            mDiaryDateMonth = itemView.findViewById(R.id.diary_date_month);
            mDiaryDateWeek = itemView.findViewById(R.id.diary_date_week);
            mDiaryDateTime = itemView.findViewById(R.id.diary_date_time);
            mDiaryWeather = itemView.findViewById(R.id.diary_weather);
            mDiaryMessage = itemView.findViewById(R.id.diary_message);
            mDiaryImg = itemView.findViewById(R.id.diary_img);
            mDiaryItemR = itemView.findViewById(R.id.diary_item_r);
        }
    }

    public UserDiaryAdapter(List<Diary> diaryList, Activity activity) {
        this.diaryList = diaryList;
        this.baseMVPActivity = (BaseMVPActivity) activity;
        stringspath = new ArrayList<>();
        for (Diary d : diaryList) {
            if (!DataVerificationTool.isEmpty(d.getDia_image())) {
                stringspath.add(InValues.send(R.string.httpHeadert) + d.getDia_image());
            }
        }
        config = TransferConfig.build()
                .setSourceUrlList(stringspath)
                .setProgressIndicator(new ProgressBarIndicator())
                .setIndexIndicator(new NumberIndexIndicator())
                .setImageLoader(GlideImageLoader.with(activity))
                .create();
    }

    public void removeData(int p) {
        diaryList.remove(p);
        notifyItemRemoved(p);
        notifyItemRangeChanged(p, diaryList.size() + 1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diary_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        diary = diaryList.get(position);
        if (transferee == null) {
            transferee = Transferee.getDefault(baseMVPActivity);
        }
        Calendar calendar = new GregorianCalendar();
        String month = "";
        String monthday = "";
        String dateTime = diary.getDia_date().substring(11);
        String dateWeek = MyDateClass.showWeekTable(diary.getDia_date(), 0);
        holder.mDiaryDateTime.setText(MyDateClass.obtainPeriod(dateTime));
        holder.mDiaryDateWeek.setText(dateWeek);
        holder.mDiaryMessage.setText(diary.getDia_message());

        calendar.setTime(MyDateClass.stringToDate(diary.getDia_date()));

        holder.mDiaryDateYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        if ((calendar.get(Calendar.MONTH) + 1) < 10) {
            month = "0" + (calendar.get(Calendar.MONTH) + 1);
        }
        if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
            monthday = "0" + calendar.get(Calendar.DAY_OF_MONTH);
        }
        holder.mDiaryDateMonth.setText(month + monthday);

        switch (diary.getDia_weather()) {
            case 1:
                holder.mDiaryWeather.setBackground(baseMVPActivity.getDrawable(R.drawable.icon_weather_sun));
                break;
            case 2:
                holder.mDiaryWeather.setBackground(baseMVPActivity.getDrawable(R.drawable.icon_weather_cloud1));
                break;
            case 3:
                holder.mDiaryWeather.setBackground(baseMVPActivity.getDrawable(R.drawable.icon_weather_cloud2));
                break;
            case 4:
                holder.mDiaryWeather.setBackground(baseMVPActivity.getDrawable(R.drawable.icon_weather_wind));
                break;
            case 5:
                holder.mDiaryWeather.setBackground(baseMVPActivity.getDrawable(R.drawable.icon_weather_rain1));
                break;
            case 6:
                holder.mDiaryWeather.setBackground(baseMVPActivity.getDrawable(R.drawable.icon_weather_rain2));
                break;
            case 7:
                holder.mDiaryWeather.setBackground(baseMVPActivity.getDrawable(R.drawable.icon_weather_rain3));
                break;
            case 8:
                holder.mDiaryWeather.setBackground(baseMVPActivity.getDrawable(R.drawable.icon_weather_snow1));
                break;
            case 9:
                holder.mDiaryWeather.setBackground(baseMVPActivity.getDrawable(R.drawable.icon_weather_snow2));
                break;
            default:
                break;
        }

        if (!DataVerificationTool.isEmpty(diary.getDia_image())) {
            holder.mDiaryImg.setVisibility(View.VISIBLE);
            Glide.with(baseMVPActivity)
                    .load(InValues.send(R.string.httpHeadert) + diary.getDia_image())
                    .into(holder.mDiaryImg);
        } else {
            holder.mDiaryImg.setVisibility(View.GONE);
        }

        holder.mDiaryImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.setNowThumbnailIndex(stringspath.indexOf(InValues.send(R.string.httpHeadert) + diary.getDia_image()));
                transferee.apply(config).show();
            }
        });

        holder.mDiaryItemR.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setComponentDialog(position);
                return true;
            }
        });
    }

    private void setComponentDialog(int position) {
        componentDialog = new ComponentDialog(baseMVPActivity, R.layout.dialog_longclick_view, new ComponentDialog.InitDialog() {
            @Override
            public void initView(View view) {
                mButtonClose = view.findViewById(R.id.button_close);
                mButtonRight = view.findViewById(R.id.button_right);
                mtitle = view.findViewById(R.id.topic_name);
                mtxt = view.findViewById(R.id.txt);
            }

            @Override
            public void initData() {
                mtitle.setVisibility(View.GONE);
                mtxt.setText("删除日记");
            }

            @Override
            public void initListener() {
                mButtonClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        componentDialog.closeMyDialog();
                    }
                });
                mButtonRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        baseMVPActivity.getDataDiaryPresenter().removeDiary(diaryList.get(position).getId());
                        componentDialog.closeMyDialog();
                        removeData(position);
                    }
                });
            }
        });
        componentDialog.showMyDialog();
    }

    @Override
    public int getItemCount() {
        return diaryList == null ? 0 : diaryList.size();
    }
}
