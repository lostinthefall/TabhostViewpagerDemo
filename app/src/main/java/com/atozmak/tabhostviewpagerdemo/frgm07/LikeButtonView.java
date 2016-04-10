package com.atozmak.tabhostviewpagerdemo.frgm07;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.atozmak.tabhostviewpagerdemo.R;

/**
 * http://www.wangchenlong.org/2016/03/22/1603/224-star-explode-anim/
 */
public class LikeButtonView extends FrameLayout implements View.OnClickListener {

    private CircleView mCvCircle;
    private ImageView mIvStar;
    private DotsView mDvDots;

    private DecelerateInterpolator mDecelerateInterpolator;
    private OvershootInterpolator mOvershootInterpolator;
    private AccelerateDecelerateInterpolator mAccelerateDecelerateInterpolator;
    private AnimatorSet mAnimatorSet;

    private boolean mIsChecked = false;

    private OnLikeClickListener listener;


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

    public LikeButtonView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void setOnLikeCLickListener(OnLikeClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCvCircle = (CircleView) findViewById(R.id.like_button_cv_circle);
        mIvStar = (ImageView) findViewById(R.id.like_button_iv_star);
        mDvDots = (DotsView) findViewById(R.id.like_button_dv_dots);
    }

    private void init() {

        //不懂；
        isInEditMode();

        LayoutInflater.from(getContext()).inflate(R.layout.like_button_group, this, true);

        mDecelerateInterpolator = new DecelerateInterpolator();
        mOvershootInterpolator = new OvershootInterpolator(4);
        mAccelerateDecelerateInterpolator = new AccelerateDecelerateInterpolator();

        setOnClickListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            /**
             * 按下的时候，星星变小，记录状态为“按着”
             */
            case MotionEvent.ACTION_DOWN:
                mIvStar.animate().scaleX(0.7f).scaleY(0.7f).setDuration(150).setInterpolator(mOvershootInterpolator);
                // Pass true to set the View's internal state to "pressed",
                // or false to reverts the View's internal state from a previously set "pressed" state.
                setPressed(true);
                break;

            /**
             * 如果状态为“按着”和“手指在本控件内移动”，那就不管。
             * 否则，把状态设为“没按着”
             */
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                float y = event.getY();
                boolean isInside = (x > 0 && x < getWidth() && y > 0 && y < getHeight());
                // isPressed() : true if the view is currently pressed, false otherwise
                if (isPressed() != isInside) {
                    setPressed(isInside);
                }
                break;

            /**
             * 手指松开时，星星回复形状，
             * 如果状态为“按着”的话，那就播放动画。
             * 然后把状态设回“没按着”。
             */
            case MotionEvent.ACTION_UP:
                mIvStar.animate().scaleX(1).scaleY(1).setInterpolator(mOvershootInterpolator);
                if (isPressed()) {
                    // Call this view's OnClickListener, if it is defined.
                    performClick();
                    setPressed(false);
                }
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {

        // mIsChecked 用来记录黄星还是暗星。true：黄星。false：暗星。
        mIsChecked = !mIsChecked;
        mIvStar.setImageResource(mIsChecked ? R.drawable.ic_star_rate_on : R.drawable.ic_star_rate_off);

        //比如快速的2次点击，当第一次的动画还没结束的时候，第二次点击会把第一次的动画取消。
        if (mAnimatorSet != null) {
            mAnimatorSet.cancel();
        }

        if (mIsChecked) {
            /**
             * 动画开始前，把星星和圆点都设置为最小。
             */
            mIvStar.animate().cancel();
            mIvStar.setScaleX(0);
            mIvStar.setScaleY(0);
            mCvCircle.setInnerCircleRadiusProgress(0);
            mCvCircle.setOuterCircleRadiusProgress(0);
            mDvDots.setCurrentProgress(0);

            mAnimatorSet = new AnimatorSet();

            /**
             * CircleView
             *
             * 这是一个圆环，动画开始后，
             * 圆环的半径越来越大，圆环的环宽越来越窄，颜色由红色变为黄色。
             */
            ObjectAnimator outerCircleAnimator =
                    ObjectAnimator.ofFloat(mCvCircle, CircleView.OUTER_CIRCLE_RADIUS_PROGRESS, 0.1f, 1f);
            outerCircleAnimator.setDuration(250);
//              outerCircleAnimator.setDuration(1250);
            outerCircleAnimator.setInterpolator(mDecelerateInterpolator);
            ObjectAnimator innerCircleAnimator
                    = ObjectAnimator.ofFloat(mCvCircle, CircleView.INNER_CIRCLE_RADIUS_PROGRESS, 0.1f, 1f);
            innerCircleAnimator.setDuration(200);
//               innerCircleAnimator.setDuration(1200);
            innerCircleAnimator.setStartDelay(200);
            innerCircleAnimator.setInterpolator(mDecelerateInterpolator);

            /**
             * mIvStar
             */
            ObjectAnimator starScaleYAnimator = ObjectAnimator.ofFloat(mIvStar, ImageView.SCALE_Y, 0.2f, 1f);
            starScaleYAnimator.setDuration(350);
            starScaleYAnimator.setStartDelay(250);
            starScaleYAnimator.setInterpolator(mOvershootInterpolator);
            ObjectAnimator starScaleXAnimator = ObjectAnimator.ofFloat(mIvStar, ImageView.SCALE_X, 0.2f, 1f);
            starScaleXAnimator.setDuration(350);
            starScaleXAnimator.setStartDelay(250);
            starScaleXAnimator.setInterpolator(mOvershootInterpolator);

            /**
             * DotsView
             */
            ObjectAnimator dotsAnimator = ObjectAnimator.ofFloat(mDvDots, DotsView.DOTS_PROGRESS, 0, 1f);
            dotsAnimator.setDuration(900);
            dotsAnimator.setStartDelay(50);
            dotsAnimator.setInterpolator(mAccelerateDecelerateInterpolator);


            mAnimatorSet.playTogether(
                    outerCircleAnimator,
                    innerCircleAnimator,
                    starScaleYAnimator,
                    starScaleXAnimator,
                    dotsAnimator
            );

            /**
             * 比如快速的2次点击，当第一次的动画还没结束的时候，第二次点击会把第一次的动画取消。
             * 取消了之后，设置各个控件的状态。
             *
             * addListener(AnimatorListener listener)
             * public static interface AnimatorListener
             * public abstract class AnimatorListenerAdapter implements Animator.AnimatorListener
             * AnimatorListenerAdapter 重写了 AnimatorListener 的所有方法。
             * AnimatorListenerAdapter 中的都不是抽象方法，所以只选需要的方法来重写就ok。
             */
            mAnimatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationCancel(Animator animation) {
                    mIvStar.setScaleX(1);
                    mIvStar.setScaleY(1);

                    mCvCircle.setOuterCircleRadiusProgress(0);
                    mCvCircle.setInnerCircleRadiusProgress(0);

                    mDvDots.setCurrentProgress(0);
                }
            });
            mAnimatorSet.start();

            listener.onYellowStar();
        } else {
            listener.onGreyStar();
        }
    }
}

