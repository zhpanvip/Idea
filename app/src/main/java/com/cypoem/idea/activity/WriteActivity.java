package com.cypoem.idea.activity;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.cypoem.idea.R;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.utils.UserInfoTools;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WriteActivity extends BaseActivity {

    @BindView(R.id.btn_complete)
    Button btnComplete;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.tv_rewrite)
    TextView tvRewrite;
    @BindView(R.id.tb_rewrite)
    ToggleButton tbRewrite;
    @BindView(R.id.rl_can_rewrite)
    RelativeLayout rlCanRewrite;
    @BindView(R.id.et_content)
    EditText etContent;
    private String title;
    private String content;
    private String parent_id = "000";
    private String chapter_id;
    private String write_id;
    private String reStatus = "0";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setToolBarTitle("创作作品");
        setData();
        setListener();
    }

    private void setListener() {
        tbRewrite.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            if (isChecked) {
                reStatus = "0";
            } else {
                reStatus = "1";
            }
        });
    }


    private void setData() {
        getSubTitle().setText("保存草稿");
        getSubTitle().setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        chapter_id = intent.getStringExtra("chapter_id");
        write_id = intent.getStringExtra("write_id");
        if (!TextUtils.isEmpty(intent.getStringExtra("parent_id"))) {
            parent_id = intent.getStringExtra("parent_id");
        }
    }

    public static void start(Context context, String write_id, String parent_id, String chapter_id) {
        Intent intent = new Intent(context, WriteActivity.class);
        intent.putExtra("write_id", write_id);
        intent.putExtra("parent_id", parent_id);
        intent.putExtra("chapter_id", chapter_id);
        context.startActivity(intent);
    }

    @OnClick({R.id.btn_complete})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.btn_complete:
                complete();
                break;
        }
    }

    private void complete() {
        if (isEmpty()) {
            return;
        }
        postArticle();
    }

    private void postArticle() {
        Map<String, String> adviceMap = new HashMap<>();
        adviceMap.put("content", content);
        adviceMap.put("parent_id", parent_id);
        adviceMap.put("user_id", UserInfoTools.getUser(this).getUserId());
        adviceMap.put("write_id", write_id);
        adviceMap.put("chapter_id", chapter_id);
        adviceMap.put("section_name", title);
        adviceMap.put("upStatus", "0");
        adviceMap.put("reStatus", reStatus);
        IdeaApi.getApiService()
                .addChapter(adviceMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<String>>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse<String> response) {
                        showToast(response.getResult());
                        finish();
                    }
                });
    }

    private boolean isEmpty() {
        title = etTitle.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            showToast("您还没有添加章节标题呢");
            return true;
        }
        content = etContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            showToast("文章内容不能为空哦");
            return true;
        }
        return false;
    }
}
