package com.thoughtworks.todolistexample.repository.user;

import com.thoughtworks.todolistexample.repository.user.entity.User;
import com.thoughtworks.todolistexample.repository.utils.GsonUtil;

import io.reactivex.Maybe;
import io.reactivex.internal.operators.maybe.MaybeObserveOn;

import static com.thoughtworks.todolistexample.constant.ConstantValue.REMOTE_USER_URL;

public class RemoteDataSource {
    public Maybe<User> getRemoteUser() {
        return MaybeObserveOn
                .create(emitter -> emitter.onSuccess(GsonUtil.getData(REMOTE_USER_URL, User.class)));
    }
}
