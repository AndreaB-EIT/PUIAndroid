package com.example.puiandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.puiandroid.task.APIArticleDownload;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        APIArticleDownload downloadTask = new APIArticleDownload(this);
        Thread downloadThread = new Thread(downloadTask);
        downloadThread.start();
    }
}