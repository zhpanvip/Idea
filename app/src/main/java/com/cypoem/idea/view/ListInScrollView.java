package com.cypoem.idea.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.airong.core.utils.DensityUtils;
import com.cypoem.idea.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：可以在ScrollView中的ListView
 *
 * @author xuhao
 * @version 1.0
 * @corporation 深圳市思迪信息科技有限公司
 * @date 2015-03-02
 */
public class ListInScrollView extends LinearLayout {

    private Context context;

    private int dividerColor = 0x000000;

    private float dividerHeight = 0;

    private BaseAdapter adapter = null;

    private List<View> itemList = new ArrayList<View>();

    private OnItemClickListener itemClickListener = null;

    private static final int MAX_ITEM_NUMS = 200;

    private boolean needBottomDivider = false;

    public ListInScrollView(Context context) {
        super(context);
        init(context);
    }

    public ListInScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ListInScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    //初始化自身
    private void init(Context context) {
        this.context = context;
        this.setOrientation(LinearLayout.VERTICAL);//纵向排列
        dividerHeight = 0.5f;
        dividerColor = 0xff999999;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setNeedBottomDivider(boolean mNeedBottomDivider) {
        needBottomDivider = mNeedBottomDivider;
    }

    private boolean isNeedDefaultPressColor=true;

    public void setNeedDefaultPressColor(boolean needDefaultPressColor){
        isNeedDefaultPressColor=needDefaultPressColor;
    }


    private void initItem() {
        if (adapter != null && adapter.getCount() >= 0 && adapter.getCount() <= MAX_ITEM_NUMS) {
            int count = adapter.getCount();
            //When count less than holdView list
            if (count < this.getChildCount()) {
                for (int i = 0; i < this.getChildCount(); i++) {
                    View cacheView = itemList.size() > i ? itemList.get(i) : null;
                    ViewGroup childView = (ViewGroup) this.getChildAt(i);
                    if (childView.indexOfChild(cacheView) >= -1) {
                        this.removeViewAt(i);
                        if (itemList.size() > i) {
                            itemList.remove(i);
                        }
                        i--;
                    }
                }
            }
            for (int i = 0; i < count; i++) {
                View cacheView = itemList.size() > i ? itemList.get(i) : null;
                View view = (View) adapter.getView(i, cacheView, this);
                if (view != null &&  !itemList.contains(view)) {
                    View ly;
                    if (needBottomDivider) {
                        ly = getDivider(view);
                    } else {
                        if (i != count - 1) {
                            ly = getDivider(view);
                        } else {
                            ly = view;
                            lastBottomView = ly;
                        }
                    }
                    itemList.add(view);
                    setListener(view, i);
                    if (mLayoutId != NO_LINE && isNeedDefaultPressColor) {
                        view.setBackgroundResource(R.drawable.theme_blue_selector_list_item_gray);
                    }
                    this.addView(ly);
                } else if (view != null && itemList.contains(view)) {
                    if(isLastBottomView(view)){
                        if (i != count - 1) {
                            Object obj=view.getTag();
                            this.removeView(view);
                            itemList.remove(view);
                            view = getDivider(view);
                            itemList.add(view);
                            if(obj!=null){
                                view.setTag(obj);
                            }
                            this.addView(view,i);

                        }

                    }else {
                        view.invalidate();
                    }
                }
            }
        } else if (adapter != null && adapter.getCount() > MAX_ITEM_NUMS) {
            throw new IllegalArgumentException(
                    "This is CustomListView can not load too many item, max over flow");
        }
    }

    private View lastBottomView;

    private boolean isLastBottomView(View view){
        return !needBottomDivider && lastBottomView==view;
    }

    private void setListener(final View view, final int position) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter != null) {
                    onItemClick(view, position, adapter.getItem(position));
                }
            }
        });
    }

    public interface OnItemClickListener {

        public void onItemClick(View view, int position, Object item);
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        this.itemClickListener = l;
    }

    private void onItemClick(View view, int position, Object item) {
        if (itemClickListener != null) {
            itemClickListener.onItemClick(view, position, item);
        }
    }

    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
        try {
            adapter.unregisterDataSetObserver(mObserver);
        } catch (Exception e) {
        }
        adapter.registerDataSetObserver(mObserver);
    }

    private View getDivider(View view) {

        if (mLayoutId == NO_LINE) {
            return view;
        }

        LinearLayout linearLayout1 = (LinearLayout) LayoutInflater.from(context)
                .inflate(mLayoutId, null);

        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        //linearLayout.addView(linearLayout1, 0);
        linearLayout.addView(view, 0);
        linearLayout.addView(linearLayout1, 1);

        return linearLayout;

    }

    public final static int NO_LINE = 0;

    private int mLayoutId = R.layout.dim;

    public void setDividerLayoutId(int dividerLayoutId) {
        mLayoutId = dividerLayoutId;
    }

    public void setDividerHeight(float height) {
        dividerHeight = DensityUtils.px2dp(context, height);
    }

    public void setDividerColor(int color) {
        dividerColor = color;
    }

    private DataSetObserver mObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            initItem();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();
            initItem();
        }
    };
}
