package com.consultation.studenthelp.module.main.news;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseFragment;
import com.consultation.studenthelp.net.RxSchedulers;
import com.consultation.studenthelp.net.bean.Artical;
import com.consultation.studenthelp.net.vo.NewsBean;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class NewsFragment extends BaseFragment {

    private RecyclerView recyclerNews;
    private List<AVObject> dataList = new ArrayList<>();
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
        getData();
        recyclerNews = view.findViewById(R.id.recyclerNews);
        recyclerNews.setAdapter(new NewsAdapter(getContext(),dataList));

    }

    private void getData() {
        AVQuery<AVObject> query = new AVQuery<>("Articles");
        query.findInBackground().compose(RxSchedulers.Schedulers()).subscribe(new Observer<List<AVObject>>() {
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(List<AVObject> result) {
                if (!result.isEmpty()){
                   showData(result);
                }
            }

            public void onError(Throwable throwable) {
                Log.e(this.getClass().getSimpleName(),throwable.toString());
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