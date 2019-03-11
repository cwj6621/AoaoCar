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
import com.jianguo.aoaocar.model.OrderRecord;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by 22077 on 2017/12/5.
 */
public class OrderRecordPop extends PopupWindow{
    private final View mView;
    private  ImageView close;
    private  ListView mListView;
    private Context mCtx;
    private List<OrderRecord> mOrderRecord = new ArrayList<>();
    private CommonAdapter<OrderRecord> mAdapter;

    public OrderRecordPop(final Context ctx, List<OrderRecord> data ) {
        this.mCtx = ctx;
        this.mOrderRecord = data;
        LayoutInflater inflater = LayoutInflater.from(ctx);
        mView = inflater.inflate(R.layout.widget_order_record_popup_window, null);
        close= (ImageView) mView.findViewById(R.id.iv_close);
        mListView=(ListView) mView.findViewById(R.id.lv_order_record);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderRecordPop.this.dismiss();
            }
        });
        mListView.setAdapter(mAdapter=new CommonAdapter<OrderRecord>(mCtx,mOrderRecord,R.layout.row_list_order_record) {
            @Override
            public void convert(ViewHolder holder, OrderRecord item) {
                ImageView iv=(ImageView) holder.getView(R.id.iv_record_line);
                if( holder.getPosition()==0){
                    iv.setVisibility(View.GONE);
                }else{
                    iv.setVisibility(View.VISIBLE);
                }
                holder.setText(R.id.tv_record_state_name,item.stateName).
                        setText(R.id.tv_record_state_time,item.stateTime);
            }
        });
        setPopState(mView,this);
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
