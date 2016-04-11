package com.atozmak.tabhostviewpagerdemo.frgm07;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;

/**
 * 发散的点, 由大点小点组成, 两类点排列和颜色均错开, 速度先慢后快向外发射.
 * <p/>
 * Created by Mak on 2016/4/8.
 */
public class DotsView extends View {

    private static final int DOTS_COUNT = 7;
    private static final int OUTER_DOTS_POSITION_ANGLE = 51;

    private static final int COLOR_1 = 0xffffc107;//橙色
    private static final int COLOR_2 = 0xffff9800;//橙带红
    private static final int COLOR_3 = 0xffff5722;//橙带更红
    private static final int COLOR_4 = 0xfff44336;//红

    private final Paint[] mCirclePaints = new Paint[4];

    private int mCenterX;
    private int mCenterY;

    private float mMaxOuterDotsRadius;// 最大外圈的半径
    private float mMaxInnerDotsRadius;// 最大内圈的半径
    private float mMaxDotSize;// 圆圈的最大尺寸

    private float mCurrentProgress = 0;// 当前进度, 核心参数 ， <0.0f~1.0f>

    private float mCurrentRadius1 = 0;// 外圈点的半径 ，圈的半径，不是圆点的半径
    private float mCurrentDotSize1 = 0;// 外圈点的大小

    private float mCurrentRadius2 = 0;
    private float mCurrentDotSize2 = 0;

    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();


    public DotsView(Context context) {
        super(context);
        init();
    }

    public DotsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public DotsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        for (int i = 0; i < mCirclePaints.length; i++) {
            mCirclePaints[i] = new Paint();
            mCirclePaints[i].setStyle(Paint.Style.FILL);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterX = w / 2;
        mCenterY = h / 2;

        mMaxDotSize = 20;

        /**
         * 保证弹射出来的圆点能不会弹到屏幕外（看不见），
         * 所以圆点运动的最大半径是view的宽度减去圆点的直径。
         */
        mMaxOuterDotsRadius = w / 2 - mMaxDotSize * 2;
        mMaxInnerDotsRadius = 0.8f * mMaxOuterDotsRadius;
    }

    //------------------------------------------

    @Override
    protected void onDraw(Canvas canvas) {
        drawOuterDotsFrame(canvas);
        drawInnerDotsFrame(canvas);
    }

    private void drawOuterDotsFrame(Canvas canvas) {
        /**
         * 计算出位置，在各个位置绘制圆点。
         */
        for (int i = 0; i < DOTS_COUNT; i++) {
            //Math.cos， in radians
            //PI是弧度的180度。
            int cX = (int) (mCenterX + mCurrentRadius1 * Math.cos(i * OUTER_DOTS_POSITION_ANGLE * Math.PI / 180));
            int cY = (int) (mCenterX + mCurrentRadius1 * Math.sin(i * OUTER_DOTS_POSITION_ANGLE * Math.PI / 180));

            canvas.drawCircle(cX, cY, mCurrentDotSize1, mCirclePaints[i % mCirclePaints.length]);
        }
    }

    private void drawInnerDotsFrame(Canvas canvas) {
        for (int i = 0; i < DOTS_COUNT; i++) {
            int cX = (int) (mCenterX + mCurrentRadius2 * Math.cos((i * OUTER_DOTS_POSITION_ANGLE - 10) * Math.PI / 180));
            int cY = (int) (mCenterY + mCurrentRadius2 * Math.sin((i * OUTER_DOTS_POSITION_ANGLE - 10) * Math.PI / 180));

            //i+1 确保颜色不同
            canvas.drawCircle(cX, cY, mCurrentDotSize2, mCirclePaints[(i + 1) % mCirclePaints.length]);
        }
    }

    //------------------------------------------

    public void setCurrentProgress(float currentProgress) {
        mCurrentProgress = currentProgress;

        updateInnerDotsPosition();
        updateOuterDotsPosition();

        updateDotsPaints();
        updateDotsAlpha();

        postInvalidate();
    }


    public float getCurrentProgress() {
        return mCurrentProgress;
    }

    //--------------------------------------

    private void updateDotsAlpha() {
        float progress = (float) ProAnimUtils.clamp(mCurrentProgress, 0.6f, 1f); // 最小0.6, 最大1
        int alpha = (int) ProAnimUtils.mapValueFromRangeToRange(progress, 0.6f, 1f, 255, 0); // 直至消失
        mCirclePaints[0].setAlpha(alpha);
        mCirclePaints[1].setAlpha(alpha);
        mCirclePaints[2].setAlpha(alpha);
        mCirclePaints[3].setAlpha(alpha);
    }

