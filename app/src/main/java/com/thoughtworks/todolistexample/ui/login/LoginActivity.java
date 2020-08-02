package com.thoughtworks.todolistexample.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.thoughtworks.todolistexample.MainApplication;
import com.thoughtworks.todolistexample.R;
import com.thoughtworks.todolistexample.ui.home.HomeActivity;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameView;
    private EditText passwordView;
    private LoginViewModel loginViewModel;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameView = findViewById(R.id.username);
        passwordView = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);

        usernameView.addTextChangedListener(new LoginWatcher());
        passwordView.addTextChangedListener(new LoginWatcher());
        loginButton.setOnClickListener(view -> login());

        loginViewModel = obtainViewModel();
        final Observer<LoginResult> observer = this::showLoginResult;
        loginViewModel.getLoginResult().observe(this, observer);

    }

    private void showLoginResult(LoginResult loginResult) {
        if (Objects.nonNull(loginResult.getFail())) {
            showLogFailedInfo(loginResult.getFail());
        }
        if (Objects.nonNull(loginResult.getSuccess())) {
            showLogSuccessInfo(loginResult.getSuccess());
            openHomeActivity();
        }
    }

    private void showLogSuccessInfo(LoggedInUserView loggedInUser) {
        String welcome = "Welcome! " + loggedInUser.getDisplayName();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLogFailedInfo(Integer failedInfo) {
        Toast.makeText(getApplicationContext(), getString(failedInfo), Toast.LENGTH_LONG).show();
    }

    private LoginViewModel obtainViewModel() {
        UserRepository userRepository = ((MainApplication) getApplicationContext()).getUserRepository();
        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.setUserRepository(userRepository);
        return loginViewModel;
    }

    private void openHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }

    private void login() {
        loginViewModel.login(usernameView.getText().toString(), passwordView.getText().toString());
    }

    private class LoginWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            setLoginBtnStyle(false, R.color.colorDeepGray, R.drawable.login_button_not_enable);

            if (!isUsernameValid()) {
                usernameView.setError(getString(R.string.username_error));
            } else if (!isPasswordValid()) {
                passwordView.setError(getString(R.string.password_error));
            } else {
                setLoginBtnStyle(true, R.color.colorWhite, R.drawable.login_button_enable);
            }
        }

        private boolean isPasswordValid() {
            String password = passwordView.getText().toString();
            return password.length() > 5 && password.length() < 19;
        }

        private boolean isUsernameValid() {
            String username = usernameView.getText().toString().trim();
            Pattern pattern = Pattern.compile("[0-9a-zA-Z]{3,12}");
            Matcher matcher = pattern.matcher(username);
            return matcher.matches();
        }

        private void setLoginBtnStyle(boolean isEnable, int textColor, int background) {
            loginButton.setEnabled(isEnable);
            loginButton.setTextColor(getColor(textColor));
            loginButton.setBackground(getDrawable(background));
        }
    }
}