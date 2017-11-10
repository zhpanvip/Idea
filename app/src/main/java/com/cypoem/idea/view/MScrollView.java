package com.cypoem.idea.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * Created by zhpan on 2017/11/10.
 * Description:
 */

public class MScrollView extends NestedScrollView {

    public interface OnScrollChangedListener {
        void onScrollChanged(MScrollView scrollView, int y);
    }

    private OnScrollChangedListener mOnScrollChangedListener;

    public MScrollView(Context context) {
        super(context);
    }

    public MScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnScrollChangedListener(OnScrollChangedListener scrollChangedListener){
        mOnScrollChangedListener=scrollChangedListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldx, int oldy) {
        super.onScrollChanged(x, y, oldx, oldy);
        if(mOnScrollChangedListener!=null)
        mOnScrollChangedListener.onScrollChanged(this,y);
    }
}
