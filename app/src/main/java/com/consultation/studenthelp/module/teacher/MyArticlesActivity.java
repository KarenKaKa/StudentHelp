package com.consultation.studenthelp.module.teacher;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.FragmentTransaction;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseActivity;
import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.module.main.news.NewsFragment;

import cn.leancloud.AVUser;

/**
 * 我的文章列表
 */
public class MyArticlesActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_articles);

        findViewById(R.id.ivBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, NewsFragment.newInstance(AVUser.getCurrentUser().getObjectId()));
        fragmentTransaction.commitNowAllowingStateLoss();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}