package com.consultation.studenthelp.module.main.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseFragment;

public class NewsHomeFragment extends BaseFragment {

    public static NewsHomeFragment newInstance() {
        NewsHomeFragment fragment = new NewsHomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_home, container, false);
    }
}