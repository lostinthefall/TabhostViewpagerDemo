package com.atozmak.tabhostviewpagerdemo.frgm08;

import android.content.Context;
import android.content.MutableContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.atozmak.tabhostviewpagerdemo.mainActivity.LogUtils;

/**
 * Created by Mak on 2016/4/8.
 */
public class CustomView extends View {


    public static final String TAG = LogUtils.makLogTag("frgm08");

    private Paint paint;
    private RectF rectF;

    private Bitmap mBitmap;
    private Canvas mCanvas;

    public CustomView(Context context) {
        super(context);
        init(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

//        Log.v(TAG, "CustomView构造函数");

        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        //包围这个圆的矩形，简单说就是一个 wrapper。
        rectF = new RectF(50, 50, 1300, 1300);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        Log.v(TAG, "onSizeChanged");

        mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);

        mCanvas = new Canvas(mBitmap);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawOval(rectF, paint);
//        Log.v(TAG, "onDraw");


        mCanvas.drawColor(0xffffff, PorterDuff.Mode.CLEAR);

        mCanvas.drawBitmap(mBitmap, 0, 0, null);

    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        Log.v(TAG, "onFinishInflate");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        Log.v(TAG, "onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
//        Log.v(TAG, "onLayout");
    }
}
