package com.jianguo.aoaocar.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.it.jianguo.common.Utils.Utils;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.activity.MainActivity;

/**
 * Created by 22077 on 2018/1/15.
 */

public class WelcomeView extends LinearLayout{
    private Context mtx;
    private int logo;
    private String desc;
    private String option;
    private boolean isOption=false;

    public WelcomeView(Context context) {
        super(context);
        this.mtx=context;
        initView();
    }
    public WelcomeView(Context context,int logo,String desc,String option,boolean isOption) {
        super(context);
        this.mtx=context;
        this.logo=logo;
        this.desc=desc;
        this.option=option;
        this.isOption=isOption;
        initView();
    }
    private void initView() {
        LayoutInflater.from(mtx).inflate(R.layout.widget_welcome_view, this);
        TextView    descView=(TextView)findViewById(R.id.tv_welcome_desc);
        TextView    doptionView=(TextView)findViewById(R.id.tv_welcome_doption);
        ImageView   iv=(ImageView)findViewById(R.id.tv_welcome_logo);
        iv.setBackgroundResource(logo);
        descView.setText(desc);
        if(isOption){
            doptionView.setBackgroundResource(R.drawable.bg_gray_background_border);
            doptionView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utils.toActivity(mtx, MainActivity.class);
                }
            });
        }
        doptionView.setText(option);
    }
}
