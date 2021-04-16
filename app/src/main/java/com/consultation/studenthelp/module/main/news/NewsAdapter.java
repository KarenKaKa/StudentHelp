package com.consultation.studenthelp.module.main.news;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.net.vo.ArticlesInfo;
import com.consultation.studenthelp.net.vo.Questionnaire;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.leancloud.AVObject;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.HomeTeacherViewHolder> {
    private Context mContext;
    private List<AVObject> list;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public NewsAdapter(Context mContext, List<AVObject> list) {
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
        AVObject bean = list.get(position);
        holder.title.setText(bean.getString(ArticlesInfo.NEWS_TITLE));
        holder.content.setText(bean.getString(ArticlesInfo.NEWS_CONTENT));
        holder.teacher.setText(bean.getString(ArticlesInfo.NEWS_TEACHER_NAME));
        holder.date.setText(dateFormat.format(bean.getUpdatedAt()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NewsDetailActivity.class);
                intent.putExtra("id", bean.getObjectId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class HomeTeacherViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView teacher;
        private TextView date;
        private TextView content;

        public HomeTeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            teacher = itemView.findViewById(R.id.teacherName);
            date = itemView.findViewById(R.id.date);
            content = itemView.findViewById(R.id.content);
        }
    }
}
