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
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airong.core.utils.ImageLoaderUtil;
import com.airong.core.utils.LogUtils;
import com.airong.core.utils.ToastUtils;
import com.cypoem.idea.R;
import com.cypoem.idea.adapter.CommonFragmentAdapter;
import com.cypoem.idea.constants.Constants;
import com.cypoem.idea.fragment.AuthorFragment;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.UserBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.net.IdeaApiService;
import com.cypoem.idea.utils.UserInfoTools;
import com.cypoem.idea.view.ScrollableLayout;
import com.cypoem.idea.view.SexView;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.util.BitmapLoadUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.cypoem.idea.constants.Constants.REQUEST_SELECT_PICTURE;
import static com.cypoem.idea.constants.Constants.SAMPLE_CROPPED_IMAGE_NAME;
import static com.cypoem.idea.constants.Constants.TAG;

public class AuthorInfoActivity extends BaseActivity {

    @BindView(R.id.iv_author)
    ImageView mIvAuthor;
    @BindView(R.id.tv_pen_name)
    TextView mTvPenName;
    @BindView(R.id.sex_view)
    SexView mSexView;
    @BindView(R.id.tv_sign)
    TextView mTvSign;
    @BindView(R.id.tv_birthday)
    TextView mTvBirthday;
    @BindView(R.id.tv_address)
    TextView mTvAddress;
    @BindView(R.id.tv_focus)
    TextView mTvFocus;
    @BindView(R.id.tv_follow)
    TextView mTvFollow;
    @BindView(R.id.tv_collect)
    TextView mTvCollect;
    @BindView(R.id.tv_like)
    TextView mTvLike;
    @BindView(R.id.tv_fans)
    TextView mTvFans;
    @BindView(R.id.tv_introduce)
    TextView mTvIntroduce;
    @BindView(R.id.iv_edit)
    ImageView mIvEdit;
    @BindView(R.id.ll_focus)
    LinearLayout llFocus;
    @BindView(R.id.ll_collect)
    LinearLayout llCollect;
    @BindView(R.id.ll_like)
    LinearLayout llLike;
    @BindView(R.id.ll_fans)
    LinearLayout llFans;
    @BindView(R.id.vp_author)
    ViewPager mViewPager;
    private CommonFragmentAdapter mAdapter;
    @BindView(R.id.tl_author)
    TabLayout mTabLayout;
    @BindView(R.id.sl_view)
    ScrollableLayout mScrollView;
    List<AuthorFragment> mList;
    private String userId = "";
    private boolean isFollow;
    private UserBean userBean;
    private String picPath;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_author_info;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
        setData();
        setListener();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 修改个人资料成功的消息
     */
    @Subscribe
    public void updateInfoSuccess(EditInfoActivity.UpdateInfoSuccess success) {
        if (UserInfoTools.getUserId(this).equals(userId)) {
            UserBean user = UserInfoTools.getUser(this);
            setUserData(user);
        }
    }

    private void setData() {
        UserBean user = UserInfoTools.getUser(this);
        if (null != user && userId.equals(UserInfoTools.getUserId(this))) {
            setUserData(user);
            setToolBarTitle("我的资料");
        } else {
            mIvEdit.setVisibility(View.GONE);
            getData(false);
            setToolBarTitle("作者简介");
        }
    }

    private void setUserData(UserBean user) {
        ImageLoaderUtil.loadImg(mIvAuthor, IdeaApiService.HOST + user.getCover_photo(), R.drawable.head_pic);
        mTvPenName.setText(user.getPen_name());
        String sex = user.getSex();
        mSexView.setMalePercent(Double.parseDouble(sex));
        mTvSign.setText(user.getDictum());
        mTvBirthday.setText(user.getBirthday());
        mTvAddress.setText(user.getAddress());
        mTvIntroduce.setText(user.getIntroduction());
        mTvFans.setText(String.valueOf(user.getWatchMeCount()));
        mTvFocus.setText(String.valueOf(user.getMyWatchCount()));
        mTvCollect.setText(String.valueOf(user.getKeep_count()));
        mTvLike.setText(String.valueOf(user.getEnjoy_count()));

    }

