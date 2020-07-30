package com.thoughtworks.todolistexample.repository.user;

import com.thoughtworks.todolistexample.repository.user.entity.User;
import com.thoughtworks.todolistexample.ui.login.UserRepository;

import io.reactivex.Completable;
import io.reactivex.Maybe;

public class UserRepositoryImpl implements UserRepository {
    private UserDataSource userDataSource;

    public UserRepositoryImpl(UserDataSource userDataSource) {
        this.userDataSource = userDataSource;
    }

    @Override
    public Maybe<User> findByName(String name) {
        return userDataSource.findByName(name);
    }

    @Override
    public Completable save(User user) {
        return userDataSource.save(user);
    }
}
