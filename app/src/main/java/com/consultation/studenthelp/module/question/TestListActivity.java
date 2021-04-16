package com.consultation.studenthelp.module.question;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseActivity;
import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.databinding.ActivityOrderListBinding;
import com.consultation.studenthelp.module.leavemessage.MessageListAdapter;
import com.consultation.studenthelp.net.vo.Questionnaire;
import com.consultation.studenthelp.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 试卷列表
 */
public class TestListActivity extends BaseActivity {
    private ActivityOrderListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_list);
        binding.setLifecycleOwner(this);

        binding.tvTitle.setText("试题列表");
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.recyclerOrder.setAdapter(new QuestionnaireAdapter(this, DataUtils.getQuestionnaireList()));
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}