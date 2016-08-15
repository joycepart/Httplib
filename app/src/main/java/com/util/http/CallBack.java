package com.util.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import rx.Subscriber;

/**
 * Created by bixinwei on 16/8/15.
 */
public abstract class CallBack<T> extends Subscriber<T> {
    private Context context;

    public CallBack(Context context){
        this.context=context;
    }

    @Override
    public void onError(Throwable e) {
        Log.e("tag","onError:"+e);
        if (e instanceof ServerException) {
           onFail(e.getMessage());
        }else{
           onFail("请求失败，请稍后再试...");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!isNetworkAvailable(context)) {
            Toast.makeText(context, "当前无网络", Toast.LENGTH_SHORT).show();
            onCompleted();
        }
    }

    public  boolean isNetworkAvailable(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getApplicationContext().getSystemService(
                Context.CONNECTIVITY_SERVICE);
        if (null == manager)
            return false;
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (null == info || !info.isAvailable())
            return false;
        return true;
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onNext(T data) {
        onSucceed(data);
    }

    public abstract void onSucceed(T data);
    protected  void onFail(String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
