package com.jianguo.aoaocar.fragment.drivingrecord;

import android.view.View;
import android.widget.TextView;

import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.fragment.drivingrecord.base.BaseDrivingFragment;

import butterknife.Bind;

/**
 * Created by 22077 on 2018/1/5.
 */
public class DrivingCarFragment extends BaseDrivingFragment {
    @Bind(R.id.tv_take_off_lock)
    TextView take_off_lock;
    @Override
    protected void fetchData() {
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_driving_record_drivingcar;
    }
    @Override
    protected void initView() {
        take_off_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loanListener!=null){
                    loanListener.loanClick(false,3,0.65f);
                }
            }
        });
    }
}
