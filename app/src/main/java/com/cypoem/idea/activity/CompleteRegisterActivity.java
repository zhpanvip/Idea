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
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.airong.core.utils.LogUtils;
import com.cypoem.idea.R;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.UserBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.utils.UserInfoTools;
import com.cypoem.idea.view.SexView;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.callback.BitmapLoadCallback;
import com.yalantis.ucrop.model.ExifInfo;
import com.yalantis.ucrop.util.BitmapLoadUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.cypoem.idea.constants.Constants.REQUEST_SELECT_PICTURE;
import static com.cypoem.idea.constants.Constants.SAMPLE_CROPPED_IMAGE_NAME;
import static com.cypoem.idea.constants.Constants.TAG;

public class CompleteRegisterActivity extends BaseActivity {
    @BindView(R.id.et_pen_name)
    EditText mEtPenName;
    @BindView(R.id.et_sign)
    EditText mEtSign;
    @BindView(R.id.seek_bar)
    SeekBar mSeekBar;
    @BindView(R.id.sex_view)
    SexView mSexView;
    @BindView(R.id.btn_complete)
    Button mBtnComplete;
    @BindView(R.id.iv_head_pic)
    CircleImageView ivHeadPic;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    private String phone;
    String password;
    private int mMaxBitmapSize = 0;
    private String picPath = "";
    private String sex = "0.5";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_complete_regist;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setListener();
        Intent intent = getIntent();
        phone = intent.getStringExtra("phone");
        password = intent.getStringExtra("password");
    }

    private void setListener() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                sex = (progress) / 100.0 + "";
                mSexView.setMalePercent(progress / 100.0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public static void start(Context context, String phone, String password) {
        Intent intent = new Intent(context, CompleteRegisterActivity.class);
        intent.putExtra("phone", phone);
        intent.putExtra("password", password);
        context.startActivity(intent);
    }


    @OnClick({R.id.iv_head_pic, R.id.btn_complete, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_head_pic:
                pickFromGallery();
                break;
            case R.id.btn_complete:
                completeRegister();
                break;
            case R.id.iv_back:
                onBackPress();
                break;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECT_PICTURE) {
                final Uri selectedUri = data.getData();
                if (selectedUri != null) {
                    showToast(selectedUri.getPath());
                    startCropActivity(data.getData());
                } else {
                    Toast.makeText(CompleteRegisterActivity.this, R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {
                handleCropResult(data);
            }
        }
        if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }
    }

    private void startCropActivity(@NonNull Uri uri) {
        String destinationFileName = SAMPLE_CROPPED_IMAGE_NAME;
        destinationFileName += ".png";
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));
        uCrop = basisConfig(uCrop);
        uCrop = advancedConfig(uCrop);
        uCrop.start(CompleteRegisterActivity.this);
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

    private UCrop basisConfig(@NonNull UCrop uCrop) {
        uCrop = uCrop.withAspectRatio(1, 1);
        return uCrop;
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
            // ImageLoaderUtil.loadCircleImg(ivHeadPic,resultUri.getPath()+".jpg",R.drawable.camera);
            BitmapLoadUtils.decodeBitmapInBackground(this, resultUri, null, maxBitmapSize, maxBitmapSize, new BitmapLoadCallback() {
                @Override
                public void onBitmapLoaded(@NonNull Bitmap bitmap, @NonNull ExifInfo exifInfo, @NonNull String s, @Nullable String s1) {
                    ivHeadPic.setImageBitmap(bitmap);
                }

                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
            // ResultActivity.startWithUri(CompleteRegisterActivity.this, resultUri);
        } else {
            Toast.makeText(CompleteRegisterActivity.this, R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show();
        }
    }

    public int getMaxBitmapSize() {
        if (mMaxBitmapSize <= 0) {
            mMaxBitmapSize = BitmapLoadUtils.calculateMaxBitmapSize(this);
        }
        return mMaxBitmapSize;
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Log.e(TAG, "handleCropError: ", cropError);
            Toast.makeText(CompleteRegisterActivity.this, cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(CompleteRegisterActivity.this, R.string.toast_unexpected_error, Toast.LENGTH_SHORT).show();
        }
    }

    private void completeRegister() {
        String penName = mEtPenName.getText().toString().trim();
        String sign = mEtSign.getText().toString().trim();
        if (TextUtils.isEmpty(picPath)) {
            showToast(R.string.select_head_pic);
            return;
        }
        if (TextUtils.isEmpty(penName)) {
            showToast(R.string.input_pen_name);
            return;
        }
        File file = new File(picPath);
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("dictum", sign)
                .addFormDataPart("pen_name", penName)
                .addFormDataPart("sex", sex)
                .addFormDataPart("phone", phone)
                .addFormDataPart("password", password)
                .addFormDataPart("uploadFile", file.getName(), imageBody);
        List<MultipartBody.Part> parts = builder.build().parts();
        /*//  图片参数
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploadFile", file.getName(), requestFile);
        //  手机号参数
        RequestBody phoneBody = RequestBody.create(MediaType.parse("multipart/form-data"), phone);
        //  密码参数
        RequestBody pswBody = RequestBody.create(MediaType.parse("multipart/form-data"), password);*/

        IdeaApi.getApiService()
                .register(parts)
                .subscribeOn(Schedulers.io())
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<UserBean>>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse<UserBean> response) {
                        EventBus.getDefault().post(new RegisterSuccess("register success"));
                        UserInfoTools.setIsLogin(getApplication(), true);
                        UserInfoTools.setUser(getApplicationContext(), response.getResult());
                        showToast("注册成功，请登陆");
                        finish();
                    }
                });
    }

    public class RegisterSuccess {
        public String msg;

        public RegisterSuccess(String msg) {
            this.msg = msg;
        }
    }
}
