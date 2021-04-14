package com.consultation.studenthelp.module.userinfo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseActivity;
import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.databinding.ActivityEditUserInfoBinding;
import com.consultation.studenthelp.net.vo.LabelsInfo;
import com.consultation.studenthelp.net.vo.UserInfo;
import com.consultation.studenthelp.utils.UserSpUtils;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import cn.leancloud.AVUser;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 编辑用户信息
 */
public class EditUserInfoActivity extends BaseActivity implements View.OnClickListener {
    private ActivityEditUserInfoBinding binding;
    private String gender = "男";
    private EditLablesAdapter adapter;
    private List<AVObject> sortList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_user_info);
        binding.setLifecycleOwner(this);

        binding.setOnClickListener(this);
        binding.switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                gender = b ? "女" : "男";
            }
        });

        binding.group.setVisibility(UserSpUtils.getUserType().equals(UserInfo.USER_TYPE_TEACHER) ? View.VISIBLE : View.GONE);
        binding.etInput.setText(AVUser.getCurrentUser().getUsername());
        binding.etSkills.setText(AVUser.getCurrentUser().getString(UserInfo.USER_SKILLS));
        binding.switchBtn.setChecked(AVUser.getCurrentUser().get(UserInfo.USER_GENDER).equals("女"));

        GridLayoutManager sortManager = new GridLayoutManager(this, 4);
        binding.recyclerLabels.setLayoutManager(sortManager);
        adapter = new EditLablesAdapter(this, sortList);
        binding.recyclerLabels.setAdapter(adapter);

        getLabels();
    }

    private void getLabels() {
        AVQuery<AVObject> query = new AVQuery<>(LabelsInfo.TABLE_NAME);
        query.findInBackground().subscribe(new Observer<List<AVObject>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
            }

            @Override
            public void onNext(@NonNull List<AVObject> labels) {
                sortList.addAll(labels);
                AVUser user = AVUser.getCurrentUser();
                String label = user.getString(UserInfo.USER_LABELS);
                if (!TextUtils.isEmpty(label)) {
                    adapter.refreshSelected(label.split(","));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(@NonNull Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ivBack) {
            onBackPressed();
        } else if (id == R.id.ivHead) {
            toast("修改头像");
        } else if (id == R.id.btnSubmit) {
            String userName = binding.etInput.getText().toString().trim();
            String skill = binding.etSkills.getText().toString().trim();
            if (TextUtils.isEmpty(userName)) {
                toast("请输入用户名");
                return;
            }
            AVUser user = AVUser.currentUser();
            user.setUsername(userName);
            user.put(UserInfo.USER_SKILLS, skill);
            user.put(UserInfo.USER_GENDER, gender);
            user.put(UserInfo.USER_LABELS, adapter.getLabels());
            user.saveInBackground().subscribe(new Observer<AVObject>() {
                public void onSubscribe(Disposable disposable) {
                }

                public void onNext(AVObject avUser) {
                    toast("修改成功");
                    finish();
                }

                public void onError(Throwable throwable) {
                    toast("修改失败：" + throwable.getMessage());
                }

                public void onComplete() {
                }
            });
        }
    }
}