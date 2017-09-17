package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airong.core.utils.SnackbarUtils;
import com.airong.core.view.PtrClassicListFooter;
import com.cypoem.idea.R;
import com.cypoem.idea.adapter.CommentAdapter;
import com.cypoem.idea.adapter.StartReadAdapter;
import com.cypoem.idea.event.RewriteSuccess;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.ArticleBean;
import com.cypoem.idea.module.bean.CommentBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.net.IdeaApiService;
import com.cypoem.idea.utils.UserInfoTools;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.social.UMPlatformData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;
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
    @BindView(R.id.iv_prise)
    ImageView mIvPrise;

    //  vertical position
    private int vPosition;

    private EditText mEtComment;

    private StartReadAdapter mAdapter;
    private int page = 1;
    private int commentPage = 1;
    private String authorId;
    private String writeId;
    private PopupWindow popupWindow;
    private RecyclerView mRecyclerView;
    private ImageView mIvDismiss;
    private CommentAdapter mCommentAdapter;
    //  章节id
    private String section_id;
    //  书id
    private String write_id;
    private int likeStatus;
    private PopupWindow mPopupWindow;
    private LinearLayout mLlShare;
    private LinearLayout mLlCollect;
    private LinearLayout mRlReport;
    private LinearLayout mRlAdvice;
    private String keepStatus;
    private LinearLayout mLlDefault;
    private int prePosition = 0;
    private int horiPosition = 0;
    private ArticleBean articleBean;
    private String chapter_id = "1";
    private String parent_id = "000";
    private String title;
    private LinearLayout mLlDelete;
    private LinearLayout mLlEdit;
    private String user_id = "1";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start_read;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
        initToolbar();
        initCommentPopWindows();
    }

    private void initToolbar() {
        Toolbar toolbar = getToolbar();
        toolbar.inflateMenu(R.menu.toolbar_menu);
        setToolBarTitle(title);
        initSharePopWindow();
        setRightIvRes(R.drawable.ic_more);
        getRightIv().setOnClickListener((View v) ->
                showPopWindow(getRightIv()));
    }

    private void showPopWindow(View v) {
        mPopupWindow.showAsDropDown(v);
    }

    private void initSharePopWindow() {
        View mPopView = View.inflate(this, R.layout.dialog_share, null);
        mPopupWindow = new PopupWindow(mPopView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPopupWindow.setFocusable(true);
        mPopView.setOnClickListener((View v) ->
                mPopupWindow.dismiss());

        mLlShare = (LinearLayout) mPopView.findViewById(R.id.ll_share);
        mLlDelete = (LinearLayout) mPopView.findViewById(R.id.ll_delete);
        mLlEdit = (LinearLayout) mPopView.findViewById(R.id.ll_edit);
        mLlCollect = (LinearLayout) mPopView.findViewById(R.id.ll_collect);
        mRlReport = (LinearLayout) mPopView.findViewById(R.id.ll_report);
        mRlAdvice = (LinearLayout) mPopView.findViewById(R.id.ll_advice);
        mLlShare.setOnClickListener(this);
        mLlCollect.setOnClickListener(this);
        mRlReport.setOnClickListener(this);
        mRlAdvice.setOnClickListener(this);
        mLlDelete.setOnClickListener(this);
        mLlEdit.setOnClickListener(this);
    }

    //  显示删除和编辑章节
    private void setDeleteChapter() {
        if (articleBean.getStatus() == 1 &&
                articleBean.getUser().getUserId().equals(user_id)) {
            mLlDelete.setVisibility(View.VISIBLE);
            mLlEdit.setVisibility(View.VISIBLE);
        } else {
            mLlDelete.setVisibility(View.GONE);
            mLlEdit.setVisibility(View.GONE);
        }
    }

    private void initData() {
        EventBus.getDefault().register(this);
        Intent intent = getIntent();
        authorId = intent.getStringExtra("authorId");
        writeId = intent.getStringExtra("writeId");
        title = intent.getStringExtra("title");
        SparseIntArray mIndexMap = new SparseIntArray();
        mIndexMap.put(0, 0);
        List<List<ArticleBean>> list = new ArrayList<>();
        mAdapter = new StartReadAdapter(this);
        mAdapter.setList(list);
        mAdapter.setmIndexMap(mIndexMap);

        LinearLayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false);
        mViewPager.setTriggerOffset(0.15f);
        mViewPager.setFlingFactor(0.1f);
        mViewPager.setLayoutManager(layout);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setHasFixedSize(true);
        mViewPager.setLongClickable(true);

        mViewPager.addOnPageChangedListener((int i, int position) -> {
            //  startAnim();
            this.vPosition = position;
            int hPosition = mIndexMap.get(position);
            setArticleData(position, hPosition);
            chapter_id = (position + 1) + "";
            parent_id = articleBean.getSection_id();
            /*if(hPosition!=0) {
                RecyclerViewPager viewPager = mAdapter.getViewPager();
                viewPager.scrollToPosition(hPosition);
            }*/
            if (position > prePosition) {
                getNextChapter(articleBean.getSection_id(), false);
                prePosition = position;
                if (hPosition == 0) {
                    mIndexMap.put(position, 0);
                }
            }
        });
        if (UserInfoTools.getIsLogin(this)) {
            user_id = UserInfoTools.getUserId(this);
        }

        getFirstChapter(page);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 重写续写成功
     *
     * @param rewriteSuccess
     */
    @Subscribe
    public void rewriteSuccess(RewriteSuccess rewriteSuccess) {
        refreshData();
    }

    private void refreshData() {
        mAdapter.getList().clear();
        page = 1;
        vPosition = 0;
        page = 1;
        commentPage = 1;
        chapter_id = "1";
        parent_id = "000";
        prePosition = 0;
        getFirstChapter(page);
    }

    /**
     * @param position 当前选中的position（竖直）
     * @param id       横向位置
     */
    public void onHorizontalItemSelected(int position, int id) {
        mAdapter.getmIndexMap().put(position, id);
        horiPosition = id;
        prePosition = position;
        // startAnim();
        setArticleData(position, id);
        // parent_id = articleBean.getSection_id();
        // String section_id = articleBean.getSection_id();
        refreshAdapterList(position);
        getNextChapter(section_id, true);
    }


    private void refreshAdapterList(int position) {
        List<List<ArticleBean>> list = mAdapter.getList();
        List<List<ArticleBean>> newList = new ArrayList<>();
        for (int i = 0; i < position + 1; i++) {
            newList.add(list.get(i));
        }
        list.clear();
        list.addAll(newList);
        //mAdapter.notifyItemChanged(position + 1);
    }


    private void setArticleData(int position, int id) {
        List<ArticleBean> articleBeen = mAdapter.getList().get(position);
        articleBean = articleBeen.get(id);
        section_id = articleBean.getSection_id();
        likeStatus = articleBean.getLikeStatus();
        write_id = articleBean.getWrite_id();
        parent_id = articleBean.getSection_id();
        keepStatus = articleBean.getKeepStatus();
        tvComment.setText("评论" + String.valueOf(articleBean.getComment_count()));
        tvContinue.setText("续写" + String.valueOf(articleBean.getUpCount()));
        tvLike.setText("赞" + String.valueOf(articleBean.getLike_count()));
        tvRewrite.setText("重写" + String.valueOf(articleBean.getReCount()));
        tvValue.setText("欣赏" + String.valueOf(articleBean.getEnjoy_count()));
        int status = articleBean.getStatus();
        if (status == 0) {
            llComment.setClickable(false);
            llLike.setClickable(false);
            llValue.setClickable(false);
            llRewrite.setClickable(false);
            llContinue.setClickable(false);
        } else {
            llComment.setClickable(true);
            llLike.setClickable(true);
            llValue.setClickable(true);
            llRewrite.setClickable(true);
            llContinue.setClickable(true);
        }
        if (likeStatus == 0) {
            mIvPrise.setImageLevel(0);
        } else {
            mIvPrise.setImageLevel(1);
        }

        //  初始化显示不显示编辑和删除
        setDeleteChapter();
    }

    public void startAnim() {
        Animation animation = AnimationUtils
                .loadAnimation(StartReadActivity.this, R.anim.draw_down);
        llTab.startAnimation(animation);
    }

    public static void start(Context context, String writeId, String authorId, String title) {
        Intent intent = new Intent(context, StartReadActivity.class);
        intent.putExtra("writeId", writeId);
        intent.putExtra("authorId", authorId);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    //  获取第一章节数据
    private void getFirstChapter(int page) {
        Map<String, String> map = new HashMap<>();
        map.put("parent_id", "0");
        map.put("user_id", user_id);
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
                            getNextChapter(results.get(0).getSection_id(), false);
                        }
                    }
                });
    }

    private void getNextChapter(String parent_id, boolean isRefresh) {
        Map<String, String> map = new HashMap<>();
        map.put("parent_id", parent_id);
        map.put("user_id", user_id);
        map.put("page", page + "");
        map.put("rows", "10");
        map.put("write_id", writeId);
        map.put("write_author_id", authorId);
        IdeaApi.getApiService()
                .getArticle(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<ArticleBean>>>(this, false) {
                    @Override
                    public void onSuccess(BasicResponse<List<ArticleBean>> response) {
                        List<ArticleBean> results = response.getResult();
                        if (results.size() > 0) {
                            mAdapter.getList().add(results);
                        } else {
                            showSnackBar(mRlRead, "已经是最后一个章节");
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
            case R.id.ll_like://    章节点赞
                priseChapter();
                break;
            case R.id.ll_value:
                showToast("赞赏功能还未开通哦");
                break;
            case R.id.ll_rewrite:   //  重写
                rewrite();
                break;
            case R.id.ll_continue:  //  续写
                continueWrite();
                break;
        }
    }

    //  章节点赞
    private void priseChapter() {
        if (isLogin()) {
            if (likeStatus == 0) {  //  未点赞状态，请求点赞
                lightChapter(1);
            } else { //  取消点赞
                lightChapter(0);
            }
        } else {
            LoginActivity.start(this);
        }
    }

    //  续写
    private void continueWrite() {
        if (isLogin()) {
            if (/*vPosition == 0 &&*/ null != articleBean) {
                parent_id = articleBean.getSection_id();
            }
            WriteActivity.start(this, writeId, parent_id, /*chapter_id*/(Integer.valueOf(chapter_id) + 1) + "", WriteActivity.CONTINUE_WRITE);
        } else {
            LoginActivity.start(this);
        }
    }

    //  重写
    private void rewrite() {
        if (isLogin()) {
            String reparent_id = "000";
            if (articleBean != null) {
                reparent_id = articleBean.getParent_id();
            }
            WriteActivity.start(this, writeId, reparent_id, chapter_id, WriteActivity.REWRITE);
        } else {
            LoginActivity.start(this);
        }
    }

    private boolean isLogin() {
        return UserInfoTools.getIsLogin(this);
    }

    /**
     * 章节点赞
     */
    private void lightChapter(int status) {
        Map<String, String> map = new HashMap<>();
        map.put("section_id", section_id);
        map.put("write_id", write_id);
        map.put("user_id", user_id);
        map.put("status", String.valueOf(status));
        IdeaApi.getApiService()
                .lightChapter(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(this, false) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                        commentPage = 1;
                        likeStatus = status;
                        mIvPrise.setImageLevel(status);
                        articleBean.setLikeStatus(status);
                        int like_count = articleBean.getLike_count();
                        if (status == 0) {
                            articleBean.setLike_count(like_count - 1);
                        } else {
                            articleBean.setLike_count(like_count + 1);
                        }
                        tvLike.setText("赞" + (articleBean.getLike_count()));
                    }
                });
    }

    private void comment() {
        int commentPage = 1;
        getComment(commentPage, false);
        popupWindow.showAtLocation(mRlRead, Gravity.CENTER, 0, 0);
    }

    private void initCommentPopWindows() {
        View popView = View.inflate(this, R.layout.comment_window, null);
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //  给弹出框设置默认动画
        popupWindow.setAnimationStyle(-1);
        //  点击其他地方取消popWindows
        popupWindow.setFocusable(true);
        initCommentPopView(popView);
    }

    private void initCommentPopView(View popView) {
        Button tvComment = (Button) popView.findViewById(R.id.btn_comment);
        mEtComment = (EditText) popView.findViewById(R.id.et_comment);
        mIvDismiss = (ImageView) popView.findViewById(R.id.iv_dismiss);
        mLlDefault = (LinearLayout) popView.findViewById(R.id.ll_default);
        LinearLayout linearLayout = (LinearLayout) popView.findViewById(R.id.ll_pop_window);
        linearLayout.setOnClickListener(this);
        tvComment.setOnClickListener(this);
        mIvDismiss.setOnClickListener(this);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        mRecyclerView = (RecyclerView) popView.findViewById(R.id.rv_comment);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mCommentAdapter = new CommentAdapter(this) {
            @Override
            public void lightComment(CommentBean commentBean, ImageView imageView, int status) {
                likeComment(commentBean, imageView, status);
            }
        };
        mCommentAdapter.fillList(new ArrayList<>());
        mRecyclerView.setAdapter(mCommentAdapter);

        initPtr(popView);
    }

    //  为评论点赞
    private void likeComment(CommentBean commentBean, ImageView imageView, int status) {
        String commentId = commentBean.getComment_id();

        IdeaApi.getApiService()
                .lightComment(user_id, commentId, String.valueOf(status))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<String>>(this, false) {
                    @Override
                    public void onSuccess(BasicResponse<String> response) {
                        imageView.setImageLevel(status);
                    }
                });
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
                getComment(commentPage, true);
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
     *
     * @param page       页码
     * @param isLoadMore 是否是上拉加载数据
     */
    private void getComment(int page, boolean isLoadMore) {

        Map<String, String> map = new HashMap<>();
        map.put("section_id", section_id);
        map.put("user_id", user_id);
        map.put("page", page + "");
        map.put("rows", "10");
        IdeaApi.getApiService()
                .getComment(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<List<CommentBean>>>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse<List<CommentBean>> response) {
                        List<CommentBean> list = mCommentAdapter.getList();
                        if (response.getResult().size() < 5) {
                            mPtrFrame.setMode(PtrFrameLayout.Mode.NONE);
                        } else {
                            mPtrFrame.setMode(PtrFrameLayout.Mode.LOAD_MORE);
                        }
                        if (!isLoadMore) {
                            list.clear();
                        }
                        list.addAll(response.getResult());
                        mCommentAdapter.notifyDataSetChanged();
                        commentPage++;
                        if (list.size() == 0) {
                            TextView tvNoData = (TextView) mLlDefault.findViewById(R.id.tv_no_data);
                            tvNoData.setText("暂无评论");
                            mLlDefault.setVisibility(View.VISIBLE);
                        } else {
                            mLlDefault.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_dismiss:
                popupWindow.dismiss();
                commentPage = 1;
                break;
            case R.id.btn_comment:
                commentClick();
                break;
            case R.id.ll_collect:
                if (isLogin())
                    collect();
                else {
                    LoginActivity.start(this);
                }
                break;
            case R.id.ll_report:
                report();
                break;
            case R.id.ll_share:
                share();
                break;
            case R.id.ll_advice:
                SuggestActivity.start(this, "Read");
                mPopupWindow.dismiss();
                break;
            case R.id.ll_delete:
                if (isLogin())
                    deleteChapter(0);
                break;
            case R.id.ll_edit:
                if (isLogin())
                    editChapter();
                break;
        }
    }

    private void commentClick() {
        if (UserInfoTools.getIsLogin(this)) {
            postComment();
        } else {
            LoginActivity.start(this);
        }
    }

    private void editChapter() {
        UpdateChapterActivity.start(this, articleBean.getSection_id(), articleBean.getSection_name(), articleBean.getContent());
        mPopupWindow.dismiss();
    }

    //  分享
    private void share() {
        mPopupWindow.dismiss();
        showShare();
        // share2Moments();
    }

    //  举报
    private void report() {
        showToast("举报成功");
        mPopupWindow.dismiss();
    }

    //  添加收藏
    private void collect() {
        mPopupWindow.dismiss();
        Map<String, String> map = new HashMap<>();
        map.put("write_id", write_id);
        map.put("section_id", section_id);
        map.put("user_id", user_id);
        String status = "0";
        if ("0".equals(keepStatus)) {
            status = "1";
        }
        map.put("keep_status", status);
        IdeaApi.getApiService()
                .addCollect(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                        commentPage = 1;
                        showToast("收藏成功");
                        mEtComment.setText("");
                        getComment(1, false);
                    }
                });
    }



    @Override
    public void dismissProgress() {
        super.dismissProgress();
        mPtrFrame.refreshComplete();
    }

    //  提交评论
    private void postComment() {
        String content = mEtComment.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            showToast("请输入评论内容");
            return;
        }
        IdeaApi.getApiService()
                .comment(user_id, section_id, content)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<String>>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse<String> response) {
                        commentPage = 1;
                        showToast("评论成功");
                        mEtComment.setText("");
                        getComment(1, false);
                    }
                });
    }

    /**
     * 删除章节
     *
     * @param type
     */
    private void deleteChapter(int type) {
        Map<String, String> map = new HashMap<>();
        map.put("section_id", articleBean.getSection_id());
        map.put("user_id", user_id);
        map.put("status", String.valueOf(type));
        IdeaApi.getApiService()
                .updateChapter(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                        showToast("删除成功");
                        refreshData();
                    }
                });
    }

    //  分享
    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(IdeaApiService.WEBSITE);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("当我们阅读的时候，思考，便成了作者。\n当我们写作的时候，思考，便成了读者。");
        oks.setImageUrl(IdeaApiService.HOST + "/favicon.jpg");
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(IdeaApiService.WEBSITE);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("不一样的阅读体验");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(IdeaApiService.WEBSITE);

        // 启动分享GUI
        oks.show(this);
    }

    private void share2Friend() {    //  分享到好友
        Platform weixin = ShareSDK.getPlatform(Wechat.NAME);
        Wechat.ShareParams sp = new Wechat.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setImageUrl(IdeaApiService.HOST + "/favicon.ico");
        sp.setUrl("http://www.cypoem.com");
        sp.setText("当我们阅读的时候，思考，便成了作者。当我们写作的时候，思考，便成了读者。");
        sp.setTitle(getString(R.string.app_name));
        weixin.share(sp);


        UMPlatformData platform = new UMPlatformData(UMPlatformData.UMedia.WEIXIN_FRIENDS, UserInfoTools.getUserId(this));
        platform.setGender(UMPlatformData.GENDER.FEMALE); // optional
        platform.setWeiboId("weChatId"); // optional

        MobclickAgent.onSocialEvent(this, platform);
    }

    private void share2Moments() {   //  分享到朋友圈
        Platform weixin = ShareSDK.getPlatform(WechatMoments.NAME);
        WechatMoments.ShareParams sp = new WechatMoments.ShareParams();
        sp.setShareType(Platform.SHARE_WEBPAGE);
        sp.setUrl("http://www.cypoem.com");
        sp.setImageUrl(IdeaApiService.HOST + "/favicon.ico");
        sp.setText("当我们阅读的时候，思考，便成了作者。当我们写作的时候，思考，便成了读者。");
        sp.setTitle(getString(R.string.app_name));
        weixin.share(sp);
    }
}
