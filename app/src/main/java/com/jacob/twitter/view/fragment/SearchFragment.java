package com.jacob.twitter.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.jacob.twitter.R;
import com.jacob.twitter.utils.UiUtils;

public class SearchFragment extends Fragment implements View.OnClickListener, TextView.OnEditorActionListener {
    private OnSearchListener mListener;
    private EditText mSearchEditText;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mSearchEditText = (EditText) view.findViewById(R.id.fragment_search_view_edit_text);
        mSearchEditText.setOnEditorActionListener(this);
        view.findViewById(R.id.fragment_twitter_brand_icon).setOnClickListener(this);
        view.findViewById(R.id.fragment_search_button).setOnClickListener(this);
        if (getParentFragment() instanceof OnSearchListener) {
            mListener = (OnSearchListener) getParentFragment();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fragment_search_button) {
            onSearch(mSearchEditText.getText().toString());
        }
    }

    public void onSearch(String expression) {
        UiUtils.hideKeyboard(mSearchEditText);
        if (mListener != null) {
            mListener.onSearch(expression);
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            onSearch(textView.getText().toString());
            return true;
        }
        return false;
    }

    public interface OnSearchListener {
        void onSearch(String expression);
    }
}
