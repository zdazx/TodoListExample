package com.thoughtworks.todolistexample.repository.user;


import com.thoughtworks.todolistexample.repository.user.entity.User;

import io.reactivex.Completable;
import io.reactivex.Maybe;

public interface UserDataSource {

    Maybe<User> findByName(String name);

    Completable save(User user);
}
