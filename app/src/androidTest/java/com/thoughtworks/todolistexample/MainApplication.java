package com.thoughtworks.todolistexample;

import android.app.Application;

import com.thoughtworks.todolistexample.repository.task.TaskRepositoryImpl;
import com.thoughtworks.todolistexample.repository.user.UserRepositoryImpl;
import com.thoughtworks.todolistexample.ui.create.TaskRepository;
import com.thoughtworks.todolistexample.ui.login.UserRepository;

import java.util.Objects;

import static org.mockito.Mockito.mock;

public class MainApplication extends Application {
    private UserRepository userRepository;
    private TaskRepository taskRepository;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public UserRepository getUserRepository() {
        if (Objects.isNull(userRepository)) {
            userRepository = mock(UserRepositoryImpl.class);
        }
        return userRepository;
    }

    public TaskRepository getTaskRepository() {
        if (Objects.isNull(taskRepository)) {
            taskRepository = mock(TaskRepositoryImpl.class);
        }
        return taskRepository;
    }
}
