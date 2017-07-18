package com.cypoem.idea.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.cypoem.idea.R;

/**
 * Created by zhpan on 2017/5/31.
 * Description: 自定义性别View
 */

public class SexView extends View {
    private int maleColor;
    private int femaleColor;
    private int mWidth;
    private int mHeight;
    private int mRadius1;
    private int mRadius2;

    //  圆心坐标
    private float mPieCenterX;
    private float mPieCenterY;
    private RectF pieOval; //圆环的矩形区域
    private float mStrokeWidth; //  圆环宽度
    private Paint mPaint;
    private Context mContext;
    private int centerColor;

    public int getCenterColor() {
        return centerColor;
    }

    public void setCenterColor(int centerColor) {
        this.centerColor = centerColor;
    }

    private double malePercent = 0.5;

    public SexView(Context context) {
        super(context);
        init(context);
    }


    public SexView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }

    public SexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SexView);
        maleColor = typedArray.getColor(R.styleable.SexView_maleColor, Color.parseColor("#1296DB"));
        femaleColor = typedArray.getColor(R.styleable.SexView_femaleColor, Color.parseColor("#D4237A"));
        centerColor=getResources().getColor(R.color.item_color);
        typedArray.recycle();
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(maleColor);

        pieOval = new RectF();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = getHeight();
        mWidth = getWidth();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mRadius1 = Math.min(mHeight, mWidth) / 2;
        mPieCenterX = mWidth / 2;
        mPieCenterY = mHeight / 2;
        mRadius2 = mRadius1 - mRadius1 / 3;
        mStrokeWidth = mRadius1 - mRadius2;

        pieOval.left = mPieCenterX - mRadius1;
        pieOval.top = mPieCenterY - mRadius1;
        pieOval.right = mPieCenterX + mRadius1;
        pieOval.bottom = mPieCenterY + mRadius1;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCircle(canvas);
    }

    private void drawCircle(Canvas canvas) {
        // 画最外层圆
        //mPaint.setColor(Color.parseColor("#f1f1f1"));
        //canvas.drawCircle(mPieCenterX, mPieCenterY, mRadius1, mPaint);
        drawArc(canvas);
        // 画最内层圆
        mPaint.setColor(getResources().getColor(R.color.item_color));
        canvas.drawCircle(mPieCenterX, mPieCenterY, mRadius2, mPaint);
    }

    //  画扇形圆环和百分比
    private void drawArc(Canvas canvas) {


        float startAngle = 0; //  扇环起始角度
        mPaint.setStrokeWidth(mStrokeWidth);    //  设置扇环宽度

        float sweep = (float) (malePercent * 360);
        mPaint.setColor(maleColor);
        //  画扇形圆环
        canvas.drawArc(pieOval, startAngle, sweep + 1, true, mPaint);
        startAngle += (malePercent) * 360;
        sweep = (float) ((1 - malePercent) * 360);
        mPaint.setColor(femaleColor);
        //  画扇形圆环
        canvas.drawArc(pieOval, startAngle, sweep + 1, true, mPaint);
    }

    public int getMaleColor() {
        return maleColor;
    }

    public void setMaleColor(int maleColor) {
        this.maleColor = maleColor;
    }

    public int getFemaleColor() {
        return femaleColor;
    }

    public void setFemaleColor(int femaleColor) {
        this.femaleColor = femaleColor;
    }

    public double getMalePercent() {
        return malePercent;
    }

    public void setMalePercent(double malePercent) {
        this.malePercent = malePercent;
        invalidate();
    }
}
