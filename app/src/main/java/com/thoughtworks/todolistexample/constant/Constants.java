package com.thoughtworks.todolistexample.constant;

public enum Constants {
    LOG_TAG("todo-list-tag");

    private String name;

    Constants(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
