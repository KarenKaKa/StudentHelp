package com.consultation.studenthelp.module.question;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.net.vo.Questionnaire;

import java.util.List;

public class QuestionnaireAdapter extends RecyclerView.Adapter<QuestionnaireAdapter.ViewHolder> {
    private Context mContext;
    private List<Questionnaire> list;

    public QuestionnaireAdapter(Context mContext, List<Questionnaire> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public QuestionnaireAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_questionnaire_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionnaireAdapter.ViewHolder holder, int position) {
        Questionnaire bean = list.get(position);
        if (position == 0) {
            holder.image.setImageResource(R.drawable.image2);
        } else {
            holder.image.setImageResource(R.drawable.image1);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, TestActivity.class);
                intent.putExtra("questionnaire", bean);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }
}
