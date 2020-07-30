package com.thoughtworks.todolistexample;

import android.app.Application;

import androidx.room.Room;

import com.thoughtworks.todolistexample.repository.AppDatabase;
import com.thoughtworks.todolistexample.repository.user.UserDataSource;
import com.thoughtworks.todolistexample.repository.user.UserRepositoryImpl;
import com.thoughtworks.todolistexample.ui.login.UserRepository;

public class MainApplication extends Application {
    private UserRepository userRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        userRepository = new UserRepositoryImpl(userDataSource());
    }

    private UserDataSource userDataSource() {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, this.getClass().getSimpleName()).build();
        return db.dbUserDataSource();
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
