package com.raahi.wikilite.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.raahi.wikilite.R;
import com.raahi.wikilite.session.SessionManager;

/**
 * Created by Raahi on 24-06-2018.
 */

public class DetailsActivity extends AppCompatActivity {
    private WebView mWebView;
    private SessionManager mSessionManager; //to store data as a caching
    private MyJavaScriptInterface myJavaScriptInterface;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        mWebView = findViewById(R.id.webview);
        mSessionManager = new SessionManager(DetailsActivity.this);

        String input = "";
        try {
            input = getIntent().getExtras().getString("title").trim().replaceAll(" ", "_");
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = "https://en.wikipedia.org/wiki/" + input;
        mWebView.getSettings().setAppCacheMaxSize(5 * 1024 * 1024); // 5MB
        mWebView.getSettings().setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // load online by default

        if (!isNetworkAvailable()) { // loading offline
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        myJavaScriptInterface = new MyJavaScriptInterface(this);
        mWebView.addJavascriptInterface(myJavaScriptInterface, "HtmlViewer");
        mWebView.setWebViewClient(new MyBrowser());
        mWebView.loadUrl(url);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return true;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            mWebView.loadUrl("javascript:window.HtmlViewer.showHTML" +
                    "('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
        }
    }

    class MyJavaScriptInterface {
        private Context ctx;
        MyJavaScriptInterface(Context ctx) {
            this.ctx = ctx;
        }

        public void showHTML(String html) {
            mSessionManager.saveLatestDetails(html);
            new AlertDialog.Builder(ctx).setTitle("HTML").setMessage(html)
                    .setPositiveButton(android.R.string.ok, null).setCancelable(false).create().show();
        }
    }
}
