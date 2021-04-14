package com.consultation.studenthelp.module.main.order;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseActivity;
import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.databinding.ActivityOrderListBinding;
import com.consultation.studenthelp.net.vo.OrderInfo;
import com.consultation.studenthelp.net.vo.UserInfo;
import com.consultation.studenthelp.utils.UserSpUtils;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import cn.leancloud.AVUser;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 我的预约
 */
public class OrderListActivity extends BaseActivity {
    private ActivityOrderListBinding binding;
    private List<AVObject> list = new ArrayList<>();
    private OrderListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_list);
        binding.setLifecycleOwner(this);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        adapter = new OrderListAdapter(this, list);
        binding.recyclerOrder.setAdapter(adapter);
        getOrderList();
    }

    private void getOrderList() {
        AVQuery<AVObject> query = new AVQuery<>(OrderInfo.TABLE_NAME);
        if (UserSpUtils.getUserType().equals(UserInfo.USER_TYPE_TEACHER)) {
            query.whereEqualTo(OrderInfo.ORDER_TEACHER_ID, AVUser.currentUser().getObjectId());
        } else {
            query.whereEqualTo(OrderInfo.ORDER_STUDENT_ID, AVUser.currentUser().getObjectId());
        }
        query.findInBackground().subscribe(new Observer<List<AVObject>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull List<AVObject> labels) {
                list.addAll(labels);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(@NonNull Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}