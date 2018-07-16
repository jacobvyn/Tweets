package com.jacob.twitter.data.service;

import com.jacob.twitter.data.service.model.Tweet;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TwitterService {
    @GET("1.1/statuses/user_timeline.json")
    Observable<List<Tweet>> search(@Query("screen_name") String userName, @Query("count") int count);
}
