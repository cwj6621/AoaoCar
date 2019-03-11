package com.jianguo.aoaocar.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.it.jianguo.common.base.BaseActivity;
import com.jianguo.aoaocar.R;
import butterknife.Bind;

/**
 * Created by 22077 on 2017/11/27.
 */

public class FeedBackActivity extends BaseActivity {
    @Bind(R.id.et_feedback)
    EditText et_feedback;
    @Bind(R.id.tv_record_length)
    TextView  recordLength;

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initView() {
        setLeftBtnDefaultOnClickListener();
        setTitle("用户反馈");

        setRightTitleAndOnClickListener("发送", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String  opinion=et_feedback.getText().toString();
                   if(opinion.isEmpty()){
                       showShortToast("请输入反馈意见");
                       return;
                 }
            }
        });

        et_feedback.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
              if(!s.toString().isEmpty()){
                  int length=s.toString().length();
                  recordLength.setText(length+"/200");
              }
            }
        });
    }
}
