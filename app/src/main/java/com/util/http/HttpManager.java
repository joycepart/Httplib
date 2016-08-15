package com.util.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


/**
 * Created by bixinwei on 16/8/15.
 */
public class HttpManager {
    public static final String BASE_URL = "http://www.izaodao.com/Api/";
    private static final int DEFAULT_TIMEOUT = 6;


    private HttpService httpService;
    private  static HttpManager instance;
    private HttpManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        httpService = retrofit.create(HttpService.class);
    }

    public static HttpManager getInstance() {
        if (instance == null) {
            synchronized (HttpManager.class) {
                if (instance == null) {
                    instance = new HttpManager();
                }
            }
        }
        return instance;
    }

    public HttpService getHttpService() {
        return httpService;
    }

    public  <T> Subscription execute(rx.Observable<Result<T>> observable, CallBack<T> subscriber){

        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<Result<T>, Observable<T>>(){

                    public Observable<T> call(Result<T> t) {
                        Result result=(Result)t;
                        if (result.getStatus() == 0) {
                            return Observable.error(new ServerException(result.getMsg()));
                        }
                        return Observable.just((T)result.getData());
                    }
                })
                .subscribe(subscriber);
    }
}
