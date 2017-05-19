package com.airong.core.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * ����ͼƬ�ؼ� ͼƬ�������
 *
 * @author clifford
 */
public class PinchImageView extends ImageView {


    ////////////////////////////////���ò���////////////////////////////////

    /**
     * ͼƬ���Ŷ���ʱ��
     */
    public static final int SCALE_ANIMATOR_DURATION = 200;

    /**
     * ���Զ���˥������
     */
    public static final float FLING_DAMPING_FACTOR = 0.9f;

    /**
     * ͼƬ���Ŵ����
     */
    private static final float MAX_SCALE = 4f;


    ////////////////////////////////������////////////////////////////////

    /**
     * ������¼�
     *
     * @see #setOnClickListener(OnClickListener)
     */
    private OnClickListener mOnClickListener;

    /**
     * ��糤���¼�
     *
     * @see #setOnLongClickListener(OnLongClickListener)
     */
    private OnLongClickListener mOnLongClickListener;

    @Override
    public void setOnClickListener(OnClickListener l) {
        //Ĭ�ϵ�click�����κε������¶��ᴥ�������Ը���Լ���
        mOnClickListener = l;
    }

    @Override
    public void setOnLongClickListener(OnLongClickListener l) {
        //Ĭ�ϵ�long click�����κγ�������¶��ᴥ�������Ը���Լ���
        mOnLongClickListener = l;
    }


    ////////////////////////////////����״̬��ȡ////////////////////////////////

    /**
     * ����״̬������״̬
     *
     * @see #getPinchMode()
     */
    public static final int PINCH_MODE_FREE = 0;

    /**
     * ����״̬����ָ����״̬
     *
     * @see #getPinchMode()
     */
    public static final int PINCH_MODE_SCROLL = 1;

    /**
     * ����״̬��˫ָ����״̬
     *
     * @see #getPinchMode()
     */
    public static final int PINCH_MODE_SCALE = 2;

    /**
     * ���任��������ǵ�λ������ôͼƬ��fit center״̬
     *
     * @see #getOuterMatrix(Matrix)
     * @see #outerMatrixTo(Matrix, long)
     */
    private Matrix mOuterMatrix = new Matrix();

    /**
     * ��������
     *
     * @see #getMask()
     * @see #zoomMaskTo(RectF, long)
     */
    private RectF mMask;

    /**
     * ��ǰ����״̬
     *
     * @see #getPinchMode()
     * @see #PINCH_MODE_FREE
     * @see #PINCH_MODE_SCROLL
     * @see #PINCH_MODE_SCALE
     */
    private int mPinchMode = PINCH_MODE_FREE;

    /**
     * ��ȡ�ⲿ�任����.
     *
     * �ⲿ�任�����¼��ͼƬ���Ʋ��������ս��,�������ͼƬfit center״̬�ı任.
     * Ĭ��ֵΪ��λ����,��ʱͼƬΪfit center״̬.
     *
     * @param matrix ����������Ķ���
     * @return �������matrix������matrix���󷵻�,����newһ����䷵��
     */
    public Matrix getOuterMatrix(Matrix matrix) {
        if (matrix == null) {
            matrix = new Matrix(mOuterMatrix);
        } else {
            matrix.set(mOuterMatrix);
        }
        return matrix;
    }

    /**
     * ��ȡ�ڲ��任����.
     *
     * �ڲ��任������ԭͼ��fit center״̬�ı任,��ԭͼ�ߴ�仯���߿ؼ���С�仯���ᷢ���ı�
     * ����δ���ֻ���ԭͼ������ʱ,��ֵ������.�����ڵ���ǰ��Ҫȷ��ǰ��������Ч,����Ӱ�������.
     *
     * @param matrix ����������Ķ���
     * @return �������matrix������matrix���󷵻�,����newһ����䷵��
     */
    public Matrix getInnerMatrix(Matrix matrix) {
        if (matrix == null) {
            matrix = new Matrix();
        } else {
            matrix.reset();
        }
        if (isReady()) {
            //ԭͼ��С
            RectF tempSrc = MathUtils.rectFTake(0, 0, getDrawable().getIntrinsicWidth(), getDrawable().getIntrinsicHeight());
            //�ؼ���С
            RectF tempDst = MathUtils.rectFTake(0, 0, getWidth(), getHeight());
            //����fit center����
            matrix.setRectToRect(tempSrc, tempDst, Matrix.ScaleToFit.CENTER);
            //�ͷ���ʱ����
            MathUtils.rectFGiven(tempDst);
            MathUtils.rectFGiven(tempSrc);
        }
        return matrix;
    }

    /**
     * ��ȡͼƬ�ܱ任����.
     *
     * �ܱ任����Ϊ�ڲ��任����x�ⲿ�任����,������ԭͼ����������״̬�ı任
     * ����δ���ֻ���ԭͼ������ʱ,��ֵ������.�����ڵ���ǰ��Ҫȷ��ǰ��������Ч,����Ӱ�������.
     *
     * @param matrix ����������Ķ���
     * @return �������matrix������matrix���󷵻�,����newһ����䷵��
     *
     * @see #getOuterMatrix(Matrix)
     * @see #getInnerMatrix(Matrix)
     */
    public Matrix getCurrentImageMatrix(Matrix matrix) {
        //��ȡ�ڲ��任����
        matrix = getInnerMatrix(matrix);
        //�����ⲿ�任����
        matrix.postConcat(mOuterMatrix);
        return matrix;
    }

    /**
     * ��ȡ��ǰ�任���ͼƬλ�úͳߴ�
     *
     * ����δ���ֻ���ԭͼ������ʱ,��ֵ������.�����ڵ���ǰ��Ҫȷ��ǰ��������Ч,����Ӱ�������.
     *
     * @param rectF ����������Ķ���
     * @return �������rectF������rectF���󷵻�,����newһ����䷵��
     *
     * @see #getCurrentImageMatrix(Matrix)
     */
    public RectF getImageBound(RectF rectF) {
        if (rectF == null) {
            rectF = new RectF();
        } else {
            rectF.setEmpty();
        }
        if (!isReady()) {
            return rectF;
        } else {
            //����һ����matrix
            Matrix matrix = MathUtils.matrixTake();
            //��ȡ��ǰ�ܱ任����
            getCurrentImageMatrix(matrix);
            //��ԭͼ���ν��б任�õ���ǰ��ʾ����
            rectF.set(0, 0, getDrawable().getIntrinsicWidth(), getDrawable().getIntrinsicHeight());
            matrix.mapRect(rectF);
            //�ͷ���ʱmatrix
            MathUtils.matrixGiven(matrix);
            return rectF;
        }
    }

    /**
     * ��ȡ��ǰ���õ�mask
     *
     * @return ���ص�ǰ��mask���󸱱�,�����ǰû������mask�򷵻�null
     */
    public RectF getMask() {
        if (mMask != null) {
            return new RectF(mMask);
        } else {
            return null;
        }
    }

    /**
     * ��ȡ��ǰ����״̬
     *
     * @see #PINCH_MODE_FREE
     * @see #PINCH_MODE_SCROLL
     * @see #PINCH_MODE_SCALE
     */
    public int getPinchMode() {
        return mPinchMode;
    }

