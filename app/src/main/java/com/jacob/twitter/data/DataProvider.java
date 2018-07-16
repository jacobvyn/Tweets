package com.jacob.twitter.data;

import android.support.annotation.NonNull;

import com.jacob.twitter.data.service.model.Tweet;

import java.util.List;

import io.reactivex.Observable;

public interface DataProvider {
    Observable<List<Tweet>> search(@NonNull String userName, int count);
}
