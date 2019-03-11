package com.jianguo.aoaocar.activity;

import android.view.View;
import android.widget.EditText;

import com.it.jianguo.common.Utils.Utils;
import com.it.jianguo.common.Utils.ValidatorUtils;
import com.it.jianguo.common.base.BaseActivity;
import com.it.jianguo.common.commonutils.ToastUitl;
import com.jianguo.aoaocar.R;
import butterknife.Bind;
/**
 * Created by 22077 on 2017/11/27.
 */
public  class UpdatePassWordActivity extends BaseActivity{
    @Bind(R.id.et_old_password)
    EditText  et_old_password;
    @Bind(R.id.et_new_password)
    EditText  et_new_password;
    @Bind(R.id.et_sure_password)
    EditText  et_sure_password;

    private String oldPass;
    private String newPass;
    private String surePass;

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_password;
    }
    @Override
    public void initView() {
        setLeftBtnDefaultOnClickListener();
        setTitle("修改密码");
        setRightTitleAndOnClickListener("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passValidator();
            }
        });
    }

    private void passValidator() {
        oldPass=et_old_password.getText().toString();
        newPass=et_new_password.getText().toString();
        surePass=et_sure_password.getText().toString();
        if(oldPass.isEmpty()){
            showShortToast("请输入原密码");
            return;
        }
        if(!ValidatorUtils.isTextPassword(oldPass))    {
            showShortToast("原密码格式有误");
            return;
        }

        if(newPass.isEmpty()){
            showShortToast("请输入新密码");
            return;
        }
        if(!ValidatorUtils.isTextPassword(newPass))    {
            showShortToast("新密码格式有误");
            return;
        }
        if(surePass.isEmpty()){
            showShortToast("请输入确认密码");
            return;
        }
        if(!ValidatorUtils.isTextPassword(surePass))    {
            showShortToast("确认密码格式有误");
            return;
        }
        if(newPass.equals(surePass)){
            showShortToast("新密码与确认密码输入不一致");
            return;
        }
        updatePassTask();

    }

    private void updatePassTask() {
        ToastUitl.showShort(UpdatePassWordActivity.this,"修改成功");
        Utils.toLoginActivity(this,LoginActivity.class);
        UpdatePassWordActivity.this.finishActivity();
    }
}
