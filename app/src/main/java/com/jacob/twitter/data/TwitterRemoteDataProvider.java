package com.jacob.twitter.data;

import android.support.annotation.NonNull;

import com.jacob.twitter.App;
import com.jacob.twitter.BuildConfig;
import com.jacob.twitter.data.service.network.AuthInterceptor;
import com.jacob.twitter.data.service.network.CacheControlInterceptor;
import com.jacob.twitter.data.service.TwitterService;
import com.jacob.twitter.data.service.model.Tweet;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class TwitterRemoteDataProvider implements DataProvider {
    private static File sCacheDir = new File(App.getContext().getCacheDir(), "cache");
    private static int sCacheSize = 5 * 1024 * 1024;
    private static Cache sCache = new Cache(sCacheDir, sCacheSize);
    private final TwitterService SERVICE;

    protected TwitterRemoteDataProvider() {
        SERVICE = getRetrofit().create(TwitterService.class);
    }

    @Override
    public Observable<List<Tweet>> search(@NonNull String userName, int count) {
        return SERVICE.search(userName, count);
    }

    private Retrofit getRetrofit() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new CacheControlInterceptor())
                .addInterceptor(new CacheControlInterceptor())
                .addInterceptor(new AuthInterceptor())
                .cache(sCache)
                .build();

        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(BuildConfig.TWITTER_BASE_URL)
                .client(okHttpClient)
                .build();
    }
}
