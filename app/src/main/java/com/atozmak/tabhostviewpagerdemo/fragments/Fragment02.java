package com.atozmak.tabhostviewpagerdemo.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;

import com.atozmak.tabhostviewpagerdemo.R;

/**
 * Created by Mak on 2016/4/5.
 */
public class Fragment02 extends Fragment {

    private Button btnAnimProperty, btnAnimNoTypeEvaluator, btnAnimTypeEvaluator, btn3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_02, container, false);

        init(v);

        btnAnimProperty.setOnClickListener(propertyListener);
        btnAnimTypeEvaluator.setOnClickListener(typeEvaluatorListener);
        btnAnimNoTypeEvaluator.setOnClickListener(noTypeEvaluatorListener);
        btn3.setOnClickListener(btn3Listener);

        return v;
    }

    private void init(View v) {
        btnAnimProperty = (Button) v.findViewById(R.id.btnAnimProperty);
        btnAnimTypeEvaluator = (Button) v.findViewById(R.id.btnAnimTypeEvaluator);
        btnAnimNoTypeEvaluator = (Button) v.findViewById(R.id.btnAnimNoTypeEvaluator);
        btn3 = (Button) v.findViewById(R.id.btn3);
    }

    View.OnClickListener propertyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Animator animator = ObjectAnimator.ofFloat(v, "x", 0, 25, -25, 25, -25, 25, -25, 25, -25, 0).setDuration(500);
            animator.setInterpolator(new LinearInterpolator());
            animator.start();
        }
    };

    View.OnClickListener noTypeEvaluatorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // ObjectAnimator.ofFloat(v, "y", 0, 300).setDuration(1000).start();
            anotherTest(v);
        }
    };

    private void anotherTest(View v) {

        //属性动画在哪里改变旋转中心的值。
        ObjectAnimator.ofFloat(v, "rotation", 0,360).setDuration(1000).start();


   /*     ValueAnimator va = ValueAnimator.ofFloat(0, 1);
        va.setDuration(1000);
        va.setTarget(btnAnimNoTypeEvaluator);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                btnAnimNoTypeEvaluator.setRotation(180 * value);
            }
        });
        va.start();*/
    }

    View.OnClickListener typeEvaluatorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //
            ObjectAnimator animator = ObjectAnimator.ofFloat(v, "y", 0, 300).setDuration(1000);

            animator.setEvaluator(new TypeEvaluator<Float>() {
                @Override
                public Float evaluate(float fraction, Float startValue, Float endValue) {
                    return startValue + 0.008f * (fraction * (endValue - startValue)) * (fraction * (endValue - startValue));
                }
            });
            animator.start();
        }
    };

    View.OnClickListener btn3Listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //结果和【ObjectAnimator.ofFloat(v, "y", 0, 300).setDuration(1000).start();】一样
            ObjectAnimator animator = ObjectAnimator.ofFloat(v, "y", 0, 300).setDuration(1000);
            animator.setEvaluator(new TypeEvaluator<Float>() {
                @Override
                public Float evaluate(float fraction, Float startValue, Float endValue) {
                    //fraction是把自定义的数值映射成0-1的值，说白了就是一个百分比的值，这里就是1/300.
                    return fraction * (endValue - startValue);
                }
            });
            animator.start();
        }
    };
}
