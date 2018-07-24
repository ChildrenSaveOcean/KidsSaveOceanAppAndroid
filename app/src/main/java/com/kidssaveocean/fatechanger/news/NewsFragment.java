package com.kidssaveocean.fatechanger.news;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.kidssaveocean.fatechanger.R;

public class NewsFragment extends Fragment {

    private static final String url = "https://www.kidssaveocean.com/copy-of-news";

    WebView webView;

    public NewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        webView = (WebView) inflater.inflate(R.layout.fragment_news, container, false);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        return webView;
    }

    public void onResume () {
        super.onResume();
        webView.loadUrl(url);
    }

}
