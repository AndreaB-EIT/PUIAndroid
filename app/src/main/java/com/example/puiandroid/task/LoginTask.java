package com.example.puiandroid.task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.puiandroid.MainActivity;
import com.example.puiandroid.utils.network.ModelManager;
import com.example.puiandroid.utils.network.exceptions.AuthenticationError;

import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;

public class LoginTask implements Runnable {

    private String username = "";
    private String password = "";
    private Activity activity;

    private static final String PREF_NAME_TEXT = "EIT_News_Login";
    private static final String PREF_NAME_ATTRIBUTE_USERID = "userID";
    private static final String PREF_NAME_ATTRIBUTE_APIKEY = "APIKEY";
    private static final String PREF_NAME_ATTRIBUTE_AUTHTYPE = "authtype";

    public LoginTask(String username, String password, Activity activity) {
        this.username = username;
        this.password = password;
        this.activity = activity;
    }

    @Override
    public void run() {
        try {
            ModelManager.login(username, password);
            SharedPreferences.Editor edit = activity.getSharedPreferences(PREF_NAME_TEXT, MODE_PRIVATE).edit();
            edit.putString(PREF_NAME_ATTRIBUTE_USERID, ModelManager.getLoggedIdUSer());
            edit.putString(PREF_NAME_ATTRIBUTE_APIKEY, ModelManager.getLoggedApiKey());
            edit.putString(PREF_NAME_ATTRIBUTE_AUTHTYPE, ModelManager.getLoggedAuthType());
            edit.commit();

            Intent backToMain = new Intent(activity, MainActivity.class);
            activity.startActivity(backToMain);

        } catch (AuthenticationError authenticationError) {
            authenticationError.printStackTrace();
        }

    }
}
