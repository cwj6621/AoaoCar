package com.jianguo.aoaocar.activity;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.it.jianguo.common.Utils.MyCountdownTimer;
import com.it.jianguo.common.Utils.ValidatorUtils;
import com.it.jianguo.common.base.BaseActivity;
import com.it.jianguo.common.baseapp.AppManager;
import com.it.jianguo.common.retrofit_rx.listener.HttpOnNextListener;
import com.it.jianguo.common.retrofit_rx.resulte.BaseResultEntity;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.global.Constant;
import com.jianguo.aoaocar.netHttp.api.GetCodeApi;
import com.jianguo.aoaocar.netHttp.api.RegiterApi;

import org.json.JSONException;

import butterknife.Bind;

/**
 * Created by 22077 on 2017/11/23.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.et_register_account)
    EditText et_account;
    @Bind(R.id.et_phone_code)
    EditText et_code;
    @Bind(R.id.tv_give_code)
    TextView give_code;

    @Bind(R.id.et_register_pass)
    EditText et_pass;
    @Bind(R.id.et_register_sure_pass)
    EditText et_sure_pass;
    @Bind(R.id.btn_sure_register)
    Button sure_regiter;

    @Bind(R.id.rb_register_protocol)
    CheckBox rb_protocol;
    @Bind(R.id.tv_register_protocole)
    TextView tv_protocole;

    private String mobile;
    private String code;
    private String pass;
    private String surePass;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        setLeftBtnDefaultOnClickListener();
        setTitle("注册");

        Spannable spannable = new SpannableString(Constant.MSG_REGISTER_PROTOCOL);
        spannable.setSpan(new ForegroundColorSpan(Color.RED), Constant.MSG_REGISTER_PROTOCOL.length() - 5, Constant.MSG_REGISTER_PROTOCOL.length() - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_protocole.setText(spannable);

        give_code.setOnClickListener(this);
        sure_regiter.setOnClickListener(this);
        tv_protocole.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_give_code:
                validatorCode();
                break;
            case R.id.btn_sure_register:
                validatorRegister();
                break;
            case R.id.tv_register_protocole:
                break;

        }
    }

    private void validatorRegister() {
        mobile=et_account.getText().toString();
        code=et_code.getText().toString();
        pass=et_pass.getText().toString();
        surePass=et_sure_pass.getText().toString();
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
        if(!rb_protocol.isChecked()){
            showShortToast("您还没有同意注册协议");
            return;
        }
        regieterTask(mobile,code,pass);
    }
    private void validatorCode() {
        mobile=et_account.getText().toString();
        if(mobile.isEmpty()){
            showShortToast("请输入手机号");
            return;
        }
        if(!ValidatorUtils.isMobile(mobile))    {
            showShortToast("手机号格式有误");
            return;
        }

        new MyCountdownTimer(give_code, R.color.black).start();//传入了文字颜色值
        getPhoneCodeTask(mobile);
    }

    private void getPhoneCodeTask(final String mobile) {
        manager.doHttpDeal(new GetCodeApi(this,mobile),new HttpOnNextListener(this){
            @Override
            protected void succeed(BaseResultEntity baseResultEntity, String method) throws JSONException {
                showShortToast("短信验证码一下发，请注意查收！");
            }
        });
    }

    private void regieterTask(final String mobile, String code, String pass) {

      manager.doHttpDeal(new RegiterApi(mobile, code, pass), new HttpOnNextListener(this) {
          @Override
          protected void succeed(BaseResultEntity baseResultEntity, String method) throws JSONException {
              AppManager.getAppManager().finishActivity();
          }
      });
    }
}
