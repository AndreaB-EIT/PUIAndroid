package com.example.puiandroid.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import com.example.puiandroid.R;
import com.example.puiandroid.adapters.RowArticleAdapter;
import com.example.puiandroid.model.Article;
import com.example.puiandroid.utils.network.ModelManager;
import com.example.puiandroid.utils.network.exceptions.AuthenticationError;
import com.example.puiandroid.utils.network.exceptions.ServerCommunicationError;

public class LoadArticlesTask extends AsyncTask<Void, Void, List<Article>> {
    
	private static final String TAG = "LoadArticlesTask";
	private Activity activity;
	private ProgressBar progressBar;
	private String greetings;
	private String filter = "All";

    public LoadArticlesTask(Activity activity) {
        this.activity = activity;
    }

    public LoadArticlesTask(Activity activity, String filter) {
        this.activity = activity;
        this.filter = filter;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressBar = activity.findViewById(R.id.pgb_main);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);
    }

    public List<Article> getFilteredList(List<Article> list) {
        List<Article> filtered = new LinkedList<>();

        for (Article article : list) {
            if (article.getCategory().equals(filter)) {
                filtered.add(article);
                Log.i(TAG, "just got this: " + article.getCategory());
            }
        }
        return filtered;
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
            progressBar.setProgress(20);
            res = ModelManager.getArticles();
            progressBar.setProgress(30);

            if (filter != "All") {
                res = getFilteredList(res);
                progressBar.setProgress(40);
            }
            for (Article article : res)
                Log.i(TAG, article.toString());
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

        if (articles.size() == 0)
            activity.findViewById(R.id.lbl_no_articles_found).setVisibility(View.VISIBLE);
        else
            activity.findViewById(R.id.lbl_no_articles_found).setVisibility(View.GONE);

        ListView listView = activity.findViewById(R.id.lst_articles);
        RowArticleAdapter adapter = new RowArticleAdapter(activity);
        adapter.addArticles(articles);
        listView.setAdapter(adapter);

        ((TextView)activity.findViewById(R.id.lbl_greetings)).setText(greetings);
        activity.findViewById(R.id.lbl_greetings).setPadding(0,8,0,24);
        activity.findViewById(R.id.tbs_main).setVisibility(View.VISIBLE);

        progressBar.setVisibility(View.GONE);
    }
}
