package com.thoughtworks.todolistexample.constant;

public enum Constants {
    IS_HAVE_TASK("isHaveTask"),
    TASK_KEY("TASK_KEY"),
    TASK_NOTIFICATION("TASK_NOTIFICATION");

    private String name;

    Constants(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
