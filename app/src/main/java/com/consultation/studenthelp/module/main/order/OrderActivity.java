package com.consultation.studenthelp.module.main.order;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.databinding.DataBindingUtil;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseActivity;
import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.databinding.ActivityOrderBinding;
import com.consultation.studenthelp.net.vo.OrderInfo;
import com.consultation.studenthelp.net.vo.UserInfo;

import java.util.Calendar;

import cn.leancloud.AVObject;
import cn.leancloud.AVUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 预约
 */
public class OrderActivity extends BaseActivity implements View.OnClickListener {
    private ActivityOrderBinding binding;
    private String teacherId;
    private String name;
    private Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order);
        binding.setLifecycleOwner(this);
        binding.setOnClickListener(this);

        teacherId = getIntent().getStringExtra(UserInfo.OBJECT_ID);
        name = getIntent().getStringExtra(UserInfo.USER_NAME);

        binding.teacherName.setText(name);

        myCalendar = Calendar.getInstance();

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
        } else if (id == R.id.start) {
            orderDate(true);
        } else if (id == R.id.startTime) {
            orderTime(true);
        } else if (id == R.id.end) {
            orderDate(false);
        } else if (id == R.id.endTime) {
            orderTime(false);
        } else if (id == R.id.btnSubmit) {
            AVObject order = new AVObject(OrderInfo.TABLE_NAME);
            order.put(OrderInfo.ORDER_TEACHER_ID, teacherId);
            order.put(OrderInfo.ORDER_TEACHER_NAME, name);
            order.put(OrderInfo.ORDER_STUDENT_ID, AVUser.currentUser().getObjectId());
            order.put(OrderInfo.ORDER_STUDENT_NAME, AVUser.currentUser().getUsername());
            order.put(OrderInfo.ORDER_START_TIME, binding.start.getText().toString() + binding.startTime.getText().toString());
            order.put(OrderInfo.ORDER_END_TIME, binding.end.getText().toString() + binding.endTime.getText().toString());
            order.put(OrderInfo.ORDER_ADDRESS, binding.etInput.getText().toString());
            order.saveInBackground().subscribe(new Observer<AVObject>() {
                public void onSubscribe(Disposable disposable) {
                }

                public void onNext(AVObject todo) {
                    toast("预约成功");
                    finish();
                }

                public void onError(Throwable throwable) {
                    toast("预约失败：" + throwable.getMessage());
                }

                public void onComplete() {
                }
            });
        }
    }

    private int startD = 0;
    private int endD = 0;
    private int startT = 0;
    private int endT = 0;

    private void orderDate(boolean start) {
        new DatePickerDialog(this, DatePickerDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                if (start) {
                    startD = year + monthOfYear + dayOfMonth;
                    binding.start.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                } else {
                    endD = year + monthOfYear + dayOfMonth;
                    if (endD < startD) {
                        toast("结束日期不能早于开始日期");
                        return;
                    } else {
                        binding.end.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }
            }
        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void orderTime(boolean start) {
        new TimePickerDialog(this, TimePickerDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int h, int m) {
                if (start) {
                    startT = h;
                    binding.startTime.setText(" " + h + ":" + m);
                } else {
                    endT = h;
                    //TODO 时间判断需更精确
                    if (startD == endD && endT - startT < 1) {
                        toast("结束时间不能早于开始时间");
                        return;
                    } else {
                        binding.endTime.setText(" " + h + ":" + m);
                    }
                }
            }
        }, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), true).show();
    }
}