package com.consultation.studenthelp.module.userinfo;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.net.vo.LabelsInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.leancloud.AVObject;

public class EditLablesAdapter extends RecyclerView.Adapter<EditLablesAdapter.ViewHolder> {
    private Context mContext;
    private List<AVObject> list;
    private List<String> selected = new ArrayList<>();

    public EditLablesAdapter(Context mContext, List<AVObject> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public void refreshSelected(String[] selected) {
        this.selected.addAll(Arrays.asList(selected));
    }

    public String getLabels() {
        StringBuilder builder = new StringBuilder(selected.size());
        for (String s : selected) {
            builder.append(s).append(",");
        }
        return builder.substring(0, builder.length() - 2);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_edit_sort, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AVObject bean = list.get(position);
        holder.name.setText(bean.getString(LabelsInfo.LABEL_NAME));

        setView(holder, selected.contains(bean.getObjectId()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int isSelected = selected.indexOf(bean.getObjectId());
                if (isSelected != -1) {
                    selected.remove(isSelected);
                    setView(holder, false);
                } else {
                    selected.add(bean.getObjectId());
                    setView(holder, true);
                }
            }
        });
    }

    private void setView(@NonNull ViewHolder holder, boolean isSelected) {
        if (isSelected) {
            holder.name.setTextColor(Color.WHITE);
            holder.name.setBackgroundResource(R.drawable.shape_solid_f6729f_2);
        } else {
            holder.name.setTextColor(Color.parseColor("#ABABAB"));
            holder.name.setBackgroundResource(R.drawable.shape_gray_stroke_2);
        }
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }
    }
}
