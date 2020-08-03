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

        final Observer<LoginFormState> formStateObserver = this::showFormInputResult;
        loginViewModel.getLoginFormStateResult().observe(this, formStateObserver);

        final Observer<LoginResult> observer = this::showLoginResult;
        loginViewModel.getLoginResult().observe(this, observer);

    }

    private void showFormInputResult(LoginFormState loginFormState) {
        setLoginBtnStyle(false, R.color.colorDeepGray);
        if (Objects.nonNull(loginFormState.getUsernameError())) {
            usernameView.setError(getString(R.string.username_error));
        } else if (Objects.nonNull(loginFormState.getPasswordError())) {
            passwordView.setError(getString(R.string.password_error));
        } else {
            setLoginBtnStyle(true, R.color.colorWhite);
        }
    }

    private void setLoginBtnStyle(boolean isEnable, int textColor) {
        loginButton.setEnabled(isEnable);
        loginButton.setTextColor(getColor(textColor));
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
            loginViewModel.loginDataChanged(usernameView.getText().toString().trim(), passwordView.getText().toString());
        }
    }
}