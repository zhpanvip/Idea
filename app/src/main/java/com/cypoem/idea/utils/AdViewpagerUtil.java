package com.cypoem.idea.utils;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.airong.core.utils.ImageLoaderUtil;
import com.bumptech.glide.Glide;
import com.cypoem.idea.R;

import java.util.List;

/**
 * Created by divine on 16/11/17.
 * Description 轮播图控件
 */

public class AdViewpagerUtil {
    private Context context;

    private ViewPager viewPager;

    private ImageViewPagerAdapter mimageViewPagerAdapter;

    private ImageView[] mImageViews;

    private String[] urls;

    private LinearLayout dotlayout;

    private ImageView[] dotViews;

    private boolean isRight = true; // 判断viewpager是不是向右滑动

    private int lastPosition = 1; // 记录viewpager从哪个页面滑动到当前页面，从而区分滑动方向

    private int autoIndex = 1; // 自动轮询时自增坐标，能确定导航圆点的位置

    private int currentIndex = 0; //当前item

    private int delayTime = 5000; //自动轮播的时间间隔

    private int imgsize = 0; //图片的数量，item的数量

    private boolean isLoop = false;//轮播开关

    private OnAdPageChangeListener onAdPageChangeListener; //pagechange回调

    private OnAdItemClickListener onAdItemClickListener; //点击事件回调

    private int dotsize = 8; //小圆点的大小宽度

    private int dotoffset = 4; //小圆点的间距

    /**
     * 不带小圆点
     */
    public AdViewpagerUtil(Context context, ViewPager viewPager, int delayTime) {
        this.context = context;
        this.viewPager = viewPager;

        this.delayTime = delayTime;
    }

    /**
     * 有小圆点
     */
    public AdViewpagerUtil(Context context, ViewPager viewPager, LinearLayout dotlayout,
                           int dotsize, int dotoffset, int delayTime) {
        this.context = context;
        this.viewPager = viewPager;
        this.dotlayout = dotlayout;
        this.dotsize = dotsize;

        this.delayTime = delayTime;
    }

    private List<String> forwardUrl;

    public void setData(String[] urls,List<String> forwardUrl){
        this.urls = urls;
        this.forwardUrl=forwardUrl;
        initVps();
    }

    /**
     * 有小圆点
     */
//    public AdViewpagerUtil(Context context, ViewPager viewPager, LinearLayout dotlayout,
//            int dotsize, int dotoffset, List<BannerBean> bannerBeanList, int delayTime) {
//        this.context = context;
//        this.viewPager = viewPager;
//        this.dotlayout = dotlayout;
//        this.dotsize = dotsize;
//        this.urls = new String[bannerBeanList.size()];
//        for (int i = 0; i < bannerBeanList.size(); i++) {
//            this.urls[i] = bannerBeanList.get(i).getImgUrl();
//        }
//    }

    public void setOnAdPageChangeListener(OnAdPageChangeListener onAdPageChangeListener) {
        this.onAdPageChangeListener = onAdPageChangeListener;
    }

    public void setOnAdItemClickListener(OnAdItemClickListener onAdItemClickListener) {
        this.onAdItemClickListener = onAdItemClickListener;
    }

