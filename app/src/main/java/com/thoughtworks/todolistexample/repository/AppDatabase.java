package com.thoughtworks.todolistexample.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.thoughtworks.todolistexample.repository.task.DBTaskDataSource;
import com.thoughtworks.todolistexample.repository.task.entity.Task;
import com.thoughtworks.todolistexample.repository.user.DBUserDataSource;
import com.thoughtworks.todolistexample.repository.user.entity.User;

@Database(entities = {User.class, Task.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DBUserDataSource dbUserDataSource();

    public abstract DBTaskDataSource dbTaskDataSource();
}