    /**
     * ��ViewPager��ϵ�ʱ��ʹ��
     * @param direction
     * @return
     */
    @Override
    public boolean canScrollHorizontally(int direction) {
        if (mPinchMode == PinchImageView.PINCH_MODE_SCALE) {
            return true;
        }
        RectF bound = getImageBound(null);
        if (bound == null) {
            return false;
        }
        if (bound.isEmpty()) {
            return false;
        }
        if (direction > 0) {
            return bound.right > getWidth();
        } else {
            return bound.left < 0;
        }
    }

    /**
     * ��ViewPager��ϵ�ʱ��ʹ��
     * @param direction
     * @return
     */
    @Override
    public boolean canScrollVertically(int direction) {
        if (mPinchMode == PinchImageView.PINCH_MODE_SCALE) {
            return true;
        }
        RectF bound = getImageBound(null);
        if (bound == null) {
            return false;
        }
        if (bound.isEmpty()) {
            return false;
        }
        if (direction > 0) {
            return bound.bottom > getHeight();
        } else {
            return bound.top < 0;
        }
    }


    ////////////////////////////////����״̬����////////////////////////////////

    /**
     * ִ�е�ǰouterMatrix��ָ��outerMatrix����Ķ���
     *
     * ���ô˷�����ֹͣ���ڽ����е������Լ����ƶ���.
     * ��durationΪ0ʱ,outerMatrixֵ�ᱻ�������ö�������������.
     *
     * @param endMatrix ����Ŀ�����
     * @param duration ��������ʱ��
     *
     * @see #getOuterMatrix(Matrix)
     */
    public void outerMatrixTo(Matrix endMatrix, long duration) {
        if (endMatrix == null) {
            return;
        }
        //����������ΪPINCH_MODE_FREE��ֹͣ�������ƵĴ���
        mPinchMode = PINCH_MODE_FREE;
        //ֹͣ�������ڽ��еĶ���
        cancelAllAnimator();
        //���ʱ�䲻�Ϸ�����ִ�н��
        if (duration <= 0) {
            mOuterMatrix.set(endMatrix);
            dispatchOuterMatrixChanged();
            invalidate();
        } else {
            //��������仯����
            mScaleAnimator = new ScaleAnimator(mOuterMatrix, endMatrix, duration);
            mScaleAnimator.start();
        }
    }

    /**
     * ִ�е�ǰmask��ָ��mask�ı仯����
     *
     * ���ô˷�������ֹͣ�����Լ�������ض���,����ֹͣ���ڽ��е�mask����.
     * ��ǰmaskΪnullʱ,��ִ�ж�����������ΪĿ��mask.
     * ��durationΪ0ʱ,��������ǰmask����ΪĿ��mask,����ִ�ж���.
     *
     * @param mask ����Ŀ��mask
     * @param duration ��������ʱ��
     *
     * @see #getMask()
     */
    public void zoomMaskTo(RectF mask, long duration) {
        if (mask == null) {
            return;
        }
        //ֹͣmask����
        if (mMaskAnimator != null) {
            mMaskAnimator.cancel();
            mMaskAnimator = null;
        }
        //���durationΪ0����֮ǰû�����ù�mask,��ִ�ж���,��������
        if (duration <= 0 || mMask == null) {
            if (mMask == null) {
                mMask = new RectF();
            }
            mMask.set(mask);
            invalidate();
        } else {
            //ִ��mask����
            mMaskAnimator = new MaskAnimator(mMask, mask, duration);
            mMaskAnimator.start();
        }
    }

    /**
     * ��������״̬
     *
     * ����λ�õ�fit center״̬,���mask,ֹͣ��������,ֹͣ���ж���.
     * �������drawable,�Լ��¼����������.
     */
    public void reset() {
        //����λ�õ�fit
        mOuterMatrix.reset();
        dispatchOuterMatrixChanged();
        //���mask
        mMask = null;
        //ֹͣ��������
        mPinchMode = PINCH_MODE_FREE;
        mLastMovePoint.set(0, 0);
        mScaleCenter.set(0, 0);
        mScaleBase = 0;
        //ֹͣ���ж���
        if (mMaskAnimator != null) {
            mMaskAnimator.cancel();
            mMaskAnimator = null;
        }
        cancelAllAnimator();
        //�ػ�
        invalidate();
    }


    ////////////////////////////////����㲥�¼�////////////////////////////////

    /**
     * �ⲿ����仯�¼�֪ͨ������
     */
    public interface OuterMatrixChangedListener {

        /**
         * �ⲿ����仯�ص�
         *
         * �ⲿ������κα仯���յ��˻ص�.
         * �ⲿ����仯��,�ܱ仯����,ͼƬ��չʾλ�ö��������仯.
         *
         * @param pinchImageView
         *
         * @see #getOuterMatrix(Matrix)
         * @see #getCurrentImageMatrix(Matrix)
         * @see #getImageBound(RectF)
         */
        void onOuterMatrixChanged(PinchImageView pinchImageView);
    }

    /**
     * ����OuterMatrixChangedListener�����б�
     *
     * @see #addOuterMatrixChangedListener(OuterMatrixChangedListener)
     * @see #removeOuterMatrixChangedListener(OuterMatrixChangedListener)
     */
    private List<OuterMatrixChangedListener> mOuterMatrixChangedListeners;

    /**
     * ��mOuterMatrixChangedListeners�������������޸�ʱ,��ʱ���޸�д�����������
     *
     * @see #mOuterMatrixChangedListeners
     */
    private List<OuterMatrixChangedListener> mOuterMatrixChangedListenersCopy;

    /**
     * mOuterMatrixChangedListeners���޸�����
     *
     * ������dispatchOuterMatrixChanged����ʱ,����1,�˳�ǰ����1
     *
     * @see #dispatchOuterMatrixChanged()
     * @see #addOuterMatrixChangedListener(OuterMatrixChangedListener)
     * @see #removeOuterMatrixChangedListener(OuterMatrixChangedListener)
     */
    private int mDispatchOuterMatrixChangedLock;

    /**
     * ����ⲿ����仯����
     *
     * @param listener
     */
    public void addOuterMatrixChangedListener(OuterMatrixChangedListener listener) {
        if (listener == null) {
            return;
        }
        //��������б�û�б��޸�����ֱ�ӽ�������ӵ������б�
        if (mDispatchOuterMatrixChangedLock == 0) {
            if (mOuterMatrixChangedListeners == null) {
                mOuterMatrixChangedListeners = new ArrayList<OuterMatrixChangedListener>();
            }
            mOuterMatrixChangedListeners.add(listener);
        } else {
            //��������б��޸ı�����,��ô�����ڼ����б��������
            //�����б������������������ʱ�滻�������б���
            if (mOuterMatrixChangedListenersCopy == null) {
                if (mOuterMatrixChangedListeners != null) {
                    mOuterMatrixChangedListenersCopy = new ArrayList<OuterMatrixChangedListener>(mOuterMatrixChangedListeners);
                } else {
                    mOuterMatrixChangedListenersCopy = new ArrayList<OuterMatrixChangedListener>();
                }
            }
            mOuterMatrixChangedListenersCopy.add(listener);
        }
    }

