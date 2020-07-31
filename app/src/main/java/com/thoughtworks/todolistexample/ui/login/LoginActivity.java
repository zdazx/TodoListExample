package com.thoughtworks.todolistexample.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.thoughtworks.todolistexample.MainApplication;
import com.thoughtworks.todolistexample.R;
import com.thoughtworks.todolistexample.ui.home.HomeActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameView;
    private EditText passwordView;
    private LoginViewModel loginViewModel;
    private Button loginButton;
    private final String LOGIN_ACTIVITY = this.getClass().getSimpleName();

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
        final Observer<Boolean> observer = aBoolean -> {
            if (!aBoolean) {
                Toast.makeText(getApplicationContext(), "用户不存在", Toast.LENGTH_LONG).show();
                return;
            }
            openHomeActivity();
        };
        loginViewModel.getLoginResult().observe(this, observer);

    }

    private LoginViewModel obtainViewModel() {
        UserRepository userRepository = ((MainApplication) getApplicationContext()).getUserRepository();
        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.setUserRepository(userRepository);
        return loginViewModel;
    }

    private void openHomeActivity() {
        Log.d(LOGIN_ACTIVITY, "login success");
        Toast.makeText(getApplicationContext(), "Welcome!", Toast.LENGTH_LONG).show();
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
                usernameView.setError("用户名必须是3 ~ 12为字母或数字");
            } else if (!isPasswordValid()) {
                passwordView.setError("密码长度必须是6 ~ 18位字符");
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