package com.thoughtworks.todolistexample.repository.task;

import com.thoughtworks.todolistexample.repository.task.entity.Task;

import java.util.List;

import io.reactivex.Maybe;

public interface TaskDataSource {
    Maybe<Long> save(Task task);

    Maybe<List<Task>> getAllTasks();

    Maybe<Integer> update(Task task);

    Maybe<Integer> delete(Task task);

}
