package com.jianguo.aoaocar.activity.user;

import com.it.jianguo.common.base.BaseActivity;
import com.jianguo.aoaocar.R;

/**
 * Created by 22077 on 2017/12/27.
 */
public class ApplayProgressActivity extends BaseActivity{
    @Override
    public int getLayoutId() {
        return R.layout.activity_applay_progress;
    }

    @Override
    public void initView() {
      setLeftBtnDefaultOnClickListener();
      setTitle("申请进度");
    }
}
