package com.example.puiandroid.task;

import android.app.Activity;
import android.widget.ListView;

// import androidx.recyclerview.widget.RecyclerView;

import com.example.puiandroid.RowArticleAdapter;
import com.example.puiandroid.R;
import com.example.puiandroid.model.Article;
import com.example.puiandroid.network.NetUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.List;

public class APIArticleDownload implements Runnable {

    private static final String API_ARTICLE_BASE_URL = "http://sanger.dia.fi.upm.es/pmd-task/public/list-example/";
    private Activity activity;

    static GsonBuilder gsonBuilder = new GsonBuilder();
    static Gson gson = gsonBuilder.create();

    public APIArticleDownload(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        String result = "";
        try {
            result = NetUtils.getURLText(API_ARTICLE_BASE_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Article article = gson.fromJson(result, Article.class);

        /* for (int i = 0; i < articleList.size(); i++) {
            articleList.get(i).getImageName();
            Article article = articleList.get(i);
            try {
                planet.setBitmap(NetUtils.getURLImage(API_PLANET_BASE_URL + planet.getImageName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } */

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /*ListView recyclerView = activity.findViewById(R.id.lst_articles);
                RowArticleAdapter adapter = new RowArticleAdapter(activity);
                adapter.addArticles(articleList);
                recyclerView.setAdapter(adapter);*/
            }
        });

    }
}
