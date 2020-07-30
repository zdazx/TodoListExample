package com.thoughtworks.todolistexample.ui.login;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.thoughtworks.todolistexample.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static androidx.core.content.ContextCompat.getColor;
import static androidx.core.content.ContextCompat.getDrawable;

public class LoginWatcher implements TextWatcher {
    private EditText usernameET;
    private EditText passwordET;
    private Button loginBtn;
    private Context context;

    public LoginWatcher(EditText usernameET, EditText passwordET, Button loginBtn, Context context) {
        this.usernameET = usernameET;
        this.passwordET = passwordET;
        this.loginBtn = loginBtn;
        this.context = context;
    }

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
            usernameET.setError("用户名必须是3 ~ 12为字母或数字");
        } else if (!isPasswordValid()) {
            passwordET.setError("密码长度必须是6 ~ 18位字符");
        } else {
            setLoginBtnStyle(true, R.color.colorWhite, R.drawable.login_button_enable);
        }
    }

    private boolean isPasswordValid() {
        String password = passwordET.getText().toString();
        return password.length() > 5 && password.length() < 19;
    }

    private boolean isUsernameValid() {
        String username = usernameET.getText().toString().trim();
        Pattern pattern = Pattern.compile("[0-9a-zA-Z]{3,12}");
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    private void setLoginBtnStyle(boolean isEnable, int textColor, int background) {
        loginBtn.setEnabled(isEnable);
        loginBtn.setTextColor(getColor(context, textColor));
        loginBtn.setBackground(getDrawable(context, background));
    }
}
