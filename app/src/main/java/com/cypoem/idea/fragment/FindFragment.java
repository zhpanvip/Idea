package com.cypoem.idea.fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.cypoem.idea.R;
import com.cypoem.idea.adapter.BaseFragmentAdapter;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhpan on 2017/4/21.
 */

public class FindFragment extends BaseFragment {

    @BindView(R.id.rb_left)
    RadioButton mRbLeft;
    @BindView(R.id.rb_right)
    RadioButton mRbRight;
    @BindView(R.id.rg_selector)
    RadioGroup mRgSelector;
    @BindView(R.id.toolbar_title)
    TextView mTitle;
    @BindView(R.id.vp_find)
    ViewPager mViewPager;

    public final static int NEWEST = 1;
    public final static int HOTEST = 2;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    protected void init() {
        initData();
    }

    private void initData() {
        mTitle.setVisibility(View.GONE);
        mRgSelector.setVisibility(View.VISIBLE);
        setViewPager();
        setListener();
    }

    private void setListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    mRbLeft.setChecked(true);
                }else if(position==1){
                    mRbRight.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void setViewPager() {
        List<HomePageFragment> mList = new ArrayList<>();
        HomePageFragment newFragment = HomePageFragment.getInstance(NEWEST);
        HomePageFragment hotFragment = HomePageFragment.getInstance(HOTEST);
        mList.add(newFragment);
        mList.add(hotFragment);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        BaseFragmentAdapter mFragmentAdapter = new BaseFragmentAdapter(fragmentManager, getContext());
        mFragmentAdapter.setmFragmentList(mList);
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setCurrentItem(0);
    }

    @OnClick({R.id.rb_left,R.id.rb_right})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rb_left:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.rb_right:
                mViewPager.setCurrentItem(1);
                break;
        }
    }

}
