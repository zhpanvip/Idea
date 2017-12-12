package com.cypoem.idea.adapter;

import android.content.Context;

import com.airong.core.recycler.BaseAdapter;
import com.airong.core.recycler.BaseHolder;
import com.cypoem.idea.module.bean.SearchBaseBean;
import com.cypoem.idea.module.bean.SearchBean;

import java.util.List;

/**
 * Created by zhpan on 2017/12/12.
 */

public abstract class SearchAdapter<T extends SearchBaseBean,VH extends BaseHolder> extends BaseAdapter<T,VH> {

    public SearchAdapter(Context context) {
        super(context);
    }

    public SearchAdapter(Context context, List<T> list) {
        super(context, list);
    }
}
