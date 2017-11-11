package com.cypoem.idea.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;

import com.airong.core.recycler.BaseAdapter;
import com.airong.core.recycler.BaseHolder;
import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhpan on 2017/11/11.
 */

public class AdapterAuthorHList extends BaseAdapter<UserBean,AdapterAuthorHList.AuthorHHolder> {

    public AdapterAuthorHList(Context context) {
        super(context);
        List<UserBean> list=new ArrayList<>();
        list.add(new UserBean());
        list.add(new UserBean());
        list.add(new UserBean());
        list.add(new UserBean());
        list.add(new UserBean());
        fillList(list);
    }

    @Override
    public AuthorHHolder createCustomViewHolder(ViewGroup parent, int viewType) {
        return new AuthorHHolder(parent,R.layout.item_find_author);
    }

    @Override
    public void bindCustomViewHolder(AuthorHHolder holder, int position) {

    }

    @Override
    public int getCustomViewType(int position) {
        return 0;
    }

    public static class AuthorHHolder extends BaseHolder{

        public AuthorHHolder(ViewGroup parent, @LayoutRes int resId) {
            super(parent, resId);
        }
    }
}
