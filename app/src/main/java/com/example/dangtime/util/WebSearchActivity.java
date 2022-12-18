package com.example.dangtime.util;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.dangtime.R;

public class WebSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_search);

        WebView wv = findViewById(R.id.wv);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.addJavascriptInterface(new BridgeInterface(), "Android");
        wv.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                wv.loadUrl("javascript:sample2_execDaumPostcode();");
            }
        });

        wv.loadUrl("https://dangtimerefactoring.web.app/");
    }

    private  class BridgeInterface{
        @JavascriptInterface
        public void processDATA(String data){
//            다음(카카오) 주소 검색 API의 결과 값이 브릿지 통로를 통해 전달 받는다. (from Javascript)
            Intent intent = new Intent();
            intent.putExtra("data", data);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}