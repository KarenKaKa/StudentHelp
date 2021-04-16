package com.consultation.studenthelp.module.question;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseActivity;
import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.databinding.ActivityTestBinding;
import com.consultation.studenthelp.net.vo.Answer;
import com.consultation.studenthelp.net.vo.MyQuestionsInfo;
import com.consultation.studenthelp.net.vo.Question;
import com.consultation.studenthelp.net.vo.Questionnaire;
import com.consultation.studenthelp.utils.DataUtils;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 答题页面
 */
public class TestActivity extends BaseActivity {
    private Questionnaire questionnaire;
    private ActivityTestBinding binding;
    private List<Question> list = new ArrayList<>();
    private TestAdapter adapter;
    private int scores = 0;//总分

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        binding.setLifecycleOwner(this);

        questionnaire = (Questionnaire) getIntent().getSerializableExtra("questionnaire");
        if (questionnaire == null) {
            finish();
        }
        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.tvTitle.setText(questionnaire.getQuestionnaireTitle());
        binding.tvSubTitle.setText(questionnaire.getQuestionnaireSubTitle());

        list.addAll(DataUtils.getQuestionList(questionnaire.getId()));
        adapter = new TestAdapter(this, list);
        binding.recycler.setAdapter(adapter);

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < list.size(); i++) {
                    Question question = list.get(i);
                    boolean answered = false;
                    for (Answer answer : question.getAnswerList()) {
                        if (answer.isSelected()) {
                            answered = true;
                            scores += answer.getScore();
                        }
                    }
                    if (!answered) {
                        toast("第" + (i + 1) + "题未作答");
                        return;
                    }
                }
                AVObject order = new AVObject(MyQuestionsInfo.TABLE_NAME);
                order.put(MyQuestionsInfo.USER_ID, AVUser.currentUser().getObjectId());
                order.put(MyQuestionsInfo.QUESTION_ID, String.valueOf(questionnaire.getId()));
                order.put(MyQuestionsInfo.QUESTION_TITLE, questionnaire.getQuestionnaireTitle());
                order.put(MyQuestionsInfo.QUESTION_SCORES, scores);
                order.put(MyQuestionsInfo.QUESTION_SCORINGCRITERIA, questionnaire.getScoringCriteria());
                order.saveInBackground().subscribe(new Observer<AVObject>() {
                    public void onSubscribe(Disposable disposable) {
                    }

                    public void onNext(AVObject todo) {
                    }

                    public void onError(Throwable throwable) {
                    }

                    public void onComplete() {
                        AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this, TimePickerDialog.THEME_HOLO_LIGHT);
                        builder.setTitle("测试结果")
                                .setMessage("完成测试，得分" + scores + "\n" + questionnaire.getScoringCriteria())
                                .setCancelable(false)
                                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        finish();
                                    }
                                })
                                .create().show();
                    }
                });
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}