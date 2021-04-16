package com.consultation.studenthelp.module.question;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.databinding.ActivityTestBinding;
import com.consultation.studenthelp.net.vo.AnswersBean;
import com.consultation.studenthelp.net.vo.QuestionsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 答题页面
 */
public class TestActivity extends AppCompatActivity {
    private String questionId;
    private ActivityTestBinding binding;
    private List<QuestionsBean> list = new ArrayList<>();
    private TestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        binding.setLifecycleOwner(this);

        questionId = getIntent().getStringExtra("questionId");

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        list.addAll(List.of(new QuestionsBean(List.of(new AnswersBean("1", 1), new AnswersBean("2", 2)), false),
                new QuestionsBean(List.of(new AnswersBean("1", 1), new AnswersBean("2", 2)), true)));
        adapter = new TestAdapter(this, list);
        binding.recycler.setAdapter(adapter);

    }

}