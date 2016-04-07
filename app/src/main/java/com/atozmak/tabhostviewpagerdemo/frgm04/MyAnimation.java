package com.atozmak.tabhostviewpagerdemo.frgm04;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Mak on 2016/4/7.
 */
public class MyAnimation extends Animation {

    private float centerX;
    private float centerY;

    private int duration;

    private Camera camera;

    public MyAnimation(float centerX, float centerY, int duration) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.duration = duration;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);

        setDuration(duration);
        camera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        camera.save();
        camera.translate(
                100.0f - 100.f * interpolatedTime,
                150.0f * interpolatedTime - 150,
                80.0f - 80.0f * interpolatedTime
        );
        camera.rotateX(360 * interpolatedTime);
        camera.rotateY(360 * interpolatedTime);

        Matrix matrix = t.getMatrix();
        camera.getMatrix(matrix);
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
        camera.restore();


    }
}
