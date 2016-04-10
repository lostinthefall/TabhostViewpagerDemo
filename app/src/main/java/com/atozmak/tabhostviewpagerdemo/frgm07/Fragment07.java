package com.atozmak.tabhostviewpagerdemo.frgm07;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.atozmak.tabhostviewpagerdemo.R;

/**
 * Created by Mak on 2016/4/8.
 */
public class Fragment07 extends Fragment {

    private LikeButtonView likeButtonView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_07, container, false);

        likeButtonView = (LikeButtonView) v.findViewById(R.id.like_button);

        likeButtonView.setOnLikeCLickListener(new OnLikeClickListener() {
            @Override
            public void onYellowStar() {
                Toast.makeText(Fragment07.this.getActivity(), "onYellowStar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onGreyStar() {
                Toast.makeText(Fragment07.this.getActivity(), "onGreyStar", Toast.LENGTH_SHORT).show();
            }
        });


        return v;
    }
}
