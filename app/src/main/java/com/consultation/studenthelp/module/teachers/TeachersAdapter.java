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
import com.consultation.studenthelp.net.vo.UserBean;

import java.util.List;

public class TeachersAdapter extends RecyclerView.Adapter<TeachersAdapter.TeacherViewHolder> {

    private Context mContext;
    private List<UserBean> list;

    public TeachersAdapter(Context mContext, List<UserBean> list) {
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
        UserBean bean = list.get(position);
        holder.name.setText(bean.getName());
        holder.skills.setText(bean.getSkills());
        holder.gender.setImageResource(bean.getGender().equals("男") ? R.drawable.icon_man : R.drawable.icon_women);
        holder.name.setText(bean.getName());

        holder.label1.setVisibility(View.GONE);
        holder.label2.setVisibility(View.GONE);
        holder.label3.setVisibility(View.GONE);
        String labels = bean.getLabels();
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

        public TeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            skills = itemView.findViewById(R.id.skills);
            label1 = itemView.findViewById(R.id.label1);
            label2 = itemView.findViewById(R.id.label2);
            label3 = itemView.findViewById(R.id.label3);
            gender = itemView.findViewById(R.id.gender);
        }
    }
}
