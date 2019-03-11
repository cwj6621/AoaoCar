package com.jianguo.aoaocar.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.model.AllBbranches;
import com.jianguo.aoaocar.model.Branches;

import java.util.ArrayList;
import java.util.List;


/**
 * 基本功能：右侧Adapter
 */
public class HeadSectionedAdapter extends SectionedBaseAdapter {

    private Context mContext;
   // private List<> leftStr;
  //  private String[][] rightStr;
    private List<AllBbranches> mAllBbranchess;
    private List<Branches>  mBranchess=new ArrayList<>();
    private OnItemListener mOnItemListener;
    public HeadSectionedAdapter(Context context, List<AllBbranches> mAllBbranchess, OnItemListener mOnItemListener) {
        this.mContext = context;
        this.mAllBbranchess = mAllBbranchess;
        this.mOnItemListener=mOnItemListener;
      //  this.rightStr = rightStr;
        for (int i=0;i<mAllBbranchess.size();i++){
            AllBbranches as=mAllBbranchess.get(i);
            mBranchess.addAll(as.mBranchess);
         //   flagArray.add(as.isSelect);
        }
    }

    @Override
    public Object getItem(int section, int position) {
      //  return rightStr[section][position];
        return   mAllBbranchess.get(section).mBranchess.get(position);
    }

    @Override
    public long getItemId(int section, int position) {
        return position;
    }

    @Override
    public int getSectionCount() {
        return mAllBbranchess.size();
    }

    @Override
    public int getCountForSection(int section) {
        return mAllBbranchess.get(section).mBranchess.size();
    }

    @Override
    public View getItemView(final int section, final int position, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.widget_menu_right_list_item, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        ((TextView) layout.findViewById(R.id.tv_acr_shop_address)).setText(mAllBbranchess.get(section).mBranchess.get(position).address);
        ((TextView) layout.findViewById(R.id.tv_acr_shop_name)).setText(mAllBbranchess.get(section).mBranchess.get(position).name);
        layout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mOnItemListener.onItemName(mAllBbranchess.get(section).mBranchess.get(position).address);
            }
        });
        return layout;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.widget_menu_header_item, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        layout.setClickable(false);
        ((TextView) layout.findViewById(R.id.textItem)).setText(mAllBbranchess.get(section).district);
        return layout;
    }

    public interface  OnItemListener{
        void onItemName(String  protify);
    }
}
