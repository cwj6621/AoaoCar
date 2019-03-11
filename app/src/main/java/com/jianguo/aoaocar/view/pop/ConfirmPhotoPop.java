package com.jianguo.aoaocar.view.pop;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jianguo.aoaocar.R;

/**
 * Created by 22077 on 2017/12/6.
 */

public class ConfirmPhotoPop {

    private final Dialog mLoadingDialog;
    private TextView sure;
    private ImageView showPic;
    private Context mContext;
    private OnDismissListener onDismissListener;
    public ConfirmPhotoPop(Context context, int index,OnDismissListener _onDismissListener) {
        this.mContext = context;
        this.onDismissListener=_onDismissListener;
        mLoadingDialog =new Dialog(context, R.style.CustomDialog);
        mLoadingDialog.setCanceledOnTouchOutside(true);

        mLoadingDialog.setContentView(R.layout.widget_confirm_phone);

        sure = (Button) mLoadingDialog.findViewById(R.id.btn_take_photo);
        showPic=(ImageView) mLoadingDialog.findViewById(R.id.iv_take_photo);
        switch (index){
            case 0:
                showPic.setBackgroundResource(R.mipmap.icon_driving_license);
                break;
            case 1:
                showPic.setBackgroundResource(R.mipmap.icon_identify_font);
                break;
            case 2:
                showPic.setBackgroundResource(R.mipmap.icon_identify_back);
                break;
            case 3:
                showPic.setBackgroundResource(R.mipmap.icon_identify_back);
                break;
        }


        sure.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 销毁弹出框
                onDismissListener.onDismiss();
                mLoadingDialog.dismiss();
            }
        });
        mLoadingDialog.show();
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        //在需要分享的地方添加代码：
    }
    //接口
    public  interface OnDismissListener {
        void onDismiss();
    }
}
