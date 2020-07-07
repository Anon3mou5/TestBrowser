package com.example.webview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceActivity extends AppCompatActivity {
    WebView browser;
    ProgressBar progressBar;
    String urli;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_main);
        Uri uri = this.getIntent().getData();
        if(uri!=null) {
            urli = uri.toString();
            Toast.makeText(getApplicationContext(), "" + urli, Toast.LENGTH_SHORT);
        }

        browser = findViewById(R.id.webView);
        progressBar = findViewById(R.id.pb_default);
        browser.setWebViewClient(new MyBrowser());
        browser.setWebChromeClient(new WebChromeClient()
                                   {
                                       @Override
                                       public void onProgressChanged(WebView view, int newProgress) {
                                           setTitle("Loading....");
                                           progressBar.setProgress(newProgress);
                                           if(newProgress==100)
                                           {
                                               setTitle("WebView");
                                               progressBar.setVisibility(View.GONE);
                                           }
                                           else{
                                               progressBar.setVisibility(View.VISIBLE);
                                           }
                                       }
                                   }
        );
        final EditText url = findViewById(R.id.textView);
        if(urli!=null)
        {
            browser.getSettings().setLoadsImagesAutomatically(true);
            browser.getSettings().setJavaScriptEnabled(true);
            browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            url.setText(urli);
            Pattern p = Pattern.compile("^https://*");
            Matcher m = p.matcher(url.getText().toString());
            Pattern pi = Pattern.compile("^www.*.com$");
            Matcher z = pi.matcher(url.getText().toString());
            if(m.matches())
            {
                browser.loadUrl(url.getText().toString());
                downloadurl n = new downloadurl(url.getText().toString());
                n.start();
            }
            else if (z.matches()) {
                browser.loadUrl("https://" + url.getText().toString());
                downloadurl n = new downloadurl("https://" + url.getText().toString());
                n.start();
            }
        }
        url.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // TODO Auto-generated method stub

                if ((actionId== EditorInfo.IME_ACTION_DONE )   )
                {
                    //Toast.makeText(getActivity(), "call",45).show();
                    // hide virtual keyboard
                    if (url.getText().toString() != null &&
                            (!url.getText().toString().equals(""))) {
                        browser.getSettings().setLoadsImagesAutomatically(true);
                        browser.getSettings().setJavaScriptEnabled(true);
                        browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                        Pattern p = Pattern.compile("^https://*");
                        Matcher m = p.matcher(url.getText().toString());
                        Pattern pi = Pattern.compile("^www.*.com$");
                        Matcher z = pi.matcher(url.getText().toString());
                        if(m.matches())
                        {
                            browser.loadUrl(url.getText().toString());
                            downloadurl n = new downloadurl(url.getText().toString());
                            n.start();
                        }
                        else if (z.matches()) {
                            browser.loadUrl("https://" + url.getText().toString());
                            downloadurl n = new downloadurl("https://" + url.getText().toString());
                            n.start();
                        }
                        else
                        {
                            String j = url.getText().toString();
                            String[] splitStr = j.trim().split("\\s+");
                            String query=" ";
                            for(int i =0 ; i<splitStr.length ; i++)
                            {
                                query=query+splitStr[i]+"+";
                            }
                            browser.loadUrl("https://www.google.com/search?q="+query+"&oq="+query);
                            downloadurl n = new downloadurl("https://www.google.com/search?q="+query+"&oq="+query);
                            n.start();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid URL", Toast.LENGTH_LONG).show();
                    }

                    //or try following:
                    //InputMethodManager imm = (InputMethodManager)getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                    return true;
                }
                return false;

            }
        });

        Button b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (url.getText().toString() != null &&
                        (! url.getText().toString().equals(""))) {
                    browser.getSettings().setLoadsImagesAutomatically(true);
                    browser.getSettings().setJavaScriptEnabled(true);
                    browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
                    Pattern p = Pattern.compile("^https://*");
                    Matcher m = p.matcher(url.getText().toString());
                    Pattern pi = Pattern.compile("^www.*.com$");
                    Matcher z = pi.matcher(url.getText().toString());
                    if(m.matches())
                    {
                        browser.loadUrl(url.getText().toString());
                        downloadurl n = new downloadurl(url.getText().toString());
                        n.start();
                    }
                    else if (z.matches()) {
                        browser.loadUrl("https://" + url.getText().toString());
                        downloadurl n = new downloadurl("https://" + url.getText().toString());
                        n.start();
                    }
                    else
                    {
                        String j = url.getText().toString();
                        String[] splitStr = j.trim().split("\\s+");
                        String query=" ";
                        for(int i =0 ; i<splitStr.length ; i++)
                        {
                            query=query+splitStr[i]+"+";
                        }
                        browser.loadUrl("https://www.google.com/search?q="+query+"&oq="+query);
                        downloadurl n = new downloadurl("https://www.google.com/search?q="+query+"&oq="+query);
                        n.start();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Invalid URL", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(request.getUrl().toString());
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);

        }
        public void onReceivedHttpError (WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            Toast.makeText(view.getContext(), "HTTP error "+errorResponse.getStatusCode(), Toast.LENGTH_LONG).show();
        }



    }
}
