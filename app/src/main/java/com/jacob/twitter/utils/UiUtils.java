package com.jacob.twitter.utils;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.jacob.twitter.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UiUtils {

    private static final String PATTERN_MENTION = "(@[A-Za-z0-9_-]+)";
    private static final String PATTERN_HASH_TAG = "#(\\w+|\\W+)";

    public static boolean isOnline(Context context) {
        try {
            ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conMgr == null) return false;
            NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnected() && activeNetwork.isAvailable();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void addFragment(AppCompatActivity activity, final Fragment fragment, int container_id) {
        if (activity != null) {
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(container_id, fragment)
                    .commit();
        }
    }

    public static void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void linkify(String text, TextView textView) {
        Resources resources = textView.getContext().getResources();
        Pattern urlPattern = Patterns.WEB_URL;
        Pattern mentionPattern = Pattern.compile(PATTERN_MENTION);
        Pattern hashTagPattern = Pattern.compile(PATTERN_HASH_TAG);

        Matcher hashTag = hashTagPattern.matcher(text);
        Matcher mention = mentionPattern.matcher(text);
        Matcher link = urlPattern.matcher(text);

        SpannableString spannableString = new SpannableString(text);

        while (hashTag.find()) {
            int start = hashTag.start();
            int end = hashTag.end();
            NonUnderlinedClickableSpan span = new NonUnderlinedClickableSpan(resources.getColor(R.color.link_color_hashtag));
            spannableString.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        while (mention.find()) {
            int start = mention.start();
            int end = mention.end();
            NonUnderlinedClickableSpan span = new NonUnderlinedClickableSpan(resources.getColor(R.color.link_color_mention));
            spannableString.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }

        while (link.find()) {
            int start = link.start();
            int end = link.end();
            NonUnderlinedClickableSpan span = new NonUnderlinedClickableSpan(resources.getColor(R.color.link_color_url));
            spannableString.setSpan(span, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        textView.setText(spannableString);
    }

    public static class NonUnderlinedClickableSpan extends ClickableSpan {
        @ColorInt
        private final int color;

        public NonUnderlinedClickableSpan(@ColorInt int color) {
            this.color = color;
        }

        @Override
        public void updateDrawState(TextPaint textPaint) {
            textPaint.setColor(color);
            textPaint.setUnderlineText(false);
        }

        @Override
        public void onClick(View widget) {
        }
    }
}
