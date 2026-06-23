package com.example.kolokvijum2.helper;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

/** Folder: 24-webview/ */
public class WebViewHelper {

    private final WebView webView;

    public WebViewHelper(AppCompatActivity activity, WebView webView) {
        this.webView = webView;
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
    }

    public void ucitajUrl(String url) {
        webView.loadUrl(url);
    }
}
