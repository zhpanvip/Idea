package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.cypoem.idea.adapter.AdapterSearchHistory;
import com.cypoem.idea.adapter.CollectAdapter;
import com.cypoem.idea.constants.Constants;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.HomePageBean;
import com.cypoem.idea.module.bean.OpusBean;
import com.cypoem.idea.module.bean.SearchHistoryBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.utils.SearchHistoryDao;
import com.cypoem.idea.view.GridViewForScrollView;
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
    LinearLayout llSearch;
    @BindView(R.id.ll_loading)
    LinearLayout llLoading;
    @BindView(R.id.lv_search)
    ListView mLvSearch;
    @BindView(R.id.iv_loading)
    ImageView ivLoading;
    private CollectAdapter mAdapter;
    @BindView(R.id.tv_no_data)
    TextView mTvNoData;
    @BindView(R.id.fl_hot)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.ll_search)
    LinearLayout mLlSearch;

    private TagAdapter<String> mTagAdapter;

    private String editContent;
    //搜索历史适配器
    private AdapterSearchHistory adapterHistory;
    //  搜索历史集合
    private List<SearchHistoryBean> mListHistory;
    private int page = 1;
    private Animation mAnimation;
    private String[] labelArray;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
       // getToolbar().setVisibility(View.GONE);
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
                TextView tv = (TextView) view.findViewById(R.id.tv_label);
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

        mAdapter = new CollectAdapter(this, R.layout.item_collect);
        List<OpusBean> mList = new ArrayList<>();
        mAdapter.setList(mList);
        mLvSearch.setAdapter(mAdapter);

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
                    mAdapter.getList().clear();
                    getData(page);
                }
                return true;
            }
            return false;
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AdapterSearchHistory adapter = (AdapterSearchHistory) parent.getAdapter();
                List<SearchHistoryBean> list = adapter.getList();
                //  设置搜索输入框最大可以输入60个字节
                //mBinding.etSearchText.setMaxByteLength(60);
                String item = list.get(position).getItem();
                etSearchText.setText(item);
                etSearchText.setSelection(item.length());
                mAdapter.getList().clear();
                getData(page);
            }
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

        mLvSearch.setOnItemClickListener((AdapterView<?> parent, View view, int position, long id) -> {
            OpusBean opusBean = mAdapter.getList().get(position);
            String writeId = String.valueOf(opusBean.getWrite_id());
            String authorId = opusBean.getUser_id();
            String write_name = opusBean.getWrite_name();
            StartReadActivity.start(SearchActivity.this, writeId, authorId, write_name);
        });

        mFlowLayout.setOnTagClickListener((View view, int position, FlowLayout parent) -> {
            etSearchText.setText(labelArray[position]);
            etSearchText.setSelection(labelArray[position].length());
            mAdapter.getList().clear();
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
                onBackPress();
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
                .getSearchData(editContent, page, Constants.NUM)
                .subscribeOn(Schedulers.io())
                .compose(bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<OpusBean>>>(this, false) {
                    @Override
                    public void onSuccess(BasicResponse<List<OpusBean>> response) {
                        getDataSuccess(response);
                    }

                    @Override
                    public void onFail(BasicResponse<List<OpusBean>> response,int code) {
                        super.onFail(response,code);
                        dismissLoading();
                    }

                    @Override
                    public void onException(ExceptionReason reason) {
                        super.onException(reason);
                        dismissLoading();
                    }
                });
    }

    private void getDataSuccess(BasicResponse<List<OpusBean>> response) {
        llSearch.setVisibility(View.VISIBLE);
        dismissLoading();
        List<OpusBean> result = response.getResult();
        if (result != null) {
            if (result.size() == 0) {
                mTvNoData.setVisibility(View.VISIBLE);
            } else {
                mTvNoData.setVisibility(View.GONE);
            }
            mAdapter.getList().addAll(response.getResult());
            mAdapter.notifyDataSetChanged();
        } else {
            mTvNoData.setVisibility(View.VISIBLE);
        }
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