    //颜色变化
    private void updateDotsPaints() {

        if (mCurrentProgress < 0.5f) {

            float progress = (float) ProAnimUtils.mapValueFromRangeToRange(mCurrentProgress, 0f, 0.5f, 0, 1f);
//            mCirclePaints[0].setColor(0xff000000);
            mCirclePaints[0].setColor((Integer) argbEvaluator.evaluate(progress, COLOR_1, COLOR_2));
            mCirclePaints[1].setColor((Integer) argbEvaluator.evaluate(progress, COLOR_2, COLOR_3));
            mCirclePaints[2].setColor((Integer) argbEvaluator.evaluate(progress, COLOR_3, COLOR_4));
            mCirclePaints[3].setColor((Integer) argbEvaluator.evaluate(progress, COLOR_4, COLOR_1));
        } else {
            float progress = (float) ProAnimUtils.mapValueFromRangeToRange(mCurrentProgress, 0.5f, 1f, 0, 1f);
            mCirclePaints[0].setColor((Integer) argbEvaluator.evaluate(progress, COLOR_2, COLOR_3));
            mCirclePaints[1].setColor((Integer) argbEvaluator.evaluate(progress, COLOR_3, COLOR_4));
            mCirclePaints[2].setColor((Integer) argbEvaluator.evaluate(progress, COLOR_4, COLOR_1));
            mCirclePaints[3].setColor((Integer) argbEvaluator.evaluate(progress, COLOR_1, COLOR_2));
        }


    }

    //--------------------------------------

    private void updateOuterDotsPosition() {
        // 半径先走的快, 后走的慢
        //private float mCurrentProgress = 0;// 当前进度, 核心参数
        if (mCurrentProgress < 0.3f) {
            /**
             * mCurrentRadius1是圈的半径，不是圆点的半径。
             *
             * 用前30%的时间，走80%的距离。
             *
             * 【mCurrentProgress（时间值）】----->【mCurrentRadius1（半径值）】
             */
            mCurrentRadius1 = (float) ProAnimUtils.mapValueFromRangeToRange(
                    mCurrentProgress, 0.0f, 0.3f, 0, mMaxOuterDotsRadius * 0.8f);
        } else {
            /**
             * 用剩下的70%的时间，走完剩下的20%的距离。
             */
            mCurrentRadius1 = (float) ProAnimUtils.mapValueFromRangeToRange(
                    mCurrentProgress, 0.3f, 1f, 0.8f * mMaxOuterDotsRadius, mMaxOuterDotsRadius);
        }

        // 圆点的大小, 前70%的时间为mMaxDotSize, 后30%的时间逐渐缩小为0.
        if (mCurrentProgress < 0.7f) {
            mCurrentDotSize1 = mMaxDotSize;
        } else {
            mCurrentDotSize1 = (float) ProAnimUtils.mapValueFromRangeToRange(
                    mCurrentProgress, 0.7f, 1f, mMaxDotSize, 0);
        }


    }

    private void updateInnerDotsPosition() {

        if (mCurrentProgress < 0.3f) {
            //前30%时间，走完全程。
            this.mCurrentRadius2 = (float) ProAnimUtils.mapValueFromRangeToRange(
                    mCurrentProgress, 0, 0.3f, 0.0f, mMaxInnerDotsRadius);
        } else {
            //剩下的时间不动。
            this.mCurrentRadius2 = mMaxInnerDotsRadius;
        }


        if (mCurrentProgress < 0.2) {
            //前20%的时间，圆点大小为 mMaxDotSize。
            this.mCurrentDotSize2 = mMaxDotSize;
        } else if (mCurrentProgress < 0.5) {
            //20%到50%的时间内，大小由 mMaxDotSize 缩小成 30%的大小。
            this.mCurrentDotSize2 = (float) ProAnimUtils.mapValueFromRangeToRange(
                    mCurrentProgress, 0.2f, 0.5f, mMaxDotSize, 0.3 * mMaxDotSize);
        } else {
            //50%到结束的时间，大小由 30%的大小 缩小成 0。
            this.mCurrentDotSize2 = (float) ProAnimUtils.mapValueFromRangeToRange(
                    mCurrentProgress, 0.5f, 1f, mMaxDotSize * 0.3f, 0);
        }

    }

    //--------------------------------------

    public static final Property<DotsView, Float> DOTS_PROGRESS
            = new Property<DotsView, Float>(Float.class, "dotsProgress") {
        @Override
        public Float get(DotsView object) {
            return object.getCurrentProgress();
        }

        @Override
        public void set(DotsView object, Float value) {
            object.setCurrentProgress(value);
        }
    };


}
