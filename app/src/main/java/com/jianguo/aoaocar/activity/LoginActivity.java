package com.jianguo.aoaocar.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.it.jianguo.common.Utils.PreUtils;
import com.it.jianguo.common.Utils.Utils;
import com.it.jianguo.common.Utils.ValidatorUtils;
import com.it.jianguo.common.base.BaseActivity;
import com.it.jianguo.common.retrofit_rx.listener.HttpOnNextListener;
import com.it.jianguo.common.retrofit_rx.resulte.BaseResultEntity;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.global.AoaoDefine;
import com.jianguo.aoaocar.netHttp.api.LoginPassApi;

import org.json.JSONException;

import butterknife.Bind;
/**
 * Created by 22077 on 2017/11/23.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.et_login_account)
    EditText  etMobile;
    @Bind(R.id.et_login_password)
    EditText  etPass;
    @Bind(R.id.btn_sure_login)
    Button btLogin;
    @Bind(R.id.tv_forget_pass)
    TextView tvForget;
    private String mobile;
    private String pass;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        setLeftBtnDefaultOnClickListener();
        setTitle("登录");
        setRightTitleAndOnClickListener("注册", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.toActivity(LoginActivity.this,RegisterActivity.class);
            }
        });
        btLogin.setOnClickListener(this);
        tvForget.setOnClickListener(this);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_default, R.anim.exit_bottom);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sure_login:
                 loginValidator();
                break;
            case R.id.tv_forget_pass:
                  Utils.toActivity(this,ForgetPassActivity.class);
                break;
        }
    }

    private void loginValidator() {
        mobile=etMobile.getText().toString();
        pass=etPass.getText().toString();
        if(mobile.isEmpty()){
            showShortToast("请输入手机号");
            return;
        }
        if(!ValidatorUtils.isMobile(mobile))    {
            showShortToast("手机号格式有误");
            return;
        }
        if(pass.isEmpty()){
            showShortToast("请输入密码");
            return;
        }
        if(!ValidatorUtils.isTextPassword(pass))    {
            showShortToast("您输入密码格式有误");
            return;
        }
       loginTask();
    }
    /**
     * 登录api
     */
    private void loginTask() {
        manager.doHttpDeal(new LoginPassApi(mobile, pass),new HttpOnNextListener(this){
            @Override
            protected void succeed(BaseResultEntity baseResultEntity, String method) throws JSONException {
                String token=baseResultEntity.getData().getString(AoaoDefine.token);
                PreUtils.putString(LoginActivity.this, AoaoDefine.token,token);
                PreUtils.putString(LoginActivity.this, AoaoDefine.phone,mobile);
                Utils.toActivity(LoginActivity.this, MainActivity.class);
            }


        });
    }
}
