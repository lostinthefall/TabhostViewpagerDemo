package com.atozmak.tabhostviewpagerdemo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;

import com.atozmak.tabhostviewpagerdemo.R;


/**
 * Created by Mak on 2016/4/5.
 */
public class Fragment03 extends Fragment implements View.OnClickListener {

    public static final int ANIM_DURATION = 1000;
    private Button btnAlpha, btnRotate, btnTranslate, btnScale, btnSet;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_03, container, false);

        init(v);

        return v;
    }

    private void init(View v) {
        btnAlpha = (Button) v.findViewById(R.id.btnAlpha);
        btnRotate = (Button) v.findViewById(R.id.btnRotate);
        btnTranslate = (Button) v.findViewById(R.id.btnTranslate);
        btnScale = (Button) v.findViewById(R.id.btnScale);
        btnSet = (Button) v.findViewById(R.id.btnSet);

        btnAlpha.setOnClickListener(this);
        btnRotate.setOnClickListener(this);
        btnTranslate.setOnClickListener(this);
        btnScale.setOnClickListener(this);
        btnSet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btnAlpha:
                AlphaAnimation aa = new AlphaAnimation(0, 1);
                aa.setDuration(ANIM_DURATION);
                btnAlpha.startAnimation(aa);
                break;

            case R.id.btnRotate:
                RotateAnimation ra = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0);
                ra.setDuration(ANIM_DURATION);
                btnRotate.startAnimation(ra);
                break;

            case R.id.btnTranslate:
                TranslateAnimation ta = new TranslateAnimation(0, 300, 0, 0);
                ta.setInterpolator(new AccelerateInterpolator());
                ta.setDuration(ANIM_DURATION);
                btnTranslate.startAnimation(ta);
                break;

            case R.id.btnScale:
                ScaleAnimation sa = new ScaleAnimation(1, 2, 1, 2);
                sa.setDuration(ANIM_DURATION);
                btnScale.startAnimation(sa);
                break;

            case R.id.btnSet:
                // new AnimationSet(true);
                // Pass true if all of the animations in this set
                // should use the interpolator associated with this AnimationSet.
                // Pass false if each animation should use its own interpolator.
                AnimationSet as = new AnimationSet(true);

                AlphaAnimation aas = new AlphaAnimation(0, 1);
                aas.setDuration(ANIM_DURATION);
                RotateAnimation ras = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0);
                ras.setDuration(ANIM_DURATION);
                TranslateAnimation tas = new TranslateAnimation(0, 200, 0, 0);
                tas.setDuration(ANIM_DURATION);
                ScaleAnimation sas = new ScaleAnimation(1, 2, 1, 2);
                sas.setDuration(ANIM_DURATION);

                as.addAnimation(aas);
                as.addAnimation(ras);
                as.addAnimation(tas);
                as.addAnimation(sas);

                as.setFillAfter(true);

                btnSet.startAnimation(as);


                break;
        }
    }
}
