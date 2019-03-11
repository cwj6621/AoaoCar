package com.jianguo.aoaocar.view.pop;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.it.jianguo.common.adapter.CommonAdapter;
import com.it.jianguo.common.adapter.ViewHolder;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.model.Free;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 22077 on 2017/12/29.
 */

public class RentCarFreePop extends PopupWindow {
    private final View mView;
    private final ListView mListView;
    private ImageView close;
    private Context mCtx;
    private CommonAdapter<Free> mAdapter;
    private List<Free> mFrees=new ArrayList<>();

    public RentCarFreePop(final Context ctx  ) {
        this.mCtx = ctx;
        LayoutInflater inflater = LayoutInflater.from(ctx);
        mView = inflater.inflate(R.layout.widget_order_free_popup_window, null);

        mListView= (ListView) mView.findViewById(R.id.lv_free_record);
        close= (ImageView) mView.findViewById(R.id.iv_close);
        setPopState(mView,this);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        initFreesDatas();
    }

    private void initFreesDatas() {
        mFrees.add(new Free("租车费用","租车及服务费",30.0f));
        mFrees.add(new Free("优惠券","折扣减免",10.0f));
        mFrees.add(new Free("不计免赔","增值服务费",10.0f));
        mListView.setAdapter(mAdapter=new CommonAdapter<Free>(mCtx,mFrees,R.layout.row_list_free_detail) {
            @Override
            public void convert(ViewHolder holder, Free item) {
                holder.setText(R.id.tv_free_desc,item.freeDesc).
                        setText(R.id.tv_free_money,"￥"+item.freeMoney).
                        setText(R.id.tv_free_name,item.freeName);
            }
        });
    }
    public static void setPopState(View mFillingView, PopupWindow pWindow) {
        // 设置SelectPicPopupWindow的View
        pWindow.setContentView(mFillingView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        pWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        pWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        pWindow.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        pWindow.setOutsideTouchable(false);
        pWindow.setAnimationStyle(R.style.AnimationPicker);
        // 实例化一个ColorDrawable颜色为半透明
        pWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置SelectPicPopupWindow弹出窗体的背景
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
    }


}
