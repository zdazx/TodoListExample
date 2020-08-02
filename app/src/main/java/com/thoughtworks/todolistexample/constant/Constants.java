package com.thoughtworks.todolistexample.constant;

public enum Constants {
    IS_HAVE_TASK("isHaveTask");

    private String name;

    Constants(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
