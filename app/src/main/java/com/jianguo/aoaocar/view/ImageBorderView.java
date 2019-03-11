package com.jianguo.aoaocar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by 22077 on 2017/12/5.
 */

public class ImageBorderView extends ImageView {
    private int mBoderWidth=2;
    private int mColor=Color.TRANSPARENT;
    public ImageBorderView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public ImageBorderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public ImageBorderView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        //获取控件需要重新绘制的区域
        Rect rect=canvas.getClipBounds();
        rect.bottom-=mBoderWidth;
        rect.right-=mBoderWidth;
        rect.left+=mBoderWidth;
        rect.top+=mBoderWidth;
        Paint paint=new Paint();
        paint.setColor(mColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mBoderWidth);
        canvas.drawRect(rect, paint);
    }

    /**
     *设置边框宽度
     * @param boder
     */
  public void setBorderWidth(int color){
      this.mColor=color;
      invalidate();
  }
}
