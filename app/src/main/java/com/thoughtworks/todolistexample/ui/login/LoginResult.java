package com.thoughtworks.todolistexample.ui.login;

public class LoginResult {
    private LoggedInUserView success;
    private Integer fail;

    public LoginResult(LoggedInUserView success) {
        this.success = success;
    }

    public LoginResult(Integer fail) {
        this.fail = fail;
    }

    public LoggedInUserView getSuccess() {
        return success;
    }

    public Integer getFail() {
        return fail;
    }
}
