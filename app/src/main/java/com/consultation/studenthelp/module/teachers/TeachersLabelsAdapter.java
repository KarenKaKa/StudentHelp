package com.consultation.studenthelp.module.teachers;

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

import java.util.List;

import cn.leancloud.AVObject;

public class TeachersLabelsAdapter extends RecyclerView.Adapter<TeachersLabelsAdapter.ViewHolder> {
    private Context mContext;
    private List<AVObject> list;
    private String selected = "all";
    private TeachersActivity.Onclick onclick;

    public TeachersLabelsAdapter(Context mContext, List<AVObject> list, String selected, TeachersActivity.Onclick onclick) {
        this.mContext = mContext;
        this.list = list;
        this.selected = selected;
        this.onclick = onclick;
    }

    @NonNull
    @Override
    public TeachersLabelsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeachersLabelsAdapter.ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_labels_sort, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TeachersLabelsAdapter.ViewHolder holder, int position) {
        AVObject bean = list.get(position);
        holder.name.setText(bean.getString(LabelsInfo.LABEL_NAME));
        if (selected.equals(bean.getObjectId())) {
            holder.name.setBackgroundColor(Color.parseColor("#DEB887"));

        } else {
            holder.name.setBackgroundColor(Color.parseColor("#D9D9D9"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isSelected = selected.equals(bean.getObjectId());
                if (!isSelected) {
                    selected = bean.getObjectId();
                    notifyDataSetChanged();
                    if (onclick != null) onclick.onClick(selected);
                }
            }
        });
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
