package com.thoughtworks.todolistexample.ui.login;

public class LoggedInUserView {
    private String displayName;

    public LoggedInUserView(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
