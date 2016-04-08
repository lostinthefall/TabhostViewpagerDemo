package com.atozmak.tabhostviewpagerdemo.frgm07;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.atozmak.tabhostviewpagerdemo.R;

/**
 * Created by Mak on 2016/4/8.
 */
public class LikeButtonView extends FrameLayout implements View.OnClickListener {

    private CircleView mCvCircle;
    private ImageView mIvStar;
    private DotsView mDvDots;

    private DecelerateInterpolator mDecelerateInterpolator;
    private OvershootInterpolator mOvershootInterpolator;
    private AccelerateDecelerateInterpolator mAccelerateDecelerateInterpolator;
    private AnimatorSet mAnimatorSet;

    private boolean mIsChecked;


    public LikeButtonView(Context context) {
        super(context);
        init();
    }

    public LikeButtonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LikeButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    private void init() {

        mCvCircle = (CircleView) findViewById(R.id.like_button_cv_circle);
        mIvStar = (ImageView) findViewById(R.id.like_button_iv_star);
        mDvDots = (DotsView) findViewById(R.id.like_button_dv_dots);

        //不懂；
        isInEditMode();

        LayoutInflater.from(getContext()).inflate(R.layout.like_button_group, this, true);

        mDecelerateInterpolator = new DecelerateInterpolator();
        mOvershootInterpolator = new OvershootInterpolator(4);
        mAccelerateDecelerateInterpolator = new AccelerateDecelerateInterpolator();

        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        mIsChecked = !mIsChecked;
        mIvStar.setImageResource(mIsChecked ? R.drawable.ic_star_rate_on : R.drawable.ic_star_rate_off);

        if (mAnimatorSet != null) {
            mAnimatorSet.cancel();
        }

        if (mIsChecked) {

            mCvCircle.setInnerCircleRadiusProgress(0);
            mCvCircle.setOuterCircleRadiusProgress(0);

            mAnimatorSet = new AnimatorSet();


            ObjectAnimator outerCircleAnimator =
                    ObjectAnimator.ofFloat(
                            mCvCircle, CircleView.OUTER_CIRCLE_RADIUS_PROGRESS,
                            0.1f, 1f);
            outerCircleAnimator.setDuration(250);
            outerCircleAnimator.setInterpolator(mDecelerateInterpolator);


            ObjectAnimator innerCircleAnimator =
                    ObjectAnimator.ofFloat(
                            mCvCircle, CircleView.INNER_CIRCLE_RADIUS_PROGRESS,
                            0.1f, 1f
                    );
            innerCircleAnimator.setDuration(200);
            innerCircleAnimator.setStartDelay(200);
            innerCircleAnimator.setInterpolator(mDecelerateInterpolator);


            mAnimatorSet.playTogether(
                    outerCircleAnimator,
                    innerCircleAnimator


            );


            mAnimatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    mCvCircle.setOuterCircleRadiusProgress(0);
                    mCvCircle.setInnerCircleRadiusProgress(0);
                }
            });

            mAnimatorSet.start();

        }


    }


}
