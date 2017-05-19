package com.cypoem.idea.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.cypoem.idea.R;
import java.util.List;

/**
 * Created by edianzu on 2017/5/19.
 */

public class PraiseAdapter extends BaseAdapter {

    List<String> mList;
    private Context context;

    public List<String> getList() {
        return mList;
    }

    public void setList(List<String> list) {
        mList = list;
    }

    public PraiseAdapter(Context context) {
        this.context = context;
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
        return mList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PraiseViewHolder holder;
        if(convertView==null){
            holder=new PraiseViewHolder();
            convertView= View.inflate(context,R.layout.item_praise,null);
            holder.mTvString= (TextView) convertView.findViewById(R.id.tv_string);
            convertView.setTag(holder);
        }else {
            holder= (PraiseViewHolder) convertView.getTag();
        }
        holder.mTvString.setText(mList.get(position));
        return convertView;
    }

    public static class PraiseViewHolder {
        TextView mTvString;
    }
}
