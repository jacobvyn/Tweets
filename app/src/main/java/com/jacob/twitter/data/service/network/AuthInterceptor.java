package com.jacob.twitter.data.service.network;

import android.support.annotation.NonNull;

import com.jacob.twitter.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class AuthInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain
                .request()
                .newBuilder()
                .addHeader("Authorization", "Bearer " + BuildConfig.TWITTER_APP_ACCESS_TOKEN)
                .build();
        return chain.proceed(request);
    }
}
