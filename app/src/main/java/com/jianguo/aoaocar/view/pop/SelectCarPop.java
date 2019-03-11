package com.jianguo.aoaocar.view.pop;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.widget.GridView;

import com.it.jianguo.common.adapter.CommonAdapter;
import com.it.jianguo.common.adapter.ViewHolder;
import com.it.jianguo.common.commonwidget.NoScrollGridView;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.model.Car;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 22077 on 2017/12/1.
 */

public class SelectCarPop {
    private final Dialog mLoadingDialog;
    private Context mContext;
    private NoScrollGridView mServiceGrid;
    private CommonAdapter<String>  mAdapter;
    private List<String> mServices=new ArrayList<>();
    private CommonAdapter<Car> mCarAdapter;
    private List<Car> mCars = new ArrayList<>();
    private GridView mCarGrid;
    public SelectCarPop(Context context ) {
        this.mContext = context;
        mLoadingDialog =new Dialog(context, R.style.CustomDialog);
        mLoadingDialog.setCanceledOnTouchOutside(true);
        mLoadingDialog.setContentView(R.layout.widget_select_car_window);
        initCarView(mLoadingDialog);
        initView(mLoadingDialog);
        Window dialogWindow = mLoadingDialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER );
        mLoadingDialog.show();


    }

    private void initView(Dialog mLoadingDialog) {
        mServiceGrid=(NoScrollGridView) mLoadingDialog.findViewById(R.id.gv_service_detail);

        mServices.add("快速取车");
        mServices.add("免检车");
        mServices.add("手机支架/转换头免费用");
        mServices.add("含不计免赔");
        mServices.add("2年内新车");
        mServices.add("芝麻信用免费授权");
        mServices.add("送乘客座位险");
        mServices.add("送NBA定制手环");

        mServiceGrid.setAdapter(mAdapter=new CommonAdapter<String>(mContext,mServices,R.layout.row_car_service_item) {
            @Override
            public void convert(ViewHolder holder, String item) {
                        holder.setText(R.id.tv_service_detail,item);
            }
        });
    }
    private void initCarView(Dialog mLoadingDialog) {
        mCarGrid = (GridView) mLoadingDialog.findViewById(R.id.gv_car_detail);
        mCars.add(new Car(1, "奇瑞X1", R.mipmap.icon_car_detail));
        mCars.add(new Car(2, "奇瑞X3", R.mipmap.icon_car_detail));
        mCars.add(new Car(3, "奇瑞X5", R.mipmap.icon_car_detail));
        mCarGrid.setAdapter(mCarAdapter = new CommonAdapter<Car>(mContext, mCars, R.layout.row_grrd_selct_car_item) {
            @Override
            public void convert(ViewHolder holder, Car item) {
                holder.setText(R.id.tv_select_car_name,item.carName);
                holder.setBackgroundResource(R.id.lv_select_car,item.icon);
            }
        });

    }

}
