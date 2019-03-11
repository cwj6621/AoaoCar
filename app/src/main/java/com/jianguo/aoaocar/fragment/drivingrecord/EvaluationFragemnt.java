package com.jianguo.aoaocar.fragment.drivingrecord;

import android.view.View;
import android.widget.Button;
import com.it.jianguo.common.adapter.CommonAdapter;
import com.it.jianguo.common.adapter.ViewHolder;
import com.it.jianguo.common.commonwidget.NoScrollGridView;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.fragment.drivingrecord.base.BaseDrivingFragment;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
/**
 * Created by 22077 on 2018/1/5.
 * 评价
 */
public class EvaluationFragemnt extends BaseDrivingFragment {
    @Bind(R.id.bt_submit)
    Button submit;
    @Bind(R.id.gv_evalaution)
    NoScrollGridView mGv;
    private CommonAdapter<String>  mAdapter;
    private List<String> mLists=new ArrayList<>();
    private boolean isFirst=false;
    @Override
    protected void fetchData() {
      }
    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_driving_record_evalaution;
    }
    @Override
    protected void initView() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loanListener!=null){
                    if (isFirst){
                         mActivity.finishActivity();
                    }else{
                        loanListener.loanClick(true,0,0.10f);
                        isFirst=true;
                    }
                }
            }
        });
        initGridView();
    }
    private void initGridView() {
        mLists.add("轮胎损坏");
        mLists.add("外观损坏");
        mLists.add("充电桩损坏");
        mLists.add("内饰脏");
        mLists.add("有异味");
        mLists.add("电量不足");
        mGv.setAdapter(mAdapter=new CommonAdapter<String>(mActivity,mLists,R.layout.row_list_evluation_detail) {
            @Override
            public void convert(ViewHolder holder, String item) {
                holder.setText(R.id.tv_evluation,item);
            }
        });
    }
}
