package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.utils.UserInfoTools;
import com.yalantis.ucrop.callback.BitmapLoadCallback;
import com.yalantis.ucrop.model.ExifInfo;
import com.yalantis.ucrop.util.BitmapLoadUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.net.Uri;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 预览
 */
public class PreviewActivity extends BaseActivity {

    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.ll_splash)
    LinearLayout llSplash;
    @BindView(R.id.tv_share)
    TextView tvShare;
    @BindView(R.id.btn_publish)
    TextView tvPublish;
    @BindView(R.id.tv_time)
    TextView tvTime;
    private String picPath;
    private int mMaxBitmapSize;
    private String penName;
    private String content;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preview;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        picPath = intent.getStringExtra("picPath");
        penName = intent.getStringExtra("penName");
        content = intent.getStringExtra("content");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String date = df.format(new Date());
        tvTime.setText(penName + "/" + date);
        tvContent.setText(content);
        setPic();
    }

    private void setPic() {
        int maxBitmapSize = getMaxBitmapSize();
        Uri uri = Uri.fromFile(new File(picPath));
        showProgress(this);
        BitmapLoadUtils.decodeBitmapInBackground(this, uri, null, maxBitmapSize, maxBitmapSize, new BitmapLoadCallback() {
            @Override
            public void onBitmapLoaded(@NonNull Bitmap bitmap, @NonNull ExifInfo exifInfo, @NonNull String s, @Nullable String s1) {
                llSplash.setBackground(new BitmapDrawable(bitmap));
                ivPic.setImageBitmap(bitmap);
                dismissProgress();
            }

            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public int getMaxBitmapSize() {
        if (mMaxBitmapSize <= 0) {
            mMaxBitmapSize = BitmapLoadUtils.calculateMaxBitmapSize(this);
        }
        return mMaxBitmapSize;
    }

    public static void start(Context context, String picPath, String penName, String content) {
        Intent intent = new Intent(context, PreviewActivity.class);
        intent.putExtra("picPath", picPath);
        intent.putExtra("penName", penName);
        intent.putExtra("content", content);
        context.startActivity(intent);
    }

    @OnClick({R.id.btn_publish, R.id.tv_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_publish:
                publish();
                break;
            case R.id.tv_share:

                break;
        }
    }

    private void publish() {
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                        showToast(response.getMsg());
                        MainActivity.start(PreviewActivity.this);
                        finish();
                    }
                });
    }


}
