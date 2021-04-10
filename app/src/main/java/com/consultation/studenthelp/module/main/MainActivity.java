package com.consultation.studenthelp.module.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseActivity;

import java.util.HashMap;

public class MainActivity extends BaseActivity<MainPresenter> implements MainView {
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.getUserInfo(new HashMap());
            }
        });
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void setText(String hahahahah) {
        textView.setText(hahahahah);
    }


    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }
}