package com.cypoem.idea.fragment;

import android.os.Bundle;

import com.cypoem.idea.R;


public class FindNewFragment extends BaseFragment {

    public static FindNewFragment getFragment() {
        return new FindNewFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find_new;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }
}
