package com.fermongroup.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class About extends Fragment {
    private WebView AboutWebView;

    public static About newInstance() {
        About fragment = new About();
        return fragment;
    }

    public About() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View AboutView = inflater.inflate(R.layout.layout_about, container, false);
        AboutWebView = (WebView) AboutView.findViewById(R.id.AboutWebView);
        AboutWebView.getSettings().setJavaScriptEnabled(true);
        AboutWebView.loadUrl("https://www.fermongroup.com/en/p/about.html");
        return AboutView;
    }
}
