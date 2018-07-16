package com.jacob.twitter.view;

import com.jacob.twitter.data.DataProvider;
import com.jacob.twitter.data.service.model.Tweet;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class TwitterPresenter implements TwitterContract.Presenter {
    private static final int DEFAULT_COUNT = 100;
    private final TwitterContract.View mView;
    private final DataProvider mDataProvider;
    private final CompositeDisposable mDisposables = new CompositeDisposable();

    public TwitterPresenter(TwitterContract.View view, DataProvider dataProvider) {
        mDataProvider = dataProvider;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void search(String user) {
        mView.showProgressView(true);
        mDisposables.add(mDataProvider.search(user, DEFAULT_COUNT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Tweet>>() {

                    @Override
                    public void onNext(List<Tweet> tweet) {
                        mView.setData(tweet);
                    }

                    @Override
                    public void onError(Throwable trouble) {
                        if (((HttpException) trouble).code() == 404) {
                            mView.nothingFound();
                        } else {
                            mView.showMessage(trouble.getMessage());
                        }
                        mView.showProgressView(false);
                    }

                    @Override
                    public void onComplete() {
                        mView.showProgressView(false);
                    }
                }));
    }

    public void destroy() {
        mDisposables.dispose();
    }

    @Override
    public void onTwitClicked(Tweet tweet) {
        // TODO: 7/16/18 implement
    }
}
