package com.thoughtworks.todolistexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText usernameView = findViewById(R.id.username);
        EditText passwordView = findViewById(R.id.password);
        Button loginBtn = findViewById(R.id.login);

        usernameView.addTextChangedListener(
                new LoginWatcher(usernameView, passwordView, loginBtn, getApplicationContext()));
        passwordView.addTextChangedListener(
                new LoginWatcher(usernameView, passwordView, loginBtn, getApplicationContext()));
    }
}