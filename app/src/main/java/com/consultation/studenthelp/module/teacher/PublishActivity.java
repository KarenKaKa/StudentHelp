package com.consultation.studenthelp.module.teacher;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.consultation.studenthelp.R;
import com.consultation.studenthelp.base.BaseActivity;
import com.consultation.studenthelp.base.BasePresenter;
import com.consultation.studenthelp.databinding.ActivityPublishBinding;
import com.consultation.studenthelp.net.vo.ArticlesInfo;
import com.consultation.studenthelp.utils.KeyBoardUtils;
import com.consultation.studenthelp.utils.RichUtils;
import com.consultation.studenthelp.utils.UserSpUtils;
import com.consultation.studenthelp.view.CommonPopupWindow;
import com.consultation.studenthelp.view.RichEditor;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;
import com.yalantis.ucrop.UCropActivity;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVUser;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.yalantis.ucrop.UCrop.EXTRA_OUTPUT_URI;

/**
 * 老师发布文章页面
 */
public class PublishActivity extends BaseActivity implements View.OnClickListener {
    private ActivityPublishBinding binding;
    private ArrayList<ImageItem> selectImages = new ArrayList<>();
    private String currentUrl = "";
    private CommonPopupWindow popupWindow; //编辑图片的pop

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_publish);
        binding.setLifecycleOwner(this);
        binding.setOnClickListener(this);

        initPop();
        initEditor();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private void initEditor() {
        //输入框显示字体的大小
        binding.richEditor.setEditorFontSize(18);
        //输入框显示字体的颜色
        binding.richEditor.setEditorFontColor(Color.parseColor("#1b1b1b"));
        //输入框背景设置
        binding.richEditor.setEditorBackgroundColor(Color.WHITE);
        //输入框文本padding
        binding.richEditor.setPadding(10, 10, 10, 10);
        //输入提示文本
        binding.richEditor.setPlaceholder("请开始你的创作！~");

        binding.richEditor.setOnDecorationChangeListener(new RichEditor.OnDecorationStateListener() {
            @Override
            public void onStateChangeListener(String text, List<RichEditor.Type> types) {
                ArrayList<String> flagArr = new ArrayList<>();
                for (int i = 0; i < types.size(); i++) {
                    flagArr.add(types.get(i).name());
                }

                if (flagArr.contains("BOLD")) {
                    binding.buttonBold.setImageResource(R.drawable.bold_);
                } else {
                    binding.buttonBold.setImageResource(R.drawable.bold);
                }

                if (flagArr.contains("UNDERLINE")) {
                    binding.buttonUnderline.setImageResource(R.drawable.underline_);
                } else {
                    binding.buttonUnderline.setImageResource(R.drawable.underline);
                }


                if (flagArr.contains("ORDEREDLIST")) {
                    binding.buttonListUl.setImageResource(R.drawable.list_ul);
                    binding.buttonListOl.setImageResource(R.drawable.list_ol_);
                } else {
                    binding.buttonListOl.setImageResource(R.drawable.list_ol);
                }

                if (flagArr.contains("UNORDEREDLIST")) {
                    binding.buttonListOl.setImageResource(R.drawable.list_ol);
                    binding.buttonListUl.setImageResource(R.drawable.list_ul_);
                } else {
                    binding.buttonListUl.setImageResource(R.drawable.list_ul);
                }

            }
        });


        binding.richEditor.setImageClickListener(new RichEditor.ImageClickListener() {
            @Override
            public void onImageClick(String imageUrl) {
                currentUrl = imageUrl;
                popupWindow.showBottom(binding.getRoot(), 0.5f);
            }
        });


    }


    private void initPop() {
        View view = LayoutInflater.from(PublishActivity.this).inflate(R.layout.newapp_pop_picture, null);
        view.findViewById(R.id.linear_cancle).setOnClickListener(v -> {
            popupWindow.dismiss();
        });

        view.findViewById(R.id.linear_editor).setOnClickListener(v -> {
            //编辑图片
            PermissionX.init(this)
                    .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                    .request(new RequestCallback() {
                        @Override
                        public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                            if (allGranted) {
                                if (ActivityCompat.checkSelfPermission(PublishActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(PublishActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                    Intent intent = new Intent(PublishActivity.this, UCropActivity.class);
                                    intent.putExtra("filePath", currentUrl);
                                    String destDir = getFilesDir().getAbsolutePath().toString();
                                    String fileName = "SampleCropImage" + System.currentTimeMillis() + ".jpg";
                                    intent.putExtra("outPath", destDir + fileName);
                                    startActivityForResult(intent, 11);
                                    popupWindow.dismiss();

                                }
                            } else {
                                toast("相册需要此权限");
                            }
                        }
                    });
        });

        view.findViewById(R.id.linear_delete_pic).setOnClickListener(v -> {
            //删除图片

            String removeUrl = "<img src=\"" + currentUrl + "\" alt=\"student_image\" width=\"100%\"><br>";

            String newUrl = binding.richEditor.getHtml().replace(removeUrl, "");
            currentUrl = "";
            binding.richEditor.setHtml(newUrl);
            if (RichUtils.isEmpty(binding.richEditor.getHtml())) {
                binding.richEditor.setHtml("");
            }
            popupWindow.dismiss();
        });
        popupWindow = new CommonPopupWindow.Builder(PublishActivity.this)
                .setView(view)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOutsideTouchable(true)//在外不可用手指取消
                .setAnimationStyle(R.style.pop_animation)//设置popWindow的出场动画
                .create();


        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                binding.richEditor.setInputEnabled(true);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_finish:
                finish();
                break;
            case R.id.txt_publish:
                if (TextUtils.isEmpty(binding.editName.getText().toString())) {
                    toast("写个标题吧");
                    return;
                }
                if (TextUtils.isEmpty(binding.richEditor.getHtml())) {
                    toast("写点内容吧");
                    return;
                }
                AVObject news = new AVObject(ArticlesInfo.TABLE_NAME);
                news.put(ArticlesInfo.NEWS_TEACHER_ID, AVUser.currentUser().getObjectId());
                news.put(ArticlesInfo.NEWS_TEACHER_NAME, UserSpUtils.getUserName());
                news.put(ArticlesInfo.NEWS_CONTENT, binding.richEditor.getHtml());
                news.put(ArticlesInfo.NEWS_TITLE, binding.editName.getText().toString().trim());
                news.saveInBackground().subscribe(new Observer<AVObject>() {
                    public void onSubscribe(Disposable disposable) {
                    }

                    public void onNext(AVObject todo) {
                        toast("发布成功");
                        finish();
                    }

                    public void onError(Throwable throwable) {
                        // 异常处理
                        toast(throwable.getMessage());
                    }

                    public void onComplete() {
                    }
                });
                break;
            case R.id.button_rich_do:
                //反撤销
                binding.richEditor.redo();
                break;
            case R.id.button_rich_undo:
                //撤销
                binding.richEditor.undo();
                break;
            case R.id.button_bold:
                //加粗
                againEdit();
                binding.richEditor.setBold();
                break;

            case R.id.button_underline:
                //加下划线
                againEdit();
                binding.richEditor.setUnderline();
                break;

            case R.id.button_list_ul:
                //加带点的序列号
                againEdit();
                binding.richEditor.setBullets();
                break;
            case R.id.button_list_ol:
                //加带数字的序列号
                againEdit();
                binding.richEditor.setNumbers();
                break;
            case R.id.button_image:
                if (!TextUtils.isEmpty(binding.richEditor.getHtml())) {
                    ArrayList<String> arrayList = RichUtils.returnImageUrlsFromHtml(binding.richEditor.getHtml());
                    if (arrayList != null && arrayList.size() >= 9) {
                        Toast.makeText(PublishActivity.this, "最多添加9张照片~", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }
                PermissionX.init(this)
                        .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .request(new RequestCallback() {
                            @Override
                            public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                                if (allGranted) {
                                    if (ActivityCompat.checkSelfPermission(PublishActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(PublishActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                        selectImage(104, selectImages);
                                        KeyBoardUtils.closeKeybord(binding.editName, PublishActivity.this);
                                    }
                                } else {
                                    toast("相册需要此权限");
                                }
                            }
                        });
                break;


        }
    }

    private void againEdit() {
        //如果第一次点击例如加粗，没有焦点时，获取焦点并弹出软键盘
        binding.richEditor.focusEditor();
        KeyBoardUtils.openKeybord(binding.editName, PublishActivity.this);
    }

    public void selectImage(int requestCode, ArrayList<ImageItem> imageItems) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setCrop(false);
        imagePicker.setMultiMode(false);
        imagePicker.setShowCamera(true);
        Intent intent = new Intent(this, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_IMAGES, imageItems);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (requestCode == 104) {
                selectImages.clear();
                ArrayList<ImageItem> selects = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                selectImages.addAll(selects);

                againEdit();
                binding.richEditor.insertImage(selectImages.get(0).path, "student_image");

                KeyBoardUtils.openKeybord(binding.editName, PublishActivity.this);
                binding.richEditor.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (binding.richEditor != null) {
                            binding.richEditor.scrollToBottom();
                        }
                    }
                }, 200);
            }
        } else if (resultCode == -1) {
            if (requestCode == 11) {
                String outPath = data.getStringExtra(EXTRA_OUTPUT_URI);
                if (!TextUtils.isEmpty(outPath)) {
                    String newHtml = binding.richEditor.getHtml().replace(currentUrl, outPath);

                    binding.richEditor.setHtml(newHtml);
                    currentUrl = "";
                }
            }
        }
    }
}