package com.jacob.twitter.view;

import com.jacob.twitter.data.service.model.Tweet;

import java.util.List;

public interface TwitterContract {
    interface View {
        void setPresenter(Presenter presenter);

        void nothingFound();

        void setData(List<Tweet> tweetList);

        void showProgressView(boolean show);

        void showMessage(String message);

    }

    interface Presenter {
        void search(String user);

        void destroy();

        void onTwitClicked(Tweet tweet);
    }
}
