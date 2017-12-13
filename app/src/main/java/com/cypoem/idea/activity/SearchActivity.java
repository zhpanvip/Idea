package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.cypoem.idea.adapter.AdapterSearchHistory;
import com.cypoem.idea.adapter.CircleListAdapter;
import com.cypoem.idea.adapter.CollectAdapter;
import com.cypoem.idea.adapter.UserListAdapter;
import com.cypoem.idea.constants.Constants;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.CircleBean;
import com.cypoem.idea.module.bean.OpusBean;
import com.cypoem.idea.module.bean.SearchBean;
import com.cypoem.idea.module.bean.SearchHistoryBean;
import com.cypoem.idea.module.bean.UserBean;
import com.cypoem.idea.module.bean.WriteBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.utils.SearchHistoryDao;
import com.cypoem.idea.utils.UserInfoTools;
import com.cypoem.idea.view.InScrollListView;
import com.cypoem.idea.view.MaxByteLengthEditText;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.iv_clear)
    ImageView ivClear;
    @BindView(R.id.et_search_text)
    MaxByteLengthEditText etSearchText;
    @BindView(R.id.layout_search)
    LinearLayout layoutSearch;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_hot_search)
    TextView tvHotSearch;
    @BindView(R.id.ll_hot_search)
    LinearLayout llHotSearch;
    @BindView(R.id.tv_clear_history)
    TextView tvClearHistory;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.lv_search_h)
    InScrollListView mListView;
    @BindView(R.id.ll_search_history)
    LinearLayout llSearchHistory;
    @BindView(R.id.ll_search_default)
    FrameLayout llSearch;
    @BindView(R.id.ll_loading)
    LinearLayout llLoading;
    @BindView(R.id.iv_loading)
    ImageView ivLoading;
    @BindView(R.id.tv_no_data)
    TextView mTvNoData;
    @BindView(R.id.fl_hot)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.ll_search)
    LinearLayout mLlSearch;
    @BindView(R.id.rv_circle)
    RecyclerView mRvCircle;
    @BindView(R.id.rv_story)
    RecyclerView mRvStory;
    @BindView(R.id.rv_user)
    RecyclerView mRvUsers;
    @BindView(R.id.rl_item_title)
    RelativeLayout mRlCircleTitle;
    @BindView(R.id.rl_user_title)
    RelativeLayout mRlUserTile;
    @BindView(R.id.rl_story_title)
    RelativeLayout mRlStoryTitle;
    @BindView(R.id.ll_story)
    LinearLayout mLlMoreStory;
    @BindView(R.id.ll_user)
    LinearLayout mLlMoreUser;
    @BindView(R.id.ll_circle)
    LinearLayout mLlMoreCircle;


    private TagAdapter<String> mTagAdapter;

    private String editContent;
    //搜索历史适配器
    private AdapterSearchHistory adapterHistory;
    //  搜索历史集合
    private List<SearchHistoryBean> mListHistory;
    private int page = 1;
    private Animation mAnimation;
    private String[] labelArray;
    private int type = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        getToolbar().setVisibility(View.GONE);
        initData();
        setListener();
    }

    @Override
    protected boolean isShowBacking() {
        return false;
    }

    private void initData() {
        mLlSearch.setVisibility(View.VISIBLE);
        labelArray = getApplicationContext().getResources().getStringArray(R.array.label);
        mTagAdapter = new TagAdapter<String>(labelArray) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                View view = LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_label2, mFlowLayout, false);
                TextView tv = view.findViewById(R.id.tv_label);
                tv.setText(s);
                return tv;
            }
        };

        mFlowLayout.setAdapter(mTagAdapter);


        adapterHistory = new AdapterSearchHistory(this);
        mListHistory = new ArrayList<>();
        getSearchHistory();
        adapterHistory.setList(mListHistory);
        mListView.setAdapter(adapterHistory);

        mAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        LinearInterpolator linearInterpolator = new LinearInterpolator();
        mAnimation.setInterpolator(linearInterpolator);
    }

    private void setListener() {
        etSearchText.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            //判断是不是搜索键
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                // 隐藏软键盘
                InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                }
                //  输入框不为空时插入数据库
                if (!TextUtils.isEmpty(editContent)) {
                    insertHistory();
                    // mAdapter.getList().clear();
                    getData(page);
                }
                return true;
            }
            return false;
        });

        mListView.setOnItemClickListener((parent, view, position, id) -> {
            AdapterSearchHistory adapter = (AdapterSearchHistory) parent.getAdapter();
            List<SearchHistoryBean> list = adapter.getList();
            //  设置搜索输入框最大可以输入60个字节
            //mBinding.etSearchText.setMaxByteLength(60);
            String item = list.get(position).getItem();
            etSearchText.setText(item);
            etSearchText.setSelection(item.length());
            // mAdapter.getList().clear();
            getData(page);
        });

        etSearchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                editContent = s.toString();
                if (TextUtils.isEmpty(editContent)) {
                    llSearch.setVisibility(View.GONE);
                    ivClear.setVisibility(View.GONE);
                } else {
                    ivClear.setVisibility(View.VISIBLE);
                }
            }
        });

        mFlowLayout.setOnTagClickListener((View view, int position, FlowLayout parent) -> {
            etSearchText.setText(labelArray[position]);
            etSearchText.setSelection(labelArray[position].length());
            // mAdapter.getList().clear();
            getData(page);
            return false;
        });
    }

    //  历史记录插入数据库
    private void insertHistory() {
        SearchHistoryDao historyDao = new SearchHistoryDao(
                SearchActivity.this);
        //  如果存在先删除
        historyDao.delHistoryByItem(editContent);
        SearchHistoryBean historyBean = new SearchHistoryBean();
        historyBean.setItem(editContent);
        historyDao.insertHistory(historyBean);
        getSearchHistory();
    }

    //  查询数据库获取搜索历史并适配ListView
    public void getSearchHistory() {
        mListHistory.clear();
        SearchHistoryDao historyDao = new SearchHistoryDao(this);
        mListHistory.addAll(historyDao.findLimit(8));
        //  适配搜索历史到ListView
        if (mListHistory.size() <= 0) {
            llSearchHistory.setVisibility(View.GONE);
        } else {
            llSearchHistory.setVisibility(View.VISIBLE);
        }
        adapterHistory.notifyDataSetChanged();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    @OnClick({R.id.iv_clear, R.id.tv_cancel, R.id.tv_clear_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_clear:
                etSearchText.setText("");
                break;
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_clear_history:
                clearHistory();
                break;
        }
    }

    //  清空搜索记录
    private void clearHistory() {
        SearchHistoryDao historyDao = new SearchHistoryDao(this);
        historyDao.delAll();
        getSearchHistory();
    }

    private void getData(int page) {
        showLoading();
        IdeaApi.getApiService()
                .search(UserInfoTools.getUserId(this), editContent, page, Constants.NUM, type)
                .subscribeOn(Schedulers.io())
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<SearchBean>>(this, false) {
                    @Override
                    public void onSuccess(BasicResponse<SearchBean> response) {
                        SearchBean result = response.getResult();
                        getDataSuccess(result);
                    }

                    @Override
                    public void dismissProgress() {
                        super.dismissProgress();
                        dismissLoading();
                    }
                });
    }

    private void getDataSuccess(SearchBean searchBean) {
        llSearch.setVisibility(View.VISIBLE);
        dismissLoading();
        if (searchBean != null) {
            List<CircleBean> circles = searchBean.getCircles();
            List<UserBean> users = searchBean.getUsers();
            List<WriteBean> writes = searchBean.getWrites();
            if (circles.size() == 0 && users.size() == 0 && writes != null && writes.size() == 0) {
                mTvNoData.setVisibility(View.VISIBLE);
            } else {
                mTvNoData.setVisibility(View.GONE);
            }
            searchUserList(users);
            setCircleList(circles);
            setStoryList(writes);
        } else {
            mTvNoData.setVisibility(View.VISIBLE);
        }
    }

    private void setStoryList(List<WriteBean> writes) {
        if (writes != null) {
            if (writes.size() <= 0) {
                mRlStoryTitle.setVisibility(View.GONE);
            } else if (writes.size() <= 3) {
                mLlMoreStory.setVisibility(View.GONE);
            }
        } else {
            mRlStoryTitle.setVisibility(View.GONE);
            mLlMoreStory.setVisibility(View.GONE);
        }
    }

    private void setCircleList(List<CircleBean> circles) {
        if (circles.size() <= 0) {
            mRlCircleTitle.setVisibility(View.GONE);
        } else if (circles.size() <= 3) {
            mLlMoreCircle.setVisibility(View.GONE);
        } else {
            mRlCircleTitle.setVisibility(View.VISIBLE);
            mLlMoreCircle.setVisibility(View.VISIBLE);
        }
        CircleListAdapter adapter = new CircleListAdapter(this);
        adapter.fillList(circles);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRvCircle.setLayoutManager(manager);
        mRvCircle.setAdapter(adapter);
    }

    private void searchUserList(List<UserBean> searchBean) {
        if (searchBean.size() <= 0) {
            mRlUserTile.setVisibility(View.GONE);
        } else if (searchBean.size() <= 3) {
            mLlMoreUser.setVisibility(View.GONE);
        } else {
            mRlUserTile.setVisibility(View.VISIBLE);
            mLlMoreUser.setVisibility(View.VISIBLE);
        }
        UserListAdapter adapter = new UserListAdapter(this);
        adapter.fillList(searchBean);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRvUsers.setLayoutManager(manager);
        mRvUsers.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissLoading();
    }

    private void dismissLoading() {
        //隐藏正在加载
        llLoading.setVisibility(View.GONE);
        ivLoading.clearAnimation();
    }

    private void showLoading() {
        llLoading.setVisibility(View.VISIBLE);
        ivLoading.startAnimation(mAnimation);
    }
}
