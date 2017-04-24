package com.example.douban.douban4_b.http;

import android.content.Context;
import android.widget.Toast;

import com.example.douban.douban4_b.R;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by wangm on 2017/2/22.
 */

public class BaseSubscriber<T> extends Subscriber<T> {
    protected Context mContext;

    public BaseSubscriber(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCompleted() {

    }


    @Override
    public void onError(Throwable e) {
        Logger.w("Subscriber onError", e);
        if (e instanceof HttpException) {
            // We had non-2XX http error
            Toast.makeText(mContext, mContext.getString(R.string.server_internal_error), Toast.LENGTH_SHORT).show();
        } else if (e instanceof IOException) {
            // A network or conversion error happened
            Toast.makeText(mContext, mContext.getString(R.string.cannot_connected_server), Toast.LENGTH_SHORT).show();
        } else if (e instanceof ApiException) {
            ApiException exception = (ApiException) e;
            if (exception.isTokenExpried()) {
                //处理token失效对应的逻辑
            } else {
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onNext(T t) {

    }
}