    private void initAdimgs(String[] urls) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        int length=urls.length;
        if(urls.length==1){
            length=1;
        }else if (urls.length>1) {
            length = urls.length + 2;
        }
        mImageViews = new ImageView[length];
        for (int i = 0; i < length; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageViews[i] = imageView;
        }
        setImg(length, urls);
    }

    private void setImg(int length, String[] urls) {
        if(urls.length==1){
            Glide.with(context).load(urls[0]).placeholder(R.drawable.background)
                    .into(mImageViews[0]);
        }else if (urls.length > 1) {
            imgsize = length;
            for (int i = 0; i < length; i++) {
                if (i < length - 2) {
                    final int index = i;
                   /* Glide.with(context).load(urls[i]).placeholder(R.drawable.background)
                            .into(mImageViews[i + 1]);*/

                    ImageLoaderUtil.loadImg(mImageViews[i+1],urls[i],R.drawable.background);
                    mImageViews[i + 1].setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (onAdItemClickListener != null) {
                                onAdItemClickListener.onItemClick(mImageViews[index + 1], (forwardUrl!=null &&forwardUrl.size()>index)?forwardUrl.get(index):"");
                            }
                        }
                    });
                }
            }
            ImageLoaderUtil.loadImg(mImageViews[0],urls[urls.length-1],R.drawable.background);
            ImageLoaderUtil.loadImg(mImageViews[length - 1],urls[0],R.drawable.background);
           /* Glide.with(context).load(urls[urls.length - 1]).placeholder(R.drawable.background)
                    .into(mImageViews[0]);
            Glide.with(context).load(urls[0]).placeholder(R.drawable.background)
                    .into(mImageViews[length - 1]);*/
        }
    }

    public void initVps() {
        initAdimgs(urls);
        initDots(urls.length);
        mimageViewPagerAdapter = new ImageViewPagerAdapter(context, mImageViews);
        viewPager.setAdapter(mimageViewPagerAdapter);
        viewPager.setOffscreenPageLimit(mImageViews.length);
        if(urls.length<=1){
            viewPager.setOnTouchListener(null);
            viewPager.setOnPageChangeListener(null);
        }else {
            startLoopViewPager();
            viewPager.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                        case MotionEvent.ACTION_MOVE:
                            isLoop = true;
                            stopLoopViewPager();
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            isLoop = false;
                            startLoopViewPager();
                        default:
                            break;
                    }
                    return false;
                }
            });
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageScrollStateChanged(int arg0) {
                    if (isRight) {
                        if (arg0 != 1) {
                            if (lastPosition == 0) {
                                viewPager.setCurrentItem(imgsize - 2, false);
                            } else if (lastPosition == imgsize - 1) {
                                viewPager.setCurrentItem(1, false);
                            }
                        }
                    }

                    if (onAdPageChangeListener != null) {
                        onAdPageChangeListener.onPageScrollStateChanged(arg0);
                    }
                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {
                    if (!isRight) {
                        if (arg1 < 0.01) {
                            if (arg0 == 0) {
                                viewPager.setCurrentItem(imgsize - 2, false);
                            } else if (arg0 == imgsize - 1) {
                                viewPager.setCurrentItem(1, false);
                            }
                        }
                    }
                    if (onAdPageChangeListener != null) {
                        onAdPageChangeListener.onPageScrolled(arg0, arg1, arg2);
                    }
                }

                @Override
                public void onPageSelected(int arg0) {
                    autoIndex = arg0;
                    if (lastPosition < arg0 && lastPosition != 0) {
                        isRight = true;
                    } else if (lastPosition == imgsize - 1) {
                        isRight = true;
                    }
                    if (lastPosition > arg0 && lastPosition != imgsize - 1) {
                        isRight = false;
                    } else if (lastPosition == 0) {
                        isRight = false;
                    }
                    lastPosition = arg0;

                    if (arg0 == 0) {
                        currentIndex = imgsize - 2;
                    } else if (arg0 == imgsize - 1) {
                        currentIndex = 1;
                    } else {
                        currentIndex = arg0;
                    }

                    for (int i = 0; i < dotViews.length; i++) {
                        if (i == currentIndex - 1) {
                            dotViews[i].setSelected(true);
                        } else {
                            dotViews[i].setSelected(false);
                        }
                    }

                    if (onAdPageChangeListener != null) {
                        onAdPageChangeListener.onPageSelected(arg0);
                    }
                }

            });
            viewPager.setCurrentItem(1);// 初始化时设置显示第一页（ViewPager中索引为1）
        }
    }

    public void initDots(int length) {
        if (dotlayout == null) {
            return;
        }
        dotlayout.removeAllViews();
        if(length<=1){
            return;
        }
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(dip2px(context, dotsize),
                dip2px(context, dotsize));
        mParams.setMargins(dip2px(context, dotoffset), 0, dip2px(context, dotoffset),
                0);//设置小圆点左右之间的间隔

        dotViews = new ImageView[length];

        for (int i = 0; i < length; i++) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(mParams);
            imageView.setImageResource(R.drawable.red_dot);
            if (i == 0) {
                imageView.setSelected(true);//默认启动时，选中第一个小圆点
            } else {
                imageView.setSelected(false);
            }
            dotViews[i] = imageView;//得到每个小圆点的引用，用于滑动页面时，（onPageSelected方法中）更改它们的状态。
            dotlayout.addView(imageView);//添加到布局里面显示
        }
    }

    Handler handler = new Handler();

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // 要做的事情
            if (viewPager.getChildCount() > 1) {
                handler.postDelayed(this, delayTime);
                autoIndex++;
                viewPager.setCurrentItem(autoIndex % imgsize, true);
            }
        }
    };

    private void startLoopViewPager() {
        if (!isLoop && viewPager != null) {
            handler.postDelayed(runnable, delayTime);// 每两秒执行一次runnable.
            isLoop = true;
        }

    }

    public void stopLoopViewPager() {
        if (isLoop && viewPager != null) {
            handler.removeCallbacks(runnable);
            isLoop = false;
        }
    }

    public interface OnAdItemClickListener {

        void onItemClick(View v, String forwardUrl);
    }

    public interface OnAdPageChangeListener {

        void onPageScrollStateChanged(int arg0);

        void onPageScrolled(int arg0, float arg1, int arg2);

        void onPageSelected(int arg0);
    }

    public int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    public static class ImageViewPagerAdapter extends PagerAdapter {
        private ImageView[] imageViews;
        private int size;
        private Context context;

        public ImageViewPagerAdapter(Context context, ImageView[] imageViews) {
            this.context = context;
            this.imageViews = imageViews;
            size = imageViews.length;
        }

        @Override
        public int getCount() {
            return imageViews.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//		((ViewPager) container).removeView((View) object);// 完全溢出view,避免数据多时出现重复现象
            container.removeView(imageViews[position]);//删除页卡
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViews[position], 0);
            return imageViews[position];
        }
    }

}
