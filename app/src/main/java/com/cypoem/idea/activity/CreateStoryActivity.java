package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airong.core.utils.LogUtils;
import com.cypoem.idea.R;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.PublishBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.utils.UserInfoTools;
import com.google.gson.Gson;
import com.yalantis.ucrop.UCrop;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CreateStoryActivity extends BaseActivity {
    @BindView(R.id.ll_label)
    LinearLayout mLlLabel;
    @BindView(R.id.pic_switch)
    SwitchCompat mSwitchCompat;
    @BindView(R.id.rl_add_pic)
    RelativeLayout mRlAddPic;

    public static final int SELECT_LABEL = 256;
    public static final int ADD_DESCRIBE = 257;
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.rl_belong)
    RelativeLayout mRlBelong;
    @BindView(R.id.et_start)
    EditText mEtStart;
    //  选择标签的位置
    private int selectedPosition = -1;
    //  是否上传图片
    private boolean hasPicture;
    private String name;
    private String label;

    private String articleStart;
    //  文章类型 1.私人 2.公开
    private int articleType;
    private String picPath;
    private int isCanOverride;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_story;
    }

    public void selectLabel(View view) {
        Intent intent = new Intent(this, AddLabelActivity.class);
        intent.putExtra("selectedPosition", selectedPosition);
        startActivityForResult(intent, SELECT_LABEL);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                // clipSuccess(requestCode, data);
                break;
            case UCrop.RESULT_ERROR:
                //  handleCropError(data);
                break;
            case SELECT_LABEL:
                setLabel(data);
                break;
            case ADD_DESCRIBE:
                // setDescribe(data);
                break;
        }
    }

    private void setLabel(Intent data) {
        mLlLabel.removeAllViews();
        label = data.getStringExtra("label");
        selectedPosition = data.getIntExtra("selectedPosition", -1);
        if (TextUtils.isEmpty(label)) {
            return;
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 10, 0);
        View view = LayoutInflater.from(this).inflate(R.layout.item_label_normal, mLlLabel, false);
        view.setLayoutParams(params);
        TextView tvLabel = view.findViewById(R.id.tv_label);
        tvLabel.setText(label);
        mLlLabel.addView(view);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setListener();
    }

    private void setListener() {
        mSwitchCompat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            hasPicture = isChecked;
            if (isChecked) {
                mRlAddPic.setVisibility(View.VISIBLE);
            } else {
                mRlAddPic.setVisibility(View.GONE);
            }
        });
    }

    @OnClick({R.id.btn_save, R.id.btn_publish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                saveDraft();
                break;
            case R.id.btn_publish:
                publishArticle();
                break;

        }
    }

    private void publishArticle() {
        name = mEtName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            showToast("标题不能为空");
            return;
        }
        if (hasPicture) {
            postArticleWithPic();
        } else {
            postArticle();
        }
    }

    /**
     * 提交不含图片的文章
     */
    private void postArticle() {
        Map<String, Object> map = new HashMap<>();
        map.put("user_id", UserInfoTools.getUserId(this));
        map.put("introduction", articleStart);
        map.put("write_name", name);
        map.put("upStatus", isCanOverride);
        map.put("type",articleType + "");
        map.put("password",UserInfoTools.getUser(this).getPassword());
        map.put("isAddPic",0);
        map.put("status",1);
        map.put("circleId",1);
        map.put("userDefinedLables","");
        map.put("authoritationLables","");
        IdeaApi.getApiService()
                .createStory(map)
                .subscribeOn(Schedulers.io())
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<PublishBean>>(this, false) {
                    @Override
                    public void onSuccess(BasicResponse<PublishBean> response) {
                        showToast(response.getMsg());
                        EventBus.getDefault().post(new EditInfoActivity.UpdateInfoSuccess());
                    }
                });
    }

    /**
     * 提交包含图片的文章
     */
    private void postArticleWithPic() {
        if (TextUtils.isEmpty(picPath)) {
            showToast("为作品添加一张图片吧");
            return;
        }
        File file = new File(picPath);
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("introduction", articleStart)
                .addFormDataPart("write_name", name)
                .addFormDataPart("reStatus", isCanOverride+"")
                .addFormDataPart("type", articleType + "")
                .addFormDataPart("user_id", UserInfoTools.getUserId(this))
                .addFormDataPart("uploadFile", file.getName(), imageBody);
        List<MultipartBody.Part> parts = builder.build().parts();
        IdeaApi.getApiService()
                .createStoryWithStory(parts)
                .subscribeOn(Schedulers.io())
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<PublishBean>>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse<PublishBean> response) {
                        showToast("作品发布成功，为作品添加一个章节吧！");
                        Gson gson = new Gson();
                        String s = gson.toJson(response);
                        LogUtils.e(s);
                        WriteActivity.start(CreateStoryActivity.this, response.getResult().getWrite_id(), "000", "1", WriteActivity.PUBLISH);
                        finish();
                    }
                });
    }

    private void saveDraft() {
        name = mEtName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            showToast("标题不能为空");
            return;
        }
    }


    /**
     * @param context      跳转起始页面
     * @param baseActivity 跳转目的页面
     */
    public static void start(Context context, Class<? extends BaseActivity> baseActivity) {
        Intent intent = new Intent(context, baseActivity);
        context.startActivity(intent);
    }
}
