package com.jianguo.aoaocar.view.pop;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.it.jianguo.common.Utils.NavigationUtils;
import com.it.jianguo.common.adapter.CommonAdapter;
import com.it.jianguo.common.adapter.ViewHolder;
import com.jianguo.aoaocar.R;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by 22077 on 2018/1/2.
 */

public class NavigationPop {
    private final Dialog mLoadingDialog;
    private ListView mListView;
    private Context mContext;
    private CommonAdapter<NavigationUtils.Navigtion>  madapter;

    private double lat=31.0581147267;
    private double lngt=121.7289038554;
    public NavigationPop(Context context, final List<NavigationUtils.Navigtion> mNavigtions) {
        this.mContext = context;
        mLoadingDialog = new Dialog(context, R.style.CustomDialog);
        mLoadingDialog.setCanceledOnTouchOutside(true);
        mLoadingDialog.show();
        mLoadingDialog.setContentView(R.layout.widget_select_navigtion);
        mLoadingDialog.getWindow().setGravity(Gravity.CENTER);
        mNavigtions.add(new NavigationUtils.Navigtion("取消",""));
        mListView = (ListView) mLoadingDialog.findViewById(R.id.lv_navigtion);
        mListView.setAdapter(madapter = new CommonAdapter<NavigationUtils.Navigtion>(mContext, mNavigtions, R.layout.row_list_navigtion_name) {
            @Override
            public void convert(ViewHolder holder, NavigationUtils.Navigtion item) {
                holder.setText(R.id.tv_navigtion_name, item.name);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mLoadingDialog.dismiss();
                NavigationUtils.Navigtion navigtion=mNavigtions.get(i);
                if(navigtion.packageName.equals(NavigationUtils.PACKAGE_NAME_BD_MAP)){//百度地图
                    baiduMapNavigtion();
                }else if(navigtion.packageName.equals(NavigationUtils.PACKAGE_NAME_MINI_MAP)){//高德地图
                    miniMapNavigtion();
                }
            }
        });
    }

    private void miniMapNavigtion() {
        Intent intent = new Intent("android.intent.action.VIEW",android.net.Uri.parse("androidamap://navi?sourceApplication=呦呦出行&lat="+lat+ "&lon="+ lngt+ "&dev=0"));
        intent.setPackage("com.autonavi.minimap");
        mContext.startActivity(intent);
    }
    private void baiduMapNavigtion() {
        try {
            Intent intent = Intent.getIntent("intent://map/direction?origin=latlng:"
                    + "31.2451292170" + ","
                    + "121.4851715516" + "|name:"+"上海市黄浦区科技京城688号" + // 终点
                    "&destination=上海野生动物园"+
                    "&mode=driving&" + // 导航路线方式
                    "region=上海" + //
                    "&src=呦呦出行#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            mContext.startActivity(intent); // 启动调用
        } catch (URISyntaxException e) {
            Log.e("intent", e.getMessage());
        }

    }

    /**
     *
     * @param sourceApplication 必填 第三方调用应用名称。如 amap
     * @param poiname 非必填 POI 名称
     * @param lat 必填 纬度
     * @param lon 必填 经度
     * @param dev 必填 是否偏移(0:lat 和 lon 是已经加密后的,不需要国测加密; 1:需要国测加密)
     * @param style 必填 导航方式(0 速度快; 1 费用少; 2 路程短; 3 不走高速；4 躲避拥堵；5 不走高速且避免收费；6 不走高速且躲避拥堵；7 躲避收费和拥堵；8 不走高速躲避收费和拥堵))
     */
    public static  void goToNaviActivity(Context context,String sourceApplication , String poiname , String lat , String lon , String dev , String style){
        StringBuffer stringBuffer  = new StringBuffer("androidamap://navi?sourceApplication=")
                .append(sourceApplication);
        if (!TextUtils.isEmpty(poiname)){
            stringBuffer.append("&poiname=").append(poiname);
        }
        stringBuffer.append("&lat=").append(lat)
                .append("&lon=").append(lon)
                .append("&dev=").append(dev)
                .append("&style=").append(style);

        Intent intent = new Intent("android.intent.action.VIEW", android.net.Uri.parse(stringBuffer.toString()));
        intent.setPackage("com.autonavi.minimap");
        context.startActivity(intent);
    }

}
