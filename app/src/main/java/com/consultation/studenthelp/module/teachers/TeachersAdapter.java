package com.consultation.studenthelp.module.teachers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.net.vo.UserInfo;

import java.util.List;

import cn.leancloud.AVObject;

public class TeachersAdapter extends RecyclerView.Adapter<TeachersAdapter.TeacherViewHolder> {

    private Context mContext;
    private List<AVObject> list;

    public TeachersAdapter(Context mContext, List<AVObject> list) {
        this.mContext = mContext;
        this.list = list;
    }


    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeacherViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_teachers, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        AVObject bean = list.get(position);
        holder.name.setText(bean.getString(UserInfo.USER_NAME));
        holder.skills.setText(bean.getString(UserInfo.USER_SKILLS));
        holder.gender.setImageResource(bean.getString(UserInfo.USER_GENDER).equals("男") ? R.drawable.icon_man : R.drawable.icon_women);
        holder.name.setText(bean.getString(UserInfo.USER_NAME));

        holder.label1.setVisibility(View.GONE);
        holder.label2.setVisibility(View.GONE);
        holder.label3.setVisibility(View.GONE);
        String labels = bean.getString(UserInfo.USER_LABELS);
        String[] label = labels.split(",");
        if (label.length >= 1) {
            holder.label1.setVisibility(View.VISIBLE);
            holder.label1.setText(label[0]);
        }
        if (label.length >= 2) {
            holder.label2.setVisibility(View.VISIBLE);
            holder.label2.setText(label[1]);
        }
        if (label.length >= 3) {
            holder.label3.setVisibility(View.VISIBLE);
            holder.label3.setText(label[2]);
        }

        holder.tvChat.setText(bean.getBoolean(UserInfo.USER_AVAILABLE) ? "咨询" : "留言");
        holder.tvChat.setBackgroundResource(bean.getBoolean(UserInfo.USER_AVAILABLE) ? R.drawable.shape_solid_deb887_15 : R.drawable.shape_solid_a4d3ee_15);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class TeacherViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView skills;
        private TextView label1;
        private TextView label2;
        private TextView label3;
        private ImageView gender;
        private TextView tvChat;

        public TeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            skills = itemView.findViewById(R.id.skills);
            label1 = itemView.findViewById(R.id.label1);
            label2 = itemView.findViewById(R.id.label2);
            label3 = itemView.findViewById(R.id.label3);
            gender = itemView.findViewById(R.id.gender);
            tvChat = itemView.findViewById(R.id.tvChat);
        }
    }
}
