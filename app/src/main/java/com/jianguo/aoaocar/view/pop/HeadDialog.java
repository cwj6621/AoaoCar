package com.jianguo.aoaocar.view.pop;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.jianguo.aoaocar.R;

/**
 * Created by 22077 on 2017/11/27.
 */

public class HeadDialog {

    private final Dialog mLoadingDialog;
    private final TextView short_rent;
    private final TextView time_sharing;
    private final TextView send_car;
    private boolean isSelect=false;
    private Context mContext;
    private OnClickItemListener  onClickItemListener;
    public HeadDialog(Context context,boolean isSelect, OnClickItemListener _onClickItemListener) {
        this.mContext = context;
        onClickItemListener=_onClickItemListener;
        this.isSelect = isSelect;
        mLoadingDialog =new Dialog(context, R.style.CustomDialog);
        mLoadingDialog.setCanceledOnTouchOutside(true);
        mLoadingDialog.setContentView(R.layout.widget_head_popup_window);
       // mLoadingDialog.getWindow().setGravity(Gravity.BOTTOM);
        Window dialogWindow = mLoadingDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER | Gravity.TOP);
        lp.y = 100; // 新位置Y坐标
        dialogWindow.setAttributes(lp);

        short_rent=(TextView) mLoadingDialog.findViewById(R.id.tv_short_rent);
        time_sharing=(TextView) mLoadingDialog.findViewById(R.id.tv_time_sharing);
        send_car=(TextView) mLoadingDialog.findViewById(R.id.tv_send_car);



         if(isSelect){
             time_sharing.setTextColor(Color.parseColor("#4CAF50"));
         }else{
             short_rent.setTextColor(Color.parseColor("#4CAF50"));
         }

        short_rent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemListener.OnClickItem(1);
                mLoadingDialog.dismiss();
            }
        });
        time_sharing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemListener.OnClickItem(0);
                mLoadingDialog.dismiss();
            }
        });
        send_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickItemListener.OnClickItem(2);
                mLoadingDialog.dismiss();
            }
        });
        mLoadingDialog.show();
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        //在需要分享的地方添加代码：
    }
    //接口
    public  interface OnClickItemListener {
        void OnClickItem(int index);
    }

}