    /**
     * ɾ���ⲿ����仯����
     *
     * @param listener
     */
    public void removeOuterMatrixChangedListener(OuterMatrixChangedListener listener) {
        if (listener == null) {
            return;
        }
        //��������б�û�б��޸�����ֱ���ڼ����б����ݽṹ���޸�
        if (mDispatchOuterMatrixChangedLock == 0) {
            if (mOuterMatrixChangedListeners != null) {
                mOuterMatrixChangedListeners.remove(listener);
            }
        } else {
            //��������б��޸�����,��ô�����丱�����޸�
            //�丱���������������ʱ�滻�ؼ����б�
            if (mOuterMatrixChangedListenersCopy == null) {
                if (mOuterMatrixChangedListeners != null) {
                    mOuterMatrixChangedListenersCopy = new ArrayList<OuterMatrixChangedListener>(mOuterMatrixChangedListeners);
                }
            }
            if (mOuterMatrixChangedListenersCopy != null) {
                mOuterMatrixChangedListenersCopy.remove(listener);
            }
        }
    }

    /**
     * �����ⲿ�����޸��¼�
     *
     * ��Ҫ��ÿ�θ��ⲿ��������ֵʱ�����ô˷���.
     *
     * @see #mOuterMatrix
     */
    private void dispatchOuterMatrixChanged() {
        if (mOuterMatrixChangedListeners == null) {
            return;
        }
        //������
        //����֮�����ü���������������Ϊ�����������ڼ��ּ�ӵ����˴˷��������ݹ�
        //ʹ��boolean�޷��жϵݹ����
        mDispatchOuterMatrixChangedLock++;
        //���б�ѭ�������в������޸��б�,������������
        for (OuterMatrixChangedListener listener : mOuterMatrixChangedListeners) {
            listener.onOuterMatrixChanged(this);
        }
        //����
        mDispatchOuterMatrixChangedLock--;
        //����ǵݹ�����,mDispatchOuterMatrixChangedLock���ܴ���1,ֻ�м���0�������б���������
        if (mDispatchOuterMatrixChangedLock == 0) {
            //����ڼ����޸��б�,��ô��������Ϊnull
            if (mOuterMatrixChangedListenersCopy != null) {
                //�������滻����ʽ���б�
                mOuterMatrixChangedListeners = mOuterMatrixChangedListenersCopy;
                //��ո���
                mOuterMatrixChangedListenersCopy = null;
            }
        }
    }


    ////////////////////////////////�������ض���////////////////////////////////

    /**
     * ��ȡͼƬ���ɷŴ�ı���
     *
     * ����Ŵ������������򲻱�����.
     * ��˫�����Ź��������ͼƬ�Ŵ�����������ֵ,��ָ�ͷŽ��ص����������.
     * ��˫���Ŵ�����в�����Ŵ�����������ֵ.
     * ���Ǵ˷������Զ��Ʋ�ͬ���ʹ�ò�ͬ�����ɷŴ����.
     *
     * @return ���ű���
     *
     * @see #scaleEnd()
     * @see #doubleTap(float, float)
     */
    protected float getMaxScale() {
        return MAX_SCALE;
    }

    /**
     * ����˫��֮��ͼƬ������Ӧ�ñ����ŵı���
     *
     * ���ֵ����getMaxScale����С��fit center�ߴ磬��ʵ��ʹ��ȡ�߽�ֵ.
     * ͨ�����Ǵ˷������Զ��Ʋ�ͬ��ͼƬ��˫��ʱʹ�ò�ͬ�ķŴ����.
     *
     * @param innerScale ��ǰ�ڲ����������ֵ
     * @param outerScale ��ǰ�ⲿ���������ֵ
     * @return �����������ű���
     *
     * @see #doubleTap(float, float)
     * @see #getMaxScale()
     */
    protected float calculateNextScale(float innerScale, float outerScale) {
        float currentScale = innerScale * outerScale;
        if (currentScale < MAX_SCALE) {
            return MAX_SCALE;
        } else {
            return innerScale;
        }
    }


    ////////////////////////////////��ʼ��////////////////////////////////

    public PinchImageView(Context context) {
        super(context);
        initView();
    }

