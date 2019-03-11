package com.jianguo.aoaocar.netHttp.api;

import android.content.Context;

import com.it.jianguo.common.Utils.PreUtils;
import com.it.jianguo.common.encrypt.CryptUtil;
import com.it.jianguo.common.encrypt.Rsa;
import com.it.jianguo.common.retrofit_rx.Api.BaseApi;
import com.it.jianguo.common.retrofit_rx.resulte.BaseResultEntity;
import com.jianguo.aoaocar.netHttp.HttpPostService;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by 22077 on 2018/2/23.
 */

public class GetCodeApi extends BaseApi<Void> {
    private String mobile;
    private String usermdeis;
    public GetCodeApi(Context mtx,String mobile) {
        super();
        this. mobile= Rsa.encrypt(mobile, CryptUtil.DQ_PUBLIC_KEY);
        this.usermdeis= String.valueOf(PreUtils.getDeviceUuid(mtx));
    }

    @Override
    public Observable<BaseResultEntity<Void>> getObservable(Retrofit retrofit) {
        return retrofit.create(HttpPostService.class).getPhoneCode(mobile,2,usermdeis);
    }
}
