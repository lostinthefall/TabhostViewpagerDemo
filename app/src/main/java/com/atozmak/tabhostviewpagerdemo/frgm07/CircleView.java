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
import android.view.View;

/**
 * 圆形视图, 外圆是实心圆圈, 颜色渐变; 内圆是擦除效果.
 * <p/>
 * Created by Mak on 2016/4/8.
 */
public class CircleView extends View {

    private static final int START_COLOR = 0xffff5722;
    private static final int END_COLOR = 0xffffc107;

    private ArgbEvaluator mArgbEvaluator;

    private Paint mCirclePaint;//圆形试图
    private Paint mMaskPaint;//掩盖视图

    private Canvas mTempCanvas;//中间画布
    private Bitmap mTempBitmap;//中间画图

    private int mMaxCircleSize;//最大圆环大小

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

    private void init() {
        mCirclePaint = new Paint();
        mCirclePaint.setStyle(Paint.Style.FILL);

        mMaskPaint = new Paint();//消失的效果
        //橡皮擦的效果。
        mMaskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        mArgbEvaluator = new ArgbEvaluator();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);



    }
}
