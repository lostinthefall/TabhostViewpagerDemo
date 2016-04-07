package com.atozmak.tabhostviewpagerdemo.frgm04;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.atozmak.tabhostviewpagerdemo.R;

/**
 * Created by Mak on 2016/4/5.
 */
public class Fragment04 extends Fragment {

    private ListView lvContainer;
    private ArrayAdapter arrayAdapter;
    private Context mContext;
    DisplayMetrics dm;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_04, container, false);

        init(v);

        return v;
    }

    private void init(View v) {

        WindowManager wm = (WindowManager) v.getContext().getSystemService(Context.WINDOW_SERVICE);
        dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);

        lvContainer = (ListView) v.findViewById(R.id.lvContainer);

        String[] list = {"fdahfd", "fdahfd", "fdahfd", "fdahfd", "fdahfd"};

        arrayAdapter = new ArrayAdapter(v.getContext(), R.layout.item_frgm04_lv, list);

        lvContainer.setAdapter(arrayAdapter);

        lvContainer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                lvContainer.startAnimation(new MyAnimation(dm.xdpi / 2, dm.ydpi / 2, 1000));
            }
        });


    }
}
