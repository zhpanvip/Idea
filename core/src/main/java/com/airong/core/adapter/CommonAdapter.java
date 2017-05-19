package com.airong.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class CommonAdapter<T> extends BaseAdapter {
	protected Context context;
	protected LayoutInflater inflater;
	protected List<T> mList;
	protected int layoutId;

	public List<T> getList() {
		return mList;
	}

	public void setList(List<T> mList) {
		this.mList = mList;
	}

	public CommonAdapter(Context context, int layoutId) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.layoutId = layoutId;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public T getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		CommonViewHolder holder = CommonViewHolder.get(context, convertView, parent, layoutId, position);
		convert(holder, getItem(position), mList);
		return holder.getConvertView();
	}

	public abstract void convert(CommonViewHolder holder, T t, List<T> list);

}
