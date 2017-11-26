package com.cypoem.idea.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airong.core.dialog.DialogUtils;
import com.airong.core.utils.ImageUtils;
import com.airong.core.utils.LogUtils;
import com.cypoem.idea.R;
import com.cypoem.idea.constants.Constants;
import com.cypoem.idea.event.PublishEverydaySuccess;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.OpusBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.utils.UserInfoTools;
import com.cypoem.idea.view.MaxByteLengthEditText;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.callback.BitmapLoadCallback;
import com.yalantis.ucrop.model.ExifInfo;
import com.yalantis.ucrop.util.BitmapLoadUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrFrameLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.cypoem.idea.constants.Constants.REQUEST_SELECT_PICTURE;
import static com.cypoem.idea.constants.Constants.SAMPLE_CROPPED_IMAGE_NAME;
import static com.cypoem.idea.constants.Constants.TAG;

public class CreateEveryDayActivity extends BaseActivity {

    @BindView(R.id.iv_add_pic)
    ImageView mIvAddPic;
    @BindView(R.id.et_sentence1)
    MaxByteLengthEditText mEtSentence1;
    @BindView(R.id.et_pen_name)
    MaxByteLengthEditText mEtPenName;
    @BindView(R.id.tv_preview)
    TextView mTvPreview;
    @BindView(R.id.tv_publish)
    TextView mTvPublish;
    @BindView(R.id.ll_pic)
    LinearLayout mLlPic;
    private java.lang.String content;
    private java.lang.String penName;
    private String picPath;
    private int mMaxBitmapSize;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_every_day;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        getSubTitle().setVisibility(View.VISIBLE);
        getSubTitle().setText("往期回看");
        setToolBarTitle("创建每日一句");
        getSubTitle().setOnClickListener((View v) -> {
            EverydayLookBackActivity.start(CreateEveryDayActivity.this);
        });
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void publishSuccess(PublishEverydaySuccess event) {
        finish();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, CreateEveryDayActivity.class);
        context.startActivity(intent);
    }


    @OnClick({R.id.iv_add_pic, R.id.tv_preview, R.id.tv_publish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_add_pic:
                pickFromGallery();
                break;
            case R.id.tv_preview:
                preview();
                break;
            case R.id.tv_publish:
                publish();
                break;
        }
    }

    private void preview() {
        if (!checkIsEmpty()) {
            PreviewActivity.start(this, picPath, penName, content);
        }
    }

    private boolean checkIsEmpty() {
        content = mEtSentence1.getText().toString().trim();
        if (TextUtils.isEmpty(picPath)) {
            showToast("请选择一张图片");
            return true;
        }
        if (TextUtils.isEmpty(content)) {
            showToast("请输入一句内容");
            return true;
        }
        penName = mEtPenName.getText().toString().trim();
        if (TextUtils.isEmpty(penName)) {
            showToast("请输入笔名");
            return true;
        }
        return false;
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_PICTURE) {
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    showToast(selectedUri.getPath());
                    startCropActivity(data.getData());
                } else {
                    Toast.makeText(CreateEveryDayActivity.this, R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            }
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }
    }

    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Log.e(TAG, "handleCropError: ", cropError);
            Toast.makeText(CreateEveryDayActivity.this, cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(CreateEveryDayActivity.this, R.string.toast_unexpected_error, Toast.LENGTH_SHORT).show();
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
            int maxBitmapSize = getMaxBitmapSize();
            BitmapLoadUtils.decodeBitmapInBackground(this, resultUri, null, maxBitmapSize, maxBitmapSize, new BitmapLoadCallback() {
                @Override
                public void onBitmapLoaded(@NonNull Bitmap bitmap, @NonNull ExifInfo exifInfo, @NonNull String s, @Nullable String s1) {
                    mLlPic.setBackground(new BitmapDrawable(bitmap));
                }

                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } else {
            Toast.makeText(CreateEveryDayActivity.this, R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show();
        }
    }

    public int getMaxBitmapSize() {
        if (mMaxBitmapSize <= 0) {
            mMaxBitmapSize = BitmapLoadUtils.calculateMaxBitmapSize(this);
        }
        return mMaxBitmapSize;
    }

    private void startCropActivity(@NonNull Uri uri) {
        String destinationFileName = SAMPLE_CROPPED_IMAGE_NAME;
        destinationFileName += ".png";
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));
        uCrop = uCrop.withAspectRatio(2, 3);
        uCrop = advancedConfig(uCrop);
        uCrop.start(CreateEveryDayActivity.this);
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

    private void publish() {
        if (checkIsEmpty()) {
            return;
        }
        File file = new File(picPath);
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("user_id", UserInfoTools.getUserId(this))
                .addFormDataPart("content", content)
                .addFormDataPart("pen_name", penName)
                .addFormDataPart("uploadFile", "head_pic", imageBody);
        List<MultipartBody.Part> parts = builder.build().parts();
        postData(parts);
    }

    private void postData(List<MultipartBody.Part> parts) {
        IdeaApi.getApiService()
                .publishEverydaySay(parts)
                .subscribeOn(Schedulers.io())
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                        showToast(response.getMsg());
                        MainActivity.start(CreateEveryDayActivity.this);
                        finish();
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (!isCanBack()) {
                showConfirmDialog();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean isCanBack() {
        return TextUtils.isEmpty(picPath)
                && TextUtils.isEmpty(mEtPenName.getText().toString().trim())
                && TextUtils.isEmpty(mEtSentence1.getText().toString().trim());
    }

    private void showConfirmDialog() {
        DialogUtils dialogUtils = new DialogUtils(this);
        dialogUtils.showTwoButtonDialog("您还没有发布，确定要退出吗？", (View v) -> {
            // MainActivity.start(CreateEveryDayActivity.this);
            finish();
        }, (View v) -> dialogUtils.dismissDialog());
    }

    @Override
    protected void onBackPress() {
        if (!isCanBack())
            showConfirmDialog();
        else finish();
    }

}
