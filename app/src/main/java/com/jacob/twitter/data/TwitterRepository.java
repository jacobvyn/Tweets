package com.jacob.twitter.data;

import android.support.annotation.NonNull;

import com.jacob.twitter.data.service.model.Tweet;

import java.util.List;

import io.reactivex.Observable;

public class TwitterRepository implements DataProvider {
    private static TwitterRepository INSTANCE;
    private final TwitterRemoteDataProvider mRemoteDataProvider;

    private TwitterRepository() {
        mRemoteDataProvider = new TwitterRemoteDataProvider();
    }

    public static TwitterRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TwitterRepository();
        }
        return INSTANCE;
    }

    @Override
    public Observable<List<Tweet>> search(@NonNull String userName, int count) {
        return mRemoteDataProvider.search(userName, count);
    }
}
