package com.consultation.studenthelp.module.teachers;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseActivity;
import com.consultation.studenthelp.databinding.ActivityTeachersBinding;
import com.consultation.studenthelp.net.vo.LabelsInfo;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVUser;

/**
 * 咨询师列表
 */
public class TeachersActivity extends BaseActivity<TeachersPresenter> implements TeachersView {
    private ActivityTeachersBinding binding;
    private TeachersAdapter adapter;
    private List<AVUser> teachers = new ArrayList<>();
    private TeachersLabelsAdapter labelsAdapter;
    private List<AVObject> labels = new ArrayList<>();
    private String selectedLabelId = "all";
    private boolean fromOrder = false;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teachers);
        binding.setLifecycleOwner(this);

        fromOrder = getIntent().getBooleanExtra("fromOrder", false);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        adapter = new TeachersAdapter(this, teachers);
        binding.recyclerTeacher.setAdapter(adapter);

        if (!fromOrder) {
            binding.recyclerSort.setVisibility(View.VISIBLE);
            String ll = getIntent().getStringExtra("selectedLabelId");
            selectedLabelId = TextUtils.isEmpty(ll) ? "all" : ll;
            AVObject object = new AVObject();
            object.put(LabelsInfo.LABEL_NAME, "全部");
            object.setObjectId("all");
            labels.add(object);
            onclick = new Onclick() {
                @Override
                public void onClick(String id) {
                    mPresenter.getTeachers(id);
                }
            };
            labelsAdapter = new TeachersLabelsAdapter(this, labels, selectedLabelId, onclick);
            layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
            binding.recyclerSort.setLayoutManager(layoutManager);
            binding.recyclerSort.setAdapter(labelsAdapter);
        } else {
            binding.recyclerSort.setVisibility(View.GONE);
        }

        mPresenter.getLabels();
        mPresenter.getTeachers(selectedLabelId);
    }

    @Override
    protected TeachersPresenter createPresenter() {
        return new TeachersPresenter(this);
    }

    @Override
    public void setData(List<AVUser> students) {
        teachers.clear();
        teachers.addAll(students);
        adapter.refreshLabels(new ArrayList<>(), fromOrder);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setLabels(List<AVObject> labels) {
        adapter.refreshLabels(labels, fromOrder);
        adapter.notifyDataSetChanged();
        if (!fromOrder) {
            this.labels.addAll(labels);
            for (int i = 0; i < labels.size(); i++) {
                if (selectedLabelId.equals(labels.get(i).getObjectId())) {
                    layoutManager.scrollToPositionWithOffset(i, 200);
                }
            }
            labelsAdapter.notifyDataSetChanged();
        }
    }

    private Onclick onclick;

    public void setOnclick(Onclick onclick) {
        this.onclick = onclick;
    }

    public interface Onclick {
        void onClick(String id);
    }
}
