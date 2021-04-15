package com.consultation.studenthelp.module.leavemessage;

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
import com.consultation.studenthelp.net.vo.MessageInfo;
import com.consultation.studenthelp.net.vo.OrderInfo;
import com.consultation.studenthelp.net.vo.UserInfo;
import com.consultation.studenthelp.utils.UserSpUtils;

import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVUser;
import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.activity.LCIMConversationActivity;
import cn.leancloud.chatkit.utils.LCIMConstants;
import cn.leancloud.im.v2.AVIMClient;
import cn.leancloud.im.v2.AVIMException;
import cn.leancloud.im.v2.callback.AVIMClientCallback;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHolder> {
    private Context mContext;
    private List<AVObject> list;

    public MessageListAdapter(Context mContext, List<AVObject> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MessageListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_message_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MessageListAdapter.ViewHolder holder, int position) {
        AVObject bean = list.get(position);
        boolean isTeacher = UserSpUtils.getUserType().equals(UserInfo.USER_TYPE_TEACHER);
        String name = isTeacher ? ("留言学生：" + bean.getString(MessageInfo.MESSAGE_STUDENT_NAME)) :
                ("留言老师：" + bean.getString(MessageInfo.MESSAGE_TEACHER_NAME));
        holder.name.setText(name);
        holder.time.setText(bean.getCreatedAtString());
        holder.content.setText(bean.getString(MessageInfo.MESSAGE_CONTENT));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LCChatKit.getInstance().open(AVUser.getCurrentUser().getObjectId(), new AVIMClientCallback() {
                    @Override
                    public void done(AVIMClient client, AVIMException e) {
                        if (null == e) {
                            Intent intent = new Intent(mContext, LCIMConversationActivity.class);
                            intent.putExtra(LCIMConstants.PEER_ID, bean.getString(isTeacher ? MessageInfo.MESSAGE_STUDENT_ID : MessageInfo.MESSAGE_TEACHER_ID));
                            mContext.startActivity(intent);
                        } else {
                            Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView time;
        private TextView content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            time = itemView.findViewById(R.id.time);
            content = itemView.findViewById(R.id.content);
        }
    }
}