    public PinchImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PinchImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        //ǿ������ͼƬscaleTypeΪmatrix
        super.setScaleType(ScaleType.MATRIX);
    }

    //����������scaleType��ֻ�����ڲ����õ�matrix
    @Override
    public void setScaleType(ScaleType scaleType) {}


    ////////////////////////////////����////////////////////////////////

    @Override
    protected void onDraw(Canvas canvas) {
        //�ڻ���ǰ���ñ任����
        if (isReady()) {
            Matrix matrix = MathUtils.matrixTake();
            setImageMatrix(getCurrentImageMatrix(matrix));
            MathUtils.matrixGiven(matrix);
        }
        //��ͼ�������ִ���
        if (mMask != null) {
            canvas.save();
            canvas.clipRect(mMask);
            super.onDraw(canvas);
            canvas.restore();
        } else {
            super.onDraw(canvas);
        }
    }


    ////////////////////////////////��Ч���ж�////////////////////////////////

    /**
     * �жϵ�ǰ����Ƿ���ִ��������ؼ���
     *
     * ����:�Ƿ���ͼƬ,ͼƬ�Ƿ��гߴ�,�ؼ��Ƿ��гߴ�.
     *
     * @return �Ƿ���ִ��������ؼ���
     */
    private boolean isReady() {
        return getDrawable() != null && getDrawable().getIntrinsicWidth() > 0 && getDrawable().getIntrinsicHeight() > 0
                && getWidth() > 0 && getHeight() > 0;
    }


    ////////////////////////////////mask��������////////////////////////////////

    /**
     * mask�޸ĵĶ���
     *
     * ��ͼƬ�Ķ����໥����.
     *
     * @see #zoomMaskTo(RectF, long)
     */
    private MaskAnimator mMaskAnimator;

    /**
     * mask�任����
     *
     * ��mask��һ��rect����������һ��rect
     */
    private class MaskAnimator extends ValueAnimator implements ValueAnimator.AnimatorUpdateListener {

        /**
         * ��ʼmask
         */
        private float[] mStart = new float[4];

        /**
         * ����mask
         */
        private float[] mEnd = new float[4];

        /**
         * �м���mask
         */
        private float[] mResult = new float[4];

        /**
         * ����mask�任����
         *
         * @param start ������ʼ״̬
         * @param end �����յ�״̬
         * @param duration ��������ʱ��
         */
        public MaskAnimator(RectF start, RectF end, long duration) {
            super();
            setFloatValues(0, 1f);
            setDuration(duration);
            addUpdateListener(this);
            //������յ㿽�������鷽�����
            mStart[0] = start.left;
            mStart[1] = start.top;
            mStart[2] = start.right;
            mStart[3] = start.bottom;
            mEnd[0] = end.left;
            mEnd[1] = end.top;
            mEnd[2] = end.right;
            mEnd[3] = end.bottom;
        }


        public void onAnimationUpdate(ValueAnimator animation) {
            //��ȡ��������,0-1��Χ
            float value = (Float) animation.getAnimatedValue();
            //���ݽ��ȶ�����յ�֮������ֵ
            for (int i = 0; i < 4; i++) {
                mResult[i] = mStart[i] + (mEnd[i] - mStart[i]) * value;
            }
            //�ڼ�mask�п��ܱ��ÿ���,�����ж�һ��
            if (mMask == null) {
                mMask = new RectF();
            }
            //�����µ�mask������
            mMask.set(mResult[0], mResult[1], mResult[2], mResult[3]);
            invalidate();
        }
    }


    ////////////////////////////////���ƶ�������////////////////////////////////

    /**
     * �ڵ�ָģʽ��:
     * ��¼��һ����ָ��λ��,���ڼ����µ�λ�ú���һ��λ�õĲ�ֵ.
     *
     * ˫ָģʽ��:
     * ��¼������ָ���е�,��Ϊ��mScaleCenter�󶨵ĵ�.
     * ����󶨿��Ա�֤mScaleCenter������ζ����������е�.
     *
     * @see #mScaleCenter
     * @see #scale(PointF, float, float, PointF)
     * @see #scaleEnd()
     */
    private PointF mLastMovePoint = new PointF();

    /**
     * ����ģʽ��ͼƬ�������е�.
     *
     * Ϊ��ָ���ĵ㾭��innerMatrix�任֮���ֵ.
     * ��ָ���ĵ������ƹ�����ʼ�ո���mLastMovePoint.
     * ͨ��˫ָ����ʱ,��Ϊ�������ĵ�.
     *
     * @see #saveScaleContext(float, float, float, float)
     * @see #mLastMovePoint
     * @see #scale(PointF, float, float, PointF)
     */
    private PointF mScaleCenter = new PointF();

    /**
     * ����ģʽ�µĻ������ű���
     *
     * Ϊ�������ֵ���Կ�ʼ����ʱ��ָ����.
     * ��ֵ�������µ���ָ֮�����Ϊ���µ�ͼƬ���ű���.
     *
     * @see #saveScaleContext(float, float, float, float)
     * @see #scale(PointF, float, float, PointF)
     */
    private float mScaleBase = 0;

    /**
     * ͼƬ���Ŷ���
     *
     * ����ģʽ��ͼƬ��λ�ô�С��������֮�󴥷�.
     * ˫��ͼƬ�Ŵ����Сʱ����.
     * �ֶ�����outerMatrixTo����.
     *
     * @see #scaleEnd()
     * @see #doubleTap(float, float)
     * @see #outerMatrixTo(Matrix, long)
     */
    private ScaleAnimator mScaleAnimator;

    /**
     * ���������Ĺ��Զ���
     *
     * @see #fling(float, float)
     */
    private FlingAnimator mFlingAnimator;

    /**
     * �������ƴ���
     *
     * ��onTouchEventĩβ��ִ��.
     */
    private GestureDetector mGestureDetector = new GestureDetector(PinchImageView.this.getContext(), new GestureDetector.SimpleOnGestureListener() {

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //ֻ���ڵ�ָģʽ����֮�������ִ��fling
            if (mPinchMode == PINCH_MODE_FREE && !(mScaleAnimator != null && mScaleAnimator.isRunning())) {
                fling(velocityX, velocityY);
            }
            return true;
        }

        public void onLongPress(MotionEvent e) {
            //��������
            if (mOnLongClickListener != null) {
                mOnLongClickListener.onLongClick(PinchImageView.this);
            }
        }

        public boolean onDoubleTap(MotionEvent e) {
            //����ָ���ٵڶ��ΰ��´���,��ʱ�����ǵ�ָģʽ������ִ��doubleTap
            if (mPinchMode == PINCH_MODE_SCROLL && !(mScaleAnimator != null && mScaleAnimator.isRunning())) {
                doubleTap(e.getX(), e.getY());
            }
            return true;
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            //�������
            if (mOnClickListener != null) {
                mOnClickListener.onClick(PinchImageView.this);
            }
            return true;
        }
    });

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        //���һ����̧�����ȡ������������ģʽ
        if(action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            //���֮ǰ������ģʽ,����Ҫ����һ�����Ž�������
            if (mPinchMode == PINCH_MODE_SCALE) {
                scaleEnd();
            }
            mPinchMode = PINCH_MODE_FREE;
        } else if (action == MotionEvent.ACTION_POINTER_UP) {
            //�����ָ�����̧��һ����ָ,��ʱ��Ҫ������ģʽ�Ŵ���
            if (mPinchMode == PINCH_MODE_SCALE) {
                //̧��ĵ��������2����ô����ģʽ����Ч�������п��ܳ�ʼ����ˣ����²�����ʼ��
                if (event.getPointerCount() > 2) {
                    //�����û��������ģʽ�����ǵ�һ����̧���ˣ���ô�õڶ�����͵���������Ϊ���ſ��Ƶ�
                    if (event.getAction() >> 8 == 0) {
                        saveScaleContext(event.getX(1), event.getY(1), event.getX(2), event.getY(2));
                        //�����û��������ģʽ�����ǵڶ�����̧���ˣ���ô�õ�һ����͵���������Ϊ���ſ��Ƶ�
                    } else if (event.getAction() >> 8 == 1) {
                        saveScaleContext(event.getX(0), event.getY(0), event.getX(2), event.getY(2));
                    }
                }
                //���̧��ĵ����2,��ô��ʱֻʣ��һ����,Ҳ��������뵥ָģʽ,��Ϊ��ʱ����ͼƬû������ȷ��λ����
            }
            //��һ���㰴�£���������ģʽ����¼��ʼ�����ĵ�
        } else if (action == MotionEvent.ACTION_DOWN) {
            //�ھ��󶯻������в�������������ģʽ
            if (!(mScaleAnimator != null && mScaleAnimator.isRunning())) {
                //ֹͣ���ж���
                cancelAllAnimator();
                //�л�������ģʽ
                mPinchMode = PINCH_MODE_SCROLL;
                //���津��������move�����ֵ
                mLastMovePoint.set(event.getX(), event.getY());
            }
            //�ǵ�һ���㰴�£��رչ���ģʽ����������ģʽ����¼����ģʽ��һЩ��ʼ����
        } else if (action == MotionEvent.ACTION_POINTER_DOWN) {
            //ֹͣ���ж���
            cancelAllAnimator();
            //�л�������ģʽ
            mPinchMode = PINCH_MODE_SCALE;
            //�������ŵ�������ָ
            saveScaleContext(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
        } else if (action == MotionEvent.ACTION_MOVE) {
            if (!(mScaleAnimator != null && mScaleAnimator.isRunning())) {
                //�ڹ���ģʽ���ƶ�
                if (mPinchMode == PINCH_MODE_SCROLL) {
                    //ÿ���ƶ�����һ����ֵ�ۻ���ͼƬλ����
                    scrollBy(event.getX() - mLastMovePoint.x, event.getY() - mLastMovePoint.y);
                    //��¼�µ��ƶ���
                    mLastMovePoint.set(event.getX(), event.getY());
                    //������ģʽ���ƶ�
                } else if (mPinchMode == PINCH_MODE_SCALE && event.getPointerCount() > 1) {
                    //�������ŵ��ľ���
                    float distance = MathUtils.getDistance(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
                    //�������ŵ��е�
                    float[] lineCenter = MathUtils.getCenterPoint(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
                    mLastMovePoint.set(lineCenter[0], lineCenter[1]);
                    //��������
                    scale(mScaleCenter, mScaleBase, distance, mLastMovePoint);
                }
            }
        }
        //������ζ���������ⲿ����
        mGestureDetector.onTouchEvent(event);
        return true;
    }

    /**
     * ��ͼƬ�ƶ�һ�ξ���
     *
     * �����ƶ��������ƶ���Χ,�����˾͵����ƶ���Χ�߽�Ϊֹ.
     *
     * @param xDiff �ƶ�����
     * @param yDiff �ƶ�����
     * @return �Ƿ�ı���λ��
     */
    private boolean scrollBy(float xDiff, float yDiff) {
        if (!isReady()) {
            return false;
        }
        //ԭͼ����
        RectF bound = MathUtils.rectFTake();
        getImageBound(bound);
        //�ؼ���С
        float displayWidth = getWidth();
        float displayHeight = getHeight();
        //�����ǰͼƬ���С�ڿؼ���ȣ������ƶ�
        if (bound.right - bound.left < displayWidth) {
            xDiff = 0;
            //���ͼƬ������ƶ��󳬳��ؼ����
        } else if (bound.left + xDiff > 0) {
            //������ƶ�֮ǰ��û�����ģ�����Ӧ���ƶ��ľ���
            if (bound.left < 0) {
                xDiff = -bound.left;
                //�����޷��ƶ�
            } else {
                xDiff = 0;
            }
            //���ͼƬ�ұ����ƶ��󳬳��ؼ��ұ�
        } else if (bound.right + xDiff < displayWidth) {
            //������ƶ�֮ǰ��û�����ģ�����Ӧ���ƶ��ľ���
            if (bound.right > displayWidth) {
                xDiff = displayWidth - bound.right;
                //�����޷��ƶ�
            } else {
                xDiff = 0;
            }
        }
        //����ͬ��
        if (bound.bottom - bound.top < displayHeight) {
            yDiff = 0;
        } else if (bound.top + yDiff > 0) {
            if (bound.top < 0) {
                yDiff = -bound.top;
            } else {
                yDiff = 0;
            }
        } else if (bound.bottom + yDiff < displayHeight) {
            if (bound.bottom > displayHeight) {
                yDiff = displayHeight - bound.bottom;
            } else {
                yDiff = 0;
            }
        }
        MathUtils.rectFGiven(bound);
        //Ӧ���ƶ��任
        mOuterMatrix.postTranslate(xDiff, yDiff);
        dispatchOuterMatrixChanged();
        //�����ػ�
        invalidate();
        //����Ƿ��б仯
        if (xDiff != 0 || yDiff != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * ��¼����ǰ��һЩ��Ϣ
     *
     * �����������ֵ.
     * ����ͼƬ�����е�.
     *
     * @param x1 ���ŵ�һ����ָ
     * @param y1 ���ŵ�һ����ָ
     * @param x2 ���ŵڶ�����ָ
     * @param y2 ���ŵڶ�����ָ
     */
    private void saveScaleContext(float x1, float y1, float x2, float y2) {
        //��¼��������ֵ,����ͼƬ���ű�������x����������
        //������ͼƬӦ���ǵȱȵ�,x��y���������ͬ
        //�����п����ⲿ�趨�˲��淶��ֵ.
        //���Ǻ�����scale�����Ὣxy���ȵ�����ֵ����,�ĳɺ�x������ͬ
        mScaleBase = MathUtils.getMatrixScale(mOuterMatrix)[0] / MathUtils.getDistance(x1, y1, x2, y2);
        //����ָ���е�����Ļ��������ͼƬ��ĳ������,ͼƬ�ϵ�������ھ����ܾ���任�����ָ�е���ͬ
        //����������Ҫ�õ�ͼƬ���������ͼƬ��fit center״̬������Ļ�ϵ�λ��
        //��Ϊ�����ļ��㶼�ǻ���ͼƬ��fit center״̬�½��б任
        //������Ҫ������ָ�е�������任����õ�mScaleCenter
        float[] center = MathUtils.inverseMatrixPoint(MathUtils.getCenterPoint(x1, y1, x2, y2), mOuterMatrix);
        mScaleCenter.set(center[0], center[1]);
    }

    /**
     * ��ͼƬ����һЩ������Ϣ��������
     *
     * @param scaleCenter mScaleCenter
     * @param scaleBase mScaleBase
     * @param distance ��ָ����֮�����
     * @param lineCenter ��ָ����֮���е�
     *
     * @see #mScaleCenter
     * @see #mScaleBase
     */
    private void scale(PointF scaleCenter, float scaleBase, float distance, PointF lineCenter) {
        if (!isReady()) {
            return;
        }
        //����ͼƬ��fit center״̬��Ŀ��״̬�����ű���
        float scale = scaleBase * distance;
        Matrix matrix = MathUtils.matrixTake();
        //����ͼƬ�����������ţ��������������������ŵ��е���
        matrix.postScale(scale, scale, scaleCenter.x, scaleCenter.y);
        //��ͼƬ�������е������ָ�����е�
        matrix.postTranslate(lineCenter.x - scaleCenter.x, lineCenter.y - scaleCenter.y);
        //Ӧ�ñ任
        mOuterMatrix.set(matrix);
        MathUtils.matrixGiven(matrix);
        dispatchOuterMatrixChanged();
        //�ػ�
        invalidate();
    }

    /**
     * ˫����Ŵ������С
     *
     * ��ͼƬ���ű������ŵ�nextScaleָ����ֵ.
     * ��nextScaleֵ���ܴ����������ֵ����С��fit center����µ�����ֵ.
     * ��˫���ĵ㾡���ƶ����ؼ�����.
     *
     * @param x ˫���ĵ�
     * @param y ˫���ĵ�
     *
     * @see #calculateNextScale(float, float)
     * @see #getMaxScale()
     */
    private void doubleTap(float x, float y) {
        if (!isReady()) {
            return;
        }
        //��ȡ��һ��任����
        Matrix innerMatrix = MathUtils.matrixTake();
        getInnerMatrix(innerMatrix);
        //��ǰ�ܵ����ű���
        float innerScale = MathUtils.getMatrixScale(innerMatrix)[0];
        float outerScale = MathUtils.getMatrixScale(mOuterMatrix)[0];
        float currentScale = innerScale * outerScale;
        //�ؼ���С
        float displayWidth = getWidth();
        float displayHeight = getHeight();
        //���Ŵ��С
        float maxScale = getMaxScale();
        //������Ҫ�Ŵ�Ĵ�С
        float nextScale = calculateNextScale(innerScale, outerScale);
        //����������Ŵ�������ֵ����С��fit centerֵ����ȡ�߽�
        if (nextScale > maxScale) {
            nextScale = maxScale;
        }
        if (nextScale < innerScale) {
            nextScale = innerScale;
        }
        //��ʼ�������Ŷ����Ľ������
        Matrix animEnd = MathUtils.matrixTake(mOuterMatrix);
        //���㻹�����ŵı���
        animEnd.postScale(nextScale / currentScale, nextScale / currentScale, x, y);
        //���Ŵ���ƶ����ؼ�����
        animEnd.postTranslate(displayWidth / 2f - x, displayHeight / 2f - y);
        //�õ��Ŵ�֮���ͼƬ����
        Matrix testMatrix = MathUtils.matrixTake(innerMatrix);
        testMatrix.postConcat(animEnd);
        RectF testBound = MathUtils.rectFTake(0, 0, getDrawable().getIntrinsicWidth(), getDrawable().getIntrinsicHeight());
        testMatrix.mapRect(testBound);
        //����λ��
        float postX = 0;
        float postY = 0;
        if (testBound.right - testBound.left < displayWidth) {
            postX = displayWidth / 2f - (testBound.right + testBound.left) / 2f;
        } else if (testBound.left > 0) {
            postX = -testBound.left;
        } else if (testBound.right < displayWidth) {
            postX = displayWidth - testBound.right;
        }
        if (testBound.bottom - testBound.top < displayHeight) {
            postY = displayHeight / 2f - (testBound.bottom + testBound.top) / 2f;
        } else if (testBound.top > 0) {
            postY = -testBound.top;
        } else if (testBound.bottom < displayHeight) {
            postY = displayHeight - testBound.bottom;
        }
        //Ӧ������λ��
        animEnd.postTranslate(postX, postY);
        //����ǰ��������ִ�еĶ���
        cancelAllAnimator();
        //�������󶯻�
        mScaleAnimator = new ScaleAnimator(mOuterMatrix, animEnd);
        mScaleAnimator.start();
        //������ʱ����
        MathUtils.rectFGiven(testBound);
        MathUtils.matrixGiven(testMatrix);
        MathUtils.matrixGiven(animEnd);
        MathUtils.matrixGiven(innerMatrix);
    }

    /**
     * �����Ų�����������
     *
     * ���ͼƬ�����߽�,�ҵ������λ�ö����ָ�.
     * ���ͼƬ���ųߴ糬�����ֵ������Сֵ,�ҵ������ֵ�����ָ�.
     */
    private void scaleEnd() {
        if (!isReady()) {
            return;
        }
        //�Ƿ�������λ��
        boolean change = false;
        //��ȡͼƬ����ı任����
        Matrix currentMatrix = MathUtils.matrixTake();
        getCurrentImageMatrix(currentMatrix);
        //�������ű���
        float currentScale = MathUtils.getMatrixScale(currentMatrix)[0];
        //�ڶ������ű���
        float outerScale = MathUtils.getMatrixScale(mOuterMatrix)[0];
        //�ؼ���С
        float displayWidth = getWidth();
        float displayHeight = getHeight();
        //������ű���
        float maxScale = getMaxScale();
        //��������
        float scalePost = 1f;
        //λ������
        float postX = 0;
        float postY = 0;
        //����������ű���������������������������
        if (currentScale > maxScale) {
            scalePost = maxScale / currentScale;
        }
        //����������������嵼�µڶ�������С��1������ͼƬ��fit center״̬��С����������������
        if (outerScale * scalePost < 1f) {
            scalePost = 1f / outerScale;
        }
        //�������������Ϊ1��˵������������
        if (scalePost != 1f) {
            change = true;
        }
        //���Ը������ŵ������������
        Matrix testMatrix = MathUtils.matrixTake(currentMatrix);
        testMatrix.postScale(scalePost, scalePost, mLastMovePoint.x, mLastMovePoint.y);
        RectF testBound = MathUtils.rectFTake(0, 0, getDrawable().getIntrinsicWidth(), getDrawable().getIntrinsicHeight());
        //��ȡ�����������ͼƬ����
        testMatrix.mapRect(testBound);
        //�������������λ�����޳����������������λ������
        if (testBound.right - testBound.left < displayWidth) {
            postX = displayWidth / 2f - (testBound.right + testBound.left) / 2f;
        } else if (testBound.left > 0) {
            postX = -testBound.left;
        } else if (testBound.right < displayWidth) {
            postX = displayWidth - testBound.right;
        }
        if (testBound.bottom - testBound.top < displayHeight) {
            postY = displayHeight / 2f - (testBound.bottom + testBound.top) / 2f;
        } else if (testBound.top > 0) {
            postY = -testBound.top;
        } else if (testBound.bottom < displayHeight) {
            postY = displayHeight - testBound.bottom;
        }
        //���λ��������Ϊ0��˵������������
        if (postX != 0 || postY != 0) {
            change = true;
        }
        //ֻ����ִ��������ִ�ж���
        if (change) {
            //�����������
            Matrix animEnd = MathUtils.matrixTake(mOuterMatrix);
            animEnd.postScale(scalePost, scalePost, mLastMovePoint.x, mLastMovePoint.y);
            animEnd.postTranslate(postX, postY);
            //����ǰ��������ִ�еĶ���
            cancelAllAnimator();
            //�������󶯻�
            mScaleAnimator = new ScaleAnimator(mOuterMatrix, animEnd);
            mScaleAnimator.start();
            //������ʱ����
            MathUtils.matrixGiven(animEnd);
        }
        //������ʱ����
        MathUtils.rectFGiven(testBound);
        MathUtils.matrixGiven(testMatrix);
        MathUtils.matrixGiven(currentMatrix);
    }

    /**
     * ִ�й��Զ���
     *
     * ���������������ƶ���ֹͣ.
     * �����ٶ�˥������С��ֹͣ.
     *
     * ���в����ٶȵ�λΪ ����/��
     *
     * @param vx x�����ٶ�
     * @param vy y�����ٶ�
     */
    private void fling(float vx, float vy) {
        if (!isReady()) {
            return;
        }
        //����ǰ��������ִ�еĶ���
        cancelAllAnimator();
        //�������Զ���
        //FlingAnimator��λΪ ����/֡,һ��60֡
        mFlingAnimator = new FlingAnimator(vx / 60f, vy / 60f);
        mFlingAnimator.start();
    }

    /**
     * ֹͣ�������ƶ���
     */
    private void cancelAllAnimator() {
        if (mScaleAnimator != null) {
            mScaleAnimator.cancel();
            mScaleAnimator = null;
        }
        if (mFlingAnimator != null) {
            mFlingAnimator.cancel();
            mFlingAnimator = null;
        }
    }

    /**
     * ���Զ���
     *
     * �ٶ���˥��,ÿ֡�ٶ�˥��Ϊԭ����FLING_DAMPING_FACTOR,���ٶ�˥����С��1ʱֹͣ.
     * ��ͼƬ�����ƶ�ʱ,����ֹͣ.
     */
    private class FlingAnimator extends ValueAnimator implements ValueAnimator.AnimatorUpdateListener {

        /**
         * �ٶ�����
         */
        private float[] mVector;

        /**
         * �������Զ���
         *
         * ������λΪ ����/֡
         *
         * @param vectorX �ٶ�����
         * @param vectorY �ٶ�����
         */
        public FlingAnimator(float vectorX, float vectorY) {
            super();
            setFloatValues(0, 1f);
            setDuration(1000000);
            addUpdateListener(this);
            mVector = new float[]{vectorX, vectorY};
        }


        public void onAnimationUpdate(ValueAnimator animation) {
            //�ƶ�ͼ�񲢸������
            boolean result = scrollBy(mVector[0], mVector[1]);
            //˥���ٶ�
            mVector[0] *= FLING_DAMPING_FACTOR;
            mVector[1] *= FLING_DAMPING_FACTOR;
            //�ٶ�̫С���߲����ƶ��˾ͽ���
            if (!result || MathUtils.getDistance(0, 0, mVector[0], mVector[1]) < 1f) {
                animation.cancel();
            }
        }
    }

    /**
     * ���Ŷ���
     *
     * �ڸ���ʱ���ڴ�һ������ı仯�𽥶�������һ������ı仯
     */
    private class ScaleAnimator extends ValueAnimator implements ValueAnimator.AnimatorUpdateListener {

        /**
         * ��ʼ����
         */
        private float[] mStart = new float[9];

        /**
         * ��������
         */
        private float[] mEnd = new float[9];

        /**
         * �м�������
         */
        private float[] mResult = new float[9];

        /**
         * ����һ�����Ŷ���
         *
         * ��һ������任������һ������
         *
         * @param start ��ʼ����
         * @param end ��������
         */
        public ScaleAnimator(Matrix start, Matrix end) {
            this(start, end, SCALE_ANIMATOR_DURATION);
        }

        /**
         * ����һ�����Ŷ���
         *
         * ��һ������任������һ������
         *
         * @param start ��ʼ����
         * @param end ��������
         * @param duration ����ʱ��
         */
        public ScaleAnimator(Matrix start, Matrix end, long duration) {
            super();
            setFloatValues(0, 1f);
            setDuration(duration);
            addUpdateListener(this);
            start.getValues(mStart);
            end.getValues(mEnd);
        }


        public void onAnimationUpdate(ValueAnimator animation) {
            //��ȡ��������
            float value = (Float) animation.getAnimatedValue();
            //���ݶ������ȼ�������м��ֵ
            for (int i = 0; i < 9; i++) {
                mResult[i] = mStart[i] + (mEnd[i] - mStart[i]) * value;
            }
            //���þ����ػ�
            mOuterMatrix.setValues(mResult);
            dispatchOuterMatrixChanged();
            invalidate();
        }
    }


    ////////////////////////////////��ֹ�ڴ涶�����ö���////////////////////////////////

    /**
     * �����
     *
     * ��ֹƵ��new��������ڴ涶��.
     * ���ڶ������󳤶�����,����̶����������������,��Ȼ�ᷢ������.
     * ��ʱ��Ҫ������������,���ǻ�ռ�ø����ڴ�.
     *
     * @param <T> ��������ɵĶ�������
     */
    private static abstract class ObjectsPool<T> {

        /**
         * ����ص��������
         */
        private int mSize;

        /**
         * ����ض���
         */
        private Queue<T> mQueue;

        /**
         * ����һ�������
         *
         * @param size ������������
         */
        public ObjectsPool(int size) {
            mSize = size;
            mQueue = new LinkedList<T>();
        }

        /**
         * ��ȡһ�����еĶ���
         *
         * ��������Ϊ��,�������Լ���newһ������.
         * �����������ж���,��ȡһ���Ѵ��ڵķ���.
         * take�����Ķ�������Ҫ�ǵõ���given�黹.
         * ������黹,��Ȼ�ᷢ���ڴ涶��,����������й©.
         *
         * @return ���õĶ���
         *
         * @see #given(Object)
         */
        public T take() {
            //�������Ϊ�վʹ���һ��
            if (mQueue.size() == 0) {
                return newInstance();
            } else {
                //��������оʹӶ����ó���һ������
                return resetInstance(mQueue.poll());
            }
        }

        /**
         * �黹�����������Ķ���
         *
         * ����黹�Ķ��������������������,��ô�黹�Ķ���ͻᱻ����.
         *
         * @param obj �黹�Ķ���
         *
         * @see #take()
         */
        public void given(T obj) {
            //�������ػ��п�λ�Ӿ͹黹����
            if (obj != null && mQueue.size() < mSize) {
                mQueue.offer(obj);
            }
        }

        /**
         * ʵ��������
         *
         * @return �����Ķ���
         */
        abstract protected T newInstance();

        /**
         * ���ö���
         *
         * �Ѷ���������յ�����մ�����һ��.
         *
         * @param obj ��Ҫ�����õĶ���
         * @return ������֮��Ķ���
         */
        abstract protected T resetInstance(T obj);
    }

    /**
     * ��������
     */
    private static class MatrixPool extends ObjectsPool<Matrix> {

        public MatrixPool(int size) {
            super(size);
        }

        @Override
        protected Matrix newInstance() {
            return new Matrix();
        }

        @Override
        protected Matrix resetInstance(Matrix obj) {
            obj.reset();
            return obj;
        }
    }

    /**
     * ���ζ����
     */
    private static class RectFPool extends ObjectsPool<RectF> {

        public RectFPool(int size) {
            super(size);
        }

        @Override
        protected RectF newInstance() {
            return new RectF();
        }

        @Override
        protected RectF resetInstance(RectF obj) {
            obj.setEmpty();
            return obj;
        }
    }


    ////////////////////////////////��ѧ���㹤����////////////////////////////////

    /**
     * ��ѧ���㹤����
     */
    public static class MathUtils {

        /**
         * ��������
         */
        private static MatrixPool mMatrixPool = new MatrixPool(16);

        /**
         * ��ȡ�������
         */
        public static Matrix matrixTake() {
            return mMatrixPool.take();
        }

        /**
         * ��ȡĳ�������copy
         */
        public static Matrix matrixTake(Matrix matrix) {
            Matrix result = mMatrixPool.take();
            if (matrix != null) {
                result.set(matrix);
            }
            return result;
        }

        /**
         * �黹�������
         */
        public static void matrixGiven(Matrix matrix) {
            mMatrixPool.given(matrix);
        }

        /**
         * ���ζ����
         */
        private static RectFPool mRectFPool = new RectFPool(16);

        /**
         * ��ȡ���ζ���
         */
        public static RectF rectFTake() {
            return mRectFPool.take();
        }

        /**
         * ����ָ��ֵ��ȡ���ζ���
         */
        public static RectF rectFTake(float left, float top, float right, float bottom) {
            RectF result = mRectFPool.take();
            result.set(left, top, right, bottom);
            return result;
        }

        /**
         * ��ȡĳ�����εĸ���
         */
        public static RectF rectFTake(RectF rectF) {
            RectF result = mRectFPool.take();
            if (rectF != null) {
                result.set(rectF);
            }
            return result;
        }

        /**
         * �黹���ζ���
         */
        public static void rectFGiven(RectF rectF) {
            mRectFPool.given(rectF);
        }

        /**
         * ��ȡ����֮�����
         *
         * @param x1 ��1
         * @param y1 ��1
         * @param x2 ��2
         * @param y2 ��2
         * @return ����
         */
        public static float getDistance(float x1, float y1, float x2, float y2) {
            float x = x1 - x2;
            float y = y1 - y2;
            return (float) Math.sqrt(x * x + y * y);
        }

        /**
         * ��ȡ������е�
         *
         * @param x1 ��1
         * @param y1 ��1
         * @param x2 ��2
         * @param y2 ��2
         * @return float[]{x, y}
         */
        public static float[] getCenterPoint(float x1, float y1, float x2, float y2) {
            return new float[]{(x1 + x2) / 2f, (y1 + y2) / 2f};
        }

        /**
         * ��ȡ���������ֵ
         *
         * @param matrix Ҫ����ľ���
         * @return float[]{scaleX, scaleY}
         */
        public static float[] getMatrixScale(Matrix matrix) {
            if (matrix != null) {
                float[] value = new float[9];
                matrix.getValues(value);
                return new float[]{value[0], value[4]};
            } else {
                return new float[2];
            }
        }

        /**
         * �������Ծ����ֵ
         *
         * matrix.mapPoints(unknownPoint) -> point
         * ��֪point��matrix,��unknownPoint��ֵ.
         *
         * @param point
         * @param matrix
         * @return unknownPoint
         */
        public static float[] inverseMatrixPoint(float[] point, Matrix matrix) {
            if (point != null && matrix != null) {
                float[] dst = new float[2];
                //����matrix�������
                Matrix inverse = matrixTake();
                matrix.invert(inverse);
                //�������任point��dst,dst���ǽ��
                inverse.mapPoints(dst, point);
                //�����ʱ����
                matrixGiven(inverse);
                return dst;
            } else {
                return new float[2];
            }
        }

        /**
         * ������������֮��ı任����
         *
         * unknownMatrix.mapRect(to, from)
         * ��֪from���κ�to����,��unknownMatrix
         *
         * @param from
         * @param to
         * @param result unknownMatrix
         */
        public static void calculateRectTranslateMatrix(RectF from, RectF to, Matrix result) {
            if (from == null || to == null || result == null) {
                return;
            }
            if (from.width() == 0 || from.height() == 0) {
                return;
            }
            result.reset();
            result.postTranslate(-from.left, -from.top);
            result.postScale(to.width() / from.width(), to.height() / from.height());
            result.postTranslate(to.left, to.top);
        }

        /**
         * ����ͼƬ��ĳ��ImageView�е���ʾ����
         *
         * @param container ImageView��Rect
         * @param srcWidth ͼƬ�Ŀ��
         * @param srcHeight ͼƬ�ĸ߶�
         * @param scaleType ͼƬ��ImageView�е�ScaleType
         * @param result ͼƬӦ����ImageView��չʾ�ľ���
         */
        public static void calculateScaledRectInContainer(RectF container, float srcWidth, float srcHeight, ScaleType scaleType, RectF result) {
            if (container == null || result == null) {
                return;
            }
            if (srcWidth == 0 || srcHeight == 0) {
                return;
            }
            //Ĭ��scaleTypeΪfit center
            if (scaleType == null) {
                scaleType = ScaleType.FIT_CENTER;
            }
            result.setEmpty();
            if (ScaleType.FIT_XY.equals(scaleType)) {
                result.set(container);
            } else if (ScaleType.CENTER.equals(scaleType)) {
                Matrix matrix = matrixTake();
                RectF rect = rectFTake(0, 0, srcWidth, srcHeight);
                matrix.setTranslate((container.width() - srcWidth) * 0.5f, (container.height() - srcHeight) * 0.5f);
                matrix.mapRect(result, rect);
                rectFGiven(rect);
                matrixGiven(matrix);
                result.left += container.left;
                result.right += container.left;
                result.top += container.top;
                result.bottom += container.top;
            } else if (ScaleType.CENTER_CROP.equals(scaleType)) {
                Matrix matrix = matrixTake();
                RectF rect = rectFTake(0, 0, srcWidth, srcHeight);
                float scale;
                float dx = 0;
                float dy = 0;
                if (srcWidth * container.height() > container.width() * srcHeight) {
                    scale = container.height() / srcHeight;
                    dx = (container.width() - srcWidth * scale) * 0.5f;
                } else {
                    scale = container.width() / srcWidth;
                    dy = (container.height() - srcHeight * scale) * 0.5f;
                }
                matrix.setScale(scale, scale);
                matrix.postTranslate(dx, dy);
                matrix.mapRect(result, rect);
                rectFGiven(rect);
                matrixGiven(matrix);
                result.left += container.left;
                result.right += container.left;
                result.top += container.top;
                result.bottom += container.top;
            } else if (ScaleType.CENTER_INSIDE.equals(scaleType)) {
                Matrix matrix = matrixTake();
                RectF rect = rectFTake(0, 0, srcWidth, srcHeight);
                float scale;
                float dx;
                float dy;
                if (srcWidth <= container.width() && srcHeight <= container.height()) {
                    scale = 1f;
                } else {
                    scale = Math.min(container.width() / srcWidth, container.height() / srcHeight);
                }
                dx = (container.width() - srcWidth * scale) * 0.5f;
                dy = (container.height() - srcHeight * scale) * 0.5f;
                matrix.setScale(scale, scale);
                matrix.postTranslate(dx, dy);
                matrix.mapRect(result, rect);
                rectFGiven(rect);
                matrixGiven(matrix);
                result.left += container.left;
                result.right += container.left;
                result.top += container.top;
                result.bottom += container.top;
            } else if (ScaleType.FIT_CENTER.equals(scaleType)) {
                Matrix matrix = matrixTake();
                RectF rect = rectFTake(0, 0, srcWidth, srcHeight);
                RectF tempSrc = rectFTake(0, 0, srcWidth, srcHeight);
                RectF tempDst = rectFTake(0, 0, container.width(), container.height());
                matrix.setRectToRect(tempSrc, tempDst, Matrix.ScaleToFit.CENTER);
                matrix.mapRect(result, rect);
                rectFGiven(tempDst);
                rectFGiven(tempSrc);
                rectFGiven(rect);
                matrixGiven(matrix);
                result.left += container.left;
                result.right += container.left;
                result.top += container.top;
                result.bottom += container.top;
            } else if (ScaleType.FIT_START.equals(scaleType)) {
                Matrix matrix = matrixTake();
                RectF rect = rectFTake(0, 0, srcWidth, srcHeight);
                RectF tempSrc = rectFTake(0, 0, srcWidth, srcHeight);
                RectF tempDst = rectFTake(0, 0, container.width(), container.height());
                matrix.setRectToRect(tempSrc, tempDst, Matrix.ScaleToFit.START);
                matrix.mapRect(result, rect);
                rectFGiven(tempDst);
                rectFGiven(tempSrc);
                rectFGiven(rect);
                matrixGiven(matrix);
                result.left += container.left;
                result.right += container.left;
                result.top += container.top;
                result.bottom += container.top;
            } else if (ScaleType.FIT_END.equals(scaleType)) {
                Matrix matrix = matrixTake();
                RectF rect = rectFTake(0, 0, srcWidth, srcHeight);
                RectF tempSrc = rectFTake(0, 0, srcWidth, srcHeight);
                RectF tempDst = rectFTake(0, 0, container.width(), container.height());
                matrix.setRectToRect(tempSrc, tempDst, Matrix.ScaleToFit.END);
                matrix.mapRect(result, rect);
                rectFGiven(tempDst);
                rectFGiven(tempSrc);
                rectFGiven(rect);
                matrixGiven(matrix);
                result.left += container.left;
                result.right += container.left;
                result.top += container.top;
                result.bottom += container.top;
            } else {
                result.set(container);
            }
        }
    }
}