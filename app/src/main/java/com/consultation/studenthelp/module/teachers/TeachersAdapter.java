package com.consultation.studenthelp.module.teachers;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.net.vo.LabelsInfo;
import com.consultation.studenthelp.net.vo.UserInfo;

import java.util.List;

import cn.leancloud.AVObject;

//TODO 待做分类筛选项
public class TeachersAdapter extends RecyclerView.Adapter<TeachersAdapter.TeacherViewHolder> {

    private Context mContext;
    private List<AVObject> list;
    private List<AVObject> labels;

    public TeachersAdapter(Context mContext, List<AVObject> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public void refreshLabels(List<AVObject> labels) {
        this.labels = labels;
    }

    private String getLabelsName(String id) {
        for (AVObject label : labels) {
            if (id.equals(label.getObjectId())) {
                return label.getString(LabelsInfo.LABEL_NAME);
            }
        }
        return null;
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

        String labels = bean.getString(UserInfo.USER_LABELS);
        if (TextUtils.isEmpty(labels)) {
            holder.label1.setVisibility(View.GONE);
            holder.label2.setVisibility(View.GONE);
            holder.label3.setVisibility(View.GONE);
        } else {
            String[] label = labels.split(",");
            if (label.length >= 1) {
                String name = getLabelsName(label[0]);
                if (!TextUtils.isEmpty(name)) {
                    holder.label1.setVisibility(View.VISIBLE);
                    holder.label1.setText(name);
                }
            }
            if (label.length >= 2) {
                String name = getLabelsName(label[1]);
                if (!TextUtils.isEmpty(name)) {
                    holder.label2.setVisibility(View.VISIBLE);
                    holder.label2.setText(name);
                }
            }
            if (label.length >= 3) {
                String name = getLabelsName(label[2]);
                if (!TextUtils.isEmpty(name)) {
                    holder.label3.setVisibility(View.VISIBLE);
                    holder.label3.setText(name);
                }
            }
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
