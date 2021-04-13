package com.consultation.studenthelp.module.main.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseFragment;
import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.databinding.FragmentMineBinding;
import com.consultation.studenthelp.module.login.LoginActivity;
import com.consultation.studenthelp.utils.UserSpUtils;

import java.util.Objects;

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
            UserSpUtils.logout();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            Objects.requireNonNull(getActivity()).finish();
        }
    }
}