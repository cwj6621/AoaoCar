package com.jianguo.aoaocar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;
import com.jianguo.aoaocar.R;
/**
 * Created by 22077 on 2018/1/9.
 * 画竖直虚线
 */

public class DashedLineView extends View {
    public Context ctx;
    public DashedLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctx=context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(getResources().getColor(R.color.colorOrangePrimary));
        paint.setStrokeWidth(dip2px(ctx,4));
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(0, 900);
        PathEffect effects = new DashPathEffect(new float[]{6, 4, 4, 4}, 2);
        paint.setPathEffect(effects);
        canvas.drawPath(path, paint);
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}