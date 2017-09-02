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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.airong.core.utils.ImageUtils;
import com.airong.core.utils.LogUtils;
import com.airong.core.utils.ToastUtils;
import com.cypoem.idea.R;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.OpusBean;
import com.cypoem.idea.module.bean.PublishBean;
import com.cypoem.idea.module.bean.RegisterBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.utils.UserInfoTools;
import com.google.gson.Gson;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.callback.BitmapLoadCallback;
import com.yalantis.ucrop.model.ExifInfo;
import com.yalantis.ucrop.util.BitmapLoadUtils;

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

import static com.cypoem.idea.constants.Constants.REQUEST_SELECT_PICTURE;
import static com.cypoem.idea.constants.Constants.SAMPLE_CROPPED_IMAGE_NAME;
import static com.cypoem.idea.constants.Constants.TAG;

public class PublishActivity extends BaseActivity {

    public static final int SELECT_LABEL = 256;
    public static final int ADD_DESCRIBE = 257;
    @BindView(R.id.iv_add_pic)
    ImageView ivAddPic;
    @BindView(R.id.et_opus_name)
    EditText etOpusName;
    @BindView(R.id.rl_label)
    RelativeLayout rlLabel;
    @BindView(R.id.rl_brief)
    RelativeLayout rlBrief;
    @BindView(R.id.tb_override)
    ToggleButton tbOverride;
    @BindView(R.id.tb_continue)
    ToggleButton tbContinue;
    @BindView(R.id.btn_complete)
    Button btnComplete;
    @BindView(R.id.activity_publish)
    ScrollView activityPublish;
    @BindView(R.id.ll_add_pic)
    LinearLayout llAddPic;
    @BindView(R.id.tv_describe)
    TextView mTvDescribe;
    @BindView(R.id.ll_label)
    LinearLayout mLlLabel;
    private String picPath;
    private int mMaxBitmapSize = 0;
    private String opusName;
    private String isCanOverride = "1";
    private String isCanRenew = "1";
    private String[] positions;
    private String label = "";
    private String describe = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_publish;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setToolBarTitle("创作作品");
        setListener();
        positions = new String[0];
    }

    private void setListener() {
        tbContinue.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            if (isChecked) {
                isCanRenew = "1";
            } else {
                isCanRenew = "0";
            }
        });
        tbOverride.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            if (isChecked) {
                isCanOverride = "1";
            } else {
                isCanOverride = "0";
            }
        });
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, PublishActivity.class);
        context.startActivity(intent);
    }


    @OnClick({R.id.iv_add_pic, R.id.rl_label, R.id.rl_brief, R.id.btn_complete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_add_pic:
                pickFromGallery();
                break;
            case R.id.rl_label:
                selectLabel();
                break;
            case R.id.rl_brief:
                addDescribe();
                break;
            case R.id.btn_complete:
                submit();
                // WriteActivity.start(this);
                break;
        }
    }

    // 添加作品描述
    private void addDescribe() {
        Intent intent = new Intent(this, DescribeActivity.class);
        describe = mTvDescribe.getText().toString();
        intent.putExtra("describe", describe);
        startActivityForResult(intent, ADD_DESCRIBE);
    }

    private void selectLabel() {
        Intent intent = new Intent(this, AddLabelActivity.class);
        intent.putExtra("positions", positions);
        startActivityForResult(intent, SELECT_LABEL);
    }

    private void submit() {
        if (TextUtils.isEmpty(picPath)) {
            showToast("为作品添加一张图片吧");
            return;
        }
        opusName = etOpusName.getText().toString().trim();
        if (TextUtils.isEmpty(opusName)) {
            showToast("作品还没有名字呢");
            return;
        }
        File file = new File(picPath);
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("introduction", describe)
                .addFormDataPart("write_name", opusName)
                .addFormDataPart("reStatus", isCanOverride)
                .addFormDataPart("upStatus", isCanRenew)
                .addFormDataPart("type", label)
                .addFormDataPart("user_id", UserInfoTools.getUserId(this))
                .addFormDataPart("uploadFile", file.getName(), imageBody);
        List<MultipartBody.Part> parts = builder.build().parts();
        IdeaApi.getApiService()
                .publishOpus(parts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<PublishBean>>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse<PublishBean> response) {
                        showToast("作品发布成功，为作品添加一个章节吧！");
                        Gson gson=new Gson();
                        String s = gson.toJson(response);
                        LogUtils.e(s);
                        WriteActivity.start(PublishActivity.this,response.getResult().getWrite_id(),"000","1",WriteActivity.PUBLISH);
                        finish();
                    }
                });
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
        switch (resultCode) {
            case RESULT_OK:
                clipSuccess(requestCode, data);
                break;
            case UCrop.RESULT_ERROR:
                handleCropError(data);
                break;
            case SELECT_LABEL:
                setLabel(data);
                break;
            case ADD_DESCRIBE:
                setDescribe(data);
                break;
        }
    }

    private void clipSuccess(int requestCode, Intent data) {
        if (requestCode == REQUEST_SELECT_PICTURE) {
            final Uri selectedUri = data.getData();
            if (selectedUri != null) {
                showToast(selectedUri.getPath());
                startCropActivity(data.getData());
            } else {
                showToast(R.string.toast_cannot_retrieve_selected_image);
            }
        } else if (requestCode == UCrop.REQUEST_CROP) {
            handleCropResult(data);
        }
    }

    private void setDescribe(Intent data) {
        describe = data.getStringExtra("describe");
        mTvDescribe.setText(describe);
    }

    private void setLabel(Intent data) {
        label = data.getStringExtra("label");
        if (TextUtils.isEmpty(label)) {
            mLlLabel.removeAllViews();
            label="";
            return;
        }
        String[] labels = label.split("-");
        positions = data.getStringExtra("positions").split("-");
        mLlLabel.removeAllViews();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 10, 0);
        for (int i = 0; i < labels.length; i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_label_normal, mLlLabel, false);
            view.setLayoutParams(params);
            TextView tvLabel = (TextView) view.findViewById(R.id.tv_label);
            tvLabel.setText(labels[i]);
            mLlLabel.addView(view);
        }
    }

    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Log.e(TAG, "handleCropError: ", cropError);
            showToast(cropError.getMessage());
        } else {
            showToast(R.string.toast_unexpected_error);
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

            showToast(resultUri.getPath());
            LogUtils.e(resultUri.getPath());
            picPath = resultUri.getPath();

            int maxBitmapSize = getMaxBitmapSize();
            // ImageLoaderUtil.loadCircleImg(ivHeadPic,resultUri.getPath()+".jpg",R.drawable.camera);
            BitmapLoadUtils.decodeBitmapInBackground(this, resultUri, null, maxBitmapSize, maxBitmapSize, new BitmapLoadCallback() {
                @Override
                public void onBitmapLoaded(@NonNull Bitmap bitmap, @NonNull ExifInfo exifInfo, @NonNull String s, @Nullable String s1) {
                    llAddPic.setBackground(new BitmapDrawable(bitmap));
                }

                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } else {
            Toast.makeText(PublishActivity.this, R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show();
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

        //uCrop = basisConfig(uCrop);
        uCrop = uCrop.withAspectRatio(16, 9);
        uCrop = advancedConfig(uCrop);
        uCrop.start(PublishActivity.this);
    }

    private UCrop advancedConfig(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        options.setCompressionQuality(80);
        options.setHideBottomControls(true);
        options.setFreeStyleCropEnabled(false);

        return uCrop.withOptions(options);
    }
}
