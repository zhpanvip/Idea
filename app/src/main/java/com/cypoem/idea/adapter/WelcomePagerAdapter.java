package com.cypoem.idea.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.cypoem.idea.R;
import com.cypoem.idea.activity.MainActivity;
import com.cypoem.idea.activity.WelcomeActivity;

/**
 * Created by zhpan on 2017/12/5.
 */

public class WelcomePagerAdapter extends PagerAdapter {
    private int[] layouts;
    private LayoutInflater mInflater;
    private Context mContext;

    public WelcomePagerAdapter(Context context, int[] layouts) {
        this.mContext = context;
        this.layouts = layouts;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return layouts.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mInflater.inflate(layouts[position], null);
        container.addView(view);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_welcome);
        int resId = mContext.getResources().getIdentifier("page" + (position + 1), "drawable", mContext.getPackageName());
        imageView.setBackgroundResource(resId);
        if (position == layouts.length - 1) {
            Button btn = (Button) view.findViewById(R.id.btn_welcome);
            btn.setOnClickListener(v -> {
                MainActivity.start(mContext);
                ((WelcomeActivity) mContext).finish();
            });
        }
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
