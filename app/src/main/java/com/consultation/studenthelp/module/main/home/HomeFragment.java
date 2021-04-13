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
import com.consultation.studenthelp.module.teachers.TeachersActivity;
import com.consultation.studenthelp.net.vo.UserBean;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.AVObject;

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeView, View.OnClickListener {
    private FragmentHomeBinding binding;
    private HomeTeacherAdapter teacherAdapter;
    private HomeSortAdapter sortAdapter;
    private List<AVObject> sortList = new ArrayList<>();

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

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        binding.recyclerTeacher.setLayoutManager(layoutManager);
        binding.recyclerTeacher.setAdapter(new HomeTeacherAdapter(getContext(),
                List.of(new UserBean("张老师", "资深心理咨询师"), new UserBean("李兰香", "心理硕士")
                        , new UserBean("王雪松", "国际二级心理咨询师"), new UserBean("孙雅苑", "北大心理硕士"))));


//        binding.recyclerNews.setAdapter(new NewsAdapter(getContext(),
//                List.of(new NewsBean("", ""), new NewsBean("", ""), new NewsBean("", ""), new NewsBean("", ""), new NewsBean("", ""), new NewsBean("", ""), new NewsBean("", ""), new NewsBean("", ""), new NewsBean("", ""))));

        GridLayoutManager sortManager = new GridLayoutManager(getContext(), 4);
        binding.recyclerSort.setLayoutManager(sortManager);
        sortAdapter = new HomeSortAdapter(getContext(), sortList);
        binding.recyclerSort.setAdapter(sortAdapter);

        mPresenter.getSorts();
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
}