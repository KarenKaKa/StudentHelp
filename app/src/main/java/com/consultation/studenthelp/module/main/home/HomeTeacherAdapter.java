package com.consultation.studenthelp.module.main.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.net.vo.UserBean;

import java.util.List;

public class HomeTeacherAdapter extends RecyclerView.Adapter<HomeTeacherAdapter.HomeTeacherViewHolder> {
    private Context mContext;
    private List<UserBean> list;

    public HomeTeacherAdapter(Context mContext, List<UserBean> list) {
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
        UserBean bean = list.get(position);
        holder.name.setText(bean.getName() + "\n" + bean.getSkills());
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
