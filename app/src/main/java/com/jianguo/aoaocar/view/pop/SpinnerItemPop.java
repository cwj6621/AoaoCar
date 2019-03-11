package com.jianguo.aoaocar.view.pop;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.it.jianguo.common.adapter.CommonAdapter;
import com.it.jianguo.common.adapter.ViewHolder;
import com.jianguo.aoaocar.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 22077 on 2018/1/25.
 */

public class SpinnerItemPop  extends PopupWindow {

    private final View mView;
    private final LinearLayout mEntifyView;
    private ListView mListView;
    private Context mCtx;
    private List<String> mPlates = new ArrayList<>();
    private CommonAdapter<String> mAdapter;
    private HashMap<String, Boolean> states = new HashMap<String, Boolean>();
    private int checkBrandIndex = -1;
    private OnDismissListListener onDismissListListener;

    public SpinnerItemPop(final Context ctx, List<String> data, int index, final OnDismissListListener onDismissListListener) {
        this.mCtx = ctx;
        this.mPlates = data;
        this.onDismissListListener = onDismissListListener;
        LayoutInflater inflater = LayoutInflater.from(ctx);
        mView = inflater.inflate(R.layout.widget_simple_spinner_item, null);
        mEntifyView = (LinearLayout) mView.findViewById(R.id.ll_condition);
        mListView = (ListView) mView.findViewById(R.id.lv_condition);
        initPopWindow();
        initstates(mPlates, index);

        mListView.setAdapter(mAdapter = new CommonAdapter<String>(ctx, mPlates, R.layout.row_list_simple_spinner_item) {
            @Override
            public void convert(ViewHolder holder, String item) {
                holder.setText(R.id.tv_item, item);
                if (states.get(String.valueOf(holder.getPosition())) == null || states.get(String.valueOf(holder.getPosition())) == false) {
                    states.put(String.valueOf(holder.getPosition()), false);
                }
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (checkBrandIndex == (position)) {
                    for (String key : states.keySet()) {
                        states.put(key, false);
                    }
                    mAdapter.notifyDataSetChanged();
                    checkBrandIndex = -1;
                } else {
                    for (String key : states.keySet()) {
                        states.put(key, false);
                    }
                    states.put(String.valueOf(position), true);
                    mAdapter.notifyDataSetChanged();
                    checkBrandIndex = position;
                    onDismissListListener.onnDismissList(checkBrandIndex, true);
                    SpinnerItemPop.this.dismiss();
                }
            }
        });
        mEntifyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private void initstates(List<String> mPlates, int index) {
        for (int i = 0; i < mPlates.size(); i++) {
            if (index == i) {
                states.put(String.valueOf(i), true);
            } else {
                states.put(String.valueOf(i), false);
            }
        }
    }
    private void initPopWindow() {
        this.setContentView(mView);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setBackgroundDrawable(new BitmapDrawable());
        //设置点击弹窗外隐藏自身
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        //设置动画
          this.setAnimationStyle(R.style.PopupWindowPopup);
        //设置位置
        //  this.showAtLocation(v, Gravity.CENTER, 0, 0);
        //设置消失监听
        //设置PopupWindow的View点击事件

        this.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                onDismissListListener.onnDismissList(checkBrandIndex, false);
            }
        });
    }
    //接口日期选择接口
    public interface OnDismissListListener {
        void onnDismissList(int index, boolean option);
    }

    @Override
    public void showAsDropDown(View anchor) {
        if(Build.VERSION.SDK_INT == 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }
}