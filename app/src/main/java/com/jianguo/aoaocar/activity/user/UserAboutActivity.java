package com.jianguo.aoaocar.activity.user;

import com.it.jianguo.common.base.BaseActivity;
import com.jianguo.aoaocar.R;

/**
 * Created by 22077 on 2017/11/27.
 */

public class UserAboutActivity extends BaseActivity{
    @Override
    public int getLayoutId() {
        return R.layout.activity_user_about;
    }
    @Override
    public void initView() {
        setLeftBtnDefaultOnClickListener();
        setTitle("关于我们");
    }
}
