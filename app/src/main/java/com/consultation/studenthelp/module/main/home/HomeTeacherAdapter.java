package com.consultation.studenthelp.module.main.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.net.vo.UserInfo;

import java.util.List;

import cn.leancloud.AVUser;

public class HomeTeacherAdapter extends RecyclerView.Adapter<HomeTeacherAdapter.HomeTeacherViewHolder> {
    private Context mContext;
    private List<AVUser> list;

    public HomeTeacherAdapter(Context mContext, List<AVUser> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public HomeTeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeTeacherViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home_teacher, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeTeacherViewHolder holder, int position) {
        AVUser bean = list.get(position);
        String skills = bean.getString(UserInfo.USER_SKILLS);
        holder.name.setText(bean.getString(UserInfo.USER_NAME) + "\n" + bean.getString(UserInfo.USER_SKILLS));
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
