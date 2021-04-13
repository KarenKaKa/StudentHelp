package com.consultation.studenthelp.module.main.home;

import android.content.Intent;
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
import com.consultation.studenthelp.module.main.news.NewsAdapter;
import com.consultation.studenthelp.module.teachers.TeachersActivity;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVUser;

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeView, View.OnClickListener {
    private FragmentHomeBinding binding;
    private HomeTeacherAdapter teacherAdapter;
    private HomeSortAdapter sortAdapter;
    private NewsAdapter newsAdapter;
    private List<AVObject> sortList = new ArrayList<>();
    private List<AVUser> teacherList = new ArrayList<>();
    private List<AVObject> articleList = new ArrayList<>();

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

        binding.tvConsults.setOnClickListener(this);
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.banner.addBannerLifecycleObserver(this)
                .setAdapter(new HomeBannerAdapter(getContext(), List.of(R.drawable.image1, R.drawable.image2)))
                .start();
        //分类
        GridLayoutManager sortManager = new GridLayoutManager(getContext(), 4);
        binding.recyclerSort.setLayoutManager(sortManager);
        sortAdapter = new HomeSortAdapter(getContext(), sortList);
        binding.recyclerSort.setAdapter(sortAdapter);

        //老师
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        binding.recyclerTeacher.setLayoutManager(layoutManager);
        teacherAdapter = new HomeTeacherAdapter(getContext(), teacherList);
        binding.recyclerTeacher.setAdapter(teacherAdapter);

        //文章
        newsAdapter = new NewsAdapter(getContext(), articleList);
        binding.recyclerNews.setAdapter(newsAdapter);


        mPresenter.getSorts();
        mPresenter.getTeachers();
        mPresenter.getArticles();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tvConsults) {
            startActivity(new Intent(getActivity(), TeachersActivity.class));
        }
    }

    @Override
    public void setSortData(List<AVObject> labels) {
        sortList.addAll(labels);
        sortAdapter.notifyDataSetChanged();
    }

    @Override
    public void setTeachers(List<AVUser> teachers) {
        teacherList.addAll(teachers);
        teacherAdapter.notifyDataSetChanged();
    }

    @Override
    public void setArticlesData(List<AVObject> news) {
        articleList.addAll(news);
        newsAdapter.notifyDataSetChanged();
    }
}