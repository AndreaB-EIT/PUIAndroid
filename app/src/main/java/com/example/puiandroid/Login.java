package com.example.puiandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.puiandroid.task.LoginTask;
import com.example.puiandroid.utils.network.ModelManager;
import com.example.puiandroid.utils.network.exceptions.AuthenticationError;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        setTitle("Login");

        Button button = findViewById(R.id.btn_login);
        button.setEnabled(false);
        EditText username = findViewById(R.id.txt_login_username);
        EditText password = findViewById(R.id.pwd_login_password);

        TextWatcher loginTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String usernameInput = username.getText().toString().trim();
                String passwordInput = password.getText().toString().trim();
                button.setEnabled(!usernameInput.isEmpty() && !passwordInput.isEmpty());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        username.addTextChangedListener(loginTextWatcher);
        password.addTextChangedListener(loginTextWatcher);
        button.setOnClickListener(v -> {
            String usernameText = username.getText().toString();
            String passwordText = password.getText().toString();

            LoginTask loginTask = new LoginTask(usernameText, passwordText, Login.this);
            Thread loginThread = new Thread(loginTask);
            loginThread.start();

            // when logged in you get the parameters
            // set up the API key

            // call the login
            // get user id, api key and auth
            // store them in the preferences
        });

    }
}
