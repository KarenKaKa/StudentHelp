package com.consultation.studenthelp.module.main.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.net.vo.OrderInfo;
import com.consultation.studenthelp.net.vo.UserInfo;
import com.consultation.studenthelp.utils.UserSpUtils;

import java.util.List;

import cn.leancloud.AVObject;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {
    private Context mContext;
    private List<AVObject> list;

    public OrderListAdapter(Context mContext, List<AVObject> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public OrderListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_order_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListAdapter.ViewHolder holder, int position) {
        AVObject bean = list.get(position);
        boolean isTeacher = UserSpUtils.getUserType().equals(UserInfo.USER_TYPE_TEACHER);
        holder.name.setText(bean.getString(isTeacher ? OrderInfo.ORDER_TEACHER_NAME : OrderInfo.ORDER_STUDENT_NAME));
        holder.start.setText("开始时间：" + bean.getString(OrderInfo.ORDER_START_TIME));
        holder.end.setText("结束时间：" + bean.getString(OrderInfo.ORDER_END_TIME));
        holder.address.setText("预约地址：" + bean.getString(OrderInfo.ORDER_ADDRESS));
        holder.tvChat.setVisibility(isTeacher ? View.VISIBLE : View.GONE);
        holder.tvChat.setBackgroundResource(bean.getBoolean(OrderInfo.ORDER_AGREE) ? R.drawable.shape_solid_deb887_15 : R.drawable.shape_solid_a4d3ee_15);
        holder.tvChat.setText(bean.getBoolean(OrderInfo.ORDER_AGREE) ? "已同意" : "同意");
        if (!bean.getBoolean(OrderInfo.ORDER_AGREE)) {
            holder.tvChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AVObject account = AVObject.createWithoutData(OrderInfo.TABLE_NAME, bean.getObjectId());
                    account.put(OrderInfo.ORDER_AGREE, true);
                    account.saveInBackground().subscribe(new Observer<AVObject>() {
                        public void onSubscribe(Disposable disposable) {
                        }

                        public void onNext(AVObject account) {
                            Toast.makeText(mContext, "操作成功", Toast.LENGTH_SHORT).show();
                            holder.tvChat.setBackgroundResource(R.drawable.shape_solid_deb887_15);
                            holder.tvChat.setText("已同意");
                        }

                        public void onError(Throwable throwable) {
                            Toast.makeText(mContext, "操作失败:" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                        public void onComplete() {
                        }
                    });
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivHead;
        private TextView name;
        private TextView start;
        private TextView end;
        private TextView address;
        private TextView tvChat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivHead = itemView.findViewById(R.id.ivHead);
            name = itemView.findViewById(R.id.name);
            start = itemView.findViewById(R.id.start);
            end = itemView.findViewById(R.id.end);
            address = itemView.findViewById(R.id.address);
            tvChat = itemView.findViewById(R.id.tvChat);
        }
    }
}
