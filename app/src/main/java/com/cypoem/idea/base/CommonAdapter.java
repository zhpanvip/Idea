package com.cypoem.idea.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {
	protected Context context;
	protected LayoutInflater inflater;
	protected List<T> datas;
	protected int layoutId;

	public CommonAdapter(Context context, List<T> datas, int layoutId) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.datas = datas;
		this.layoutId = layoutId;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public T getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CommonViewHolder holder = CommonViewHolder.get(context, convertView, parent, layoutId, position);
		convert(holder, getItem(position), datas);
		return holder.getmConvertView();
	}

	public abstract void convert(CommonViewHolder holder, T t, List<T> datas);

}
