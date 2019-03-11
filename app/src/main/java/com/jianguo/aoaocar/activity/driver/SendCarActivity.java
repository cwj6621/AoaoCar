package com.jianguo.aoaocar.activity.driver;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.it.jianguo.common.Utils.Utils;
import com.it.jianguo.common.base.BaseActivity;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.activity.AllBranchesActivity;
import com.jianguo.aoaocar.activity.SelectCityActivity;
import com.jianguo.timedialog.TimePickerDialog;
import com.jianguo.timedialog.data.Type;
import com.jianguo.timedialog.listener.OnDateSetListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;

/**
 * Created by 22077 on 2018/1/24.
 */

public class SendCarActivity extends BaseActivity implements View.OnClickListener, OnDateSetListener {
    private static final int REQUEST_CODE_SEND_ADDRESS = 0x0020;
    private static final int REQUEST_CODE_SEND_BADDRESS = 0x0021;
    @Bind(R.id.cb_send_car_home)
    CheckBox  cb_send;
    @Bind(R.id.cb_take_car_home)
    CheckBox  cb_take;

    @Bind(R.id.tv_send_car_home_desc)
    TextView send_desc;
    @Bind(R.id.tv_send_car_home_address)
    TextView  send_adress;
    @Bind(R.id.tv_take_car_home_desc)
    TextView take_desc;
    @Bind(R.id.tv_take_car_home_address)
    TextView  take_address;

    @Bind(R.id.tv_take_car_time)
    TextView take_car_time;
    @Bind(R.id.tv_sum_time)
    TextView  sum_time;
    @Bind(R.id.tv_refund_car_time)
    TextView  refund_car_time;

    @Bind(R.id.btn_rob_order)
    Button btnOrder;
    private TimePickerDialog mDialogAll;
    private boolean isStartTime;

    private boolean isSendAddress=false;
    private boolean isSendBranches=false;
    @Override
    public int getLayoutId() {
        return R.layout.activity_send_car;
    }

    @Override
    public void initView() {
    setLeftBtnDefaultOnClickListener();
    setTitle("送车上门");

        setRightTitleAndOnClickListener("上海", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.toLoginActivity(SendCarActivity.this, SelectCityActivity.class);
            }
        });

        send_adress.setOnClickListener(this);
        take_address.setOnClickListener(this);
        take_car_time.setOnClickListener(this);
        refund_car_time.setOnClickListener(this);
        btnOrder.setOnClickListener(this);

        cb_send.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    send_desc.setText("送车地址");
                    send_desc.setTextColor(Color.parseColor("#FF8600"));
                }else{
                    send_desc.setText("取车门店");
                    send_desc.setTextColor(Color.parseColor("#999999"));
                }
            }
        });
        cb_take.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    take_desc.setText("取车地址");
                    take_desc.setTextColor(Color.parseColor("#FF8600"));
                }else{
                    take_desc.setText("还车门店");
                    take_desc.setTextColor(Color.parseColor("#999999"));
                }
            }
        });

        initTimeDialog();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_send_car_home_address:
                if(cb_send.isChecked()){
                    isSendAddress=false;
                    Intent intent=new Intent(SendCarActivity.this,SendCarAddressActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_SEND_ADDRESS);
                }else{
                    isSendBranches=false;
                    Intent intent=new Intent(SendCarActivity.this,AllBranchesActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_SEND_BADDRESS);
                }
                break;
            case R.id.tv_take_car_home_address:
                if(cb_take.isChecked()){
                    isSendAddress=true;
                    Intent intent=new Intent(SendCarActivity.this,SendCarAddressActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_SEND_ADDRESS);
                }else{
                    isSendBranches=true;
                    Intent intent=new Intent(SendCarActivity.this,AllBranchesActivity.class);
                    startActivityForResult(intent, REQUEST_CODE_SEND_BADDRESS);
                }
                break;
            case R.id.tv_take_car_time:
                isStartTime=true;
                mDialogAll.show(getSupportFragmentManager(), "all");
                break;
            case R.id.tv_refund_car_time:
                isStartTime=false;
                mDialogAll.show(getSupportFragmentManager(), "all");
                break;
            case R.id.btn_rob_order:

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK &&data!=null){
            switch (requestCode){
                case  REQUEST_CODE_SEND_ADDRESS: //地址
                    String mAddress = data.getStringExtra("");
                    if(isSendAddress){

                    }else{

                    }
                    break;
                case   REQUEST_CODE_SEND_BADDRESS: //网点
                    String mBranches = data.getStringExtra("name");
                    if(isSendBranches){
                        take_address.setText(mBranches);
                    }else{
                        send_adress.setText(mBranches);
                    }
                    break;
            }
        }
    }

    private void initTimeDialog() {
        long tenYears =30 * 1000 * 60 * 60 * 24L;
        mDialogAll = new TimePickerDialog.Builder( )
                .setCallBack(this)
                .setCyclic(false)
                .setMinMillseconds(System.currentTimeMillis())
                .setMaxMillseconds(System.currentTimeMillis() + tenYears)
                .setCurrentMillseconds(System.currentTimeMillis())
                .setThemeColor(getResources().getColor(R.color.colorOrangePrimary))
                .setType(Type.ALL)
                .setWheelItemTextNormalColor(getResources().getColor(R.color.timetimepicker_default_text_color))
                .setWheelItemTextSelectorColor(getResources().getColor(R.color.colorOrangePrimary))
                .setWheelItemTextSize(13)
                .build();
    }

    @Override
    public void onDateSet(TimePickerDialog timePickerView, long millseconds) {
        String text = getDateToString(millseconds);
        if(isStartTime){
            take_car_time.setText(text);
        }else{
            refund_car_time.setText(text);
        }
    }
    public String getDateToString(long time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d = new Date(time);
        return sf.format(d);
    }
}
