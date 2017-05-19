package com.cypoem.idea.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.cypoem.idea.adapter.PraiseAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


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
    ListView mLvPraise;
    @BindView(R.id.cb_system)
    CheckBox mCbSystem;
    @BindView(R.id.lv_system)
    RecyclerView mLvSystem;
    @BindView(R.id.cb_other)
    CheckBox mCbOther;
    @BindView(R.id.lv_other)
    RecyclerView mLvOther;

    private PraiseAdapter mPraiseAdapter;
    private List<String> mList;

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
        mCbPraise.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mLvPraise.setVisibility(View.VISIBLE);
                }else {
                    mLvPraise.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initData() {
        toolbarTitle.setText("我的消息");
        mPraiseAdapter=new PraiseAdapter(getContext());
        mList=new ArrayList<>();
        mList.add("123");
        mList.add("123");
        mList.add("123");
        mList.add("123");
        mPraiseAdapter.setList(mList);
        mLvPraise.setAdapter(mPraiseAdapter);
    }

}