    private void setListener() {
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mScrollView.getHelper().setCurrentScrollableContainer(mList.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void getData(boolean showLoading) {
        IdeaApi.getApiService()
                .getUserInfo(userId, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<UserBean>>(this, showLoading) {
                    @Override
                    public void onSuccess(BasicResponse<UserBean> response) {
                        userBean = response.getResult();
                        mTvFollow.setVisibility(View.VISIBLE);
                        if (userBean.getWatch_status() == 0) {
                            mTvFollow.setText("关注");
                        } else {
                            mTvFollow.setText("已关注");
                        }
                        setUserData(userBean);
                    }
                });
    }

    private void initData() {
        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        mList = new ArrayList<>();
        /*Bundle bundle = new Bundle();
        bundle.putString("userId",userId);*/
        AuthorFragment fragmentStart = AuthorFragment.getFragment(Constants.MY_START_OPUS, userId);
        AuthorFragment fragmentJoin = AuthorFragment.getFragment(Constants.MY_JOIN_OPUS, userId);
        AuthorFragment fragmentCreate = AuthorFragment.getFragment(Constants.MY_OWN_OPUS, userId);
        mList.add(fragmentStart);
        mList.add(fragmentJoin);
        mList.add(fragmentCreate);
        mAdapter = new CommonFragmentAdapter(getSupportFragmentManager(), this);
        mAdapter.setFragmentList(mList);
        mViewPager.setAdapter(mAdapter);
        mScrollView.getHelper().setCurrentScrollableContainer(mList.get(0));
    }

    public static void start(Context context, String userId) {
        Intent intent = new Intent(context, AuthorInfoActivity.class);
        intent.putExtra("userId", userId);
        context.startActivity(intent);
    }


    @OnClick({R.id.ll_collect, R.id.ll_like, R.id.ll_fans, R.id.ll_focus,
            R.id.iv_edit, R.id.tv_follow, R.id.iv_author})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_collect:
                CollectActivity.start(this);
                break;
            case R.id.ll_like:
                // PraiseActivity.start(this);
                break;
            case R.id.ll_fans:
                FansActivity.start(this, Constants.FOLLOWS, userId);
                break;
            case R.id.ll_focus:
                FansActivity.start(this, Constants.FOCUS, userId);
                break;
            case R.id.iv_edit:
                EditInfoActivity.start(this);
                break;
            case R.id.tv_follow:
                if (UserInfoTools.getIsLogin(this))
                    follow();
                else
                    LoginActivity.start(this);
                break;
            case R.id.iv_author:
                if (userId.equals(UserInfoTools.getUserId(this)))
                    pickFromGallery();
                break;
        }
    }


    private void follow() {
        if (userBean.getWatch_status() == 1) {
            cancelFocus(userBean.getUserId());
        } else {
            addFocus(userBean.getUserId());
        }
    }

    //  关注
    private void addFocus(String focusId) {
        IdeaApi.getApiService()
                .addFocus(UserInfoTools.getUserId(this), focusId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<String>>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse<String> response) {
                        ToastUtils.show(response.getMsg());
                        mTvFollow.setText("已关注");
                    }
                });
    }

    //  取消关注
    private void cancelFocus(String focusId) {
        IdeaApi.getApiService()
                .cancelFocus(UserInfoTools.getUserId(this), focusId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<String>>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse<String> response) {
                        ToastUtils.show(response.getMsg());
                        mTvFollow.setText(R.string.focus);
                    }
                });
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

    //  选择图片结果处理
    private void selectPicResult(int requestCode, Intent data) {
        if (requestCode == REQUEST_SELECT_PICTURE) {
            final Uri selectedUri = data.getData();
            if (selectedUri != null) {
                startCropActivity(data.getData());
            } else {
                Toast.makeText(AuthorInfoActivity.this, R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == UCrop.REQUEST_CROP) {
            handleCropResult(data);
        }
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
            //  向服务器提交头像数据
            postHeadPic();
        } else {
            showToast(R.string.toast_cannot_retrieve_cropped_image);
        }
    }

    /*public int getMaxBitmapSize() {
        if (mMaxBitmapSize <= 0) {
            mMaxBitmapSize = BitmapLoadUtils.calculateMaxBitmapSize(this);
        }
        return mMaxBitmapSize;
    }*/

    private void startCropActivity(@NonNull Uri uri) {
        String destinationFileName = SAMPLE_CROPPED_IMAGE_NAME;
        destinationFileName += ".png";
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));
        uCrop = uCrop.withAspectRatio(2, 3);
        uCrop = advancedConfig(uCrop);
        uCrop.start(AuthorInfoActivity.this);
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

    private void postHeadPic() {

        File file = new File(picPath);
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("user_id", UserInfoTools.getUserId(this))
                .addFormDataPart("type", Constants.HEAD_COVER)
                .addFormDataPart("uploadFile", "head_pic", imageBody);
        List<MultipartBody.Part> parts = builder.build().parts();
        IdeaApi.getApiService()
                .updateHeadPic(parts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                        EventBus.getDefault().post(new EditInfoActivity.UpdateInfoSuccess());
                        showToast(response.getMsg());
                        String url = response.getResult().toString();
                        UserInfoTools.setCover(AuthorInfoActivity.this, url);
                        ImageLoaderUtil.loadImg(mIvAuthor, IdeaApiService.HOST + url, R.drawable.head_pic);
                    }
                });
    }
}
