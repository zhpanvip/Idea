package com.cypoem.idea.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.cypoem.idea.activity.StartReadActivity;
import com.cypoem.idea.activity.WelcomeActivity;
import com.cypoem.idea.adapter.HomeAdapter;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.BaseOpusBean;
import com.cypoem.idea.module.bean.HomeBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.zhpan.viewpager.view.CircleViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrFrameLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhpan on 2017/4/21.
 */
public class MessageInFragment extends BaseFragment {
    /*@BindView(R.id.rv_write)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.ll_home)
    LinearLayout mLlHome;
    private HomeAdapter mAdapter;
    private int page = 1;
    private final int ROWS = 10;
    */
    private int type;
    @BindView(R.id.cvp_banner)
    CircleViewPager mViewPager;
    private List<Integer> mListInt = new ArrayList<>();
    public static MessageInFragment getFragment(int type) {
        MessageInFragment fragment = new MessageInFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    protected void initvp() {
        for (int i = 1; i <= 4; i++) {
            mListInt.add(R.layout.welcome_pager2);
        }
        mViewPager.setAutoPlay(false);
        mViewPager.setCanLoop(false);
        mViewPager.setIndicatorRadius(5);
        mViewPager.setIndicatorColor(getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimary));
        mViewPager.setPages(mListInt, () -> new WelcomeActivity.MViewHolder());
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find_in;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
       // initPtr(true);
        initvp();
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            type = bundle.getInt("type");
        }
        rootView.findViewById(R.id.toolbar).setVisibility(View.GONE);

    }
}
