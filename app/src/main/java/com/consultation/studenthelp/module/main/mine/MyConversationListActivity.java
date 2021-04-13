package com.consultation.studenthelp.module.main.mine;

import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseActivity;
import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.databinding.ActivityMyConversationListBinding;

import cn.leancloud.chatkit.activity.LCIMConversationListFragment;

/**
 * 我的咨询列表
 */
public class MyConversationListActivity extends BaseActivity {
    private ActivityMyConversationListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_conversation_list);
        binding.setLifecycleOwner(this);

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, new LCIMConversationListFragment());
        fragmentTransaction.commitNowAllowingStateLoss();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}