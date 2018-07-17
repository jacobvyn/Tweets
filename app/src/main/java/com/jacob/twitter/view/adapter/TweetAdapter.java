package com.jacob.twitter.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jacob.twitter.R;
import com.jacob.twitter.data.service.model.Tweet;
import com.jacob.twitter.utils.NumberUtils;
import com.jacob.twitter.utils.UiUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.TweetViewHolder> {
    private final List<Tweet> mData = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    @NonNull
    @Override
    public TweetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tweet_item, null, false);
        return new TweetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TweetViewHolder holder, int position) {
        holder.setViews(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<Tweet> tweetList) {
        mData.clear();
        mData.addAll(tweetList);
        notifyDataSetChanged();
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public class TweetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tweet_item_avatar)
        protected ImageView avatar;
        @BindView(R.id.tweet_item_retweet_sign_image)
        protected ImageView reTweet_sign;
        @BindView(R.id.tweet_item_author)
        protected TextView author;
        @BindView(R.id.tweet_item_screen_name)
        protected TextView screenName;
        @BindView(R.id.tweet_item_message_text_view)
        protected TextView text;
        @BindView(R.id.stats_comments_counter)
        protected TextView commentsCounter;
        @BindView(R.id.stats_retwit_counter)
        protected TextView reTweetCounter;
        @BindView(R.id.stats_likes_counter)
        protected TextView likesCounter;

        public TweetViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        public void setViews(Tweet tweet) {
            boolean isReTweet = tweet.isReTweet();
            tweet = isReTweet ? tweet.getReTweet() : tweet;

            Picasso.get().load(tweet.getUser().getProfileImageUrlHttps()).into(avatar);
            author.setText(tweet.getUser().getName());
            screenName.setText(tweet.getUser().getScreenName());
            UiUtils.linkify(tweet.getText(), text);
            likesCounter.setText(NumberUtils.ellipsize(tweet.getFavoriteCount()));
            reTweetCounter.setText(NumberUtils.ellipsize(tweet.getRetweetCount()));
            commentsCounter.setText(NumberUtils.ellipsize(tweet.getUser().getListedCount()));
            reTweet_sign.setVisibility(isReTweet ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClicked(mData.get(getAdapterPosition()));
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(Tweet tweet);
    }
}
