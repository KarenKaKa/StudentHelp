package com.consultation.studenthelp.module.leavemessage;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseActivity;
import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.databinding.ActivityLeaveMessageBinding;
import com.consultation.studenthelp.net.vo.MessageInfo;
import com.consultation.studenthelp.net.vo.UserInfo;

import cn.leancloud.AVObject;
import cn.leancloud.AVUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 留言
 */
public class LeaveMessageActivity extends BaseActivity implements View.OnClickListener {
    private ActivityLeaveMessageBinding binding;
    private String teacherId;
    private String teacherName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_leave_message);
        binding.setLifecycleOwner(this);
        binding.setOnClickListener(this);

        teacherId = getIntent().getStringExtra(UserInfo.OBJECT_ID);
        teacherName = getIntent().getStringExtra(UserInfo.USER_NAME);

        binding.etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.title.setText(charSequence.length() + "/200");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ivBack) {
            onBackPressed();
        } else if (id == R.id.btnSubmit) {
            String content = binding.etInput.getText().toString().trim();
            if (TextUtils.isEmpty(content)) {
                toast("请留言");
                return;
            }
            AVObject order = new AVObject(MessageInfo.TABLE_NAME);
            order.put(MessageInfo.MESSAGE_TEACHER_ID, teacherId);
            order.put(MessageInfo.MESSAGE_TEACHER_NAME, teacherName);
            order.put(MessageInfo.MESSAGE_STUDENT_ID, AVUser.currentUser().getObjectId());
            order.put(MessageInfo.MESSAGE_STUDENT_NAME, AVUser.currentUser().getUsername());
            order.put(MessageInfo.MESSAGE_CONTENT, content);
            order.saveInBackground().subscribe(new Observer<AVObject>() {
                public void onSubscribe(Disposable disposable) {
                }

                public void onNext(AVObject todo) {
                    toast("留言成功");
                    finish();
                }

                public void onError(Throwable throwable) {
                    toast("留言失败：" + throwable.getMessage());
                }

                public void onComplete() {
                }
            });
        }
    }
}