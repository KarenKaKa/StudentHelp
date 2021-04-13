package com.consultation.studenthelp.module.teachers;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.net.vo.UserInfo;
import com.consultation.studenthelp.utils.Constants;

import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.activity.LCIMConversationActivity;
import cn.leancloud.chatkit.utils.LCIMConstants;
import cn.leancloud.im.v2.AVIMClient;
import cn.leancloud.im.v2.AVIMException;
import cn.leancloud.im.v2.callback.AVIMClientCallback;

//TODO 待做分类筛选项
public class TeachersAdapter extends RecyclerView.Adapter<TeachersAdapter.TeacherViewHolder> {

    private Context mContext;
    private List<AVObject> list;

    public TeachersAdapter(Context mContext, List<AVObject> list) {
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

        holder.tvChat.setText(bean.getBoolean(UserInfo.USER_AVAILABLE) ? "咨询" : "留言");
        holder.tvChat.setBackgroundResource(bean.getBoolean(UserInfo.USER_AVAILABLE) ? R.drawable.shape_solid_deb887_15 : R.drawable.shape_solid_a4d3ee_15);
        holder.tvChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.getBoolean(UserInfo.USER_AVAILABLE)) {
                    LCChatKit.getInstance().open(LCChatKit.getInstance().getCurrentUserId(), new AVIMClientCallback() {
                        @Override
                        public void done(AVIMClient client, AVIMException e) {
                            if (null == e) {
                                Intent intent = new Intent(mContext, LCIMConversationActivity.class);
                                intent.putExtra(LCIMConstants.PEER_ID, bean.getObjectId());
                                mContext.startActivity(intent);
                            } else {
                                Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(mContext,"留言",Toast.LENGTH_SHORT).show();
                }
            }
        });
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
