package com.atozmak.tabhostviewpagerdemo.frgm05;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.atozmak.tabhostviewpagerdemo.R;

import java.util.ArrayList;

/**
 * Created by Mak on 2016/4/5.
 */
public class Fragment05 extends Fragment {

    // 定义小球的大小的常量
    static final float BALL_SIZE = 150F;
    // 定义小球从屏幕上方下落到屏幕底端的总时间
    static final float FULL_TIME = 5000;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_05, container, false);

        LinearLayout containerll = (LinearLayout)
                v.findViewById(R.id.frgm05ll);
        // 设置该窗口显示MyAnimationView组件
        containerll.addView(new MyAnimationView(v.getContext()));


        return v;
    }

    public class MyAnimationView extends View implements AnimatorUpdateListener {

        public final ArrayList<ShapeHolder> balls = new ArrayList<>();

        public MyAnimationView(Context context) {
            super(context);
            setBackgroundColor(Color.WHITE);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            if (event.getAction() != MotionEvent.ACTION_DOWN
                    && event.getAction() != MotionEvent.ACTION_MOVE) {
                return false;
            }

            ShapeHolder newBall = addBall(event.getX(), event.getY());

            // 计算小球下落动画开始时的y坐标
            float startY = newBall.getY();
            // 计算小球下落动画结束时的y坐标（落到屏幕最下方，就是屏幕高度减去小球高度）
            float endY = getHeight() - BALL_SIZE;

            // 获取你的View的高度
            float h = (float) getHeight();

            float eventY = event.getY();
            // 计算动画的持续时间
            int duration = (int) (FULL_TIME * ((h - eventY) / h));

            //----------------------------------------------

 /*           float startY = newBall.getY();
            float endY = getHeight() - 50f;
            float h = (float) getHeight();
            float eventY = event.getY();
            int duration = (int) (500 * ((h - eventY) / h));
            ValueAnimator bounceAnim = ObjectAnimator.ofFloat(newBall, "y", startY, endY);
            bounceAnim.setDuration(duration);
            bounceAnim.setInterpolator(new AccelerateInterpolator());
            ValueAnimator squashAnim1 = ObjectAnimator.ofFloat(newBall, "x", newBall.getX(),
                    newBall.getX() - 25f);
            squashAnim1.setDuration(duration / 4);
            squashAnim1.setRepeatCount(1);
            squashAnim1.setRepeatMode(ValueAnimator.REVERSE);
            squashAnim1.setInterpolator(new DecelerateInterpolator());
            ValueAnimator squashAnim2 = ObjectAnimator.ofFloat(newBall, "width", newBall.getWidth(),
                    newBall.getWidth() + 50);
            squashAnim2.setDuration(duration / 4);
            squashAnim2.setRepeatCount(1);
            squashAnim2.setRepeatMode(ValueAnimator.REVERSE);
            squashAnim2.setInterpolator(new DecelerateInterpolator());
            ValueAnimator stretchAnim1 = ObjectAnimator.ofFloat(newBall, "y", endY,
                    endY + 25f);
            stretchAnim1.setDuration(duration / 4);
            stretchAnim1.setRepeatCount(1);
            stretchAnim1.setInterpolator(new DecelerateInterpolator());
            stretchAnim1.setRepeatMode(ValueAnimator.REVERSE);
            ValueAnimator stretchAnim2 = ObjectAnimator.ofFloat(newBall, "height",
                    newBall.getHeight(), newBall.getHeight() - 25);
            stretchAnim2.setDuration(duration / 4);
            stretchAnim2.setRepeatCount(1);
            stretchAnim2.setInterpolator(new DecelerateInterpolator());
            stretchAnim2.setRepeatMode(ValueAnimator.REVERSE);
            ValueAnimator bounceBackAnim = ObjectAnimator.ofFloat(newBall, "y", endY,
                    startY);
            bounceBackAnim.setDuration(duration);
            bounceBackAnim.setInterpolator(new DecelerateInterpolator());
            // Sequence the down/squash&stretch/up animations
            AnimatorSet bouncer = new AnimatorSet();
            bouncer.play(bounceAnim).before(squashAnim1);
            bouncer.play(squashAnim1).with(squashAnim2);
            bouncer.play(squashAnim1).with(stretchAnim1);
            bouncer.play(squashAnim1).with(stretchAnim2);
            bouncer.play(bounceBackAnim).after(stretchAnim2);*/

            //----------------------------------------------


            ValueAnimator fallAnim = ObjectAnimator.ofFloat(newBall, "y", startY, endY);
            fallAnim.setDuration(duration);
            fallAnim.setInterpolator(new BounceInterpolator());

            fallAnim.addUpdateListener(this);

            // 定义对newBall对象的alpha属性执行从1到0的动画（即定义渐隐动画）
            ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(newBall
                    , "alpha", 1f, 1f);
            // 设置动画持续时间
            fadeAnim.setDuration(250);

            // 为fadeAnim动画添加监听器
            fadeAnim.addListener(new AnimatorListenerAdapter() {
                // 当动画结束时
                @Override
                public void onAnimationEnd(Animator animation) {
                    // 动画结束时将该动画关联的ShapeHolder删除
                    balls.remove(((ObjectAnimator) animation).getTarget());
                }
            });


            // 定义一个AnimatorSet来组合动画
            AnimatorSet animatorSet = new AnimatorSet();
            // 指定在播放fadeAnim之前，先播放fallAnim动画
            animatorSet.play(fallAnim).before(fadeAnim);
            // 开发播放动画
            animatorSet.start();
            return true;
        }

        private ShapeHolder addBall(float x, float y) {

            // 创建一个椭圆
            // java.lang.Object
            //   ↳	android.graphics.drawable.shapes.Shape
            //   ↳	android.graphics.drawable.shapes.RectShape
            //   ↳	android.graphics.drawable.shapes.OvalShape
            OvalShape circle = new OvalShape();

            // 设置该椭圆的宽、高
            // Resizes the dimensions of this shape.
            circle.resize(BALL_SIZE, BALL_SIZE);

            // 将椭圆包装成Drawable对象
            ShapeDrawable drawable = new ShapeDrawable(circle);

            // 创建一个ShapeHolder对象
            ShapeHolder shapeHolder = new ShapeHolder(drawable);

            // 设置ShapeHolder的x、y坐标
            shapeHolder.setX(x - BALL_SIZE / 2);
            shapeHolder.setY(y - BALL_SIZE / 2);

            int red = (int) (Math.random() * 2550);
            int green = (int) (Math.random() * 2550);
            int blue = (int) (Math.random() * 2550);
            // 将red、green、blue三个随机数组合成ARGB颜色
            int color = 0xff000000 + red << 16 | green << 8 | blue;

            // 获取drawable上关联的画笔
            Paint paint = drawable.getPaint();
            // 将red、green、blue三个随机数除以4得到商值组合成ARGB颜色
            int darkColor = 0xff000000 | red / 4 << 16
                    | green / 4 << 8 | blue / 4;

            // 创建圆形渐变
            RadialGradient gradient = new RadialGradient(
                    37.5f, 12.5f, BALL_SIZE, color, darkColor
                    , Shader.TileMode.CLAMP);
            paint.setShader(gradient);

            // 为shapeHolder设置paint画笔
            shapeHolder.setPaint(paint);
            balls.add(shapeHolder);

            return shapeHolder;
        }

        @Override
        protected void onDraw(Canvas canvas) {

            for (int i = 0; i < balls.size(); ++i) {
                ShapeHolder shapeHolder = balls.get(i);

                canvas.save();
                // 坐标变换：将画布坐标系统平移到shapeHolder的X、Y坐标处
                canvas.translate(shapeHolder.getX()
                        , shapeHolder.getY());
                // 将shapeHolder持有的圆形绘制在Canvas上
                shapeHolder.getShape().draw(canvas);
                // 恢复Canvas坐标系统
                canvas.restore();
            }
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            this.invalidate();
        }
    }

}
