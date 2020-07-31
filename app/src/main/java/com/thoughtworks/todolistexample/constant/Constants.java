package com.thoughtworks.todolistexample.constant;

public enum Constants {
    CREATE_FAILED("创建失败"),
    CREATE_SUCCESS("创建成功"),
    MUST_EDIT_TITLE("请将标题填写完整"),
    MUST_EDIT_DATE("请选择日期");

    private String name;

    Constants(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
