package com.jianguo.aoaocar.view.pop;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.it.jianguo.common.Utils.Utils;
import com.it.jianguo.common.commonutils.ToastUitl;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.activity.CostInstrutionActivity;
import com.jianguo.aoaocar.activity.DrivingRecordActivity;
import com.jianguo.aoaocar.view.linttext.Link;
import com.jianguo.aoaocar.view.linttext.LinkBuilder;

/**
 * Created by 22077 on 2018/1/3.
 */

public class ConventSuccessPop {
    private final Dialog mLoadingDialog;
    private Context mContext;
    private TextView carNameLencense,checkNitice,instruction1,instruction2,ok;
    public ConventSuccessPop(Context context) {
        this.mContext = context;
        mLoadingDialog = new Dialog(context, R.style.CustomDialog);
        mLoadingDialog.setCanceledOnTouchOutside(true);
        mLoadingDialog.show();
        mLoadingDialog.setContentView(R.layout.widget_convent_success);
        mLoadingDialog.getWindow().setGravity(Gravity.CENTER);

        carNameLencense = (TextView) mLoadingDialog.findViewById(R.id.tv_car_name_and_lencense);
        checkNitice = (TextView) mLoadingDialog.findViewById(R.id.tv_car_check_nitice);
        instruction1 = (TextView) mLoadingDialog.findViewById(R.id.tv_instruction1);
        instruction2 = (TextView) mLoadingDialog.findViewById(R.id.tv_instruction2);
        ok = (TextView) mLoadingDialog.findViewById(R.id.tv_ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onTakePhonetener!=null){
                    mLoadingDialog.dismiss();
                    onTakePhonetener.OnCloseDimss();
                    Utils.toActivity(mContext, DrivingRecordActivity.class);
                }

            }
        });
        LinkBuilder.on(instruction1)
                .addLink(getExampleLinks1())
                .build();
        LinkBuilder.on(instruction2)
                .addLink(getExampleLinks2())
                .build();
    }
    private Link getExampleLinks1() {
        // action on a long click instead of a short click
        Link instruction1Here = new Link("联系客服");
        instruction1Here.setTextColor(Color.parseColor("#259B24"));
        instruction1Here.setBold(true);
        instruction1Here.setOnClickListener(new Link.OnClickListener() {
            @Override
            public void onClick(String clickedText) {
                if(onTakePhonetener!=null){
                    onTakePhonetener.OnTakePhone();
                }
            }
        });
        return  instruction1Here;
    }
    private Link getExampleLinks2() {
        // action on a long click instead of a short click
        Link instruction2Here = new Link("跨区域服务费");
        instruction2Here.setTextColor(Color.parseColor("#259B24"));
        instruction2Here.setBold(true);
        instruction2Here.setOnClickListener(new Link.OnClickListener() {
            @Override
            public void onClick(String clickedText) {
                Utils.toActivity(mContext,CostInstrutionActivity.class);
            }
        });
         return  instruction2Here;
    }

    private static OnTakePhonetener onTakePhonetener;
    public static void setOnTakePhonetener(OnTakePhonetener _onTakePhonetener) {
       onTakePhonetener=_onTakePhonetener;
    }
    public  interface OnTakePhonetener {
        void OnTakePhone();
        void OnCloseDimss();
    }
}
