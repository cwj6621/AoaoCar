package com.jianguo.aoaocar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import com.it.jianguo.common.Utils.Utils;

/**
 * Created by 22077 on 2017/11/22.
 */

public class HorizontalGridView extends GridView {
    public HorizontalGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public HorizontalGridView(Context context) {
        super(context);
    }

    public HorizontalGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        int childWidth = Utils.dip2px(70);
        int childHeight = Utils.dip2px(45);
        int lastPadding = Utils.dip2px(10);
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(expandSpec , heightMeasureSpec);

        //设置GridView的宽度
        setMeasuredDimension(childCount * childWidth + lastPadding, childHeight);
    }

}
