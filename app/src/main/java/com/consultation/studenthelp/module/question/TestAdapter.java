package com.consultation.studenthelp.module.question;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.net.vo.Answer;
import com.consultation.studenthelp.net.vo.Question;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {
    private Context mContext;
    private List<Question> list;

    public TestAdapter(Context mContext, List<Question> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_test, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Question bean = list.get(position);
        holder.questionTitle.setText(bean.getQuestionTitle());

        holder.recyclerAnswer.setAdapter(new AnswerAdapter(mContext, bean.getAnswerList(), bean.isMultiSelect(), new OnItemClick() {
            @Override
            public void onItemClickListener(Answer answersBean) {
                if (bean.isMultiSelect()) {
                    if (answersBean.isSelected()) {
                        answersBean.setSelected(false);
                    } else {
                        answersBean.setSelected(true);
                    }
                    notifyItemChanged(position);
                } else {
                    if (!answersBean.isSelected()) {
                        for (Answer answer : bean.getAnswerList()) {
                            if (answer.isSelected()) {
                                answer.setSelected(false);
                            }
                        }
                        answersBean.setSelected(true);
                    }
                    notifyItemChanged(position);
                }
            }
        }));
    }

    private int getScores(List<Answer> selectedList) {
        int s = 0;
        for (Answer answersBean : selectedList) {
            s += answersBean.getScore();
        }
        return s;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView questionTitle;
        private RecyclerView recyclerAnswer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTitle = itemView.findViewById(R.id.questionTitle);
            recyclerAnswer = itemView.findViewById(R.id.recyclerAnswer);
        }
    }

    public interface OnItemClick {
        void onItemClickListener(Answer bean);
    }
}
