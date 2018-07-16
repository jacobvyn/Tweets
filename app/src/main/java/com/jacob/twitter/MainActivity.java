package com.jacob.twitter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jacob.twitter.data.TwitterRepository;
import com.jacob.twitter.utils.UiUtils;
import com.jacob.twitter.view.TwitterContract;
import com.jacob.twitter.view.fragment.TwitterFragment;
import com.jacob.twitter.view.TwitterPresenter;

public class MainActivity extends AppCompatActivity {
    private TwitterContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TwitterFragment fragment = TwitterFragment.newInstance();
        mPresenter = new TwitterPresenter(fragment, TwitterRepository.getInstance());
        UiUtils.addFragment(this, fragment, R.id.container);
    }

    @Override
    protected void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
    }
}
