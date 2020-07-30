package com.thoughtworks.todolistexample.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.thoughtworks.todolistexample.MainApplication;
import com.thoughtworks.todolistexample.R;
import static com.thoughtworks.todolistexample.constant.Constants.LOG_TAG;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameView;
    private EditText passwordView;
    private LoginViewModel loginViewModel;

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

        loginViewModel = obtainViewModel();
        final Observer<Boolean> observer = aBoolean -> {
            if (!aBoolean) {
                Toast.makeText(getApplicationContext(), "用户不存在", Toast.LENGTH_LONG).show();
                return;
            }
            openHomepageActivity();
        };
        loginViewModel.getLoginResult().observe(this, observer);

    }

    private LoginViewModel obtainViewModel() {
        UserRepository userRepository = ((MainApplication) getApplicationContext()).getUserRepository();
        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.setUserRepository(userRepository);
        return loginViewModel;
    }

    private void openHomepageActivity() {
        Log.d(LOG_TAG.name(), "login success");
        Toast.makeText(getApplicationContext(), "Welcome!", Toast.LENGTH_LONG).show();
    }

    private void login() {
        loginViewModel.login(usernameView.getText().toString(), passwordView.getText().toString());
    }
}