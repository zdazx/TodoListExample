package com.thoughtworks.todolistexample.repository.task;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.thoughtworks.todolistexample.repository.task.entity.Task;

import java.util.List;

import io.reactivex.Maybe;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface DBTaskDataSource extends TaskDataSource {
    @Insert(onConflict = REPLACE)
    Maybe<Long> save(Task task);

    @Query("SELECT * FROM task")
    Maybe<List<Task>> getAllTasks();

    @Update
    Maybe<Integer> update(Task task);

}
