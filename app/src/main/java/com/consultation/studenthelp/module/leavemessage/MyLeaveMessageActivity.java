package com.consultation.studenthelp.module.leavemessage;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseActivity;
import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.databinding.ActivityOrderListBinding;
import com.consultation.studenthelp.net.vo.MessageInfo;
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
 * 我的留言列表
 */
public class MyLeaveMessageActivity extends BaseActivity {
    private ActivityOrderListBinding binding;
    private List<AVObject> list = new ArrayList<>();
    private MessageListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_list);
        binding.setLifecycleOwner(this);

        binding.tvTitle.setText("我的留言");
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        adapter = new MessageListAdapter(this, list);
        binding.recyclerOrder.setAdapter(adapter);
        getMessageList();
    }

    private void getMessageList() {
        AVQuery<AVObject> query = new AVQuery<>(MessageInfo.TABLE_NAME);
        if (UserSpUtils.getUserType().equals(UserInfo.USER_TYPE_TEACHER)) {
            query.whereEqualTo(MessageInfo.MESSAGE_TEACHER_ID, AVUser.currentUser().getObjectId());
        } else {
            query.whereEqualTo(MessageInfo.MESSAGE_STUDENT_ID, AVUser.currentUser().getObjectId());
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