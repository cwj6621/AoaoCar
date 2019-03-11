package com.jianguo.aoaocar.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.model.AllBbranches;

import java.util.List;


/**
 * 基本功能：左侧Adapter
 * 创建：王杰
 * 创建时间：16/4/14
 * 邮箱：w489657152@gmail.com
 */
public class LeftListAdapter extends BaseAdapter {


     private Context context;
     private List<AllBbranches> mAllBbranchess;

    public LeftListAdapter(Context context,  List<AllBbranches> mAllBbranchess) {
        this.context = context;
        this.mAllBbranchess=mAllBbranchess;

    }

    @Override
    public int getCount() {
        return    mAllBbranchess.size();
    }

    @Override
    public Object getItem(int arg0) {
        return  mAllBbranchess.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        Holder holder = null;
        if (arg1 == null) {
            holder = new Holder();
            arg1 = LayoutInflater.from(context).inflate(R.layout.widget_menu_left_list_item, null);
            holder.left_list_item = (TextView) arg1.findViewById(R.id.left_list_item);
            arg1.setTag(holder);
        } else {
            holder = (Holder) arg1.getTag();
        }
        holder.updataView(arg0);
        return arg1;
    }

    private class Holder {
        private TextView left_list_item;
        public void updataView(final int position) {
            left_list_item.setText(mAllBbranchess.get(position).district);
            if (mAllBbranchess.get(position).isSelect) {
                left_list_item.setBackgroundColor(Color.rgb(255, 255, 255));
                left_list_item.setTextColor(Color.parseColor("#FF8600"));
            } else {
                left_list_item.setBackgroundColor(Color.TRANSPARENT);
                left_list_item.setTextColor(Color.BLACK);
            }
        }
    }
}
