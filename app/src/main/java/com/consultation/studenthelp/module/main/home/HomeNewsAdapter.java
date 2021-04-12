package com.consultation.studenthelp.module.main.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.net.vo.NewsBean;

import java.util.List;

public class HomeNewsAdapter extends RecyclerView.Adapter<HomeNewsAdapter.HomeTeacherViewHolder> {
    private Context mContext;
    private List<NewsBean> list;

    public HomeNewsAdapter(Context mContext, List<NewsBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public HomeTeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeTeacherViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home_news, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeTeacherViewHolder holder, int position) {
        NewsBean bean = list.get(position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class HomeTeacherViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public HomeTeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }
    }
}
