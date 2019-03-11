package com.jianguo.aoaocar.fragment.drivingrecord;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.it.jianguo.common.adapter.CommonAdapter;
import com.it.jianguo.common.adapter.ViewHolder;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.fragment.drivingrecord.base.BaseDrivingFragment;
import com.jianguo.aoaocar.fragment.drivingrecord.base.LoanClickListener;
import com.jianguo.aoaocar.model.Detection;
import com.jianguo.aoaocar.model.Detection.DetectionState;
import com.jianguo.aoaocar.model.OrderRecord;
import com.jianguo.aoaocar.view.DashedLineView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by 22077 on 2018/1/5.
 */

public class RefundCarFragment extends BaseDrivingFragment{
    @Bind(R.id.bt_back)
    Button btnBack;
    @Bind(R.id.lv_refund_car_step)
    ListView mListView;

    private CommonAdapter<Detection> mAdapter;
    List<Detection> mDetections = new ArrayList<>();
    @Override
    protected void fetchData() {

    }
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_driving_record_refundcar;
    }

    @Override
    protected void initView() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loanListener!=null){
                    loanListener.loanClick(true,4,0.75f);
                }
            }
        });


        mDetections.add(new Detection(1,"在网点", DetectionState.COMPLETE));
        mDetections.add(new Detection(2,"已熄火", DetectionState.NOT_COMPLETE));
        mDetections.add(new Detection(3,"关车灯", DetectionState.DETECTION_ING));
        mDetections.add(new Detection(4,"弹出钥匙", DetectionState.TO_DETECTION));
        mDetections.add(new Detection(5,"锁车门", DetectionState.TO_DETECTION));
        mDetections.add(new Detection(6,"已充电", DetectionState.TO_DETECTION));

        mListView.setAdapter(mAdapter=new CommonAdapter<Detection>(mActivity,mDetections,R.layout.row_list_etection_state_record) {
            @SuppressLint("ResourceAsColor")
            @Override
            public void convert(ViewHolder holder, Detection item) {
                DashedLineView iv=(DashedLineView) holder.getView(R.id.iv_record_line);
                ImageView img=(ImageView) holder.getView(R.id.rl_iv);
                TextView tv=(TextView) holder.getView(R.id.tv_etection_state);

                if( holder.getPosition()==(mDetections.size()-1)){
                    iv.setVisibility(View.GONE);
                }else{
                    iv.setVisibility(View.VISIBLE);
                }
                holder.setText(R.id.tv_etection_state_name,item.name);
                tv.setText(item.etectionState.getDesc());
                switch (item.etectionState) {
                    case COMPLETE://完成
                        tv.setTextColor(Color.parseColor("#4CAF50"));
                        img.setBackgroundResource(R.drawable.icon_success);
                        break;
                    case NOT_COMPLETE://未完成
                        tv.setTextColor(Color.parseColor("#FF0000"));
                        img.setBackgroundResource(R.drawable.icon_worng);
                        break;
                    case DETECTION_ING://正在检测中
                        tv.setTextColor(Color.parseColor("#000000"));
                        break;
                    case TO_DETECTION://待检测
                        img.setBackgroundResource(R.drawable.icon_warm);

                        break;

                }
            }
        });
    }
}
