package com.thoughtworks.todolistexample.repository.task;

import androidx.room.Dao;
import androidx.room.Insert;

import com.thoughtworks.todolistexample.repository.task.entity.Task;

import io.reactivex.Completable;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface DBTaskDataSource extends TaskDataSource {
    @Insert(onConflict = REPLACE)
    Completable save(Task task);

}
