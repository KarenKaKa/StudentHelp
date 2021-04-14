package com.consultation.studenthelp.module.main.home;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.net.vo.UserInfo;

import java.util.List;

import cn.leancloud.AVUser;
import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.activity.LCIMConversationActivity;
import cn.leancloud.chatkit.utils.LCIMConstants;
import cn.leancloud.im.v2.AVIMClient;
import cn.leancloud.im.v2.AVIMException;
import cn.leancloud.im.v2.callback.AVIMClientCallback;

public class HomeTeacherAdapter extends RecyclerView.Adapter<HomeTeacherAdapter.HomeTeacherViewHolder> {
    private Context mContext;
    private List<AVUser> list;

    public HomeTeacherAdapter(Context mContext, List<AVUser> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public HomeTeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeTeacherViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_home_teacher, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeTeacherViewHolder holder, int position) {
        AVUser bean = list.get(position);
        holder.name.setText(bean.getString(UserInfo.USER_NAME));
        holder.skills.setText(bean.getString(UserInfo.USER_SKILLS));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!AVUser.getCurrentUser().getObjectId().isEmpty()) {
                    LCChatKit.getInstance().open(AVUser.getCurrentUser().getObjectId(), new AVIMClientCallback() {
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
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class HomeTeacherViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView skills;

        public HomeTeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            skills = itemView.findViewById(R.id.skills);
        }
    }
}
