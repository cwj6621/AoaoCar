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
public class LoginPassApi extends BaseApi<String> {
    private String mobile;
    private String password;
    public LoginPassApi(String mobile, String password) {
        this.mobile = Rsa.encrypt(mobile,CryptUtil.DQ_PUBLIC_KEY);//rsa公钥加密
        this.password = Rsa.encrypt(password,CryptUtil.DQ_PUBLIC_KEY);;
    }
    @Override
    public Observable<BaseResultEntity<String>> getObservable(Retrofit retrofit) {
        return retrofit.create(HttpPostService.class).login(mobile,password);
    }
}
