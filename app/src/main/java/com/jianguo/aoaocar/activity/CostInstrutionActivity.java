package com.jianguo.aoaocar.activity;

import com.it.jianguo.common.base.BaseActivity;
import com.jianguo.aoaocar.R;
/**
 * Created by 22077 on 2018/1/3.
 */
public class CostInstrutionActivity extends BaseActivity{
    @Override
    public int getLayoutId() {
        return R.layout.activity_cost_instrution;
    }
    @Override
    public void initView() {
      setLeftBtnDefaultOnClickListener();
      setTitle("费用说明");

    }
}
