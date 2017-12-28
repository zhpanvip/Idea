package com.cypoem.idea.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cypoem.idea.R;
import com.cypoem.idea.activity.BaseActivity;
import com.cypoem.idea.activity.CreateCircleActivity;
import com.cypoem.idea.activity.CreateEveryDayActivity;
import com.cypoem.idea.activity.CreateStoryActivity;
import com.cypoem.idea.activity.LoginActivity;
import com.cypoem.idea.activity.PublishActivity;
import com.cypoem.idea.activity.SuggestActivity;
import com.cypoem.idea.utils.UserInfoTools;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhpan on 2017/4/21.
 */

public class WritingFragment extends BaseFragment {
    @BindView(R.id.toolbar_subtitle)
    TextView toolbarSubtitle;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        toolbarTitle.setText("发布作品");
        toolbarSubtitle.setVisibility(View.VISIBLE);
        toolbarSubtitle.setText("创作说明");
    }

    @OnClick({R.id.fl_create_circle, R.id.fl_person_story, R.id.fl_everyday_sentence, R.id.fl_public_story})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_create_circle:
                CreateCircleActivity.start(getContext(), CreateCircleActivity.class);
                break;
            case R.id.fl_person_story:
                CreateStoryActivity.start(getContext(), CreateStoryActivity.class);
                break;
            case R.id.fl_everyday_sentence:
                CreateEveryDayActivity.start(getContext(), CreateEveryDayActivity.class);
                break;
            case R.id.fl_public_story:
                CreateStoryActivity.start(getContext(), CreateStoryActivity.class);
                break;
        }
    }
}
