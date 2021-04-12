package com.consultation.studenthelp.module.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseFragment;
import com.consultation.studenthelp.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends BaseFragment {
    private FragmentHomeBinding binding;
    private HomeTeacherAdapter teacherAdapter;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = DataBindingUtil.bind(view);
        binding.setLifecycleOwner(this);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.banner.addBannerLifecycleObserver(this)
                .setAdapter(new HomeBannerAdapter(getContext(), List.of(R.drawable.image1, R.drawable.image2)))
                .start();

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        binding.recyclerTeacher.setLayoutManager(layoutManager);
        binding.recyclerTeacher.setAdapter(new HomeTeacherAdapter(getContext(),
                List.of(new TeacherBean("张老师", "资深心理咨询师"), new TeacherBean("李兰香", "心理硕士")
                        , new TeacherBean("王雪松", "国际二级心理咨询师"), new TeacherBean("孙雅苑", "北大心理硕士"))));


        binding.recyclerNews.setAdapter(new HomeNewsAdapter(getContext(),
                List.of(new NewsBean("", ""), new NewsBean("", ""), new NewsBean("", ""), new NewsBean("", ""), new NewsBean("", ""), new NewsBean("", ""), new NewsBean("", ""), new NewsBean("", ""), new NewsBean("", ""))));
    }
}