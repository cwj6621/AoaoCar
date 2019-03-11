package com.jianguo.aoaocar.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jianguo.aoaocar.R;


/**
 * Created by 22077 on 2017/8/31.
 */
public class SelectConditionView extends LinearLayout{
    private  Context mContext;
    private ImageView iv;
    private TextView condition;
   // private boolean isSelect=false;
    public SelectConditionView(Context context) {
        super(context,null);
    }
    public SelectConditionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }
    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.widget_select_condition_view, this);
        condition=(TextView)findViewById(R.id.tv_condition);
        iv=(ImageView)findViewById(R.id.lv_select_condition);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
              //  isSelect=!isSelect;
               // isSelectCondtion(isSelect);
                if(onClickListener!=null) {
                    onClickListener.onOnClick(v);
                }
            }
        });
    }

   public void setCondtionContext(String condtion){
       condition.setText(condtion);
        isSelectCondtion(false);
   }
    public void isSelectCondtion(boolean isSelect){
        if(isSelect){
            iv.setBackgroundResource(R.mipmap.icon_shang_xai);
        }else{
            iv.setBackgroundResource(R.mipmap.icon_xia);
        }
    }

    private  OnSelectClickListener onClickListener;
    public void setOnClickListener(OnSelectClickListener onClickListener){
      this.onClickListener=onClickListener;
    }


    public interface OnSelectClickListener {
        /**
         * Callback method to be invoked when current item changed
         *
         */
        public void onOnClick(View v);
    }

}
