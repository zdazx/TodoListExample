package com.thoughtworks.todolistexample.repository.user;

import com.thoughtworks.todolistexample.repository.user.entity.User;
import com.thoughtworks.todolistexample.ui.login.UserRepository;

import io.reactivex.Completable;
import io.reactivex.Maybe;

public class UserRepositoryImpl implements UserRepository {
    private UserDataSource userDataSource;
    private RemoteDataSource remoteDataSource;

    public UserRepositoryImpl(UserDataSource userDataSource) {
        this.userDataSource = userDataSource;
        remoteDataSource = new RemoteDataSource();
    }

    @Override
    public Maybe<User> findByName(String name) {
        return userDataSource.findByName(name)
                .switchIfEmpty(remoteDataSource.getRemoteUser())
                .doOnSuccess(user -> userDataSource.save(user));

    }

    @Override
    public Completable save(User user) {
        return userDataSource.save(user);
    }
}
