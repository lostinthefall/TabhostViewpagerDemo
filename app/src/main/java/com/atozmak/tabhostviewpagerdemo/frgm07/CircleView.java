package com.atozmak.tabhostviewpagerdemo.frgm07;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.view.View;

/**
 * 圆形视图, 外圆是实心圆圈, 颜色渐变; 内圆是擦除效果.
 * <p/>
 * Created by Mak on 2016/4/8.
 */
public class CircleView extends View {

    private static final int START_COLOR = 0xffff5722;//红色 http://rgb.phpddt.com/
    private static final int END_COLOR = 0xffffc107;//橙色 http://rgb.phpddt.com/

    private ArgbEvaluator mArgbEvaluator;//颜色值计算器。

    private Paint mCirclePaint;//圆形试图
    private Paint mMaskPaint;//掩盖视图

    private Canvas mTempCanvas;//中间画布
    private Bitmap mTempBitmap;//中间画图

    private int mMaxCircleSize;//最大圆环大小（半径）

    private float mOuterCircleRadiusProgress;
    private float mInnerCircleRadiusProgress;


    public CircleView(Context context) {
        super(context);
        init();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //初始化, 外圆使用实心画笔(Paint), 内圆使用擦除画笔. ArgbEvaluator控制颜色变换.
    private void init() {
        mCirclePaint = new Paint();
        mCirclePaint.setStyle(Paint.Style.FILL);

        mMaskPaint = new Paint();//消失的效果
        //橡皮擦的效果。
        mMaskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        mArgbEvaluator = new ArgbEvaluator();
    }

    //---------------------------------------------------

    /**
     * onMeasure结束后会调用onSizeChanged。
     * <p/>
     * @param w    Current width of this view.
     * @param h    Current height of this view.
     * @param oldw Old width of this view.
     * @param oldh Old height of this view.
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //圆的半径最大不超过本view宽度的一般。
        mMaxCircleSize = w / 2;

        //Returns a mutable bitmap with the specified width and height.
        mTempBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);

        //* Construct a canvas with the specified bitmap to draw into. The bitmap must be mutable.
        mTempCanvas = new Canvas(mTempBitmap);
    }

    //---------------------------------------------------

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mTempCanvas.drawColor(0xffffff, PorterDuff.Mode.CLEAR);

        mTempCanvas.drawCircle(
                getWidth() / 2,
                getHeight() / 2,
                mOuterCircleRadiusProgress * mMaxCircleSize,
                mCirclePaint
        );
        mTempCanvas.drawCircle(
                getWidth() / 2,
                getHeight() / 2,
                mInnerCircleRadiusProgress * mMaxCircleSize,
                mMaskPaint
        );
        canvas.drawBitmap(mTempBitmap, 0, 0, null);
    }

    //---------------------------------------------------

    public float getOuterCircleRadiusProgress() {
        return mOuterCircleRadiusProgress;
    }

    public void setOuterCircleRadiusProgress(float outerCircleRadiusProgress) {
        mOuterCircleRadiusProgress = outerCircleRadiusProgress;
        updateCircleColor();
        postInvalidate();
    }

    // 更新圆圈的颜色变化
    private void updateCircleColor() {

        // 0.5到1颜色渐变
        float colorProgress = (float) ProAnimUtils.clamp(
                mOuterCircleRadiusProgress, 0.5, 1
        );

        //转换映射控件
        colorProgress = (float) ProAnimUtils.mapValueFromRangeToRange(
                colorProgress, 0.5f, 1f, 0f, 1f
        );
        mCirclePaint.setColor((Integer) mArgbEvaluator.evaluate(colorProgress, START_COLOR, END_COLOR));
    }

    public float getInnerCircleRadiusProgress() {
        return mInnerCircleRadiusProgress;
    }

    public void setInnerCircleRadiusProgress(float innerCircleRadiusProgress) {
        mInnerCircleRadiusProgress = innerCircleRadiusProgress;
        postInvalidate();
    }

    //---------------------------------------------------

    // <T> The class on which the property is declared.  <V> The type that this property represents.
    public static final Property<CircleView, Float> INNER_CIRCLE_RADIUS_PROGRESS =

            //public Property(Class<V> type, String name)
            new Property<CircleView, Float>(Float.class, "innerCircleRadiusProgress") {

                // public abstract V get(T object);
                @Override
                public Float get(CircleView object) {
                    return object.getInnerCircleRadiusProgress();
                }

                // public void set(T object, V value)
                @Override
                public void set(CircleView object, Float value) {
                    object.setInnerCircleRadiusProgress(value);
                }
            };

    public static final Property<CircleView, Float> OUTER_CIRCLE_RADIUS_PROGRESS =
            new Property<CircleView, Float>(Float.class, "outerCircleRadiusProgress") {
                @Override
                public Float get(CircleView object) {
                    return object.getOuterCircleRadiusProgress();
                }

                @Override
                public void set(CircleView object, Float value) {
                    object.setOuterCircleRadiusProgress(value);
                }
            };

    //---------------------------------------------------
}
