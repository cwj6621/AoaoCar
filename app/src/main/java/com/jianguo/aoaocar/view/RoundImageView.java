package com.jianguo.aoaocar.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.jianguo.aoaocar.R;

/**
 * Created by 22077 on 2017/11/29.
 */

@SuppressLint("AppCompatCustomView")
public class RoundImageView extends ImageView{

    private int mBorderOutThickness = 0;
   private int mBorderInThickness = 0;
    private Context mContext;
    private int defaultColor= 0xFFFFFFFF;
       // 外圆边框颜色  
     private int mBorderOutsideColor = 0;
      // 内圆边框颜色  
      private int mBorderInsideColor = 0;
     // RoundImageView控件默认的长、宽  
          private int defaultWidth=0;
       private int defaultHeight= 0;

    public RoundImageView(Context context) {
        super(context);
        mContext=context;
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // 设置RoundImageView的属性值，比如颜色，宽度等
        mContext=context;
        setRoundImageViewAttributes(attrs);
    }
    public RoundImageView(Context context, @Nullable AttributeSet attrs,int defStyle) {
        super(context, attrs,defStyle);
        mContext=context;
        setRoundImageViewAttributes(attrs);
    }

    private void setRoundImageViewAttributes(AttributeSet attrs) {
        TypedArray a=mContext.obtainStyledAttributes(attrs, R.styleable.round_image_view);

        mBorderInThickness=a.getDimensionPixelSize(R.styleable.round_image_view_border_in_width,0);
        mBorderOutThickness=a.getDimensionPixelSize(R.styleable.round_image_view_border_out_width,0);
        mBorderOutsideColor=a.getColor(R.styleable.round_image_view_border_outcolor, defaultColor);
        mBorderInsideColor = a.getColor(R.styleable.round_image_view_border_incolor,defaultColor);
        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable=getDrawable();
        if(drawable==null){
            return;
        }
        if (getWidth()==0||getHeight()==0){
            return;
        }
         this.measure(0,0);
        if(drawable.getClass()==NinePatchDrawable.class){
            return;
        }
        Bitmap b = ((BitmapDrawable)drawable).getBitmap();
        Bitmap  bitmap =  b.copy(Bitmap.Config.ARGB_8888, true);
        if (defaultWidth == 0) {
             defaultWidth = getWidth();
         }
        if (defaultHeight == 0){
            defaultHeight = getHeight();
        }
        int radius=0;
      //  int mBorderThickness=mBorderInThickness+mBorderOutThickness;
     // 这里的判断是如果内圆和外圆设置的颜色值不为空且不是默认颜色，就定义画两个圆框，分别为内圆和外圆边框
        if(mBorderInsideColor !=defaultColor&& mBorderOutsideColor != defaultColor){
              radius=(defaultWidth<defaultHeight?defaultWidth:defaultHeight)/2-(mBorderInThickness+mBorderOutThickness);
          // 画内圆  
             drawCircleBorder(canvas, radius+mBorderInThickness/2,mBorderInThickness,mBorderInsideColor);
          // 画外圆  
             drawCircleBorder(canvas, radius+mBorderInThickness+mBorderOutThickness/2, mBorderOutThickness,mBorderOutsideColor);

        }else if(mBorderInsideColor !=defaultColor&& mBorderOutsideColor == defaultColor){

               radius=(defaultWidth<defaultHeight?defaultWidth:defaultHeight)/2-mBorderInsideColor;
               drawCircleBorder(canvas, radius+mBorderInsideColor/2,mBorderInsideColor,mBorderInsideColor);

        }else if (mBorderInsideColor == defaultColor&&mBorderOutsideColor!=defaultColor){

                 radius=(defaultWidth<defaultHeight?defaultWidth:defaultHeight)/2-mBorderOutsideColor;
                  drawCircleBorder(canvas, radius+mBorderOutsideColor/2,mBorderOutsideColor,mBorderOutsideColor);

        }else {// 这种情况是没有设置属性颜色的情况下，即没有边框的情况  
            radius = (defaultWidth < defaultHeight ? defaultWidth : defaultHeight) / 2;
        }
        Bitmap roundBitmap  = getCroppedRoundBitmap(bitmap, radius);
        canvas.drawBitmap(roundBitmap,defaultWidth/2-radius, defaultHeight/2-radius,null);
     }
         /** 
      * 获取裁剪后的圆形图片 
      *  
      * @param bmp 
      * @param radius 
      *            半径 
      * @return 
      */
    public  Bitmap getCroppedRoundBitmap(Bitmap  bmp, int radius) {
        Bitmap  scaledSrcBmp;
       int diameter= radius*2;
      // 为了防止宽高不相等，造成圆形图片变形，因此截取长方形中处于中间位置最大的正方形图片
         int bmpWidth= bmp.getWidth();
         int bmpHeight=bmp.getHeight();

        int squareWidth= 0, squareHeight=0;
        int x=0,y=0;
        Bitmap squareBitmap;
        if(bmpHeight>bmpWidth) {// 高大于宽  
            squareWidth=squareHeight=bmpWidth;
             x=0;
            y=(bmpHeight-bmpWidth)/2;
            // 截取正方形图片
            squareBitmap=Bitmap.createBitmap(bmp,x,y,squareWidth,squareHeight);
        }else if(bmpWidth>bmpHeight){//宽大于高
            squareWidth=squareHeight=bmpHeight;
            x=(bmpWidth-bmpHeight)/2;
            y=0;
            squareBitmap=Bitmap.createBitmap(bmp,x,y,squareWidth,squareHeight);
        }else{
            squareBitmap=bmp;
        }
           if(squareBitmap.getWidth()!=diameter||squareBitmap.getHeight()!=diameter){
               scaledSrcBmp=Bitmap.createScaledBitmap(squareBitmap, diameter, diameter, true);
          }else{
                 scaledSrcBmp=squareBitmap;
            }
        Bitmap output=Bitmap.createBitmap(scaledSrcBmp.getWidth(), scaledSrcBmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint =new Paint();
         Rect rect=new Rect(0, 0, scaledSrcBmp.getWidth(), scaledSrcBmp.getHeight());
        paint.setAntiAlias(true);
       paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);

        canvas.drawCircle(scaledSrcBmp.getWidth()/2,
         scaledSrcBmp.getHeight()/2, scaledSrcBmp.getWidth()/2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
         canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);
     bmp=null;
     squareBitmap = null;
    scaledSrcBmp=null;
      return  output;
    }

        /** 
      * 画边缘的圆，即内圆或者外圆 
      */
    private void drawCircleBorder(Canvas canvas, int  radius, int border, int color) {
        Paint paint=new Paint();
        paint.setAntiAlias(true);;
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(border);
        canvas.drawCircle(defaultWidth/2,defaultHeight/2, radius,paint);
    }
}
