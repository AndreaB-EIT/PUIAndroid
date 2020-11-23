package com.example.puiandroid.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.puiandroid.R;
import com.example.puiandroid.task.LoadArticlesTask;
import com.example.puiandroid.utils.network.ModelManager;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private static final String PREF_NAME_TEXT = "EIT_News_Login";
    private static final String PREF_NAME_ATTRIBUTE_USERID = "userID";
    private static final String PREF_NAME_ATTRIBUTE_APIKEY = "APIKEY";
    private static final String PREF_NAME_ATTRIBUTE_AUTHTYPE = "authtype";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("List of articles");
        ((TextView)findViewById(R.id.lbl_greetings)).setText("Fetching your articles, one moment please...");

        ModelManager.configureConnection("https://sanger.dia.fi.upm.es/pmd-task/");

        // if connected (check for preferences --> Read from app preferences the stored values)
        ExtendedFloatingActionButton fab = findViewById(R.id.fab_login);
        SharedPreferences preferences = getSharedPreferences(PREF_NAME_TEXT, Context.MODE_PRIVATE);
        String userID = preferences.getString(PREF_NAME_ATTRIBUTE_USERID, "default");
        String apikey = preferences.getString(PREF_NAME_ATTRIBUTE_APIKEY, "default");
        String authtype = preferences.getString(PREF_NAME_ATTRIBUTE_AUTHTYPE, "default");
        if(userID != "default" && apikey != "default" && authtype != "default") {
            fab.setText("LOGOUT");
            fab.setIcon(getResources().getDrawable(R.drawable.logout_icon, getTheme()));
            fab.setOnClickListener(v -> {
                ModelManager.logout();
                SharedPreferences logging_out = getSharedPreferences(PREF_NAME_TEXT,Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = logging_out.edit();
                editor.clear();
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            });
            ModelManager.stayloggedin(userID, preferences.getString(PREF_NAME_ATTRIBUTE_APIKEY, "default"), preferences.getString(PREF_NAME_ATTRIBUTE_AUTHTYPE, "default"));
        } else {
            fab.setOnClickListener(v -> {
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(login);
            });
        }

        LoadArticlesTask loadArticlesTask = new LoadArticlesTask(this);
        loadArticlesTask.execute();

        ListView listView = findViewById(R.id.lst_articles);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent details = new Intent(getApplicationContext(), ArticleDetailsActivity.class);
            details.putExtra("id", (int)id);
            details.putExtra("orderIfEdit", position);
            startActivity(details);
        });

        TabLayout tabLayout = findViewById(R.id.tbs_main);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getText().toString()) {
                    case "National":
                        LoadArticlesTask loadArticlesTaskN = new LoadArticlesTask(MainActivity.this, "National");
                        loadArticlesTaskN.execute();
                        break;
                    case "Economy":
                        LoadArticlesTask loadArticlesTaskE = new LoadArticlesTask(MainActivity.this, "Economy");
                        loadArticlesTaskE.execute();
                        break;
                    case "Sports":
                        LoadArticlesTask loadArticlesTaskS = new LoadArticlesTask(MainActivity.this, "Sports");
                        loadArticlesTaskS.execute();
                        break;
                    case "Technology":
                        LoadArticlesTask loadArticlesTaskT = new LoadArticlesTask(MainActivity.this, "Technology");
                        loadArticlesTaskT.execute();
                        break;
                    case "All":
                        LoadArticlesTask loadArticlesTask = new LoadArticlesTask(MainActivity.this);
                        loadArticlesTask.execute();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }
}
