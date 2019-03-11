package com.jianguo.aoaocar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.it.jianguo.common.Utils.Utils;
import com.jianguo.aoaocar.R;

/**
 * Created by 22077 on 2018/1/18.
 */

public class TextStepView extends View{
    private int maxStep=5;
    private int proStep;
    private int interval;
    private float startX;
    private float stopX;
    private float bgCenterY;
    private int default_color;
    private int selected_color;
    private int textPadding;
    private String[] titles = {"预约", "取车", "用车", "支付", "完成"};
    private Paint textSelectPaint;
    private Paint textPaint;
    public TextStepView(Context context) {
        super(context);
        init(context, null);
    }

    public TextStepView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TextStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }
    private void init(final Context context, AttributeSet attrs) {
        if (context == null || attrs == null) {
            return;
        }
        TypedArray typedArray = null;
        try {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextStepView);
            proStep = typedArray.getInt(R.styleable.TextStepView_prostep, 0);
            default_color = typedArray.getColor(R.styleable.TextStepView_default_color, 0);
            selected_color = typedArray.getColor(R.styleable.TextStepView_selected_color, 0);
            textPadding = (int) typedArray.getDimension(R.styleable.TextStepView_text_padding, 0);
        } finally {
            if (typedArray != null) {
                typedArray.recycle();
            }
        }

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(default_color);
        textPaint.setStrokeWidth(0);
        textPaint.setTextSize(35);
        textPaint.setTextAlign(Paint.Align.CENTER);

        textSelectPaint = new Paint();
        textSelectPaint.setAntiAlias(true);
        textSelectPaint.setStyle(Paint.Style.FILL);
        textSelectPaint.setColor(selected_color);
        textSelectPaint.setStrokeWidth(0);
        textSelectPaint.setTextSize(40);
        textSelectPaint.setTextAlign(Paint.Align.CENTER);

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int bgWidth;
        if (widthMode == MeasureSpec.EXACTLY) {
            bgWidth = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        } else
            bgWidth = Utils.dip2px(getContext(), 300);

        int bgHeight;
        if (heightMode == MeasureSpec.EXACTLY) {
            bgHeight = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
        } else
            bgHeight = Utils.dip2px(getContext(), 40);
        float left = getPaddingLeft() + 40;
        stopX = bgWidth - 40;
        startX = left;
        bgCenterY = bgHeight / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        interval = (int) ((stopX - startX) / (maxStep - 1));
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        for (int i = 0; i < maxStep; i++) {
            if (i < proStep) {
                //setPaintColor(i);
                if (null != titles && i<titles.length)
                    if (i==(proStep-1)){
                        canvas.drawText(titles[i], startX + (i * interval), bgCenterY - textPadding, textSelectPaint);
                    }else{
                        canvas.drawText(titles[i], startX + (i * interval), bgCenterY - textPadding, textPaint);
                    }
            } else {
                if (null != titles && i<titles.length) {
                    String title = titles[i];
                    if (null == title) continue;
                    canvas.drawText(title, startX + (i * interval), bgCenterY - textPadding, textPaint);
                }
            }
        }
    }
    /**
     * 设置进度
     */
    public void setProstep( int step) {
        if (this.proStep != step) {
            this.proStep = step;
            invalidate();
        }
    }
}
