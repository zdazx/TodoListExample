package com.thoughtworks.todolistexample.repository.user.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private String password;

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
