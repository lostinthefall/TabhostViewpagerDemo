package com.atozmak.tabhostviewpagerdemo.frgm06;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.ArcShape;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.PathShape;
import android.graphics.drawable.shapes.RectShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.atozmak.tabhostviewpagerdemo.R;

/**
 * shape参考http://blog.csdn.net/jjwwmlp456/article/details/46928859
 */
public class Fragment06 extends Fragment {

    TextView tvRectShape, tvRoundRectShape, tvOvalShape, tvArcShape, tvPathShape;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_06, container, false);

        init(v);

        initShape();

        return v;
    }

    //Shape只是用来设定控件的外形，与控件本身没有关系。
    private void initShape() {

        //矩形
        RectShape rectShape = new RectShape();
        ShapeDrawable shapeDrawable = new ShapeDrawable(rectShape);
        shapeDrawable.getPaint().setColor(Color.YELLOW);
        shapeDrawable.getPaint().setStyle(Paint.Style.FILL);
        tvRectShape.setBackground(shapeDrawable);

        //圆角矩形
        float[] outer = {50, 50, 50, 50, 50, 50, 50, 50};
        RoundRectShape roundRectShape = new RoundRectShape(outer, null, null);
        ShapeDrawable shapeDrawable1 = new ShapeDrawable(roundRectShape);
        shapeDrawable1.getPaint().setStyle(Paint.Style.STROKE);
        shapeDrawable1.getPaint().setColor(Color.BLUE);
        shapeDrawable1.getPaint().setStrokeWidth(20);
        tvRoundRectShape.setBackground(shapeDrawable1);

        //椭圆
        //而当View的宽高相等时，就绘出了圆
        OvalShape ovalShape = new OvalShape();
        ShapeDrawable shapeDrawable2 = new ShapeDrawable(ovalShape);
        shapeDrawable2.getPaint().setColor(Color.CYAN);
        shapeDrawable2.getPaint().setStyle(Paint.Style.STROKE);
        shapeDrawable2.getPaint().setStrokeWidth(50);
        tvOvalShape.setBackground(shapeDrawable2);

        //扇形
        ArcShape arcShape = new ArcShape(90, 270);
        ShapeDrawable shapeDrawable3 = new ShapeDrawable(arcShape);
        shapeDrawable3.getPaint().setColor(Color.MAGENTA);
        shapeDrawable3.getPaint().setStrokeWidth(10);
        tvArcShape.setBackground(shapeDrawable3);

        //路径
        Path path = new Path();
        path.moveTo(50, 0);
        path.lineTo(0, 50);
        path.lineTo(50, 100);
        path.lineTo(100, 50);
        path.lineTo(50, 0);
        // tvPathShape控件的宽度在xml指定了300dp，
        // new PathShape(path, 100, 100)中x轴方向指定了100dp，
        // 所以会在x轴方向拉长3倍，
        // 即原来path画的是转了45度角的正方形，
        // 现在展现出来的是x轴方向被拉长3倍的菱形。
        PathShape pathShape = new PathShape(path, 100, 100);
        ShapeDrawable shapeDrawable4 = new ShapeDrawable(pathShape);
        tvPathShape.setBackground(shapeDrawable4);
    }

    private void init(View v) {
        tvRectShape = (TextView) v.findViewById(R.id.tvRectShape);
        tvRoundRectShape = (TextView) v.findViewById(R.id.tvRoundRectShape);
        tvOvalShape = (TextView) v.findViewById(R.id.tvOvalShape);
        tvArcShape = (TextView) v.findViewById(R.id.tvArcShape);
        tvPathShape = (TextView) v.findViewById(R.id.tvPathShape);
    }
}
