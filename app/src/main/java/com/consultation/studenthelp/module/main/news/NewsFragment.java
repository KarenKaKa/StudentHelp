package com.consultation.studenthelp.module.main.news;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseFragment;
import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.net.RxSchedulers;
import com.consultation.studenthelp.net.vo.ArticlesInfo;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 文章列表
 */
public class NewsFragment extends BaseFragment {
    private RecyclerView recyclerNews;
    private List<AVObject> dataList = new ArrayList<>();
    private String teacherId;
    private TextView tvTitle;

    public static NewsFragment newInstance() {
        NewsFragment fragment = new NewsFragment();
        return fragment;
    }

    public static NewsFragment newInstance(String teacherId) {
        NewsFragment fragment = new NewsFragment();
        fragment.teacherId = teacherId;
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
        getData();
        tvTitle = view.findViewById(R.id.tvTitle);
        tvTitle.setText(teacherId == null ? "主题阅读" : "我的文章");
        recyclerNews = view.findViewById(R.id.recyclerNews);
        recyclerNews.setAdapter(new NewsAdapter(getContext(), dataList));

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void getData() {
        AVQuery<AVObject> query = new AVQuery<>(ArticlesInfo.TABLE_NAME);
        if (teacherId != null) {
            query.whereEqualTo(ArticlesInfo.NEWS_TEACHER_ID, teacherId);
        }
        query.findInBackground().compose(RxSchedulers.Schedulers()).subscribe(new Observer<List<AVObject>>() {
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(List<AVObject> result) {
                if (!result.isEmpty()) {
                    showData(result);
                }
            }

            public void onError(Throwable throwable) {
                Log.e(this.getClass().getSimpleName(), throwable.toString());
            }

            public void onComplete() {
            }
        });

    }

    private void showData(List<AVObject> result) {
        dataList.clear();
        dataList.addAll(result);
        recyclerNews.getAdapter().notifyDataSetChanged();
    }
}