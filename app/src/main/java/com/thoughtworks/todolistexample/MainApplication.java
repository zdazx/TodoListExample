package com.thoughtworks.todolistexample;

import android.app.Application;

import androidx.room.Room;

import com.thoughtworks.todolistexample.repository.AppDatabase;
import com.thoughtworks.todolistexample.repository.task.TaskDataSource;
import com.thoughtworks.todolistexample.repository.task.TaskRepositoryImpl;
import com.thoughtworks.todolistexample.repository.user.UserDataSource;
import com.thoughtworks.todolistexample.repository.user.UserRepositoryImpl;
import com.thoughtworks.todolistexample.ui.create.TaskRepository;
import com.thoughtworks.todolistexample.ui.login.UserRepository;

import java.util.Objects;

public class MainApplication extends Application {
    private UserRepository userRepository;
    private TaskRepository taskRepository;
    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        userRepository = new UserRepositoryImpl(userDataSource());
        taskRepository = new TaskRepositoryImpl(taskDataSource());
    }

    @Override
    public void onTerminate() {
        getDatabase().close();
        super.onTerminate();
    }

    private AppDatabase getDatabase() {
        if (Objects.isNull(database)) {
            database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, this.getClass().getSimpleName()).build();
        }
        return database;
    }

    private TaskDataSource taskDataSource() {
        return getDatabase().dbTaskDataSource();
    }

    private UserDataSource userDataSource() {
        return getDatabase().dbUserDataSource();
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public TaskRepository getTaskRepository() {
        return taskRepository;
    }
}
