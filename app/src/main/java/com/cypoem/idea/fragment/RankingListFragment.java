package com.cypoem.idea.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airong.core.utils.ImageLoaderUtil;
import com.cypoem.idea.R;
import com.cypoem.idea.constants.Constants;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.RankingBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.net.IdeaApiService;
import com.cypoem.idea.utils.UserInfoTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RankingListFragment extends BaseFragment {
    public static final int GET = 2;
    public static final int DONATE = 1;
    @BindView(R.id.iv_head1)
    ImageView mIvHead1;
    @BindView(R.id.rl_first)
    RelativeLayout mRlFirst;
    @BindView(R.id.tv_name1)
    TextView mTvName1;
    @BindView(R.id.tv_value1)
    TextView mTvValue1;
    @BindView(R.id.iv_head2)
    ImageView mIvHead2;
    @BindView(R.id.textView11)
    TextView mTextView11;
    @BindView(R.id.rl_second)
    RelativeLayout mRlSecond;
    @BindView(R.id.tv_name2)
    TextView mTvName2;
    @BindView(R.id.tv_value2)
    TextView mTvValue2;
    @BindView(R.id.iv_head3)
    ImageView mIvHead3;
    @BindView(R.id.rl_third)
    RelativeLayout mRlThird;
    @BindView(R.id.tv_name3)
    TextView mTvName3;
    @BindView(R.id.tv_value3)
    TextView mTvValue3;
    @BindView(R.id.rv_ranking)
    RecyclerView mRvRanking;
    @BindView(R.id.ll_second)
    LinearLayout mLlSecond;
    @BindView(R.id.ll_third)
    LinearLayout mLlThird;
    private int page = 1;
    private int type = 1;
    private List<RankingBean> mList;


    public static RankingListFragment getFragment(int type) {
        RankingListFragment fragment = new RankingListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        getData();
    }

    //  请求数据
    private void getData() {
        IdeaApi.getApiService()
                .getRanking(UserInfoTools.getUserId(getContext()), page, Constants.NUM, type)
                .subscribeOn(Schedulers.io())
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<RankingBean>>>(getActivity()) {
                    @Override
                    public void onSuccess(BasicResponse<List<RankingBean>> response) {
                        List<RankingBean> result = response.getResult();
                        getDataSuccess(result);
                    }

                    @Override
                    public void dismissProgress() {
                        super.dismissProgress();
                        mRefreshLayout.setRefreshing(false);
                    }
                });
    }

    private void getDataSuccess(List<RankingBean> result) {
        if(result.size()==1){
           setFirst(result.get(0));
            mLlSecond.setVisibility(View.GONE);
        }else if(result.size()==2){
            setFirst(result.get(0));
            setSecond(result.get(1));
            mLlThird.setVisibility(View.GONE);
        }else {
            setFirst(result.get(0));
            setSecond(result.get(1));
            setThird(result.get(2));
            for(int i=0;i<result.size();i++){
                if(i==0||i==1||i==2){
                    continue;
                }
                mList.add(result.get(i));
            }
        }
    }

    private void setThird(RankingBean rankingBean) {
        ImageLoaderUtil.loadCircleImg(mIvHead3, IdeaApiService.HOST+rankingBean.getIcon(),R.drawable.login_logo);
        mTvName3.setText(rankingBean.getPen_name());
        mTvValue3.setText(type==1?"送出  ":"收获  "+rankingBean.getRollout_count());
    }

    private void setSecond(RankingBean rankingBean) {
        ImageLoaderUtil.loadCircleImg(mIvHead2, IdeaApiService.HOST+rankingBean.getIcon(),R.drawable.login_logo);
        mTvName2.setText(rankingBean.getPen_name());
        mTvValue2.setText(type==1?"送出  ":"收获  "+rankingBean.getRollout_count());
    }

    private void setFirst(RankingBean rankingBean){
        ImageLoaderUtil.loadCircleImg(mIvHead1, IdeaApiService.HOST+rankingBean.getIcon(),R.drawable.login_logo);
        mTvName1.setText(rankingBean.getPen_name());
        mTvValue1.setText(type==1?"送出  ":"收获  "+rankingBean.getRollout_count());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ranking_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        type=arguments.getInt("type");
        mList=new ArrayList<>();
        setRefreshLayout(true);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.rl_first, R.id.rl_second, R.id.rl_third})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_first:
                break;
            case R.id.rl_second:
                break;
            case R.id.rl_third:
                break;
        }
    }
}
