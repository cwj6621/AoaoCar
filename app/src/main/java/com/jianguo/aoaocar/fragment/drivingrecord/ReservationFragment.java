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
 * 预定
 */

public class ReservationFragment extends BaseDrivingFragment {
    @Bind(R.id.iv_reservation_explain)
    ImageView reservation_explain;
    @Override
    protected void fetchData() {

    }
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_driving_record_reservation;
    }

    @Override
    protected void initView() {

        reservation_explain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loanListener!=null){
                    loanListener.loanClick(true,1,0.35f);
                }
            }
        });
    }
}
