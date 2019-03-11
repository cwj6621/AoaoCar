package com.jianguo.aoaocar.netHttp.api;

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

public class RegiterApi extends BaseApi<Void> {
    private String mobile;
    private String code;
    private String pass;
    public RegiterApi(String mobile, String code, String pass) {
        super();
        this. mobile= Rsa.encrypt(mobile, CryptUtil.DQ_PUBLIC_KEY);
        this. code= code;
        this. pass= Rsa.encrypt(pass, CryptUtil.DQ_PUBLIC_KEY);
    }

    @Override
    public Observable<BaseResultEntity<Void>> getObservable(Retrofit retrofit) {
        return retrofit.create(HttpPostService.class).register(mobile,pass,code);
    }
}
