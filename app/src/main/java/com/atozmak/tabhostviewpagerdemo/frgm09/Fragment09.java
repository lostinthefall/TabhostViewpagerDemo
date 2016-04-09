package com.atozmak.tabhostviewpagerdemo.frgm09;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;

import com.atozmak.tabhostviewpagerdemo.R;

/**
 * Created by Mak on 2016/4/9.
 */
public class Fragment09 extends Fragment implements View.OnClickListener {

    private Button btnDecelerate, btnOvershoot, btnAccelerateDecelerate;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_09, container, false);

        btnDecelerate = (Button) v.findViewById(R.id.btnDecelerate);
        btnOvershoot = (Button) v.findViewById(R.id.btnOvershoot);
        btnAccelerateDecelerate = (Button) v.findViewById(R.id.btnAccelerateDecelerate);

        btnDecelerate.setOnClickListener(this);
        btnOvershoot.setOnClickListener(this);
        btnAccelerateDecelerate.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDecelerate:
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(btnDecelerate, "Y", 0, 800).setDuration(1000);
                animator1.setInterpolator(new DecelerateInterpolator());
                animator1.start();
                break;
            case R.id.btnOvershoot:
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(btnOvershoot, "Y", 0, 800).setDuration(1000);
                animator2.setInterpolator(new OvershootInterpolator());
                animator2.start();
                break;
            case R.id.btnAccelerateDecelerate:
                ObjectAnimator animator3 = ObjectAnimator.ofFloat(btnAccelerateDecelerate, "Y", 0, 800).setDuration(1000);
                animator3.setInterpolator(new AccelerateDecelerateInterpolator());
                animator3.start();
                break;
        }
    }
}
