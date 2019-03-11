package com.jianguo.aoaocar.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jianguo.aoaocar.R;

/**
 * Created by Administrator on 2017/3/18.
 */
public class MapCenterPotion extends LinearLayout {
    private  LinearLayout ProgressBar;
    private TextView textView;
    private ImageView imageView;
    public MapCenterPotion(Context context) {
        super(context);
    }
    public MapCenterPotion(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.widget_map_center_marker, this);
        ProgressBar = (LinearLayout) findViewById(R.id.ll_ProgressBar);
        imageView = (ImageView) findViewById(R.id.imageView1);
        textView = (TextView) findViewById(R.id.textView1);
    }
    public void setImageResource(int resId) {
        imageView.setImageResource(resId);
    }
    /**
     * 设置显示的文字
     */
    public void setTextViewText(String text) {
        textView.setText(text);
    }

    public void setProgressBar(boolean isSaerch){
        if (isSaerch){
            textView.setVisibility(View.GONE);
            ProgressBar.setVisibility(View.VISIBLE);
        }else{
            ProgressBar.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }
    }
}