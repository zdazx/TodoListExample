package com.thoughtworks.todolistexample.constant;

public enum Constants {
    LOG_TAG("todo-list-tag"),
    CREATE_FAILED("创建失败"),
    CREATE_SUCCESS("创建成功");

    private String name;

    Constants(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
