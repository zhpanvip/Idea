package com.cypoem.idea.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import com.airong.core.adapter.CommonAdapter;
import com.airong.core.adapter.CommonViewHolder;
import com.airong.core.utils.ImageLoaderUtil;
import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.CollectBean;
import java.util.List;

/**
 * Created by zhpan on 2017/5/20.
 *
 */

public class CollectAdapter extends CommonAdapter<CollectBean> {
    public CollectAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, CollectBean collectBean, List<CollectBean> list) {
        ImageView imageView = holder.getView(R.id.iv_collect);
        TextView tvTitle=holder.getView(R.id.tv_title);
        TextView tvSubTitle=holder.getView(R.id.tv_sub_title);
        TextView tvTime=holder.getView(R.id.tv_time);
        TextView tvDetails=holder.getView(R.id.tv_details);
        TextView tvOverride=holder.getView(R.id.tv_override);
        TextView tvContinue=holder.getView(R.id.tv_continue);
        TextView tvEssay= holder.getView(R.id.tv_essay);

        ImageLoaderUtil.loadRoundImg(imageView,collectBean.getPicUrl(),R.drawable.background);
        tvTitle.setText(collectBean.getTitle());
        tvSubTitle.setText(collectBean.getSubTitle());
        tvTime.setText(collectBean.getTime()+" /"+collectBean.getAuther());
        tvDetails.setText("赞"+collectBean.getPraiseNum()+"/阅读"+collectBean.getReadNum()+"/作品"+collectBean.getArticlesNum());
    }
}
