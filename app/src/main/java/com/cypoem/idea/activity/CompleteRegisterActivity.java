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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.airong.core.utils.LogUtils;
import com.cypoem.idea.R;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.RegisterBean;
import com.cypoem.idea.module.post_bean.RegisterPost;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.view.SexView;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.callback.BitmapLoadCallback;
import com.yalantis.ucrop.model.ExifInfo;
import com.yalantis.ucrop.util.BitmapLoadUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CompleteRegisterActivity extends BaseActivity {
    private static final int REQUEST_SELECT_PICTURE = 0x01;
    private static final String TAG = "CompleteRegister";
    private static final String SAMPLE_CROPPED_IMAGE_NAME = "CropImage";
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
    private String phone;
    String password;
    private int mMaxBitmapSize = 0;
    private String picPath="";

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
                mSexView.setMalePercent(1 - progress / 100.0);
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


    @OnClick({R.id.iv_head_pic, R.id.btn_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_head_pic:
                pickFromGallery();
                break;
            case R.id.btn_complete:
                completeRegister();
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
        destinationFileName += ".jpg";
      /*  switch (mRadioGroupCompressionSettings.getCheckedRadioButtonId()) {
            case R.id.radio_png:
                destinationFileName += ".png";
                break;
            case R.id.radio_jpeg:
                destinationFileName += ".jpg";
                break;
        }*/

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
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        /*switch (mRadioGroupCompressionSettings.getCheckedRadioButtonId()) {
            case R.id.radio_png:
                options.setCompressionFormat(Bitmap.CompressFormat.PNG);
                break;
            case R.id.radio_jpeg:
            default:
                options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
                break;
        }*/
        options.setCompressionQuality(40);

        options.setHideBottomControls(true);
        options.setFreeStyleCropEnabled(false);

        /*
        If you want to configure how gestures work for all UCropActivity tabs

        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        * */

        /*
        This sets max size for bitmap that will be decoded from source Uri.
        More size - more memory allocation, default implementation uses screen diagonal.

        options.setMaxBitmapSize(640);
        * */


       /*

        Tune everything (ﾉ◕ヮ◕)ﾉ*:･ﾟ✧

        options.setMaxScaleMultiplier(5);
        options.setImageToCropBoundsAnimDuration(666);
        options.setDimmedLayerColor(Color.CYAN);
        options.setCircleDimmedLayer(true);
        options.setShowCropFrame(false);
        options.setCropGridStrokeWidth(20);
        options.setCropGridColor(Color.GREEN);
        options.setCropGridColumnCount(2);
        options.setCropGridRowCount(1);
        options.setToolbarCropDrawable(R.drawable.your_crop_icon);
        options.setToolbarCancelDrawable(R.drawable.your_cancel_icon);

        // Color palette
        options.setToolbarColor(ContextCompat.getColor(this, R.color.your_color_res));
        options.setStatusBarColor(ContextCompat.getColor(this, R.color.your_color_res));
        options.setActiveWidgetColor(ContextCompat.getColor(this, R.color.your_color_res));
        options.setToolbarWidgetColor(ContextCompat.getColor(this, R.color.your_color_res));
        options.setRootViewBackgroundColor(ContextCompat.getColor(this, R.color.your_color_res));

        // Aspect ratio options
        options.setAspectRatioOptions(1,
            new AspectRatio("WOW", 1, 2),
            new AspectRatio("MUCH", 3, 4),
            new AspectRatio("RATIO", CropImageView.DEFAULT_ASPECT_RATIO, CropImageView.DEFAULT_ASPECT_RATIO),
            new AspectRatio("SO", 16, 9),
            new AspectRatio("ASPECT", 1, 1));

       */

        return uCrop.withOptions(options);
    }


    private UCrop basisConfig(@NonNull UCrop uCrop) {
        uCrop = uCrop.withAspectRatio(1, 1);
        /*switch (mRadioGroupAspectRatio.getCheckedRadioButtonId()) {
            case R.id.radio_origin:
                uCrop = uCrop.useSourceImageAspectRatio();
                break;
            case R.id.radio_square:
                uCrop = uCrop.withAspectRatio(1, 1);
                break;
            case R.id.radio_dynamic:
                // do nothing
                break;
            default:
                try {
                    float ratioX = Float.valueOf(mEditTextRatioX.getText().toString().trim());
                    float ratioY = Float.valueOf(mEditTextRatioY.getText().toString().trim());
                    if (ratioX > 0 && ratioY > 0) {
                        uCrop = uCrop.withAspectRatio(ratioX, ratioY);
                    }
                } catch (NumberFormatException e) {
                    Log.i(TAG, String.format("Number please: %s", e.getMessage()));
                }
                break;
        }*/

       /* if (mCheckBoxMaxSize.isChecked()) {
            try {
                int maxWidth = Integer.valueOf(mEditTextMaxWidth.getText().toString().trim());
                int maxHeight = Integer.valueOf(mEditTextMaxHeight.getText().toString().trim());
                if (maxWidth > 0 && maxHeight > 0) {
                    uCrop = uCrop.withMaxResultSize(maxWidth, maxHeight);
                }
            } catch (NumberFormatException e) {
                Log.e(TAG, "Number please", e);
            }
        }*/

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

            showToast(resultUri.getPath());
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
        File file = new File(picPath);
         MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        builder.addFormDataPart("uploadFile", file.getName(), imageBody);
        builder.addFormDataPart("phone", phone)
                .addFormDataPart("password", password);
        List<MultipartBody.Part> parts = builder.build().parts();

       /*RequestBody requestFile=RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part filePart=MultipartBody.Part.createFormData("uploadFile",file.getName(),requestFile);
        RequestBody phoneBody=RequestBody.create(MediaType.parse("multipart/form-data"),phone);
        RequestBody passwordBody=RequestBody.create(MediaType.parse("multipart/form-data"),password);
        HashMap<String,RequestBody> map=new HashMap<>();
        map.put("phone",phoneBody);
        map.put("password",passwordBody);*/
        IdeaApi.getApiService()
                .register(parts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<RegisterBean>>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse<RegisterBean> response) {
                        Toast.makeText(CompleteRegisterActivity.this, "请求数据成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}
