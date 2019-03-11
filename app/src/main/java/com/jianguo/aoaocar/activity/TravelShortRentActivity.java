package com.jianguo.aoaocar.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.it.jianguo.common.Utils.Utils;
import com.it.jianguo.common.base.BaseActivity;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.global.Constant;
import com.jianguo.aoaocar.view.pop.HeadDialog;
import com.jianguo.timedialog.AddressDialog;
import com.jianguo.timedialog.TimePickerDialog;
import com.jianguo.timedialog.data.Type;
import com.jianguo.timedialog.listener.OnDateSetListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
/**
 * Created by 22077 on 2017/11/27.
 */
public class TravelShortRentActivity extends BaseActivity implements View.OnClickListener, OnDateSetListener  {
    @Bind(R.id.rl_title)
    RelativeLayout rl_title;
    @Bind(R.id.tv_righttitle)
    TextView righttitle;

    @Bind(R.id.tv_take_car_dot)
    TextView take_car;
    @Bind(R.id.tv_refund_car_dot)
    TextView refund_car;

    @Bind(R.id.rl_refund_car_time)
    LinearLayout rl_refund_car_time;
    @Bind(R.id.rl_take_car_time)
    LinearLayout rl_take_car_time;

    @Bind(R.id.et_take_car_time)
    TextView take_car_time;
    @Bind(R.id.et_refund_car_time)
    TextView refund_car_time;
    @Bind(R.id.tv_sum_time)
    TextView sum_time;
    @Bind(R.id.btn_select_car)
    Button select_car;

    AddressDialog mAddressDialog;
    TimePickerDialog mDialogAll;
    private boolean isStartTime=true;
    @Override
    public int getLayoutId() {
        return R.layout.activity_travel_short_rent;
    }

    @Override
    public void initView() {
        righttitle.setText("上海");

        take_car.setOnClickListener(this);
        refund_car.setOnClickListener(this);
        rl_refund_car_time.setOnClickListener(this);
        refund_car_time.setOnClickListener(this);
        rl_take_car_time.setOnClickListener(this);
        select_car.setOnClickListener(this);
        rl_title.setOnClickListener(this);

        initTimeDialog();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.activity_default, R.anim.exit_bottom);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_select_car:
                Utils.toActivity(this, SelectCarActivity.class);
                break;
            case R.id.tv_take_car_dot:
                startActivityForResult(AllBranchesActivity.class, Constant.REQUEST_CODE_BRANCH_ONE);
                break;
            case R.id.tv_refund_car_dot:
                startActivityForResult(AllBranchesActivity.class, Constant.REQUEST_CODE_BRANCH_TWO);
                break;
            case R.id.rl_take_car_time:
                isStartTime=true;
                mDialogAll.show(getSupportFragmentManager(), "all");
                break;
            case R.id.rl_refund_car_time:
                isStartTime=false;
                mDialogAll.show(getSupportFragmentManager(), "all");
                break;
            case R.id.rl_title:
                HeadDialog window = new HeadDialog(TravelShortRentActivity.this, false, new HeadDialog.OnClickItemListener() {
                    @Override
                    public void OnClickItem(int indxe) {
                        switch (indxe) {
                            case 0:
                                TravelShortRentActivity.this.finishActivity();
                                overridePendingTransition(R.anim.activity_default, R.anim.exit_bottom);
                                break;
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.REQUEST_CODE_BRANCH_ONE:
                    take_car.setText(data.getStringExtra("name"));
                    break;
                case Constant.REQUEST_CODE_BRANCH_TWO:
                    refund_car.setText(data.getStringExtra("name"));
                    break;
            }
        }
    }

    private void initTimeDialog() {
        long tenYears = 3L * 365 * 1000 * 60 * 60 * 24L;
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
    public void onDateSet(TimePickerDialog timePickerDialog, long millseconds) {
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