package com.jianguo.aoaocar.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.jianguo.aoaocar.R;


/**
 * Created by 22077 on 2017/11/22.
 */

public class CricleMeunView extends View{
    private final Resources mResources;
    private Paint circlePaint  ;           //画圆的笔，画弧线的笔，画圆中文字的笔
    private RectF rectF;                            //画弧线--弧线根据矩形生成
        //弧线度数变量
         public    float point_y=0;
         float point_x=0;
         float radius=0;
          public     boolean  isMonve=true;
    //中心圆的颜色
    private int centerCircleColor;
    private Context mtx;
    private Bitmap mBitmap;
    private Paint mBitPaint;

    public CricleMeunView(Context context, AttributeSet attrs) {
              super(context, attrs);
           mResources = getResources();
          initPaint();
        }

        private void initPaint() {
            //实心圆画笔
            circlePaint = new Paint();
            circlePaint.setColor(Color.WHITE);
            circlePaint.setStyle(Paint.Style.FILL);
            circlePaint.setAntiAlias(true);
          //  point_y = getHeight();
            mBitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mBitPaint.setFilterBitmap(true);
            mBitPaint.setDither(true);

           mBitmap= BitmapFactory.decodeResource(mResources, R.drawable.icon_warm);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            //这是圆心的坐标，这个是在xml设置引用的这个布局控件的大小的中心点到这个布局控件的最左边
            point_x = getWidth();
            point_y = getHeight();
            float xy=0;
           if(isMonve){
               xy=point_y+point_y/2;
           }else{
               xy=point_y+point_y/4;
           }
            //point_y = getHeight();
            //圆圈的半径 这里是100
            radius=  getHeight();       //改半径会造成联动的，因为围绕着mCircleWidth这个变量去开展绘制，要理解就要画图,大家拿起手上的纸画一下就好
            //设置圆弧              //注意先确定圆弧的宽度，然后根据得到这个布局的中心点，然后进行演算，得到半径。然后确定一个矩形来画圆弧。圆弧
            //画里面的圆
            canvas.drawCircle(point_x/2,  xy, point_x, circlePaint);
            canvas.drawBitmap(mBitmap,point_x/2,-point_y,mBitPaint);


        }
    public void setCirclePaint(boolean isShow){
        isMonve=isShow;
        invalidate();
    }
}



