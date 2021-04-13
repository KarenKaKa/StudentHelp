package com.consultation.studenthelp.module.main.mine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseFragment;
import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.databinding.FragmentMineBinding;
import com.consultation.studenthelp.module.login.LoginActivity;
import com.consultation.studenthelp.module.main.MainActivity;
import com.consultation.studenthelp.utils.UserSpUtils;

import java.util.Objects;

import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.activity.LCIMConversationActivity;
import cn.leancloud.chatkit.utils.LCIMConstants;
import cn.leancloud.im.v2.AVIMClient;
import cn.leancloud.im.v2.AVIMException;
import cn.leancloud.im.v2.callback.AVIMClientCallback;

//TODO 待做个人信息编辑页  性别 昵称
public class MineFragment extends BaseFragment implements View.OnClickListener {
    private FragmentMineBinding binding;

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = DataBindingUtil.bind(view);
        binding.setLifecycleOwner(this);

        binding.ivHead.setOnClickListener(this);
        binding.tvConsults.setOnClickListener(this);
        binding.tvAppointment.setOnClickListener(this);
        binding.tvLogout.setOnClickListener(this);
        binding.tvTeachers.setOnClickListener(this);
        binding.tvTests.setOnClickListener(this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ivHead) {
            toast("修改头像");
        } else if (id == R.id.tvConsults) {
            startActivity(new Intent(getActivity(), MyConversationListActivity.class));
        } else if (id == R.id.tvAppointment) {
            toast("我的预约");
        } else if (id == R.id.tvTests) {
            toast("测试记录");
        } else if (id == R.id.tvTeachers) {
            toast("我喜欢的老师");
        } else if (id == R.id.tvLogout) {
            LCChatKit.getInstance().close(new AVIMClientCallback() {
                @Override
                public void done(AVIMClient avimClient, AVIMException e) {
                    if (null!= e) {
                        e.printStackTrace();
                    } else {
                        getActivity().finish();
                    }
                }
            });
            UserSpUtils.logout();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            Objects.requireNonNull(getActivity()).finish();
        }
    }
}