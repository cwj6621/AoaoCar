package com.jianguo.aoaocar.netHttp;

import com.it.jianguo.common.retrofit_rx.resulte.BaseResultEntity;
import com.jianguo.aoaocar.model.SwitBdItem;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by 22077 on 2018/2/23.
 */

public interface HttpPostService {

    @FormUrlEncoded
    @POST("index.php?g=api&m=userno&a=getcode")
        //获取验证吗   http://106.14.215.15/ddb/index.php?g=api&m=useryes&a=yue_home_txian
    Observable<BaseResultEntity<Void>> getPhoneCode(@Field("phone") String tel, @Field("regtype") int regtype, @Field("usermdeis") String usermdeis);

    @FormUrlEncoded
    @POST("index.php?g=api&m=userno&a=login")
   //登录
    Observable<BaseResultEntity<String>> login(@Field("phone") String tel, @Field("userpwd") String password);

    @FormUrlEncoded
    @POST("index.php?g=api&m=userno&a=regist")
        //注册
    Observable<BaseResultEntity<Void>> register(@Field("phone") String tel,@Field("userpwd") String password,@Field("phonecode") String phonecode);

    @FormUrlEncoded
    @POST("index.php?g=api&m=userno&a=getcarshop")
        //获取附近网点
    Observable<BaseResultEntity<SwitBdItem>> getCarShops(@Field("lag")String latitude, @Field("lng")String longitude);
}
