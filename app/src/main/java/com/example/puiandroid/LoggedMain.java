package com.example.puiandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.puiandroid.task.APIArticleDownload;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LoggedMain extends AppCompatActivity {
    
    /////////
    // WIP //
    /////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        APIArticleDownload downloadTask = new APIArticleDownload(this);
        Thread downloadThread = new Thread(downloadTask);
        downloadThread.start();

        FloatingActionButton fab = findViewById(R.id.fab_login);
        // fab.image change image so it'll be logout (different layout?)
        /* fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(getApplicationContext(), Login.class);

                startActivity(login);

            }
        }); */
    }
}
