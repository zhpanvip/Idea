package com.cypoem.idea.adapter;

import android.content.Context;
import android.widget.TextView;
import com.airong.core.adapter.CommonAdapter;
import com.airong.core.adapter.CommonViewHolder;
import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.NotifyBean;

import java.util.List;

/**
 * Created by edianzu on 2017/5/19.
 *
 */

public class NotifyAdapter extends CommonAdapter<NotifyBean> {


    public NotifyAdapter(Context context, int layoutId) {
        super(context,layoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, NotifyBean notifyBean, List<NotifyBean> list) {
       TextView tvNotifyTitle=holder.getView(R.id.tv_notify_title);
        TextView tvNotifyContent=holder.getView(R.id.tv_notify_content);
        TextView tvNotifyTime=holder.getView(R.id.tv_notify_time);

        tvNotifyTitle.setText(notifyBean.getTitle());
        tvNotifyContent.setText(notifyBean.getContent());
        tvNotifyTime.setText(notifyBean.getTitle());
    }
}
