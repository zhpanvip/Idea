package com.cypoem.idea.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airong.core.utils.ImageLoaderUtil;
import com.airong.core.utils.LogUtils;
import com.cypoem.idea.R;
import com.cypoem.idea.adapter.CircleListAdapter;
import com.cypoem.idea.constants.Constants;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.CircleListBean;
import com.cypoem.idea.module.bean.CreateCircleResponse;
import com.cypoem.idea.module.bean.UserBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.net.IdeaApiService;
import com.cypoem.idea.utils.UserInfoTools;
import com.yalantis.ucrop.UCrop;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.cypoem.idea.constants.Constants.REQUEST_SELECT_PICTURE;
import static com.cypoem.idea.constants.Constants.TAG;

public class CreateCircleActivity extends BaseActivity {

    @BindView(R.id.btn_save)
    TextView mBtnSave;
    @BindView(R.id.iv_circle_icon)
    ImageView mIvCircleIcon;
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
    private String picPath = "";
    private String circleDescription = "";
    private String category = "";

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
                .addFormDataPart("category", category)
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

    @OnClick({R.id.btn_save, R.id.ll_add_pic, R.id.rl_label, R.id.tv_public, R.id.tv_private})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                createCircle();
                break;
            case R.id.ll_add_pic:
                pickFromGallery();
                break;
            case R.id.rl_label:
                break;
            case R.id.tv_public:
                break;
            case R.id.tv_private:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case UCrop.RESULT_ERROR:
                handleCropError(data);
                break;
            case RESULT_OK://
                selectPicResult(requestCode, data);
                break;
        }
    }

    //  选择图片结果处理
    private void selectPicResult(int requestCode, Intent data) {
        if (requestCode == REQUEST_SELECT_PICTURE) {
            final Uri selectedUri = data.getData();
            if (selectedUri != null) {
                startCropActivity(data.getData());
            } else {
                Toast.makeText(CreateCircleActivity.this, R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == UCrop.REQUEST_CROP) {
            handleCropResult(data);
        }
    }

    /**
     * 剪切成功
     *
     * @param result
     */
    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            LogUtils.e(resultUri.getPath());
            picPath = resultUri.getPath();
            ImageLoaderUtil.loadCircleImg(mIvCircleIcon, picPath, R.drawable.login_photo);
            //  向服务器提交头像数据
            //  postHeadPic();
        } else {
            showToast(R.string.toast_cannot_retrieve_cropped_image);
        }
    }


    private void startCropActivity(@NonNull Uri uri) {
        String destinationFileName = System.currentTimeMillis() + "";
        destinationFileName += ".png";
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));
        uCrop = uCrop.withAspectRatio(1, 1);
        uCrop = advancedConfig(uCrop);
        uCrop.start(CreateCircleActivity.this);
    }

    /**
     * Sometimes you want to adjust more options, it's done via {@link com.yalantis.ucrop.UCrop.Options} class.
     *
     * @param uCrop - ucrop builder instance
     * @return - ucrop builder instance
     */
    private UCrop advancedConfig(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);

        options.setCompressionQuality(40);

        options.setHideBottomControls(true);
        options.setFreeStyleCropEnabled(false);
        return uCrop.withOptions(options);
    }

    //  处理选择图片错误的情况
    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Log.e(TAG, "handleCropError: ", cropError);
            showToast(cropError.getMessage());
        } else {
            showToast(R.string.toast_unexpected_error);
        }
    }

    private void pickFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.permission_read_storage_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, getString(R.string.label_select_picture)), REQUEST_SELECT_PICTURE);
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
