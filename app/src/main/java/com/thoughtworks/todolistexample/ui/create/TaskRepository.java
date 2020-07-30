package com.thoughtworks.todolistexample.ui.create;

import com.thoughtworks.todolistexample.repository.task.entity.Task;

import io.reactivex.Completable;

public interface TaskRepository {
    Completable save(Task task);

}
