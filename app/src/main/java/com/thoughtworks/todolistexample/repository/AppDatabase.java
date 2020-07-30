package com.thoughtworks.todolistexample.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.thoughtworks.todolistexample.repository.user.DBUserDataSource;
import com.thoughtworks.todolistexample.repository.user.entity.User;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DBUserDataSource dbUserDataSource();
}
