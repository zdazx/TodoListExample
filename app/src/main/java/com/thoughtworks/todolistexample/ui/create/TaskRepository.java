package com.thoughtworks.todolistexample.ui.create;

import com.thoughtworks.todolistexample.repository.task.entity.Task;

import java.util.List;

import io.reactivex.Maybe;

public interface TaskRepository {
    Maybe<Long> save(Task task);

    Maybe<List<Task>> getAllTasks();

    Maybe<Integer> update(Task task);

}
