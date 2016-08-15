package com.util.http;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import rx.Subscription;

public class MainActivity extends Activity {
    HttpManager hm=HttpManager.getInstance();
    Subscription subscription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ReqDTO dto = new ReqDTO();
        dto.setOnce(true);
        subscription=hm.execute(hm.getHttpService().getVideos(dto),
                new CallBack<List<VideoBean>>(this) {
                    @Override
                    public void onSucceed(List<VideoBean> data) {
                        for (int i = 0; i < data.size(); i++) {
                            Log.e("tag", data.get(i).getTitle());
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }
}
