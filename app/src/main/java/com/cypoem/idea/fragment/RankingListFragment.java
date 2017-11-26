package com.cypoem.idea.fragment;

import android.os.Bundle;
import com.cypoem.idea.R;

public class RankingListFragment extends BaseFragment {
    public static final int GET=1;
    public static final int DONATE=0;

    public static RankingListFragment getFragment(int type) {
        RankingListFragment fragment = new RankingListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ranking_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setRefreshLayout(true);
    }
}
