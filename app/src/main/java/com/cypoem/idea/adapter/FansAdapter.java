package com.cypoem.idea.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.airong.core.adapter.CommonAdapter;
import com.airong.core.adapter.CommonViewHolder;
import com.airong.core.utils.ImageLoaderUtil;
import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.FansBean;
import com.cypoem.idea.net.IdeaApiService;

import java.util.List;

/**
 * Created by zhpan on 2017/5/21.
 *
 */

public class FansAdapter extends CommonAdapter<FansBean> {

    public FansAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, FansBean fansBean, List<FansBean> list) {
        ImageView ivHead=holder.getView(R.id.iv_head_pic);
        TextView tvName=holder.getView(R.id.tv_name);
        TextView tvSign=holder.getView(R.id.tv_sign);
        TextView tvArticle=holder.getView(R.id.tv_article);

        ImageLoaderUtil.loadCircleImg(ivHead, IdeaApiService.HOST+fansBean.getIcon(),R.drawable.head_pic);
        tvName.setText(fansBean.getPen_name());
        tvSign.setText(fansBean.getIntroduction());
        tvArticle.setText("发起"+fansBean.getEnjoy_count()+"/参与"+fansBean.getMyWatchCount()+"/原创"+fansBean.getWatchMeCount());
    }
}
