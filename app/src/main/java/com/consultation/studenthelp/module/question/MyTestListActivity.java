package com.consultation.studenthelp.module.question;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseActivity;
import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.databinding.ActivityOrderListBinding;
import com.consultation.studenthelp.net.vo.MyQuestionsInfo;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import cn.leancloud.AVUser;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 我的测试记录
 */
public class MyTestListActivity extends BaseActivity {
    private ActivityOrderListBinding binding;
    private List<AVObject> list = new ArrayList<>();
    private RecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_list);
        binding.setLifecycleOwner(this);

        binding.tvTitle.setText("测试记录");
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        adapter = new RecordAdapter(this, list);
        binding.recyclerOrder.setAdapter(adapter);
        getList();
    }

    private void getList() {
        AVQuery<AVObject> query = new AVQuery<>(MyQuestionsInfo.TABLE_NAME);
        query.whereEqualTo(MyQuestionsInfo.USER_ID, AVUser.currentUser().getObjectId());
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