package com.jianguo.aoaocar.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.model.LatLng;
import com.jianguo.aoaocar.R;

import mapapi.clusterutil.clustering.ClusterItem;

/**
 * Created by 22077 on 2018/1/16.
 */

public class SwitBdItem implements ClusterItem {

    private Context context;
    private final LatLng mPosition;
    private int quantify=0;
    private int id;
    private String shopname ;
    public SwitBdItem(Context context,int id,String shopname,LatLng latLng,int quantify) {
        this.context=context;
        this.id = id;
        this.shopname=shopname;
        mPosition = latLng;
        this.quantify=quantify;

    }
    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public BitmapDescriptor getBitmapDescriptor() {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.widget_mapapi_item_market, null);
        TextView textView = (TextView) view.findViewById(R.id.icon_title);
        textView.setText(quantify + "");
        return BitmapDescriptorFactory.fromView(view);
    }
}
