package com.consultation.studenthelp.module.question;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.net.vo.Answer;

import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {
    private Context mContext;
    private List<Answer> list;
    private boolean isMuti;
    private TestAdapter.OnItemClick onItemClick;

    public AnswerAdapter(Context mContext, List<Answer> list, boolean muti, TestAdapter.OnItemClick onItemClick) {
        this.mContext = mContext;
        this.list = list;
        this.isMuti = muti;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_test_answer, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Answer bean = list.get(position);
        holder.answerTitle.setText(bean.getAnswerTitle());
        if (isMuti) {
            holder.imageBtn.setImageResource(bean.isSelected() ? R.drawable.icon_selected1 : R.drawable.icon_select1);
        } else {
            holder.imageBtn.setImageResource(bean.isSelected() ? R.drawable.icon_selected : R.drawable.icon_select);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClick != null) {
                    onItemClick.onItemClickListener(bean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageBtn;
        private TextView answerTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageBtn = itemView.findViewById(R.id.imageBtn);
            answerTitle = itemView.findViewById(R.id.answerTitle);
        }
    }
}
