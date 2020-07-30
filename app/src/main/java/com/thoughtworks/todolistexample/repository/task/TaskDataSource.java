package com.thoughtworks.todolistexample.repository.task;

import com.thoughtworks.todolistexample.repository.task.entity.Task;

import io.reactivex.Maybe;

public interface TaskDataSource {
    Maybe<Long> save(Task task);

}
