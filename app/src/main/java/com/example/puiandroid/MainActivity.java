package com.example.puiandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.puiandroid.task.APIArticleDownload;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        APIArticleDownload downloadTask = new APIArticleDownload(this);
        Thread downloadThread = new Thread(downloadTask);
        downloadThread.start();

        FloatingActionButton fab = findViewById(R.id.fab_login);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(getApplicationContext(), Login.class);

                startActivity(login);

            }
        });
    }
}