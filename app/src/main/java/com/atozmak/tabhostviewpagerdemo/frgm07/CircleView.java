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

import com.atozmak.tabhostviewpagerdemo.mainActivity.LogUtils;

/**
 * 圆形视图, 外圆是实心圆圈, 颜色渐变; 内圆是擦除效果.
 * <p/>
 * Created by Mak on 2016/4/8.
 */
public class CircleView extends View {

    public static final String TAG = LogUtils.makLogTag("CircleView");

    private static final int START_COLOR_RED = 0xffff5722;//红色 http://rgb.phpddt.com/
    private static final int END_COLOR_ORANGE = 0xffffc107;//橙色 http://rgb.phpddt.com/

    private ArgbEvaluator mArgbEvaluator;//颜色值计算器。

    private Paint mCirclePaint;//圆形试图
    private Paint mMaskPaint;//掩盖视图

    private Canvas mTempCanvas;//中间画布
    private Bitmap mTempBitmap;//中间画图

    private int mMaxCircleSize;//最大圆环大小（半径）

    private float mOuterCircleRadiusProgress;//初始值默认为0.0
    private float mInnerCircleRadiusProgress;//初始值默认为0.0


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
     *
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
        /**
         * 画一个透明的背景。不画的话效果也是一样，不知道作者为什么会这样做。
         *
         * 如果是用canvas.drawColor(0xffffff, PorterDuff.Mode.CLEAR);就会得到黑色的背景。
         * 即使在xml文件中也改变不了背景色。还是黑色
         *
         * mTempCanvas.drawColor(0xffffff, PorterDuff.Mode.CLEAR);
         */
        //  mTempCanvas.drawColor(0xffffff, PorterDuff.Mode.CLEAR);

        mTempCanvas.drawCircle(
                getWidth() / 2,
                getHeight() / 2,
                //变化值<0~1> * 半径 ，即，圆将由小变大，最大至半径为mMaxCircleSize。
                mOuterCircleRadiusProgress * mMaxCircleSize,
                mCirclePaint
        );
        //mMaskPaint只会擦除本view里面的底色，本view外的颜色不受影响。
        mTempCanvas.drawCircle(
                getWidth() / 2,
                getHeight() / 2,
                mInnerCircleRadiusProgress * mMaxCircleSize,
                mMaskPaint
        );
        /**
         * Canvas(bitmap) 与 canvas.setBitmap(bitmap)中的bitmap是Canvas的mBitmap，直接操作/修改的对象。
         * canvas.drawBitmap(bitmap)中的bitmap是源bitmap，draw时，不对源bitmap进行写操作，
         */
        canvas.drawBitmap(mTempBitmap, 0, 0, null);

//        Log.v(TAG, "onDraw——————" + "mOuterCircleRadiusProgress= " + mOuterCircleRadiusProgress);

    }

    //---------------------------------------------------

    public float getOuterCircleRadiusProgress() {
        return mOuterCircleRadiusProgress;
    }

    public void setOuterCircleRadiusProgress(float outerCircleRadiusProgress) {
        mOuterCircleRadiusProgress = outerCircleRadiusProgress;
        updateCircleColor();

        //因为这是一个动态的view，所以需要通知view在数值更新时就要更新view。
        postInvalidate();

//        Log.v(TAG, "updateCircleColor>>>>postInvalidate>>>>>");
    }

    // 更新圆圈的颜色变化
    private void updateCircleColor() {

        /**
         *  0.5到1颜色渐变
         *
         *  如果【mOuterCircleRadiusProgress】在【0.5到1】之间的话，
         *  就返回【mOuterCircleRadiusProgress】，否则返回0.5或者1.
         *
         *  流程：【mOuterCircleRadiusProgress】<0.1~1>,【colorProgress】<0.5~1>
         */
        float colorProgress = (float) ProAnimUtils.clamp(
                mOuterCircleRadiusProgress, 0.5, 1
        );

        /**
         *  public class ArgbEvaluator implements TypeEvaluator
         *  public Object evaluate(float fraction, Object startValue, Object endValue)
         *
         * 【colorProgress】是一个【fraction】
         */
        //转换映射控件
        colorProgress = (float) ProAnimUtils.mapValueFromRangeToRange(
                colorProgress, 0.5f, 1f, 0f, 1f
        );
        /**
         * 【mOuterCircleRadiusProgress】在0.5之前，【mCirclePaint.setColor】都是红色，之后会由红色0向黄色过度。
         * 即先红色一会，再转黄色。
         * 【mapValueFromRangeToRange】是使得颜色过度平滑<0~1>，而不是已经转了一般黄色的红色想黄色转<0.5~1>.
         * 把擦除效果的圆屏蔽了就能看到效果了。
         */
        mCirclePaint.setColor((Integer) mArgbEvaluator.evaluate(colorProgress, START_COLOR_RED, END_COLOR_ORANGE));
    }


    /**
     * get,set例子。
     * <p/>
     * private static class MyView{
     * private View mTarget;
     * private MyView(View mTarget){
     * this.mTarget=mTarget;
     * }
     * public int getWidth(){
     * return mTarget.getLayoutParams().width;
     * }
     * public void setWidth(int width){
     * mTarget.getLayoutParams().width=width;
     * mTarget.requestLayout();
     * }
     * }
     * 使用时只需要操纵包类就可以调用get,set方法：
     * MyView mMyView=new MyView(mButton);
     * ObjectAnimator.ofInt(mMyView,"width",500).setDuration(500).start();
     */

    public float getInnerCircleRadiusProgress() {
        return mInnerCircleRadiusProgress;
    }

    public void setInnerCircleRadiusProgress(float innerCircleRadiusProgress) {
        mInnerCircleRadiusProgress = innerCircleRadiusProgress;

        // 从【ObjectAnimation.ofFloat】中获取到【开始值】和【终值】，
        // 通过【Property】传递到【setInnerCircleRadiusProgress】，
        // 【setInnerCircleRadiusProgress】把值传给【mInnerCircleRadiusProgress】，
        // 然后通知系统更新view。
        postInvalidate();
    }

    //---------------------------------------------------

    /**
     * （似乎是）Property作为中介传递数据。
     * <p/>
     * 问题是：ObjectAnimator是如何获取自定义属性名的。
     * <p/>
     * public static <T> ObjectAnimator ofFloat( T target,   Property<T, Float> property,   float... values)
     * <p/>
     * ObjectAnimator.ofFloat(mCvCircle, CircleView.OUTER_CIRCLE_RADIUS_PROGRESS, 0.1f, 1f);
     * ObjectAnimator.ofFloat(mCvCircle, CircleView.INNER_CIRCLE_RADIUS_PROGRESS, 0.1f, 1f);
     * <p/>
     * <T> The class on which the property is declared.
     * <V> The type that this property represents.
     */
    public static final Property<CircleView, Float> INNER_CIRCLE_RADIUS_PROGRESS =

            //public Property(Class<V> type, String name)
            new Property<CircleView, Float>(Float.class, "innerCircleRadiusProgress") {

                // public abstract V get(T object);
                @Override
                public Float get(CircleView object) {
                    return object.getInnerCircleRadiusProgress();
                }

                /**
                 *
                 * public void set(T object, V value)
                 *
                 * @param object //
                 * @param value 这个value是从哪里来的。(应该是)ObjectAnimator.ofFloat的时候传进去的value。
                 */
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
