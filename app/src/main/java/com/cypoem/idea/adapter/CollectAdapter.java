package com.cypoem.idea.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import com.airong.core.adapter.CommonAdapter;
import com.airong.core.adapter.CommonViewHolder;
import com.airong.core.utils.ImageLoaderUtil;
import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.CollectBean;
import com.cypoem.idea.module.bean.OpusBean;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.net.IdeaApiService;

import java.util.List;

/**
 * Created by zhpan on 2017/5/20.
 *
 */

public class CollectAdapter extends CommonAdapter<OpusBean> {
    public CollectAdapter(Context context, int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, OpusBean collectBean, List<OpusBean> list) {
        ImageView imageView = holder.getView(R.id.iv_collect);
        TextView tvTitle=holder.getView(R.id.tv_title);
        TextView tvSubTitle=holder.getView(R.id.tv_sub_title);
        TextView tvTime=holder.getView(R.id.tv_time);
        TextView tvDetails=holder.getView(R.id.tv_details);
        TextView tvOverride=holder.getView(R.id.tv_override);
        TextView tvContinue=holder.getView(R.id.tv_continue);
        TextView tvEssay= holder.getView(R.id.tv_essay);

        ImageLoaderUtil.loadRoundImg(imageView,IdeaApiService.HOST+collectBean.getPic(),R.drawable.background);
        tvTitle.setText(collectBean.getWrite_name());
       // tvSubTitle.setText(collectBean.get());
        String date = collectBean.getDelivery_time();
        tvTime.setText(date.substring(0,10)+" /"+"作者");
        tvDetails.setText("赞"+collectBean.getLike_count()+"/阅读"+collectBean.getRead_count()+"/章节"+collectBean.getSection_count());

        //String[] split = collectBean.getType().split(" ");

    }
}
