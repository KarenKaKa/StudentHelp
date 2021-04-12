package com.consultation.studenthelp.module.main.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseFragment;
import com.consultation.studenthelp.module.main.home.HomeNewsAdapter;
import com.consultation.studenthelp.net.vo.NewsBean;

import java.util.List;

public class NewsFragment extends BaseFragment {

    private RecyclerView recyclerNews;

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerNews = view.findViewById(R.id.recyclerNews);
        recyclerNews.setAdapter(new HomeNewsAdapter(getContext(),
                List.of(new NewsBean("", ""), new NewsBean("", ""), new NewsBean("", ""), new NewsBean("", ""), new NewsBean("", ""),
                        new NewsBean("", ""), new NewsBean("", ""), new NewsBean("", ""), new NewsBean("", ""), new NewsBean("", "")
                        , new NewsBean("", ""), new NewsBean("", ""), new NewsBean("", ""), new NewsBean("", ""), new NewsBean("", "")
                        , new NewsBean("", ""), new NewsBean("", ""), new NewsBean("", ""), new NewsBean("", ""), new NewsBean("", ""))));
    }
}