package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airong.core.view.PtrClassicListFooter;
import com.airong.core.view.PtrClassicListHeader;
import com.cypoem.idea.R;
import com.cypoem.idea.adapter.CommentAdapter;
import com.cypoem.idea.adapter.StartReadAdapter;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.ArticleBean;
import com.cypoem.idea.module.bean.CommentBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.utils.UserInfoTools;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class StartReadActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_comment)
    TextView tvComment;
    @BindView(R.id.tv_like)
    TextView tvLike;
    @BindView(R.id.tv_value)
    TextView tvValue;
    @BindView(R.id.tv_continue)
    TextView tvContinue;
    @BindView(R.id.ll_tab)
    LinearLayout llTab;
    @BindView(R.id.rv_read)
    RecyclerViewPager mViewPager;
    @BindView(R.id.ll_comment)
    LinearLayout llComment;
    @BindView(R.id.ll_like)
    LinearLayout llLike;
    @BindView(R.id.ll_value)
    LinearLayout llValue;
    @BindView(R.id.tv_rewrite)
    TextView tvRewrite;
    @BindView(R.id.ll_rewrite)
    LinearLayout llRewrite;
    @BindView(R.id.ll_continue)
    LinearLayout llContinue;
    @BindView(R.id.rl_read)
    RelativeLayout mRlRead;

    private EditText mEtComment;

    private StartReadAdapter mAdapter;
    private int page = 1;
    private int commentPage=1;
    private String authorId;
    private String writeId;
    private PopupWindow popupWindow;
    private RecyclerView mRecyclerView;
    private ImageView mIvDismiss;
    private CommentAdapter mCommentAdapter;
    private String section_id;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start_read;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
        initPopWindows();
    }

    private void initData() {
        Intent intent = getIntent();
        authorId = intent.getStringExtra("authorId");
        writeId = intent.getStringExtra("writeId");

        List<List<ArticleBean>> list = new ArrayList<>();
        mAdapter = new StartReadAdapter(this);
        mAdapter.setList(list);

        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false);
        mViewPager.setTriggerOffset(0.15f);
        mViewPager.setFlingFactor(0.25f);
        mViewPager.setLayoutManager(layout);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setHasFixedSize(true);
        mViewPager.setLongClickable(true);

        mViewPager.addOnPageChangedListener((int i, int i1) -> {
            showToast("vertical position=" + i1);
            startAnim();
        });

        getData(page);
    }

    public void onHorizontalItemSelected(int position, int id) {
        showToast("horizontal position=" + position);
        startAnim();
        setArticleData(position, id);
    }

    private void setArticleData(int position, int id) {
        List<ArticleBean> articleBeen = mAdapter.getList().get(position);
        ArticleBean articleBean = articleBeen.get(id);
        section_id = articleBean.getSection_id();
        tvComment.setText(String.valueOf(articleBean.getComment_count()));
        tvContinue.setText(String.valueOf(articleBean.getRead_count()));
        tvLike.setText(String.valueOf(articleBean.getLike_count()));
        tvRewrite.setText(String.valueOf(articleBean.getEnjoy_count()));
        tvValue.setText(String.valueOf(articleBean.getEnjoy_count()));
    }

    public void startAnim() {
        Animation animation = AnimationUtils
                .loadAnimation(StartReadActivity.this, R.anim.draw_down);
        llTab.startAnimation(animation);
    }

    public static void start(Context context, String writeId, String authorId) {
        Intent intent = new Intent(context, StartReadActivity.class);
        intent.putExtra("writeId", writeId);
        intent.putExtra("authorId", authorId);
        context.startActivity(intent);
    }

    private void getData(int page) {
        Map<String, String> map = new HashMap<>();
        map.put("parent_id", "0");
        map.put("user_id", UserInfoTools.getUserId(this));
        map.put("page", page + "");
        map.put("rows", "10");
        map.put("write_id", writeId);
        map.put("write_author_id", authorId);
        IdeaApi.getApiService()
                .getArticle(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<ArticleBean>>>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse<List<ArticleBean>> response) {
                        List<ArticleBean> results = response.getResult();
                        mAdapter.getList().add(results);
                        mAdapter.notifyDataSetChanged();
                        if (results.size() > 0) {
                            setArticleData(0, 0);
                        }
                    }
                });
    }

    @OnClick({R.id.ll_comment, R.id.ll_like, R.id.ll_value, R.id.ll_rewrite, R.id.ll_continue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_comment:
                comment();
                break;
            case R.id.ll_like:
                break;
            case R.id.ll_value:
                break;
            case R.id.ll_rewrite:
                break;
            case R.id.ll_continue:
                break;

        }
    }

    private void comment() {
        getComment(1,false);
        popupWindow.showAtLocation(mRlRead, Gravity.CENTER, 0, 0);
    }

    private void initPopWindows() {
        View popView = View.inflate(this, R.layout.comment_window, null);
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //  给弹出框设置默认动画
        popupWindow.setAnimationStyle(-1);
        //  点击其他地方取消popWindows
        popupWindow.setFocusable(true);
        initPopView(popView);
    }

    private void initPopView(View popView) {
        Button tvComment = (Button) popView.findViewById(R.id.btn_comment);
        mEtComment= (EditText) popView.findViewById(R.id.et_comment);
        mIvDismiss = (ImageView) popView.findViewById(R.id.iv_dismiss);
        LinearLayout linearLayout = (LinearLayout) popView.findViewById(R.id.ll_pop_window);
        linearLayout.setOnClickListener(this);
        tvComment.setOnClickListener(this);
        mIvDismiss.setOnClickListener(this);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        mRecyclerView = (RecyclerView) popView.findViewById(R.id.rv_comment);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mCommentAdapter = new CommentAdapter(this);
        mCommentAdapter.fillList(new ArrayList<>());
        mRecyclerView.setAdapter(mCommentAdapter);

        initPtr(popView);
    }

    protected void initPtr(View popView) {
        mPtrFrame = (PtrClassicFrameLayout) popView.findViewById(R.id.list_view_frame);
        mPtrFrame.setMode(PtrFrameLayout.Mode.LOAD_MORE);
        PtrClassicListFooter footer = new PtrClassicListFooter(this);
        footer.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setFooterView(footer);
        mPtrFrame.addPtrUIHandler(footer);

        mPtrFrame.setPtrHandler(new PtrDefaultHandler2() {
            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                getComment(++commentPage,true);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
            }

            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                return super.checkCanDoLoadMore(frame, content, footer);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
        mPtrFrame.setKeepHeaderWhenRefresh(true);
    }

    /**
     * 获取评论
     * @param page  页码
     * @param isLoadMore 是否是上拉加载数据
     */
    private void getComment(int page,boolean isLoadMore) {
        Map<String, String> map = new HashMap<>();
        map.put("section_id", section_id);
        map.put("user_id", UserInfoTools.getUserId(this));
        map.put("page", page + "");
        map.put("rows", "5");
        IdeaApi.getApiService()
                .getComment(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<CommentBean>>>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse<List<CommentBean>> response) {
                        List<CommentBean> list = mCommentAdapter.getList();
                        if(response.getResult().size()<5){
                            mPtrFrame.setMode(PtrFrameLayout.Mode.NONE);
                        }else {
                            mPtrFrame.setMode(PtrFrameLayout.Mode.LOAD_MORE);
                        }
                        if(!isLoadMore){
                            list.clear();
                        }
                        list.addAll(response.getResult());
                        mCommentAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_dismiss:
                popupWindow.dismiss();
                break;
            case R.id.btn_comment:
                postComment();
                break;
        }
    }

    @Override
    public void dismissProgress() {
        super.dismissProgress();
        mPtrFrame.refreshComplete();
    }

    private void postComment() {
        String content = mEtComment.getText().toString().trim();
        if(TextUtils.isEmpty(content)){
            showToast("请输入评论内容");
            return;
        }
        IdeaApi.getApiService()
                .comment(UserInfoTools.getUserId(this),section_id,content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<String>>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse<String> response) {
                        commentPage=1;
                        showToast("评论成功");
                        mEtComment.setText("");
                        getComment(1,false);
                    }
                });

    }
}
