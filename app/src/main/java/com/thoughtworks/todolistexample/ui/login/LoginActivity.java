package com.thoughtworks.todolistexample.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thoughtworks.todolistexample.R;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameView;
    private EditText passwordView;
    private LoginViewModel loginViewModel;
    public static final String tag = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameView = findViewById(R.id.username);
        passwordView = findViewById(R.id.password);
        Button loginBtn = findViewById(R.id.login);

        usernameView.addTextChangedListener(
                new LoginWatcher(usernameView, passwordView, loginBtn, getApplicationContext()));
        passwordView.addTextChangedListener(
                new LoginWatcher(usernameView, passwordView, loginBtn, getApplicationContext()));
        loginBtn.setOnClickListener(view -> login());

        loginViewModel = new LoginViewModel();
        final Observer<Boolean> observer = aBoolean -> {
            if (!aBoolean) {
                Toast.makeText(getApplicationContext(), "用户不存在", Toast.LENGTH_LONG).show();
            }
            openHomepageActivity();
        };
        loginViewModel.getLoginResult().observe(this, observer);

    }

    private void openHomepageActivity() {
        Log.d(tag, "login success");
    }

    private void login() {
        loginViewModel.login(usernameView.getText().toString(), passwordView.getText().toString());
    }
}