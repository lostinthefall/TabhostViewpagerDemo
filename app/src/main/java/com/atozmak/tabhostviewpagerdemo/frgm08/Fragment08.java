package com.atozmak.tabhostviewpagerdemo.frgm08;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atozmak.tabhostviewpagerdemo.R;

/**
 * Created by Mak on 2016/4/8.
 */
public class Fragment08 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_08, container, false);
        return v;
    }


}
