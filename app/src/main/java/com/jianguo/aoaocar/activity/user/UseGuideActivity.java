package com.jianguo.aoaocar.activity.user;

import com.it.jianguo.common.base.BaseActivity;
import com.jianguo.aoaocar.R;
/**
 * Created by 22077 on 2017/12/1.
 */
public class UseGuideActivity extends BaseActivity{
    @Override
    public int getLayoutId() {
        return R.layout.activity_use_guide;
    }

    @Override
    public void initView() {
        setLeftBtnDefaultOnClickListener();
        setTitle("使用指南");

    }
}
