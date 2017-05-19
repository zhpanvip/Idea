package com.cypoem.idea.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.cypoem.idea.adapter.NotifyAdapter;
import com.cypoem.idea.module.bean.NotifyBean;
import com.cypoem.idea.view.ListInScrollView;
import com.cypoem.idea.view.ListViewForScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by zhpan on 2017/4/21.
 */

public class MessageFragment extends BaseFragment {
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.cb_praise)
    CheckBox mCbPraise;
    @BindView(R.id.lv_praise)
    ListViewForScrollView mLvPraise;
    @BindView(R.id.cb_system)
    CheckBox mCbSystem;
    @BindView(R.id.lv_system)
    ListViewForScrollView mLvSystem;
    @BindView(R.id.cb_other)
    CheckBox mCbOther;
    @BindView(R.id.lv_other)
    ListViewForScrollView mLvOther;

    private NotifyAdapter mPraiseAdapter;
    private NotifyAdapter mSystemAdapter;
    private NotifyAdapter mOtherAdapter;
    private List<NotifyBean> mList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void init() {
        initData();
        setListener();
    }

    private void setListener() {
        mCbPraise.setOnCheckedChangeListener((buttonView,isChecked)-> {
                if(isChecked){
                    mLvPraise.setVisibility(View.VISIBLE);
                }else {
                    mLvPraise.setVisibility(View.GONE);
                }
        });
        mCbSystem.setOnCheckedChangeListener((buttonView,isChecked)-> {
            if(isChecked){
                mLvSystem.setVisibility(View.VISIBLE);
            }else {
                mLvSystem.setVisibility(View.GONE);
            }
        });
        mCbOther.setOnCheckedChangeListener((buttonView,isChecked)-> {
            if(isChecked){
                mLvOther.setVisibility(View.VISIBLE);
            }else {
                mLvOther.setVisibility(View.GONE);
            }
        });
    }

    private void initData() {
        toolbarTitle.setText("我的消息");
        mPraiseAdapter=new NotifyAdapter(getContext(),R.layout.item_praise);
        mSystemAdapter=new NotifyAdapter(getContext(),R.layout.item_praise);
        mOtherAdapter=new NotifyAdapter(getContext(),R.layout.item_praise);
        mList=new ArrayList<>();
        NotifyBean notifyBean=new NotifyBean();
        notifyBean.setContent("这是内容");
        notifyBean.setTime("这是时间");
        notifyBean.setTitle("这是标题");
        mList.add(notifyBean);
        mList.add(notifyBean);
        mList.add(notifyBean);
        mList.add(notifyBean);

        mPraiseAdapter.setList(mList);
        mSystemAdapter.setList(mList);
        mOtherAdapter.setList(mList);
        mLvPraise.setAdapter(mPraiseAdapter);
        mLvSystem.setAdapter(mSystemAdapter);
        mLvOther.setAdapter(mOtherAdapter);
    }

}
