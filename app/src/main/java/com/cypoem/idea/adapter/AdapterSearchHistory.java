package com.cypoem.idea.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cypoem.idea.R;
import com.cypoem.idea.activity.SearchActivity;
import com.cypoem.idea.module.bean.SearchHistoryBean;
import com.cypoem.idea.utils.SearchHistoryDao;

import java.util.List;

/**
 * Created by zhpan on 2016/10/27.
 */

public class AdapterSearchHistory extends BaseAdapter {

    List<SearchHistoryBean> mList;

    Context mContext;

    public AdapterSearchHistory(Context context) {
        mContext = context;
    }

    public void setList(List<SearchHistoryBean> list) {
        mList = list;
    }

    public List<SearchHistoryBean> getList() {
        return mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        HotViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_search_history, null);
            holder = new HotViewHolder();

            holder.mTextView = (TextView) convertView.findViewById(R.id.tv_name);
            holder.mImageView = (ImageView) convertView.findViewById(R.id.iv_flag);

            convertView.setTag(holder);
        } else {
            holder = (HotViewHolder) convertView.getTag();
        }

        holder.mTextView.setText(mList.get(position).getItem());

        //SearchHotBean searchHotBean = mList.get(position);

        //holder.mTextView.setText(mList.get(position).getSearchText());

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SearchHistoryDao historyDao = new SearchHistoryDao(mContext);

                int id = mList.get(position).getId();
                if (historyDao.delHistoryById(id)) {
                    Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                }
                SearchActivity activityBeforeSearch = (SearchActivity) mContext;
                activityBeforeSearch.getSearchHistory();
            }
        });

        return convertView;
    }

    static class HotViewHolder {

        TextView mTextView;

        ImageView mImageView;
    }
}
