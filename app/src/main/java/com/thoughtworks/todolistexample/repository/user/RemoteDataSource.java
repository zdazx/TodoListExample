package com.thoughtworks.todolistexample.repository.user;

import com.thoughtworks.todolistexample.repository.user.entity.User;
import com.thoughtworks.todolistexample.repository.utils.GsonUtil;

import io.reactivex.Maybe;
import io.reactivex.internal.operators.maybe.MaybeObserveOn;

public class RemoteDataSource {
    public Maybe<User> getRemoteUser() {
        final String url = "https://twc-android-bootcamp.github.io/fake-data/data/user.json";
        return MaybeObserveOn
                .create(emitter -> emitter.onSuccess(GsonUtil.getData(url, User.class)));
    }
}
