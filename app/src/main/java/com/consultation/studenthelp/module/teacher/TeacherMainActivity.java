package com.consultation.studenthelp.module.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseActivity;
import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.databinding.ActivityTeacherMainBinding;
import com.consultation.studenthelp.module.leavemessage.MyLeaveMessageActivity;
import com.consultation.studenthelp.module.login.LoginActivity;
import com.consultation.studenthelp.module.main.mine.MyConversationListActivity;
import com.consultation.studenthelp.module.main.order.OrderListActivity;
import com.consultation.studenthelp.module.userinfo.EditUserInfoActivity;
import com.consultation.studenthelp.utils.UserSpUtils;

import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.im.v2.AVIMClient;
import cn.leancloud.im.v2.AVIMException;
import cn.leancloud.im.v2.callback.AVIMClientCallback;

public class TeacherMainActivity extends BaseActivity implements View.OnClickListener {
    private ActivityTeacherMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teacher_main);
        binding.setLifecycleOwner(this);

        binding.ivHead.setOnClickListener(this);
        binding.tvConsults.setOnClickListener(this);
        binding.tvAppointment.setOnClickListener(this);
        binding.tvLogout.setOnClickListener(this);
        binding.tvTests.setOnClickListener(this);
        binding.tvMessage.setOnClickListener(this);
        binding.tvArt.setOnClickListener(this);
        binding.tvPubArt.setOnClickListener(this);

        binding.name.setText(UserSpUtils.getUserName());
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ivHead) {
            startActivity(new Intent(this, EditUserInfoActivity.class));
        } else if (id == R.id.tvConsults) {
            startActivity(new Intent(this, MyConversationListActivity.class));
        } else if (id == R.id.tvMessage) {
            startActivity(new Intent(this, MyLeaveMessageActivity.class));
        } else if (id == R.id.tvAppointment) {
            startActivity(new Intent(this, OrderListActivity.class));
        } else if (id == R.id.tvTests) {
            toast("发布测试");
        } else if (id == R.id.tvArt) {
            startActivity(new Intent(this, MyArticlesActivity.class));
        } else if (id == R.id.tvLogout) {
            LCChatKit.getInstance().close(new AVIMClientCallback() {
                @Override
                public void done(AVIMClient avimClient, AVIMException e) {
                    if (null != e) {
                        e.printStackTrace();
                    } else {
                        finish();
                    }
                }
            });
            UserSpUtils.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else if (id == R.id.tvPubArt) {
            startActivity(new Intent(this, PublishActivity.class));
        }
    }
}