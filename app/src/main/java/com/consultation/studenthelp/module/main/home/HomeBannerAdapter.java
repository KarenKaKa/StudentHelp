package com.consultation.studenthelp.module.main.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.consultation.studenthelp.R;
import com.youth.banner.adapter.BannerAdapter;

import java.util.List;


public class HomeBannerAdapter extends BannerAdapter<Integer, HomeBannerAdapter.BannerViewHolder> {
    private Context mContext;

    public HomeBannerAdapter(Context context, List<Integer> datas) {
        super(datas);
        this.mContext = context;
    }

    @Override
    public BannerViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new BannerViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_banner, parent, false));
    }

    @Override
    public void onBindView(BannerViewHolder holder, Integer data, int position, int size) {
        holder.image.setImageResource(mDatas.get(position));
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position == 0) {
                    Toast.makeText(mContext, "抑郁症自测", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "咨询预约", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static class BannerViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }
}
