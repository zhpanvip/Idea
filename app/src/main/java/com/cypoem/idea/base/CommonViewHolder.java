package com.cypoem.idea.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CommonViewHolder {
	private SparseArray<View> mViews;
	private int position;
	private View mConvertView;
	
	public CommonViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
		this.position = position;
		this.mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		mConvertView.setTag(this);
	}
	
	public View getmConvertView() {
		return mConvertView;
	}
	
	public static CommonViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
		if (convertView == null) {
			return new CommonViewHolder(context, parent, layoutId, position);
		} else {
			CommonViewHolder commonViewHolder = (CommonViewHolder) convertView.getTag();
			commonViewHolder.position = position;
			return commonViewHolder;
		}
	}
	
	public int getPosition() {
		return position;
	}
	
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}
}
