package com.atozmak.tabhostviewpagerdemo.mainActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;

import com.atozmak.tabhostviewpagerdemo.R;
import com.atozmak.tabhostviewpagerdemo.frgm07.Fragment07;
import com.atozmak.tabhostviewpagerdemo.frgm08.Fragment08;
import com.atozmak.tabhostviewpagerdemo.frgm09.Fragment09;
import com.atozmak.tabhostviewpagerdemo.mainActivity.MyFrgmPagerAdapter;
import com.atozmak.tabhostviewpagerdemo.frgm01.Fragment01;
import com.atozmak.tabhostviewpagerdemo.frgm02.Fragment02;
import com.atozmak.tabhostviewpagerdemo.frgm03.Fragment03;
import com.atozmak.tabhostviewpagerdemo.frgm04.Fragment04;
import com.atozmak.tabhostviewpagerdemo.frgm05.Fragment05;
import com.atozmak.tabhostviewpagerdemo.frgm06.Fragment06;

import java.util.ArrayList;
import java.util.List;

/**
 * TabHost and ViewPager by https://www.youtube.com/watch?v=GxEi_I3tv2k
 */
public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, TabHost.OnTabChangeListener {

    private ViewPager mViewPager;
    private List<Fragment> listFrgms;
    private MyFrgmPagerAdapter adapter;
    private TabHost tabHost;
    private HorizontalScrollView horizontalScrollView;
    private int SET_PAGE = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewPager();
        initTabHost();
        initPage();
    }

    private void initPage() {
        int currentPosition = SET_PAGE - 1;
        mViewPager.setCurrentItem(currentPosition);
        tabHost.setCurrentTab(currentPosition);
        View tabView = tabHost.getCurrentTabView();
        int scrollPos = tabView.getLeft()
                - (horizontalScrollView.getWidth() - tabView.getWidth()) / 2;
        horizontalScrollView.smoothScrollTo(scrollPos, 0);
    }

    private void initTabHost() {

        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        String[] tabNames = {"tab01", "属性动画", "tween_drawable",
                "自定义tween(弃用)", "小球", "shape", "星星", "画圆圈","插值器"};

        for (int i = 0; i < tabNames.length; i++) {
            TabHost.TabSpec tabSpec;
            tabSpec = tabHost.newTabSpec(tabNames[i]);
            tabSpec.setIndicator(tabNames[i]);
            tabSpec.setContent(new FakeContent(getApplicationContext()));
            tabHost.addTab(tabSpec);
        }
        tabHost.setOnTabChangedListener(this);

        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalView);
    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        listFrgms = new ArrayList<>();
        listFrgms.add(new Fragment01());
        listFrgms.add(new Fragment02());
        listFrgms.add(new Fragment03());
        listFrgms.add(new Fragment04());
        listFrgms.add(new Fragment05());
        listFrgms.add(new Fragment06());
        listFrgms.add(new Fragment07());
        listFrgms.add(new Fragment08());
        listFrgms.add(new Fragment09());

        adapter = new MyFrgmPagerAdapter(getSupportFragmentManager(), listFrgms);
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(this);
    }
    //--------------------------------------------------------

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int selectedPage) {
        tabHost.setCurrentTab(selectedPage);
    }

    //viewpager listener
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //tab listener
    @Override
    public void onTabChanged(String tabId) {
        int selectedItem = tabHost.getCurrentTab();
        mViewPager.setCurrentItem(selectedItem);

        View tabView = tabHost.getCurrentTabView();
        //scrollPos等于那个需要移动的view要移动的路程
        //现在看得不是很明白，要看看smoothScrollBy的源码才知道。先记住吧。
        //http://blog.csdn.net/crazy__chen/article/details/47174897
        int scrollPos = tabView.getLeft()
                - (horizontalScrollView.getWidth() - tabView.getWidth()) / 2;
        horizontalScrollView.smoothScrollTo(scrollPos, 0);


        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        Log.v("TAG", "screenWidth=" + screenWidth);//screenWidth=1080

        Log.v("TAG", "horizontalScrollView.getWidth()="
                + horizontalScrollView.getWidth()); //horizontalScrollView.getWidth()=1080

        Log.v("TAG", "tabView.getWidth()=" + tabView.getWidth());//tabView.getWidth()=240
    }

    //--------------------------------------------------------

    public class FakeContent implements TabHost.TabContentFactory {

        Context context;

        public FakeContent(Context context) {
            this.context = context;
        }

        @Override
        public View createTabContent(String tag) {

            View fakeView = new View(context);
            fakeView.setMinimumHeight(0);
            fakeView.setMinimumWidth(0);

            return fakeView;

        }
    }

    //--------------------------------------------------------


}
