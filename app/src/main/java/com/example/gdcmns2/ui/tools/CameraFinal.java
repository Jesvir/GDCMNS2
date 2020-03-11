package com.example.gdcmns2.ui.tools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.gdcmns2.R;

public class CameraFinal extends AppCompatActivity {
    ProgressBar progressBar;
    boolean isContinously = false;
    MediaController mediacontroller;
    ImageButton btnRefresh;
    Uri uri;
    WebView webView;
    int pressed;

    String uriString = "http://"+CameraIP._ip+":8000";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_camera_final);
        getSupportActionBar().setTitle(CameraIP.name);
        btnRefresh = (ImageButton)findViewById(R.id.webrefresh);
        webView = (WebView)findViewById(R.id.webViewer);




        playVideo();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void playVideo(){
        try {
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl("http://"+CameraIP._ip+":8000");

            btnRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    webView.reload();
                }
            });


        }
        catch (Exception e){
            Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
