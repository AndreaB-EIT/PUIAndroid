package com.example.puiandroid.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.puiandroid.R;
import com.example.puiandroid.model.Article;
import com.example.puiandroid.utils.SerializationUtils;
import com.example.puiandroid.utils.network.ModelManager;
import com.example.puiandroid.utils.network.exceptions.ServerCommunicationError;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LoadSingleArticleTask extends AsyncTask<Void, Void, Article> {

    private static final String TAG = "LoadArticlesTask";
    private Activity activity;
    private int id;
    private ProgressBar progressBar;

    public LoadSingleArticleTask(Activity activity, int id) {
        this.activity = activity;
        this.id = id;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressBar = activity.findViewById(R.id.pgb_details);
        progressBar.setProgress(0);
    }

    @Override
    protected Article doInBackground(Void... voids) {
        Article res = null;
        progressBar.setProgress(10);
        try {
            progressBar.setProgress(20);
            res = ModelManager.getArticle(id);
            progressBar.setProgress(30);
            Log.i(TAG, res.toString());
        } catch (ServerCommunicationError e) {
            Log.e(TAG,e.getMessage());
        }
        progressBar.setProgress(50);
        return res;
    }

    @Override
    protected void onPostExecute(Article article) {
        super.onPostExecute(article);
        progressBar.setProgress(60);

        activity.setTitle(article.getTitleText());

        TextView category = activity.findViewById(R.id.lbl_details_category);
        category.setText(article.getCategory());

        TextView title = activity.findViewById(R.id.lbl_details_title);
        title.setText(article.getTitleText());

        TextView subtitle = activity.findViewById(R.id.lbl_details_subtitle);
        subtitle.setText(article.getSubtitleText());

        ImageView image = activity.findViewById(R.id.img_details_image);
        Bitmap bitmap = null;
        try {
            bitmap = SerializationUtils.base64StringToImg(article.getImage().getImage());
        } catch (ServerCommunicationError serverCommunicationError) {
            serverCommunicationError.printStackTrace();
        }
        if (bitmap == null)
            image.setImageDrawable(activity.getResources().getDrawable(R.drawable.upmlogo, activity.getTheme()));
        else
            image.setImageBitmap(bitmap);

        TextView aabstract = activity.findViewById(R.id.lbl_details_abstract);
        aabstract.setText(article.getAbstractText());

        TextView body = activity.findViewById(R.id.lbl_details_body);
        body.setText(article.getBodyText());

        progressBar.setProgress(90);

        TextView lastUpdate = activity.findViewById(R.id.lbl_details_last_update);
        Date lastUpdateDate = article.getLastUpdate();
        String patternBig = "dd/MM/yyyy";
        String patternSmall = "HH:mm:ss";
        DateFormat dfBig = new SimpleDateFormat(patternBig);
        DateFormat dfSmall = new SimpleDateFormat(patternSmall);
        String lastUpdateStringBig = dfBig.format(lastUpdateDate);
        String lastUpdateStringSmall = dfSmall.format(lastUpdateDate);
        lastUpdate.setText("Last updated on the day " + lastUpdateStringBig + " at " + lastUpdateStringSmall + " by " + article.getIdUser());

        activity.findViewById(R.id.lbl_details_wait).setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        activity.findViewById(R.id.lly_content).setVisibility(View.VISIBLE);

    }

}
