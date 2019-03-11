package com.jianguo.aoaocar.activity.user;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.RelativeLayout;

import com.it.jianguo.common.Utils.Utils;
import com.it.jianguo.common.base.BaseActivity;
import com.jianguo.aoaocar.R;

import butterknife.Bind;

/**
 * Created by 22077 on 2017/11/23.
 */

public class UserPurseActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.cardview_account)
    CardView  cardview_account;
    @Bind(R.id.rl_account_charge)
    RelativeLayout account_charge;
    @Bind(R.id.rl_security_deposit)
    RelativeLayout  security_deposit;
    @Bind(R.id.rl_coupon)
    RelativeLayout  rl_coupon;
    @Bind(R.id.rl_costs_instruction)
    RelativeLayout  costs_instruction;
    @Override
    public int getLayoutId() {
        return R.layout.activity_user_purse;
    }
    @Override
    public void initView() {
        setLeftBtnDefaultOnClickListener();
        setTitle("钱包");

        account_charge.setOnClickListener(this);
        security_deposit.setOnClickListener(this);
        rl_coupon.setOnClickListener(this);
        costs_instruction.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case   R.id.rl_account_charge:
                Utils.toActivity(UserPurseActivity.this,AccountPurseActivity.class);
                break;
            case   R.id.rl_coupon:
                Utils.toActivity(UserPurseActivity.this,CouponActivity.class);
                break;

        }
    }
}
