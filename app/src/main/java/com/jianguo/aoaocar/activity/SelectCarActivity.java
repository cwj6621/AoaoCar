package com.jianguo.aoaocar.activity;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.it.jianguo.common.Utils.Utils;
import com.it.jianguo.common.adapter.CommonAdapter;
import com.it.jianguo.common.adapter.ViewHolder;
import com.it.jianguo.common.base.BaseActivity;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.global.Constant;
import com.jianguo.aoaocar.model.Car;
import com.jianguo.aoaocar.view.pop.SelectCarPop;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by 22077 on 2017/12/1.
 */

public class SelectCarActivity extends BaseActivity{
    @Bind(R.id.lv_use_car)
    ListView  mUsecar;
    @Bind(R.id.lv_over_car)
    ListView  mOverCar;

    private List<Car>   mUsecars=new ArrayList<>();
    private List<Car>   mOverCars=new ArrayList<>();

    private CommonAdapter<Car> mUseCarAdapter;
    private CommonAdapter<Car> mOverCarsAdapter;
    @Override
    public int getLayoutId() {
        return  R.layout.activity_select_car;
    }

    @Override
    public void initView() {
       setLeftBtnDefaultOnClickListener();
        setTitle("选车");

        setUseCarView();
        setOverCarView();
    }

    private void setOverCarView() {
        mOverCars.add(new Car(0,"奇瑞QQ",192f,false));
        mOverCars.add(new Car(1,"奇瑞V5",180f,false));
        mOverCars.add(new Car(2,"奇瑞V3",150f,false));
        mOverCars.add(new Car(3,"奇瑞X1",192f,false));
        mOverCars.add(new Car(4,"奇瑞E3",180f,false));

        mOverCar.setAdapter(mOverCarsAdapter=new CommonAdapter<Car>(this,mOverCars, R.layout.row_list_select_over_car) {
            @Override
            public void convert(ViewHolder holder, Car item) {
                holder.setText(R.id.tv_car_name,item.carName);
                TextView price=holder.getView(R.id.tv_car_price);
                setSpannableText(item.price,price,R.color.colorHint);
            }
        });

    }

    private void setSpannableText(float price, TextView tv,int color) {
        String  desc="￥"+price+"/天起";
        final SpannableStringBuilder sb = new SpannableStringBuilder(desc);
        sb.setSpan(new AbsoluteSizeSpan(Utils.dp2Px(18)), 1, desc.length()-3, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sb.setSpan(new ForegroundColorSpan(color),1, desc.length()-3,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(sb);
    }

    private void setUseCarView() {
        mUsecars.add(new Car(0,"奇瑞X7",192f,true));
        mUsecars.add(new Car(1,"奇瑞X5",180f,true));
        mUsecars.add(new Car(2,"奇瑞E5",150f,true));
        mUsecar.setAdapter(mUseCarAdapter=new CommonAdapter<Car>(this,mUsecars,R.layout.row_list_select_car) {
            @Override
            public void convert(ViewHolder holder, Car item) {
                holder.setText(R.id.tv_car_name,item.carName);
                TextView price=holder.getView(R.id.tv_car_price);
                setSpannableText(item.price,price,Color.BLACK);
            }
        });
        mUsecar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SelectCarPop  pop=new SelectCarPop(SelectCarActivity.this);
            }
        });
    }
}
