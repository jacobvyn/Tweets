package com.jacob.twitter.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jacob.twitter.R;
import com.jacob.twitter.data.service.model.Tweet;
import com.jacob.twitter.utils.UiUtils;
import com.jacob.twitter.view.adapter.TweetAdapter;
import com.jacob.twitter.view.TwitterContract;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TwitterFragment extends Fragment implements TwitterContract.View, SearchFragment.OnSearchListener, TweetAdapter.OnItemClickListener {

    @BindView(R.id.fragment_twitter_progress_bar)
    protected View mProgressBar;
    @BindView(R.id.fragment_twitter_nothing_found_view)
    protected View mNothingFoundView;
    @BindView(R.id.fragment_twitter_recycler_view)
    protected RecyclerView recyclerView;
    private TweetAdapter mAdapter;
    private TwitterContract.Presenter mPresenter;
    private Unbinder mUnbinder;
    private View mRootView;

    public static TwitterFragment newInstance() {
        return new TwitterFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_twitter, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        mRootView = view;
        mUnbinder = ButterKnife.bind(this, view);
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_twitter_search_container, SearchFragment.newInstance())
                .commit();
        configureRecyclerView(view.getContext());
        mPresenter.search("BarackObama");
    }

    private void configureRecyclerView(Context context) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration decoration = new DividerItemDecoration(context, layoutManager.getOrientation());
        recyclerView.addItemDecoration(decoration);
        mAdapter = new TweetAdapter();
        mAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void setPresenter(TwitterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void nothingFound() {
        mNothingFoundView.setVisibility(View.VISIBLE);
        mAdapter.clear();
    }

    @Override
    public void setData(List<Tweet> tweetList) {
        mNothingFoundView.setVisibility(View.GONE);
        mAdapter.setData(tweetList);
    }

    @Override
    public void showProgressView(boolean show) {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(mRootView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onSearch(String expression) {
        if (mPresenter != null) {
            mPresenter.search(expression);
        }
    }

    @Override
    public void onItemClicked(Tweet tweet) {
        if (mPresenter != null) {
            mPresenter.onTwitClicked(tweet);
        }
    }

    @Override
    public void onDestroyView() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroyView();
    }
}
