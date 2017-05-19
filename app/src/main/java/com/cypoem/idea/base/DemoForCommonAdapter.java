package com.cypoem.idea.base;

import android.content.Context;
import com.cypoem.idea.module.bean.Contact;
import java.util.List;

public class DemoForCommonAdapter extends CommonAdapter<Contact> {

	public DemoForCommonAdapter(Context context, List<Contact> datas, int layoutId) {
		super(context, datas, layoutId);
	}

	@Override
	public void convert(CommonViewHolder holder, Contact t, List<Contact> datas) {
//		TextView tvId = holder.getView(R.id.tv_id);
//		TextView tvName = holder.getView(R.id.tv_name);
//		TextView tvAge = holder.getView(R.id.tv_age);
//
//		tvId.setText(t.getGendle());
//		tvName.setText(t.getUserName());
//		tvAge.setText(String.valueOf(t.getAge()));

	}
}
