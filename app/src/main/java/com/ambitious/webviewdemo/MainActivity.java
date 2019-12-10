package com.ambitious.webviewdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private Button b1, btn_js;
    private String link;
    private WebView webView;
    private EditText editText;
    private ProgressBar progressBar;
    private boolean isEnable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.b1);
        btn_js = (Button) findViewById(R.id.btn_js);
        webView = (WebView) findViewById(R.id.webView1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        editText = (EditText) findViewById(R.id.editText);
        btn_js.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEnable) {
                    btn_js.setText("Disable JS");
                    webView.getSettings().setJavaScriptEnabled(true);
                    isEnable=false;
                } else {
                    btn_js.setText("Enable JS");
                    webView.getSettings().setJavaScriptEnabled(false);
                    isEnable=true;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        home();
    }


    public void click(View view) {
        hideKeyboard(this);
        link = "http://" + editText.getText().toString();
        loadLink();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_file) {
            loadLocalPage();
        }
        if (id == R.id.action_Home) {
            home();
        }
        if (id == R.id.action_refresh) {
            refresh();
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadLink() {
        WebSettings webSetting = webView.getSettings();
        //webSetting.setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebViewClient());
        webSetting.setJavaScriptEnabled(true);
        webView.loadUrl(link);
    }

    public void loadLocalPage() {
        WebSettings webSetting = webView.getSettings();
        webSetting.setBuiltInZoomControls(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("file:///android_asset/webpage.html");
    }

    public void refresh() {
        loadLink();
    }

    public void home() {
        WebSettings webSetting = webView.getSettings();
        webSetting.setBuiltInZoomControls(true);
        webSetting.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://www.google.com");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            // TODO Auto-generated method stub
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            // TODO Auto-generated method stub

            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }

    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}