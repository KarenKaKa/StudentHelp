package com.consultation.studenthelp.module.question;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.net.vo.MyQuestionsInfo;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.leancloud.AVObject;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.ViewHolder> {
    private Context mContext;
    private List<AVObject> list;

    public RecordAdapter(Context mContext, List<AVObject> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public RecordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_record_list, parent, false));
    }
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    @Override
    public void onBindViewHolder(@NonNull RecordAdapter.ViewHolder holder, int position) {
        AVObject bean = list.get(position);
        holder.title.setText(bean.getString(MyQuestionsInfo.QUESTION_TITLE));
        holder.subTitle.setText("评分标准：" + bean.getString(MyQuestionsInfo.QUESTION_SCORINGCRITERIA));
        holder.score.setText("我的得分：" + bean.getNumber(MyQuestionsInfo.QUESTION_SCORES));
        holder.time.setText(dateFormat.format(bean.getCreatedAt()));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView subTitle;
        private TextView score;
        private TextView time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            subTitle = itemView.findViewById(R.id.subTitle);
            score = itemView.findViewById(R.id.score);
            time = itemView.findViewById(R.id.time);
        }
    }
}
