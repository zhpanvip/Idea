package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.cypoem.idea.constants.Constants;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.CircleListBean;
import com.cypoem.idea.module.bean.CreateCircleResponse;
import com.cypoem.idea.module.bean.UserBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.utils.UserInfoTools;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CreateCircleActivity extends BaseActivity {

    @BindView(R.id.btn_save)
    TextView mBtnSave;
    @BindView(R.id.iv_circle_icon)
    ImageView mIvCircleIcon;
    @BindView(R.id.ll_update_icon)
    LinearLayout mLlUpdateIcon;
    @BindView(R.id.rv_icon)
    RecyclerView mRvIcon;
    @BindView(R.id.et_circle_name)
    EditText mEtCircleName;
    @BindView(R.id.rl_label)
    RelativeLayout mRlLabel;
    @BindView(R.id.tv_public)
    TextView mTvPublic;
    @BindView(R.id.tv_private)
    TextView mTvPrivate;
    @BindView(R.id.rl_type)
    RelativeLayout mRlType;
    @BindView(R.id.et_introduce)
    EditText mEtIntroduce;
    private String picPath="";
    private java.lang.String circleDescription;
    private java.lang.String category;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_circle;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }


    private void createCircle() {
        String circleName = mEtCircleName.getText().toString().trim();
        String introduce = mEtIntroduce.getText().toString().trim();
        if (TextUtils.isEmpty(picPath)) {
            showToast(R.string.select_head_pic);
            return;
        }
        if (TextUtils.isEmpty(circleName)) {
            showToast("请输入圈子名称");
            return;
        }
        File file = new File(picPath);
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("introduction", introduce)
                .addFormDataPart("name", circleName)
                .addFormDataPart("type", "2")
                .addFormDataPart("style", "1")
                .addFormDataPart("circleDescription", circleDescription)
                .addFormDataPart("category",category)
                .addFormDataPart("uploadFile", file.getName(), imageBody);
        List<MultipartBody.Part> parts = builder.build().parts();

        IdeaApi.getApiService()
                .createCircle(parts)
                .subscribeOn(Schedulers.io())
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<CreateCircleResponse>>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse<CreateCircleResponse> response) {
                        createCircleSuccess(response.getResult());
                    }
                });
    }

    private void createCircleSuccess(CreateCircleResponse result) {
        showToast("创建成功");
    }


    public static void start(Context context) {
        Intent intent = new Intent(context, CreateCircleActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.btn_save, R.id.ll_update_icon, R.id.rl_label, R.id.tv_public, R.id.tv_private})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                createCircle();
                break;
            case R.id.ll_update_icon:
                break;
            case R.id.rl_label:
                break;
            case R.id.tv_public:
                break;
            case R.id.tv_private:
                break;
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
