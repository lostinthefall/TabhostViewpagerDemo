package com.atozmak.tabhostviewpagerdemo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atozmak.tabhostviewpagerdemo.R;

/**
 * Created by Mak on 2016/4/5.
 */
public class Fragment01 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_01, container,false);

        return v;
    }
}
