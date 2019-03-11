package com.jianguo.aoaocar.fragment.drivingrecord;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.it.jianguo.common.base.BaseFragment;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.fragment.drivingrecord.base.BaseDrivingFragment;

import butterknife.Bind;

/**
 * Created by 22077 on 2018/1/5.
 */

public class TakeCarFragment extends BaseDrivingFragment {
    @Bind(R.id.tv_open_car_door)
    TextView open_car_door;
    @Override
    protected void fetchData() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_driving_record_takecar;
    }

    @Override
    protected void initView() {
        open_car_door.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loanListener!=null){
                    loanListener.loanClick(true,2,0.55f);
                }
            }
        });
    }
}
