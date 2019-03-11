package com.jianguo.aoaocar.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.it.jianguo.common.Utils.PreUtils;
import com.it.jianguo.common.Utils.Utils;
import com.it.jianguo.common.base.BaseActivity;
import com.it.jianguo.common.baseapp.AppManager;
import com.it.jianguo.common.baseapp.BaseApplication;
import com.it.jianguo.common.easyPermission.PermissionCallBackM;
import com.it.jianguo.common.theme.Theme;
import com.jianguo.aoaocar.R;

import butterknife.Bind;

import static com.baidu.location.b.g.R;

/**
 * Created by 22077 on 2017/12/7.
 */

public class SplashActivity extends BaseActivity {

    @Bind(R.id.ken_burns_images)
    ImageView mKenBurns;

    private SDKReceiver mReceiver;
    private LocationClient mLocClient;
    private boolean isShow=false;
    @Override
    public int getLayoutId() {
        onPreCreate(Theme.No);
        setTranslucentStatus();
        return R.layout.activity_splash;
    }
    @Override
    public void initView() {

        initLoClient();
        requestPermission();

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash);
        if(PreUtils.getBoolean(this,"isFrist",false)){
            animation.setDuration(2000);
        }else{
            animation.setDuration(4000);
            PreUtils.putBoolean(this,"isFrist",true);
            isShow=true;
        }
        mKenBurns.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                if(isShow){
                    Utils.toActivity(SplashActivity.this,WecomeActivity.class);
                }else{
                    Utils.toActivity(SplashActivity.this,MainActivity.class);
                }
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void initLoClient() {
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK);
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        iFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mReceiver = new SDKReceiver();
        registerReceiver(mReceiver, iFilter);
        initBaiduMapLocation();

    }
    private void requestPermission(){
        requestPermission(0010, new String[]{ Manifest.permission.ACCESS_COARSE_LOCATION },
                getString(R.string.rationale_location), new PermissionCallBackM() {
                    @Override
                    public void onPermissionGrantedM(int requestCode, String... perms) {
                        mLocClient.start();
                    }
                    @Override
                    public void onPermissionDeniedM(int requestCode, String... perms) {
                    }
                });}
    private void initBaiduMapLocation() {
        mLocClient = BaseApplication.mLocationClient;
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式:高精度模式
        option.setCoorType("bd09ll"); // 设置坐标类型:百度经纬度
        option.setScanSpan(1000);// 设置发起定位请求的间隔时间为1000ms:低于1000为手动定位一次，大于或等于1000则为定时定位
        option.setIsNeedAddress(true);// 不需要包含地址信息
        option.setOpenGps(true);
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        mLocClient.setLocOption(option);
    }
    /**
     * 构造广播监听类，监听 SDK key 验证以及网络异常广播
     */
    public class SDKReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String s = intent.getAction();
            //根据系统服务类得到connectactivitymanger，这是一个系统服务类专门用于网络管理
            ConnectivityManager connectactivitymanger = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkinfo = connectactivitymanger.getActiveNetworkInfo();
            if (s.equals(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR)) {
                Log.i("MSG", "百度地图key 验证出错! 请在 AndroidManifest.xml 文件中检查 key 设置");
            } else if (!(networkinfo != null && networkinfo.isAvailable())) {
                Toast.makeText(SplashActivity.this, "当前网络连接不稳定，请检查您的网络设置!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void onBackPressed() {
            AppManager.getAppManager().finishAllActivity();
            super.onBackPressed();
    }
}
