package com.atozmak.tabhostviewpagerdemo.mainActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Mak on 2016/4/5.
 */
public class MyFrgmPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> listFrgms;

    public MyFrgmPagerAdapter(FragmentManager fm, List<Fragment> listFrgms) {
        super(fm);

        this.listFrgms = listFrgms;
    }

    @Override
    public Fragment getItem(int position) {
        return listFrgms.get(position);
    }

    @Override
    public int getCount() {
        return listFrgms.size();
    }
}
