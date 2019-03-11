package com.jianguo.aoaocar.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jianguo.aoaocar.R;

/**
 * Created by 22077 on 2018/1/24.
 */

public class AddNumberView extends LinearLayout{
   private Context mtx;
   private int number=1;
   private  OnNunberChangeListenter mOnNunberChangeListenter;
    public AddNumberView(Context context) {
        this(context, null);
    }

    public AddNumberView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mtx = context;
        init();
    }
    private void init() {
        LayoutInflater.from(mtx).inflate(R.layout.widget_add_number_view, this);
        Button reduct=(Button)findViewById(R.id.btn_reduct);
        final TextView    numberView=(TextView)findViewById(R.id.tv_number);
        Button    add=(Button)findViewById(R.id.btn_add);
        numberView.setText(number+"");
        reduct.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               if(number>1){
                   number--;
                   if(mOnNunberChangeListenter!=null){
                       mOnNunberChangeListenter.onNunberChange(number);
                       numberView.setText(number+"");
                   }
               }
            }
        });
        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                number++;
                if(mOnNunberChangeListenter!=null){
                    mOnNunberChangeListenter.onNunberChange(number);
                    numberView.setText(number+"");
                }
            }
        });
    }
    public  void setOnNunberChangeListenter(OnNunberChangeListenter onNunberChangeListenter){
        this.mOnNunberChangeListenter=onNunberChangeListenter;
    }


  public  interface OnNunberChangeListenter{
      void  onNunberChange(int number);
  }
}
