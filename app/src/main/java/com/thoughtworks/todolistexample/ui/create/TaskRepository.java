package com.thoughtworks.todolistexample.ui.create;

import com.thoughtworks.todolistexample.repository.task.entity.Task;

import io.reactivex.Maybe;

public interface TaskRepository {
    Maybe<Long> save(Task task);

}
