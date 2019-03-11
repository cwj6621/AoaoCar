package com.jianguo.aoaocar.netHttp.api;

import android.content.Context;
import com.baidu.mapapi.model.LatLng;
import com.it.jianguo.common.retrofit_rx.Api.BaseApi;
import com.it.jianguo.common.retrofit_rx.resulte.BaseResultEntity;
import com.jianguo.aoaocar.netHttp.HttpPostService;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by 22077 on 2018/3/8.
 */

public class GetCarShopSApi<SwitBdItem> extends BaseApi<com.jianguo.aoaocar.model.SwitBdItem> {
   private  LatLng point;

    public GetCarShopSApi(Context ctx,LatLng point) {
        super();
        this.point=point;
    }

    @Override
    public Observable<BaseResultEntity<com.jianguo.aoaocar.model.SwitBdItem>> getObservable(Retrofit retrofit) {
        return retrofit.create(HttpPostService.class).getCarShops(point.latitude+"",point.longitude+"");
    }
}
