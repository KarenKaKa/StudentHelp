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
import com.consultation.studenthelp.utils.Constants;

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

        binding.rbHome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    showFragment(Constants.TAG_FRAGMENT_NEWS);
                }
            }
        });

        binding.rbHome.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    showFragment(Constants.TAG_FRAGMENT_MINE);
                }
            }
        });
        binding.rbHome.setChecked(true);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void showFragment(String fragmentTag) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(currFragment);
        if (getSupportFragmentManager().findFragmentByTag(fragmentTag) == null) {
            switch (fragmentTag) {
                case Constants.TAG_FRAGMENT_HOME:
//                    currFragment = HomeFragment.newInstance();
                    break;
                case Constants.TAG_FRAGMENT_NEWS:
//                    currFragment = NewsHomeFragment.newInstance();
                    break;
                case Constants.TAG_FRAGMENT_MINE:
//                    currFragment = MineFragment.newInstance();
                    break;
            }
            fragmentTransaction.add(R.id.fragment_container, currFragment, fragmentTag);

        } else {
            currFragment = getSupportFragmentManager().findFragmentByTag(fragmentTag);
            fragmentTransaction.show(currFragment);
        }
        fragmentTransaction.commitNowAllowingStateLoss();
    }
}