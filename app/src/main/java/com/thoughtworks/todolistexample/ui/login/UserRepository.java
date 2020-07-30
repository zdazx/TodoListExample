package com.thoughtworks.todolistexample.ui.login;

import com.thoughtworks.todolistexample.repository.user.entity.User;

import io.reactivex.Completable;
import io.reactivex.Maybe;

public interface UserRepository {
    Maybe<User> findByName(String name);

    Completable save(User user);
}
