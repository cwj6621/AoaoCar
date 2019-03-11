package com.jianguo.aoaocar.activity;

import android.view.View;
import android.widget.RelativeLayout;

import com.it.jianguo.common.Utils.Utils;
import com.it.jianguo.common.base.BaseActivity;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.activity.user.UserAboutActivity;

import butterknife.Bind;

/**
 * Created by 22077 on 2017/11/27.
 */
public class SetActivity extends BaseActivity{
    @Bind(R.id.rl_set_update_password)
    RelativeLayout update_password;
    @Bind(R.id.rl_user_feedback)
    RelativeLayout user_feedback;
    @Bind(R.id.rl_user_about)
    RelativeLayout user_about;
    @Override
    public int getLayoutId() {
        return R.layout.activiity_set;
    }

    @Override
    public void initView() {
        setLeftBtnDefaultOnClickListener();
        setTitle("设置");

        update_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.toActivity(SetActivity.this,UpdatePassWordActivity.class);
            }
        });
        user_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.toActivity(SetActivity.this,FeedBackActivity.class);
            }
        });
        user_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.toActivity(SetActivity.this,UserAboutActivity.class);

            }
        });
    }
}
