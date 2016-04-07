package com.atozmak.tabhostviewpagerdemo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.Button;

import com.atozmak.tabhostviewpagerdemo.R;

/**
 * Created by Mak on 2016/4/5.
 */
public class Fragment01 extends Fragment {

    private Button btnAnimTween;
    private AnimationSet animationSet;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_01, container, false);

        init(v);
        initAnim();

        btnAnimTween.setOnClickListener(tweenListener);


        return v;
    }

    private void initAnim() {
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(1000);

        animationSet = new AnimationSet(true);
        animationSet.setDuration(1000);
        animationSet.addAnimation(rotateAnimation);
    }

    private void init(View v) {
        btnAnimTween = (Button) v.findViewById(R.id.btnAnimTween);

    }

    View.OnClickListener tweenListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            v.startAnimation(animationSet);
        }
    };



}
