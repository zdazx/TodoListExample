package com.thoughtworks.todolistexample.repository.task;

import com.thoughtworks.todolistexample.repository.task.entity.Task;

import io.reactivex.Completable;

public interface TaskDataSource {
    Completable save(Task task);

}
