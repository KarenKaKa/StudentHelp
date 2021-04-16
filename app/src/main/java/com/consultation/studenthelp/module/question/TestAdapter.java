package com.consultation.studenthelp.module.question;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.net.vo.AnswersBean;
import com.consultation.studenthelp.net.vo.QuestionsBean;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {
    private Context mContext;
    private List<QuestionsBean> list;

    public TestAdapter(Context mContext, List<QuestionsBean> list) {
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
        QuestionsBean bean = list.get(position);
//        holder.questionTitle.setText(bean.getQuestionTitle());

        holder.recyclerAnswer.setAdapter(new AnswerAdapter(mContext, bean.getAnswers(), bean.isMuti(), new OnItemClick() {
            @Override
            public void onItemClickListener(AnswersBean answersBean) {
                if (bean.isMuti()) {
                    if (answersBean.isSelected()) {
                        deleteSelected(bean.getSelectedList(), answersBean);
                        answersBean.setSelected(false);
                    } else {
                        answersBean.setSelected(true);
                        bean.getSelectedList().add(answersBean);
                    }
                    notifyItemChanged(position);
                } else {
                    if (!answersBean.isSelected()) {
                        if (bean.getSelectedList().size() > 0) {
                            bean.getSelectedList().get(0).setSelected(false);
                            bean.getSelectedList().clear();
                        }
                        answersBean.setSelected(true);
                        bean.getSelectedList().add(answersBean);
                    }
                    notifyItemChanged(position);
                }
            }
        }));
    }

    private void deleteSelected(List<AnswersBean> selectedList, AnswersBean bean) {
        for (AnswersBean answersBean : selectedList) {
            if (bean.getAnswerId().equals(answersBean.getAnswerId())) {
                answersBean.setSelected(false);
                bean.setSelected(false);
                selectedList.remove(answersBean);
                return;
            }
        }
    }

    private int getScores(List<AnswersBean> selectedList) {
        int s = 0;
        for (AnswersBean answersBean : selectedList) {
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
        void onItemClickListener(AnswersBean bean);
    }
}
