package com.consultation.studenthelp.module.main.news;

import android.annotation.TargetApi;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.databinding.DataBindingUtil;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseActivity;
import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.databinding.ActivityNewsDetailBinding;
import com.consultation.studenthelp.net.RxSchedulers;
import com.consultation.studenthelp.net.vo.ArticlesInfo;
import com.consultation.studenthelp.net.vo.UserInfo;
import com.consultation.studenthelp.utils.RichUtils;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import cn.leancloud.AVUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 文章详情
 */
public class NewsDetailActivity extends BaseActivity {
    private ActivityNewsDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news_detail);
        binding.setLifecycleOwner(this);

        getDetails(getIntent().getStringExtra("id"));

        binding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.tvChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO
                toast("打开聊天页面咨询");
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void getDetails(String id) {
        AVQuery<AVObject> query = new AVQuery<>(ArticlesInfo.TABLE_NAME);
        query.whereEqualTo(ArticlesInfo.OBJECT_ID, id);
        query.findInBackground().compose(RxSchedulers.Schedulers()).subscribe(new Observer<List<AVObject>>() {
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(List<AVObject> result) {
                if (!result.isEmpty()) {
                    showData(result.get(0));
                }
            }

            public void onError(Throwable throwable) {
                toast(throwable.getMessage());
            }

            public void onComplete() {
            }
        });
    }

    private void showData(AVObject details) {
        String teacherName = details.getString(ArticlesInfo.NEWS_TEACHER_NAME);
        binding.name.setText(teacherName);
        AVQuery query = AVUser.getQuery();
        query.whereEqualTo(UserInfo.USER_NAME, teacherName);
        query.findInBackground().compose(RxSchedulers.Schedulers()).subscribe(new Observer<List<AVUser>>() {
            public void onSubscribe(Disposable disposable) {
            }

            public void onNext(List<AVUser> result) {
                if (!result.isEmpty()) {
                    binding.skills.setText(result.get(0).getString(UserInfo.USER_SKILLS));
                }
            }

            public void onError(Throwable throwable) {
                toast(throwable.getMessage());
            }

            public void onComplete() {
            }
        });

        binding.title.setText(details.getString(ArticlesInfo.NEWS_TITLE));
        binding.time.setText(details.getUpdatedAtString());

        WebSettings settings = binding.webView.getSettings();
        //settings.setUseWideViewPort(true);//调整到适合webview的大小，不过尽量不要用，有些手机有问题
        settings.setLoadWithOverviewMode(true);//设置WebView是否使用预览模式加载界面。
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowFileAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        binding.webView.setVerticalScrollBarEnabled(false);//不能垂直滑动
        binding.webView.setHorizontalScrollBarEnabled(false);//不能水平滑动
        settings.setTextSize(WebSettings.TextSize.NORMAL);//通过设置WebSettings，改变HTML中文字的大小
        settings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        //设置WebView属性，能够执行Javascript脚本
        binding.webView.getSettings().setJavaScriptEnabled(true);//设置js可用

        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//支持内容重新布局
        binding.webView.setWebViewClient(webViewClient);
        binding.webView.setWebChromeClient(new WebChromeClient());
        String data = details.getString(ArticlesInfo.NEWS_CONTENT);
        ArrayList<String> arrayList = RichUtils.returnImageUrlsFromHtml(data);
        if (arrayList.size() > 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                if (!arrayList.get(i).contains("http")) {
                    //如果不是http,那么就是本地绝对路径，要加上file
                    data = data.replace(arrayList.get(i), "file://" + arrayList.get(i));
                }
            }
        }

        binding.webView.loadDataWithBaseURL(null, data, "text/html", "utf-8", null);
    }

    public WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }


        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return super.shouldOverrideUrlLoading(view, url);
        }


        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            return super.shouldOverrideUrlLoading(view, request);
        }
    };
}