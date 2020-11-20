package com.example.puiandroid.task;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.Properties;

import com.example.puiandroid.model.Article;
import com.example.puiandroid.utils.network.ModelManager;
import com.example.puiandroid.utils.network.RESTConnection;
import com.example.puiandroid.utils.network.exceptions.AuthenticationError;
import com.example.puiandroid.utils.network.exceptions.ServerCommunicationError;

public class LoadArticlesTask extends AsyncTask<Void, Void, List<Article>> {
    
	private static final String TAG = "LoadArticlesTask";

	/*private static final String strIdUser = "us_4_1";
    private static final String strApiKey = "DEV_TEAM_4414";
    private static final String strIdAuthUser = "4414";*/

	@Override
    protected List<Article> doInBackground(Void... voids) {
        List<Article> res = null;
		//ModelManager uses singleton pattern, connecting once per app execution in enough
        if (!ModelManager.isConnected()){
			// if it is the first login
            if (strIdUser==null || strIdUser.equals("")) {
                try {
                    ModelManager.login("ws_user", "ws_password");
                } catch (AuthenticationError e) {
                    Log.e(TAG, e.getMessage());
                }
            }
			// if we have saved user credentials from previous connections
			else{
                ModelManager.stayloggedin(strIdUser,strApiKey,strIdAuthUser);
            }
        }
		//If connection has been successful
        if (ModelManager.isConnected()) {
            try {
				// obtain 6 articles from offset 0
                res = ModelManager.getArticles(6, 0);
                for (Article article : res) {
					// We print articles in Log
                    Log.i(TAG, article.toString());
                }
            } catch (ServerCommunicationError e) {
                Log.e(TAG,e.getMessage());
            }
        }
        return res;
    }
}