package com.thoughtworks.todolistexample.repository.task;

import com.thoughtworks.todolistexample.repository.task.entity.Task;
import com.thoughtworks.todolistexample.ui.create.TaskRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Maybe;

public class TaskRepositoryImpl implements TaskRepository {
    private TaskDataSource taskDataSource;

    public TaskRepositoryImpl(TaskDataSource taskDataSource) {
        this.taskDataSource = taskDataSource;
    }

    @Override
    public Maybe<Long> save(Task task) {
        return taskDataSource.save(task);
    }

    @Override
    public Maybe<List<Task>> getAllTasks() {
        return taskDataSource.getAllTasks();
    }
}
