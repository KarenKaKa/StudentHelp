package com.consultation.studenthelp.module.main;

import android.os.Bundle;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseActivity;
import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.databinding.ActivityMainBinding;
import com.consultation.studenthelp.module.main.home.HomeFragment;
import com.consultation.studenthelp.module.main.mine.MineFragment;
import com.consultation.studenthelp.module.main.news.NewsFragment;
import com.consultation.studenthelp.utils.Constants;

import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.im.v2.AVIMClient;
import cn.leancloud.im.v2.AVIMException;
import cn.leancloud.im.v2.callback.AVIMClientCallback;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private Long timeBackPress = 0l;
    private Fragment currFragment;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        binding.rbHome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    showFragment(Constants.TAG_FRAGMENT_HOME);
                }
            }
        });
        binding.rbNews.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    showFragment(Constants.TAG_FRAGMENT_NEWS);
                }
            }
        });
        binding.rbMine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    showFragment(Constants.TAG_FRAGMENT_MINE);
                }
            }
        });
        binding.rbHome.setChecked(true);
        showFragment(Constants.TAG_FRAGMENT_HOME);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void showFragment(String fragmentTag) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (currFragment != null) {
            fragmentTransaction.hide(currFragment);
        }
        if (getSupportFragmentManager().findFragmentByTag(fragmentTag) == null) {
            switch (fragmentTag) {
                case Constants.TAG_FRAGMENT_HOME:
                    currFragment = HomeFragment.newInstance();
                    break;
                case Constants.TAG_FRAGMENT_NEWS:
                    currFragment = NewsFragment.newInstance();
                    break;
                case Constants.TAG_FRAGMENT_MINE:
                    currFragment = MineFragment.newInstance();
                    break;
            }
            fragmentTransaction.add(R.id.fragment_container, currFragment, fragmentTag);
        } else {
            currFragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);
            fragmentTransaction.show(currFragment);
        }
        fragmentTransaction.commitNowAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        if (timeBackPress + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            toast("再次点击退出应用~");
        }
        timeBackPress = System.currentTimeMillis();

    }
}