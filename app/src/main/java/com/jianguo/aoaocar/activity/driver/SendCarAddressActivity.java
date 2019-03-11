package com.jianguo.aoaocar.activity.driver;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;

import com.it.jianguo.common.Utils.PreUtils;
import com.it.jianguo.common.Utils.Utils;
import com.it.jianguo.common.adapter.CommonAdapter;
import com.it.jianguo.common.adapter.ViewHolder;
import com.it.jianguo.common.base.BaseActivity;
import com.jianguo.aoaocar.R;
import com.jianguo.aoaocar.activity.AllBranchesActivity;
import com.jianguo.aoaocar.model.MyLocation;
import com.jianguo.aoaocar.view.MapCenterPotion;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by 22077 on 2018/1/24.
 */

public class SendCarAddressActivity extends BaseActivity implements OnGetGeoCoderResultListener {
    @Bind(R.id.mv_address)
    MapView mapView;
    @Bind(R.id.search_view)
    LinearLayout searchView;
    @Bind(R.id.bt_mapCenter)
    MapCenterPotion mapCenter;
    @Bind(R.id.lv_address)
    ListView mListView;

    private BaiduMap baiduMap;
    private double latitude;
    private double longitude;
    private CommonAdapter<MyLocation> mAdapter;
    private List<MyLocation> mLocations = new ArrayList<MyLocation>();
    private Marker marker;
    private GeoCoder mGeoCoder;
    private MapStatus ms;

    @Override
    public int getLayoutId() {
        return R.layout.activity_send_car_address;
    }
    @Override
    public void initView() {
        setLeftBtnDefaultOnClickListener();
        setTitle("送车上门");
        SDKInitializer.initialize(this );
        initMapView();
    }

    private void initMapView() {
        baiduMap = mapView.getMap();
        //普通地图
        initListView();
        initMap();
    }
    private void initListView() {
        mListView.setAdapter(mAdapter = new CommonAdapter<MyLocation>(this, mLocations, R.layout.row_item_list_mine_location) {
            @Override
            public void convert(ViewHolder holder, MyLocation item) {
                holder.setText(R.id.tv_mechant_name, item.complang).setText(R.id.tv_mechant_address, item.address);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.putExtra("name", mLocations.get(position));
                SendCarAddressActivity.this.setResult(RESULT_OK, intent);
                //关闭Activity
                SendCarAddressActivity.this.finishActivity();
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.toActivity(SendCarAddressActivity.this,SearchAddressActivity.class);
            }
        });
    }
    private void initMap() {
        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mapView.showZoomControls(false);//隐藏缩放按钮
        // 开启定位图层
        baiduMap.setMyLocationEnabled(true);

        mGeoCoder = GeoCoder.newInstance();//编译监听
     //   poisearch = PoiSearch.newInstance();//运搜索
        //设置POI检索监听者；
       // poisearch.setOnGetPoiSearchResultListener(new PoiSearchResultListener());
        mGeoCoder.setOnGetGeoCodeResultListener(this);
        //发起检索请求

        latitude = Double.parseDouble(PreUtils.getString(this,"Latitude","31.2461092170"));
        longitude = Double.parseDouble(PreUtils.getString(this,"Longitude","121.4897215516"));
        LatLng point = new LatLng(latitude, longitude);
        setLoaction(point);
        setGeocode(point);
        /**
         *
         * 地图改变监听
         */
        baiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
                                                  /**
                                                   * 手势操作地图，设置地图状态等操作导致地图状态开始改变。
                                                   *
                                                   * @param status 地图状态改变开始时的地图状态
                                                   */
                                                  public void onMapStatusChangeStart(MapStatus status) {
                                                      mapCenter.setProgressBar(true);
                                                  }

                                                  /**
                                                   * 地图状态变化中
                                                   *
                                                   * @param status 当前地图状态
                                                   */
                                                  public void onMapStatusChange(MapStatus status) {
                                                  }
                                                  /**
                                                   * 地图状态改变结束
                                                   *
                                                   * @param status 地图状态改变结束后的地图状态
                                                   */
                                                  public void onMapStatusChangeFinish(MapStatus status) {
                                                      mapCenter.setProgressBar(false);
                                                      LatLng ll = status.target;
                                                      latitude = ll.latitude;
                                                      longitude = ll.longitude;
                                                      // Toast.makeText(getActivity(),ll.latitude+"",Toast.LENGTH_LONG).show();
                                                      setGeocode(ll);
                                                  }
                                              }
        );
    }
    /**
     * 地图编译监听
     */
    private void setGeocode(LatLng point) {

        mGeoCoder.reverseGeoCode(new ReverseGeoCodeOption().location(point));
    }
    private void setLoaction(LatLng point) {

        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(20)
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(0).latitude(point.latitude)
                .longitude(point.longitude).build();
        baiduMap.setMyLocationData(locData);

        ms = new MapStatus.Builder().target(point).zoom(18.0f).build();
        baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(ms));

    }
    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }
    @Override
    public void onResume() {
        mapView.onResume();

        super.onResume();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mGeoCoder.destroy();
        if (mapView != null) {
            mapView.onDestroy();
        }
        // 退出时销毁定位
    }
    /**
     *
     * 检索
     * @param geoCodeResult
     */
    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {stopProgressDialog();
    }
    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {

        if ((result == null)
                || (result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND)) {
            return;
        }
        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
            mLocations.clear();
            List<PoiInfo> mPois = result.getPoiList();
            String  province=   result.getAddressDetail().province;
            String  district  = result.getAddressDetail().district;
            String  xiAddress=result.getAddress();

            for (PoiInfo po: mPois  ) {
                Double latitude = po.location.latitude;
                Double longitude = po.location.longitude;
                String name = po.name;
                String city = po.city;
                String address = po.address;
                if(address.equals(null)||address.equals(" ")){
                    address= xiAddress;
                }
                 mLocations.add(new MyLocation(latitude, longitude, city, name, address));
                }
                mAdapter.notifyDataSetChanged();
            return;
        }
    }
}
