package com.example.puiandroid.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
import java.util.Properties;

import com.example.puiandroid.MainActivity;
import com.example.puiandroid.R;
import com.example.puiandroid.RowArticleAdapter;
import com.example.puiandroid.model.Article;
import com.example.puiandroid.utils.network.ModelManager;
import com.example.puiandroid.utils.network.RESTConnection;
import com.example.puiandroid.utils.network.exceptions.AuthenticationError;
import com.example.puiandroid.utils.network.exceptions.ServerCommunicationError;

public class LoadArticlesTask extends AsyncTask<Void, Void, List<Article>> {
    
	private static final String TAG = "LoadArticlesTask";
	private Activity activity;
	private ProgressBar progressBar;
	private String greetings;

    public LoadArticlesTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressBar = activity.findViewById(R.id.pgb_main);
        progressBar.setProgress(0);
    }

    @Override
    protected List<Article> doInBackground(Void... voids) {
        List<Article> res = null;
        progressBar.setProgress(10);

		//ModelManager uses singleton pattern, connecting once per app execution in enough
        if (!ModelManager.isConnected()){
            try {
                ModelManager.login("ws_user", "ws_password");
            } catch (AuthenticationError e) {
                Log.e(TAG, e.getMessage());
            }
            greetings = "Hello, guest! You may login with the bottom right button!";
        } else greetings = "Hello and welcome back, user!";
        try {
            // obtain 6 articles from offset 0
            progressBar.setProgress(20);
            res = ModelManager.getArticles(6, 0);
            progressBar.setProgress(30);
            for (Article article : res) {
                // We print articles in Log
                Log.i(TAG, article.toString());
            }
        } catch (ServerCommunicationError e) {
            Log.e(TAG,e.getMessage());
        }

        progressBar.setProgress(50);

        return res;
    }

    @Override
    protected void onPostExecute(List<Article> articles) {
        super.onPostExecute(articles);

        progressBar.setProgress(80);

        ListView recyclerView = activity.findViewById(R.id.lst_articles);
        RowArticleAdapter adapter = new RowArticleAdapter(activity);
        adapter.addArticles(articles);
        recyclerView.setAdapter(adapter);

        ((TextView)activity.findViewById(R.id.lbl_greetings)).setText(greetings);
        activity.findViewById(R.id.lbl_greetings).setPadding(0,8,0,24);

        progressBar.setVisibility(View.GONE);

    }


}
