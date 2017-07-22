package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.airong.core.utils.ImageLoaderUtil;
import com.cypoem.idea.R;
import com.cypoem.idea.adapter.CommonFragmentAdapter;
import com.cypoem.idea.constants.Constants;
import com.cypoem.idea.fragment.AuthorFragment;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.UserBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.utils.UserInfoTools;
import com.cypoem.idea.view.ScrollableLayout;
import com.cypoem.idea.view.SexView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    private String userId="";

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

    @Subscribe
    public void updateInfoSuccess(EditInfoActivity.UpdateInfoSuccess success){
        UserBean user = UserInfoTools.getUser(this);
        setUserData(user);
    }

    private void setData() {
        UserBean user = UserInfoTools.getUser(this);
        if(null!=user&&userId.equals(UserInfoTools.getUserId(this))){
            setUserData(user);
        }else {
            mIvEdit.setVisibility(View.GONE);
            mTvFollow.setVisibility(View.VISIBLE);
            getData(false);
        }
    }

    private void setUserData(UserBean user) {
        ImageLoaderUtil.loadImg(mIvAuthor,user.getIcon(),R.drawable.head_pic);
        mTvPenName.setText(user.getPen_name());
        String sex = user.getSex();
        mSexView.setMalePercent(Double.parseDouble(sex));
        mTvSign.setText(user.getIntroduction());
        mTvBirthday.setText(user.getBirthday());
        mTvAddress.setText(user.getAddress());
        mTvIntroduce.setText(user.getIntroduction());
        mTvFans.setText(user.getWatchMeCount()+"");
        mTvFocus.setText(user.getMyWatchCount()+"");
        mTvCollect.setText(user.getKeep_count()+"");
        mTvLike.setText(user.getEnjoy_count()+"");

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
                .getUserInfo(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<UserBean>>(this,showLoading) {
                    @Override
                    public void onSuccess(BasicResponse<UserBean> response) {
                        setUserData(response.getResult());
                    }
                });
    }

    private void initData() {
        Intent intent = getIntent();
        userId=intent.getStringExtra("userId");
        mList = new ArrayList<>();
        /*Bundle bundle = new Bundle();
        bundle.putString("userId",userId);*/
        AuthorFragment fragmentStart = AuthorFragment.getFragment(Constants.MY_START_OPUS,userId);
        AuthorFragment fragmentJoin = AuthorFragment.getFragment(Constants.MY_JOIN_OPUS,userId);
        AuthorFragment fragmentCreate = AuthorFragment.getFragment(Constants.MY_OWN_OPUS,userId);
        mList.add(fragmentStart);
        mList.add(fragmentJoin);
        mList.add(fragmentCreate);
        mAdapter = new CommonFragmentAdapter(getSupportFragmentManager(), this);
        mAdapter.setFragmentList(mList);
        mViewPager.setAdapter(mAdapter);
        mScrollView.getHelper().setCurrentScrollableContainer(mList.get(0));
    }

    public static void start(Context context,String userId) {
        Intent intent = new Intent(context, AuthorInfoActivity.class);
        intent.putExtra("userId",userId);
        context.startActivity(intent);
    }


    @OnClick({R.id.ll_collect, R.id.ll_like, R.id.ll_fans, R.id.ll_focus, R.id.iv_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_collect:
                CollectActivity.start(this);
                break;
            case R.id.ll_like:
                PraiseActivity.start(this);
                break;
            case R.id.ll_fans:
                FansActivity.start(this, Constants.FOLLOWS);
                break;
            case R.id.ll_focus:
                FansActivity.start(this,Constants.FOCUS);
                break;
            case R.id.iv_edit:
                EditInfoActivity.start(this);
                break;
            case R.id.tv_follow:
                follow();
                break;
        }
    }



    private void follow() {

    }
}
