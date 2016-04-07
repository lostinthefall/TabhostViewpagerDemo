package com.atozmak.tabhostviewpagerdemo.frgm06;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.atozmak.tabhostviewpagerdemo.R;

/**
 * Created by Mak on 2016/4/7.
 */
public class CustomView extends View {

    Paint paint;

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.FILL);
        paint.setShader(new RadialGradient(0.0f, 0.0f, 600, getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2), Shader.TileMode.CLAMP));


    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawRect(0, 0, 500, 500, paint);
    }
}
