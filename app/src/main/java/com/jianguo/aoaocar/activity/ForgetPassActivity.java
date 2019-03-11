package com.jianguo.aoaocar.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.it.jianguo.common.Utils.MyCountdownTimer;
import com.it.jianguo.common.Utils.ValidatorUtils;
import com.it.jianguo.common.base.BaseActivity;
import com.jianguo.aoaocar.R;
import butterknife.Bind;
/**
 * Created by 22077 on 2017/12/11.
 */
public class ForgetPassActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.et_forget_mobile)
    EditText etMobile;
    @Bind(R.id.et_phone_code)
    EditText etCode;
    @Bind(R.id.et_forget_pass)
    EditText etPass;
    @Bind(R.id.et_forget_sure_pass)
    EditText etSurePass;
    @Bind(R.id.tv_give_code)
    TextView getCode;
    @Bind(R.id.btn_sure_sunmit)
    Button btnSubmit;
    private String mobile;
    private String code;
    private String pass;
    private String surePass;
    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_pass;
    }

    @Override
    public void initView() {
         setLeftBtnDefaultOnClickListener();
        setTitle("忘记密码");

        getCode.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_give_code:
                validatorCode();
                break;
            case R.id.btn_sure_sunmit:
                validatorPass();
                break;
        }
    }
    /**
     * 验证码
     */
    private void validatorCode() {
        mobile=etMobile.getText().toString();
        if(mobile.isEmpty()){
            showShortToast("请输入手机号");
            return;
        }
        if(!ValidatorUtils.isMobile(mobile))    {
            showShortToast("手机号格式有误");
            return;
        }

        new MyCountdownTimer(getCode, R.color.black).start();//传入了文字颜色值
        getPhoneCodeTask(mobile);
    }

    /**
     * 验证密码
     */
    private void validatorPass() {
        code=etCode.getText().toString();
        pass=etPass.getText().toString();
        surePass=etMobile.getText().toString();
        if(mobile.isEmpty()){
            showShortToast("请输入手机号");
            return;
        }
        if(!ValidatorUtils.isMobile(mobile))    {
            showShortToast("手机号格式有误");
            return;
        }
        if(code.isEmpty()){
            showShortToast("请输入短信验证码");
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
        if(surePass.isEmpty()){
            showShortToast("请输入确认密码");
            return;
        }
        if(!ValidatorUtils.isTextPassword(surePass))    {
            showShortToast("您输入确认密码格式有误");
            return;
        }
        forgetPassTask();
    }
    private void getPhoneCodeTask(String mobile) {

    }
    private void forgetPassTask() {
    }
}
