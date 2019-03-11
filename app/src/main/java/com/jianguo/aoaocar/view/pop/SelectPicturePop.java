package com.jianguo.aoaocar.view.pop;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.jianguo.aoaocar.R;


public class SelectPicturePop {

	private final Dialog mLoadingDialog;
	private TextView cancel;

	private TextView picture;
	private TextView canmera;
	private Context mContext;
	private OnSelectedSharetener onSelectedSharetener;
	public SelectPicturePop(Context context, OnSelectedSharetener _onSelectedSharetener) {
		this.mContext = context;
		onSelectedSharetener=_onSelectedSharetener;

		mLoadingDialog =new Dialog(context, R.style.CustomDialog);
		mLoadingDialog.setCanceledOnTouchOutside(true);
		mLoadingDialog.show();
		mLoadingDialog.setContentView(R.layout.widget_select_option);
		mLoadingDialog.getWindow().setGravity(Gravity.BOTTOM);

		cancel = (TextView) mLoadingDialog.findViewById(R.id.tv_option_cancel);
		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 销毁弹出框
				mLoadingDialog.dismiss();
			}
		});
		picture =(TextView) mLoadingDialog.findViewById(R.id.tv_option_picture);
		canmera =(TextView) mLoadingDialog.findViewById(R.id.tv_option_cramera);
		picture.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onSelectedSharetener.onShareSelected(1);
				mLoadingDialog.dismiss();
			}
		});
		canmera.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onSelectedSharetener.onShareSelected(2);
				mLoadingDialog.dismiss();
			}
		});

		// mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		//在需要分享的地方添加代码：
	}
	//接口日期选择接口
	public  interface OnSelectedSharetener {
		void onShareSelected(int index);
	}
}