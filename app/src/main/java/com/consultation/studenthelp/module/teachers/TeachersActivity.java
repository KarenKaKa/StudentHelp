package com.consultation.studenthelp.module.teachers;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseActivity;
import com.consultation.studenthelp.databinding.ActivityTeachersBinding;
import com.consultation.studenthelp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.im.v2.AVIMClient;
import cn.leancloud.im.v2.AVIMException;
import cn.leancloud.im.v2.callback.AVIMClientCallback;

/**
 * 咨询师列表
 */
public class TeachersActivity extends BaseActivity<TeachersPresenter> implements TeachersView {
    private ActivityTeachersBinding binding;
    private TeachersAdapter adapter;
    private List<AVObject> teachers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teachers);
        binding.setLifecycleOwner(this);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        adapter = new TeachersAdapter(this, teachers);
        binding.recyclerTeacher.setAdapter(adapter);

        mPresenter.getTeachers();
    }

    @Override
    protected TeachersPresenter createPresenter() {
        return new TeachersPresenter(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setData(List<AVObject> students) {
        teachers.addAll(students);
        adapter.notifyDataSetChanged();
    }
}
